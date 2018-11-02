package com.zcpure.foreign.trade.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcpure.foreign.trade.*;
import com.zcpure.foreign.trade.command.order.*;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import com.zcpure.foreign.trade.dto.order.OrderDTO;
import com.zcpure.foreign.trade.dto.user.CustomerDTO;
import com.zcpure.foreign.trade.dto.user.SupplierDTO;
import com.zcpure.foreign.trade.enums.OrderStatusEnum;
import com.zcpure.foreign.trade.order.dao.entity.OrderDetailEntity;
import com.zcpure.foreign.trade.order.dao.entity.OrderDisDetailEntity;
import com.zcpure.foreign.trade.order.dao.entity.OrderEntity;
import com.zcpure.foreign.trade.order.dao.mapper.OrderMapper;
import com.zcpure.foreign.trade.order.dao.repository.OrderDetailRepository;
import com.zcpure.foreign.trade.order.dao.repository.OrderDisDetailRepository;
import com.zcpure.foreign.trade.order.dao.repository.OrderRepository;
import com.zcpure.foreign.trade.order.feign.CustomerFeign;
import com.zcpure.foreign.trade.order.feign.GoodsFeign;
import com.zcpure.foreign.trade.order.feign.SupplierFeign;
import com.zcpure.foreign.trade.order.service.OrderService;
import com.zcpure.foreign.trade.order.utils.page.PageBeanAssembler;
import com.zcpure.foreign.trade.utils.UniqueNoUtils;
import com.zcpure.foreign.trade.utils.page.PageBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ethan
 * @create_time 2018/10/26 13:54
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private GoodsFeign goodsFeign;

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private OrderDisDetailRepository orderDisDetailRepository;

	@Autowired
	private CustomerFeign customerFeign;
	@Autowired
	private SupplierFeign supplierFeign;

	@Override
	public void add(OrderAddCommand command) {
		Validate.isTrue(command.getDetailList() != null && command.getDetailList().size() > 0,
			"下单商品信息不存在");

		String orderCode = UniqueNoUtils.next(UniqueNoUtils.UniqueNoType.SO);

		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		Validate.notNull(info, "用户信息获取失败");

		WebJsonBean<CustomerDTO> customerResult = customerFeign.getByCode(command.getCustomerCode());
		Validate.isTrue(customerResult.getCode() == BaseCode.SUCCESS.getIndex(),
			"获取客户信息失败");
		CustomerDTO customer = customerResult.getData();
		Validate.isTrue(customer != null && customer.getGroupCode().equals(info.getGroupCode()),
			"客户信息不存在");

		OrderEntity orderEntity = new OrderEntity();
		List<OrderDetailEntity> orderDetailList = getOrderDetail(command.getDetailList());
		orderDetailList.forEach(item -> item.setOrderCode(orderCode));

		orderEntity.setCode(orderCode);
		orderEntity.setGroupCode(info.getGroupCode());
		orderEntity.setGroupName(info.getGroupName());
		orderEntity.setName(command.getName());
		orderEntity.setRemark(command.getRemark());
		orderEntity.setStatus(OrderStatusEnum.INIT.getCode());
		orderEntity.setTotalNum(orderDetailList.stream().mapToInt(OrderDetailEntity::getNum).sum());
		orderEntity.setTotalAmount(new BigDecimal(orderDetailList.stream()
			.mapToDouble(item -> item.getSalePrice().multiply(new BigDecimal(item.getNum())).doubleValue()).sum()));
		orderEntity.setDetailEntityList(orderDetailList);
		orderEntity.setCustomerCode(customer.getCode());
		orderEntity.setCustomerName(customer.getName());

		orderRepository.save(orderEntity);
	}

	@Override
	public PageBean<OrderDTO> queryPage(OrderQueryCommand command) {
		PageHelper.startPage(command.getPageNo() != null ? command.getPageNo() : Const.PAGE_DEFAULT_NO,
			command.getPageSize() != null ? command.getPageSize() : Const.PAGE_DEFAULT_SIZE);
		List<OrderDTO> result = orderMapper.queryPage(command);
		return new PageBeanAssembler().toBeanByList(result);
	}

	@Override
	public OrderDTO getDetail(String code) {
		OrderEntity orderEntity = orderRepository.findOne(code);
		OrderDTO orderDTO = OrderEntity.form(orderEntity);
		orderDTO.setDetailList(orderEntity.getDetailEntityList().stream()
			.map(OrderDetailEntity::form).collect(Collectors.toList()));
		return orderDTO;
	}

	@Override
	public void update(OrderUpdateCommand command) {
		OrderEntity orderEntity = orderRepository.findOne(command.getCode());
		Validate.notNull(orderEntity, "订单不存在：" + command.getCode());
		if (StringUtils.isNotBlank(command.getRemark())) {
			orderEntity.setRemark(command.getRemark());
		}
		if (command.getDetailList() != null && command.getDetailList().size() > 0) {
			List<OrderAddDetailCommand> newAdd = new ArrayList<>();
			List<OrderDetailEntity> detailEntityList = orderEntity.getDetailEntityList();
			command.getDetailList().forEach(item -> {
				OrderDetailEntity findEntity = detailEntityList.stream()
					.filter(i -> i.getGoodsCode().equals(item.getGoodsCode()))
					.findFirst()
					.orElseGet(null);
				if (findEntity != null) {
					findEntity.setNum(item.getBuyNum());
				} else {
					newAdd.add(item);
				}
			});
			List<OrderDetailEntity> newAddEntity = getOrderDetail(newAdd);
			newAddEntity.forEach(item -> item.setOrderCode(command.getCode()));
			detailEntityList.addAll(newAddEntity);
			orderRepository.save(orderEntity);
		}
	}

	@Override
	public void confirm(String code) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		OrderEntity orderEntity = orderRepository.findOne(code);
		Validate.isTrue(orderEntity != null && orderEntity.getGroupCode().equals(info.getGroupCode()),
			"订单不存在");
		Validate.isTrue(orderEntity.getStatus() == OrderStatusEnum.INIT.getCode(),
			"订单状态不能确认");
		orderEntity.setStatus(OrderStatusEnum.CONFIRM.getCode());
		orderRepository.save(orderEntity);
	}

	@Override
	public void distribution(OrderDistributionCommand command) {
		OrderEntity orderEntity = orderRepository.findOne(command.getCode());
		Validate.notNull(orderEntity, "订单不存在：" + command.getCode());
		Validate.isTrue(orderEntity.getStatus().equals(OrderStatusEnum.CONFIRM.getCode()),
			"订单不在确认状态：" + orderEntity.getStatus());

		if (command.getDetailList() != null && command.getDetailList().size() > 0) {
			String codes = command.getDetailList().stream()
				.map(OrderDistributionDetailCommand::getSupplierCode)
				.collect(Collectors.joining(","));
			WebJsonBean<List<SupplierDTO>> supplierResult = supplierFeign.batchByCodes(codes);
			Validate.isTrue(supplierResult.getCode() == BaseCode.SUCCESS.getIndex());
			Map<String, SupplierDTO> supplierMap = supplierResult.getData()
				.stream()
				.collect(Collectors.toMap(SupplierDTO::getCode, v -> v));

			List<OrderDetailEntity> detailEntityList = orderEntity.getDetailEntityList();
			command.getDetailList().stream()
				.collect(Collectors.groupingBy(OrderDistributionDetailCommand::getGoodsCode))
				.forEach((goodsCode, dataList) -> {
					OrderDetailEntity findDetailEntity = detailEntityList.stream()
						.filter(i -> i.getGoodsCode().equals(goodsCode))
						.findFirst()
						.orElseGet(null);
					if (findDetailEntity != null) {
						List<OrderDisDetailEntity> disDetailEntityList = findDetailEntity.getDisDetailEntityList();
						if (disDetailEntityList == null) {
							findDetailEntity.setDisDetailEntityList(
								dataList.stream()
									.map(item -> {
										SupplierDTO supplier = supplierMap.get(item.getSupplierCode());
										Validate.notNull(supplier, "供应商不存在：" + item.getSupplierCode());
										return OrderDisDetailEntity.form(findDetailEntity, supplier, item.getNum());
									}).collect(Collectors.toList())
							);
						} else {
							List<OrderDistributionDetailCommand> newAdd = new ArrayList<>();
							dataList.forEach(item -> {
								OrderDisDetailEntity findDisDetailEntity = disDetailEntityList
									.stream()
									.filter(i -> i.getSupplierCode().equals(item.getSupplierCode()))
									.findFirst()
									.orElseGet(null);
								if (findDisDetailEntity != null) {
									findDisDetailEntity.setInitDisNum(item.getNum());
								} else {
									newAdd.add(item);
								}
								disDetailEntityList.addAll(
									newAdd.stream()
										.map(addItem -> {
											SupplierDTO supplier = supplierMap.get(item.getSupplierCode());
											Validate.notNull(supplier, "供应商不存在：" + addItem.getSupplierCode());
											return OrderDisDetailEntity.form(findDetailEntity, supplier, addItem.getNum());
										})
										.collect(Collectors.toList())
								);
							});

						}
						findDetailEntity.setInitDisNum(findDetailEntity.getDisDetailEntityList()
							.stream().mapToInt(OrderDisDetailEntity::getInitDisNum).sum());
						findDetailEntity.setDisNum(findDetailEntity.getDisDetailEntityList()
							.stream().mapToInt(OrderDisDetailEntity::getDisNum).sum());
					}
				});
			orderEntity.setTotalInitDisNum(detailEntityList.stream().mapToInt(OrderDetailEntity::getInitDisNum).sum());
			orderEntity.setTotalDisNum(detailEntityList.stream().mapToInt(OrderDetailEntity::getDisNum).sum());
			orderRepository.save(orderEntity);
		}
	}

	@Override
	public void distributionUpdate(OrderDistributionUpdateCommand command) {
		OrderDisDetailEntity entity = orderDisDetailRepository.getByOrderCodeAndGoodsCodeAndSupplierCode(
			command.getOrderCode(),
			command.getGoodsCode(),
			command.getSupplierCode());
		if (entity != null) {
			Integer updateNum = command.getNum() - entity.getDisNum();
			entity.setDisNum(command.getNum());
			orderDisDetailRepository.save(entity);

			OrderDetailEntity orderDetailEntity = orderDetailRepository.getByOrderCodeAndGoodsCode(
				command.getOrderCode(),
				command.getGoodsCode());
			orderDetailEntity.setDisNum(orderDetailEntity.getDisNum() + updateNum);

			OrderEntity orderEntity = orderRepository.findOne(command.getOrderCode());
			orderEntity.setTotalDisNum(orderEntity.getTotalDisNum() + updateNum);

			orderDetailRepository.save(orderDetailEntity);
			orderRepository.save(orderEntity);
		}

	}

	private List<OrderDetailEntity> getOrderDetail(List<OrderAddDetailCommand> detailList) {
		String goodsCodes = detailList.stream().map(OrderAddDetailCommand::getGoodsCode)
			.collect(Collectors.joining(","));
		WebJsonBean<List<GoodsDTO>> goodsResult = goodsFeign.getBatchByCode(goodsCodes);
		Validate.isTrue(goodsResult.getCode() == BaseCode.SUCCESS.getIndex(),
			"获取商品信息失败");
		Map<String, GoodsDTO> goodsMap = goodsResult.getData()
			.stream()
			.collect(Collectors.toMap(GoodsDTO::getCode, item -> item));
		return detailList.stream().map(item -> {
			GoodsDTO goods = goodsMap.getOrDefault(item.getGoodsCode(), null);
			return OrderDetailEntity.form(goods, item.getBuyNum());
		}).filter(item -> item != null).collect(Collectors.toList());
	}
}
