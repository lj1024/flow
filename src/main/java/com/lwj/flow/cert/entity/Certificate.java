package com.lwj.flow.cert.entity;

import java.util.Date;

import com.lwj.flow.common.entity.support.YesOrNo;

public class Certificate {
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
    public String getCertificateNo() {
        return certificateNo;
    }

    /**
     *
     * 
     */
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo == null ? null : certificateNo.trim();
    }

    /**
     *
     * 
     */
    public Date getLicenseDate() {
        return licenseDate;
    }

    /**
     *
     * 
     */
    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
    }

    /**
     *
     * 
     */
    public Date getExpireDate() {
        return expireDate;
    }

    /**
     *
     * 
     */
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    /**
     *
     * 
     */
    public Date getReviewDate() {
        return reviewDate;
    }

    /**
     *
     * 
     */
    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    /**
     *
     * 
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * 
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     *
     * 
     */
    public Integer getTradeId() {
        return tradeId;
    }

    /**
     *
     * 
     */
    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    /**
     *
     * 
     */
    public String getTradeName() {
        return tradeName;
    }

    /**
     *
     * 
     */
    public void setTradeName(String tradeName) {
        this.tradeName = tradeName == null ? null : tradeName.trim();
    }

    /**
     *
     * 
     */
    public Integer getDomainId() {
        return domainId;
    }

    /**
     *
     * 
     */
    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    /**
     *
     * 
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     *
     * 
     */
    public void setDomainName(String domainName) {
        this.domainName = domainName == null ? null : domainName.trim();
    }

    /**
     *
     * 
     */
    public Integer getLevelId() {
        return levelId;
    }

    /**
     *
     * 
     */
    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    /**
     *
     * 
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     *
     * 
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    /**
     *
     * 
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     *
     * 
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     *
     * 
     */
    public YesOrNo getIsValid() {
        return isValid;
    }

    /**
     *
     * 
     */
    public void setIsValid(YesOrNo isValid) {
        this.isValid = isValid;
    }
}