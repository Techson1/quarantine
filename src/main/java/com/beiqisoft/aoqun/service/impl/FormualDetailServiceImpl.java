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
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.FormualDetail;
import com.beiqisoft.aoqun.repository.FormualDetailRepository;
import com.beiqisoft.aoqun.service.FormualDetailService;
import com.beiqisoft.aoqun.util.MyUtils;

@Service
public class FormualDetailServiceImpl extends BaseServiceIml<FormualDetail,FormualDetailRepository> implements FormualDetailService{

	@Autowired
	public FormualDetailRepository formualDetailRepository;
	
	public Page<FormualDetail> find(final FormualDetail formualDetail) {
		return formualDetailRepository.findAll(new Specification<FormualDetail>() {
			public Predicate toPredicate(Root<FormualDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(formualDetail,root,criteriaBuilder);
				
				if (formualDetail.getFormula()!=null){
					list.add(criteriaBuilder.equal(root.join("formula", JoinType.INNER).get("id"), formualDetail.getFormula().getId()));
				}
				
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(formualDetail.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<FormualDetail> find(FormualDetail formualDetail, int size) {
		return formualDetailRepository.findAll(new Specification<FormualDetail>() {
			public Predicate toPredicate(Root<FormualDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public FormualDetailRepository getRepository() {
		return formualDetailRepository;
	}

	@Override
	public Message addVerify(FormualDetail formualDetail) {
		if(MyUtils.doublePlusDouble(formualDetailRepository.findByFormual(
				formualDetail.getFormula().getId()), formualDetail.getRatio())>100){
			return GlobalConfig.setAbnormal("百分比大于100");
		}
		if (formualDetailRepository.findByFormula_idAndMaterial_id(
				formualDetail.getFormula().getId(),formualDetail.getMaterial().getId())!=null){
			return GlobalConfig.setAbnormal("该原料已存在");
		}
		return GlobalConfig.SUCCESS;
	}

}
