package com.zcpure.foreign.trade.goods.utils.page;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zcpure.foreign.trade.utils.page.PageBean;

import java.util.List;

public class PageBeanAssembler {

	public <T> PageBean<T> toBean(Page<T> page) {
		return new PageBean<T>(
			page.getTotal(),
			page.getResult(),
			page.getPageNum(),
			page.getPageSize(),
			page.getPages());
	}

	public <T> PageBean<T> toBeanByList(List<T> list) {
		PageInfo page = new PageInfo(list);
		return new PageBean<T>(
			page.getTotal(),
			page.getList(),
			page.getPageNum(),
			page.getPageSize(),
			page.getPages());
	}

}
