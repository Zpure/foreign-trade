package com.zcpure.foreign.trade.goods.controller;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.GoodsAddCommand;
import com.zcpure.foreign.trade.command.goods.GoodsQueryCommand;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import com.zcpure.foreign.trade.goods.service.GoodsService;
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
@RequestMapping("/api/goods")
@Api(value = "商品")
public class GoodsController {
	@Autowired
	private GoodsService goodsService;

	@ApiOperation(value = "添加商品信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody GoodsAddCommand command) {
		goodsService.add(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "获取品牌信息")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public WebJsonBean<PageBean<GoodsDTO>> queryByPage(@RequestBody GoodsQueryCommand command) {
		PageBean<GoodsDTO> result = goodsService.queryPage(command);
		return WebJsonBean.SUCCESS(result);
	}

	@ApiOperation(value = "获取商品信息")
	@RequestMapping(value = "/{code}")
	public WebJsonBean<GoodsDTO> getByCode(@PathVariable String code) {
		GoodsDTO result = goodsService.getByCode(code);
		return WebJsonBean.SUCCESS(result);
	}
}
