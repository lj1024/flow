package com.lwj.flow.cert.mapper;

import com.lwj.easymybatis.common.CommonMaper;
import com.lwj.flow.cert.entity.CertDictionary;

public interface CertDictionaryMapper extends CommonMaper<CertDictionary> {
	
	
	public Integer getSortNumber();
}