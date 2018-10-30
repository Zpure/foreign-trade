package com.zcpure.foreign.trade.goods.controller;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.CategoryAddCommand;
import com.zcpure.foreign.trade.dto.goods.CategoryLinkDTO;
import com.zcpure.foreign.trade.goods.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ethan
 * @create_time 2018/10/22 16:50
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@ApiOperation(value = "添加分类信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody CategoryAddCommand command) {
		categoryService.add(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "获取分类链")
	@RequestMapping(value = "/link/{id}")
	public WebJsonBean<CategoryLinkDTO> getLink(@PathVariable Long id) {
		CategoryLinkDTO result = categoryService.getOneLink(id);
		return WebJsonBean.SUCCESS(result);
	}

	@ApiOperation(value = "获取分类链")
	@RequestMapping(value = "/link-str/{id}")
	public WebJsonBean<String> getLinkStr(@PathVariable Long id) {
		CategoryLinkDTO category = categoryService.getOneLink(id);
		if (category == null) {
			return null;
		}
		return WebJsonBean.SUCCESS(category.toLinkStr());
	}
}
