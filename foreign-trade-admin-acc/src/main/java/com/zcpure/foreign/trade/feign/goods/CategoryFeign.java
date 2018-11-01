package com.zcpure.foreign.trade.feign.goods;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.CategoryAddCommand;
import com.zcpure.foreign.trade.dto.goods.CategoryDTO;
import com.zcpure.foreign.trade.dto.goods.CategoryLinkDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "foreign-trade-goods")
public interface CategoryFeign {

	@PostMapping("/api/category/add")
	WebJsonBean<Void> add(@RequestBody CategoryAddCommand command);

	@GetMapping("/api/category/child/{id}")
	WebJsonBean<List<CategoryDTO>> getChild(@PathVariable("id") Long id);

	@GetMapping("/api/category/link/{id}")
	WebJsonBean<CategoryLinkDTO> getLink(@PathVariable("id") Long id);

	@GetMapping("/api/category/link-str/{id}")
	WebJsonBean<String> getLinkStr(@PathVariable("id") Long id);
}
