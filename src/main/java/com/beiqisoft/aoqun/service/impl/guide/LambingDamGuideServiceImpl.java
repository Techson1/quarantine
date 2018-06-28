package com.beiqisoft.aoqun.service.impl.guide;

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
import com.beiqisoft.aoqun.entity.guide.LambingDamGuide;
import com.beiqisoft.aoqun.repository.guide.LambingDamGuideRepository;
import com.beiqisoft.aoqun.service.guide.LambingDamGuideService;

@Service
public class LambingDamGuideServiceImpl extends BaseServiceIml<LambingDamGuide,LambingDamGuideRepository> implements LambingDamGuideService{

	@Autowired
	public LambingDamGuideRepository lambingDamGuideRepository;
	
	public Page<LambingDamGuide> find(final LambingDamGuide lambingDamGuide) {
		return lambingDamGuideRepository.findAll(new Specification<LambingDamGuide>() {
			public Predicate toPredicate(Root<LambingDamGuide> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(lambingDamGuide,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(lambingDamGuide.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	@Override
	public Page<LambingDamGuide> find(LambingDamGuide lambingDamGuide, int size) {
		return lambingDamGuideRepository.findAll(new Specification<LambingDamGuide>() {
			public Predicate toPredicate(Root<LambingDamGuide> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public LambingDamGuideRepository getRepository() {
		return lambingDamGuideRepository;
	}

}
