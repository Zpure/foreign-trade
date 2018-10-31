package com.zcpure.foreign.trade.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 供应商
 */
@Data
public class SupplierDTO implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	private String code;
	private String name;
	private String phone;
	private String email;
	private String address;
	private String country;
	private String website;
	private String company;
	private Integer priority;
	private Integer status;
	private Date createTime;
	private List<SupplierGoodsDTO> detailList;
}
