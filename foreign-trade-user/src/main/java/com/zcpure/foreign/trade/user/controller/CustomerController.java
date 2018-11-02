package com.zcpure.foreign.trade.user.controller;

import com.github.pagehelper.PageHelper;
import com.zcpure.foreign.trade.*;
import com.zcpure.foreign.trade.command.user.*;
import com.zcpure.foreign.trade.dto.user.CustomerDTO;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import com.zcpure.foreign.trade.user.dao.entity.CustomerEntity;
import com.zcpure.foreign.trade.user.dao.entity.UserEntity;
import com.zcpure.foreign.trade.user.dao.mapper.CustomerMapper;
import com.zcpure.foreign.trade.user.dao.mapper.UserMapper;
import com.zcpure.foreign.trade.user.dao.repostitory.CustomerRepository;
import com.zcpure.foreign.trade.user.dao.repostitory.UserRepository;
import com.zcpure.foreign.trade.user.service.CustomerService;
import com.zcpure.foreign.trade.user.service.UserService;
import com.zcpure.foreign.trade.user.utils.page.PageBeanAssembler;
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
@RequestMapping("/api/customer")
@Api(value = "客户")
public class CustomerController {
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerService customerService;

	@ApiOperation(value = "添加客户信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody CustomerAddCommand command) {
		customerService.add(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "获取客户信息")
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public WebJsonBean<CustomerDTO> getByCode(@PathVariable String code) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		CustomerEntity customerEntity = customerRepository.findOne(code);
		if(customerEntity == null || !customerEntity.getGroupCode().equals(info.getGroupCode())) {
			return new WebJsonBean<>(BaseCode.FAIL);
		}
		return WebJsonBean.SUCCESS(CustomerEntity.formDTO(customerEntity));
	}

	@ApiOperation(value = "更新客户信息")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public WebJsonBean<Void> update(@RequestBody CustomerUpdateCommand command) {
		customerService.update(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "获取客户列表信息")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public WebJsonBean<PageBean<CustomerDTO>> queryByPage(@RequestBody CustomerQueryCommand command) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		command.setGroupCode(info.getGroupCode());
		PageHelper.startPage(command.getPageNo() != null ? command.getPageNo() : Const.PAGE_DEFAULT_NO,
			command.getPageSize() != null ? command.getPageSize() : Const.PAGE_DEFAULT_SIZE);
		List<CustomerDTO> result = customerMapper.queryPage(command);
		return WebJsonBean.SUCCESS(new PageBeanAssembler().toBeanByList(result));
	}

}
