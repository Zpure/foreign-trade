package com.zcpure.foreign.trade.feign.user;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.LoginCommand;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "foreign-trade-user")
public interface UserFeign {

	@GetMapping(value = "/api/user/login")
	WebJsonBean<UserDTO> login(@RequestBody LoginCommand command);

}
