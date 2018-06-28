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
import com.beiqisoft.aoqun.entity.InventoryDifference;
import com.beiqisoft.aoqun.repository.InventoryDifferenceRepository;
import com.beiqisoft.aoqun.service.InventoryDifferenceService;

@Service
public class InventoryDifferenceServiceImpl extends BaseServiceIml<InventoryDifference,InventoryDifferenceRepository> implements InventoryDifferenceService{

	@Autowired
	public InventoryDifferenceRepository inventoryDifferenceRepository;
	
	public Page<InventoryDifference> find(final InventoryDifference inventoryDifference) {
		return inventoryDifferenceRepository.findAll(new Specification<InventoryDifference>() {
			public Predicate toPredicate(Root<InventoryDifference> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(inventoryDifference,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(inventoryDifference.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "base.paddock"));
	}
	
	public Page<InventoryDifference> find(InventoryDifference inventoryDifference, int size) {
		return inventoryDifferenceRepository.findAll(new Specification<InventoryDifference>() {
			public Predicate toPredicate(Root<InventoryDifference> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public InventoryDifferenceRepository getRepository() {
		return inventoryDifferenceRepository;
	}

}
