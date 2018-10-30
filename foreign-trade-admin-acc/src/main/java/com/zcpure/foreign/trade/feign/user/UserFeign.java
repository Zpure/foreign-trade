package com.zcpure.foreign.trade.feign.user;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.LoginCommand;
import com.zcpure.foreign.trade.command.user.UserAddCommand;
import com.zcpure.foreign.trade.command.user.UserQueryCommand;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import com.zcpure.foreign.trade.utils.page.PageBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "foreign-trade-user")
public interface UserFeign {

	@PostMapping(value = "/api/user/login")
	WebJsonBean<UserDTO> login(@RequestBody LoginCommand command);

	@PostMapping(value = "/api/user/add")
	WebJsonBean<Void> add(@RequestBody UserAddCommand command);

	@PostMapping(value = "/api/user/page")
	WebJsonBean<PageBean<UserDTO>> page(@RequestBody UserQueryCommand command);

	@PostMapping(value = "/api/user/remove")
	WebJsonBean<Void> remove(@RequestBody UserQueryCommand command);

}
