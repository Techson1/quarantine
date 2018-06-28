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
import com.beiqisoft.aoqun.entity.Formula;
import com.beiqisoft.aoqun.repository.FormulaRepository;
import com.beiqisoft.aoqun.service.FormulaService;

@Service
public class FormulaServiceImpl extends BaseServiceIml<Formula,FormulaRepository> implements FormulaService{

	@Autowired
	public FormulaRepository formulaRepository;
	
	public Page<Formula> find(final Formula formula) {
		return formulaRepository.findAll(new Specification<Formula>() {
			public Predicate toPredicate(Root<Formula> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(formula,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(formula.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Formula> find(Formula formula, int size) {
		return formulaRepository.findAll(new Specification<Formula>() {
			public Predicate toPredicate(Root<Formula> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public FormulaRepository getRepository() {
		return formulaRepository;
	}

}
