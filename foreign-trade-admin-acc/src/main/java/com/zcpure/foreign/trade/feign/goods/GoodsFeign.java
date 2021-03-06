package com.zcpure.foreign.trade.feign.goods;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.GoodsAddCommand;
import com.zcpure.foreign.trade.command.goods.GoodsQueryCommand;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import com.zcpure.foreign.trade.utils.page.PageBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@FeignClient(name = "foreign-trade-goods")
public interface GoodsFeign {

	@PostMapping("/api/goods/add")
	WebJsonBean<Void> add(@RequestBody GoodsAddCommand command);

	@PostMapping("/api/goods/page")
	WebJsonBean<PageBean<GoodsDTO>> queryByPage(@RequestBody GoodsQueryCommand command);

	@GetMapping("/api/goods/{code}")
	WebJsonBean<GoodsDTO> getByCode(@PathVariable("code") String code);

	@GetMapping("/api/goods/batch-code")
	WebJsonBean<List<GoodsDTO>> batchByCode(@RequestParam("codes") String codes);

	@GetMapping("/api/goods/on-sale/{code}")
	WebJsonBean<Void> onSale(@PathVariable("code") String code);

	@GetMapping("/api/goods/off-sale/{code}")
	WebJsonBean<Void> offSale(@PathVariable("code") String code);
}
