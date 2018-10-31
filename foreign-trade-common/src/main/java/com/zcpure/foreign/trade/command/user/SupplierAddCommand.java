package com.zcpure.foreign.trade.command.user;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 供应商
 */
@Data
public class SupplierAddCommand implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	@NotNull(message = "供应商编码不能为空")
	private String code;
	@NotNull(message = "供应商名称不能为空")
	private String name;
	private String phone;
	private String email;
	private String address;
	private String country;
	private String website;
	private String company;
	@NotNull(message = "级别不能为空")
	@Min(value = 1, message = "只能1到100")
	@Max(value = 100, message = "只能1到100")
	private Integer priority;
}
