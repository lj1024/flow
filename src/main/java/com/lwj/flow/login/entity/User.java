package com.lwj.flow.login.entity;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lwj.flow.common.entity.support.CustomDateSerializer;
import com.lwj.flow.common.entity.support.Sex;
import com.lwj.flow.common.entity.support.YesOrNo;

public class User {
    /**
     * 
     *
     * 
     */
    private Integer id;

    /**
     * 登录账号
     *
     * 
     */
    private String username;

    /**
     * 邮箱
     *
     * 
     */
    private String email;

    /**
     * 密码
     *
     * 
     */
    private String password;

    /**
     * 真实姓名
     *
     * 
     */
    private String realName;

    /**
     * 电话
     *
     * 
     */
    private Integer phone;

    /**
     * 性别
     *
     * 
     */
    private Sex sex;

    /**
     * 照片
     *
     * 
     */
    private String photo;

    /**
     * 创建时间
     *
     * 
     */
    private Date createTime;

    /**
     * 最后登录时间
     *
     * 
     */
    private Date lastLoginTime;

    /**
     * 是否离职
     *
     * 
     */
    private YesOrNo isLeave;

    /**
     * 1:有效，0:禁止登录
     *
     * 
     */
    private Integer status;

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
    public String getUsername() {
        return username;
    }

    /**
     *
     * 
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
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
    public String getPassword() {
        return password;
    }

    /**
     *
     * 
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     *
     * 
     */
    public String getRealName() {
        return realName;
    }

    /**
     *
     * 
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
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
 

    /**
     *
     * 
     */
    public String getPhoto() {
        return photo;
    }

    /**
     *
     * 
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     *
     * 
     */
    @JsonSerialize(using=CustomDateSerializer.class)
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
    @JsonSerialize(using=CustomDateSerializer.class)
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     *
     * 
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     *
     * 
     */


    /**
     *
     * 
     */
    public Integer getStatus() {
        return status;
    }

    public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public YesOrNo getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(YesOrNo isLeave) {
		this.isLeave = isLeave;
	}

	/**
     *
     * 
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", realName=" + realName + ", phone=" + phone + ", sex=" + sex + ", photo=" + photo + ", createTime="
				+ createTime + ", lastLoginTime=" + lastLoginTime + ", isLeave=" + isLeave + ", status=" + status + "]";
	}
    
    
}