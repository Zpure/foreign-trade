package com.zcpure.foreign.trade.goods.controller;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.ModelAddCommand;
import com.zcpure.foreign.trade.command.goods.ModelQueryCommand;
import com.zcpure.foreign.trade.command.goods.ModelUpdateCommand;
import com.zcpure.foreign.trade.dto.goods.ModelDTO;
import com.zcpure.foreign.trade.goods.service.ModelService;
import com.zcpure.foreign.trade.utils.page.PageBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ethan
 * @create_time 2018/10/22 15:32
 */
@RestController
@RequestMapping("/api/model")
@CrossOrigin
public class ModelController {
	@Autowired
	private ModelService modelService;

	@ApiOperation(value = "添加型号")
	@RequestMapping(value = "/add")
	public WebJsonBean<Void> add(@RequestBody ModelAddCommand command) {
		modelService.add(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "更新型号")
	@RequestMapping(value = "/update")
	public WebJsonBean<Void> update(@RequestBody ModelUpdateCommand command) {
		modelService.update(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "获取型号信息")
	@RequestMapping(value = "/page")
	public WebJsonBean<PageBean<ModelDTO>> queryByPage(@RequestBody ModelQueryCommand command) {
		PageBean<ModelDTO> result = modelService.queryPage(command);
		return WebJsonBean.SUCCESS(result);
	}
}
