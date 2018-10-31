package com.zcpure.foreign.trade.user.dao.repostitory;


import com.zcpure.foreign.trade.user.dao.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ethan
 * @create_time 2018/10/22 11:39
 */
public interface SupplierRepository extends JpaRepository<SupplierEntity, String> {

	List<SupplierEntity> findByCodeIn(List<String> codes);
}
