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
import com.beiqisoft.aoqun.entity.Joining;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.entity.Pregnancy;
import com.beiqisoft.aoqun.repository.BaseInfoRepository;
import com.beiqisoft.aoqun.repository.BreedParameterRepository;
import com.beiqisoft.aoqun.repository.JoiningRepository;
import com.beiqisoft.aoqun.repository.ParityRepository;
import com.beiqisoft.aoqun.repository.PregnancyRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.PaddockChangeService;
import com.beiqisoft.aoqun.service.ParityService;
import com.beiqisoft.aoqun.service.PregnancyService;
import com.beiqisoft.aoqun.util.DateUtils;

@Service
public class PregnancyServiceImpl extends BaseServiceIml<Pregnancy,PregnancyRepository> implements PregnancyService{

	@Autowired
	public PregnancyRepository pregnancyRepository;
	@Autowired
	public JoiningRepository joiningRepository;
	@Autowired
	public ParityRepository parityRepository;
	@Autowired
	public BaseInfoRepository baseInfoRepository;
	@Autowired
	public ParityService parityService;
	@Autowired
	public PaddockChangeService paddockChangeService;
	@Autowired
	public BreedParameterRepository breedParameterRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	
	public Page<Pregnancy> find(final Pregnancy pregnancy) {
		return pregnancyRepository.findAll(new Specification<Pregnancy>() {
			public Predicate toPredicate(Root<Pregnancy> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(pregnancy,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(pregnancy.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "pregnancyDate","ctime"));
	}
	
	public Page<Pregnancy> find(Pregnancy pregnancy, int size) {
		return pregnancyRepository.findAll(new Specification<Pregnancy>() {
			public Predicate toPredicate(Root<Pregnancy> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public PregnancyRepository getRepository() {
		return pregnancyRepository;
	}

	@Override
	public Message verify(String damCode,BaseInfo dam, Date pregnancyDate) {
		if (dam==null){
			return new Message(GlobalConfig.ABNORMAL,damCode+":该羊不存在");
		}
		message = baseInfoService.flagVerify(dam);
		if (!message.isCodeEqNormal()){
			return message;
		}
		if (SystemM.PUBLIC_SEX_SIRE.equals(dam.getSex())){
			return new Message(GlobalConfig.ABNORMAL,dam.getCode()+":羊只性别错误");
		}
		//已有测孕结果的羊只
		if (SystemM.BASE_INFO_BREEDING_STATE_GESTATION.equals(dam.peelBreedingState()) || SystemM.BASE_INFO_BREEDING_STATE_UNPREGNANCY.equals(dam.peelBreedingState())){
			//查询胎次记录
//					Parity nowParity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, dam.getId());
//					List<Pregnancy> prengList = pregnancyRepository.findByParity_idOrderByPregnancySeqDesc(nowParity.getId());
			List<Pregnancy> prengList = pregnancyRepository.findByDam_idOrderByCtimeDesc(dam.getId());
			if(prengList != null && prengList.size()>0){
				return this.editUiVerify(prengList.get(0));
			}
		}
//		if (!SystemM.BASE_INFO_BREEDING_STATE_CROSS.equals(dam.peelBreedingState())){
//			return new Message(GlobalConfig.ABNORMAL,dam.getCode()
//					+":只有繁殖状态为已配种的羊才能测孕,该羊只的繁殖状态为:"
//						+SystemM.baseInfoBreedingRturnChinese(dam.peelBreedingState()));
//		}
		//如果是妊娠或者未孕 ，提示：该羊状态为妊娠或者未孕，测孕日期为2018-01-01 ，是否覆盖该条记录，继续流程，更新 
		Parity nowParity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, dam.getId());
		if (nowParity==null){
			return new Message(GlobalConfig.ABNORMAL,dam.getCode()+":该羊的胎次不存在,请联系维护人员");
		}
		Joining joining=joiningRepository.findByIsNewestJoiningAndParity_id(SystemM.PUBLIC_TRUE, nowParity.getId());
		//判断配种记录是否存在,理论上来说不可能不存在,防止人为破坏数据库文件
		if (joining==null){
			return new Message(GlobalConfig.ABNORMAL,dam.getCode()+":该羊的配种记录不存在,请联系维护人员");
		}
		if (DateUtils.dateSubDate(joining.getJoiningDate(), pregnancyDate)>=0){
			return new Message(GlobalConfig.ABNORMAL,dam.getCode()
					+":该羊的测孕时间不能早于配种日期,该羊的配种日期为:"+DateUtils.DateToStr(joining.getJoiningDate()));
		}
		System.out.println(DateUtils.dateSubDate(pregnancyDate, joining.getJoiningDate()));
		if(DateUtils.dateSubDate(pregnancyDate, joining.getJoiningDate())>=140){
			return GlobalConfig.setAbnormal(dam.getCode()
					+":该羊的测孕日期不能晚于配种日期140天,该羊的配种日期:"+DateUtils.DateToStr(joining.getJoiningDate()));
		}
		return GlobalConfig.SUCCESS;
	}

	@Transactional
	@Override
	public Pregnancy add(BaseInfo dam, Pregnancy pregnancy) {
		
		if (SystemM.BASE_INFO_BREEDING_STATE_GESTATION.equals(dam.peelBreedingState())
				|| SystemM.BASE_INFO_BREEDING_STATE_UNPREGNANCY.equals(dam.peelBreedingState())) {
//			Pregnancy editPregnancy = pregnancyRepository.findOne(pregnancy.getId());
			Pregnancy editPregnancy = new Pregnancy();
			List<Pregnancy> prengList = pregnancyRepository.findByDam_idOrderByCtimeDesc(dam.getId());
			if(prengList != null && prengList.size()>0){
				editPregnancy = prengList.get(0);
			}
			editPregnancy.setPredict(breedParameterRepository.findByName(SystemM.BREED_PRODUCTION).getParameter());
			// 转圈
			if (pregnancy.getToPaddock() != null && pregnancy.getToPaddock().getId() != null) {
				paddockChangeService.add(pregnancy.getToPaddock(), editPregnancy.getDam().getCode(), pregnancy.getOrg(),
						pregnancy.getRecorder());
			} else {
				pregnancy.setToPaddock(null);
			}
			pregnancy.setCtime(new Date());
			pregnancyRepository.save(editPregnancy.editReturnThis(pregnancy));
			Parity parity = editPregnancy.getParity();
			Joining joining = joiningRepository.findByParity_idAndJoiningSeq(parity.getId(),
					editPregnancy.getPregnancySeq());
			parity.setPostCross(DateUtils.dateSubDate(joining.getJoiningDate(), pregnancy.getPregnancyDate()));
			// 修改羊只繁殖状态
			baseInfoService.getRepository()
					.save(editPregnancy.getDam().setBreedingStateReutrnThis(editPregnancy.returnBreedingState()));
			joiningRepository.save(joining.setResultReturnThis(pregnancy.getResult()));
			
			return editPregnancy;
		}
		Parity nowParity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,dam.getId());
		pregnancy.setDam(dam);
		pregnancy.setParity(nowParity);
		pregnancy.setPregnancySeq(joiningRepository.findByIsNewestJoiningAndParity_id(
				SystemM.PUBLIC_TRUE, nowParity.getId()).getJoiningSeq());
		//保存测孕
		pregnancy.setPaddock(dam.getPaddock());
		if (SystemM.PARITY_TYPE_NB.equals(nowParity.getParityType())){
			dam.setBreedingState(pregnancy.returnBreedingState());
			baseInfoRepository.save(dam);
			//修改配种
			Joining joining=joiningRepository.findByIsNewestJoiningAndParity_id(
					SystemM.PUBLIC_TRUE, nowParity.getId());
			joiningRepository.save(joining.setResultReturnThis(pregnancy.getResult()));
			//计算胎次的配种天数,配种天数=测孕日期-第一次配种日期-
			nowParity.setPostCross(DateUtils.dateSubDate(pregnancy.getPregnancyDate(),
					joining.getJoiningDate()));
			//获取配种日期
			pregnancy.setJoiningDate(joining.getJoiningDate());
			pregnancy.setJoining(joining);
		}
		//TODO 如果该胎次不是自然繁殖
		else{
			dam.setBreedingState(pregnancy.returnEtBreedingState());
			baseInfoRepository.save(dam);
			//如果羊只为空怀状态,修改胎次
			if (SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT.equals(dam.getBreedingState())){
				//修改胎次
				nowParity.setIsNewestParity(SystemM.PUBLIC_FALSE);
				nowParity.setIsClosed(SystemM.PUBLIC_FALSE);
				nowParity.setClosedDate(pregnancy.getPregnancyDate());
				parityRepository.save(nowParity);
				parityService.add(dam, nowParity);
			}
		}
		//保存胎次
		parityRepository.save(nowParity);
		//转圈
		if (pregnancy.getToPaddock()!=null && pregnancy.getToPaddock().getId()!=null){
			paddockChangeService.add(pregnancy.getToPaddock(), dam.getCode(), pregnancy.getOrg(),pregnancy.getRecorder());
		}
		else{
			pregnancy.setToPaddock(null);
		}
		pregnancy.setPredict(
				breedParameterRepository.findByName(SystemM.BREED_PRODUCTION).getParameter());
		//保存妊检
		pregnancyRepository.save(pregnancy);
		return pregnancy;
	}

	@Override
	public Message editUiVerify(Pregnancy pregnancy) {
		BaseInfo dam=pregnancy.getDam();
		if (dam==null){
			return new Message(GlobalConfig.ABNORMAL,"该羊不存在,请联系维护人员");
		}
		message = baseInfoService.flagVerify(dam);
		if (!message.isCodeEqNormal()){
			return message;
		}
		if (!SystemM.BASE_INFO_BREEDING_STATE_UNPREGNANCY.equals(dam.peelBreedingState())
				&& !SystemM.BASE_INFO_BREEDING_STATE_GESTATION.equals(dam.peelBreedingState())
					&&!SystemM.BASE_INFO_BREEDING_STATE_RECEPTOR_TRANSPLANT.equals(dam.peelBreedingState())){
			return new Message(GlobalConfig.ABNORMAL,
					"繁殖状态只有为未孕或妊娠状态才能修改,该羊只的繁殖状态为:"
							+SystemM.baseInfoBreedingRturnChinese(dam.peelBreedingState()));
		}
		Parity nowParity=pregnancy.getParity();
		if (!SystemM.PUBLIC_TRUE.equals(nowParity.getIsNewestParity())){
			return new Message(GlobalConfig.ABNORMAL,"该羊只的胎次记录已关闭,不能进入修改界面");
		}
		Joining joining=joiningRepository.findByIsNewestJoiningAndParity_id(SystemM.PUBLIC_TRUE, nowParity.getId());
		if (!SystemM.PUBLIC_TRUE.equals(joining.getIsNewestJoining())){
			return GlobalConfig.setAbnormal("该记录不是最新记录不能修改");
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message delVerify(Long id) {
		Pregnancy pregnancy =pregnancyRepository.findOne(id);
		BaseInfo dam=pregnancy.getDam();
		if (!(SystemM.BASE_INFO_BREEDING_STATE_GESTATION.equals(dam.peelBreedingState())
				||SystemM.BASE_INFO_BREEDING_STATE_UNPREGNANCY.equals(dam.peelBreedingState()))){
			return new Message(GlobalConfig.ABNORMAL,"不能删除");
		}
		if(!pregnancy.equals(pregnancyRepository.findByParity_idOrderByPregnancySeqDesc(
				pregnancy.getParity().getId()).get(0))){
			return new Message(GlobalConfig.ABNORMAL,"不能删除");
		}
		
		if (!SystemM.PUBLIC_TRUE.equals(pregnancy.getParity().getIsNewestParity())){
			return new Message(GlobalConfig.ABNORMAL,"不能删除");
		}
		return delete(id);
		//return GlobalConfig.SUCCESS;
	}

	@Transactional
	public Message delete(Long id) {
		Pregnancy pregnancy =pregnancyRepository.findOne(id);
		//修改母羊
		BaseInfo dam=pregnancy.getDam();
		dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_CROSS+pregnancy.getPregnancySeq());
		baseInfoRepository.save(dam);
		//修改胎次
		Parity parity=pregnancy.getParity();
		parity.setPostCross(0);
		parityRepository.save(parity);
		//修改胎次记录
		Joining joining=joiningRepository.findByIsNewestJoiningAndParity_id(
				SystemM.PUBLIC_TRUE, parity.getId());
		joiningRepository.save(joining.setResultReturnThis(null));
		//删除妊娠记录
		pregnancyRepository.delete(pregnancy);
		return GlobalConfig.SUCCESS;
	}
	
	@Override
	public Message verify(String damCode, BaseInfo dam) {

		if (SystemM.BASE_INFO_BREEDING_STATE_GESTATION.equals(dam.peelBreedingState())
				|| SystemM.BASE_INFO_BREEDING_STATE_UNPREGNANCY.equals(dam.peelBreedingState())) {
			// 查询胎次记录
			Parity nowParity = parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, dam.getId());
			// 查询测孕记录
			// Pregnancy pregnancy =
			// pregnancyRepository.findByResultAndParity_id(SystemM.RESULTS_PREGNANCY,
			// nowParity.getId());
			List<Pregnancy> prengList = pregnancyRepository.findByDam_idOrderByCtimeDesc(dam.getId());
			Pregnancy pregnancy = new Pregnancy();
			if (prengList != null && prengList.size() > 0) {
				pregnancy = prengList.get(0);
			}
			// 查询配种记录
			Joining joining = joiningRepository.findByIsNewestJoiningAndParity_id(SystemM.PUBLIC_TRUE,
					nowParity.getId());
			return new Message(GlobalConfig.ABNORMAL,
					dam.getCode() + ":该羊的配种时间是" + DateUtils.DateToStrMit(joining.getJoiningDate()) + ",上次测孕时间是"
							+ DateUtils.DateToStrMit(pregnancy.getPregnancyDate()) + "，结果："
							+ SystemM.baseInfoBreedingRturnChinese(dam.peelBreedingState()) + "。勾选将替换上次测孕结果");
		}

		// if
		// (SystemM.BASE_INFO_BREEDING_STATE_UNPREGNANCY.equals(dam.peelBreedingState())){
		// //查询胎次记录
		// Parity
		// nowParity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,
		// dam.getId());
		// //查询配种记录
		// Joining
		// joining=joiningRepository.findByIsNewestJoiningAndParity_id(SystemM.PUBLIC_TRUE,
		// nowParity.getId());
		// return new Message(GlobalConfig.ABNORMAL,dam.getCode()
		// +":该羊的配种时间是"+DateUtils.DateToStrMit(joining.getJoiningDate())+",上次测孕结果：未孕。勾选将替换上次测孕结果");
		// }
		return GlobalConfig.SUCCESS;
	}
}
