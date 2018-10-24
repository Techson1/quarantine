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
import org.springframework.transaction.annotation.Transactional;

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.GeneralVeternary;
import com.beiqisoft.aoqun.repository.DeathDisposalReasonRepository;
import com.beiqisoft.aoqun.repository.GeneralVeternaryRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.GeneralVeternaryService;

@Service
public class GeneralVeternaryServiceImpl extends BaseServiceIml<GeneralVeternary,GeneralVeternaryRepository> implements GeneralVeternaryService{

	@Autowired
	public GeneralVeternaryRepository generalVeternaryRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public DeathDisposalReasonRepository deathDisposalReasonRepository;
	
	public Page<GeneralVeternary> find(final GeneralVeternary generalVeternary) {
		return generalVeternaryRepository.findAll(new Specification<GeneralVeternary>() {
			public Predicate toPredicate(Root<GeneralVeternary> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(generalVeternary,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(generalVeternary.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<GeneralVeternary> find(GeneralVeternary generalVeternary, int size) {
		return generalVeternaryRepository.findAll(new Specification<GeneralVeternary>() {
			public Predicate toPredicate(Root<GeneralVeternary> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}
	public List<GeneralVeternary> findList(GeneralVeternary generalVeternary) {
		return generalVeternaryRepository.findAll(new Specification<GeneralVeternary>() {
			public Predicate toPredicate(Root<GeneralVeternary> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new Sort(Sort.Direction.DESC,new String[] {"ctime"}));
	}
	public GeneralVeternaryRepository getRepository() {
		return generalVeternaryRepository;
	}

	@Transactional
	@Override
	public GeneralVeternary add(GeneralVeternary generalVeternary, String earTag) {
		generalVeternary.setBase(baseInfoService.findByCodeOrRfid(earTag));
		generalVeternaryRepository.save(generalVeternary);
		generalVeternary.setReason(deathDisposalReasonRepository.findOne(generalVeternary.getReason().getId()));
		generalVeternary.setFatherReason(deathDisposalReasonRepository.findOne(generalVeternary.getFatherReason().getId()));
		return generalVeternary;
	}

	@Override
	public Message updateVerify(Long id) {
		GeneralVeternary general=generalVeternaryRepository.findOne(id);
		if (!SystemM.NORMAL.equals(general.getBase().getPhysiologyStatus()+"")){
			return GlobalConfig.setAbnormal("该羊已出库不能修改");
		}
		
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message addVerify(String earTag) {
		BaseInfo base=baseInfoService.findByCodeOrRfid(earTag);
		if (base==null){
			return GlobalConfig.setAbnormal("该羊不存在");
		}
		if(!SystemM.NORMAL.equals(base.getPhysiologyStatus()+"")){
			return GlobalConfig.setAbnormal("该羊已出库不能修改");
		}
		return GlobalConfig.SUCCESS;
	}

}
