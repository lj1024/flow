package com.lwj.flow.common.entity.support;

public enum YesOrNo {
	Yes(1,"是"),No(0,"否");
	
	private Integer code;
	private String desc;
	
	private YesOrNo(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}


	
	
	

}
