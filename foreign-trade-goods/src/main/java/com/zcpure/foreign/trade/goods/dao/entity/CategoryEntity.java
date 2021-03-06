package com.zcpure.foreign.trade.goods.dao.entity;

import com.zcpure.foreign.trade.command.goods.CategoryAddCommand;
import com.zcpure.foreign.trade.dto.goods.CategoryDTO;
import com.zcpure.foreign.trade.dto.goods.CategoryLinkDTO;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Entity
@Table(name = "ft_category")
@Where(clause = "delete_flag <> 1")
@Data
public class CategoryEntity extends BaseEntity {
	private static final long serialVersionUID = 3176858011720865431L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Integer isDir;
	private Long parentId;
	private String name;
	private Integer categoryLevel;

	public static CategoryEntity fromAdd(CategoryAddCommand command, Integer categoryLevel) {
		if (command == null) {
			return null;
		}
		CategoryEntity entity = new CategoryEntity();
		entity.setIsDir(command.getIsDir());
		entity.setName(command.getName());
		entity.setParentId(command.getParentId());
		entity.setCategoryLevel(categoryLevel);
		return entity;
	}

	public static CategoryDTO formDTO(CategoryEntity entity) {
		if (entity == null) {
			return null;
		}
		CategoryDTO dto = new CategoryDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public static CategoryLinkDTO toLinkDTO(CategoryEntity entity) {
		if (entity == null) {
			return null;
		}
		CategoryLinkDTO dto = new CategoryLinkDTO();
		dto.setId(entity.getId());
		dto.setIsDir(entity.getIsDir());
		dto.setName(entity.getName());
		dto.setParentId(entity.getParentId());
		dto.setCategoryLevel(entity.getCategoryLevel());
		return dto;
	}

}
