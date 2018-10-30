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
@CrossOrigin
@Api(value = "管理员用户")
public class UserController {
	@Autowired
	private UserFeign userFeign;

	@ApiOperation(value = "添加用户信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> queryByPage(@RequestBody UserAddCommand command) {
		userFeign.add(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "删除用户信息")
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public WebJsonBean<Void> remove(@RequestBody UserQueryCommand command) {
		userFeign.remove(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "获取用户信息")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public WebJsonBean<PageBean<UserDTO>> queryByPage(@RequestBody UserQueryCommand command) {
		return userFeign.page(command);
	}

}
