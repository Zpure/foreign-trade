package com.zcpure.foreign.trade.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户
 */
@Data
public class CustomerDTO implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	private String groupCode;
	private String groupName;
	private String code;
	private String name;
	private String phone;
	private String email;
	private String address;
	private String country;
	private String website;
	private String company;
	private Integer priority;
	private Date createTime;
}
