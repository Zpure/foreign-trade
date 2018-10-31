package com.zcpure.foreign.trade.command.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "管理员信息")
public class GroupAddCommand implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	@NotNull(message = "编码不能为空")
	@ApiModelProperty(value = "集团编码")
	private String code;
	@NotNull(message = "名称不能为空")
	@ApiModelProperty(value = "集团名称")
	private String name;
	@NotNull(message = "手机号不能为空")
	@ApiModelProperty(value = "集团手机号")
	private String phone;
	@ApiModelProperty(value = "集团邮箱")
	private String email;
	@ApiModelProperty(value = "集团地址")
	private String address;
	@ApiModelProperty(value = "集团备注")
	private String remark;
}
