package com.zcpure.foreign.trade.user.dao.mapper;

import com.zcpure.foreign.trade.command.user.SupplierQueryCommand;
import com.zcpure.foreign.trade.command.user.SupplierUpdateCommand;
import com.zcpure.foreign.trade.dto.user.SupplierDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierMapper {
	List<SupplierDTO> queryPage(SupplierQueryCommand command);

	void update(SupplierUpdateCommand command);
}
