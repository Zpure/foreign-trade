package com.zcpure.foreign.trade.dto.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ethan
 * @create_time 2018/10/22 15:18
 * 型号数据
 */
@Data
@ApiModel(value = "商品信息")
public class GoodsDTO implements Serializable {
	private static final long serialVersionUID = -8932488644005867171L;

	@ApiModelProperty(value = "集团编码")
	private String groupCode;
	@ApiModelProperty(value = "商品编码")
	private String code;
	@ApiModelProperty(value = "商品名称")
	private String name;
	@ApiModelProperty(value = "商品品牌")
	private Long brandId;
	@ApiModelProperty(value = "商品品牌名")
	private String brandName;
	@ApiModelProperty(value = "商品型号")
	private Long modelId;
	@ApiModelProperty(value = "商品型号名")
	private String modelName;
	@ApiModelProperty(value = "商品分类")
	private Long categoryId;
	@ApiModelProperty(value = "商品分类名")
	private String categoryName;
	@ApiModelProperty(value = "商品分类链路")
	private String categoryLinkName;
	@ApiModelProperty(value = "商品成本价")
	private BigDecimal costPrice;
	@ApiModelProperty(value = "商品售价")
	private BigDecimal salePrice;
	@ApiModelProperty(value = "商品主图")
	private String mainImg;
	@ApiModelProperty(value = "商品其他图片")
	private String otherImg;
	@ApiModelProperty(value = "商品描述")
	private String descInfo;
	@ApiModelProperty(value = "商品状态")
	private Integer status;
	@ApiModelProperty(value = "商品排序优先级")
	private Integer priority;
	@ApiModelProperty(value = "商品创建时间")
	private Date createTime;
}
