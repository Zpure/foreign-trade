package com.zcpure.foreign.trade.goods.dao.entity;

import com.zcpure.foreign.trade.command.goods.ModelAddCommand;
import com.zcpure.foreign.trade.command.goods.ModelUpdateCommand;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @author ethan
 * @create_time 2018/10/22 15:18
 * 型号数据
 */
@Table(name = "ft_model")
@Where(clause = "delete_flag <> 1")
@Entity
@Data
public class ModelEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long brandId;

	private String brandName;

	private String name;

	private String alias;

	private String mainImg;

	private String otherImg;

	public static ModelEntity formAdd(ModelAddCommand command, BrandEntity brandEntity) {
		ModelEntity entity = new ModelEntity();
		entity.setBrandId(brandEntity.getId());
		entity.setBrandName(brandEntity.getName());
		entity.setName(command.getName());
		entity.setAlias(command.getAlias());
		entity.setMainImg(command.getMainImg());
		entity.setOtherImg(command.getOtherImg());
		return entity;
	}

	public void fillUpdateInfo(ModelUpdateCommand command) {
		this.setAlias(command.getAlias());
		this.setMainImg(command.getMainImg());
		this.setOtherImg(command.getOtherImg());
	}
}
