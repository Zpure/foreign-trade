package com.zcpure.foreign.trade.goods.controller;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.BrandAddCommand;
import com.zcpure.foreign.trade.command.goods.BrandQueryCommand;
import com.zcpure.foreign.trade.dto.goods.BrandDTO;
import com.zcpure.foreign.trade.goods.service.BrandService;
import com.zcpure.foreign.trade.utils.page.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@RestController
@RequestMapping("/api/brand")
@CrossOrigin
@Api(value = "品牌")
public class BrandController {
	@Autowired
	private BrandService brandService;

	@ApiOperation(value = "添加品牌信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody BrandAddCommand command) {
		brandService.add(command);
		return WebJsonBean.SUCCESS();
	}


	@ApiOperation(value = "获取品牌信息")
	@RequestMapping(value = "/page")
	public WebJsonBean<PageBean<BrandDTO>> queryByPage(@RequestBody BrandQueryCommand command) {
		PageBean<BrandDTO> result = brandService.queryPage(command);
		return WebJsonBean.SUCCESS(result);
	}

	@ApiOperation(value = "获取品牌信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public WebJsonBean<BrandDTO> getById(@PathVariable("id") Long id) {
		BrandDTO result = brandService.getById(id);
		return WebJsonBean.SUCCESS(result);
	}
}
