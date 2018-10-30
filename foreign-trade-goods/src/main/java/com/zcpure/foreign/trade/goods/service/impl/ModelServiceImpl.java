package com.zcpure.foreign.trade.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zcpure.foreign.trade.command.goods.ModelAddCommand;
import com.zcpure.foreign.trade.command.goods.ModelQueryCommand;
import com.zcpure.foreign.trade.command.goods.ModelUpdateCommand;
import com.zcpure.foreign.trade.goods.dao.entity.BrandEntity;
import com.zcpure.foreign.trade.goods.dao.entity.ModelEntity;
import com.zcpure.foreign.trade.goods.dao.mapper.ModelMapper;
import com.zcpure.foreign.trade.goods.dao.repository.BrandRepository;
import com.zcpure.foreign.trade.goods.dao.repository.ModelRepository;
import com.zcpure.foreign.trade.dto.goods.ModelDTO;
import com.zcpure.foreign.trade.goods.service.ModelService;
import com.zcpure.foreign.trade.utils.page.PageBean;
import com.zcpure.foreign.trade.goods.utils.page.PageBeanAssembler;
import com.zcpure.foreign.trade.goods.utils.page.RowBoundsBuilder;
import org.apache.commons.lang.Validate;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ethan
 * @create_time 2018/10/22 15:34
 */
@Service
@Transactional
public class ModelServiceImpl implements ModelService {
	@Autowired
	private ModelRepository modelRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private BrandRepository brandRepository;

	@Override
	public void add(ModelAddCommand command) {
		BrandEntity brandEntity = brandRepository.findOne(command.getBrandId());
		Validate.notNull(brandEntity,
			"品牌不存在：" + command.getBrandId());
		Validate.isTrue(!modelRepository.existsByBrandIdAndName(brandEntity.getId(), command.getName()),
			"该品牌下的型号已存在");
		ModelEntity modelEntity = ModelEntity.formAdd(command, brandEntity);
		modelRepository.save(modelEntity);
	}

	@Override
	public void update(ModelUpdateCommand command) {
		ModelEntity modelEntity = modelRepository.findOne(command.getId());
		Validate.notNull(modelEntity, "型号不存在：" + command.getId());
		modelEntity.fillUpdateInfo(command);
		modelRepository.save(modelEntity);
	}

	@Override
	public PageBean<ModelDTO> queryPage(ModelQueryCommand command) {
		PageHelper.startPage(command.getPageNo(), command.getPageSize());
		List<ModelDTO> result = modelMapper.queryPage(command);
		return new PageBeanAssembler().toBeanByList(result);
	}
}
