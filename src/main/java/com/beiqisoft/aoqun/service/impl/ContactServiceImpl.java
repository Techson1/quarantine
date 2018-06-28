package com.beiqisoft.aoqun.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
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
import com.beiqisoft.aoqun.entity.Contact;
import com.beiqisoft.aoqun.repository.ContactRepository;
import com.beiqisoft.aoqun.service.ContactService;

@Service
public class ContactServiceImpl extends BaseServiceIml<Contact,ContactRepository> implements ContactService{

	@Autowired
	public ContactRepository contactRepository;
	
	public Page<Contact> find(final Contact contact) {
		return contactRepository.findAll(new Specification<Contact>() {
			public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(contact,root,criteriaBuilder);
				
				if (contact.getContactType()!=null && contact.getContactType().getId()!=null){
					list.add(criteriaBuilder.equal(root.join("contactType", JoinType.INNER).get("id"), contact.getContactType().getId()));
				}
				else{
					list.add(criteriaBuilder.notEqual(root.join("contactType",JoinType.INNER).get("type"),"厂长"));
					list.add(criteriaBuilder.notEqual(root.join("contactType",JoinType.INNER).get("type"),"兽医"));
					list.add(criteriaBuilder.notEqual(root.join("contactType",JoinType.INNER).get("type"),"采购商"));
					list.add(criteriaBuilder.notEqual(root.join("contactType",JoinType.INNER).get("type"),"供应商"));
					list.add(criteriaBuilder.notEqual(root.join("contactType",JoinType.INNER).get("type"),"技术员"));
				}
				
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(contact.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "flag","contactType","firstName"));
	}
	
	public Page<Contact> find(Contact contact, int size) {
		return contactRepository.findAll(new Specification<Contact>() {
			public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public ContactRepository getRepository() {
		return contactRepository;
	}

	@Override
	public Page<Contact> contactList(Contact contact) {
		return contactRepository.findAll((root,query,criteriaBuilder) ->{
			List<Predicate> list = getEntityPredicate(contact,root,criteriaBuilder);
			if (contact.getContactType()!=null){
				list.add(criteriaBuilder.equal(root.join("contactType", JoinType.INNER).get("id"), contact.getContactType().getId()));
			}
			else{
				list.add(criteriaBuilder.notEqual(root.join("contactType",JoinType.INNER).get("type"),"厂长"));
				list.add(criteriaBuilder.notEqual(root.join("contactType",JoinType.INNER).get("type"),"兽医"));
				list.add(criteriaBuilder.notEqual(root.join("contactType",JoinType.INNER).get("type"),"采购商"));
				list.add(criteriaBuilder.notEqual(root.join("contactType",JoinType.INNER).get("type"),"供应商"));
				list.add(criteriaBuilder.notEqual(root.join("contactType",JoinType.INNER).get("type"),"技术员"));
			}
			query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
			return query.getRestriction();
			
		},new PageRequest(contact.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}

}
