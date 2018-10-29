package com.zcpure.foreign.trade.goods.dao.mapper;

import com.github.pagehelper.Page;
import com.zcpure.foreign.trade.command.goods.BrandQueryCommand;
import com.zcpure.foreign.trade.dto.goods.BrandDTO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandMapper {
	Page<BrandDTO> queryPage(BrandQueryCommand command, RowBounds bounds);
}
