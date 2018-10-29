package com.zcpure.foreign.trade.goods.utils.page;

import org.apache.ibatis.session.RowBounds;

public class RowBoundsBuilder {

	public static RowBounds build(Integer pageNo, Integer pageSize) {
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = 20;
		}
		return new RowBounds((pageNo - 1) * pageSize, pageSize);
	}

}
