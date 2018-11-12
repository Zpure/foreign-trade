package com.zcpure.foreign.trade.order.controller;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.order.dao.entity.TestDetailEntity;
import com.zcpure.foreign.trade.order.dao.entity.TestEntity;
import com.zcpure.foreign.trade.order.dao.repository.TestRepository;
import com.zcpure.foreign.trade.utils.UniqueNoUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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

	@RequestMapping(value = "/detail/{code}", method = RequestMethod.GET)
	public WebJsonBean<String> get(@PathVariable String code) {
		TestEntity one = testRepository.findOne(code);
		return WebJsonBean.SUCCESS(one.getName());
	}


	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestParam String name) {
		TestEntity one = new TestEntity();
		one.setCode(UniqueNoUtils.next(UniqueNoUtils.UniqueNoType.GC));
		one.setName(name);
		testRepository.save(one);
		return WebJsonBean.SUCCESS();
	}

	@RequestMapping(value = "/add-and-child", method = RequestMethod.POST)
	public WebJsonBean<Void> add1(@RequestParam String name, @RequestParam String childName) {
		TestEntity one = new TestEntity();
		one.setCode(UniqueNoUtils.next(UniqueNoUtils.UniqueNoType.GC));
		one.setName(name);

		TestDetailEntity detailEntity = new TestDetailEntity();
		detailEntity.setName(childName);
		one.setDetailList(Arrays.asList(detailEntity));

		testRepository.save(one);
		return WebJsonBean.SUCCESS();
	}

	@RequestMapping(value = "/clear-child", method = RequestMethod.POST)
	public WebJsonBean<Void> clear(@RequestParam String code) {
		TestEntity one = testRepository.findOne(code);
		one.getDetailList().clear();
		testRepository.save(one);
		return WebJsonBean.SUCCESS();
	}

	@RequestMapping(value = "/clear-add-child", method = RequestMethod.POST)
	public WebJsonBean<Void> clear(@RequestParam String code, @RequestParam String childName) {
		TestEntity one = testRepository.findOne(code);
		one.getDetailList().clear();

		TestDetailEntity detailEntity = new TestDetailEntity();
		detailEntity.setName(childName);
		one.getDetailList().add(detailEntity);

		testRepository.save(one);
		return WebJsonBean.SUCCESS();
	}
}
