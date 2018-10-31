package com.zcpure.foreign.trade.user.service.impl;

import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import com.zcpure.foreign.trade.command.user.CustomerAddCommand;
import com.zcpure.foreign.trade.command.user.CustomerUpdateCommand;
import com.zcpure.foreign.trade.user.dao.entity.CustomerEntity;
import com.zcpure.foreign.trade.user.dao.mapper.CustomerMapper;
import com.zcpure.foreign.trade.user.dao.repostitory.CustomerRepository;
import com.zcpure.foreign.trade.user.service.CustomerService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ethan
 * @create_time 2018/10/31 14:55
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public void add(CustomerAddCommand command) {
		List<CustomerEntity> entityList = customerRepository.findByCodeOrEmail(command.getCode(), command.getEmail());
		Validate.isTrue(entityList == null || entityList.size() == 0,
			"客户编码或邮箱已存在");
		CustomerEntity customerEntity = CustomerEntity.form(command);
		customerRepository.save(customerEntity);
	}

	@Override
	public void update(CustomerUpdateCommand command) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		command.setGroupCode(info.getGroupCode());
		customerMapper.update(command);
	}
}
