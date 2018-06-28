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
import com.beiqisoft.aoqun.entity.LambingDam;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.entity.Pregnancy;
import com.beiqisoft.aoqun.repository.BaseInfoRepository;
import com.beiqisoft.aoqun.repository.JoiningRepository;
import com.beiqisoft.aoqun.repository.LambingDamRepository;
import com.beiqisoft.aoqun.repository.ParityRepository;
import com.beiqisoft.aoqun.repository.PregnancyRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.BreedParameterService;
import com.beiqisoft.aoqun.service.LambingDamService;
import com.beiqisoft.aoqun.util.DateUtils;

@Service
public class LambingDamServiceImpl extends BaseServiceIml<LambingDam,LambingDamRepository> implements LambingDamService{

	@Autowired
	public LambingDamRepository lambingDamRepository;
	@Autowired
	public ParityRepository parityRepository;
	@Autowired
	public BaseInfoRepository baseInfoRepository;
	@Autowired
	public PregnancyRepository pregnancyRepository;
	@Autowired
	public JoiningRepository joiningRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public BreedParameterService breedParameterService;
	
	public Page<LambingDam> find(final LambingDam lambingDam) {
		return lambingDamRepository.findAll(new Specification<LambingDam>() {
			public Predicate toPredicate(Root<LambingDam> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(lambingDam,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(lambingDam.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "bornDate","ctime"));
	}
	
	public Page<LambingDam> find(LambingDam lambingDam, int size) {
		return lambingDamRepository.findAll(new Specification<LambingDam>() {
			public Predicate toPredicate(Root<LambingDam> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public LambingDamRepository getRepository() {
		return lambingDamRepository;
	}

	public Message addVerify(BaseInfo dam, Date bornDate,Long orgId) {
		if (dam==null){
			return new Message(GlobalConfig.ABNORMAL,"该记录不存在");
		}
		if (dam.getOrg()==null){
			return GlobalConfig.setAbnormal("该羊不存在");
		}
		if (!orgId.equals(dam.getOrg().getId())){
			return GlobalConfig.setAbnormal("该羊不存在");
		}
		message =  baseInfoService.flagVerify(dam);
		if (!message.isCodeEqNormal()){
			return message;
		}
		if (!SystemM.PUBLIC_SEX_DAM.equals(dam.getSex())){
			return GlobalConfig.setAbnormal("该羊性别错误");
		}
		if (!SystemM.BASE_INFO_BREEDING_STATE_GESTATION.equals(dam.peelBreedingState())){
			return GlobalConfig.setAbnormal("只有羊只繁殖状态为妊娠的羊才能生产,该羊的繁殖状态为"+
					SystemM.baseInfoBreedingRturnChinese(dam.peelBreedingState()));
		}
		Parity nowParity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, dam.getId());
		if (nowParity==null){
			return new Message(GlobalConfig.ABNORMAL,"胎次不存在,请联系维护人员");
		}
		Pregnancy pregnancy=pregnancyRepository.findByResultAndParity_id(SystemM.RESULTS_PREGNANCY, nowParity.getId());
		if (pregnancy==null){
			return new Message(GlobalConfig.ABNORMAL,"只有最新的测孕记录为已孕才能添加生产记录");
		}
		//查找配种
		Joining joining= joiningRepository.findByIsNewestJoiningAndParity_id(SystemM.PUBLIC_TRUE, nowParity.getId());
		//计算产期是否复合规则
		if (DateUtils.dateSubDate(bornDate, pregnancy.getPregnancyDate())<=0){
			return GlobalConfig.setAbnormal("产羔日期,不能晚于测孕日期,该羊的测孕日期为"
						+DateUtils.DateToStr(pregnancy.getPregnancyDate()));
		}
		int gestation=breedParameterService.getRepository().findByName(SystemM.BREED_PRODUCTION).getParameter();
		int day=gestation-DateUtils.dateSubDate(bornDate, joining.getJoiningDate());
		if (Math.abs(day)>5){
			return GlobalConfig.setIsPass("该羊与标准孕期相差"+day+"天,是否要添加");
		}
		return GlobalConfig.SUCCESS;
	}
	
	@Transactional
	public Message add(LambingDam lambingDam, BaseInfo dam) {
		Parity nowParity =parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, dam.getId());
		lambingDam.setParity(nowParity);
		lambingDam.setDam(dam);
		lambingDam.setJoining(joiningRepository.findByIsNewestJoiningAndParity_id(SystemM.PUBLIC_TRUE, nowParity.getId()));
		if (lambingDam.getJoining().getProject()==null){
			lambingDam.setBornType(SystemM.PARITY_TYPE_NB);
		}
		lambingDam.setAliveCountF(0);
		lambingDam.setAliveCountM(0);
		lambingDam.setDeadCountF(0);
		lambingDam.setDeadCountM(0);
		lambingDam.setBornCountF(0);
		lambingDam.setBornCountM(0);
		lambingDam.setBadCountF(0);
		lambingDam.setBadCountM(0);
		lambingDamRepository.save(lambingDam);
		Pregnancy pregnancy=pregnancyRepository.findByResultAndParity_id(SystemM.RESULTS_PREGNANCY, nowParity.getId());
		nowParity.setGestation(DateUtils.dateSubDate(lambingDam.getBornDate(), pregnancy.getPregnancyDate()));
		parityRepository.save(nowParity);
		dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_LACTATION);
		baseInfoRepository.save(dam);
		return new Message(GlobalConfig.NORMAL,""+lambingDam.getId());
	}

	public Message editUiVerify(LambingDam lambingDam) {
		BaseInfo dam=lambingDam.getDam();
		message = baseInfoService.flagVerify(dam);
		if (!message.isCodeEqNormal()){
			return message;
		}
		if (!SystemM.BASE_INFO_BREEDING_STATE_LACTATION.equals(dam.peelBreedingState())){
			return new Message(GlobalConfig.ABNORMAL,"只有繁殖状态为哺乳的羊才能进入修改页面,该羊的繁殖状态为:"
				+SystemM.baseInfoBreedingRturnChinese(dam.peelBreedingState()));
		}
		Parity parity=lambingDam.getParity();
		if (!SystemM.PUBLIC_TRUE.equals(parity.getIsNewestParity())){
			return new Message(GlobalConfig.ABNORMAL,"改胎次已关闭,不能进入修改页面");
		}
		return GlobalConfig.SUCCESS;
	}

	@Transactional
	public LambingDam edit(LambingDam lambingDam) {
		LambingDam editLambingDam=lambingDamRepository.findOne(lambingDam.getId());
		lambingDamRepository.save(editLambingDam.update(lambingDam));
		//修改胎次
		Parity nowParity=editLambingDam.getParity();
		Pregnancy pregnancy=pregnancyRepository.findByResultAndParity_id(SystemM.RESULTS_PREGNANCY, nowParity.getId());
		nowParity.setGestation(DateUtils.dateSubDate(lambingDam.getBornDate(), pregnancy.getPregnancyDate()));
		parityRepository.save(nowParity);
		return editLambingDam;
	}

	public Message delVerify(Long id) {
		LambingDam lambingDam=lambingDamRepository.findOne(id);
		if(lambingDam==null) {
			return new Message(GlobalConfig.ABNORMAL,"该羊不存在");
		}
		if(!SystemM.BASE_INFO_BREEDING_STATE_LACTATION.equals(lambingDam.getDam().peelBreedingState())){
			return new Message(GlobalConfig.ABNORMAL,"不能删除!");
		}
		if (!SystemM.PUBLIC_TRUE.equals(lambingDam.getParity().getIsNewestParity())){
			return new Message(GlobalConfig.ABNORMAL,"不能删除2!");
		}
		if (!baseInfoRepository.findByLambingDam_id(id).isEmpty()){
			return new Message(GlobalConfig.ABNORMAL,"存在羔羊不能删除");
		}
//		if (!lambingRepository.findByLambingDam_id(id).isEmpty()){
//			return new Message(GlobalConfig.ABNORMAL,"存在羔羊数据不能删除!");
//		}
		return delete(id);
	}

	@Transactional
	public Message delete(Long id) {
		LambingDam lambingDam=lambingDamRepository.findOne(id);
		//修改母羊
		BaseInfo dam=lambingDam.getDam();
		Pregnancy pregnancy=pregnancyRepository.findByResultAndParity_id(SystemM.RESULTS_PREGNANCY, lambingDam.getParity().getId());
		dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_GESTATION+pregnancy.getPregnancySeq());
		baseInfoRepository.save(dam);
		//修改胎次
		Parity parity=lambingDam.getParity();
		parity.setGestation(0);
		lambingDamRepository.delete(lambingDam);
		return GlobalConfig.SUCCESS;
	}

	@Transactional
	public List<LambingDam> appList(String code, Date bornDate,Long orgId) {
		if (code!=null && !"".equals(code)){
		   return lambingDamRepository.findByCodeAndBornDate(code, code, bornDate,orgId);
		}else{
		   return lambingDamRepository.findByBornDateAndOrg_idOrderByCtimeDesc(bornDate,orgId);
		}
	}
}