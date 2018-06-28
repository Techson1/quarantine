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
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.repository.ParityRepository;
import com.beiqisoft.aoqun.service.ParityService;
import com.beiqisoft.aoqun.util.DateUtils;

@Service
public class ParityServiceImpl extends BaseServiceIml<Parity,ParityRepository> implements ParityService{

	@Autowired
	public ParityRepository parityRepository;
	
	public Page<Parity> find(final Parity parity) {
		return parityRepository.findAll(new Specification<Parity>() {
			public Predicate toPredicate(Root<Parity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(parity,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(parity.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Parity> find(Parity parity, int size) {
		return parityRepository.findAll(new Specification<Parity>() {
			public Predicate toPredicate(Root<Parity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public ParityRepository getRepository() {
		return parityRepository;
	}

	@Override
	public Message add(BaseInfo dam) {
		parityRepository.save(new Parity(dam));
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message add(BaseInfo dam, Parity parity) {
		parityRepository.save(new Parity(dam,parity));
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message addHistory(BaseInfo dam) {
		Parity parity= new Parity(dam);
		parity.setStartDate(DateUtils.dateAddInteger(dam.getBirthDay(), 240));
		parityRepository.save(parity);
		return GlobalConfig.SUCCESS;
	}

}
