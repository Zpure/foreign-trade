package com.zcpure.foreign.trade.goods.dao.mapper;

import com.zcpure.foreign.trade.command.goods.GoodsQueryCommand;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsMapper {
	List<GoodsDTO> queryPage(GoodsQueryCommand command);
}
