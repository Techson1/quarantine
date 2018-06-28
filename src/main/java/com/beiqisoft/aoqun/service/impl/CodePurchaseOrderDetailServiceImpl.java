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
import com.beiqisoft.aoqun.entity.CodePurchaseOrderDetail;
import com.beiqisoft.aoqun.repository.CodePurchaseOrderDetailRepository;
import com.beiqisoft.aoqun.service.CodePurchaseOrderDetailService;

/**
 *  @deprecated
 * */
@Service
public class CodePurchaseOrderDetailServiceImpl extends BaseServiceIml<CodePurchaseOrderDetail,CodePurchaseOrderDetailRepository> implements CodePurchaseOrderDetailService{

	@Autowired
	public CodePurchaseOrderDetailRepository codePurchaseOrderDetailRepository;
	
	public Page<CodePurchaseOrderDetail> find(final CodePurchaseOrderDetail codePurchaseOrderDetail) {
		return codePurchaseOrderDetailRepository.findAll(new Specification<CodePurchaseOrderDetail>() {
			public Predicate toPredicate(Root<CodePurchaseOrderDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(codePurchaseOrderDetail,root,criteriaBuilder);
				
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(codePurchaseOrderDetail.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<CodePurchaseOrderDetail> findTest(final CodePurchaseOrderDetail codePurchaseOrderDetail) {
		return codePurchaseOrderDetailRepository.findAll(new Specification<CodePurchaseOrderDetail>() {
			public Predicate toPredicate(Root<CodePurchaseOrderDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(codePurchaseOrderDetail,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(codePurchaseOrderDetail.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<CodePurchaseOrderDetail> find(CodePurchaseOrderDetail codePurchaseOrderDetail, int size) {
		return codePurchaseOrderDetailRepository.findAll(new Specification<CodePurchaseOrderDetail>() {
			public Predicate toPredicate(Root<CodePurchaseOrderDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public CodePurchaseOrderDetailRepository getRepository() {
		return codePurchaseOrderDetailRepository;
	}

}
