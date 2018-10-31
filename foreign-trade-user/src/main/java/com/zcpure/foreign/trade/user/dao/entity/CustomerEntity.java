package com.zcpure.foreign.trade.user.dao.entity;

import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import com.zcpure.foreign.trade.command.user.CustomerAddCommand;
import com.zcpure.foreign.trade.dto.user.CustomerDTO;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 管理用户
 */
@Table(name = "ft_customer")
@Where(clause = "delete_flag <> 1")
@Entity
@Data
public class CustomerEntity extends BaseEntity {
	private static final long serialVersionUID = 1111320370190733556L;

	@Id
	@Column(length = 64, unique = true, updatable = false)
	private String code;
	@Column(nullable = false)
	private String name;
	private String phone;
	@Column(nullable = false)
	private String email;
	private String address;
	private String country;
	private String website;
	private String company;
	@Column(nullable = false)
	private Integer priority;
	@Column(nullable = false)
	private String groupCode;
	@Column(nullable = false)
	private String groupName;

	public static CustomerDTO formDTO(CustomerEntity entity) {
		if (entity == null) {
			return null;
		}
		CustomerDTO dto = new CustomerDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public static CustomerEntity form(CustomerAddCommand command) {
		if (command == null) {
			return null;
		}
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		CustomerEntity entity = new CustomerEntity();
		BeanUtils.copyProperties(command, entity);
		entity.setGroupCode(info.getGroupCode());
		entity.setGroupName(info.getGroupName());
		return entity;
	}
}
