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
import com.beiqisoft.aoqun.entity.Hint;
import com.beiqisoft.aoqun.repository.HintRepository;
import com.beiqisoft.aoqun.service.HintService;

@Service
public class HintServiceImpl extends BaseServiceIml<Hint,HintRepository> implements HintService{

	@Autowired
	public HintRepository hintRepository;
	
	public Page<Hint> find(final Hint hint) {
		return hintRepository.findAll(new Specification<Hint>() {
			public Predicate toPredicate(Root<Hint> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(hint,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(hint.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Hint> find(Hint hint, int size) {
		return hintRepository.findAll(new Specification<Hint>() {
			public Predicate toPredicate(Root<Hint> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public HintRepository getRepository() {
		return hintRepository;
	}

	@Override
	public List<Hint> findByList(Hint hint) {
		return hintRepository.findAll((root,query,criteriaBuilder)->{
			List<Predicate> list = getEntityPredicate(hint,root,criteriaBuilder);
			query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
			return query.getRestriction();
		});
	}
}
