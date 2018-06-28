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
import com.beiqisoft.aoqun.entity.Lambing;
import com.beiqisoft.aoqun.repository.LambingRepository;
import com.beiqisoft.aoqun.service.LambingService;

/**
 * 羔羊生产业务处理实现类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 * @deprecated
 */
@Service
public class LambingServiceImpl extends BaseServiceIml<Lambing,LambingRepository> implements LambingService{

	@Autowired
	public LambingRepository lambingRepository;
	
	public Page<Lambing> find(final Lambing lambing) {
		return lambingRepository.findAll(new Specification<Lambing>() {
			public Predicate toPredicate(Root<Lambing> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(lambing,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(lambing.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Lambing> find(Lambing lambing, int size) {
		return lambingRepository.findAll(new Specification<Lambing>() {
			public Predicate toPredicate(Root<Lambing> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public LambingRepository getRepository() {
		return lambingRepository;
	}

}
