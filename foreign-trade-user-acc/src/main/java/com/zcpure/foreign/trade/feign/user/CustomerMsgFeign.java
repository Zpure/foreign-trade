package com.zcpure.foreign.trade.feign.user;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.CustomerMsgAddCommand;
import com.zcpure.foreign.trade.command.user.CustomerMsgQueryCommand;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import com.zcpure.foreign.trade.dto.user.CustomerMsgDTO;
import com.zcpure.foreign.trade.utils.page.PageBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@FeignClient(name = "foreign-trade-user")
public interface CustomerMsgFeign {

	@PostMapping("/api/customer/msg/page")
	WebJsonBean<PageBean<CustomerMsgDTO>> queryByPage(@RequestBody CustomerMsgQueryCommand command);

	@PostMapping("/api/customer/msg/add")
	WebJsonBean<GoodsDTO> add(@RequestBody CustomerMsgAddCommand command);
}
