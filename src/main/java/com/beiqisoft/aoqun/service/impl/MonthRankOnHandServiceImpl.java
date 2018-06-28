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
import com.beiqisoft.aoqun.entity.MonthRankOnHand;
import com.beiqisoft.aoqun.repository.MonthRankOnHandRepository;
import com.beiqisoft.aoqun.service.MonthRankOnHandService;

@Service
public class MonthRankOnHandServiceImpl extends BaseServiceIml<MonthRankOnHand,MonthRankOnHandRepository> implements MonthRankOnHandService{

	@Autowired
	public MonthRankOnHandRepository monthRankOnHandRepository;
	
	public Page<MonthRankOnHand> find(final MonthRankOnHand monthRankOnHand) {
		return monthRankOnHandRepository.findAll(new Specification<MonthRankOnHand>() {
			public Predicate toPredicate(Root<MonthRankOnHand> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(monthRankOnHand,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(monthRankOnHand.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<MonthRankOnHand> find(MonthRankOnHand monthRankOnHand, int size) {
		return monthRankOnHandRepository.findAll(new Specification<MonthRankOnHand>() {
			public Predicate toPredicate(Root<MonthRankOnHand> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}
	
	public List<MonthRankOnHand> findList(MonthRankOnHand monthRankOnHand){
		return monthRankOnHandRepository.findAll((root,query,criteriaBuilder) ->{
			List<Predicate> list = getEntityPredicate(monthRankOnHand,root,criteriaBuilder);
			query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
			query.orderBy(orders("desc,desc,desc,ace,ace","breed,sex,rankName,age,moonAge").queryOrder(root,criteriaBuilder));
			return query.getRestriction();
		});
	}
	public MonthRankOnHandRepository getRepository() {
		return monthRankOnHandRepository;
	}

}
