package com.zcpure.foreign.trade.user.feign;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "foreign-trade-goods")
public interface GoodsFeign {

	@GetMapping(value = "/api/goods/{code}")
	WebJsonBean<GoodsDTO> getByCode(@PathVariable("code") String code);

}
