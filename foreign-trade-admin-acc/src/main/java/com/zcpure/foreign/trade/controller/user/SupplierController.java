package com.zcpure.foreign.trade.controller.user;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.SupplierAddCommand;
import com.zcpure.foreign.trade.command.user.SupplierGoodsAddCommand;
import com.zcpure.foreign.trade.command.user.SupplierQueryCommand;
import com.zcpure.foreign.trade.dto.user.SupplierDTO;
import com.zcpure.foreign.trade.feign.user.SupplierFeign;
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
@RequestMapping("/api、admin/supplier")
@Api(value = "客户")
public class SupplierController {
	@Autowired
	private SupplierFeign supplierFeign;

	@ApiOperation(value = "添加供应商信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WebJsonBean<Void> add(@RequestBody SupplierAddCommand command) {
		return supplierFeign.add(command);
	}

	@ApiOperation(value = "添加供应商商品信息")
	@RequestMapping(value = "/add-goods", method = RequestMethod.POST)
	public WebJsonBean<Void> addGoods(@RequestBody SupplierGoodsAddCommand command) {
		return supplierFeign.addGoods(command);
	}

	@ApiOperation(value = "供应商信息")
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public WebJsonBean<SupplierDTO> getByCode(@PathVariable("code") String code) {
		return supplierFeign.getByCode(code);
	}

	@ApiOperation(value = "供应商信息")
	@RequestMapping(value = "/batch", method = RequestMethod.GET)
	public WebJsonBean<SupplierDTO> batchByCodes(@RequestParam("codes") String codes) {
		return supplierFeign.batchByCodes(codes);
	}

	@ApiOperation(value = "供应商详情")
	@RequestMapping(value = "/detail/{code}", method = RequestMethod.GET)
	public WebJsonBean<SupplierDTO> getDetailByCode(@PathVariable("code") String code) {
		return supplierFeign.getDetailByCode(code);
	}

	@ApiOperation(value = "供应商停产")
	@RequestMapping(value = "/stop/{code}", method = RequestMethod.POST)
	public WebJsonBean<Void> stop(@PathVariable String code) {
		return supplierFeign.stop(code);
	}

	@ApiOperation(value = "供应商停产")
	@RequestMapping(value = "/stop/goods/{id}", method = RequestMethod.POST)
	public WebJsonBean<Void> stopGoods(@PathVariable Long id) {
		return supplierFeign.stopGoods(id);
	}

	@ApiOperation(value = "获取供应商列表信息")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public WebJsonBean<PageBean<SupplierDTO>> queryByPage(@RequestBody SupplierQueryCommand command) {
		return supplierFeign.queryByPage(command);
	}

}
