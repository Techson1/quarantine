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
import com.beiqisoft.aoqun.entity.BreedParameter;
import com.beiqisoft.aoqun.repository.BreedParameterRepository;
import com.beiqisoft.aoqun.service.BreedParameterService;

@Service
public class BreedParameterServiceImpl extends BaseServiceIml<BreedParameter,BreedParameterRepository> implements BreedParameterService{

	@Autowired
	public BreedParameterRepository breedParameterRepository;
	
	public Page<BreedParameter> find(final BreedParameter breedParameter) {
		return breedParameterRepository.findAll(new Specification<BreedParameter>() {
			public Predicate toPredicate(Root<BreedParameter> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(breedParameter,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(breedParameter.getPageNum(), GlobalConfig.PAGE_MAX_SIZE, Sort.Direction.DESC, "id"));
	}
	
	public Page<BreedParameter> find(BreedParameter breedParameter, int size) {
		return breedParameterRepository.findAll(new Specification<BreedParameter>() {
			public Predicate toPredicate(Root<BreedParameter> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public BreedParameterRepository getRepository() {
		return breedParameterRepository;
	}

}
