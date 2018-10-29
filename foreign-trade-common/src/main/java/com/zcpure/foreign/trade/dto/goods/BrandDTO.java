package com.zcpure.foreign.trade.dto.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 品牌
 *
 * @author obama
 */
@Data
@ApiModel(value = "品牌信息")
public class BrandDTO implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	private Long id;
	@ApiModelProperty(value = "品牌名称")
	private String name;
	private Date createTime;
	private Date modifyTime;
}
