package com.zcpure.foreign.trade.order.feign;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.dto.user.SupplierDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ethan
 * @create_time 2018/10/23 16:19
 */
@FeignClient(name = "foreign-trade-user")
public interface SupplierFeign {
	@GetMapping(value = "/api/supplier/{code}")
	WebJsonBean<SupplierDTO> getByCode(@PathVariable("code") String code);

	@GetMapping(value = "/api/supplier/batch")
	WebJsonBean<List<SupplierDTO>> batchByCodes(@RequestParam("codes") String codes);

	@GetMapping(value = "/api/supplier/detail/{code}")
	WebJsonBean<SupplierDTO> getDetailByCode(@PathVariable("code") String code);
}
