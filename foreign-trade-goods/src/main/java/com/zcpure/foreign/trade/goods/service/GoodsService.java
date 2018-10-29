package com.zcpure.foreign.trade.goods.service;

import com.zcpure.foreign.trade.command.goods.GoodsAddCommand;
import com.zcpure.foreign.trade.command.goods.GoodsQueryCommand;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import com.zcpure.foreign.trade.utils.page.PageBean;

/**
 * @author ethan
 * @create_time 2018/10/22 13:45
 */
public interface GoodsService {
	void add(GoodsAddCommand command);

	PageBean<GoodsDTO> queryPage(GoodsQueryCommand command);

	GoodsDTO getByCode(String code);
}
