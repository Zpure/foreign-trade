package com.zcpure.foreign.trade.command.goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ethan
 * @create_time 2018/10/22 15:26
 */
@Data
public class CategoryAddCommand implements Serializable {
	private static final long serialVersionUID = -7328075393067899897L;

	@ApiModelProperty("父分类")
	private Long parentId;

	@ApiModelProperty("分类名")
	private String name;

	@ApiModelProperty("是否是目录 0：不是，1：是")
	private Integer isDir;
}
