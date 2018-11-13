package com.zcpure.foreign.trade.command.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderAddDetailCommand implements Serializable {
	private static final long serialVersionUID = 6348392991146109707L;

	@ApiModelProperty(value = "商品编码")
	private String goodsCode;

	@ApiModelProperty(value = "商品购买数量")
	private Integer buyNum;

	@ApiModelProperty(value = "商品购买数量")
	private BigDecimal price;
}
