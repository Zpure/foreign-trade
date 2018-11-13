package com.zcpure.foreign.trade.command.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 供应商
 */
@Data
public class SupplierGoodsDetailAddCommand implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	@NotNull(message = "商品编码不能为空")
	private String goodsCode;
	@NotNull(message = "供应商价格不能为空")
	private BigDecimal price;
}
