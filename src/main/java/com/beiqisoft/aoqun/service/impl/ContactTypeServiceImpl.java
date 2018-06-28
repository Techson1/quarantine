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
import com.beiqisoft.aoqun.entity.ContactType;
import com.beiqisoft.aoqun.repository.ContactTypeRepository;
import com.beiqisoft.aoqun.service.ContactTypeService;

@Service
public class ContactTypeServiceImpl extends BaseServiceIml<ContactType,ContactTypeRepository> implements ContactTypeService{

	@Autowired
	public ContactTypeRepository contactTypeRepository;
	
	public Page<ContactType> find(final ContactType contactType) {
		return contactTypeRepository.findAll(new Specification<ContactType>() {
			public Predicate toPredicate(Root<ContactType> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(contactType,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(contactType.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<ContactType> find(ContactType contactType, int size) {
		return contactTypeRepository.findAll(new Specification<ContactType>() {
			public Predicate toPredicate(Root<ContactType> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public ContactTypeRepository getRepository() {
		return contactTypeRepository;
	}

}
