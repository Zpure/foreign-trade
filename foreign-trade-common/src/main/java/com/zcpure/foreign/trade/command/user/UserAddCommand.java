package com.zcpure.foreign.trade.command.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "管理员信息")
public class UserAddCommand implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	@ApiModelProperty(value = "集团编码", hidden = true)
	private String groupCode;
	@NotNull(message = "名称不能为空")
	@ApiModelProperty(value = "管理员名称")
	private String name;
	@NotNull(message = "手机号不能为空")
	@ApiModelProperty(value = "管理员手机号")
	private String phone;
	@NotNull(message = "密码不能为空")
	@ApiModelProperty(value = "密码")
	private String password;
	@ApiModelProperty(value = "管理员邮箱")
	private String email;
	@ApiModelProperty(value = "管理员地址")
	private String address;
}
