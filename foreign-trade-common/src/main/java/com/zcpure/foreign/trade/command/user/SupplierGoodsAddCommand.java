package com.zcpure.foreign.trade.command.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 供应商
 */
@Data
public class SupplierGoodsAddCommand implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	@NotNull(message = "供应商编码不能为空")
	private String supplierCode;
	@NotNull(message = "供应商商品详情")
	private List<SupplierGoodsDetailAddCommand> detailList;
}
