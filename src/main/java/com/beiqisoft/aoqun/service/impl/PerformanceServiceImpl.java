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
import com.beiqisoft.aoqun.entity.Performance;
import com.beiqisoft.aoqun.repository.PerformanceRepository;
import com.beiqisoft.aoqun.service.PerformanceService;

@Service
public class PerformanceServiceImpl extends BaseServiceIml<Performance,PerformanceRepository> implements PerformanceService{

	@Autowired
	public PerformanceRepository performanceRepository;
	
	public Page<Performance> find(final Performance performance) {
		return performanceRepository.findAll(new Specification<Performance>() {
			public Predicate toPredicate(Root<Performance> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(performance,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(performance.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Performance> find(Performance performance, int size) {
		return performanceRepository.findAll(new Specification<Performance>() {
			public Predicate toPredicate(Root<Performance> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public PerformanceRepository getRepository() {
		return performanceRepository;
	}

}
