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
import com.beiqisoft.aoqun.entity.Inventory;
import com.beiqisoft.aoqun.repository.InventoryRepository;
import com.beiqisoft.aoqun.service.InventoryService;

@Service
public class InventoryServiceImpl extends BaseServiceIml<Inventory,InventoryRepository> implements InventoryService{

	@Autowired
	public InventoryRepository inventoryRepository;
	
	public Page<Inventory> find(final Inventory inventory) {
		return inventoryRepository.findAll(new Specification<Inventory>() {
			public Predicate toPredicate(Root<Inventory> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(inventory,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(inventory.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Inventory> find(Inventory inventory, int size) {
		return inventoryRepository.findAll(new Specification<Inventory>() {
			public Predicate toPredicate(Root<Inventory> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public InventoryRepository getRepository() {
		return inventoryRepository;
	}

}
