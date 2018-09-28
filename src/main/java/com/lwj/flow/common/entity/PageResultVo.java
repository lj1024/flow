package com.lwj.flow.common.entity;

import java.io.Serializable;
import java.util.List;

public class PageResultVo<T> implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	  * 总数量
	  */
     private long totalCount;
     /**
      * 分页查询的结果
      */
     private List<T> results;
     
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
     
}
