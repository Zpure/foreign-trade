package com.zcpure.foreign.trade.user.dao.entity;

import com.zcpure.foreign.trade.dto.user.UserDTO;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

/**
 * 管理用户
 */
@Table(name = "ft_user")
@Where(clause = "delete_flag <> 1")
@Entity
@Data
public class UserEntity extends BaseEntity {
	private static final long serialVersionUID = 1111320370190733556L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String groupCode;
	private String groupName;
	private String name;
	@Column(unique = true)
	private String phone;
	private String password;
	private String salt;
	private String email;
	private String address;
	private Integer userLevel;

	public static UserDTO form(UserEntity entity) {
		if (entity == null) {
			return null;
		}
		UserDTO dto = new UserDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
}
