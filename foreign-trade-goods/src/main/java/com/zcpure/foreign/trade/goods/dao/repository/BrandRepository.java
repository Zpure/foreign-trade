package com.zcpure.foreign.trade.goods.dao.repository;


import com.zcpure.foreign.trade.goods.dao.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ethan
 * @create_time 2018/10/22 11:39
 */
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
	Boolean existsByName(String name);
}
