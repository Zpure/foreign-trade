package com.zcpure.foreign.trade.order.service;

import com.zcpure.foreign.trade.command.order.*;
import com.zcpure.foreign.trade.dto.order.OrderDTO;
import com.zcpure.foreign.trade.utils.page.PageBean;

/**
 * @author ethan
 * @create_time 2018/10/26 13:53
 */
public interface OrderService {
	void add(OrderAddCommand command);

	PageBean<OrderDTO> queryPage(OrderQueryCommand command);

	OrderDTO getDetail(String code);

	void update(OrderUpdateCommand command);

	void distribution(OrderDistributionCommand command);

	void distributionUpdate(OrderDistributionUpdateCommand command);
}
