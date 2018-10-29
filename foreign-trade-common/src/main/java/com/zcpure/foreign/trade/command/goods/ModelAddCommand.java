package com.zcpure.foreign.trade.command.goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ethan
 * @create_time 2018/10/22 15:26
 */
@Data
public class ModelAddCommand implements Serializable {
	private static final long serialVersionUID = -7328075393067899897L;

	@ApiModelProperty("品牌ID")
	private Long brandId;

	@ApiModelProperty("型号名称")
	private String name;

	@ApiModelProperty("型号别名，多个别名以英文逗号隔开")
	private String alias;

	@ApiModelProperty("主图")
	private String mainImg;

	@ApiModelProperty("其他图片")
	private String otherImg;
}
