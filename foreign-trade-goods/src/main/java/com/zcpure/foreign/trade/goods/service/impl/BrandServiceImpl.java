package com.zcpure.foreign.trade.goods.service.impl;

import com.github.pagehelper.Page;
import com.zcpure.foreign.trade.command.goods.BrandAddCommand;
import com.zcpure.foreign.trade.command.goods.BrandQueryCommand;
import com.zcpure.foreign.trade.goods.dao.entity.BrandEntity;
import com.zcpure.foreign.trade.goods.dao.mapper.BrandMapper;
import com.zcpure.foreign.trade.goods.dao.repository.BrandRepository;
import com.zcpure.foreign.trade.dto.goods.BrandDTO;
import com.zcpure.foreign.trade.goods.service.BrandService;
import com.zcpure.foreign.trade.utils.page.PageBean;
import com.zcpure.foreign.trade.goods.utils.page.PageBeanAssembler;
import com.zcpure.foreign.trade.goods.utils.page.RowBoundsBuilder;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ethan
 * @create_time 2018/10/22 14:13
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {
	@Autowired
	private BrandMapper brandMapper;
	@Autowired
	private BrandRepository brandRepository;

	@Override
	public void add(BrandAddCommand command) {
		if(!brandRepository.existsByName(command.getName())) {
			brandRepository.save(new BrandEntity(command.getName()));
		}
	}

	@Override
	public PageBean<BrandDTO> queryPage(BrandQueryCommand command) {
		RowBounds bounds = RowBoundsBuilder.build(command.getPageNo(), command.getPageSize());
		Page<BrandDTO> result = brandMapper.queryPage(command, bounds);
		return new PageBeanAssembler().toBean(result);
	}

	@Override
	public BrandDTO getById(Long id) {
		BrandEntity entity = brandRepository.findOne(id);
		return BrandEntity.toDTO(entity);
	}
}
