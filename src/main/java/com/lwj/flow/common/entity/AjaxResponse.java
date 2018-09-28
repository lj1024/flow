package com.lwj.flow.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AjaxResponse<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean status;
	
	private String errMsg;
	private T entity;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}


	

	

	
	
	

}
