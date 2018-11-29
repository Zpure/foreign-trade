package com.zcpure.foreign.trade.user.controller;

import com.github.pagehelper.PageHelper;
import com.zcpure.foreign.trade.Const;
import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.CustomerMsgAddCommand;
import com.zcpure.foreign.trade.command.user.CustomerMsgQueryCommand;
import com.zcpure.foreign.trade.dto.user.CustomerMsgDTO;
import com.zcpure.foreign.trade.enums.CustomerMsgStatusEnum;
import com.zcpure.foreign.trade.user.dao.mapper.CustomerMsgMapper;
import com.zcpure.foreign.trade.user.service.CustomerMsgService;
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
@RequestMapping("/api/customer/msg")
@Api(value = "客户")
public class CustomerMsgController {
	@Autowired
	private CustomerMsgMapper customerMsgMapper;

	@Autowired
	private CustomerMsgService customerMsgService;

	@ApiOperation(value = "添加客户消息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody CustomerMsgAddCommand command) {
		customerMsgService.add(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "获取客户消息列表")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public WebJsonBean<PageBean<CustomerMsgDTO>> queryByPage(@RequestBody CustomerMsgQueryCommand command) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		command.setGroupCode(info.getGroupCode());
		PageHelper.startPage(command.getPageNo() != null ? command.getPageNo() : Const.PAGE_DEFAULT_NO,
			command.getPageSize() != null ? command.getPageSize() : Const.PAGE_DEFAULT_SIZE);
		List<CustomerMsgDTO> result = customerMsgMapper.queryPage(command);
		return WebJsonBean.SUCCESS(new PageBeanAssembler().toBeanByList(result));
	}

	@ApiOperation(value = "处理消息")
	@RequestMapping(value = "/deal/{id}", method = RequestMethod.POST)
	public WebJsonBean<Void> confirm(@PathVariable Long id) {
		customerMsgService.updateStatus(id, CustomerMsgStatusEnum.DEAL);
		return WebJsonBean.SUCCESS();
	}

}
