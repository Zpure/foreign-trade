package com.zcpure.foreign.trade.dto.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "单链分类")
public class CategoryLinkDTO implements Serializable {
	private static final long serialVersionUID = 3176858011720865431L;

	@ApiModelProperty(value = "ID")
	private Long id;

	@ApiModelProperty(value = "是否是目录")
	private Integer isDir;

	@ApiModelProperty(value = "父分类ID")
	private Long parentId;

	@ApiModelProperty(value = "分类名")
	private String name;

	@ApiModelProperty(value = "子分类")
	private CategoryLinkDTO child;

	public String toLinkStr() {
		return toLinkStr(this.child, this.name);
	}

	public static String toLinkStr(CategoryLinkDTO dto, String str) {
		if (dto == null) {
			return str;
		}
		return toLinkStr(dto.getChild(), str + ";" + dto.getName());
	}
}
