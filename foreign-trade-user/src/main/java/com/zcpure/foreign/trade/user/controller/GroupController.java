package com.zcpure.foreign.trade.user.controller;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.GroupAddCommand;
import com.zcpure.foreign.trade.command.user.UserAddCommand;
import com.zcpure.foreign.trade.user.dao.repostitory.GroupRepository;
import com.zcpure.foreign.trade.user.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ethan
 * @create_time 2018/10/31 13:50
 */
@RestController
@RequestMapping("/api/group")
@Api("集团管理")
public class GroupController {

	@Autowired
	private GroupService groupService;

	@ApiOperation(value = "添加集团信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody GroupAddCommand command) {
		groupService.add(command);
		return WebJsonBean.SUCCESS();
	}
}
