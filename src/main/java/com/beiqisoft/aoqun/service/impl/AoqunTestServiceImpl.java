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
import com.beiqisoft.aoqun.entity.AoqunTest;
import com.beiqisoft.aoqun.repository.AoqunTestRepository;
import com.beiqisoft.aoqun.service.AoqunTestService;

@Service
public class AoqunTestServiceImpl extends BaseServiceIml<AoqunTest,AoqunTestRepository> implements AoqunTestService{

	@Autowired
	public AoqunTestRepository aoqunTestRepository;
	
	public Page<AoqunTest> find(final AoqunTest aoqunTest) {
		return aoqunTestRepository.findAll(new Specification<AoqunTest>() {
			public Predicate toPredicate(Root<AoqunTest> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(aoqunTest,root,criteriaBuilder);
				//list.add(criteriaBuilder.(root.join("org", JoinType.LEFT).get("id"), 1L));
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(aoqunTest.getPageNum(),15, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<AoqunTest> find(AoqunTest aoqunTest, int size) {
		return aoqunTestRepository.findAll(new Specification<AoqunTest>() {
			public Predicate toPredicate(Root<AoqunTest> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}
	
	

	public AoqunTestRepository getRepository() {
		return aoqunTestRepository;
	}

	@Override
	public Page<AoqunTest> findTest(AoqunTest aoqunTest) {
		return aoqunTestRepository.findAll(new Specification<AoqunTest>() {
			public Predicate toPredicate(Root<AoqunTest> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(aoqunTest.getPageNum(),5, Sort.Direction.DESC, "ctime"));
	}
}
