package com.zcpure.foreign.trade.order.feign;

import com.zcpure.foreign.trade.command.goods.GoodsQueryCommand;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import com.zcpure.foreign.trade.utils.page.PageBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ethan
 * @create_time 2018/10/23 16:19
 */
@FeignClient(name = "foreign-trade-goods")
public interface GoodsFeign {
	@PostMapping(value = "/api/goods/page")
	PageBean<GoodsDTO> queryPage(@RequestBody GoodsQueryCommand command);

	@GetMapping(value = "/api/goods/{code}")
	GoodsDTO getByCode(@PathVariable String code);

	@GetMapping(value = "/api/goods/batch-code")
	List<GoodsDTO> getBatchByCode(@RequestParam("codes") String codes);
}
