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
import com.beiqisoft.aoqun.entity.ImmunePlan;
import com.beiqisoft.aoqun.repository.ImmunePlanRepository;
import com.beiqisoft.aoqun.service.ImmunePlanService;

@Service
public class ImmunePlanServiceImpl extends BaseServiceIml<ImmunePlan,ImmunePlanRepository> implements ImmunePlanService{

	@Autowired
	public ImmunePlanRepository immunePlanRepository;
	
	public Page<ImmunePlan> find(final ImmunePlan immunePlan) {
		return immunePlanRepository.findAll(new Specification<ImmunePlan>() {
			public Predicate toPredicate(Root<ImmunePlan> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(immunePlan,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(immunePlan.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<ImmunePlan> find(ImmunePlan immunePlan, int size) {
		return immunePlanRepository.findAll(new Specification<ImmunePlan>() {
			public Predicate toPredicate(Root<ImmunePlan> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public ImmunePlanRepository getRepository() {
		return immunePlanRepository;
	}

}
