package com.zcpure.foreign.trade.order.dao.repository;


import com.zcpure.foreign.trade.order.dao.entity.OrderDisDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ethan
 * @create_time 2018/10/22 11:39
 */
public interface OrderDisDetailRepository extends JpaRepository<OrderDisDetailEntity, Long> {
	OrderDisDetailEntity getByOrderCodeAndGoodsCodeAndName(String orderCode, String goodsCode, String name);
}
