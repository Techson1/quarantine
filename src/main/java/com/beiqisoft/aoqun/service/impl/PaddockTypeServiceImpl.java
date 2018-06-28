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
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.PaddockType;
import com.beiqisoft.aoqun.repository.PaddockTypeRepository;
import com.beiqisoft.aoqun.service.PaddockTypeService;

/**@deprecated*/
@Service
public class PaddockTypeServiceImpl extends BaseServiceIml<PaddockType,PaddockTypeRepository> implements PaddockTypeService{

	@Autowired
	public PaddockTypeRepository paddockTypeRepository;
	
	public Page<PaddockType> find(final PaddockType paddockType) {
		return paddockTypeRepository.findAll(new Specification<PaddockType>() {
			public Predicate toPredicate(Root<PaddockType> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(paddockType,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(paddockType.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<PaddockType> find(PaddockType paddockType, int size) {
		return paddockTypeRepository.findAll(new Specification<PaddockType>() {
			public Predicate toPredicate(Root<PaddockType> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public PaddockTypeRepository getRepository() {
		return paddockTypeRepository;
	}

	@Override
	public Message saveReturnMessage(PaddockType paddockType) {
		paddockTypeRepository.save(paddockType);
		return GlobalConfig.SUCCESS;
	}

}
