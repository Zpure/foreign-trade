package com.zcpure.foreign.trade.dto.goods;

import lombok.Data;

import java.util.Date;

/**
 * @author ethan
 * @create_time 2018/10/22 15:18
 * 型号数据
 */
@Data
public class ModelDTO {
	private Long id;

	private Long brandId;

	private String brandName;

	private String name;

	private String alias;

	private String mainImg;

	private String otherImg;

	private Date createTime;
}
