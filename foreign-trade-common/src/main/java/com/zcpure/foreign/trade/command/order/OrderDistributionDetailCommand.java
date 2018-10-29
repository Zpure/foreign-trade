package com.zcpure.foreign.trade.command.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDistributionDetailCommand implements Serializable {
	private static final long serialVersionUID = 6348392991146109707L;

	@ApiModelProperty(value = "商品编码")
	private String goodsCode;

	@ApiModelProperty(value = "供货人")
	private String name;

	@ApiModelProperty(value = "配货数量")
	private Integer num;
}
