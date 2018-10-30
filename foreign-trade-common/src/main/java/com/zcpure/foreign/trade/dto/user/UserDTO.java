package com.zcpure.foreign.trade.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "管理员信息")
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	private Long id;
	@ApiModelProperty(value = "集团编码")
	private String groupCode;
	@ApiModelProperty(value = "集团名称")
	private String groupName;
	@ApiModelProperty(value = "管理员名称")
	private String name;
	@ApiModelProperty(value = "管理员手机号")
	private String phone;
	@ApiModelProperty(value = "管理员邮箱")
	private String email;
	@ApiModelProperty(value = "管理员地址")
	private String address;
	@ApiModelProperty(value = "管理员等级")
	private Integer userLevel;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
}
