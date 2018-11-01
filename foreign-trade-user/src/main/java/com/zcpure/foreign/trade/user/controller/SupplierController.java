package com.zcpure.foreign.trade.user.controller;

import com.github.pagehelper.PageHelper;
import com.zcpure.foreign.trade.*;
import com.zcpure.foreign.trade.command.user.SupplierAddCommand;
import com.zcpure.foreign.trade.command.user.SupplierGoodsAddCommand;
import com.zcpure.foreign.trade.command.user.SupplierQueryCommand;
import com.zcpure.foreign.trade.dto.user.SupplierDTO;
import com.zcpure.foreign.trade.user.dao.entity.SupplierEntity;
import com.zcpure.foreign.trade.user.dao.entity.SupplierGoodsEntity;
import com.zcpure.foreign.trade.user.dao.mapper.SupplierMapper;
import com.zcpure.foreign.trade.user.dao.repostitory.SupplierGoodsRepository;
import com.zcpure.foreign.trade.user.dao.repostitory.SupplierRepository;
import com.zcpure.foreign.trade.user.service.SupplierService;
import com.zcpure.foreign.trade.user.utils.page.PageBeanAssembler;
import com.zcpure.foreign.trade.utils.page.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@RestController
@RequestMapping("/api/supplier")
@Api(value = "客户")
public class SupplierController {
	@Autowired
	private SupplierMapper supplierMapper;
	@Autowired
	private SupplierRepository supplierRepository;
	@Autowired
	private SupplierGoodsRepository supplierGoodsRepository;

	@Autowired
	private SupplierService supplierService;

	@ApiOperation(value = "添加供应商信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody SupplierAddCommand command) {
		supplierService.add(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "添加供应商商品信息")
	@RequestMapping(value = "/add-goods", method = RequestMethod.POST)
	public WebJsonBean<Void> addGoods(@RequestBody SupplierGoodsAddCommand command) {
		supplierService.addGoods(command);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "供应商信息")
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public WebJsonBean<SupplierDTO> getByCode(@PathVariable("code") String code) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		SupplierEntity supplierEntity = supplierRepository.findOne(code);
		if(supplierEntity == null || !supplierEntity.getGroupCode().equals(info.getGroupCode())) {
			return new WebJsonBean<>(BaseCode.FAIL);
		}
		SupplierDTO dto = SupplierEntity.formDTO(supplierEntity);
		return WebJsonBean.SUCCESS(dto);
	}

	@ApiOperation(value = "供应商信息")
	@RequestMapping(value = "/batch", method = RequestMethod.GET)
	public WebJsonBean<SupplierDTO> batchByCodes(@RequestParam("codes") String codes) {
		List<String> codeList = Arrays.asList(codes.split(","));
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		List<SupplierEntity> supplierEntityList = supplierRepository.findByCodeIn(codeList);
		if(supplierEntityList == null || supplierEntityList.stream()
			.filter(item -> !item.getGroupCode().equals(info.getGroupCode()))
			.findFirst()
			.isPresent()) {
			return new WebJsonBean<>(BaseCode.FAIL);
		}
		return WebJsonBean.SUCCESS(supplierEntityList.stream().map(SupplierEntity::formDTO).collect(Collectors.toList()));
	}

	@ApiOperation(value = "供应商详情")
	@RequestMapping(value = "/detail/{code}", method = RequestMethod.GET)
	public WebJsonBean<SupplierDTO> getDetailByCode(@PathVariable("code") String code) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		SupplierEntity supplierEntity = supplierRepository.findOne(code);
		if(supplierEntity == null || supplierEntity.getGroupCode() != info.getGroupCode()) {
			return new WebJsonBean<>(BaseCode.FAIL);
		}
		List<SupplierGoodsEntity> detailList = supplierGoodsRepository.findBySupplierCode(supplierEntity.getCode());
		SupplierDTO dto = SupplierEntity.formDTO(supplierEntity);
		if(detailList != null) {
			dto.setDetailList(detailList.stream().map(SupplierGoodsEntity::formDTO).collect(Collectors.toList()));
		}
		return WebJsonBean.SUCCESS(dto);
	}

	@ApiOperation(value = "供应商停产")
	@RequestMapping(value = "/stop/{code}", method = RequestMethod.POST)
	public WebJsonBean<Void> stop(@PathVariable String code) {
		supplierService.stop(code);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "供应商停产")
	@RequestMapping(value = "/stop/goods/{id}", method = RequestMethod.POST)
	public WebJsonBean<Void> stopGoods(@PathVariable Long id) {
		supplierService.stopGoods(id);
		return WebJsonBean.SUCCESS();
	}

	@ApiOperation(value = "获取供应商列表信息")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public WebJsonBean<PageBean<SupplierDTO>> queryByPage(@RequestBody SupplierQueryCommand command) {
		PageHelper.startPage(command.getPageNo() != null ? command.getPageNo() : Const.PAGE_DEFAULT_NO,
			command.getPageSize() != null ? command.getPageSize() : Const.PAGE_DEFAULT_SIZE);
		List<SupplierDTO> result = supplierMapper.queryPage(command);
		return WebJsonBean.SUCCESS(new PageBeanAssembler().toBeanByList(result));
	}

}
