package com.zcpure.foreign.trade.order.dao.repository;


import com.zcpure.foreign.trade.order.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ethan
 * @create_time 2018/10/22 11:39
 */
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
}
