package com.beiqisoft.aoqun.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.entity.Customer;
import com.beiqisoft.aoqun.repository.CustomerRepository;
import com.beiqisoft.aoqun.service.CustomerService;

@Service
public class CustomerServiceImpl extends BaseServiceIml<Customer,CustomerRepository> implements CustomerService{

	@Autowired
	public CustomerRepository customerRepository;
	
	public Page<Customer> find(final Customer customer) {
		return customerRepository.findAll(new Specification<Customer>() {
			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(customer,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(customer.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Customer> find(Customer customer, int size) {
		return customerRepository.findAll(new Specification<Customer>() {
			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}
   /**
    * 获取该组织机构下的 客户
    * @param orgid
    * @return
    */
	public List<Customer> findByOrgid(Long orgid){
		
		return customerRepository.findByOrgId(orgid);
		
	}
	public CustomerRepository getRepository() {
		return customerRepository;
	}
	/**
	 * 查询客户
	 * @param firstName 名称
	 * @param orgid 场区id
	 * @return
	 */
	public List<Customer> findByFirstNameLikeAndOrgId(String firstName,Long orgid){
		return customerRepository.findByFirstNameLikeAndOrgId(firstName, orgid);
	}
}
