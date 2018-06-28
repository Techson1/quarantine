package com.beiqisoft.aoqun.service.guide;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.guide.JoiningLead;
import com.beiqisoft.aoqun.repository.guide.JoiningLeadRepository;

public interface JoiningLeadService extends BaseService<JoiningLead, JoiningLeadRepository>{
	/**
	 * 分页获取用户对象
	 * @param joiningLead 查询条件
	 * @return
	 */
	Page<JoiningLead> find(JoiningLead joiningLead);
	
	Page<JoiningLead> find(JoiningLead joiningLead, int pageNum);
	
}
