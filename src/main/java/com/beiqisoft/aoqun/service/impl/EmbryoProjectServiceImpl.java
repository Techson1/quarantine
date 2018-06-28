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
import com.beiqisoft.aoqun.entity.EmbryoProject;
import com.beiqisoft.aoqun.repository.EmbryoProjectRepository;
import com.beiqisoft.aoqun.service.EmbryoProjectService;

@Service
public class EmbryoProjectServiceImpl extends BaseServiceIml<EmbryoProject,EmbryoProjectRepository> implements EmbryoProjectService{

	@Autowired
	public EmbryoProjectRepository embryoProjectRepository;
	
	public Page<EmbryoProject> find(final EmbryoProject embryoProject) {
		return embryoProjectRepository.findAll(new Specification<EmbryoProject>() {
			public Predicate toPredicate(Root<EmbryoProject> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(embryoProject,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(embryoProject.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<EmbryoProject> find(EmbryoProject embryoProject, int size) {
		return embryoProjectRepository.findAll(new Specification<EmbryoProject>() {
			public Predicate toPredicate(Root<EmbryoProject> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public EmbryoProjectRepository getRepository() {
		return embryoProjectRepository;
	}

}
