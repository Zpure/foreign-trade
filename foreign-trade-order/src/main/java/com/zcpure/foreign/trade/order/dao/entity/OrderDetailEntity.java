package com.zcpure.foreign.trade.order.dao.entity;

import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import com.zcpure.foreign.trade.dto.order.OrderDetailDTO;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Table(name = "ft_order_detail")
@Where(clause = "delete_flag <> 1")
@Entity
@Data
public class OrderDetailEntity extends BaseEntity {
	private static final long serialVersionUID = 1111320370190733556L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String orderCode;
	private String groupCode;
	private String goodsCode;
	private String goodsName;
	private Long brandId;
	private String brandName;
	private Long modelId;
	private String modelName;
	private Long categoryId;
	private String categoryName;
	private String categoryLinkName;
	private BigDecimal costPrice;
	private BigDecimal salePrice;
	private String mainImg;
	private String otherImg;
	private String descInfo;
	private Integer num;
	private Integer initDisNum = 0;
	private Integer disNum = 0;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "detailId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
	private List<OrderDisDetailEntity> disDetailEntityList;

	public static OrderDetailEntity form(GoodsDTO goodsDTO, Integer num) {
		if (goodsDTO == null) {
			return null;
		}
		OrderDetailEntity entity = new OrderDetailEntity();
		entity.setGroupCode(goodsDTO.getGroupCode());
		entity.setGoodsCode(goodsDTO.getCode());
		entity.setGoodsName(goodsDTO.getName());
		entity.setBrandId(goodsDTO.getBrandId());
		entity.setBrandName(goodsDTO.getBrandName());
		entity.setModelId(goodsDTO.getModelId());
		entity.setModelName(goodsDTO.getModelName());
		entity.setCategoryId(goodsDTO.getCategoryId());
		entity.setCategoryName(goodsDTO.getCategoryName());
		entity.setCategoryLinkName(goodsDTO.getCategoryLinkName());
		entity.setCostPrice(goodsDTO.getCostPrice());
		entity.setSalePrice(goodsDTO.getSalePrice());
		entity.setMainImg(goodsDTO.getMainImg());
		entity.setOtherImg(goodsDTO.getOtherImg());
		entity.setDescInfo(goodsDTO.getDescInfo());
		entity.setNum(num);
		return entity;
	}

	public static OrderDetailDTO form(OrderDetailEntity entity) {
		if (entity == null) {
			return null;
		}
		OrderDetailDTO dto = new OrderDetailDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
}
