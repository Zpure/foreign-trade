package com.zcpure.foreign.trade.order.dao.mapper;

import com.zcpure.foreign.trade.command.order.OrderQueryCommand;
import com.zcpure.foreign.trade.dto.order.OrderDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
	List<OrderDTO> queryPage(OrderQueryCommand command);
}
