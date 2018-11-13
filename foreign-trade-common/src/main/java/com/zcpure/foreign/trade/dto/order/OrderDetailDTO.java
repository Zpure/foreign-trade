package com.zcpure.foreign.trade.dto.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("订单详情")
public class OrderDetailDTO implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	@ApiModelProperty("详情ID")
	private Long id;
	@ApiModelProperty("订单编码")
	private String orderCode;
	@ApiModelProperty("所属集团")
	private String groupCode;
	@ApiModelProperty("商品编码")
	private String goodsCode;
	@ApiModelProperty("商品名称")
	private String goodsName;
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
	@ApiModelProperty(value = "商品下单价")
	private BigDecimal price;
	@ApiModelProperty(value = "商品主图")
	private String mainImg;
	@ApiModelProperty(value = "商品其他图片")
	private String otherImg;
	@ApiModelProperty(value = "商品描述")
	private String descInfo;
	@ApiModelProperty("购买数量")
	private Integer num;
	@ApiModelProperty("分配数量")
	private Integer initDisNum;
	@ApiModelProperty("配货数量")
	private Integer disNum;
	@ApiModelProperty("创建时间")
	private Date createTime;
}
