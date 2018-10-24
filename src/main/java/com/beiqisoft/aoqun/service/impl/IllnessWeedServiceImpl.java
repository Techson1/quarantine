package com.beiqisoft.aoqun.service.impl;

import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.IllnessWeed;
import com.beiqisoft.aoqun.repository.IllnessWeedRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.IllnessWeedService;
import com.beiqisoft.aoqun.util.MyUtils;

@Service
public class IllnessWeedServiceImpl extends BaseServiceIml<IllnessWeed,IllnessWeedRepository> implements IllnessWeedService{

	@Autowired
	public IllnessWeedRepository illnessWeedRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	
	public Page<IllnessWeed> find(final IllnessWeed illnessWeed) {
		return illnessWeedRepository.findAll(new Specification<IllnessWeed>() {
			public Predicate toPredicate(Root<IllnessWeed> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(illnessWeed,root,criteriaBuilder);
				
				if (illnessWeed.getPaddock()!=null){
					list.add(criteriaBuilder.equal(root.join("paddock", JoinType.INNER).get("id"), illnessWeed.getPaddock().getId()));
				}
				
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(illnessWeed.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "date"));
	}
	
	public Page<IllnessWeed> find(IllnessWeed illnessWeed, int size) {
		return illnessWeedRepository.findAll(new Specification<IllnessWeed>() {
			public Predicate toPredicate(Root<IllnessWeed> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
	           List<Predicate> list = getEntityPredicate(illnessWeed,root,criteriaBuilder);
				
				if (illnessWeed.getPaddock()!=null){
					list.add(criteriaBuilder.equal(root.join("paddock", JoinType.INNER).get("id"), illnessWeed.getPaddock().getId()));
				}
				
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	@Override
	public List<IllnessWeed> findAll(IllnessWeed illnessWeed) {
		 
		return illnessWeedRepository.findAll(new Specification<IllnessWeed>() {

			@Override
			public Predicate toPredicate(Root<IllnessWeed> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 
				return query.getRestriction();
			}
			
			
		}, new Sort(Sort.Direction.DESC,new String[] {"ctime"}));
		
	}
	public IllnessWeedRepository getRepository() {
		return illnessWeedRepository;
	}

	@Transactional
	@Override
	public IllnessWeed add(IllnessWeed illnessWeed, String earTag) {
		illnessWeed.setBase(baseInfoService.findByCodeOrRfid(earTag));
		illnessWeed.setPaddock(illnessWeed.getBase().getPaddock());
		illnessWeedRepository.save(illnessWeed);
		
		illnessWeed.getBase().setPhysiologyStatus(MyUtils.strToLong(SystemM.STAY_DEATH_WEED_OUT));
		illnessWeed.getBase().setDeliveryDate(illnessWeed.getDate());
		illnessWeed.getBase().setIsAudit(SystemM.PUBLIC_FALSE);
		baseInfoService.getRepository().save(illnessWeed.getBase());
		//修改羊只
		return illnessWeed;
	}

	@Override
	public Message delete(Long id) {
		message=updateAndDelVerify(id);
		if (!message.isCodeEqNormal()){
			return message;
		}
		IllnessWeed illnessWeed=illnessWeedRepository.findOne(id);
		illnessWeed.getBase().setPhysiologyStatus(MyUtils.strToLong(SystemM.NORMAL));
		illnessWeed.getBase().setIsAudit(null);
		baseInfoService.getRepository().save(illnessWeed.getBase());
		illnessWeedRepository.delete(id);
		
		baseInfoService.moonAgeEdit(illnessWeed.getBase().getCode(), new Date());
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message update(IllnessWeed illnessWeed) {
		IllnessWeed nowIllnessWeed=illnessWeedRepository.findOne(illnessWeed.getId()).update(illnessWeed);
		illnessWeedRepository.save(nowIllnessWeed);
		
		nowIllnessWeed.getBase().setDeliveryDate(illnessWeed.getDate());
		baseInfoService.getRepository().save(nowIllnessWeed.getBase());
		baseInfoService.moonAgeEdit(nowIllnessWeed.getBase().getCode(), illnessWeed.getDate());
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message updateAndDelVerify(Long id) {
		IllnessWeed illnessWeed=illnessWeedRepository.findOne(id);
		if (illnessWeed.getBase()==null){
			return new Message(GlobalConfig.ABNORMAL,"数据错误,淘汰羊为空,请联系维护人员");
		}
		if (!SystemM.STAY_DEATH_WEED_OUT.equals(illnessWeed.getBase().getPhysiologyStatus()+"")){
			return new Message(GlobalConfig.ABNORMAL,"该记录只有为疾病淘汰代审核才能修改或删除");
		}
		return GlobalConfig.SUCCESS;
	}

}
