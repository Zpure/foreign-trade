package com.zcpure.foreign.trade.user.dao.entity;

import com.zcpure.foreign.trade.Const;
import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.command.user.UserAddCommand;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import com.zcpure.foreign.trade.enums.UserLevelEnum;
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

	public static UserDTO formDTO(UserEntity entity) {
		if (entity == null) {
			return null;
		}
		UserDTO dto = new UserDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public static UserEntity form(RequestThroughInfo info, UserAddCommand command, GroupEntity groupEntity) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(command, userEntity);
		userEntity.setPassword(Const.DEFAULT_PASSWORD);
		userEntity.setGroupCode(info.getGroupCode());
		userEntity.setGroupName(info.getGroupName());
		userEntity.setUserLevel(UserLevelEnum.GROUP_NORMAL.getCode());
		userEntity.setSalt("1");
		if(info.getUserLevel() == UserLevelEnum.ADMIN.getCode()) {
			userEntity.setGroupCode(groupEntity.getCode());
			userEntity.setGroupName(groupEntity.getName());
			userEntity.setUserLevel(UserLevelEnum.GROUP_ADMIN.getCode());
		}
		return userEntity;
	}
}
