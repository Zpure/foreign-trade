package com.zcpure.foreign.trade.order.dao.mapper;

import com.zcpure.foreign.trade.command.order.OrderDetailQueryCommand;
import com.zcpure.foreign.trade.dto.order.OrderDetailDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailMapper {
	List<OrderDetailDTO> queryPage(OrderDetailQueryCommand command);
}
