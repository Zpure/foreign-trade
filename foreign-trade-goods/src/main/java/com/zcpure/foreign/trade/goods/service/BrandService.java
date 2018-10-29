package com.zcpure.foreign.trade.goods.service;

import com.zcpure.foreign.trade.command.goods.BrandAddCommand;
import com.zcpure.foreign.trade.command.goods.BrandQueryCommand;
import com.zcpure.foreign.trade.dto.goods.BrandDTO;
import com.zcpure.foreign.trade.utils.page.PageBean;

/**
 * @author ethan
 * @create_time 2018/10/22 13:45
 */
public interface BrandService {
	void add(BrandAddCommand command);
}
