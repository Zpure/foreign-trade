package com.zcpure.foreign.trade.goods.feign;

import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.WebJsonBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "foreign-trade-order")
public interface OrderFeign {

	@PostMapping(value = "/api/order/through-info")
	WebJsonBean<RequestThroughInfo> throughInfo();

}
