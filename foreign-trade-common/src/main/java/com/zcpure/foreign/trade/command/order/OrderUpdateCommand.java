package com.zcpure.foreign.trade.command.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderUpdateCommand implements Serializable {
	private static final long serialVersionUID = 6348392991146109707L;

	@ApiModelProperty(value = "订单编码")
	private String code;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "下单商品详情")
	private List<OrderAddDetailCommand> detailList;
}
