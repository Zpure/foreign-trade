package com.zcpure.foreign.trade.order.dao.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Table(name = "ft_order_detail_test")
@Where(clause = "delete_flag <> 1")
@Entity
@Data
public class TestDetailEntity extends BaseEntity {
	private static final long serialVersionUID = 1111320370190733556L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String testCode;
	private String name;
}
