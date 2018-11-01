package com.zcpure.foreign.trade.feign.user;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.SupplierAddCommand;
import com.zcpure.foreign.trade.command.user.SupplierGoodsAddCommand;
import com.zcpure.foreign.trade.command.user.SupplierQueryCommand;
import com.zcpure.foreign.trade.dto.user.SupplierDTO;
import com.zcpure.foreign.trade.utils.page.PageBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@FeignClient(name = "foreign-trade-user")
public interface SupplierFeign {

	@PostMapping(value = "/api/supplier/add")
	WebJsonBean<Void> add(@RequestBody SupplierAddCommand command);

	@PostMapping(value = "/api/supplier/add-goods")
	WebJsonBean<Void> addGoods(@RequestBody SupplierGoodsAddCommand command);

	@GetMapping(value = "/api/supplier/{code}")
	WebJsonBean<SupplierDTO> getByCode(@PathVariable("code") String code);

	@GetMapping(value = "/api/supplier/batch")
	WebJsonBean<SupplierDTO> batchByCodes(@RequestParam("codes") String codes);

	@GetMapping(value = "/api/supplier/detail/{code}")
	WebJsonBean<SupplierDTO> getDetailByCode(@PathVariable("code") String code);

	@PostMapping(value = "/api/supplier/stop/{code}")
	WebJsonBean<Void> stop(@PathVariable("code") String code);

	@PostMapping(value = "/api/supplier/stop/goods/{id}")
	WebJsonBean<Void> stopGoods(@PathVariable("id") Long id);

	@PostMapping(value = "/api/supplier/page")
	WebJsonBean<PageBean<SupplierDTO>> queryByPage(@RequestBody SupplierQueryCommand command);

}
