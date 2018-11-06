package com.zcpure.foreign.trade.dto.goods;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class CategoryDTO implements Serializable {
	private static final long serialVersionUID = 3176858011720865431L;

	private Long id;
	private Integer isDir;
	private Long parentId;
	private String name;
	private Integer categoryLevel;
	private List<CategoryDTO> childList;

	public static List<CategoryDTO> build(List<CategoryDTO> list, Long categoryId) {
		List<CategoryDTO> parentList = new ArrayList<>();
		if(list != null && list.size() > 0) {
			Map<Long, List<CategoryDTO>> levelMap = list.stream().collect(Collectors.groupingBy(CategoryDTO::getParentId));
			for (CategoryDTO item : levelMap.getOrDefault(categoryId, new ArrayList<>())) {
				parentList.add(findChildren(item, levelMap));
			}
		}
		return parentList;
	}

	private static CategoryDTO findChildren(CategoryDTO categoryNode, Map<Long, List<CategoryDTO>> categoryMap) {
		for (CategoryDTO item : categoryMap.getOrDefault(categoryNode.getId(), new ArrayList<>())) {
			if (categoryNode.getChildList() == null) {
				categoryNode.setChildList(new ArrayList<>());
			}
			categoryNode.getChildList().add(findChildren(item, categoryMap));
		}
		return categoryNode;
	}

}
