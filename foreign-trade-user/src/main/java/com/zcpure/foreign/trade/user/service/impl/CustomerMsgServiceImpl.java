package com.zcpure.foreign.trade.user.service.impl;

import com.zcpure.foreign.trade.command.user.CustomerMsgAddCommand;
import com.zcpure.foreign.trade.enums.CustomerMsgStatusEnum;
import com.zcpure.foreign.trade.user.dao.entity.CustomerMsgEntity;
import com.zcpure.foreign.trade.user.dao.entity.GroupEntity;
import com.zcpure.foreign.trade.user.dao.repostitory.CustomerMsgRepository;
import com.zcpure.foreign.trade.user.dao.repostitory.GroupRepository;
import com.zcpure.foreign.trade.user.service.CustomerMsgService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ethan
 * @create_time 2018/10/31 14:55
 */
@Service
@Transactional
public class CustomerMsgServiceImpl implements CustomerMsgService {

	@Autowired
	private CustomerMsgRepository customerMsgRepository;
	@Autowired
	private GroupRepository groupRepository;

	@Override
	public void add(CustomerMsgAddCommand command) {
		GroupEntity groupEntity = groupRepository.findOne(command.getGroupCode());
		Validate.notNull(groupEntity, "集团信息错误：" + command.getGroupCode());
		CustomerMsgEntity entity = CustomerMsgEntity.form(command, groupEntity);
		Validate.notNull(entity, "信息错误");
		customerMsgRepository.save(entity);
	}

	@Override
	public void updateStatus(Long id, CustomerMsgStatusEnum status) {
		CustomerMsgEntity entity = customerMsgRepository.findOne(id);
		Validate.notNull(entity, "消息不存在");
		entity.setStatus(status.getCode());
		customerMsgRepository.save(entity);
	}
}
