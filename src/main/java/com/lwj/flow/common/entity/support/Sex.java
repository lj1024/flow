package com.lwj.flow.common.entity.support;

public enum Sex {
    Male(1,"男"),Female(2,"女");
	
	private Integer code;
	private String desc;
	
	private Sex(Integer code, String desc) {
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
