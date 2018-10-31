package com.zcpure.foreign.trade.user.dao.repostitory;


import com.zcpure.foreign.trade.user.dao.entity.SupplierGoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ethan
 * @create_time 2018/10/22 11:39
 */
public interface SupplierGoodsRepository extends JpaRepository<SupplierGoodsEntity, Long> {
	SupplierGoodsEntity findBySupplierCodeAndGoodsCode(String supplierCode, String goodsCode);

	List<SupplierGoodsEntity> findBySupplierCode(String code);
}
