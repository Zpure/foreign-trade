package com.zcpure.foreign.trade.user.dao.mapper;

import com.zcpure.foreign.trade.command.user.SupplierGoodsQueryCommand;
import com.zcpure.foreign.trade.dto.user.SupplierGoodsDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierGoodsMapper {
	List<SupplierGoodsDTO> queryPage(SupplierGoodsQueryCommand command);
}
