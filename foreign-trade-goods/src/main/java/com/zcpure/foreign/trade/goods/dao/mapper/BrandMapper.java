package com.zcpure.foreign.trade.goods.dao.mapper;

import com.zcpure.foreign.trade.command.goods.BrandQueryCommand;
import com.zcpure.foreign.trade.dto.goods.BrandDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandMapper {
	List<BrandDTO> queryPage(BrandQueryCommand command);
}
