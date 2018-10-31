package com.zcpure.foreign.trade.order.feign;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.dto.user.CustomerDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ethan
 * @create_time 2018/10/23 16:19
 */
@FeignClient(name = "foreign-trade-user")
public interface CustomerFeign {
	@GetMapping(value = "/api/customer/{code}")
	WebJsonBean<CustomerDTO> getByCode(@PathVariable("code") String code);
}
