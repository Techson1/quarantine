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
import com.beiqisoft.aoqun.entity.OnHand;
import com.beiqisoft.aoqun.repository.OnHandRepository;
import com.beiqisoft.aoqun.service.OnHandService;

@Service
public class OnHandServiceImpl extends BaseServiceIml<OnHand,OnHandRepository> implements OnHandService{

	@Autowired
	public OnHandRepository onHandRepository;
	
	public Page<OnHand> find(final OnHand onHand) {
		return onHandRepository.findAll(new Specification<OnHand>() {
			public Predicate toPredicate(Root<OnHand> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(onHand,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(onHand.getPageNum(), GlobalConfig.PAGE_MAX_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<OnHand> find(OnHand onHand, int size) {
		return onHandRepository.findAll(new Specification<OnHand>() {
			public Predicate toPredicate(Root<OnHand> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public OnHandRepository getRepository() {
		return onHandRepository;
	}

}
