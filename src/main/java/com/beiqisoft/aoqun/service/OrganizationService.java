package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.repository.OrganizationRepository;

public interface OrganizationService extends BaseService<Organization, OrganizationRepository>{
	/**
	 * 分页获取用户对象
	 * @param organization 查询条件
	 * @return
	 */
	Page<Organization> find(Organization organization);
	
	Page<Organization> find(Organization organization, int pageNum);
	
}
