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
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.repository.BreedRepository;
import com.beiqisoft.aoqun.service.BreedService;

@Service
public class BreedServiceImpl extends BaseServiceIml<Breed,BreedRepository> implements BreedService{

	@Autowired
	public BreedRepository breedRepository;
	
	public Page<Breed> find(final Breed breed) {
		return breedRepository.findAll(new Specification<Breed>() {
			public Predicate toPredicate(Root<Breed> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(breed,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(breed.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.ASC, "breedType"));
	}
	
	public Page<Breed> find(Breed breed, int size) {
		return breedRepository.findAll(new Specification<Breed>() {
			public Predicate toPredicate(Root<Breed> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public BreedRepository getRepository() {
		return breedRepository;
	}

	@Override
	public Breed findByComparisonbreedIds(Breed damBreed, Breed sireBreed) {
		//如果id一致说明品种一样
		if (damBreed.getId().equals(sireBreed.getId())){
			return damBreed;
		}
		//计算出两个品种间的血统,并根据血统查询品种
		Breed breed=breedRepository.findByBreedIds(damBreed.getBloodIncludeBlood(sireBreed.getBloodOrId()));
		//如果品种等于空,那么该品种因是杂种
		if (breed==null){
			return breedRepository.findByBreedName("杂种");
		}
		return breed;
	}

}
