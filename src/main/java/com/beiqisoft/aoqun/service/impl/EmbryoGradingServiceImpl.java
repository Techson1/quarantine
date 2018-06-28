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
import com.beiqisoft.aoqun.entity.EmbryoGrading;
import com.beiqisoft.aoqun.repository.EmbryoGradingRepository;
import com.beiqisoft.aoqun.service.EmbryoGradingService;

@Service
public class EmbryoGradingServiceImpl extends BaseServiceIml<EmbryoGrading,EmbryoGradingRepository> implements EmbryoGradingService{

	@Autowired
	public EmbryoGradingRepository embryoGradingRepository;
	
	public Page<EmbryoGrading> find(final EmbryoGrading embryoGrading) {
		return embryoGradingRepository.findAll(new Specification<EmbryoGrading>() {
			public Predicate toPredicate(Root<EmbryoGrading> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(embryoGrading,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(embryoGrading.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<EmbryoGrading> find(EmbryoGrading embryoGrading, int size) {
		return embryoGradingRepository.findAll(new Specification<EmbryoGrading>() {
			public Predicate toPredicate(Root<EmbryoGrading> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public EmbryoGradingRepository getRepository() {
		return embryoGradingRepository;
	}

}
