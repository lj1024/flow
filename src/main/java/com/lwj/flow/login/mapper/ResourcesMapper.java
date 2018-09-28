package com.lwj.flow.login.mapper;

import java.util.List;

import com.lwj.easymybatis.common.CommonMaper;
import com.lwj.easymybatis.common.Criteria;
import com.lwj.flow.login.entity.Resources;

public interface ResourcesMapper extends CommonMaper<Resources> {
	
	/**
	 * 获取用户权限
	 * @param criteria
	 * @return
	 */
	public List<Resources>  queryResourcesByUser(Criteria criteria);
}