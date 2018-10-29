package com.zcpure.foreign.trade.goods.dao.mapper;

import com.github.pagehelper.Page;
import com.zcpure.foreign.trade.command.goods.GoodsQueryCommand;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsMapper {
	Page<GoodsDTO> queryPage(GoodsQueryCommand command, RowBounds bounds);
}
