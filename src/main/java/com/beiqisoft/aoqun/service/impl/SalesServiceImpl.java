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
import com.beiqisoft.aoqun.entity.Sales;
import com.beiqisoft.aoqun.repository.SalesRepository;
import com.beiqisoft.aoqun.service.SalesService;

@Service
public class SalesServiceImpl extends BaseServiceIml<Sales,SalesRepository> implements SalesService{

	@Autowired
	public SalesRepository salesRepository;
	
	public Page<Sales> find(final Sales sales) {
		return salesRepository.findAll(new Specification<Sales>() {
			public Predicate toPredicate(Root<Sales> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(sales,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(sales.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Sales> find(Sales sales, int size) {
		return salesRepository.findAll(new Specification<Sales>() {
			public Predicate toPredicate(Root<Sales> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public SalesRepository getRepository() {
		return salesRepository;
	}

}
