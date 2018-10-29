package com.zcpure.foreign.trade.goods.dao.repository;

import com.zcpure.foreign.trade.goods.dao.entity.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ethan
 * @create_time 2018/10/22 18:17
 */
public interface GoodsRepository extends JpaRepository<GoodsEntity, String> {
	Boolean existsByModelIdAndName(Long modelId, String name);
}
