package com.zcpure.foreign.trade.user.dao.entity;

import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import com.zcpure.foreign.trade.command.user.SupplierAddCommand;
import com.zcpure.foreign.trade.dto.user.SupplierDTO;
import com.zcpure.foreign.trade.enums.SupplierStatusEnum;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.List;

/**
 * 管理用户
 */
@Table(name = "ft_supplier")
@Where(clause = "delete_flag <> 1")
@Entity
@Data
public class SupplierEntity extends BaseEntity {
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
	private Integer status;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "supplierCode", referencedColumnName = "code")
	private List<SupplierGoodsEntity> detailList;

	public static SupplierDTO formDTO(SupplierEntity entity) {
		if (entity == null) {
			return null;
		}
		SupplierDTO dto = new SupplierDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public static SupplierEntity form(SupplierAddCommand command) {
		if (command == null) {
			return null;
		}
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		SupplierEntity entity = new SupplierEntity();
		BeanUtils.copyProperties(command, entity);
		entity.setGroupCode(info.getGroupCode());
		entity.setGroupName(info.getGroupName());
		entity.setStatus(SupplierStatusEnum.NORMAL.getCode());
		return entity;
	}
}
