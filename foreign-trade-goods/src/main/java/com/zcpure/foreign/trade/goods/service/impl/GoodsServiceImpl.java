package com.zcpure.foreign.trade.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zcpure.foreign.trade.Const;
import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import com.zcpure.foreign.trade.command.goods.GoodsAddCommand;
import com.zcpure.foreign.trade.command.goods.GoodsQueryCommand;
import com.zcpure.foreign.trade.dto.goods.CategoryLinkDTO;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import com.zcpure.foreign.trade.goods.dao.entity.CategoryEntity;
import com.zcpure.foreign.trade.goods.dao.entity.GoodsEntity;
import com.zcpure.foreign.trade.goods.dao.entity.ModelEntity;
import com.zcpure.foreign.trade.goods.dao.mapper.GoodsMapper;
import com.zcpure.foreign.trade.goods.dao.repository.BrandRepository;
import com.zcpure.foreign.trade.goods.dao.repository.CategoryRepository;
import com.zcpure.foreign.trade.goods.dao.repository.GoodsRepository;
import com.zcpure.foreign.trade.goods.dao.repository.ModelRepository;
import com.zcpure.foreign.trade.goods.service.CategoryService;
import com.zcpure.foreign.trade.goods.service.GoodsService;
import com.zcpure.foreign.trade.goods.utils.page.PageBeanAssembler;
import com.zcpure.foreign.trade.goods.utils.page.RowBoundsBuilder;
import com.zcpure.foreign.trade.utils.UniqueNoUtils;
import com.zcpure.foreign.trade.utils.page.PageBean;
import org.apache.commons.lang.Validate;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ethan
 * @create_time 2018/10/22 18:23
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
	@Autowired
	private ModelRepository modelRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private GoodsRepository goodsRepository;

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private CategoryService categoryService;

	@Override
	public void add(GoodsAddCommand command) {
		ModelEntity modelEntity = modelRepository.findOne(command.getModelId());
		Validate.notNull(modelEntity, "型号信息不存在：" + command.getModelId());

		CategoryEntity categoryEntity = categoryRepository.findOne(command.getCategoryId());
		Validate.notNull(categoryEntity, "分类信息不存在：" + command.getCategoryId());
		Validate.isTrue(categoryEntity.getIsDir() != 1, "该分类为目录");

		CategoryLinkDTO categoryLinkDTO = categoryService.getOneLink(command.getCategoryId());

		Validate.isTrue(!goodsRepository.existsByModelIdAndName(command.getModelId(), command.getName()),
			"该型号下已存在商品名：" + command.getName());

		String goodsCode = UniqueNoUtils.next(UniqueNoUtils.UniqueNoType.GC);
		GoodsEntity goodsEntity = GoodsEntity.form(goodsCode, command, modelEntity,
			categoryEntity, categoryLinkDTO.toLinkStr());

		goodsRepository.save(goodsEntity);

	}

	@Override
	public PageBean<GoodsDTO> queryPage(GoodsQueryCommand command) {
		PageHelper.startPage(command.getPageNo() != null ? command.getPageNo() : Const.PAGE_DEFAULT_NO,
			command.getPageSize() != null ? command.getPageSize() : Const.PAGE_DEFAULT_SIZE);
		List<GoodsDTO> result = goodsMapper.queryPage(command);
		return new PageBeanAssembler().toBeanByList(result);
	}

	@Override
	public GoodsDTO getByCode(String code) {
		GoodsEntity goodsEntity = goodsRepository.findOne(code);
		return GoodsEntity.formDTO(goodsEntity);
	}
}
