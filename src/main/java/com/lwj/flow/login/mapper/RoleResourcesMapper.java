package com.lwj.flow.login.mapper;

import java.util.List;

import com.lwj.easymybatis.common.CommonMaper;
import com.lwj.easymybatis.common.Criteria;
import com.lwj.flow.login.entity.RoleResources;

public interface RoleResourcesMapper extends CommonMaper<RoleResources> {
	
	/**
	 * 查找角色最基本的权限(不包括主菜单，子菜单)
	 * @param criteria
	 * @return
	 */
	public List<RoleResources>  queryBasicRoleResources(Criteria  criteria);
}