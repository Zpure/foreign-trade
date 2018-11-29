package com.zcpure.foreign.trade.goods.controller;

import com.zcpure.foreign.trade.BaseCode;
import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.GoodsAddCommand;
import com.zcpure.foreign.trade.command.goods.GoodsQueryCommand;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import com.zcpure.foreign.trade.enums.GoodsStatusEnum;
import com.zcpure.foreign.trade.goods.dao.entity.GoodsEntity;
import com.zcpure.foreign.trade.goods.dao.repository.GoodsRepository;
import com.zcpure.foreign.trade.goods.service.GoodsService;
import com.zcpure.foreign.trade.utils.page.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

	@Autowired
	private GoodsRepository goodsRepository;

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
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		GoodsDTO result = goodsService.getByCode(code);
		if (result == null || !result.getGroupCode().equals(info.getGroupCode())) {
			return new WebJsonBean<>(BaseCode.FAIL);
		}
		return WebJsonBean.SUCCESS(result);
	}

	@ApiOperation(value = "批量获取商品信息")
	@RequestMapping(value = "/batch-code")
	public WebJsonBean<List<GoodsDTO>> batchByCode(@RequestParam("codes") String codes) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		List<String> codeList = Arrays.asList(codes.split(","));
		List<GoodsEntity> entityList = goodsRepository.findByCodeIn(codeList);
		if (entityList == null || entityList.stream()
			.filter(item -> !item.getGroupCode().equals(info.getGroupCode()))
			.findFirst()
			.isPresent()) {
			return new WebJsonBean<>(BaseCode.FAIL);
		}
		List<GoodsDTO> result = entityList.stream().map(GoodsEntity::formDTO).collect(Collectors.toList());
		return WebJsonBean.SUCCESS(result);
	}

	@ApiOperation(value = "上架商品信息")
	@RequestMapping(value = "/on-sale/{code}")
	public WebJsonBean<Void> onSale(@PathVariable String code) {
		goodsService.updateStatus(code, GoodsStatusEnum.ON_SALE);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "下架商品信息")
	@RequestMapping(value = "/off-sale/{code}")
	public WebJsonBean<Void> offSale(@PathVariable String code) {
		goodsService.updateStatus(code, GoodsStatusEnum.OFF_SALE);
		return WebJsonBean.SUCCESS();
	}
}
