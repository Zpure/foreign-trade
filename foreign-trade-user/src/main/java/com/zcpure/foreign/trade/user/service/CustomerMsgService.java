package com.zcpure.foreign.trade.user.service;

import com.zcpure.foreign.trade.command.user.CustomerMsgAddCommand;
import com.zcpure.foreign.trade.enums.CustomerMsgStatusEnum;

/**
 * @author ethan
 * @create_time 2018/10/22 13:45
 */
public interface CustomerMsgService {

	void add(CustomerMsgAddCommand command);

	void updateStatus(Long id, CustomerMsgStatusEnum status);
}
