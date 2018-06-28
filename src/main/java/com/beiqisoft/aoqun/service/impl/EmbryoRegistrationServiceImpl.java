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
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.EmbryoRegistration;
import com.beiqisoft.aoqun.repository.EmbryoRegistrationRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.EmbryoRegistrationService;

@Service
public class EmbryoRegistrationServiceImpl extends BaseServiceIml<EmbryoRegistration,EmbryoRegistrationRepository> implements EmbryoRegistrationService{

	@Autowired
	public EmbryoRegistrationRepository embryoRegistrationRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	
	public Page<EmbryoRegistration> find(final EmbryoRegistration embryoRegistration) {
		return embryoRegistrationRepository.findAll(new Specification<EmbryoRegistration>() {
			public Predicate toPredicate(Root<EmbryoRegistration> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(embryoRegistration,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(embryoRegistration.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<EmbryoRegistration> find(EmbryoRegistration embryoRegistration, int size) {
		return embryoRegistrationRepository.findAll(new Specification<EmbryoRegistration>() {
			public Predicate toPredicate(Root<EmbryoRegistration> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public EmbryoRegistrationRepository getRepository() {
		return embryoRegistrationRepository;
	}

	@Override
	public String add(EmbryoRegistration embryoRegistration, int i) {
		EmbryoRegistration embryoRegistrationed=embryoRegistrationRepository.findByCode(embryoRegistration.getPrefix()+(Integer.parseInt(embryoRegistration.getStart())+i));
		if (embryoRegistrationed==null){
			String code="0000000000000";
			code=code+(Integer.parseInt(embryoRegistration.getStart())+i);
			code=embryoRegistration.getPrefix()+code.substring(code.length()-embryoRegistration.getStart().length());
			embryoRegistrationRepository.save(
					embryoRegistration.setCodeReturnThis(code));
			return null;
		}
		return embryoRegistrationed.getCode();
	}

	@Override
	public Message findByOne(Long breedId) {
		List<EmbryoRegistration> list =embryoRegistrationRepository.findByisUseAndBreed_id(SystemM.PUBLIC_FALSE,breedId);
		if (list.isEmpty()){
			return GlobalConfig.setAbnormal("没有该品种的细管编号");
		}
		return GlobalConfig.setNormal(list.get(0).getCode()); 
	}
}
