package com.lwj.flow.login.mapper;

import org.apache.ibatis.annotations.Param;

import com.lwj.easymybatis.common.CommonMaper;
import com.lwj.flow.login.entity.Role;

public interface RoleMapper extends CommonMaper<Role> {
	
	public Role getRoleByUserId(@Param("userid")int id);
}