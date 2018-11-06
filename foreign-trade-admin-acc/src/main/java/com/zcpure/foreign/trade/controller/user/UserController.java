package com.zcpure.foreign.trade.controller.user;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.UserAddCommand;
import com.zcpure.foreign.trade.command.user.UserQueryCommand;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import com.zcpure.foreign.trade.feign.user.UserFeign;
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
@RequestMapping("/api/admin/user")
@Api(value = "管理员用户")
public class UserController {
	@Autowired
	private UserFeign userFeign;

	@ApiOperation(value = "添加用户信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> queryByPage(@RequestBody UserAddCommand command) {
		return userFeign.add(command);
	}

	@ApiOperation(value = "删除用户信息")
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public WebJsonBean<Void> remove(@RequestBody UserQueryCommand command) {
		return userFeign.remove(command);
	}

	@ApiOperation(value = "获取用户信息")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public WebJsonBean<PageBean<UserDTO>> queryByPage(UserQueryCommand command) {
		return userFeign.page(command);
	}

}
