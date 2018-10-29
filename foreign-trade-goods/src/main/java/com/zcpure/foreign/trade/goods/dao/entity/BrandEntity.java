package com.zcpure.foreign.trade.goods.dao.entity;

import com.zcpure.foreign.trade.dto.goods.BrandDTO;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

/**
 * 品牌
 */
@Table(name = "ft_brand")
@Where(clause = "delete_flag <> 1")
@Entity
@Data
public class BrandEntity extends BaseEntity {
	private static final long serialVersionUID = 1111320370190733556L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;

	public BrandEntity() {
	}

	public BrandEntity(String name) {
		this.name = name;
	}

	public static BrandDTO toDTO(BrandEntity entity) {
		if (entity == null) {
			return null;
		}
		BrandDTO dto = new BrandDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
}
