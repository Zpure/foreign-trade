package com.zcpure.foreign.trade.user.dao.entity;

import com.zcpure.foreign.trade.enums.DeleteFlagEnum;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体基础信息
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = -5733386633728577588L;
	/**
	 * 创建时间
	 */
	@Column(updatable = false)
	@org.hibernate.annotations.CreationTimestamp
	private Date createTime;
	/**
	 * 修改时间
	 */
	@Column
	@org.hibernate.annotations.UpdateTimestamp
	private Date modifyTime;

	/**
	 * 删除标志
	 */
	@Column(length = 2, nullable = false)
	private Integer deleteFlag = DeleteFlagEnum.NORMAL.getCode();

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
