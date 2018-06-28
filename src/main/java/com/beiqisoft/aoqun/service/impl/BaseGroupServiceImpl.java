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
import com.beiqisoft.aoqun.entity.BaseGroup;
import com.beiqisoft.aoqun.repository.BaseGroupRepository;
import com.beiqisoft.aoqun.service.BaseGroupService;

@Service
public class BaseGroupServiceImpl extends BaseServiceIml<BaseGroup,BaseGroupRepository> implements BaseGroupService{

	@Autowired
	public BaseGroupRepository baseGroupRepository;
	
	public Page<BaseGroup> find(final BaseGroup baseGroup) {
		return baseGroupRepository.findAll(new Specification<BaseGroup>() {
			public Predicate toPredicate(Root<BaseGroup> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(baseGroup,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(baseGroup.getPageNum(), 20, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<BaseGroup> find(BaseGroup baseGroup, int size) {
		return baseGroupRepository.findAll(new Specification<BaseGroup>() {
			public Predicate toPredicate(Root<BaseGroup> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public BaseGroupRepository getRepository() {
		return baseGroupRepository;
	}

	@Override
	public List<BaseGroup> findByList(BaseGroup baseGroup) {
		return baseGroupRepository.findAll((root,query,criteriaBuilder)->{
			List<Predicate> list = getEntityPredicate(baseGroup,root,criteriaBuilder);
			query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
			return query.getRestriction();
		});
	}
}
