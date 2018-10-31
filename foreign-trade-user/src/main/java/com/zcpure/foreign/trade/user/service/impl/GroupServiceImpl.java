package com.zcpure.foreign.trade.user.service.impl;

import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import com.zcpure.foreign.trade.command.user.GroupAddCommand;
import com.zcpure.foreign.trade.enums.UserLevelEnum;
import com.zcpure.foreign.trade.user.dao.entity.GroupEntity;
import com.zcpure.foreign.trade.user.dao.repostitory.GroupRepository;
import com.zcpure.foreign.trade.user.service.GroupService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ethan
 * @create_time 2018/10/31 13:56
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Override
	public void add(GroupAddCommand command) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		Validate.isTrue(info.getUserLevel() == UserLevelEnum.ADMIN.getCode(),
			"不允许添加集团");
		GroupEntity existEntity = groupRepository.findOne(command.getCode());
		Validate.isTrue(existEntity == null,
			"该集团编码已经存在：" + command.getCode());
		GroupEntity groupEntity = GroupEntity.form(command);
		groupRepository.save(groupEntity);
	}
}
