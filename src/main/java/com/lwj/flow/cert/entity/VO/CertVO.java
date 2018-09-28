package com.lwj.flow.cert.entity.VO;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lwj.flow.cert.entity.Employee;
import com.lwj.flow.common.entity.support.CustomDateSerializer;
import com.lwj.flow.common.entity.support.YesOrNo;

public class CertVO {
	
	/**
     * id
     *
     * 
     */
    private Integer id;

    /**
     * 证书编号
     *
     * 
     */
    private String certificateNo;

    /**
     * 发证日期
     *
     * 
     */
    private Date licenseDate;

    /**
     * 有效期
     *
     * 
     */
    private Date expireDate;

    /**
     * 有效期
     *
     * 
     */
    private Date reviewDate;

    /**
     * 备注
     *
     * 
     */
    private String remark;

    /**
     * 行业id
     *
     * 
     */
    private Integer tradeId;

    /**
     * 行业名字
     *
     * 
     */
    private String tradeName;

    /**
     * 专业
     *
     * 
     */
    private Integer domainId;

    /**
     * 行子名字
     *
     * 
     */
    private String domainName;

    /**
     * 等级id
     *
     * 
     */
    private Integer levelId;

    /**
     * 等级名称
     *
     * 
     */
    private String levelName;

    /**
     * 员工id
     *
     * 
     */
    private Integer employeeId;

    /**
     * 是否有效
     *
     * 
     */
    private YesOrNo isValid;
	
	private Employee employee;



	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

    @JsonSerialize(using=CustomDateSerializer.class)
	public Date getLicenseDate() {
		return licenseDate;
	}

	public void setLicenseDate(Date licenseDate) {
		this.licenseDate = licenseDate;
	}

    @JsonSerialize(using=CustomDateSerializer.class)
	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

    @JsonSerialize(using=CustomDateSerializer.class)
	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getTradeId() {
		return tradeId;
	}

	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public Integer getDomainId() {
		return domainId;
	}

	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public YesOrNo getIsValid() {
		return isValid;
	}

	public void setIsValid(YesOrNo isValid) {
		this.isValid = isValid;
	}
	
	
	
	
	

}
