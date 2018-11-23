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

import java.util.List;
import java.util.Optional;

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

		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		SupplierEntity supplierEntity = supplierRepository.findOne(command.getSupplierCode());
		Validate.isTrue(supplierEntity != null && supplierEntity.getGroupCode().equals(info.getGroupCode()),
			"供应商不存在：" + command.getSupplierCode());

		List<SupplierGoodsEntity> supplierGoodsEntityList = supplierEntity.getDetailList();
		command.getDetailList().forEach(detail -> {
			Optional<SupplierGoodsEntity> opSupplierGoods = supplierGoodsEntityList.stream()
				.filter(item -> item.getGoodsCode().equals(detail.getGoodsCode()))
				.findFirst();
			if (opSupplierGoods.isPresent()) {
				opSupplierGoods.get().setPrice(detail.getPrice());
			} else {
				WebJsonBean<GoodsDTO> goodsInfo = goodsFeign.getByCode(detail.getGoodsCode());
				Validate.isTrue(goodsInfo.getCode() == BaseCode.SUCCESS.getIndex(),
					"获取商品信息错误");

				SupplierGoodsEntity entity = SupplierGoodsEntity.form(supplierEntity, goodsInfo.getData(), detail.getPrice());
				supplierGoodsEntityList.add(entity);
			}

		});

		supplierRepository.save(supplierEntity);
	}

	@Override
	public void updateStatus(String code, SupplierStatusEnum status) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		SupplierEntity supplierEntity = supplierRepository.findOne(code);
		Validate.isTrue(supplierEntity != null && supplierEntity.getGroupCode().equals(info.getGroupCode()),
			"供应商不存在：" + code);
		supplierEntity.setStatus(status.getCode());
		supplierEntity.getDetailList().forEach(item -> {
			if(SupplierStatusEnum.NORMAL.equals(status)) {
				item.setStatus(SupplierGoodsStatusEnum.NORMAL.getCode());
			}
			if(SupplierStatusEnum.STOP.equals(status)) {
				item.setStatus(SupplierGoodsStatusEnum.STOP.getCode());
			}
		});

		supplierRepository.save(supplierEntity);
	}

	@Override
	public void updateStatusGoods(Long id, SupplierGoodsStatusEnum status) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		SupplierGoodsEntity entity = supplierGoodsRepository.findOne(id);
		Validate.isTrue(entity != null && entity.getGroupCode().equals(info.getGroupCode()),
			"供应商商品不存在");

		SupplierEntity supplierEntity = supplierRepository.findOne(entity.getSupplierCode());
		Validate.isTrue(supplierEntity != null && supplierEntity.getStatus() == SupplierStatusEnum.NORMAL.getCode(),
			"供应商状态错误");
		entity.setStatus(status.getCode());
		supplierGoodsRepository.save(entity);
	}
}
