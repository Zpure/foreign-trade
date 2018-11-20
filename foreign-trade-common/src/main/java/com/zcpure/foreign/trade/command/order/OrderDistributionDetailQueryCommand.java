package com.zcpure.foreign.trade.command.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDistributionDetailQueryCommand implements Serializable {
	private static final long serialVersionUID = 6348392991146109707L;

	@ApiModelProperty(value = "订单编码")
	private String orderCode;

	@ApiModelProperty(value = "商品编码")
	private String goodsCode;
}
