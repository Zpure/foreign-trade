package com.zcpure.foreign.trade.goods.service.impl;

import com.zcpure.foreign.trade.command.goods.CategoryAddCommand;
import com.zcpure.foreign.trade.dto.goods.CategoryLinkDTO;
import com.zcpure.foreign.trade.goods.dao.entity.CategoryEntity;
import com.zcpure.foreign.trade.goods.dao.repository.CategoryRepository;
import com.zcpure.foreign.trade.goods.service.CategoryService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ethan
 * @create_time 2018/10/22 16:13
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void add(CategoryAddCommand command) {
		Integer categoryLevel = 1;
		if (command.getParentId() > 0) {
			CategoryEntity parentCategory = categoryRepository.findOne(command.getParentId());
			Validate.notNull(parentCategory,
				"父分类不存在：" + command.getParentId());
			Validate.isTrue(parentCategory.getIsDir() == 1,
				"父分类不是目录：" + parentCategory.getName());
			Validate.isTrue(!categoryRepository.existsByParentIdAndName(command.getParentId(), command.getName()),
				"改分类已存在");
			categoryLevel = parentCategory.getCategoryLevel() + 1;
		}

		CategoryEntity categoryEntity = CategoryEntity.fromAdd(command, categoryLevel);
		categoryRepository.save(categoryEntity);

	}

	@Override
	public CategoryLinkDTO getOneLink(Long cid) {
		CategoryEntity categoryEntity = categoryRepository.findOne(cid);
		return getOneLink(CategoryEntity.toLinkDTO(categoryEntity), null);
	}

	private CategoryLinkDTO getOneLink(CategoryLinkDTO category, CategoryLinkDTO childCategory) {
		if (category == null) {
			return childCategory;
		}
		category.setChild(childCategory);
		CategoryEntity parentCategory = categoryRepository.findOne(category.getParentId());
		return getOneLink(CategoryEntity.toLinkDTO(parentCategory), category);
	}
}
