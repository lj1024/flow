package com.lwj.flow.cert.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lwj.easymybatis.common.Criteria;
import com.lwj.easymybatis.support.OrderEnum;
import com.lwj.flow.cert.entity.Certificate;
import com.lwj.flow.cert.entity.Employee;
import com.lwj.flow.cert.entity.VO.CertVO;
import com.lwj.flow.cert.mapper.CertVOMapper;
import com.lwj.flow.cert.mapper.CertificateMapper;
import com.lwj.flow.cert.mapper.EmployeeMapper;
import com.lwj.flow.common.controller.BaseController;
import com.lwj.flow.common.entity.AjaxResponse;
import com.lwj.flow.common.entity.PageResultVo;
import com.lwj.flow.common.entity.support.YesOrNo;
@Controller
@RequestMapping("/cert")
public class CertController extends BaseController{
	
	@Autowired
	private CertificateMapper certificateMapper;
	@Autowired
	private EmployeeMapper  employeeMapper;
	@Autowired
	private CertVOMapper  certVOMapper;
	
	
	/**
	 * 证书管理页面
	 * @return
	 */
	@RequiresPermissions({"/cert/list"})
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model) {

		
		return "cert/list";
	}
	
	
	/**
	 * 分页查询证书
	 * @param page 
	 * @param size
	 * @return
	 */
	@RequiresPermissions({"/cert/list"})
	@RequestMapping("/page")
	@ResponseBody
	public AjaxResponse<PageResultVo<CertVO>>  page(int page,int size){
		if(page <= 0){
			page = 1;
		}
		if(size <= 0 ){
			size = 10;
		}
		AjaxResponse<PageResultVo<CertVO>> response = new AjaxResponse<PageResultVo<CertVO>>();
		response.setStatus(true);
		PageResultVo<CertVO>  pageResultVo =  new PageResultVo<CertVO>();
		Criteria criteria = new Criteria();
		criteria.put("pageFrom", (page - 1) * size);
		criteria.put("pageTo", size);
		criteria.putOrder("id", OrderEnum.DESC);
		List<CertVO> certVOLists = certVOMapper.page(criteria);
		long count = certVOMapper.count(criteria);
		
		
		
		pageResultVo.setTotalCount(count);
		pageResultVo.setResults(certVOLists);
		response.setEntity(pageResultVo);
		return response;
	}

}
