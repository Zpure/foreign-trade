package com.zcpure.foreign.trade.user.controller;

import com.github.pagehelper.Page;
import com.zcpure.foreign.trade.BaseCode;
import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.LoginCommand;
import com.zcpure.foreign.trade.command.user.UserQueryCommand;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import com.zcpure.foreign.trade.user.dao.entity.UserEntity;
import com.zcpure.foreign.trade.user.dao.mapper.UserMapper;
import com.zcpure.foreign.trade.user.dao.repostitory.UserRepository;
import com.zcpure.foreign.trade.user.utils.page.PageBeanAssembler;
import com.zcpure.foreign.trade.user.utils.page.RowBoundsBuilder;
import com.zcpure.foreign.trade.utils.page.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin
@Api(value = "管理员用户")
public class UserController {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRepository userRepository;


	@ApiOperation(value = "获取用户信息")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public WebJsonBean<PageBean<UserDTO>> queryByPage(@RequestBody UserQueryCommand command) {
		RowBounds bounds = RowBoundsBuilder.build(command.getPageNo(), command.getPageSize());
		Page<UserDTO> result = userMapper.queryPage(command, bounds);
		return WebJsonBean.SUCCESS(new PageBeanAssembler().toBean(result));
	}

	@ApiOperation(value = "登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public WebJsonBean<UserDTO> login(@RequestBody LoginCommand command) {
		UserEntity userEntity = userRepository.findByPhone(command.getUsername());
		if (userEntity == null || !userEntity.getPassword().equals(command.getPassword())) {
			return new WebJsonBean<>(BaseCode.LOGIN_ERROR, null);
		}
		return WebJsonBean.SUCCESS(UserEntity.form(userEntity));
	}

}
