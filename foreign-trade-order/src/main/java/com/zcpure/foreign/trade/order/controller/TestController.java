package com.zcpure.foreign.trade.order.controller;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.order.dao.entity.TestEntity;
import com.zcpure.foreign.trade.order.dao.repository.TestRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@RestController
@RequestMapping("/api/order/test")
@Api(value = "订单")
public class TestController {

	@Autowired
	private TestRepository testRepository;

	@ApiOperation(value = "订单详情")
	@RequestMapping(value = "/detail/{code}", method = RequestMethod.GET)
	public WebJsonBean<TestEntity> page(@PathVariable String code) {
		TestEntity one = testRepository.findOne(code);
		return WebJsonBean.SUCCESS(one);
	}
}
