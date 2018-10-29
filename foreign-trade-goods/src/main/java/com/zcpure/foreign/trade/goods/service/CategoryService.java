package com.zcpure.foreign.trade.goods.service;

import com.zcpure.foreign.trade.command.goods.CategoryAddCommand;
import com.zcpure.foreign.trade.dto.goods.CategoryLinkDTO;

/**
 * @author ethan
 * @create_time 2018/10/22 13:45
 */
public interface CategoryService {
	void add(CategoryAddCommand command);

	CategoryLinkDTO getOneLink(Long cid);
}
