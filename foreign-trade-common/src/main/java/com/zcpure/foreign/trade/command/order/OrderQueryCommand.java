package com.zcpure.foreign.trade.command.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderQueryCommand implements Serializable {
	private static final long serialVersionUID = 6348392991146109707L;

	@ApiModelProperty(value = "集团编码", hidden = true)
	private String groupCode;

	@ApiModelProperty(value = "订单编码")
	private String code;

	@ApiModelProperty(value = "订单名称名称")
	private String name;

	@ApiModelProperty(value = "订单状态")
	private Integer status;

	@ApiModelProperty(value = "页数")
	private Integer pageNo;

	@ApiModelProperty(value = "每页数量")
	private Integer pageSize;
}
