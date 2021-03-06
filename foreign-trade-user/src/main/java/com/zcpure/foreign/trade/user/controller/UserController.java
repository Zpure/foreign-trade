package com.zcpure.foreign.trade.user.controller;

import com.github.pagehelper.PageHelper;
import com.zcpure.foreign.trade.*;
import com.zcpure.foreign.trade.command.user.LoginCommand;
import com.zcpure.foreign.trade.command.user.UserAddCommand;
import com.zcpure.foreign.trade.command.user.UserQueryCommand;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import com.zcpure.foreign.trade.enums.UserLevelEnum;
import com.zcpure.foreign.trade.user.dao.entity.UserEntity;
import com.zcpure.foreign.trade.user.dao.mapper.UserMapper;
import com.zcpure.foreign.trade.user.dao.repostitory.UserRepository;
import com.zcpure.foreign.trade.user.service.UserService;
import com.zcpure.foreign.trade.user.utils.page.PageBeanAssembler;
import com.zcpure.foreign.trade.utils.page.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "管理员用户")
public class UserController {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@ApiOperation(value = "添加用户信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody UserAddCommand command) {
		userService.add(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "删除用户信息")
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public WebJsonBean<Void> remove(@RequestBody UserQueryCommand command) {
		userService.remove(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "获取用户信息")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public WebJsonBean<PageBean<UserDTO>> queryByPage(@RequestBody UserQueryCommand command) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		command.setGroupCode(info.getGroupCode());
		PageHelper.startPage(command.getPageNo() != null ? command.getPageNo() : Const.PAGE_DEFAULT_NO,
			command.getPageSize() != null ? command.getPageSize() : Const.PAGE_DEFAULT_SIZE);
		Set<Integer> levelSet = new HashSet<>();
		if(info.getUserLevel() == UserLevelEnum.ADMIN.getCode()) {
			levelSet.add(UserLevelEnum.GROUP_ADMIN.getCode());
			levelSet.add(UserLevelEnum.GROUP_NORMAL.getCode());
		} else if(info.getUserLevel() == UserLevelEnum.GROUP_ADMIN.getCode()) {
			levelSet.add(UserLevelEnum.GROUP_NORMAL.getCode());
		} else {
			levelSet.add(-1);
		}
		command.setLevels(levelSet);
		List<UserDTO> result = userMapper.queryPage(command);
		return WebJsonBean.SUCCESS(new PageBeanAssembler().toBeanByList(result));
	}

	@ApiOperation(value = "登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public WebJsonBean<UserDTO> login(@RequestBody LoginCommand command) {
		UserEntity userEntity = userRepository.findByName(command.getUsername());
		if (userEntity == null || !userEntity.getPassword().equals(command.getPassword())) {
			return new WebJsonBean<>(BaseCode.LOGIN_ERROR, null);
		}
		return WebJsonBean.SUCCESS(UserEntity.formDTO(userEntity));
	}

}
