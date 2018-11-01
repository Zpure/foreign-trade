package com.zcpure.foreign.trade.dto.goods;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {
	private static final long serialVersionUID = 3176858011720865431L;

	private Long id;
	private Integer isDir;
	private Long parentId;
	private String name;

}
