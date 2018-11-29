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

	@PostMapping("/api/goods/page")
	WebJsonBean<PageBean<GoodsDTO>> queryByPage(@RequestBody GoodsQueryCommand command);

	@GetMapping("/api/goods/{code}")
	WebJsonBean<GoodsDTO> getByCode(@PathVariable("code") String code);
}
