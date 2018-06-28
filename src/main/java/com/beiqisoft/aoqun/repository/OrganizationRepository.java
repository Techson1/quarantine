package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Organization;

public interface OrganizationRepository extends BaseRepository<Organization>{
	
	/**
	 * 查询分场全称
	 * @param orgName
	 * 			分场全称
	 * @return List<Organization> 
	 * */
	List<Organization> findByOrgName(String orgName);
	
	/**
	 * 分厂查询
	 * */
	@Query(value="FROM Organization o WHERE o.orgName=?1")
	Organization findByOrgNameV(String orgName);
}
