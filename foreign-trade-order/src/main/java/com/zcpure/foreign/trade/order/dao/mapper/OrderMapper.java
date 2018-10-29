package com.zcpure.foreign.trade.order.dao.mapper;

import com.github.pagehelper.Page;
import com.zcpure.foreign.trade.command.order.OrderQueryCommand;
import com.zcpure.foreign.trade.dto.order.OrderDTO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
	Page<OrderDTO> queryPage(OrderQueryCommand command, RowBounds bounds);
}
