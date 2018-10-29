package com.zcpure.foreign.trade.user.controller;

import com.github.pagehelper.Page;
import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.UserQueryCommand;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import com.zcpure.foreign.trade.user.dao.mapper.UserMapper;
import com.zcpure.foreign.trade.user.dao.repostitory.UserRepository;
import com.zcpure.foreign.trade.user.utils.page.PageBeanAssembler;
import com.zcpure.foreign.trade.user.utils.page.RowBoundsBuilder;
import com.zcpure.foreign.trade.utils.page.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	@RequestMapping(value = "/page")
	public WebJsonBean<PageBean<UserDTO>> queryByPage(@RequestBody UserQueryCommand command) {
		RowBounds bounds = RowBoundsBuilder.build(command.getPageNo(), command.getPageSize());
		Page<UserDTO> result = userMapper.queryPage(command, bounds);
		return WebJsonBean.SUCCESS(new PageBeanAssembler().toBean(result));
	}

}
