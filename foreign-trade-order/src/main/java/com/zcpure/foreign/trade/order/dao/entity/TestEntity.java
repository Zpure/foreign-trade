package com.zcpure.foreign.trade.order.dao.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Table(name = "ft_order_test")
@Where(clause = "delete_flag <> 1")
@Entity
@Data
public class TestEntity extends BaseEntity {
	private static final long serialVersionUID = 1111320370190733556L;
	@Id
	@Column(length = 64, unique = true, updatable = false)
	private String code;
	private String name;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "testCode", referencedColumnName = "code")
	private List<TestDetailEntity> detailList;
}
