package com.zcpure.foreign.trade.user.dao.mapper;

import com.zcpure.foreign.trade.command.user.CustomerMsgQueryCommand;
import com.zcpure.foreign.trade.command.user.CustomerQueryCommand;
import com.zcpure.foreign.trade.command.user.CustomerUpdateCommand;
import com.zcpure.foreign.trade.dto.user.CustomerDTO;
import com.zcpure.foreign.trade.dto.user.CustomerMsgDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMsgMapper {
	List<CustomerMsgDTO> queryPage(CustomerMsgQueryCommand command);
}
