package com.zcpure.foreign.trade.user.dao.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 管理用户
 */
@Table(name = "ft_group")
@Where(clause = "delete_flag <> 1")
@Entity
@Data
public class GroupEntity extends BaseEntity {
	private static final long serialVersionUID = 1111320370190733556L;

	@Id
	@Column(length = 64, unique = true, updatable = false)
	private String code;
	private String name;
	private String phone;
	private String email;
	private String address;
	private String remark;
}
