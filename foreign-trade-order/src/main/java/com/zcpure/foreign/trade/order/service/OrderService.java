package com.zcpure.foreign.trade.order.service;

import com.zcpure.foreign.trade.command.order.*;
import com.zcpure.foreign.trade.dto.order.OrderDTO;
import com.zcpure.foreign.trade.dto.order.OrderDetailDTO;
import com.zcpure.foreign.trade.dto.order.OrderDisDetailDTO;
import com.zcpure.foreign.trade.enums.OrderStatusEnum;
import com.zcpure.foreign.trade.utils.page.PageBean;

import java.util.List;

/**
 * @author ethan
 * @create_time 2018/10/26 13:53
 */
public interface OrderService {
	void add(OrderAddCommand command);

	PageBean<OrderDTO> queryPage(OrderQueryCommand command);

	PageBean<OrderDetailDTO> queryDetailPage(OrderDetailQueryCommand command);

	OrderDTO getDetail(String code);

	void update(OrderUpdateCommand command);

	void updateStatus(String code, OrderStatusEnum status);

	/**
	 * 订单分配给供应商
	 * @param command
	 */
	void distribution(OrderDistributionCommand command);

	List<OrderDisDetailDTO> distributionDetail(OrderDistributionDetailQueryCommand command);

	/**
	 * s实际配货数量
	 * @param command
	 */
	void distributionUpdate(OrderDistributionUpdateCommand command);
}
