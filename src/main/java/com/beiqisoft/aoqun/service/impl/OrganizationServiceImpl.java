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
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.repository.OrganizationRepository;
import com.beiqisoft.aoqun.service.OrganizationService;

@Service
public class OrganizationServiceImpl extends BaseServiceIml<Organization,OrganizationRepository> implements OrganizationService{

	@Autowired
	public OrganizationRepository organizationRepository;
	
	public Page<Organization> find(final Organization organization) {
		return organizationRepository.findAll(new Specification<Organization>() {
			public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(organization,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(organization.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Organization> find(Organization organization, int size) {
		return organizationRepository.findAll(new Specification<Organization>() {
			public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public OrganizationRepository getRepository() {
		return organizationRepository;
	}

}
