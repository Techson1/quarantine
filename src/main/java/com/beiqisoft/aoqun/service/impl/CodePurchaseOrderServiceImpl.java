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
import com.beiqisoft.aoqun.entity.CodePurchaseOrder;
import com.beiqisoft.aoqun.repository.CodePurchaseOrderRepository;
import com.beiqisoft.aoqun.service.CodePurchaseOrderService;

@SuppressWarnings("deprecation")
@Service
public class CodePurchaseOrderServiceImpl extends BaseServiceIml<CodePurchaseOrder,CodePurchaseOrderRepository> implements CodePurchaseOrderService{

	@Autowired
	public CodePurchaseOrderRepository codePurchaseOrderRepository;
	
	public Page<CodePurchaseOrder> find(final CodePurchaseOrder codePurchaseOrder) {
		return codePurchaseOrderRepository.findAll(new Specification<CodePurchaseOrder>() {
			public Predicate toPredicate(Root<CodePurchaseOrder> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(codePurchaseOrder,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(codePurchaseOrder.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<CodePurchaseOrder> find(CodePurchaseOrder codePurchaseOrder, int size) {
		return codePurchaseOrderRepository.findAll(new Specification<CodePurchaseOrder>() {
			public Predicate toPredicate(Root<CodePurchaseOrder> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public CodePurchaseOrderRepository getRepository() {
		return codePurchaseOrderRepository;
	}

}
