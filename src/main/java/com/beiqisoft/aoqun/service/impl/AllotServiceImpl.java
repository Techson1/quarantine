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
import com.beiqisoft.aoqun.entity.Allot;
import com.beiqisoft.aoqun.repository.AllotRepository;
import com.beiqisoft.aoqun.service.AllotService;

@Service
public class AllotServiceImpl extends BaseServiceIml<Allot,AllotRepository> implements AllotService{

	@Autowired
	public AllotRepository allotRepository;
	
	public Page<Allot> find(final Allot allot) {
		return allotRepository.findAll(new Specification<Allot>() {
			public Predicate toPredicate(Root<Allot> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(allot,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(allot.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Allot> find(Allot allot, int size) {
		return allotRepository.findAll(new Specification<Allot>() {
			public Predicate toPredicate(Root<Allot> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public AllotRepository getRepository() {
		return allotRepository;
	}

}
