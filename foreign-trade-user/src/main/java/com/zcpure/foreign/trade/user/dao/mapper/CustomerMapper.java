package com.zcpure.foreign.trade.user.dao.mapper;

import com.zcpure.foreign.trade.command.user.CustomerQueryCommand;
import com.zcpure.foreign.trade.command.user.CustomerUpdateCommand;
import com.zcpure.foreign.trade.dto.user.CustomerDTO;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMapper {
	List<CustomerDTO> queryPage(CustomerQueryCommand command);

	void update(CustomerUpdateCommand command);
}
