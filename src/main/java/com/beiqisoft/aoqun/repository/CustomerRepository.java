package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Customer;

public interface CustomerRepository extends BaseRepository<Customer>{
	
	Customer findByFirstName(String firstName);

	/**
	 * 模糊查询
	 * */
	List<Customer> findByFirstNameLike(String firstName);
	/**
	 * 模糊查询加上场区区分
	 * */
	List<Customer> findByFirstNameLikeAndOrgId(String firstName,Long OrgId);
	/**
	 * 模糊查询
	 * */
	List<Customer> findByOrgId(Long orgId);
	
}
