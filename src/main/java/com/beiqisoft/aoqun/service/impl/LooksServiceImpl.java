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
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Looks;
import com.beiqisoft.aoqun.repository.LooksRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.LooksService;
import com.beiqisoft.aoqun.util.DateUtils;

@Service
public class LooksServiceImpl extends BaseServiceIml<Looks,LooksRepository> implements LooksService{

	@Autowired
	public LooksRepository looksRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	
	public Page<Looks> find(final Looks looks) {
		return looksRepository.findAll(new Specification<Looks>() {
			public Predicate toPredicate(Root<Looks> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(looks,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(looks.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Looks> find(Looks looks, int size) {
		return looksRepository.findAll(new Specification<Looks>() {
			public Predicate toPredicate(Root<Looks> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public LooksRepository getRepository() {
		return looksRepository;
	}

	@Override
	public Message verify(String code,Long orgId) {
		BaseInfo base =baseInfoService.findByCodeOrRfid(code);
		Looks look=looksRepository.findByBase_id(base.getId());
		Message message = baseInfoService.flagVerify(base);
		if (!message.isCodeEqNormal()){
			return message;
		}
		if (!base.getOrg().getId().equals(orgId)){
			return GlobalConfig.setAbnormal("该羊不是本场羊只");
		}
		if (look!=null&&look.getDate()!=null){
			return GlobalConfig.setIsPass("该羊上一次添加品相的时间为:"+DateUtils.DateToStrMit(look.getDate())+",是否要修改品相数据?");
		}
		return GlobalConfig.SUCCESS; 
	}

}
