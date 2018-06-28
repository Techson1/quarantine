package com.beiqisoft.aoqun.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
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
import com.beiqisoft.aoqun.entity.DeathDisposalReason;
import com.beiqisoft.aoqun.repository.DeathDisposalReasonRepository;
import com.beiqisoft.aoqun.service.DeathDisposalReasonService;

@Service
public class DeathDisposalReasonServiceImpl extends BaseServiceIml<DeathDisposalReason,DeathDisposalReasonRepository> implements DeathDisposalReasonService{

	@Autowired
	public DeathDisposalReasonRepository deathDisposalReasonRepository;
	
	public Page<DeathDisposalReason> find(final DeathDisposalReason deathDisposalReason) {
		return deathDisposalReasonRepository.findAll(new Specification<DeathDisposalReason>() {
			public Predicate toPredicate(Root<DeathDisposalReason> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(deathDisposalReason,root,criteriaBuilder);
				
				if (deathDisposalReason.getParent()!=null){
					Join<DeathDisposalReason,DeathDisposalReason> join = root.join("parent", JoinType.INNER);
					list.add(criteriaBuilder.equal(join.get("id"), deathDisposalReason.getParent().getId()));
				}
				
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(deathDisposalReason.getPageNum(), 400, Sort.Direction.ASC, "flag"));
	}
	
	public Page<DeathDisposalReason> find(DeathDisposalReason deathDisposalReason, int size) {
		return deathDisposalReasonRepository.findAll(new Specification<DeathDisposalReason>() {
			public Predicate toPredicate(Root<DeathDisposalReason> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.ASC, "flag"));
	}

	public DeathDisposalReasonRepository getRepository(){
		return deathDisposalReasonRepository;
	}

}
