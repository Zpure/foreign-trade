package com.zcpure.foreign.trade.command.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerMsgQueryCommand implements Serializable {
	private static final long serialVersionUID = 6348392991146109707L;
	@ApiModelProperty(value = "集团编码", hidden = true)
	private String groupCode;
	@ApiModelProperty(value = "用户名称")
	private String name;
	@ApiModelProperty(value = "用户手机号")
	private String phone;
	@ApiModelProperty(value = "邮箱")
	private String email;
	@ApiModelProperty(value = "状态")
	private Integer status;
	@ApiModelProperty(value = "页数")
	private Integer pageNo;
	@ApiModelProperty(value = "每页数量")
	private Integer pageSize;
}
