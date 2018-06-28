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
import com.beiqisoft.aoqun.entity.OnHandMonth;
import com.beiqisoft.aoqun.repository.OnHandMonthRepository;
import com.beiqisoft.aoqun.service.OnHandMonthService;

@Service
public class OnHandMonthServiceImpl extends BaseServiceIml<OnHandMonth,OnHandMonthRepository> implements OnHandMonthService{

	@Autowired
	public OnHandMonthRepository onHandMonthRepository;
	
	public Page<OnHandMonth> find(final OnHandMonth onHandMonth) {
		return onHandMonthRepository.findAll(new Specification<OnHandMonth>() {
			public Predicate toPredicate(Root<OnHandMonth> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(onHandMonth,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(onHandMonth.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<OnHandMonth> find(OnHandMonth onHandMonth, int size) {
		return onHandMonthRepository.findAll(new Specification<OnHandMonth>() {
			public Predicate toPredicate(Root<OnHandMonth> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public OnHandMonthRepository getRepository() {
		return onHandMonthRepository;
	}

}
