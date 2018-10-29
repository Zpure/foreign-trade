package com.zcpure.foreign.trade.goods.dao.repository;


import com.zcpure.foreign.trade.goods.dao.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ethan
 * @create_time 2018/10/22 15:24
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
	Boolean existsByParentIdAndName(Long parentId, String name);
}
