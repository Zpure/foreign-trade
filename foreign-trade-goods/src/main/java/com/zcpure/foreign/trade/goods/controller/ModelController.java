package com.zcpure.foreign.trade.goods.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zcpure.foreign.trade.Const;
import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.ModelAddCommand;
import com.zcpure.foreign.trade.command.goods.ModelQueryCommand;
import com.zcpure.foreign.trade.command.goods.ModelUpdateCommand;
import com.zcpure.foreign.trade.dto.goods.ModelDTO;
import com.zcpure.foreign.trade.goods.dao.mapper.ModelMapper;
import com.zcpure.foreign.trade.goods.service.ModelService;
import com.zcpure.foreign.trade.goods.utils.page.PageBeanAssembler;
import com.zcpure.foreign.trade.goods.utils.page.RowBoundsBuilder;
import com.zcpure.foreign.trade.utils.page.PageBean;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ethan
 * @create_time 2018/10/22 15:32
 */
@RestController
@RequestMapping("/api/model")
public class ModelController {
	@Autowired
	private ModelService modelService;

	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "添加型号")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody ModelAddCommand command) {
		modelService.add(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "更新型号")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public WebJsonBean<Void> update(@RequestBody ModelUpdateCommand command) {
		modelService.update(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "获取型号信息")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public WebJsonBean<PageBean<ModelDTO>> queryByPage(@RequestBody ModelQueryCommand command) {
		PageHelper.startPage(command.getPageNo() != null ? command.getPageNo() : Const.PAGE_DEFAULT_NO,
			command.getPageSize() != null ? command.getPageSize() : Const.PAGE_DEFAULT_SIZE);
		List<ModelDTO> result = modelMapper.queryPage(command);
		return WebJsonBean.SUCCESS(new PageBeanAssembler().toBeanByList(result));
	}
}
