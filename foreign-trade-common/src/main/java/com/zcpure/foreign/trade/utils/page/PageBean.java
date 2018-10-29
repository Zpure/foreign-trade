package com.zcpure.foreign.trade.utils.page;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 分页
 * 
 */
public class PageBean<T> implements Serializable {
	private static final long serialVersionUID = 8656597559014685635L;

	/**
	 * 总记录数
	 */
	private long totalNum;
	/**
	 * 结果集
	 */
	private List<T> record;
	/**
	 * 第几页
	 */
	private int pageNo;
	/**
	 * 每页记录数
	 */
	private int pageSize;
	/**
	 * 总页数
	 */
	private int pages;

	public PageBean() {
		this.pageNo = 1;
		this.pageSize = 20;
		this.totalNum = 0;
		this.pages = 0;
		this.record = new ArrayList<>();
	}


	/**
	 * 构造函数
	 * 
	 * @param totalNum
	 *            总记录数
	 * @param list
	 *            结果集
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页记录数
	 * @param pages
	 *            总页数
	 */
	public PageBean(long totalNum, List<T> list, int pageNo, int pageSize, int pages) {
		this.totalNum = totalNum;
		this.record = list;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.pages = pages;
	}

	public long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}

	public List<T> getRecord() {
		return record;
	}

	public void setRecord(List<T> record) {
		this.record = record;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	
	public <K> PageBean<K> to(Class<K> cls) {
	    List<K> list = record == null ? Collections.emptyList() : new ArrayList<>(record.size());
	    if(record != null) {
            try {
                for(T t : record) {
                    K k = cls.newInstance();
                    BeanUtils.copyProperties(t, k);
                    list.add(k);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
	    }
	    return new PageBean<>(totalNum, list, pageNo, pageSize, pageSize);
	}
}
