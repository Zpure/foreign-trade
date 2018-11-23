package com.zcpure.foreign.trade.order.controller;

import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.order.*;
import com.zcpure.foreign.trade.dto.order.OrderDTO;
import com.zcpure.foreign.trade.dto.order.OrderDetailDTO;
import com.zcpure.foreign.trade.dto.order.OrderDisDetailDTO;
import com.zcpure.foreign.trade.enums.OrderStatusEnum;
import com.zcpure.foreign.trade.order.service.OrderService;
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
@RequestMapping("/api/order")
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
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		command.setGroupCode(info.getGroupCode());
		return WebJsonBean.SUCCESS(orderService.queryPage(command));
	}

	@ApiOperation(value = "查询订单详情")
	@RequestMapping(value = "/detail/page", method = RequestMethod.POST)
	public WebJsonBean<PageBean<OrderDetailDTO>> page(@RequestBody OrderDetailQueryCommand command) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		command.setGroupCode(info.getGroupCode());
		return WebJsonBean.SUCCESS(orderService.queryDetailPage(command));
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
	@RequestMapping(value = "/confirm/{code}", method = RequestMethod.POST)
	public WebJsonBean<Void> confirm(@PathVariable String code) {
		orderService.updateStatus(code, OrderStatusEnum.CONFIRM);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "配货完成")
	@RequestMapping(value = "/distribution/{code}", method = RequestMethod.POST)
	public WebJsonBean<Void> distribution(@PathVariable String code) {
		orderService.updateStatus(code, OrderStatusEnum.DISTRIBUTION);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "发货订单")
	@RequestMapping(value = "/delivery/{code}", method = RequestMethod.POST)
	public WebJsonBean<Void> delivery(@PathVariable String code) {
		orderService.updateStatus(code, OrderStatusEnum.DELIVERY);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "收货订单")
	@RequestMapping(value = "/receipt/{code}", method = RequestMethod.POST)
	public WebJsonBean<Void> receipt(@PathVariable String code) {
		orderService.updateStatus(code, OrderStatusEnum.RECEIPT);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "完成订单")
	@RequestMapping(value = "/success/{code}", method = RequestMethod.POST)
	public WebJsonBean<Void> success(@PathVariable String code) {
		orderService.updateStatus(code, OrderStatusEnum.SUCCESS);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "分配订单")
	@RequestMapping(value = "/distribution", method = RequestMethod.POST)
	public WebJsonBean<Void> distribution(@RequestBody OrderDistributionCommand command) {
		orderService.distribution(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "分配详情")
	@RequestMapping(value = "/distribution/detail", method = RequestMethod.POST)
	public WebJsonBean<List<OrderDisDetailDTO>> distributionDetail(@RequestBody OrderDistributionDetailQueryCommand command) {
		return WebJsonBean.SUCCESS(orderService.distributionDetail(command));
	}

	@ApiOperation(value = "订单配货")
	@RequestMapping(value = "/distribution/update", method = RequestMethod.POST)
	public WebJsonBean<Void> distributionUpdate(@RequestBody OrderDistributionUpdateCommand command) {
		orderService.distributionUpdate(command);
		return WebJsonBean.SUCCESS();
	}
}
