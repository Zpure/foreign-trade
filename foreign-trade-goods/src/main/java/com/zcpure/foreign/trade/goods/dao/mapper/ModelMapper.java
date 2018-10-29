package com.zcpure.foreign.trade.goods.dao.mapper;

import com.github.pagehelper.Page;
import com.zcpure.foreign.trade.command.goods.ModelQueryCommand;
import com.zcpure.foreign.trade.dto.goods.ModelDTO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelMapper {
	Page<ModelDTO> queryPage(ModelQueryCommand command, RowBounds bounds);
}
