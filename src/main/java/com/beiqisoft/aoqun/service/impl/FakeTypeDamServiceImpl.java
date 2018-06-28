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
import com.beiqisoft.aoqun.entity.FakeTypeDam;
import com.beiqisoft.aoqun.repository.FakeTypeDamRepository;
import com.beiqisoft.aoqun.service.FakeTypeDamService;

@Service
public class FakeTypeDamServiceImpl extends BaseServiceIml<FakeTypeDam,FakeTypeDamRepository> implements FakeTypeDamService{

	@Autowired
	public FakeTypeDamRepository fakeTypeDamRepository;
	
	public Page<FakeTypeDam> find(final FakeTypeDam fakeTypeDam) {
		return fakeTypeDamRepository.findAll(new Specification<FakeTypeDam>() {
			public Predicate toPredicate(Root<FakeTypeDam> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(fakeTypeDam,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(fakeTypeDam.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "flag"));
	}
	
	public Page<FakeTypeDam> find(FakeTypeDam fakeTypeDam, int size) {
		return fakeTypeDamRepository.findAll(new Specification<FakeTypeDam>() {
			public Predicate toPredicate(Root<FakeTypeDam> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public FakeTypeDamRepository getRepository() {
		return fakeTypeDamRepository;
	}
}
