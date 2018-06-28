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
import com.beiqisoft.aoqun.entity.FakeTypeSire;
import com.beiqisoft.aoqun.repository.FakeTypeSireRepository;
import com.beiqisoft.aoqun.service.FakeTypeSireService;

@Service
public class FakeTypeSireServiceImpl extends BaseServiceIml<FakeTypeSire,FakeTypeSireRepository> implements FakeTypeSireService{

	@Autowired
	public FakeTypeSireRepository fakeTypeSireRepository;
	
	public Page<FakeTypeSire> find(final FakeTypeSire fakeTypeSire) {
		return fakeTypeSireRepository.findAll(new Specification<FakeTypeSire>() {
			public Predicate toPredicate(Root<FakeTypeSire> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(fakeTypeSire,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(fakeTypeSire.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "flag"));
	}
	
	public Page<FakeTypeSire> find(FakeTypeSire fakeTypeSire, int size) {
		return fakeTypeSireRepository.findAll(new Specification<FakeTypeSire>() {
			public Predicate toPredicate(Root<FakeTypeSire> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public FakeTypeSireRepository getRepository() {
		return fakeTypeSireRepository;
	}

}
