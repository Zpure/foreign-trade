package com.zcpure.foreign.trade.user.service;

import com.zcpure.foreign.trade.command.user.SupplierAddCommand;
import com.zcpure.foreign.trade.command.user.SupplierGoodsAddCommand;
import com.zcpure.foreign.trade.command.user.SupplierUpdateCommand;
import com.zcpure.foreign.trade.enums.SupplierGoodsStatusEnum;
import com.zcpure.foreign.trade.enums.SupplierStatusEnum;

/**
 * @author ethan
 * @create_time 2018/10/22 13:45
 */
public interface SupplierService {

	void add(SupplierAddCommand command);

	void update(SupplierUpdateCommand command);

	void addGoods(SupplierGoodsAddCommand command);

	void updateStatus(String code, SupplierStatusEnum status);

	void updateStatusGoods(Long id, SupplierGoodsStatusEnum status);
}
