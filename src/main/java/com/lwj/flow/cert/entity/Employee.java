package com.lwj.flow.cert.entity;

import java.util.Date;

import com.lwj.flow.common.entity.support.Sex;
import com.lwj.flow.common.entity.support.YesOrNo;

public class Employee {
    /**
     * id
     *
     * 
     */
    private Integer id;

    /**
     * 姓名
     *
     * 
     */
    private String emName;

    /**
     * 性别
     *
     * 
     */
    private Sex sex;

    /**
     * 身份证号
     *
     * 
     */
    private String identityCardNo;

    /**
     * 手机
     *
     * 
     */
    private Integer phone;

    /**
     * 邮箱
     *
     * 
     */
    private String email;

    /**
     * 地址
     *
     * 
     */
    private String address;

    /**
     * 省份
     *
     * 
     */
    private String province;

    /**
     * 城市
     *
     * 
     */
    private String city;

    /**
     * 社保情况
     *
     * 
     */
    private YesOrNo socialSecurity;

    /**
     * 创建时间
     *
     * 
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * 
     */
    private Date updateTime;

    /**
     * 是否为内部员工
     *
     * 
     */
    private YesOrNo isInternal;

    /**
     * 用户id
     *
     * 
     */
    private Integer userId;

    /**
     * 备注
     *
     * 
     */
    private String remark;

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
    public String getEmName() {
        return emName;
    }

    /**
     *
     * 
     */
    public void setEmName(String emName) {
        this.emName = emName == null ? null : emName.trim();
    }



    /**
     *
     * 
     */
    public String getIdentityCardNo() {
        return identityCardNo;
    }

    /**
     *
     * 
     */
    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo = identityCardNo == null ? null : identityCardNo.trim();
    }

    /**
     *
     * 
     */
    public Integer getPhone() {
        return phone;
    }

    /**
     *
     * 
     */
    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    /**
     *
     * 
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * 
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     *
     * 
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * 
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     *
     * 
     */
    public String getProvince() {
        return province;
    }

    /**
     *
     * 
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     *
     * 
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * 
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }



    /**
     *
     * 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *
     * 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     *
     * 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     *
     * 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }





    /**
     *
     * 
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * 
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public YesOrNo getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(YesOrNo socialSecurity) {
		this.socialSecurity = socialSecurity;
	}

	public YesOrNo getIsInternal() {
		return isInternal;
	}

	public void setIsInternal(YesOrNo isInternal) {
		this.isInternal = isInternal;
	}

	public YesOrNo getIsValid() {
		return isValid;
	}

	public void setIsValid(YesOrNo isValid) {
		this.isValid = isValid;
	}


    
}