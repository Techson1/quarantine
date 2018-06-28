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
import com.beiqisoft.aoqun.entity.Material;
import com.beiqisoft.aoqun.repository.MaterialRepository;
import com.beiqisoft.aoqun.service.MaterialService;

@Service
public class MaterialServiceImpl extends BaseServiceIml<Material,MaterialRepository> implements MaterialService{

	@Autowired
	public MaterialRepository materialRepository;
	
	public Page<Material> find(final Material material) {
		return materialRepository.findAll(new Specification<Material>() {
			public Predicate toPredicate(Root<Material> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(material,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(material.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Material> find(Material material, int size) {
		return materialRepository.findAll(new Specification<Material>() {
			public Predicate toPredicate(Root<Material> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public MaterialRepository getRepository() {
		return materialRepository;
	}

}
