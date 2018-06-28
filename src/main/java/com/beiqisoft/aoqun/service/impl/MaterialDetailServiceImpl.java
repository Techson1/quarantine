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
import com.beiqisoft.aoqun.entity.MaterialDetail;
import com.beiqisoft.aoqun.repository.MaterialDetailRepository;
import com.beiqisoft.aoqun.service.MaterialDetailService;
import com.beiqisoft.aoqun.util.MyUtils;

@Service
public class MaterialDetailServiceImpl extends BaseServiceIml<MaterialDetail,MaterialDetailRepository> implements MaterialDetailService{

	@Autowired
	public MaterialDetailRepository materialDetailRepository;
	
	public Page<MaterialDetail> find(final MaterialDetail materialDetail) {
		return materialDetailRepository.findAll(new Specification<MaterialDetail>() {
			public Predicate toPredicate(Root<MaterialDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(materialDetail,root,criteriaBuilder);
				
				if (materialDetail.getMaterial()!=null){
					list.add(criteriaBuilder.equal(root.join("material", JoinType.INNER).get("id"), materialDetail.getMaterial().getId()));
				}
				
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(materialDetail.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<MaterialDetail> find(MaterialDetail materialDetail, int size) {
		return materialDetailRepository.findAll(new Specification<MaterialDetail>() {
			public Predicate toPredicate(Root<MaterialDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public MaterialDetailRepository getRepository() {
		return materialDetailRepository;
	}

	@Override
	public Message addVerify(MaterialDetail materialDetail) {
		if (MyUtils.doublePlusDouble(materialDetailRepository.findByMateria(
				materialDetail.getMaterial().getId()),materialDetail.getRatio())>100){
			return GlobalConfig.setAbnormal("百分比大于100");
		}
		if (materialDetailRepository.findByMaterial_idAndBurden_id(
				materialDetail.getMaterial().getId(),materialDetail.getBurden().getId())!=null){
			return GlobalConfig.setAbnormal("该原料已存在");
		}
		return GlobalConfig.SUCCESS;
	}

}
