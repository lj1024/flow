package com.lwj.flow.login.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lwj.easymybatis.common.Criteria;
import com.lwj.easymybatis.support.OrderEnum;
import com.lwj.flow.common.controller.BaseController;
import com.lwj.flow.common.entity.AjaxResponse;
import com.lwj.flow.common.entity.PageResultVo;
import com.lwj.flow.login.entity.Role;
import com.lwj.flow.login.entity.User;
import com.lwj.flow.login.entity.UserRole;
import com.lwj.flow.login.mapper.RoleMapper;
import com.lwj.flow.login.mapper.RoleResourcesMapper;
import com.lwj.flow.login.mapper.UserRoleMapper;
import com.lwj.flow.login.service.ResourceService;
import com.lwj.flow.login.service.ShiroService;


/**
 * 角色管理
 * @author luwja
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	
	private Logger  logger = LoggerFactory.getLogger(RoleController.class);

	

	
	@Autowired
	private RoleMapper  roleMapper;
	@Autowired
	private ResourceService  resourceService;
	@Autowired
	private RoleResourcesMapper roleResourceMapper;
	@Autowired
	private UserRoleMapper  userRoleMapper;
	@Autowired
	private ShiroService shiroService;
	
	/**
	 * 角色管理页面
	 * @return
	 */
	@RequiresPermissions({"/role/list"})
	@RequestMapping("/list")
	public String list() {

		
		return "/role/list";
	}
	
	/**
	 * 分页查询角色列表
	 * @param page 
	 * @param size
	 * @return
	 */
	@RequiresPermissions({"/role/list"})
	@RequestMapping("/page")
	@ResponseBody
	public AjaxResponse<PageResultVo<Role>>  page(int page,int size){
		if(page <= 0){
			page = 1;
		}
		if(size <= 0 ){
			size = 10;
		}
		AjaxResponse<PageResultVo<Role>> response = new AjaxResponse<PageResultVo<Role>>();
		response.setStatus(true);
		PageResultVo<Role>  pageResultVo =  new PageResultVo<Role>();
		Criteria criteria = new Criteria();
		criteria.put("pageFrom", (page - 1) * size);
		criteria.put("pageTo", size);
		criteria.putOrder("id", OrderEnum.ASC);
		List<Role> lists = roleMapper.page(criteria);
		long count = roleMapper.count(criteria);
		pageResultVo.setTotalCount(count);
		pageResultVo.setResults(lists);
		response.setEntity(pageResultVo);
		return response;

		
	}
	
	@RequiresPermissions("/role/assignResources")
	@RequestMapping("/saveRoleResouces")
	@ResponseBody
	@Transactional
	public AjaxResponse<List<Integer>>  saveRoleResouces(int roleId,@RequestParam(value="resourceIds", required = false)List<Integer> resourceIds ){
		AjaxResponse<List<Integer>> response = new AjaxResponse<List<Integer>>();
		boolean status = true;
		String errMsg="";
		response.setStatus(true);
		Criteria criteria = new Criteria();
		criteria.putWhere("id", roleId);
		Role role = roleMapper.read(criteria);
		if(role != null && role.getId() != null) {
			criteria.clear();
			criteria.putWhere("roleid", role.getId());
			//删除角色原有资源
			roleResourceMapper.delete(criteria);
			resourceService.saveRoleResources(role, resourceIds);
			List<UserRole>  userRoles = userRoleMapper.query(criteria);
			List<Integer>  userIds = new ArrayList<Integer>();
			for(UserRole userRole : userRoles){
				userIds.add(userRole.getUserid());
			}
			//刷新用户权限
			shiroService.clearUserAuthByUserId(userIds);
			
		} else {
			logger.error("修改角色权限失败,角色不存在,roleid:{}",roleId);
			status = false;
			errMsg = "保存失败,角色不存在";
		}
		response.setStatus(status);
		response.setErrMsg(errMsg);
		return response;
	}
	
	
	
	@RequiresPermissions("/role/create")
	@RequestMapping("/create")
	@ResponseBody
	@Transactional
	public AjaxResponse<List<Integer>>  createRole(String roleName,@RequestParam(value="resourceIds", required = false)List<Integer> resourceIds ){
		AjaxResponse<List<Integer>> response = new AjaxResponse<List<Integer>>();
		boolean status = true;
		String errMsg="";
		Role role = new Role();
		role.setRoledesc(roleName);
		roleMapper.add(role);
		resourceService.saveRoleResources(role, resourceIds);
		response.setStatus(status);
		response.setErrMsg(errMsg);
		return response;
	}
	
	@RequiresPermissions("/role/delete")
	@RequestMapping("/delete")
	@ResponseBody
	@Transactional
	public AjaxResponse<List<Integer>>  deleteRole(Role role){
		logger.info("删除角色:{}",role);
		AjaxResponse<List<Integer>> response = new AjaxResponse<List<Integer>>();
		boolean status = true;
		String errMsg="";
		if(role != null && role.getId() != null) {
			Criteria  criteria = new Criteria();
			criteria.putWhere("id", role.getId());
			roleMapper.delete(criteria);
			criteria.clear();
			criteria.putWhere("roleid", role.getId());
			List<UserRole>  userRoles = userRoleMapper.query(criteria);
			List<Integer>  userIds = new ArrayList<Integer>();
			for(UserRole userRole : userRoles){
				userIds.add(userRole.getUserid());
			}
			//刷新用户权限
			shiroService.clearUserAuthByUserId(userIds);
			roleResourceMapper.delete(criteria);
			userRoleMapper.delete(criteria);
		}
		response.setStatus(status);
		response.setErrMsg(errMsg);
		return response;
	}
	
	/**
	 * ajax验证角色名称是否存在
	 * @param roleName
	 * @return
	 */
	@RequiresPermissions({"/role/create"})
	@RequestMapping("/validateRoleName")
	@ResponseBody
	public AjaxResponse<Boolean>  validateUserName(String roleName){
		AjaxResponse<Boolean> response = new AjaxResponse<Boolean>();
		boolean status = true;
		String errMsg="";
		//用户名是否存在
		Boolean entity = false;
		if(!StringUtils.isEmpty(roleName)){
			Criteria  criteria = new Criteria();
			criteria.putWhere("roledesc", roleName);
			Role dbRole  = roleMapper.read(criteria);
			if(dbRole != null){
				entity = true;
			}
			
		} else {
			status=false;
			errMsg="角色名称为空";
		}
		
		response.setStatus(status);
		response.setErrMsg(errMsg);
		response.setEntity(entity);
		return response;
	}
	
	@RequestMapping("/study")
	public String study() {

		
		return "permission/study";
	}

}
