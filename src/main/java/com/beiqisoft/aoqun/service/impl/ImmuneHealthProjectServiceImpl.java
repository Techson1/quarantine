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
import com.beiqisoft.aoqun.entity.ImmuneHealthProject;
import com.beiqisoft.aoqun.repository.ImmuneHealthProjectRepository;
import com.beiqisoft.aoqun.service.ImmuneHealthProjectService;

@Service
public class ImmuneHealthProjectServiceImpl extends BaseServiceIml<ImmuneHealthProject,ImmuneHealthProjectRepository> implements ImmuneHealthProjectService{

	@Autowired
	public ImmuneHealthProjectRepository immuneHealthProjectRepository;
	
	public Page<ImmuneHealthProject> find(final ImmuneHealthProject immuneHealthProject) {
		return immuneHealthProjectRepository.findAll(new Specification<ImmuneHealthProject>() {
			public Predicate toPredicate(Root<ImmuneHealthProject> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(immuneHealthProject,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(immuneHealthProject.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<ImmuneHealthProject> find(ImmuneHealthProject immuneHealthProject, int size) {
		return immuneHealthProjectRepository.findAll(new Specification<ImmuneHealthProject>() {
			public Predicate toPredicate(Root<ImmuneHealthProject> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public ImmuneHealthProjectRepository getRepository() {
		return immuneHealthProjectRepository;
	}

}
