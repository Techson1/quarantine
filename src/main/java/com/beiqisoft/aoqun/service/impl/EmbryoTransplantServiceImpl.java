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

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.EmbryoFlush;
import com.beiqisoft.aoqun.entity.EmbryoTransplant;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.repository.EmbryoFlushRepository;
import com.beiqisoft.aoqun.repository.EmbryoTransplantRepository;
import com.beiqisoft.aoqun.repository.ParityRepository;
import com.beiqisoft.aoqun.repository.ReceptorGroupRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.EmbryoTransplantService;

@Service
public class EmbryoTransplantServiceImpl extends BaseServiceIml<EmbryoTransplant,EmbryoTransplantRepository> implements EmbryoTransplantService{

	@Autowired
	public EmbryoTransplantRepository embryoTransplantRepository;
	@Autowired
	public ReceptorGroupRepository receptorGroupRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public ParityRepository parityRepository;
	
	public Page<EmbryoTransplant> find(final EmbryoTransplant embryoTransplant) {
		return embryoTransplantRepository.findAll(new Specification<EmbryoTransplant>() {
			public Predicate toPredicate(Root<EmbryoTransplant> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(embryoTransplant,root,criteriaBuilder);

				if(embryoTransplant.getEmbryoFlush()!=null){
					Join<EmbryoFlush,EmbryoFlushRepository> join = root.join("embryoFlush", JoinType.INNER);
					list.add(criteriaBuilder.equal(join.get("id"), embryoTransplant.getEmbryoFlush().getId()));
				}
				
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(embryoTransplant.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<EmbryoTransplant> find(EmbryoTransplant embryoTransplant, int size) {
		return embryoTransplantRepository.findAll(new Specification<EmbryoTransplant>() {
			public Predicate toPredicate(Root<EmbryoTransplant> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}
	public EmbryoTransplantRepository getRepository() {
		return embryoTransplantRepository;
	}

	@Override
	public Message updateVerify(String code,Long id) {
		EmbryoTransplant embryoTransplant=embryoTransplantRepository.findOne(id);
		embryoTransplant.setReceptor(baseInfoService.findByCodeOrRfid(code));
		if (embryoTransplant.getReceptor()==null){
			return new Message(GlobalConfig.ABNORMAL,"该羊耳号不存在");
		}
		
		if (receptorGroupRepository.findByBaseInfo_idAndProject_id(
				embryoTransplant.getReceptor().getId(),embryoTransplant.getProject().getId())==null){
			return new Message(GlobalConfig.ABNORMAL,"该羊不在受体组群中,不能添加");
		}
		if (embryoTransplantRepository.findByReceptor_idAndProject_id(
				embryoTransplant.getReceptor().getId(),embryoTransplant.getProject().getId())!=null){
			return new Message(GlobalConfig.ABNORMAL,"该羊已做过鲜胚移植,不能绑定");
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message bindingCode(String code,Long id) {
		EmbryoTransplant embryoTransplant=embryoTransplantRepository.findOne(id);
		if (embryoTransplant.getReceptor()!=null){
			baseInfoService.getRepository().save(embryoTransplant.getReceptor()
					.setBreedingStateReutrnThis(SystemM.BASE_INFO_BREEDING_STATE_RECEPTOR_PREPARE));
		}
		BaseInfo base=baseInfoService.findByCodeOrRfid(code);
		embryoTransplant.setReceptor(base);
		//绑定胎次
		Parity parity = parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, base.getId());
		embryoTransplant.setReceptorParity(parity);
		embryoTransplantRepository.save(embryoTransplant);
		//修改羊只信息
		baseInfoService.getRepository().save(embryoTransplant.getReceptor()
				.setBreedingStateReutrnThis(SystemM.BASE_INFO_BREEDING_STATE_RECEPTOR_TRANSPLANT));
		return GlobalConfig.SUCCESS;
	}
	@Override
	public Message delVerify(Long id) {
		EmbryoTransplant embryoTransplant=embryoTransplantRepository.findOne(id);
		if (embryoTransplant.getReceptor()==null){
			return delete(embryoTransplant);
		}
		
		if (embryoTransplant.getReceptor()!=null){
			if (!SystemM.PUBLIC_TRUE.equals(embryoTransplant.getReceptorParity().getIsNewestParity())){
				return GlobalConfig.setAbnormal("该胎次已经关闭,不能呢删除");
			}
		}
		
		if(!SystemM.BASE_INFO_BREEDING_STATE_RECEPTOR_TRANSPLANT.equals(
				embryoTransplant.getReceptor().getBreedingState())){
			return GlobalConfig.setAbnormal("该羊繁殖状态不为已移植,不能修改");
		}
		return delete(embryoTransplant);
	}
	
	@Override
	public Message delete(EmbryoTransplant embryoTransplant){
		if (embryoTransplant.getReceptor()!=null){
			baseInfoService.getRepository().save(embryoTransplant.getReceptor()
					.setBreedingStateReutrnThis(SystemM.BASE_INFO_BREEDING_STATE_RECEPTOR_PREPARE));
		}
		embryoTransplantRepository.delete(embryoTransplant);
		return GlobalConfig.SUCCESS;
	}

	@Override
	public boolean deitJudge(String sheetCode, Long embryoFlushId,String qualityGrade,Integer transNum,String recorder) {
		EmbryoTransplant embryoTransplant=embryoTransplantRepository
				.findBySheetCodeAndEmbryoFlush_id(sheetCode,embryoFlushId);
		if (embryoTransplant!=null){
			embryoTransplant.setTransNum(transNum);
			embryoTransplant.setQualityGrade(qualityGrade);
			embryoTransplant.setRecorder(recorder);
			embryoTransplantRepository.save(embryoTransplant);
			return false;
		}
		return true;
	}
}
