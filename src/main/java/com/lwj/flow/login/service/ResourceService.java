package com.lwj.flow.login.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lwj.easymybatis.common.Criteria;
import com.lwj.easymybatis.support.OrderEnum;
import com.lwj.flow.login.entity.Resources;
import com.lwj.flow.login.entity.Role;
import com.lwj.flow.login.entity.RoleResources;
import com.lwj.flow.login.entity.vo.ResourceVO;
import com.lwj.flow.login.mapper.ResourcesMapper;
import com.lwj.flow.login.mapper.RoleResourcesMapper;

@Service
public class ResourceService {
	
	private Logger  logger = LoggerFactory.getLogger(ResourceService.class);

	
	@Autowired
	private ResourcesMapper  resourcesMapper;
	
	@Autowired
	private RoleResourcesMapper roleResourceMapper;
	
	/**
	 * 递归获取子菜单
	 * @param resourceVO
	 * @return
	 */
	public ResourceVO  getChild(ResourceVO resourceVO){
		if(resourceVO != null ) {
			Criteria criteria = new Criteria();
			criteria.putWhere("parentid", resourceVO.getId());
			criteria.putOrder("sort", OrderEnum.ASC);
			List<ResourceVO> childResourceVOs = new ArrayList<ResourceVO>();
			resourceVO.setChildResources(childResourceVOs);
			//主菜单
			List<Resources> resourceList = resourcesMapper.query(criteria);
			if( !resourceList.isEmpty()) {
				for(Resources  resource : resourceList){
					ResourceVO childResourceVO = new ResourceVO();
					childResourceVOs.add(childResourceVO);
					childResourceVO.setId(resource.getId());
					childResourceVO.setName(resource.getName());
					getChild(childResourceVO);
				}
			}
		}
		
		return resourceVO;
	}
	/**
	 * 获取父资源id
	 * @param id
	 * @return
	 */
	public List<Integer>  getParentId(Integer id){
		
		List<Integer> list = new ArrayList<Integer>(); 
		Criteria criteria = new Criteria();
		criteria.putWhere("id", id);
		Resources resource  = resourcesMapper.read(criteria);
		criteria.clear();
		criteria.putWhere("id", resource.getParentid());
		Resources parentResource  = resourcesMapper.read(criteria);
		if(parentResource != null){
			list.add(parentResource.getId());
			list.addAll(getParentId(parentResource.getId()));
		}
		return list;
	}
	
	/**
	 * 保存角色资源
	 * @param role
	 * @param resourceIds
	 */
	public void saveRoleResources(Role role,List<Integer> resourceIds){
		logger.info("保存角色资源:{}",role);
		if(role != null) {
			if(resourceIds != null && !resourceIds.isEmpty()){
				List<Integer> allIds  = new ArrayList<Integer>();
				allIds.addAll(resourceIds);
				for(int i=0;i<resourceIds.size();i++){
					List<Integer> pids = this.getParentId(resourceIds.get(i));
					for(Integer pid : pids){
						if(!allIds.contains(pid)) {
							allIds.add(pid);
						}
					}
				}
				for(Integer id: allIds){
					RoleResources  roleResources = new RoleResources();
					roleResources.setRoleid(role.getId());
					roleResources.setResourcesid(id);
					roleResourceMapper.add(roleResources);
				}
			} else {
				logger.info("角色:{},权限被设置为空",role.getId());
			}
		}
			
	}
}



