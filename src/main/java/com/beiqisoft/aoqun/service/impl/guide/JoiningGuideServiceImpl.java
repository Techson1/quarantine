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
import com.beiqisoft.aoqun.entity.guide.JoiningGuide;
import com.beiqisoft.aoqun.repository.guide.JoiningGuideRepository;
import com.beiqisoft.aoqun.service.guide.JoiningGuideService;

@Service
public class JoiningGuideServiceImpl extends BaseServiceIml<JoiningGuide,JoiningGuideRepository> implements JoiningGuideService{

	@Autowired
	public JoiningGuideRepository joiningGuideRepository;
	
	public Page<JoiningGuide> find(final JoiningGuide joiningGuide) {
		return joiningGuideRepository.findAll(new Specification<JoiningGuide>() {
			public Predicate toPredicate(Root<JoiningGuide> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(joiningGuide,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(joiningGuide.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<JoiningGuide> find(JoiningGuide joiningGuide, int size) {
		return joiningGuideRepository.findAll(new Specification<JoiningGuide>() {
			public Predicate toPredicate(Root<JoiningGuide> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public JoiningGuideRepository getRepository() {
		return joiningGuideRepository;
	}

}
