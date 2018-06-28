package com.beiqisoft.aoqun.service.impl;

import java.util.Date;
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
import com.beiqisoft.aoqun.entity.BreedingWeed;
import com.beiqisoft.aoqun.repository.BreedingWeedRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.BreedingWeedService;
import com.beiqisoft.aoqun.util.MyUtils;

@Service
public class BreedingWeedServiceImpl extends BaseServiceIml<BreedingWeed,BreedingWeedRepository> implements BreedingWeedService{

	@Autowired
	public BreedingWeedRepository breedingWeedRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	
	
	public Page<BreedingWeed> find(final BreedingWeed breedingWeed) {
		return breedingWeedRepository.findAll(new Specification<BreedingWeed>() {
			public Predicate toPredicate(Root<BreedingWeed> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(breedingWeed,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(breedingWeed.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<BreedingWeed> find(BreedingWeed breedingWeed, int size) {
		return breedingWeedRepository.findAll(new Specification<BreedingWeed>() {
			public Predicate toPredicate(Root<BreedingWeed> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public BreedingWeedRepository getRepository() {
		return breedingWeedRepository;
	}

	@Override
	public Message addVerify(String earTag) {
		BaseInfo base=baseInfoService.findByCodeOrRfid(earTag);
		if (base==null){
			return new Message(GlobalConfig.ABNORMAL,"该羊不存在");
		}
		if(!SystemM.NORMAL.equals(base.getPhysiologyStatus()+"")){
			return new Message(GlobalConfig.ABNORMAL,"该羊已经出库");
		}
		
		return GlobalConfig.SUCCESS;
	}

	@Transactional
	@Override
	public BreedingWeed add(BreedingWeed breedingWeed, String earTag) {
		breedingWeed.setBase(baseInfoService.findByCodeOrRfid(earTag));
		breedingWeed.setPaddock(breedingWeed.getBase().getPaddock());
		breedingWeed.getBase().setPhysiologyStatus(MyUtils.strToLong(SystemM.STAY_DEATH_BREEDING));
		breedingWeed.getBase().setDeliveryDate(breedingWeed.getDate());
		breedingWeed.getBase().setIsAudit(SystemM.PUBLIC_FALSE);
		breedingWeedRepository.save(breedingWeed);
		baseInfoService.getRepository().save(breedingWeed.getBase());
		//TODO 库存管理没有实现
		return breedingWeed;
	}

	@Override
	public Message updateAndDelVerify(Long id) {
		BreedingWeed breedingWeed=breedingWeedRepository.findOne(id);
		if (breedingWeed.getBase()==null){
			return new Message(GlobalConfig.ABNORMAL,"数据错误,淘汰羊为空,请联系维护人员");
		}
		if (!SystemM.STAY_DEATH_BREEDING.equals(breedingWeed.getBase().getPhysiologyStatus()+"")){
			return new Message(GlobalConfig.ABNORMAL,"该记录只有为育种淘汰代审核才能修改或删除");
		}
		return GlobalConfig.SUCCESS;
	}

	@Transactional
	@Override
	public Message delete(Long id) {
		message=updateAndDelVerify(id);
		if (!message.isCodeEqNormal()){
			return message;
		}
		
		BreedingWeed breedingWeed=breedingWeedRepository.findOne(id);
		breedingWeed.getBase().setPhysiologyStatus(MyUtils.strToLong(SystemM.NORMAL));
		
		baseInfoService.getRepository().save(breedingWeed.getBase());
		breedingWeedRepository.delete(id);
		baseInfoService.moonAgeEdit(breedingWeed.getBase().getCode(), new Date());
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message update(BreedingWeed breedingWeed) {
		BreedingWeed nowbreedingWeed=breedingWeedRepository.findOne(breedingWeed.getId());
		breedingWeedRepository.save(nowbreedingWeed.update(breedingWeed));
		
		nowbreedingWeed.getBase().setDeliveryDate(nowbreedingWeed.getDate());
		baseInfoService.getRepository().save(nowbreedingWeed.getBase());
		baseInfoService.moonAgeEdit(nowbreedingWeed.getBase().getCode(), breedingWeed.getDate());
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message updateUiverify(Long id) {
		return null;
	}

}
