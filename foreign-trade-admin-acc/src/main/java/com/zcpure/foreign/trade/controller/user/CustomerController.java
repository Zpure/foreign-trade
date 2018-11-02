package com.zcpure.foreign.trade.controller.user;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.CustomerAddCommand;
import com.zcpure.foreign.trade.command.user.CustomerQueryCommand;
import com.zcpure.foreign.trade.command.user.CustomerUpdateCommand;
import com.zcpure.foreign.trade.dto.user.CustomerDTO;
import com.zcpure.foreign.trade.feign.user.CustomerFeign;
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
@RequestMapping("/api/admin/customer")
@Api(value = "客户")
public class CustomerController {
	@Autowired
	private CustomerFeign customerFeign;

	@ApiOperation(value = "添加客户信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody CustomerAddCommand command) {
		return customerFeign.add(command);
	}

	@ApiOperation(value = "获取客户信息")
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public WebJsonBean<CustomerDTO> getByCode(@PathVariable String code) {
		return customerFeign.getByCode(code);
	}

	@ApiOperation(value = "更新客户信息")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public WebJsonBean<Void> update(@RequestBody CustomerUpdateCommand command) {
		return customerFeign.update(command);
	}

	@ApiOperation(value = "获取客户列表信息")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public WebJsonBean<PageBean<CustomerDTO>> queryByPage(@RequestBody CustomerQueryCommand command) {
		return customerFeign.queryByPage(command);
	}

}
