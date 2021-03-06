package com.zcpure.foreign.trade.feign.order;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.order.*;
import com.zcpure.foreign.trade.dto.order.OrderDTO;
import com.zcpure.foreign.trade.dto.order.OrderDetailDTO;
import com.zcpure.foreign.trade.dto.order.OrderDisDetailDTO;
import com.zcpure.foreign.trade.utils.page.PageBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "foreign-trade-order")
public interface OrderFeign {

	@PostMapping(value = "/api/order/add")
	WebJsonBean<Void> add(@RequestBody OrderAddCommand command);

	@PostMapping(value = "/api/order/page")
	WebJsonBean<PageBean<OrderDTO>> page(@RequestBody OrderQueryCommand command);

	@PostMapping(value = "/api/order/detail/page")
	WebJsonBean<PageBean<OrderDetailDTO>> pageDetail(@RequestBody OrderDetailQueryCommand command);

	@PostMapping(value = "/api/order/update")
	WebJsonBean<Void> update(@RequestBody OrderUpdateCommand command);

	@GetMapping(value = "/api/order/detail/{code}")
	WebJsonBean<OrderDTO> detail(@PathVariable("code") String code);

	@PostMapping(value = "/api/order/confirm/{code}")
	WebJsonBean<Void> confirm(@PathVariable("code") String code);

	@PostMapping(value = "/api/order/distribution/{code}")
	WebJsonBean<Void> distribution(@PathVariable("code") String code);

	@PostMapping(value = "/api/order/delivery/{code}")
	WebJsonBean<Void> delivery(@PathVariable("code") String code);

	@PostMapping(value = "/api/order/receipt/{code}")
	WebJsonBean<Void> receipt(@PathVariable("code") String code);

	@PostMapping(value = "/api/order/success/{code}")
	WebJsonBean<Void> success(@PathVariable("code") String code);

	@PostMapping(value = "/api/order/distribution")
	WebJsonBean<Void> distribution(@RequestBody OrderDistributionCommand command);

	@PostMapping(value = "/api/order/distribution/detail")
	WebJsonBean<List<OrderDisDetailDTO>> distributionDetail(@RequestBody OrderDistributionDetailQueryCommand command);

	@PostMapping(value = "/api/order/distribution/query")
	WebJsonBean<PageBean<OrderDisDetailDTO>> pageDistributionDetail(@RequestBody OrderDistributionDetailQueryCommand command);

	@PostMapping(value = "/api/order/distribution/update")
	WebJsonBean<Void> distributionUpdate(@RequestBody OrderDistributionUpdateCommand command);

}
