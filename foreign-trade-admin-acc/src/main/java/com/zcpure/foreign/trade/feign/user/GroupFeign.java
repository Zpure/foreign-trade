package com.zcpure.foreign.trade.feign.user;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.GroupAddCommand;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ethan
 * @create_time 2018/10/31 13:50
 */
@FeignClient(name = "foreign-trade-user")
public interface GroupFeign {

	@PostMapping(value = "/api/group/add")
	WebJsonBean<Void> add(@RequestBody GroupAddCommand command);
}
