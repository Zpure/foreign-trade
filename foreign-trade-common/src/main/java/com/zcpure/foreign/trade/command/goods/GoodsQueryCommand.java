package com.zcpure.foreign.trade.command.goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsQueryCommand implements Serializable {
	private static final long serialVersionUID = 6348392991146109707L;
	@ApiModelProperty(value = "集团编码", hidden = true)
	private String groupCode;

	@ApiModelProperty(value = "商品编码")
	private String code;

	@ApiModelProperty(value = "商品名称")
	private String name;

	@ApiModelProperty(value = "品牌名称")
	private String brandName;

	@ApiModelProperty(value = "型号名称")
	private String modelName;

	@ApiModelProperty(value = "商品状态")
	private Integer status;

	@ApiModelProperty(value = "页数")
	private Integer pageNo;

	@ApiModelProperty(value = "每页数量")
	private Integer pageSize;
}
