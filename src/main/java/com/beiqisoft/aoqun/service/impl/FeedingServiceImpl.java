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
import com.beiqisoft.aoqun.entity.Feeding;
import com.beiqisoft.aoqun.repository.FeedingRepository;
import com.beiqisoft.aoqun.service.FeedingService;

@Service
public class FeedingServiceImpl extends BaseServiceIml<Feeding,FeedingRepository> implements FeedingService{

	@Autowired
	public FeedingRepository feedingRepository;
	
	public Page<Feeding> find(final Feeding feeding) {
		return feedingRepository.findAll(new Specification<Feeding>() {
			public Predicate toPredicate(Root<Feeding> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(feeding,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(feeding.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Feeding> find(Feeding feeding, int size) {
		return feedingRepository.findAll(new Specification<Feeding>() {
			public Predicate toPredicate(Root<Feeding> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public FeedingRepository getRepository() {
		return feedingRepository;
	}

}
