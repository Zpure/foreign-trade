package com.zcpure.foreign.trade.feign.user;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.CustomerAddCommand;
import com.zcpure.foreign.trade.command.user.CustomerQueryCommand;
import com.zcpure.foreign.trade.command.user.CustomerUpdateCommand;
import com.zcpure.foreign.trade.dto.user.CustomerDTO;
import com.zcpure.foreign.trade.utils.page.PageBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@FeignClient(name = "foreign-trade-user")
public interface CustomerFeign {
	@PostMapping(value = "/api/customer/add")
	WebJsonBean<Void> add(@RequestBody CustomerAddCommand command);

	@GetMapping(value = "/api/customer/{code}")
	WebJsonBean<CustomerDTO> getByCode(@PathVariable("code") String code);

	@PostMapping(value = "/api/customer/update")
	WebJsonBean<Void> update(@RequestBody CustomerUpdateCommand command);

	@PostMapping(value = "/api/customer/page")
	WebJsonBean<PageBean<CustomerDTO>> queryByPage(@RequestBody CustomerQueryCommand command);

}
