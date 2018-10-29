package com.zcpure.foreign.trade.goods.service;

import com.zcpure.foreign.trade.command.goods.ModelAddCommand;
import com.zcpure.foreign.trade.command.goods.ModelQueryCommand;
import com.zcpure.foreign.trade.command.goods.ModelUpdateCommand;
import com.zcpure.foreign.trade.dto.goods.ModelDTO;
import com.zcpure.foreign.trade.utils.page.PageBean;

/**
 * @author ethan
 * @create_time 2018/10/22 13:45
 */
public interface ModelService {
	void add(ModelAddCommand command);

	void update(ModelUpdateCommand command);

	PageBean<ModelDTO> queryPage(ModelQueryCommand command);
}
