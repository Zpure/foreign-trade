package com.zcpure.foreign.trade.command.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginCommand implements Serializable {
	private static final long serialVersionUID = 6348392991146109707L;
	@ApiModelProperty(value = "用户名")
	private String username;
	@ApiModelProperty(value = "密码")
	private Integer password;

}
