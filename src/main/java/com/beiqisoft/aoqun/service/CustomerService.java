package com.beiqisoft.aoqun.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Customer;
import com.beiqisoft.aoqun.repository.CustomerRepository;

public interface CustomerService extends BaseService<Customer, CustomerRepository>{
	/**
	 * 分页获取用户对象
	 * @param customer 查询条件
	 * @return
	 */
	Page<Customer> find(Customer customer);
	 /**
	    * 获取该组织机构下的 客户
	    * @param orgid
	    * @return
	    */
	public List<Customer> findByOrgid(Long orgid);
	Page<Customer> find(Customer customer, int pageNum);
	/**
	 * 查询客户
	 * @param firstName 名称
	 * @param orgid 场区id
	 * @return
	 */
	public List<Customer> findByFirstNameLikeAndOrgId(String firstName,Long orgid);
	
}
