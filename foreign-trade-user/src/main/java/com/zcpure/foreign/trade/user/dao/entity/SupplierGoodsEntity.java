package com.zcpure.foreign.trade.user.dao.entity;

import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import com.zcpure.foreign.trade.dto.user.SupplierGoodsDTO;
import com.zcpure.foreign.trade.enums.SupplierGoodsStatusEnum;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 管理用户
 */
@Table(name = "ft_supplier_goods")
@Where(clause = "delete_flag <> 1")
@Entity
@Data
public class SupplierGoodsEntity extends BaseEntity {
	private static final long serialVersionUID = 1111320370190733556L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String supplierCode;
	@Column(nullable = false)
	private String supplierName;
	@Column(nullable = false)
	private String goodsCode;
	@Column(nullable = false)
	private String goodsName;
	@Column(nullable = false)
	private String groupCode;
	@Column(nullable = false)
	private String groupName;
	private Long brandId;
	private String brandName;
	private Long modelId;
	private String modelName;
	private Long categoryId;
	private String categoryName;
	private String categoryLinkName;
	private String mainImg;
	private String otherImg;
	private String descInfo;
	private BigDecimal price;
	private Integer status;

	public static SupplierGoodsDTO formDTO(SupplierGoodsEntity entity) {
		if (entity == null) {
			return null;
		}
		SupplierGoodsDTO dto = new SupplierGoodsDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public static SupplierGoodsEntity form(SupplierEntity supplierEntity, GoodsDTO goodsDTO, BigDecimal price) {
		SupplierGoodsEntity entity = new SupplierGoodsEntity();
		BeanUtils.copyProperties(goodsDTO, entity);
		entity.setGoodsCode(goodsDTO.getCode());
		entity.setGoodsName(goodsDTO.getName());
		entity.setSupplierCode(supplierEntity.getCode());
		entity.setSupplierName(supplierEntity.getName());
		entity.setGoodsCode(supplierEntity.getGroupCode());
		entity.setGoodsName(supplierEntity.getGroupName());
		entity.setPrice(price);
		entity.setStatus(SupplierGoodsStatusEnum.NORMAL.getCode());
		return entity;

	}
}
