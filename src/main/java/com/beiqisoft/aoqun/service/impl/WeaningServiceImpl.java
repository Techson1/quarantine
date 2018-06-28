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
import com.beiqisoft.aoqun.entity.LambingDam;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.entity.Weaning;
import com.beiqisoft.aoqun.repository.BaseInfoRepository;
import com.beiqisoft.aoqun.repository.LambingDamRepository;
import com.beiqisoft.aoqun.repository.ParityRepository;
import com.beiqisoft.aoqun.repository.WeaningRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.PaddockChangeService;
import com.beiqisoft.aoqun.service.ParityService;
import com.beiqisoft.aoqun.service.WeaningService;
import com.beiqisoft.aoqun.util.DateUtils;

@Service
public class WeaningServiceImpl extends BaseServiceIml<Weaning,WeaningRepository> implements WeaningService{

	@Autowired
	public WeaningRepository weaningRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public ParityRepository parityRepository;
	@Autowired
	public ParityService parityService;
	@Autowired
	public LambingDamRepository lambingDamRepository;
	@Autowired
	public BaseInfoRepository baseInfoRepository;
	@Autowired
	public PaddockChangeService paddockChangeService;

	
	public Page<Weaning> find(final Weaning weaning) {
		return weaningRepository.findAll(new Specification<Weaning>() {
			public Predicate toPredicate(Root<Weaning> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(weaning,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(weaning.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "weaningDate","ctime"));
	}
	
	public Page<Weaning> find(Weaning weaning, int size) {
		return weaningRepository.findAll(new Specification<Weaning>() {
			public Predicate toPredicate(Root<Weaning> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public WeaningRepository getRepository() {
		return weaningRepository;
	}

	@Override
	public Message addVerify(String code, Date weaningDate) {
		BaseInfo dam=baseInfoService.findByCodeOrRfid(code);
		if (dam==null){
			return new Message(GlobalConfig.ABNORMAL,code+":该羊不存在");
		}
		message = baseInfoService.flagVerify(dam);
		if (!message.isCodeEqNormal()){
			return message;
		}
		if (!SystemM.BASE_INFO_BREEDING_STATE_LACTATION.equals(dam.peelBreedingState())){
			return new Message(GlobalConfig.ABNORMAL,code
				+":只有繁殖状态为哺乳的羊才可以断奶,该羊的繁殖状态为:"
					+SystemM.baseInfoBreedingRturnChinese(dam.peelBreedingState()));
		}
		Parity parity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, dam.getId());
		if (parity==null){
			return new Message(GlobalConfig.ABNORMAL,code+":该羊的胎次不存在请联系维护人员");
		}
		LambingDam lambingDam=lambingDamRepository.findByParity_id(parity.getId());
		if (DateUtils.dateSubDate(lambingDam.getBornDate(),weaningDate)>=0){
			return new Message(GlobalConfig.ABNORMAL,code
					+":断奶日期不能小于生产日期,该羊的生产日期为:"
						+DateUtils.DateToStr(lambingDam.getBornDate()));
		}
		return GlobalConfig.SUCCESS;
	}

	@Transactional
	public Weaning add(String code, Weaning weaning) {
		Weaning nowWeaning=new Weaning(weaning);
		BaseInfo dam=baseInfoService.findByCodeOrRfid(code);
		Parity parityRng=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, dam.getId());
		nowWeaning.setDam(dam);
		nowWeaning.setParity(parityRng);
		/**保存生产记录*/
		nowWeaning.setLambingDam(lambingDamRepository.findByParity_id(parityRng.getId()));
		weaningRepository.save(nowWeaning);
		//修改胎次
		parityRng.setIsClosed(SystemM.PUBLIC_TRUE);
		parityRng.setIsNewestParity(SystemM.PUBLIC_FALSE);
		parityRng.setClosedDate(weaning.getWeaningDate());
		LambingDam lambingDam=lambingDamRepository.findByParity_id(parityRng.getId());
		parityRng.setLactation(DateUtils.dateSubDate(lambingDam.getBornDate(),weaning.getWeaningDate()));
		parityRepository.save(parityRng);
		//修改羊只
		dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT);
		baseInfoRepository.save(dam);
		
		//添加胎次
		parityService.add(dam, parityRng);
		//转圈添加
		if(weaning.getPaddock()!=null && weaning.getPaddock().getId()!=null){
			paddockChangeService.add(weaning.getPaddock(), dam.getCode(), weaning.getOrg(),weaning.getRecorder());
		}
		return nowWeaning;
	}

	@Transactional
	public Message delVerify(Long id) {
		Weaning weaning=weaningRepository.findOne(id);
		Parity nowParity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,weaning.getDam().getId());
		if (!SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT.equals(weaning.getDam().peelBreedingState())){
			//TODO 断奶删除提示
			return new Message(GlobalConfig.ABNORMAL,"不能删除1");
		}
		
		if (nowParity.getParityMaxNumber()-weaning.getParity().getParityMaxNumber()!=1){
			return new Message(GlobalConfig.ABNORMAL,"不能删除2");
		}
		return delete(weaning.getId());
	}
	
	@Transactional
	public Message delete(Long id) {
		Weaning weaning=weaningRepository.findOne(id);
		//删除最新胎次
		Parity nowParity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,weaning.getDam().getId());
		parityRepository.delete(nowParity);
		//修改断奶胎次为最新胎次
		Parity parity=weaning.getParity();
		parity.setIsNewestParity(SystemM.PUBLIC_TRUE);
		parity.setLactation(0);
		parityRepository.save(parity);
		//修改羊只的繁殖状态为哺乳
		BaseInfo dam=weaning.getDam();
		dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_LACTATION);
		baseInfoRepository.save(dam);
		
		weaningRepository.delete(id);
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message updateUiVerify(Long id) {
		Weaning weaning=weaningRepository.findOne(id);
		Parity nowParity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,weaning.getDam().getId());
		
		message = baseInfoService.flagVerify(weaning.getDam());
		if (!message.isCodeEqNormal()){
			return message;
		}
		
		if (!SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT.equals(weaning.getDam().peelBreedingState())){
			//TODO 断奶删除提示
			return new Message(GlobalConfig.ABNORMAL,
					"只有羊只繁殖状态为空怀才能修改断奶,该羊的繁殖状态为:"
							+SystemM.baseInfoBreedingRturnChinese(weaning.getDam().peelBreedingState()));
		}
		
		if (nowParity.getParityMaxNumber()-weaning.getParity().getParityMaxNumber()!=1){
			return new Message(GlobalConfig.ABNORMAL,"该羊的胎次已关闭,不能删除");
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message updateVerify(Long id, Date date) {
		Weaning weaning=weaningRepository.findOne(id);
		LambingDam lambingDam=lambingDamRepository.findByParity_id(weaning.getParity().getId());
		if (DateUtils.dateSubDate(lambingDam.getBornDate(),date)>=0){
			return new Message(GlobalConfig.ABNORMAL,
					"断奶日期不能小于生产日期,该羊的生产日期为:"
						+DateUtils.DateToStr(lambingDam.getBornDate()));
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message update(Weaning weaning) {
		Weaning upWeaning=weaningRepository.findOne(weaning.getId()).getWeaning(weaning);
		upWeaning.setCtime(new Date());
		//修改胎次
		Parity parity =upWeaning.getParity();
		LambingDam lambingDam=lambingDamRepository.findByParity_id(parity.getId());
		parity.setLactation(DateUtils.dateSubDate(lambingDam.getBornDate(),upWeaning.getWeaningDate()));
		parity.setClosedDate(weaning.getWeaningDate());
		
		Parity nowParity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,upWeaning.getDam().getId());
		nowParity.setStartDate(weaning.getWeaningDate());
		
		if (weaning.getPaddock()!=null && weaning.getPaddock().getId()!=null){
			paddockChangeService.add(upWeaning.getPaddock(), upWeaning.getDam().getCode(), upWeaning.getOrg(),upWeaning.getRecorder());
		}
		weaningRepository.save(upWeaning);
		parityRepository.save(parity);
		parityRepository.save(nowParity);
		return GlobalConfig.SUCCESS;
	}
}
