package com.zcpure.foreign.trade.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderDisDetailDTO implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;
	private Long id;
	private Long detailId;
	private String orderCode;
	private String groupCode;
	private String groupName;
	private String goodsCode;
	private String supplierCode;
	private String supplierName;
	private BigDecimal costPrice;
	private BigDecimal salePrice;
	private BigDecimal price;
	private Integer totalNum;
	private Integer initDisNum;
	private Integer disNum;
}
