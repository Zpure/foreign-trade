package com.zcpure.foreign.trade.order.controller;

import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import com.zcpure.foreign.trade.WebJsonBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@RestController
@RequestMapping("/api/order")
@CrossOrigin
@Api(value = "订单")
public class OrderController {

	@ApiOperation(value = "添加品牌信息")
	@RequestMapping(value = "/through-info", method = RequestMethod.GET)
	public WebJsonBean<RequestThroughInfo> throughInfo() {
		return WebJsonBean.SUCCESS(RequestThroughInfoContext.getInfo());
	}
}
