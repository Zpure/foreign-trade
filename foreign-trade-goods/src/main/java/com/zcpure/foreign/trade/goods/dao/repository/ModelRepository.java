package com.zcpure.foreign.trade.goods.dao.repository;

import com.zcpure.foreign.trade.goods.dao.entity.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ethan
 * @create_time 2018/10/22 15:24
 */
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {

	Boolean existsByBrandIdAndName(Long brandId, String name);
}
