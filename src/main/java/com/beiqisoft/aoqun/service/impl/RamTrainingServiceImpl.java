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
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.RamTraining;
import com.beiqisoft.aoqun.repository.RamTrainingRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.RamTrainingService;

@Service
public class RamTrainingServiceImpl extends BaseServiceIml<RamTraining,RamTrainingRepository> implements RamTrainingService{

	@Autowired
	public RamTrainingRepository ramTrainingRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	
	public Page<RamTraining> find(final RamTraining ramTraining) {
		return ramTrainingRepository.findAll(new Specification<RamTraining>() {
			public Predicate toPredicate(Root<RamTraining> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(ramTraining,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(ramTraining.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "date","ctime"));
	}
	
	public Page<RamTraining> find(RamTraining ramTraining, int size) {
		return ramTrainingRepository.findAll(new Specification<RamTraining>() {
			public Predicate toPredicate(Root<RamTraining> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public RamTrainingRepository getRepository() {
		return ramTrainingRepository;
	}

	@Override
	public Message saveVerify(String earTag) {
		BaseInfo ram=baseInfoService.findByCodeOrRfid(earTag);
		if (ram==null){
			return GlobalConfig.setAbnormal("该羊只不存在");
		}
		if (!SystemM.PUBLIC_SEX_SIRE.equals(ram.getSex())){
			return GlobalConfig.setAbnormal("该羊性别错误");
		}
		return GlobalConfig.SUCCESS;
	}
}
