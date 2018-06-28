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
import com.beiqisoft.aoqun.entity.BreedingOnHand;
import com.beiqisoft.aoqun.repository.BreedingOnHandRepository;
import com.beiqisoft.aoqun.service.BreedingOnHandService;

@Service
public class BreedingOnHandServiceImpl extends BaseServiceIml<BreedingOnHand,BreedingOnHandRepository> implements BreedingOnHandService{

	@Autowired
	public BreedingOnHandRepository breedingOnHandRepository;
	
	public Page<BreedingOnHand> find(final BreedingOnHand breedingOnHand) {
		return breedingOnHandRepository.findAll(new Specification<BreedingOnHand>() {
			public Predicate toPredicate(Root<BreedingOnHand> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(breedingOnHand,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(breedingOnHand.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<BreedingOnHand> find(BreedingOnHand breedingOnHand, int size) {
		return breedingOnHandRepository.findAll(new Specification<BreedingOnHand>() {
			public Predicate toPredicate(Root<BreedingOnHand> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public BreedingOnHandRepository getRepository() {
		return breedingOnHandRepository;
	}

	@Override
	public List<BreedingOnHand> findByList(BreedingOnHand breedingOnHand) {
		return breedingOnHandRepository.findAll((root,query,criteriaBuilder)->{
			List<Predicate> list = getEntityPredicate(breedingOnHand,root,criteriaBuilder);
			query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
			query.orderBy(orders("desc,desc,asc", "breed,sex,breedingState").queryOrder(root, criteriaBuilder));
			return query.getRestriction();
		});
	}

}
