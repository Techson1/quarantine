package com.beiqisoft.aoqun.service;

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
	
	Page<Customer> find(Customer customer, int pageNum);
	
}
