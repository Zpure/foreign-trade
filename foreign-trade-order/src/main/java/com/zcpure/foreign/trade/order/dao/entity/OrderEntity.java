package com.zcpure.foreign.trade.order.dao.entity;

import com.zcpure.foreign.trade.dto.order.OrderDTO;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Table(name = "ft_order")
@Where(clause = "delete_flag <> 1")
@Entity
@Data
public class OrderEntity extends BaseEntity {
	private static final long serialVersionUID = 1111320370190733556L;
	@Id
	@Column(length = 64, unique = true, updatable = false)
	private String code;
	private String groupCode;
	private String groupName;
	private String customerCode;
	private String customerName;
	private String name;
	private BigDecimal totalAmount;
	private Integer totalNum;
	private Integer totalInitDisNum = 0;
	private Integer totalDisNum = 0;
	private Integer status;
	private String remark;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "orderCode", referencedColumnName = "code", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
	private List<OrderDetailEntity> detailEntityList;

	public static OrderDTO form(OrderEntity entity) {
		if (entity == null) {
			return null;
		}
		OrderDTO dto = new OrderDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
}
