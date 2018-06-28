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
import com.beiqisoft.aoqun.entity.BreedingState;
import com.beiqisoft.aoqun.repository.BreedingStateRepository;
import com.beiqisoft.aoqun.service.BreedingStateService;

@Service
public class BreedingStateServiceImpl extends BaseServiceIml<BreedingState,BreedingStateRepository> implements BreedingStateService{

	@Autowired
	public BreedingStateRepository breedingStateRepository;
	
	public Page<BreedingState> find(final BreedingState breedingState) {
		return breedingStateRepository.findAll(new Specification<BreedingState>() {
			public Predicate toPredicate(Root<BreedingState> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(breedingState,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(breedingState.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<BreedingState> find(BreedingState breedingState, int size) {
		return breedingStateRepository.findAll(new Specification<BreedingState>() {
			public Predicate toPredicate(Root<BreedingState> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public BreedingStateRepository getRepository() {
		return breedingStateRepository;
	}

}
