package com.zcpure.foreign.trade.command.goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ethan
 * @create_time 2018/10/22 15:26
 */
@Data
public class BrandAddCommand implements Serializable {
	private static final long serialVersionUID = -7328075393067899897L;
	@ApiModelProperty("品牌名称")
	private String name;
}
