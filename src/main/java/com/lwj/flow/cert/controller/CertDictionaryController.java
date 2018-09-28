package com.lwj.flow.cert.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lwj.easymybatis.common.Criteria;
import com.lwj.easymybatis.support.OrderEnum;
import com.lwj.flow.cert.entity.CertDictionary;
import com.lwj.flow.cert.entity.VO.CertDictionaryVO;
import com.lwj.flow.cert.entity.support.CertType;
import com.lwj.flow.cert.mapper.CertDictionaryMapper;
import com.lwj.flow.cert.service.CertDictionaryServices;
import com.lwj.flow.common.controller.BaseController;
import com.lwj.flow.common.entity.AjaxResponse;
import com.lwj.flow.common.entity.support.YesOrNo;
import com.lwj.flow.login.entity.Resources;
import com.lwj.flow.login.entity.User;
import com.lwj.flow.login.entity.vo.ResourceVO;

/**
 * 证书字典控制类
 * 
 * @author luwja
 *
 */
@Controller
@RequestMapping("/certdictionary")
public class CertDictionaryController extends BaseController{
	
	private Logger  logger = LoggerFactory.getLogger(CertDictionaryController.class);

	@Autowired
	private CertDictionaryMapper certDictionaryMapper;
	@Autowired
	private CertDictionaryServices  certDictionaryServices;

	@RequestMapping("/list")
	public String list() {

		return "certdictionary/list";
	}

	/**
	 * 获取树形资源结构
	 * 
	 * @return
	 */
	@RequestMapping("/getCertDicTree")
	@ResponseBody
	public AjaxResponse<CertDictionaryVO> getResoucesTree() {
		AjaxResponse<CertDictionaryVO> response = new AjaxResponse<CertDictionaryVO>();
		response.setStatus(true);
		Criteria criteria = new Criteria();
		criteria.putWhere("parentId", 0);
		criteria.putWhere("isValid", YesOrNo.Yes);
		criteria.putOrder("sort", OrderEnum.ASC);
		CertDictionary cert = certDictionaryMapper.read(criteria);
		CertDictionaryVO certVO = new CertDictionaryVO();
		certVO.setId(cert.getId());
		certVO.setDicName(cert.getDicName());
		certVO.setCertType(cert.getType());
		certVO.setParentId(cert.getParentId());
		certDictionaryServices.getChild(certVO);
		response.setEntity(certVO);
		return response;
	}
	
	/**
	 * 创建证书字典
	 * @param parentId 父节点id
	 * @param parentCertType父节点类型
	 * @param dicName要增加的节点的名称
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public AjaxResponse<CertDictionaryVO> create(int parentId,CertType parentCertType,String dicName) {
		AjaxResponse<CertDictionaryVO> response = new AjaxResponse<CertDictionaryVO>();
		boolean status = false;
		String errMsg="";
		CertDictionaryVO certVO = null;
		CertDictionary cert = null;

		if(parentId != 0 && parentCertType != null && !StringUtils.isEmpty(dicName)) {
			Criteria criteria = new Criteria();
			criteria.putWhere("id", parentId);
			criteria.putWhere("isValid", YesOrNo.Yes);
			//父字典
			CertDictionary parentCert = certDictionaryMapper.read(criteria);
			if(parentCert != null && parentCert.getType() == parentCertType) {
				cert = new CertDictionary();
				cert.setDicName(dicName);
				cert.setParentId(parentId);
				if(parentCertType == CertType.Cert){
					cert.setType(CertType.Trade);
				} else if (parentCertType == CertType.Trade) {
					cert.setType(CertType.Domain);
				} else if (parentCertType == CertType.Domain) {
					cert.setType(CertType.Level);
				}
				cert.setIsValid(YesOrNo.Yes);
				cert.setSort(certDictionaryMapper.getSortNumber());
				certDictionaryMapper.add(cert);
				 certVO = new CertDictionaryVO();
				certVO.setId(cert.getId());
				certVO.setDicName(cert.getDicName());
				certVO.setCertType(cert.getType());
				certVO.setParentId(cert.getParentId());
				status = true;
			} else {
				errMsg="创建证书字典异常";
				logger.error("创建证书字典,提交的父字典类型{}和数据库的父字典类型{}不匹配", parentCertType,parentCert);
			}
			
		} else {
			errMsg="创建证书字典异常";
			logger.error("创建证书字典提交参数异常", parentId);
		}
		response.setErrMsg(errMsg);
		response.setStatus(status);
		response.setEntity(certVO);
		return response;
	}
	
	/**
	 * 删除指定证书字典
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxResponse<Object> delete(int id) {
		AjaxResponse<Object> response = new AjaxResponse<Object>();
		boolean status = false;
		String errMsg="";

		if(id != 0 ) {
			Criteria criteria = new Criteria();
			criteria.putWhere("id", id);
			criteria.putUpdate("isValid", YesOrNo.No);
			//父字典
			certDictionaryMapper.update(criteria);
			status = true;

			
		} else {
			errMsg="删除证书字典异常";
			logger.error("删除证书字典提交参数异常,id:{}", id);
		}
		response.setErrMsg(errMsg);
		response.setStatus(status);
		response.setEntity(null);
		return response;
	}
	
	/**
	 * 更新证书字典名称
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public AjaxResponse<Object> edit(int id,String dicName) {
		AjaxResponse<Object> response = new AjaxResponse<Object>();
		boolean status = false;
		String errMsg="";

		if(id != 0 && !StringUtils.isEmpty(dicName)) {
			Criteria criteria = new Criteria();
			criteria.putWhere("id", id);
			criteria.putUpdate("dicName", dicName);
			certDictionaryMapper.update(criteria);
			status = true;

			
		} else {
			errMsg="更新证书字典异常";
			logger.error("更新证书字典提交参数异常,id:{},dicName:{}", id,dicName);
		}
		response.setErrMsg(errMsg);
		response.setStatus(status);
		response.setEntity(null);
		return response;
	}
	
	/**
	 * ajax验证名称是否存在
	 * @param dicName 证书字典名称
	 * @return
	 */
	@RequestMapping("/validateDicName")
	@ResponseBody
	public AjaxResponse<CertDictionary>  validateDicName(int parentId,String dicName){
		AjaxResponse<CertDictionary> response = new AjaxResponse<CertDictionary>();
		boolean status = true;
		String errMsg="";
		//用户名是否存在
		CertDictionary  dbCert = null;
		if(parentId !=0 && !StringUtils.isEmpty(dicName)){
			Criteria  criteria = new Criteria();
			criteria.putWhere("parentId", parentId);
			criteria.putWhere("dicName", dicName);
			criteria.putWhere("isValid", YesOrNo.Yes);
			 dbCert = certDictionaryMapper.read(criteria);
			
			
		} else {
			status=false;
			errMsg="请提交正确的证书字典名称";
		}
		
		response.setStatus(status);
		response.setErrMsg(errMsg);
		response.setEntity(dbCert);
		return response;
	}

}
