package com.zcpure.foreign.trade.feign;

import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.WebJsonBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "foreign-trade-order")
public interface OrderFeign {

	@GetMapping(value = "/api/order/through-info")
	WebJsonBean<RequestThroughInfo> throughInfo();

}
