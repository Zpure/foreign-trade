package com.zcpure.foreign.trade.controller.order;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.order.*;
import com.zcpure.foreign.trade.dto.order.OrderDTO;
import com.zcpure.foreign.trade.dto.order.OrderDetailDTO;
import com.zcpure.foreign.trade.dto.order.OrderDisDetailDTO;
import com.zcpure.foreign.trade.feign.order.OrderFeign;
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
@RequestMapping("/api/admin/order")
@Api(value = "订单")
public class OrderController {
	@Autowired
	private OrderFeign orderFeign;

	@ApiOperation(value = "生成订单")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody OrderAddCommand command) {
		return orderFeign.add(command);
	}

	@ApiOperation(value = "查询订单")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public WebJsonBean<PageBean<OrderDTO>> page(OrderQueryCommand command) {
		return orderFeign.page(command);
	}

	@ApiOperation(value = "查询订单")
	@RequestMapping(value = "/detail/page", method = RequestMethod.GET)
	public WebJsonBean<PageBean<OrderDetailDTO>> pageDetail(OrderDetailQueryCommand command) {
		return orderFeign.pageDetail(command);
	}

	@ApiOperation(value = "订单更新")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public WebJsonBean<Void> update(@RequestBody OrderUpdateCommand command) {
		return orderFeign.update(command);
	}

	@ApiOperation(value = "订单详情")
	@RequestMapping(value = "/detail/{code}", method = RequestMethod.GET)
	public WebJsonBean<OrderDTO> page(@PathVariable String code) {
		return orderFeign.detail(code);
	}

	@ApiOperation(value = "确认订单")
	@RequestMapping(value = "/confirm/{code}", method = RequestMethod.GET)
	public WebJsonBean<Void> confirm(@PathVariable String code) {
		return orderFeign.confirm(code);
	}

	@ApiOperation(value = "分配订单")
	@RequestMapping(value = "/distribution", method = RequestMethod.POST)
	public WebJsonBean<Void> distribution(@RequestBody OrderDistributionCommand command) {
		return orderFeign.distribution(command);
	}

	@ApiOperation(value = "分配详情")
	@RequestMapping(value = "/distribution/detail", method = RequestMethod.POST)
	public WebJsonBean<List<OrderDisDetailDTO>> distributionDetail(@RequestBody OrderDistributionDetailQueryCommand command) {
		return orderFeign.distributionDetail(command);
	}

	@ApiOperation(value = "订单配货")
	@RequestMapping(value = "/distribution/update", method = RequestMethod.POST)
	public WebJsonBean<Void> distributionUpdate(@RequestBody OrderDistributionUpdateCommand command) {
		return orderFeign.distributionUpdate(command);
	}
}
