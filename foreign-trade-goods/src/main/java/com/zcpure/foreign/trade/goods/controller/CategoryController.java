package com.zcpure.foreign.trade.goods.controller;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.CategoryAddCommand;
import com.zcpure.foreign.trade.dto.goods.CategoryDTO;
import com.zcpure.foreign.trade.dto.goods.CategoryLinkDTO;
import com.zcpure.foreign.trade.goods.dao.entity.CategoryEntity;
import com.zcpure.foreign.trade.goods.dao.repository.CategoryRepository;
import com.zcpure.foreign.trade.goods.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ethan
 * @create_time 2018/10/22 16:50
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CategoryRepository categoryRepository;

	@ApiOperation(value = "添加分类信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody CategoryAddCommand command) {
		categoryService.add(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "获取分类")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public WebJsonBean<List<CategoryDTO>> getCategory() {
		List<CategoryEntity> entityList = categoryRepository.findAll();
		List<CategoryDTO> result = CategoryDTO.build(entityList.stream().map(CategoryEntity::formDTO).collect(Collectors.toList()), 0L  );
		return WebJsonBean.SUCCESS(result);
	}

	@ApiOperation(value = "获取子分类")
	@RequestMapping(value = "/child/{id}", method = RequestMethod.GET)
	public WebJsonBean<List<CategoryDTO>> getChild(@PathVariable Long id) {
		List<CategoryEntity> entityList = categoryRepository.findByParentId(id);
		return WebJsonBean.SUCCESS(entityList.stream().map(CategoryEntity::formDTO).collect(Collectors.toList()));
	}

	@ApiOperation(value = "获取分类链")
	@RequestMapping(value = "/link/{id}", method = RequestMethod.GET)
	public WebJsonBean<CategoryLinkDTO> getLink(@PathVariable Long id) {
		CategoryLinkDTO result = categoryService.getOneLink(id);
		return WebJsonBean.SUCCESS(result);
	}

	@ApiOperation(value = "获取分类链")
	@RequestMapping(value = "/link-str/{id}", method = RequestMethod.GET)
	public WebJsonBean<String> getLinkStr(@PathVariable Long id) {
		CategoryLinkDTO category = categoryService.getOneLink(id);
		if (category == null) {
			return WebJsonBean.FAIL();
		}
		return WebJsonBean.SUCCESS(category.toLinkStr());
	}
}
