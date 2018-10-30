package com.zcpure.foreign.trade.user.dao.mapper;

import com.zcpure.foreign.trade.command.user.UserQueryCommand;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
	List<UserDTO> queryPage(UserQueryCommand command);

	void delete(UserQueryCommand command);
}
