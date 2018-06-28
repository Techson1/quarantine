package com.beiqisoft.aoqun.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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
import com.beiqisoft.aoqun.entity.rep.DamBreedStateRep;
import com.beiqisoft.aoqun.repository.DamBreedStateRepRepository;
import com.beiqisoft.aoqun.service.DamBreedStateRepService;

@Service
public class DamBreedStateRepServiceImpl extends BaseServiceIml<DamBreedStateRep,DamBreedStateRepRepository> implements DamBreedStateRepService{

	@Autowired
	public DamBreedStateRepRepository damBreedStateRepRepository;
	
	public Page<DamBreedStateRep> find(final DamBreedStateRep damBreedStateRep) {
		return damBreedStateRepRepository.findAll(new Specification<DamBreedStateRep>() {
			public Predicate toPredicate(Root<DamBreedStateRep> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(damBreedStateRep,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(damBreedStateRep.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<DamBreedStateRep> find(DamBreedStateRep damBreedStateRep, int size) {
		return damBreedStateRepRepository.findAll(new Specification<DamBreedStateRep>() {
			public Predicate toPredicate(Root<DamBreedStateRep> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public DamBreedStateRepRepository getRepository() {
		return damBreedStateRepRepository;
	}

	@Override
	public List<DamBreedStateRep> statistics() {
		return damBreedStateRepRepository.findByStatistics().stream().map(x->new DamBreedStateRep(x)).collect(Collectors.toList());
	}

}
