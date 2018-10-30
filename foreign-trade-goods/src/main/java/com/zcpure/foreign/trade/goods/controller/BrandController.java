package com.zcpure.foreign.trade.goods.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.goods.BrandAddCommand;
import com.zcpure.foreign.trade.command.goods.BrandQueryCommand;
import com.zcpure.foreign.trade.dto.goods.BrandDTO;
import com.zcpure.foreign.trade.goods.dao.entity.BrandEntity;
import com.zcpure.foreign.trade.goods.dao.mapper.BrandMapper;
import com.zcpure.foreign.trade.goods.dao.repository.BrandRepository;
import com.zcpure.foreign.trade.goods.service.BrandService;
import com.zcpure.foreign.trade.goods.utils.page.PageBeanAssembler;
import com.zcpure.foreign.trade.goods.utils.page.RowBoundsBuilder;
import com.zcpure.foreign.trade.utils.page.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@RestController
@RequestMapping("/api/brand")
@Api(value = "品牌")
public class BrandController {
	@Autowired
	private BrandService brandService;

	@Autowired
	private BrandMapper brandMapper;
	@Autowired
	private BrandRepository brandRepository;

	@ApiOperation(value = "添加品牌信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody BrandAddCommand command) {
		brandService.add(command);
		return WebJsonBean.SUCCESS();
	}


	@ApiOperation(value = "获取品牌信息")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public WebJsonBean<PageBean<BrandDTO>> queryByPage(@RequestBody BrandQueryCommand command) {
		PageHelper.startPage(command.getPageNo(), command.getPageSize());
		List<BrandDTO> result = brandMapper.queryPage(command);
		return WebJsonBean.SUCCESS(new PageBeanAssembler().toBeanByList(result));
	}

	@ApiOperation(value = "获取品牌信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public WebJsonBean<BrandDTO> getById(@PathVariable("id") Long id) {
		BrandEntity entity = brandRepository.findOne(id);
		BrandDTO result = BrandEntity.toDTO(entity);
		return WebJsonBean.SUCCESS(result);
	}
}
