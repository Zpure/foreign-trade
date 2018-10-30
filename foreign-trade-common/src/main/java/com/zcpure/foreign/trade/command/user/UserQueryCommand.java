package com.zcpure.foreign.trade.command.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserQueryCommand implements Serializable {
	private static final long serialVersionUID = 6348392991146109707L;
	@ApiModelProperty(value = "集团编码", hidden = true)
	private String groupCode;
	@ApiModelProperty(value = "用户名称")
	private String name;
	@ApiModelProperty(value = "用户手机号")
	private String phone;
	@ApiModelProperty(value = "用户ID集合")
	private Set<Long> ids;
	@ApiModelProperty(value = "用户等级集合")
	private Set<Integer> levels;
	@ApiModelProperty(value = "页数")
	private Integer pageNo;
	@ApiModelProperty(value = "每页数量")
	private Integer pageSize;
}
