package com.zcpure.foreign.trade.command.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class SupplierGoodsQueryCommand implements Serializable {
	private static final long serialVersionUID = 6348392991146109707L;
	@ApiModelProperty(value = "集团编码", hidden = true)
	private String groupCode;
	@ApiModelProperty(value = "供应商编码")
	private String supplierCode;
	@ApiModelProperty(value = "商品编码")
	private String goodsCode;
	@ApiModelProperty(value = "商品名称")
	private String goodsName;
	@ApiModelProperty(value = "品牌名称")
	private String brandName;
	@ApiModelProperty(value = "型号名称")
	private String modelName;
	@ApiModelProperty(value = "分类名称")
	private String categoryName;
	@ApiModelProperty(value = "供货状态")
	private String status;
	@ApiModelProperty(value = "页数")
	private Integer pageNo;
	@ApiModelProperty(value = "每页数量")
	private Integer pageSize;
}
