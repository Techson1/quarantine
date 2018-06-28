package com.beiqisoft.aoqun.service.impl;

import java.util.ArrayList;
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
import com.beiqisoft.aoqun.entity.BreedingPlan;
import com.beiqisoft.aoqun.repository.BreedingPlanRepository;
import com.beiqisoft.aoqun.service.BreedingPlanService;

@Service
public class BreedingPlanServiceImpl extends BaseServiceIml<BreedingPlan,BreedingPlanRepository> implements BreedingPlanService{

	@Autowired
	public BreedingPlanRepository breedingPlanRepository;
	
	public Page<BreedingPlan> find(final BreedingPlan breedingPlan) {
		return breedingPlanRepository.findAll(new Specification<BreedingPlan>() {
			public Predicate toPredicate(Root<BreedingPlan> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(breedingPlan,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(breedingPlan.getPageNum(), 50, Sort.Direction.DESC,"flag"));
	}
	
	public Page<BreedingPlan> find(BreedingPlan breedingPlan, int size) {
		return breedingPlanRepository.findAll(new Specification<BreedingPlan>() {
			public Predicate toPredicate(Root<BreedingPlan> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public BreedingPlanRepository getRepository() {
		return breedingPlanRepository;
	}

	@Override
	public Page<BreedingPlan> Specialfind(BreedingPlan breedingPlan) {
		return breedingPlanRepository.findAll(new Specification<BreedingPlan>() {
			public Predicate toPredicate(Root<BreedingPlan> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				   List<Predicate> predicates = new ArrayList<Predicate>();  
				   
				   if (breedingPlan.getName()!=null && !"".equals(breedingPlan.getName())){
					   predicates.add(criteriaBuilder.equal(root.<String>get("name"),breedingPlan.getName()));
				   }
				   /*if (breedingPlan.getDamBreed()!=null){
					   predicates.add(criteriaBuilder.);
				   }*/
				return query.getRestriction();
			
			}
		},new PageRequest(breedingPlan.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}

}
