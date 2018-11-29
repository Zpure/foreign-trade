package com.zcpure.foreign.trade.user.dao.entity;

import com.zcpure.foreign.trade.command.user.CustomerMsgAddCommand;
import com.zcpure.foreign.trade.enums.CustomerMsgStatusEnum;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

/**
 * 管理用户
 */
@Table(name = "ft_customer_msg")
@Where(clause = "delete_flag <> 1")
@Entity
@Data
public class CustomerMsgEntity extends BaseEntity {
	private static final long serialVersionUID = 1111320370190733556L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 19)
	private Long id;
	@Column(nullable = false)
	private String name;
	private String phone;
	@Column(nullable = false)
	private String email;
	private String ip;
	private String msg;
	@Column(nullable = false)
	private String groupCode;
	@Column(nullable = false)
	private String groupName;
	private Integer status;

	public static CustomerMsgEntity form(CustomerMsgAddCommand command, GroupEntity groupEntity) {
		if (command == null) {
			return null;
		}
		CustomerMsgEntity entity = new CustomerMsgEntity();
		BeanUtils.copyProperties(command, entity);
		entity.setGroupCode(groupEntity.getCode());
		entity.setGroupName(groupEntity.getName());
		entity.setStatus(CustomerMsgStatusEnum.SUBMIT.getCode());
		return entity;
	}
}
