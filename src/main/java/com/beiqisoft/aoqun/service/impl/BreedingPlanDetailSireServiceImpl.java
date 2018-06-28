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
import com.beiqisoft.aoqun.entity.BreedingPlanDetailSire;
import com.beiqisoft.aoqun.repository.BreedingPlanDetailSireRepository;
import com.beiqisoft.aoqun.service.BreedingPlanDetailSireService;

@Service
public class BreedingPlanDetailSireServiceImpl extends BaseServiceIml<BreedingPlanDetailSire,BreedingPlanDetailSireRepository> implements BreedingPlanDetailSireService{

	@Autowired
	public BreedingPlanDetailSireRepository breedingPlanDetailSireRepository;
	
	public Page<BreedingPlanDetailSire> find(final BreedingPlanDetailSire breedingPlanDetailSire) {
		return breedingPlanDetailSireRepository.findAll(new Specification<BreedingPlanDetailSire>() {
			public Predicate toPredicate(Root<BreedingPlanDetailSire> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(breedingPlanDetailSire,root,criteriaBuilder);
				
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(breedingPlanDetailSire.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<BreedingPlanDetailSire> find(BreedingPlanDetailSire breedingPlanDetailSire, int size) {
		return breedingPlanDetailSireRepository.findAll(new Specification<BreedingPlanDetailSire>() {
			public Predicate toPredicate(Root<BreedingPlanDetailSire> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public BreedingPlanDetailSireRepository getRepository() {
		return breedingPlanDetailSireRepository;
	}

}
