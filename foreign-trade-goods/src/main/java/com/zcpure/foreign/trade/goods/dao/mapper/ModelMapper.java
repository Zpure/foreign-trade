package com.zcpure.foreign.trade.goods.dao.mapper;

import com.zcpure.foreign.trade.command.goods.ModelQueryCommand;
import com.zcpure.foreign.trade.dto.goods.ModelDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelMapper {
	List<ModelDTO> queryPage(ModelQueryCommand command);
}
