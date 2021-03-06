package com.zcpure.foreign.trade.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcpure.foreign.trade.*;
import com.zcpure.foreign.trade.command.order.*;
import com.zcpure.foreign.trade.dto.goods.GoodsDTO;
import com.zcpure.foreign.trade.dto.order.OrderDTO;
import com.zcpure.foreign.trade.dto.order.OrderDetailDTO;
import com.zcpure.foreign.trade.dto.order.OrderDisDetailDTO;
import com.zcpure.foreign.trade.dto.user.CustomerDTO;
import com.zcpure.foreign.trade.dto.user.SupplierDTO;
import com.zcpure.foreign.trade.enums.OrderStatusEnum;
import com.zcpure.foreign.trade.order.dao.entity.OrderDetailEntity;
import com.zcpure.foreign.trade.order.dao.entity.OrderDisDetailEntity;
import com.zcpure.foreign.trade.order.dao.entity.OrderEntity;
import com.zcpure.foreign.trade.order.dao.mapper.OrderDetailMapper;
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
import java.util.*;
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
	private OrderDetailMapper orderDetailMapper;

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
		Set<String> goodsCodeSet = new HashSet<>();
		command.getDetailList().forEach(item -> {
			Validate.isTrue(!goodsCodeSet.contains(item.getGoodsCode()), "下单商品重复：" + item.getGoodsCode());
			goodsCodeSet.add(item.getGoodsCode());
		});

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
			.mapToDouble(item -> item.getPrice().multiply(new BigDecimal(item.getNum())).doubleValue()).sum()));
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
	public PageBean<OrderDetailDTO> queryDetailPage(OrderDetailQueryCommand command) {
		PageHelper.startPage(command.getPageNo() != null ? command.getPageNo() : Const.PAGE_DEFAULT_NO,
			command.getPageSize() != null ? command.getPageSize() : Const.PAGE_DEFAULT_SIZE);
		List<OrderDetailDTO> result = orderDetailMapper.queryPage(command);
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
				Optional<OrderDetailEntity> findEntityOp = detailEntityList.stream()
					.filter(i -> i.getGoodsCode().equals(item.getGoodsCode()))
					.findFirst();
				if (findEntityOp.isPresent()) {
					findEntityOp.get().setNum(item.getBuyNum());
					findEntityOp.get().setPrice(item.getPrice());
				} else {
					newAdd.add(item);
				}
			});
			List<OrderDetailEntity> newAddEntity = getOrderDetail(newAdd);
			newAddEntity.forEach(item -> item.setOrderCode(command.getCode()));
			detailEntityList.addAll(newAddEntity);
			orderEntity.setTotalNum(detailEntityList.stream()
				.mapToInt(item -> item.getNum()).sum());
			orderEntity.setTotalAmount(new BigDecimal(detailEntityList.stream()
				.mapToDouble(item -> item.getPrice().multiply(new BigDecimal(item.getNum())).doubleValue()).sum()));
			orderRepository.save(orderEntity);
		}
	}

	@Override
	public void updateStatus(String code, OrderStatusEnum status) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		OrderEntity orderEntity = orderRepository.findOne(code);
		Validate.isTrue(orderEntity != null && orderEntity.getGroupCode().equals(info.getGroupCode()),
			"订单不存在");
		switch (status) {
			case CONFIRM:{
				Validate.isTrue(orderEntity.getStatus() == OrderStatusEnum.INIT.getCode(),
					"订单状态不能确认");
				break;
			}
			case DISTRIBUTION:{
				Validate.isTrue(orderEntity.getStatus() == OrderStatusEnum.CONFIRM.getCode(),
					"订单状态不能配货完成");
				Validate.isTrue(orderEntity.getTotalDisNum() >= orderEntity.getTotalNum(),
					"订单还没有配货完");
				break;
			}
			case DELIVERY:{
				Validate.isTrue(orderEntity.getStatus() == OrderStatusEnum.DISTRIBUTION.getCode(),
					"订单状态不能发货");
				break;
			}
			case RECEIPT:{
				Validate.isTrue(orderEntity.getStatus() == OrderStatusEnum.DELIVERY.getCode(),
					"订单状态不能收货");
				break;
			}
			case SUCCESS:{
				Validate.isTrue(orderEntity.getStatus() == OrderStatusEnum.RECEIPT.getCode(),
					"订单状态不能完成");
				break;
			}
			default: break;
		}
		orderEntity.setStatus(status.getCode());
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
					Optional<OrderDetailEntity> findDetailEntityOp = detailEntityList.stream()
						.filter(i -> i.getGoodsCode().equals(goodsCode))
						.findFirst();
					if (findDetailEntityOp.isPresent()) {
						OrderDetailEntity findDetailEntity = findDetailEntityOp.get();
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
							// 移除已分配的配送
							Set<String> disSupplierCodeSet = dataList.stream()
								.map(OrderDistributionDetailCommand::getSupplierCode)
								.collect(Collectors.toSet());
							for (OrderDisDetailEntity orderDisDetailEntity : disDetailEntityList) {
								if (!disSupplierCodeSet.contains(orderDisDetailEntity.getSupplierCode())) {
									disDetailEntityList.remove(orderDisDetailEntity);
								}
							}

							List<OrderDistributionDetailCommand> newAdd = new ArrayList<>();
							dataList.forEach(item -> {
								Optional<OrderDisDetailEntity> findDisDetailEntityOp = disDetailEntityList
									.stream()
									.filter(i -> i.getSupplierCode().equals(item.getSupplierCode()))
									.findFirst();
								if (findDisDetailEntityOp.isPresent()) {
									findDisDetailEntityOp.get().setInitDisNum(item.getNum());
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
	public List<OrderDisDetailDTO> distributionDetail(OrderDistributionDetailQueryCommand command) {
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		OrderDetailEntity orderDetailEntity = orderDetailRepository.getByOrderCodeAndGoodsCode(command.getOrderCode(), command.getGoodsCode());
		Validate.isTrue(orderDetailEntity != null && orderDetailEntity.getGroupCode().equals(info.getGroupCode()),
			"订单详情信息不存在");
		return orderDetailEntity.getDisDetailEntityList()
			.stream()
			.map(OrderDisDetailEntity::form)
			.collect(Collectors.toList());
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
			return OrderDetailEntity.form(goods, item);
		}).filter(item -> item != null).collect(Collectors.toList());
	}
}
