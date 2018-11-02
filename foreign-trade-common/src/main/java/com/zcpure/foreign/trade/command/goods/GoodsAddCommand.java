package com.zcpure.foreign.trade.command.goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ethan
 * @create_time 2018/10/22 15:18
 * 型号数据
 */
@Data
public class GoodsAddCommand implements Serializable {
	private static final long serialVersionUID = -7328075393067899897L;

	@ApiModelProperty("商品名称")
	private String name;

	@ApiModelProperty("型号ID")
	private Long modelId;

	@ApiModelProperty("分类ID")
	private Long categoryId;

	@ApiModelProperty("成本价")
	private BigDecimal costPrice;

	@ApiModelProperty("售价")
	private BigDecimal salePrice;

	@ApiModelProperty("主图")
	private String mainImg;

	@ApiModelProperty("其他图片")
	private String otherImg;

	@ApiModelProperty("商品描述")
	private String descInfo;

	@ApiModelProperty("权重")
	private Integer priority;
}
