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
import com.beiqisoft.aoqun.entity.FeedGroup;
import com.beiqisoft.aoqun.repository.FeedGroupRepository;
import com.beiqisoft.aoqun.service.FeedGroupService;

@Service
public class FeedGroupServiceImpl extends BaseServiceIml<FeedGroup,FeedGroupRepository> implements FeedGroupService{

	@Autowired
	public FeedGroupRepository feedGroupRepository;
	
	public Page<FeedGroup> find(final FeedGroup feedGroup) {
		return feedGroupRepository.findAll(new Specification<FeedGroup>() {
			public Predicate toPredicate(Root<FeedGroup> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(feedGroup,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(feedGroup.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<FeedGroup> find(FeedGroup feedGroup, int size) {
		return feedGroupRepository.findAll(new Specification<FeedGroup>() {
			public Predicate toPredicate(Root<FeedGroup> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public FeedGroupRepository getRepository() {
		return feedGroupRepository;
	}

}
