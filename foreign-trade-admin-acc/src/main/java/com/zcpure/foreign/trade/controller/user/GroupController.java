package com.zcpure.foreign.trade.controller.user;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.GroupAddCommand;
import com.zcpure.foreign.trade.feign.user.GroupFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ethan
 * @create_time 2018/10/31 13:50
 */
@RestController
@RequestMapping("/api/admin/group")
@Api("集团管理")
public class GroupController {

	@Autowired
	private GroupFeign groupFeign;

	@ApiOperation(value = "添加集团信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody GroupAddCommand command) {
		return groupFeign.add(command);
	}
}
