package com.zcpure.foreign.trade.controller;

import com.zcpure.foreign.trade.BaseCode;
import com.zcpure.foreign.trade.Const;
import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.LoginCommand;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import com.zcpure.foreign.trade.feign.user.UserFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@RestController
@RequestMapping("/api/admin")
@Api(value = "登录")
public class LoginController {
	@Autowired
	private UserFeign userFeign;

	@ApiOperation(value = "登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public WebJsonBean<UserDTO> login(HttpServletRequest request, @RequestBody LoginCommand command) {
		HttpSession session = request.getSession();
		UserDTO loginInfo = (UserDTO) session.getAttribute(Const.LOGIN_TOKEN);
		if (loginInfo != null) {
			session.removeAttribute(Const.LOGIN_TOKEN);
		}
		WebJsonBean<UserDTO> result = userFeign.login(command);
		if (result.getCode() == BaseCode.SUCCESS.getIndex()) {
			session.setAttribute(Const.LOGIN_TOKEN, result.getData());
		}
		return result;
	}
}
