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
import com.beiqisoft.aoqun.entity.guide.PregnancyGuide;
import com.beiqisoft.aoqun.repository.guide.PregnancyGuideRepository;
import com.beiqisoft.aoqun.service.guide.PregnancyGuideService;

@Service
public class PregnancyGuideServiceImpl extends BaseServiceIml<PregnancyGuide,PregnancyGuideRepository> implements PregnancyGuideService{

	@Autowired
	public PregnancyGuideRepository pregnancyGuideRepository;
	
	public Page<PregnancyGuide> find(final PregnancyGuide pregnancyGuide) {
		return pregnancyGuideRepository.findAll(new Specification<PregnancyGuide>() {
			public Predicate toPredicate(Root<PregnancyGuide> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(pregnancyGuide,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(pregnancyGuide.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<PregnancyGuide> find(PregnancyGuide pregnancyGuide, int size) {
		return pregnancyGuideRepository.findAll(new Specification<PregnancyGuide>() {
			public Predicate toPredicate(Root<PregnancyGuide> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public PregnancyGuideRepository getRepository() {
		return pregnancyGuideRepository;
	}

}
