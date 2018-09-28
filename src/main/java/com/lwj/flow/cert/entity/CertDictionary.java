package com.lwj.flow.cert.entity;

import com.lwj.flow.cert.entity.support.CertType;
import com.lwj.flow.common.entity.support.YesOrNo;

public class CertDictionary {
    /**
     * 
     *
     * 
     */
    private Integer id;

    /**
     * 名称
     *
     * 
     */
    private String dicName;

    /**
     * 资源类型   trade:行业   domain: 专业 level：级别
     *
     * 
     */
    private CertType type;

    /**
     * 父id
     *
     * 
     */
    private Integer parentId;

    /**
     * 排序
     *
     * 
     */
    private Integer sort;

    /**
     * 是否有效
     *
     * 
     */
    private YesOrNo isValid;

    /**
     *
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * 
     */
    public String getDicName() {
        return dicName;
    }

    /**
     *
     * 
     */
    public void setDicName(String dicName) {
        this.dicName = dicName == null ? null : dicName.trim();
    }

   

    public CertType getType() {
		return type;
	}

	public void setType(CertType type) {
		this.type = type;
	}

	/**
     *
     * 
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     *
     * 
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     *
     * 
     */
    public Integer getSort() {
        return sort;
    }

    /**
     *
     * 
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

	public YesOrNo getIsValid() {
		return isValid;
	}

	public void setIsValid(YesOrNo isValid) {
		this.isValid = isValid;
	}

	@Override
	public String toString() {
		return "CertDictionary [id=" + id + ", dicName=" + dicName + ", type=" + type + ", parentId=" + parentId
				+ ", sort=" + sort + ", isValid=" + isValid + "]";
	}



}