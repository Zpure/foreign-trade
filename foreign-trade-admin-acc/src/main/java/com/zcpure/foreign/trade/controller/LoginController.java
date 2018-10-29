package com.zcpure.foreign.trade.controller;

import com.zcpure.foreign.trade.BaseCode;
import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.LoginCommand;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import com.zcpure.foreign.trade.feign.UserFeign;
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
@RequestMapping("/api")
@CrossOrigin
@Api(value = "登录")
public class LoginController {
	@Autowired
	private UserFeign userFeign;

	private static String LOGIN_TOKEN = "login_token_session";

	@ApiOperation(value = "登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public WebJsonBean<UserDTO> login(HttpServletRequest request, @RequestBody LoginCommand command) {
		HttpSession session = request.getSession();
		UserDTO loginInfo = (UserDTO) session.getAttribute(LOGIN_TOKEN);
		if (loginInfo != null) {
			return new WebJsonBean(BaseCode.SUCCESS, loginInfo);
		} else {
			WebJsonBean<UserDTO> result = userFeign.login(command);
			if (result.getCode() == BaseCode.SUCCESS.getIndex()) {
				session.setAttribute(LOGIN_TOKEN, result.getData());
			}
			return result;
		}
	}
}
