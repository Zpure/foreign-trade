package com.zcpure.foreign.trade.order.dao.entity;

import com.zcpure.foreign.trade.dto.order.OrderDisDetailDTO;
import com.zcpure.foreign.trade.dto.user.SupplierDTO;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "ft_order_detail_dis", uniqueConstraints = {@UniqueConstraint(columnNames = {"orderCode", "goodsCode", "supplierCode"})})
@Where(clause = "delete_flag <> 1")
@Entity
@Data
public class OrderDisDetailEntity extends BaseEntity {
	private static final long serialVersionUID = 1111320370190733556L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long detailId;
	private String orderCode;
	private String groupCode;
	private String groupName;
	private String goodsCode;
	private String supplierCode;
	private String supplierName;
	private BigDecimal costPrice;
	private BigDecimal salePrice;
	private BigDecimal price;
	private Integer totalNum;
	private Integer initDisNum;
	private Integer disNum;

	public static OrderDisDetailEntity form(OrderDetailEntity detailEntity, SupplierDTO supplier, Integer num) {
		if (detailEntity == null) {
			return null;
		}
		OrderDisDetailEntity disDetailEntity = new OrderDisDetailEntity();
		disDetailEntity.setDetailId(detailEntity.getId());
		disDetailEntity.setOrderCode(detailEntity.getOrderCode());
		disDetailEntity.setGroupCode(detailEntity.getGroupCode());
		disDetailEntity.setGroupName(detailEntity.getGoodsName());
		disDetailEntity.setGoodsCode(detailEntity.getGoodsCode());
		disDetailEntity.setCostPrice(detailEntity.getCostPrice());
		disDetailEntity.setSalePrice(detailEntity.getSalePrice());
		disDetailEntity.setPrice(detailEntity.getPrice());
		disDetailEntity.setSupplierCode(supplier.getCode());
		disDetailEntity.setSupplierName(supplier.getName());
		disDetailEntity.setTotalNum(detailEntity.getNum());
		disDetailEntity.setInitDisNum(num);
		disDetailEntity.setDisNum(0);
		return disDetailEntity;
	}

	public static OrderDisDetailDTO form(OrderDisDetailEntity entity) {
		if (entity == null) {
			return null;
		}
		OrderDisDetailDTO dto = new OrderDisDetailDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
}
