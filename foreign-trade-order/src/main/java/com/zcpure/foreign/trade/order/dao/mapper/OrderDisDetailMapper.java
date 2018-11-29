package com.zcpure.foreign.trade.order.dao.mapper;

import com.zcpure.foreign.trade.command.order.OrderDistributionDetailQueryCommand;
import com.zcpure.foreign.trade.dto.order.OrderDisDetailDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDisDetailMapper {
	List<OrderDisDetailDTO> queryPage(OrderDistributionDetailQueryCommand command);
}
