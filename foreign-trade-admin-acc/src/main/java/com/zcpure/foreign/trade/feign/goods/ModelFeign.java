package com.zcpure.foreign.trade.feign.goods;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.*;
import com.zcpure.foreign.trade.dto.goods.BrandDTO;
import com.zcpure.foreign.trade.dto.goods.ModelDTO;
import com.zcpure.foreign.trade.utils.page.PageBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "foreign-trade-goods")
public interface ModelFeign {

	@PostMapping(value = "/api/model/add")
	WebJsonBean<Void> add(@RequestBody ModelAddCommand command);

	@PostMapping(value = "/api/model/page")
	WebJsonBean<PageBean<ModelDTO>> queryByPage(@RequestBody ModelQueryCommand command);

	@PostMapping(value = "/api/model/update")
	WebJsonBean<Void> update(@RequestBody ModelUpdateCommand command);

}
