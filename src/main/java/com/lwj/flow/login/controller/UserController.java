package com.lwj.flow.login.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lwj.easymybatis.common.Criteria;
import com.lwj.easymybatis.support.OrderEnum;
import com.lwj.flow.common.controller.BaseController;
import com.lwj.flow.common.entity.AjaxResponse;
import com.lwj.flow.common.entity.PageResultVo;
import com.lwj.flow.common.entity.support.Sex;
import com.lwj.flow.common.entity.support.YesOrNo;
import com.lwj.flow.common.service.PasswordHelper;
import com.lwj.flow.login.entity.Role;
import com.lwj.flow.login.entity.User;
import com.lwj.flow.login.entity.UserRole;
import com.lwj.flow.login.entity.vo.UserRoleVo;
import com.lwj.flow.login.mapper.RoleMapper;
import com.lwj.flow.login.mapper.UserMapper;
import com.lwj.flow.login.mapper.UserRoleMapper;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	private Logger  logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserMapper  userMapper;
	@Autowired
	private RoleMapper  roleMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private PasswordHelper passwordHelper;


	
	/**
	 * 用户管理页面
	 * @return
	 */
	@RequiresPermissions({"/user/list"})
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model) {
	 List<Role> roles = 	roleMapper.query(null);
	 model.addAttribute("roles", roles);
		
		return "user/list";
	}
	
	/**
	 * 分页查询用户列表
	 * @param page 
	 * @param size
	 * @return
	 */
	@RequiresPermissions({"/user/list"})
	@RequestMapping("/page")
	@ResponseBody
	public AjaxResponse<PageResultVo<UserRoleVo>>  page(int page,int size){
		if(page <= 0){
			page = 1;
		}
		if(size <= 0 ){
			size = 10;
		}
		AjaxResponse<PageResultVo<UserRoleVo>> response = new AjaxResponse<PageResultVo<UserRoleVo>>();
		response.setStatus(true);
		PageResultVo<UserRoleVo>  pageResultVo =  new PageResultVo<UserRoleVo>();
		Criteria criteria = new Criteria();
		criteria.put("pageFrom", (page - 1) * size);
		criteria.put("pageTo", size);
		criteria.putOrder("id", OrderEnum.DESC);
		List<User> userLists = userMapper.page(criteria);
		long count = userMapper.count(criteria);
		List<UserRoleVo> lists = new ArrayList<>();

		for(User user :userLists){
			UserRoleVo vo = new UserRoleVo();
			vo.setUser(user);
			Role role = roleMapper.getRoleByUserId(user.getId());
			vo.setRole(role);
			lists.add(vo);
		}
		
		
		pageResultVo.setTotalCount(count);
		pageResultVo.setResults(lists);
		response.setEntity(pageResultVo);
		return response;
	}
	
	/**
	 * 创建用户
	 * @param user
	 * @return
	 */
	@RequiresPermissions({"/user/create"})
	@RequestMapping("/create")
	@ResponseBody
	public AjaxResponse<Object>  create(User user,Integer roleid){
		logger.info("创建用户:{}",user);
		AjaxResponse<Object> response = new AjaxResponse<Object>();
		boolean status = true;
		String errMsg="";
		if(user != null && !StringUtils.isEmpty(user.getUsername())){
			Criteria  criteria = new Criteria();
			criteria.putWhere("username", user.getUsername());
			User dbUser  = userMapper.read(criteria);
			if(dbUser == null){
				if(user.getSex() == null){
					user.setSex(Sex.Male);
				}
				
				if(user.getStatus() == null){
					user.setStatus(0);
				} 
				user.setCreateTime(new Date());
				user.setIsLeave(YesOrNo.No);
				//添加默认密码
				user.setPassword("abc.123");
				//对密码进行加密
				passwordHelper.encryptPassword(user);
				userMapper.add(user);
				//添加用户角色
				if(roleid != null){
					UserRole  userRole = new UserRole();
					userRole.setUserid(user.getId());
					userRole.setRoleid(roleid);
					userRoleMapper.add(userRole);
				}
				
			} else {
				status = false;
				errMsg="创建用户失败:登录账号已存在,请修改登录账号";	
			}
			
		} else {
			status = false;
			errMsg="创建用户失败:没有设置登录账号";
		} 
		response.setStatus(status);
		response.setErrMsg(errMsg);
		return response;
	}
	
	/**
	 * 更新用户
	 * @param 更新用户
	 * @return
	 */
	@RequiresPermissions({"/user/update"})
	@RequestMapping("/update")
	@ResponseBody
	@Transactional
	public AjaxResponse<Object>  update(User user,Integer roleid){
		logger.info("更新用户:{},roleid:{}",user,roleid);
		AjaxResponse<Object> response = new AjaxResponse<Object>();
		boolean status = true;
		String errMsg="";
		if(user != null && !StringUtils.isEmpty(user.getUsername()) && user.getId() != null){
			Criteria  criteria = new Criteria();
			criteria.putWhere("id", user.getId());
			User dbUser  = userMapper.read(criteria);
			if(dbUser != null){
				if(user.getSex() == null){
					user.setSex(Sex.Male);
				}
				
				if(user.getStatus() == null){
					user.setStatus(0);
				} 
				if(!StringUtils.isEmpty(user.getPassword())){
					//修改用户密码,密码忘记通过这里强制修改密码
					passwordHelper.encryptPassword(user);
				}
				criteria.clear();
				criteria.putWhere("id", user.getId());
				criteria.putUpdate("email", user.getEmail());
				criteria.putUpdate("realName", user.getRealName());
				criteria.putUpdate("sex", user.getSex());
				criteria.putUpdate("status", user.getStatus());
				criteria.putUpdate("isLeave", user.getIsLeave());
				if(!StringUtils.isEmpty(user.getPassword())) {
					criteria.putUpdate("password", user.getPassword());
				}
				userMapper.update(criteria);
				//更改用户角色
				if(roleid != null){
					criteria.clear();
					criteria.putWhere("userid", user.getId());
					UserRole dbuserRole = userRoleMapper.read(criteria);
					if(dbuserRole != null){
						criteria.putUpdate("roleid", roleid);
						userRoleMapper.update(criteria);
					} else {
						UserRole userRole = new UserRole();
						userRole.setUserid(user.getId());
						userRole.setRoleid(roleid);
						userRoleMapper.add(userRole);
					}
					
					
				}
				
			} else {
				logger.error("用户不存在:{}",user);
				status = false;
				errMsg="保存用户失败";	
			}
			
		} else {
			logger.error("登录账号或者账号id为空:{}",user);
			status = false;
			errMsg="保存用户失败";
		} 
		response.setStatus(status);
		response.setErrMsg(errMsg);
		return response;
	}
	
	
	/**
	 * 删除用户
	 * @param 删除用户
	 * @return
	 */
	@RequiresPermissions({"/user/delete"})
	@RequestMapping("/delete")
	@ResponseBody
	@Transactional
	public AjaxResponse<Object>  update(Integer userid){
		logger.info("删除用户:{}",userid);
		AjaxResponse<Object> response = new AjaxResponse<Object>();
		boolean status = true;
		String errMsg="";
		if(userid != null ){
			Criteria  criteria = new Criteria();
			criteria.putWhere("id", userid);
			User dbUser  = userMapper.read(criteria);
			if(dbUser != null){
				criteria.putUpdate("isLeave", YesOrNo.Yes);
				criteria.putUpdate("status", 0);
				userMapper.update(criteria);
				criteria.clear();
				criteria.putWhere("userid", userid);
				userRoleMapper.delete(criteria);
			} else {
				logger.error("用户不存在:{}",userid);
				status = false;
				errMsg="删除用户失败";	
			}
			
		} else {
			logger.error("账号id为空:{}",userid);
			status = false;
			errMsg="删除用户失败";
		} 
		response.setStatus(status);
		response.setErrMsg(errMsg);
		return response;
	}
	
	/**
	 * ajax验证用户名是否存在
	 * @param userName
	 * @return
	 */
	@RequiresPermissions({"/user/create"})
	@RequestMapping("/validateUserName")
	@ResponseBody
	public AjaxResponse<Boolean>  validateUserName(String userName){
		AjaxResponse<Boolean> response = new AjaxResponse<Boolean>();
		boolean status = true;
		String errMsg="";
		//用户名是否存在
		Boolean entity = false;
		if(!StringUtils.isEmpty(userName)){
			Criteria  criteria = new Criteria();
			criteria.putWhere("username", userName);
			User dbUser  = userMapper.read(criteria);
			if(dbUser != null){
				entity = true;
			}
			
		} else {
			status=false;
			errMsg="登录账号为空";
		}
		
		response.setStatus(status);
		response.setErrMsg(errMsg);
		response.setEntity(entity);
		return response;
	}
}
