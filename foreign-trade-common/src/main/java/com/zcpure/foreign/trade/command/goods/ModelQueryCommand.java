package com.zcpure.foreign.trade.command.goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ModelQueryCommand implements Serializable {
	private static final long serialVersionUID = 6348392991146109707L;
	@ApiModelProperty(value = "品牌ID")
	private Long brandId;

	@ApiModelProperty(value = "品牌名称")
	private String brandName;

	@ApiModelProperty(value = "型号名称")
	private String name;

	@ApiModelProperty(value = "页数")
	private Integer pageNo;

	@ApiModelProperty(value = "每页数量")
	private Integer pageSize;
}
