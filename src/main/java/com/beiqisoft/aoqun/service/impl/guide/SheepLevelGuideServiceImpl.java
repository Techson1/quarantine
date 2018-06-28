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
import com.beiqisoft.aoqun.entity.guide.SheepLevelGuide;
import com.beiqisoft.aoqun.repository.guide.SheepLevelGuideRepository;
import com.beiqisoft.aoqun.service.guide.SheepLevelGuideService;

@Service
public class SheepLevelGuideServiceImpl extends BaseServiceIml<SheepLevelGuide,SheepLevelGuideRepository> implements SheepLevelGuideService{

	@Autowired
	public SheepLevelGuideRepository sheepLevelGuideRepository;
	
	public Page<SheepLevelGuide> find(final SheepLevelGuide sheepLevelGuide) {
		return sheepLevelGuideRepository.findAll(new Specification<SheepLevelGuide>() {
			public Predicate toPredicate(Root<SheepLevelGuide> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(sheepLevelGuide,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(sheepLevelGuide.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<SheepLevelGuide> find(SheepLevelGuide sheepLevelGuide, int size) {
		return sheepLevelGuideRepository.findAll(new Specification<SheepLevelGuide>() {
			public Predicate toPredicate(Root<SheepLevelGuide> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public SheepLevelGuideRepository getRepository() {
		return sheepLevelGuideRepository;
	}

}
