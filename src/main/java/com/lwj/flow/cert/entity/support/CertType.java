package com.lwj.flow.cert.entity.support;

public enum CertType {
	Cert(0,"证书"),Trade(1,"行业"),Domain(2,"专业"),Level(3,"等级");
	
	private Integer code;
	private String desc;
	
	private CertType(Integer code, String desc) {
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
