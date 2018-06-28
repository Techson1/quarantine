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
import com.beiqisoft.aoqun.entity.Sterilize;
import com.beiqisoft.aoqun.repository.SterilizeRepository;
import com.beiqisoft.aoqun.service.SterilizeService;

@Service
public class SterilizeServiceImpl extends BaseServiceIml<Sterilize,SterilizeRepository> implements SterilizeService{

	@Autowired
	public SterilizeRepository sterilizeRepository;
	
	public Page<Sterilize> find(final Sterilize sterilize) {
		return sterilizeRepository.findAll(new Specification<Sterilize>() {
			public Predicate toPredicate(Root<Sterilize> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(sterilize,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(sterilize.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Sterilize> find(Sterilize sterilize, int size) {
		return sterilizeRepository.findAll(new Specification<Sterilize>() {
			public Predicate toPredicate(Root<Sterilize> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public SterilizeRepository getRepository() {
		return sterilizeRepository;
	}

}
