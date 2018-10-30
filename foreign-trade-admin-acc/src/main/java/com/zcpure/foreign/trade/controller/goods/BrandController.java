package com.zcpure.foreign.trade.controller.goods;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.BrandAddCommand;
import com.zcpure.foreign.trade.command.goods.BrandQueryCommand;
import com.zcpure.foreign.trade.dto.goods.BrandDTO;
import com.zcpure.foreign.trade.feign.goods.BrandFeign;
import com.zcpure.foreign.trade.utils.page.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@RestController
@RequestMapping("/api/admin/brand")
@CrossOrigin
@Api(value = "品牌")
public class BrandController {
	@Autowired
	private BrandFeign brandFeign;

	@ApiOperation(value = "添加品牌信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody BrandAddCommand command) {
		return brandFeign.add(command);
	}


	@ApiOperation(value = "获取品牌信息")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public WebJsonBean<PageBean<BrandDTO>> queryByPage(@RequestBody BrandQueryCommand command) {
		return brandFeign.queryByPage(command);
	}
}
