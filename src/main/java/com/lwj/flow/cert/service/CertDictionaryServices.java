package com.lwj.flow.cert.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lwj.easymybatis.common.Criteria;
import com.lwj.easymybatis.support.OrderEnum;
import com.lwj.flow.cert.entity.CertDictionary;
import com.lwj.flow.cert.entity.VO.CertDictionaryVO;
import com.lwj.flow.cert.mapper.CertDictionaryMapper;
import com.lwj.flow.common.entity.support.YesOrNo;

@Service
public class CertDictionaryServices {
	
	
	@Autowired
	private CertDictionaryMapper certDictionaryMapper;
	
	
	/**
	 * 递归获取证书字典
	 * @param certDictionaryVO
	 * @return
	 */
	public CertDictionaryVO  getChild(CertDictionaryVO certDictionaryVO){
		if(certDictionaryVO != null ) {
			Criteria criteria = new Criteria();
			criteria.putWhere("parentId", certDictionaryVO.getId());
			criteria.putOrder("sort", OrderEnum.ASC);
			criteria.putWhere("isValid", YesOrNo.Yes);
			List<CertDictionaryVO> childs = new ArrayList<CertDictionaryVO>();
			certDictionaryVO.setChilds(childs);
			//主菜单
			List<CertDictionary> certList = certDictionaryMapper.query(criteria);
			if( !certList.isEmpty()) {
				for(CertDictionary  cert : certList){
					CertDictionaryVO certVO = new CertDictionaryVO();
					childs.add(certVO);
					certVO.setId(cert.getId());
					certVO.setDicName(cert.getDicName());
					certVO.setCertType(cert.getType());
					certVO.setParentId(cert.getParentId());

					getChild(certVO);
				}
			}
		}
		
		return certDictionaryVO;
	}

}
