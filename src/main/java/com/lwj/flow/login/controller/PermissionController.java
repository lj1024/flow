package com.lwj.flow.login.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lwj.easymybatis.common.Criteria;
import com.lwj.easymybatis.support.OrderEnum;
import com.lwj.flow.common.controller.BaseController;
import com.lwj.flow.common.entity.AjaxResponse;
import com.lwj.flow.common.entity.PageResultVo;
import com.lwj.flow.login.entity.Resources;
import com.lwj.flow.login.entity.RoleResources;
import com.lwj.flow.login.entity.vo.ResourceVO;
import com.lwj.flow.login.mapper.ResourcesMapper;
import com.lwj.flow.login.mapper.RoleResourcesMapper;
import com.lwj.flow.login.service.ResourceService;


@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController{
	
//	private Logger  logger = LoggerFactory.getLogger(PermissionController.class);
	
	@Autowired
	private ResourcesMapper  resourcesMapper;
	@Autowired
	private ResourceService  resourceService;
	@Autowired
	private RoleResourcesMapper roleResourceMapper;

	
	/**
	 * 权限列表页面
	 * @return
	 */
	@RequiresPermissions({"/permission/list"})
	@RequestMapping("/list")
	public String list() {

		
		return "/permission/perList";
	}
	
	/**
	 * 分页查询权限列表
	 * @param page 
	 * @param size
	 * @return
	 */
	@RequiresPermissions({"/permission/list"})
	@RequestMapping("/page")
	@ResponseBody
	public AjaxResponse<PageResultVo<Resources>>  page(int page,int size){
		if(page <= 0){
			page = 1;
		}
		if(size <= 0 ){
			size = 10;
		}
		AjaxResponse<PageResultVo<Resources>> response = new AjaxResponse<PageResultVo<Resources>>();
		response.setStatus(true);
		PageResultVo<Resources>  pageResultVo =  new PageResultVo<Resources>();
		Criteria criteria = new Criteria();
		criteria.put("pageFrom", (page - 1) * size);
		criteria.put("pageTo", size);
		criteria.putOrder("sort", OrderEnum.ASC);
		List<Resources> lists = resourcesMapper.page(criteria);
		long count = resourcesMapper.count(criteria);
		pageResultVo.setTotalCount(count);
		pageResultVo.setResults(lists);
		response.setEntity(pageResultVo);
		return response;
	}
	
	/**
	 * 获取树形资源结构
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping("/getResourcesTree")
	@ResponseBody
	public AjaxResponse<List<ResourceVO>>  getResoucesTree(){
		AjaxResponse<List<ResourceVO>> response = new AjaxResponse<List<ResourceVO>>();
		response.setStatus(true);
		List<ResourceVO>  resourceVOList = new ArrayList<ResourceVO>();
		Criteria criteria = new Criteria();
		criteria.putWhere("parentid", 0);
		criteria.putOrder("sort", OrderEnum.ASC);
		//主菜单
		List<Resources> resourceList = resourcesMapper.query(criteria);
		for(Resources resource :resourceList){
			ResourceVO resourceVO = new ResourceVO();
			resourceVO.setId(resource.getId());
			resourceVO.setName(resource.getName());
			resourceService.getChild(resourceVO);
			resourceVOList.add(resourceVO);
		}
		response.setEntity(resourceVOList);
		return response;
	}
	
	/**
	 * 获取角色权限
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping("/getRoleResourceIds")
	@ResponseBody
	public AjaxResponse<List<Integer>>  getRoleResouceIds(int roleId){
		AjaxResponse<List<Integer>> response = new AjaxResponse<List<Integer>>();
		response.setStatus(true);
		Criteria criteria = new Criteria();
		criteria.putWhere("roleid", roleId);
		List<RoleResources> lists = roleResourceMapper.queryBasicRoleResources(criteria);
		List<Integer>  resourceIds = new ArrayList<Integer>();
		for(RoleResources roleResource : lists){
			resourceIds.add(roleResource.getResourcesid());
		}
		response.setEntity(resourceIds);
		return response;
	}
	
	
	
	
	
	
	
	@RequestMapping("/study")
	public String study() {

		
		return "permission/study";
	}

	public static void main(String[] args) {
		int[] a = new int[]{1,2,3};
		System.out.println(a[0]);
	}
}
