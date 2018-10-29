package com.zcpure.foreign.trade.goods.dao.entity;

import com.zcpure.foreign.trade.enums.GoodsStatusEnum;
import com.zcpure.foreign.trade.command.goods.GoodsAddCommand;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author ethan
 * @create_time 2018/10/22 15:18
 * 型号数据
 */
@Table(name = "ft_goods")
@Where(clause = "delete_flag <> 1")
@Entity
@Data
public class GoodsEntity extends BaseEntity {
	@Id
	@Column(length = 64, unique = true, updatable = false)
	private String code;
	private String groupCode;
	private String name;
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
	private Integer status;
	private String priority;

	public static GoodsEntity form(String goodsCode, GoodsAddCommand command,
	                               ModelEntity modelEntity,
	                               CategoryEntity categoryEntity, String categoryLinkName) {
		GoodsEntity goodsEntity = new GoodsEntity();
		goodsEntity.setCode(goodsCode);
		goodsEntity.setName(command.getName());
		goodsEntity.setBrandId(modelEntity.getBrandId());
		goodsEntity.setBrandName(modelEntity.getBrandName());
		goodsEntity.setModelId(modelEntity.getId());
		goodsEntity.setModelName(modelEntity.getName());
		goodsEntity.setCategoryId(categoryEntity.getId());
		goodsEntity.setCategoryName(categoryEntity.getName());
		goodsEntity.setCategoryLinkName(categoryLinkName);
		goodsEntity.setCostPrice(command.getCostPrice());
		goodsEntity.setSalePrice(command.getSalePrice());
		goodsEntity.setMainImg(command.getMainImg());
		goodsEntity.setOtherImg(command.getOtherImg());
		goodsEntity.setDescInfo(command.getDescInfo());
		goodsEntity.setStatus(GoodsStatusEnum.OFF_SALE.getCode());
		goodsEntity.setPriority(command.getPriority());
		return goodsEntity;
	}

	public static GoodsDTO formDTO(GoodsEntity entity) {
		if(entity == null) {
			return null;
		}
		GoodsDTO dto = new GoodsDTO();
		dto.setGroupCode(entity.getGroupCode());
		dto.setCode(entity.getCode());
		dto.setName(entity.getName());
		dto.setBrandId(entity.getBrandId());
		dto.setBrandName(entity.getBrandName());
		dto.setModelId(entity.getModelId());
		dto.setModelName(entity.getModelName());
		dto.setCategoryId(entity.getCategoryId());
		dto.setCategoryName(entity.getCategoryName());
		dto.setCategoryLinkName(entity.getCategoryLinkName());
		dto.setCostPrice(entity.getCostPrice());
		dto.setSalePrice(entity.getSalePrice());
		dto.setMainImg(entity.getMainImg());
		dto.setOtherImg(entity.getOtherImg());
		dto.setDescInfo(entity.getDescInfo());
		dto.setStatus(entity.getStatus());
		dto.setPriority(entity.getPriority());
		dto.setCreateTime(entity.getCreateTime());
		return dto;
	}
}
