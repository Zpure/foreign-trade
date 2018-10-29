package com.zcpure.foreign.trade.user.dao.mapper;

import com.github.pagehelper.Page;
import com.zcpure.foreign.trade.command.goods.BrandQueryCommand;
import com.zcpure.foreign.trade.command.user.UserQueryCommand;
import com.zcpure.foreign.trade.dto.goods.BrandDTO;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
	Page<UserDTO> queryPage(UserQueryCommand command, RowBounds bounds);
}
