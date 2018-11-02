package com.zcpure.foreign.trade.user.service.impl;

import com.zcpure.foreign.trade.BaseCode;
import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.command.user.SupplierAddCommand;
import com.zcpure.foreign.trade.command.user.SupplierGoodsAddCommand;
import com.zcpure.foreign.trade.command.user.SupplierUpdateCommand;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import com.zcpure.foreign.trade.enums.SupplierGoodsStatusEnum;
import com.zcpure.foreign.trade.enums.SupplierStatusEnum;
import com.zcpure.foreign.trade.user.dao.entity.SupplierEntity;
import com.zcpure.foreign.trade.user.dao.entity.SupplierGoodsEntity;
import com.zcpure.foreign.trade.user.dao.mapper.SupplierMapper;
import com.zcpure.foreign.trade.user.dao.repostitory.SupplierGoodsRepository;
import com.zcpure.foreign.trade.user.dao.repostitory.SupplierRepository;
import com.zcpure.foreign.trade.user.feign.GoodsFeign;
import com.zcpure.foreign.trade.user.service.SupplierService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ethan
 * @create_time 2018/10/31 14:55
 */
@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
	@Autowired
	private SupplierRepository supplierRepository;
	@Autowired
	private SupplierGoodsRepository supplierGoodsRepository;

	@Autowired
	private SupplierMapper supplierMapper;

	@Autowired
	private GoodsFeign goodsFeign;

	@Override
	public void add(SupplierAddCommand command) {
		SupplierEntity existEntity = supplierRepository.findOne(command.getCode());
		Validate.isTrue(existEntity == null,
			"供应商编码已经存在");
		SupplierEntity supplierEntity = SupplierEntity.form(command);
		supplierRepository.save(supplierEntity);
	}

	@Override
	public void update(SupplierUpdateCommand command) {

	}

	@Override
	public void addGoods(SupplierGoodsAddCommand command) {
		SupplierGoodsEntity existSupplierGoodsEntity = supplierGoodsRepository.findBySupplierCodeAndGoodsCode(command.getSupplierCode(), command.getGoodsCode());
		Validate.isTrue(existSupplierGoodsEntity == null,
			"该供应信息已存在");

		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		SupplierEntity supplierEntity = supplierRepository.findOne(command.getSupplierCode());
		Validate.isTrue(supplierEntity != null && supplierEntity.getGroupCode().equals(info.getGroupCode()),
			"供应商不存在：" + command.getSupplierCode());
		WebJsonBean<GoodsDTO> goodsInfo = goodsFeign.getByCode(command.getGoodsCode());
		Validate.isTrue(goodsInfo.getCode() == BaseCode.SUCCESS.getIndex(),
			"获取商品信息错误");

		SupplierGoodsEntity entity = SupplierGoodsEntity.form(supplierEntity, goodsInfo.getData(), command.getPrice());
		supplierGoodsRepository.save(entity);
	}

	@Override
	public void stop(String code) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		SupplierEntity supplierEntity = supplierRepository.findOne(code);
		Validate.isTrue(supplierEntity != null && supplierEntity.getGroupCode().equals(info.getGroupCode()),
			"供应商不存在：" + code);
		supplierEntity.setStatus(SupplierStatusEnum.STOP.getCode());
	}

	@Override
	public void stopGoods(Long id) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		SupplierGoodsEntity entity = supplierGoodsRepository.findOne(id);
		Validate.isTrue(entity != null && entity.getGroupCode().equals(info.getGroupCode()),
			"供应商商品不存在");

		entity.setStatus(SupplierGoodsStatusEnum.STOP.getCode());
		supplierGoodsRepository.save(entity);
	}
}
