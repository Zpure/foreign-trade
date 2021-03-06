package com.zcpure.foreign.trade.command.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDistributionDetailQueryCommand implements Serializable {
	private static final long serialVersionUID = 6348392991146109707L;

	@ApiModelProperty(value = "集团编码")
	private String groupCode;

	@ApiModelProperty(value = "订单编码")
	private String orderCode;

	@ApiModelProperty(value = "商品编码")
	private String goodsCode;

	@ApiModelProperty(value = "商品名称")
	private String goodsName;

	@ApiModelProperty(value = "品牌名称")
	private String brandName;

	@ApiModelProperty(value = "型号名称")
	private String modelName;

	@ApiModelProperty(value = "供应商编码")
	private String supplierCode;

	@ApiModelProperty(value = "供应商名称")
	private String supplierName;

	@ApiModelProperty(value = "页数")
	private Integer pageNo;

	@ApiModelProperty(value = "每页数量")
	private Integer pageSize;
}
