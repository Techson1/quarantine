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
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.SizeView;
import com.beiqisoft.aoqun.repository.SizeViewRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.SizeViewService;

@Service
public class SizeViewServiceImpl extends BaseServiceIml<SizeView,SizeViewRepository> implements SizeViewService{

	@Autowired
	public SizeViewRepository sizeViewRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	
	public Page<SizeView> find(final SizeView sizeView) {
		return sizeViewRepository.findAll(new Specification<SizeView>() {
			public Predicate toPredicate(Root<SizeView> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(sizeView,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(sizeView.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "date"));
	}
	
	public Page<SizeView> find(SizeView sizeView, int size) {
		return sizeViewRepository.findAll(new Specification<SizeView>() {
			public Predicate toPredicate(Root<SizeView> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}
	public List<SizeView> findAll(SizeView sizeView) {
		return sizeViewRepository.findAll(new Specification<SizeView>() {
			public Predicate toPredicate(Root<SizeView> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new Sort(Sort.Direction.DESC,new String[] {"ctime"}));
	}
	public SizeViewRepository getRepository() {
		return sizeViewRepository;
	}

	@Transactional
	@Override
	public SizeView add(SizeView sizeView, String code) {
		BaseInfo base= baseInfoService.findByCodeOrRfid(code);
		//SizeView size=sizeViewRepository.findByBase_id(base.getId());
		//if (size==null){
			sizeViewRepository.save(sizeView.setBaseReturnThis(base));
		//}
		//else{
		//	sizeViewRepository.save(size.setAll(sizeView));
		//}
		return sizeView;
	}

	@Override
	public Message verify(String earTag,Long orgId) {
		BaseInfo base=baseInfoService.findByCodeOrRfid(earTag);
		if (base==null){
			return GlobalConfig.setAbnormal("该羊不存在");
		}
		if(sizeViewRepository.findByBase_id(base.getId())!=null){
			return GlobalConfig.setAbnormal("该羊已存在体尺中不能添加");
		}
		if(!base.getOrg().getId().equals(orgId)){
			return GlobalConfig.setAbnormal("该羊不是本场羊只");
		}
		return baseInfoService.flagVerify(base);
	}

}
