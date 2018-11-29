package com.zcpure.foreign.trade.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 客户
 */
@Data
public class CustomerMsgDTO implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	private Long id;
	private String name;
	private String phone;
	private String email;
	private String ip;
	private String msg;
	private Integer status;
}
