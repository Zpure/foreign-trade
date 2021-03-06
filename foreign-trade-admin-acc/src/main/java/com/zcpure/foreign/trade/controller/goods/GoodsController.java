package com.zcpure.foreign.trade.controller.goods;

import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.GoodsAddCommand;
import com.zcpure.foreign.trade.command.goods.GoodsQueryCommand;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import com.zcpure.foreign.trade.feign.goods.GoodsFeign;
import com.zcpure.foreign.trade.utils.page.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@RestController
@RequestMapping("/api/admin/goods")
@Api(value = "商品")
public class GoodsController {
	@Autowired
	private GoodsFeign goodsFeign;

	@ApiOperation(value = "添加商品信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody GoodsAddCommand command) {
		return goodsFeign.add(command);
	}

	@ApiOperation(value = "获取商品信息")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public WebJsonBean<PageBean<GoodsDTO>> queryByPage(GoodsQueryCommand command) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		command.setGroupCode(info.getGroupCode());
		return goodsFeign.queryByPage(command);
	}

	@ApiOperation(value = "获取商品信息")
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public WebJsonBean<GoodsDTO> getByCode(@PathVariable("code") String code) {
		return goodsFeign.getByCode(code);
	}

	@ApiOperation(value = "批量获取商品信息")
	@RequestMapping(value = "/batch-code", method = RequestMethod.GET)
	public WebJsonBean<List<GoodsDTO>> batchByCode(@RequestParam("codes") String codes) {
		return goodsFeign.batchByCode(codes);
	}

	@ApiOperation(value = "上架商品")
	@RequestMapping(value = "/on-sale/{code}", method = RequestMethod.POST)
	public WebJsonBean<Void> onSale(@PathVariable("code") String code) {
		return goodsFeign.onSale(code);
	}

	@ApiOperation(value = "下架商品")
	@RequestMapping(value = "/off-sale/{code}", method = RequestMethod.POST)
	public WebJsonBean<Void> offSale(@PathVariable("code") String code) {
		return goodsFeign.offSale(code);
	}
}
