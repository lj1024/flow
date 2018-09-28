package com.lwj.flow.cert.entity.VO;

import java.util.List;

import com.lwj.flow.cert.entity.support.CertType;

public class CertDictionaryVO {
	
	private int id;
	private String dicName;
	private CertType certType;
	private int parentId;
	
	private List<CertDictionaryVO> childs;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public CertType getCertType() {
		return certType;
	}

	public void setCertType(CertType certType) {
		this.certType = certType;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public List<CertDictionaryVO> getChilds() {
		return childs;
	}

	public void setChilds(List<CertDictionaryVO> childs) {
		this.childs = childs;
	}
	
	

}
