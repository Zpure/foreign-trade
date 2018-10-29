package com.zcpure.foreign.trade.command.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderDistributionCommand implements Serializable {
	private static final long serialVersionUID = 6348392991146109707L;

	@ApiModelProperty(value = "订单编码")
	private String code;

	@ApiModelProperty(value = "配货详情")
	private List<OrderDistributionDetailCommand> detailList;
}
