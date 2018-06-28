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
import com.beiqisoft.aoqun.entity.FrozenEmbryo;
import com.beiqisoft.aoqun.repository.EmbryoRegistrationRepository;
import com.beiqisoft.aoqun.repository.FrozenEmbryoRepository;
import com.beiqisoft.aoqun.repository.FrozenEmbryoTransplantRepository;
import com.beiqisoft.aoqun.service.FrozenEmbryoService;

@Service
public class FrozenEmbryoServiceImpl extends BaseServiceIml<FrozenEmbryo,FrozenEmbryoRepository> implements FrozenEmbryoService{

	@Autowired
	public FrozenEmbryoRepository frozenEmbryoRepository;
	@Autowired
	public EmbryoRegistrationRepository embryoRegistrationRepository;
	@Autowired
	public FrozenEmbryoTransplantRepository frozenEmbryoTransplantRepository;
	
	public Page<FrozenEmbryo> find(final FrozenEmbryo frozenEmbryo) {
		return frozenEmbryoRepository.findAll(new Specification<FrozenEmbryo>() {
			public Predicate toPredicate(Root<FrozenEmbryo> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(frozenEmbryo,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(frozenEmbryo.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<FrozenEmbryo> find(FrozenEmbryo frozenEmbryo, int size) {
		return frozenEmbryoRepository.findAll(new Specification<FrozenEmbryo>() {
			public Predicate toPredicate(Root<FrozenEmbryo> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public FrozenEmbryoRepository getRepository() {
		return frozenEmbryoRepository;
	}

	@Transactional
	@Override
	public Message add(FrozenEmbryo frozenEmbryo) {
		frozenEmbryo.setEmbryoRegistration(embryoRegistrationRepository
				.findByCode(frozenEmbryo.getTubuleCode()));
		frozenEmbryo.setFrozenNumber(frozenEmbryo.getUsableNumber());
		frozenEmbryo.setRealityNumber(frozenEmbryo.getUsableNumber());
		frozenEmbryo.setIsOutsourcing(SystemM.PUBLIC_TRUE);
		
		embryoRegistrationRepository.save(frozenEmbryo.getEmbryoRegistration()
				.setIsUseReturnThis(SystemM.PUBLIC_TRUE,frozenEmbryo.getFreezeDate()));
		frozenEmbryoRepository.save(frozenEmbryo);
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message delVerify(Long id) {
		FrozenEmbryo frozenEmbryo = frozenEmbryoRepository.findOne(id);
		if (!frozenEmbryoTransplantRepository.findByFrozenEmbryo_id(frozenEmbryo.getId()).isEmpty()){
			return GlobalConfig.setAbnormal("该冻胚细管已使用,不能删除");
		}
		return delete(frozenEmbryo);
	}

	@Override
	public Message delete(FrozenEmbryo frozenEmbryo){
		frozenEmbryoRepository.delete(frozenEmbryo);
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message updateVerify(FrozenEmbryo frozenEmbryo) {
		Integer transNum=frozenEmbryoTransplantRepository.findByTransNumNotId(frozenEmbryo.getId());
		if (frozenEmbryo.isfrozenNumber(transNum)){
			return GlobalConfig.setAbnormal("冷冻胚胎数不能小于已用冷冻胚胎数,已用冷冻胚胎数为:"+transNum);
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message update(FrozenEmbryo frozenEmbryo) {
		frozenEmbryoRepository.save(frozenEmbryoRepository.findOne(frozenEmbryo.getId()).setFrozenEmbryo(frozenEmbryo));
		return GlobalConfig.SUCCESS;
	}

}
