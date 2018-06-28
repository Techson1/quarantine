package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Company;
import com.beiqisoft.aoqun.repository.CompanyRepository;

public interface CompanyService extends BaseService<Company, CompanyRepository>{
	/**
	 * 分页获取用户对象
	 * @param company 查询条件
	 * @return
	 */
	Page<Company> find(Company company);
	
	Page<Company> find(Company company, int pageNum);
	
}
