package com.zcpure.foreign.trade.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 供应商供应列表
 */
@Data
public class SupplierGoodsDTO implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	private Long id;
	private String supplierCode;
	private String supplierName;
	private String goodsCode;
	private String goodsName;
	private String groupCode;
	private String groupName;
	private Long brandId;
	private String brandName;
	private Long modelId;
	private String modelName;
	private Long categoryId;
	private String categoryName;
	private String categoryLinkName;
	private String mainImg;
	private String otherImg;
	private String descInfo;
	private BigDecimal price;
	private Integer status;
}
