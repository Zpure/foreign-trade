package com.zcpure.foreign.trade.user.dao.repostitory;


import com.zcpure.foreign.trade.user.dao.entity.CustomerEntity;
import com.zcpure.foreign.trade.user.dao.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ethan
 * @create_time 2018/10/22 11:39
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
	List<CustomerEntity> findByCodeOrEmail(String code, String email);

}
