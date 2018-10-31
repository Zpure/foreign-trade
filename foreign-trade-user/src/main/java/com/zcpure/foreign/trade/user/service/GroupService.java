package com.zcpure.foreign.trade.user.service;

import com.zcpure.foreign.trade.command.user.GroupAddCommand;
import com.zcpure.foreign.trade.command.user.UserAddCommand;
import com.zcpure.foreign.trade.command.user.UserQueryCommand;

/**
 * @author ethan
 * @create_time 2018/10/22 13:45
 */
public interface GroupService {

	void add(GroupAddCommand command);
}
