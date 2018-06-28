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
import org.springframework.transaction.annotation.Transactional;

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.BreedingPlan;
import com.beiqisoft.aoqun.entity.BreedingPlanDetailDam;
import com.beiqisoft.aoqun.repository.BaseInfoRepository;
import com.beiqisoft.aoqun.repository.BreedingPlanDetailDamRepository;
import com.beiqisoft.aoqun.repository.BreedingPlanRepository;
import com.beiqisoft.aoqun.service.BreedingPlanDetailDamService;
import com.beiqisoft.aoqun.service.ParityService;

@Service
public class BreedingPlanDetailDamServiceImpl extends BaseServiceIml<BreedingPlanDetailDam,BreedingPlanDetailDamRepository> implements BreedingPlanDetailDamService{

	@Autowired
	public BreedingPlanDetailDamRepository breedingPlanDetailDamRepository;
	@Autowired
	public BaseInfoRepository baseInfoRepository;
	@Autowired
	public ParityService parityService;
	
	public Page<BreedingPlanDetailDam> find(final BreedingPlanDetailDam breedingPlanDetailDam) {
		return breedingPlanDetailDamRepository.findAll(new Specification<BreedingPlanDetailDam>() {
			public Predicate toPredicate(Root<BreedingPlanDetailDam> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(breedingPlanDetailDam,root,criteriaBuilder);
				if (breedingPlanDetailDam.getBreedingPlan()!=null && breedingPlanDetailDam.getBreedingPlan().getId()!=null){
					Join<BreedingPlan,BreedingPlanRepository> join = root.join("breedingPlan", JoinType.INNER);
					list.add(criteriaBuilder.equal(join.get("id"), breedingPlanDetailDam.getBreedingPlan().getId()));
				}
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(breedingPlanDetailDam.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<BreedingPlanDetailDam> find(BreedingPlanDetailDam breedingPlanDetailDam, int size) {
		return breedingPlanDetailDamRepository.findAll(new Specification<BreedingPlanDetailDam>() {
			public Predicate toPredicate(Root<BreedingPlanDetailDam> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public BreedingPlanDetailDamRepository getRepository() {
		return breedingPlanDetailDamRepository;
	}

	@Transactional
	public Message saves(BreedingPlanDetailDam breedingPlanDetailDam,String[] codes) {
		for (String code:codes){
			//一、添加选配母羊明细
			BaseInfo dam=baseInfoRepository.findByCodeOrRfid(code, code);
			breedingPlanDetailDamRepository.save(
				new BreedingPlanDetailDam(breedingPlanDetailDam,dam));
			//二、修改BaseInfo
			if (SystemM.BASE_INFO_BREEDING_STATE_STATELESS.equals(dam.getBreedingState())){
				dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT);
				baseInfoRepository.save(dam);
				//添加胎次
				parityService.add(dam);
			}
		}
		return GlobalConfig.SUCCESS;
	}
}
