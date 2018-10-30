package com.zcpure.foreign.trade.order.controller;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.order.*;
import com.zcpure.foreign.trade.dto.order.OrderDTO;
import com.zcpure.foreign.trade.order.service.OrderService;
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
@RequestMapping("/api/order")
@CrossOrigin
@Api(value = "订单")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@ApiOperation(value = "生成订单")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody OrderAddCommand command) {
		orderService.add(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "查询订单")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public WebJsonBean<PageBean<OrderDTO>> page(@RequestBody OrderQueryCommand command) {
		return WebJsonBean.SUCCESS(orderService.queryPage(command));
	}

	@ApiOperation(value = "订单更新")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public WebJsonBean<Void> update(@RequestBody OrderUpdateCommand command) {
		orderService.update(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "订单详情")
	@RequestMapping(value = "/detail/{code}", method = RequestMethod.GET)
	public WebJsonBean<OrderDTO> page(@PathVariable String code) {
		return WebJsonBean.SUCCESS(orderService.getDetail(code));
	}

	@ApiOperation(value = "确认订单")
	@RequestMapping(value = "/confirm/{code}", method = RequestMethod.GET)
	public WebJsonBean<Void> confirm(@PathVariable String code) {
		orderService.confirm(code);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "分配订单")
	@RequestMapping(value = "/distribution", method = RequestMethod.POST)
	public WebJsonBean<Void> distribution(@PathVariable OrderDistributionCommand command) {
		orderService.distribution(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "订单配货")
	@RequestMapping(value = "/distribution/update", method = RequestMethod.POST)
	public WebJsonBean<Void> distributionUpdate(@PathVariable OrderDistributionUpdateCommand command) {
		orderService.distributionUpdate(command);
		return WebJsonBean.SUCCESS();
	}
}
