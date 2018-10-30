package com.zcpure.foreign.trade.controller.goods;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.ModelAddCommand;
import com.zcpure.foreign.trade.command.goods.ModelQueryCommand;
import com.zcpure.foreign.trade.command.goods.ModelUpdateCommand;
import com.zcpure.foreign.trade.dto.goods.ModelDTO;
import com.zcpure.foreign.trade.feign.goods.ModelFeign;
import com.zcpure.foreign.trade.utils.page.PageBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ethan
 * @create_time 2018/10/22 15:32
 */
@RestController
@RequestMapping("/api/model")
public class ModelController {
	@Autowired
	private ModelFeign modelFeign;

	@ApiOperation(value = "添加型号")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody ModelAddCommand command) {
		return modelFeign.add(command);
	}

	@ApiOperation(value = "更新型号")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public WebJsonBean<Void> update(@RequestBody ModelUpdateCommand command) {
		return modelFeign.update(command);
	}

	@ApiOperation(value = "获取型号信息")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public WebJsonBean<PageBean<ModelDTO>> queryByPage(@RequestBody ModelQueryCommand command) {
		return modelFeign.queryByPage(command);
	}
}
