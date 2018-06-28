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
import com.beiqisoft.aoqun.entity.guide.JoiningLead;
import com.beiqisoft.aoqun.repository.guide.JoiningLeadRepository;
import com.beiqisoft.aoqun.service.guide.JoiningLeadService;

@Service
public class JoiningLeadServiceImpl extends BaseServiceIml<JoiningLead,JoiningLeadRepository> implements JoiningLeadService{

	@Autowired
	public JoiningLeadRepository joiningLeadRepository;
	
	public Page<JoiningLead> find(final JoiningLead joiningLead) {
		return joiningLeadRepository.findAll(new Specification<JoiningLead>() {
			public Predicate toPredicate(Root<JoiningLead> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(joiningLead,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(0, GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<JoiningLead> find(JoiningLead joiningLead, int size) {
		return joiningLeadRepository.findAll(new Specification<JoiningLead>() {
			public Predicate toPredicate(Root<JoiningLead> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public JoiningLeadRepository getRepository() {
		return joiningLeadRepository;
	}

}
