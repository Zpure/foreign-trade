package com.zcpure.foreign.trade.controller.goods;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.CategoryAddCommand;
import com.zcpure.foreign.trade.dto.goods.CategoryDTO;
import com.zcpure.foreign.trade.dto.goods.CategoryLinkDTO;
import com.zcpure.foreign.trade.feign.goods.CategoryFeign;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ethan
 * @create_time 2018/10/22 16:50
 */
@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {
	@Autowired
	private CategoryFeign categoryFeign;

	@ApiOperation(value = "添加分类信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody CategoryAddCommand command) {
		return categoryFeign.add(command);
	}

	@ApiOperation(value = "获取子分类")
	@RequestMapping(value = "/child/{id}", method = RequestMethod.GET)
	public WebJsonBean<List<CategoryDTO>> getChild(@PathVariable("id") Long id) {
		return categoryFeign.getChild(id);
	}

	@ApiOperation(value = "获取分类链")
	@RequestMapping(value = "/link/{id}", method = RequestMethod.GET)
	public WebJsonBean<CategoryLinkDTO> getLink(@PathVariable("id") Long id) {
		return categoryFeign.getLink(id);
	}

	@ApiOperation(value = "获取分类链")
	@RequestMapping(value = "/link-str/{id}", method = RequestMethod.GET)
	public WebJsonBean<String> getLinkStr(@PathVariable("id") Long id) {
		return categoryFeign.getLinkStr(id);
	}
}
