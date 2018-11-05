package com.zcpure.foreign.trade.user.service.impl;

import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import com.zcpure.foreign.trade.command.user.UserAddCommand;
import com.zcpure.foreign.trade.command.user.UserQueryCommand;
import com.zcpure.foreign.trade.enums.UserLevelEnum;
import com.zcpure.foreign.trade.user.dao.entity.GroupEntity;
import com.zcpure.foreign.trade.user.dao.entity.UserEntity;
import com.zcpure.foreign.trade.user.dao.mapper.UserMapper;
import com.zcpure.foreign.trade.user.dao.repostitory.GroupRepository;
import com.zcpure.foreign.trade.user.dao.repostitory.UserRepository;
import com.zcpure.foreign.trade.user.service.UserService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ethan
 * @create_time 18-10-29 下午6:30
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UserMapper userMapper;

	@Override
	@Validated
	public void add(UserAddCommand command) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		Validate.isTrue(!userRepository.existsByGroupCodeAndPhone(info.getGroupCode(), command.getPhone()),
			"该手机号已经存在");
		Validate.isTrue(!userRepository.existsByGroupCodeAndName(info.getGroupCode(), command.getName()),
			"该用户名已经存在");
		GroupEntity groupEntity = groupRepository.findOne(command.getGroupCode());
		UserEntity userEntity = UserEntity.form(info, command, groupEntity);
		userRepository.save(userEntity);
	}

	@Override
	public void remove(UserQueryCommand command) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		command.setGroupCode(info.getGroupCode());
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
		userMapper.delete(command);
	}
}
