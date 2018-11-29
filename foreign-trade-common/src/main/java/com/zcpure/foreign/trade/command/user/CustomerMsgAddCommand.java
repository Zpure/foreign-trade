package com.zcpure.foreign.trade.command.user;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 客户
 */
@Data
public class CustomerMsgAddCommand implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	@NotNull(message = "客户名称不能为空")
	private String name;
	private String phone;
	@NotNull(message = "客户邮箱不能为空")
	private String email;
	private String ip;
	private String msg;
	private String groupCode;
}
