package com.zcpure.foreign.trade.command.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 客户
 */
@Data
public class CustomerUpdateCommand implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	@ApiModelProperty(hidden = true)
	private String groupCode;
	@NotNull(message = "客户编码不能为空")
	private String code;
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
	private Integer priority = 1;
}
