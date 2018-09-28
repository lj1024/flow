package com.lwj.flow.cert.mapper;

import java.util.List;

import com.lwj.easymybatis.common.Criteria;
import com.lwj.flow.cert.entity.VO.CertVO;

public interface CertVOMapper {
	
	public List<CertVO>  page(Criteria cri);
	
	public long  count(Criteria cri);

}
