package com.zcpure.foreign.trade.feign.goods;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.BrandAddCommand;
import com.zcpure.foreign.trade.command.goods.BrandQueryCommand;
import com.zcpure.foreign.trade.dto.goods.BrandDTO;
import com.zcpure.foreign.trade.utils.page.PageBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "foreign-trade-goods")
public interface BrandFeign {

	@PostMapping(value = "/api/brand/add")
	WebJsonBean<Void> add(@RequestBody BrandAddCommand command);

	@PostMapping(value = "/api/brand/page")
	WebJsonBean<PageBean<BrandDTO>> queryByPage(@RequestBody BrandQueryCommand command);

}
