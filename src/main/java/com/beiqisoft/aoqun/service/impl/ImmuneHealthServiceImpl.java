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
import com.beiqisoft.aoqun.entity.ImmuneHealth;
import com.beiqisoft.aoqun.repository.ImmuneHealthRepository;
import com.beiqisoft.aoqun.service.ImmuneHealthService;

@Service
public class ImmuneHealthServiceImpl extends BaseServiceIml<ImmuneHealth,ImmuneHealthRepository> implements ImmuneHealthService{

	@Autowired
	public ImmuneHealthRepository immuneHealthRepository;
	
	public Page<ImmuneHealth> find(final ImmuneHealth immuneHealth) {
		return immuneHealthRepository.findAll(new Specification<ImmuneHealth>() {
			public Predicate toPredicate(Root<ImmuneHealth> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(immuneHealth,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(immuneHealth.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<ImmuneHealth> find(ImmuneHealth immuneHealth, int size) {
		return immuneHealthRepository.findAll(new Specification<ImmuneHealth>() {
			public Predicate toPredicate(Root<ImmuneHealth> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public ImmuneHealthRepository getRepository() {
		return immuneHealthRepository;
	}

}
