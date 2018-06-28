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
import com.beiqisoft.aoqun.entity.Company;
import com.beiqisoft.aoqun.repository.CompanyRepository;
import com.beiqisoft.aoqun.service.CompanyService;

@Service
public class CompanyServiceImpl extends BaseServiceIml<Company,CompanyRepository> implements CompanyService{

	@Autowired
	public CompanyRepository companyRepository;
	
	public Page<Company> find(final Company company) {
		return companyRepository.findAll(new Specification<Company>() {
			public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(company,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(company.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Company> find(Company company, int size) {
		return companyRepository.findAll(new Specification<Company>() {
			public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public CompanyRepository getRepository() {
		return companyRepository;
	}

}
