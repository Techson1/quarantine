package com.beiqisoft.aoqun.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.BreedingPlan;
import com.beiqisoft.aoqun.entity.BreedingPlanDetailDam;
import com.beiqisoft.aoqun.entity.BreedingPlanDetailSire;
import com.beiqisoft.aoqun.entity.EmbryoProject;
import com.beiqisoft.aoqun.entity.Joining;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.entity.Pregnancy;
import com.beiqisoft.aoqun.repository.BaseInfoRepository;
import com.beiqisoft.aoqun.repository.BreedParameterRepository;
import com.beiqisoft.aoqun.repository.BreedRepository;
import com.beiqisoft.aoqun.repository.DonorGroupRepository;
import com.beiqisoft.aoqun.repository.EmbryoFlushRepository;
import com.beiqisoft.aoqun.repository.EmbryoProjectRepository;
import com.beiqisoft.aoqun.repository.JoiningRepository;
import com.beiqisoft.aoqun.repository.OrganizationRepository;
import com.beiqisoft.aoqun.repository.ParityRepository;
import com.beiqisoft.aoqun.repository.PregnancyRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.BreedService;
import com.beiqisoft.aoqun.service.BreedingPlanDetailDamService;
import com.beiqisoft.aoqun.service.BreedingPlanDetailSireService;
import com.beiqisoft.aoqun.service.JoiningService;
import com.beiqisoft.aoqun.service.OrganizationService;
import com.beiqisoft.aoqun.service.PaddockChangeService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.MyUtils;

@Service
public class JoiningServiceImpl extends BaseServiceIml<Joining,JoiningRepository> implements JoiningService{

	@Autowired
	public JoiningRepository joiningRepository;
	@Autowired
	public BaseInfoRepository baseInfoRepository;
	@Autowired
	public ParityRepository parityRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public DonorGroupRepository donorGroupRepository;
	@Autowired
	public EmbryoProjectRepository embryoProjectRepository;
	@Autowired
	public BreedRepository breedRepository;
	@Autowired
	public PregnancyRepository pregnancyRepository;
	@Autowired
	public BreedService breedService;
	@Autowired
	public EmbryoFlushRepository embryoFlushRepository;
	@Autowired
	public BreedParameterRepository breedParameterRepository;
	@Autowired
	public BreedingPlanDetailDamService breedingPlanDetailDamService;
	@Autowired
	public BreedingPlanDetailSireService breedingPlanDetailSireService;
	@Autowired
	public PaddockChangeService paddockChangeService;
	
	@Autowired
	public OrganizationRepository organizationRepository;
	@Override
	public Page<Joining> findTest() {
		return joiningRepository.findAll(new Specification<Joining>() {
			public Predicate toPredicate(Root<Joining> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				//list.add(criteriaBuilder.equal(root.get("joiningSeq").as(String.class), "1"));
				Join<Parity,ParityRepository> join = root.join("parity", JoinType.INNER);
				//Path<String> exp3 = join.get("id");
				list.add(criteriaBuilder.equal(join.get("id"), 2l));
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				
				return query.getRestriction();
			}
		},new PageRequest(0, GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	public Page<Joining> find(final Joining joining) {
		return joiningRepository.findAll(new Specification<Joining>() {
			public Predicate toPredicate(Root<Joining> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(joining,root,criteriaBuilder);
				if (joining.getProject()!=null){
					Join<EmbryoProject,EmbryoProjectRepository> join = root.join("project", JoinType.INNER);
					list.add(criteriaBuilder.equal(join.get("id"), joining.getProject().getId()));
				}
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(joining.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "joiningDate","ctime"));
	}
	
	public Page<Joining> find(Joining joining, int size) {
		return joiningRepository.findAll(new Specification<Joining>() {
			public Predicate toPredicate(Root<Joining> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public JoiningRepository getRepository() {
		return joiningRepository;
	}

	@Override
	public Message joiningDamVerify(BaseInfo dam,String damCode,Long orgId) {
		String sys = "";
		if (dam==null){
			return new Message(GlobalConfig.ABNORMAL,damCode+":该羊不存在");
		}
		if (!SystemM.PUBLIC_SEX_DAM.equals(dam.getSex())){
			return new Message(GlobalConfig.ABNORMAL,dam.getCode()+":该羊是只公羊");
		}
		if (dam.getPhysiologyStatus()!=null&&!SystemM.NORMAL.equals(dam.getPhysiologyStatus()+"")){
			if((dam.getPhysiologyStatus()+"").equals("2")) {
				sys= "已疾病淘汰";
				sys=damCode+":"+sys;
			}
			if((dam.getPhysiologyStatus()+"").equals("3")) {
				sys= "已育种淘汰";
				sys=damCode+":"+sys;
			}
			if((dam.getPhysiologyStatus()+"").equals("4")) {
				sys= "已死亡";
				sys=damCode+":"+sys;
			}
			if((dam.getPhysiologyStatus()+"").equals("5")) {
				sys= "疾病淘汰待审核";
			}
			if((dam.getPhysiologyStatus()+"").equals("6")) {
				sys= "育种淘汰待审核";
			}
			if((dam.getPhysiologyStatus()+"").equals("7")) {
				sys= "死亡待审核";
				sys=damCode+":"+sys;
			}
			if((dam.getPhysiologyStatus()+"").equals("8")) {
				sys= "审核失败";
				sys=damCode+":"+sys;
			}
			if((dam.getPhysiologyStatus()+"").equals("9")) {
				sys= "销售待审核";
				sys=damCode+":"+sys;
			}
			if((dam.getPhysiologyStatus()+"").equals("10")) {
				sys= "销售审核";
				sys=damCode+":"+sys;
			}
			if((dam.getPhysiologyStatus()+"").equals("11")) {
				sys= "调拨待审核";
				sys=damCode+":"+sys;
			}
			if((dam.getPhysiologyStatus()+"").equals("12")) {
				sys= "调拨审核";
				sys=damCode+":"+sys;
			}
			
			//return GlobalConfig.setIsPass(sireCode+":"+sys);
		}

		if(orgId!=null&&dam.getOrg().getId()!=orgId) {
			if(!sys.equals("")) {
				sys= sys+"且在"+dam.getOrg().getBrief();
			}else {
				sys = damCode+":"+dam.getOrg().getBrief();
			}
			//return GlobalConfig.setIsPass(sireCode+":该羊在"+sire.getOrg().getBrief());
		}
		if(!sys.equals("")) {
			return GlobalConfig.setIsPass(sys);
		}
		if (!SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT
				.equals(dam.peelBreedingState())&&
					!SystemM.BASE_INFO_BREEDING_STATE_CROSS.equals(dam.peelBreedingState()) &&
							!SystemM.BASE_INFO_BREEDING_STATE_UNPREGNANCY.equals(dam.peelBreedingState())){
			return new Message(GlobalConfig.ABNORMAL,dam.getCode()+":该羊只的繁殖状态为:"
							+SystemM.baseInfoBreedingRturnChinese(dam.peelBreedingState())
								+",只有繁殖状态为配种和空怀状态的羊才能配种");
		}
		//判断羊只是否存在定级
		if (dam.getRank()==null){
			return GlobalConfig.setAbnormal(damCode+":该母羊没有定级");
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message joiningVerify(BaseInfo dam,Date joiningDate) {
		Parity nowParity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, dam.getId());
		
		if (nowParity ==null){
			return GlobalConfig.SUCCESS;
		}
		//配种判断
		Joining nowJoning=joiningRepository
				.findByIsNewestJoiningAndParity_id(SystemM.PUBLIC_TRUE, nowParity.getId());
		if (nowParity!=null && nowJoning!=null && DateUtils.dateSubDate(nowJoning.getJoiningDate(), joiningDate)>=0){
			return new Message(GlobalConfig.ABNORMAL,dam.getCode()
					+":当前配种日期必须大于上一次配种日期,该羊的上一次配种日期为:"+nowJoning.getJoiningDate());
		}
		if (nowParity!=null && nowJoning==null && DateUtils.dateSubDate(nowParity.getStartDate(), joiningDate)>=0){
			return new Message(GlobalConfig.ABNORMAL,dam.getCode()
					+":当前配种日期必须大于等于本胎次开始日期,该羊的上一次断奶日期为:"+nowParity.getStartDate());
		}
		//TODO 最大5配改为10配
		if (nowJoning!=null && nowJoning.getJoiningSeqReturnInteger()>=10){
			return new Message(GlobalConfig.ABNORMAL,dam.getCode()+":该羊已配种10次,不能再配种");
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message joiningBloodVerify(BaseInfo dam,BaseInfo sire) {
		
		if (dam.getSire()!=null && dam.getSire().equals(sire)){
			return new Message(GlobalConfig.ABNORMAL,dam.getCode()+":该母羊与耳标为:"+sire.getCode()
					+"的公羊,的系谱关系为父女,不能进行配种");
		}
		if (dam.getDam()!=null && dam.getDam().equals(dam)){
			return new Message(GlobalConfig.ABNORMAL,dam.getCode()+":该母羊与耳标为:"+sire.getCode()
					+"的公羊,的系谱关系为母子,不能进行配种");
		}
		if (sire.getDam()!=null && sire.getSire()!=null && sire.getDam()
				.equals(dam.getDam()) && sire.getSire().equals(dam.getSire())){
			if (DateUtils.dateSubDate(sire.getBirthDay(),dam.getBirthDay())>0){
				return new Message(GlobalConfig.ABNORMAL,dam.getCode()+":该母羊与耳标为:"+sire.getCode()
						+"的公羊,的系谱关系为亲兄妹,不能进行配种");
			}
			else{
				return new Message(GlobalConfig.ABNORMAL,dam.getCode()+":该母羊与耳标为:"+sire.getCode()
						+"的公羊,的系谱关系为亲姐弟,不能进行配种");
			}
		}
		return GlobalConfig.SUCCESS;
	}
	@Override
	public Message editUiVerify(Joining joining) {
		//羊只校验
		if (joining.getDam()==null){
			return new Message(GlobalConfig.ABNORMAL,joining.getDam().getCode()
					+":该羊数据错误，错误原因为配种没有和母羊关联");
		}
		message = baseInfoService.flagVerify(joining.getDam());
		if (!message.isCodeEqNormal()){
			return message;
		}
		if (!SystemM.BASE_INFO_BREEDING_STATE_CROSS.equals(joining.getDam().peelBreedingState())){
			return new Message(GlobalConfig.ABNORMAL,joining.getDam().getCode()
					+":该羊只繁殖状态为"+SystemM.baseInfoBreedingRturnChinese(joining.getDam().peelBreedingState())
						+",只有繁殖状态为已配种的羊只,才能进入修改页面");
		}
		if(!SystemM.PUBLIC_TRUE.equals(joining.getIsNewestJoining())){
			return new Message(GlobalConfig.ABNORMAL,joining.getDam().getCode()+":该羊不是最新配种不能修改");
		}
		
		//胎次校验
		if (joining.getParity()==null){
			return new Message(GlobalConfig.ABNORMAL,joining.getDam().getCode()
					+":该羊数据出现错误,错误原因为配种没有和胎次关联");
		}
		if (!SystemM.PUBLIC_TRUE.equals(joining.getParity().getIsNewestParity())){
			return new Message(GlobalConfig.ABNORMAL,joining.getDam().getCode()
					+"该羊只的胎次记录已关闭,不能进入修改界面");
		}
		return GlobalConfig.SUCCESS;
	}
	
	@Override
	public Message editVerify(Joining joining, BaseInfo sire) {
		if(!SystemM.PUBLIC_SEX_SIRE.equals(sire.getSex())){
			return new Message(GlobalConfig.ABNORMAL,"公羊性别错误");
		}
		Joining eqjoining=joiningRepository.findOne(joining.getId());
		
		Joining lastJoining=joiningRepository.findByParity_idAndJoiningSeq(
				eqjoining.getParity().getId(), MyUtils.strParseIntSubOne(eqjoining.getJoiningSeq())); 
		
		if (lastJoining!=null && DateUtils.dateSubDate(lastJoining.getJoiningDate(),joining.getJoiningDate())>=0){
			return new Message(GlobalConfig.ABNORMAL,"当前配种时间不能大于上一次配种时间,上一次配种时间为:"
				+DateUtils.DateToStr(lastJoining.getJoiningDate()));
		}
		
		Pregnancy pregnancy=pregnancyRepository.findByPregnancySeqAndParity_id(
				MyUtils.strParseIntSubOne(eqjoining.getJoiningSeq()), eqjoining.getParity().getId());
		if(pregnancy!=null){
			if (DateUtils.dateSubDate(pregnancy.getPregnancyDate(), joining.getJoiningDate())>=0){
				return new Message(GlobalConfig.ABNORMAL,"当前配种时间不能大于上一次妊娠时间,上一次妊娠时间为:"
					+DateUtils.DateToStr(pregnancy.getPregnancyDate()));
			}
		}
		return GlobalConfig.SUCCESS;
	}
	@Override
	public Message addAiVerify(String damCode, String sireCode,Date joiningDate,Long projectId){
		
		BaseInfo dam = baseInfoService.findByCodeOrRfid(damCode);
		BaseInfo sire=baseInfoService.findByCodeOrRfid(sireCode);
		if (dam==null){
			return new Message(GlobalConfig.ABNORMAL,damCode+":该羊不存在");
		}
		if (!SystemM.PUBLIC_SEX_DAM.equals(dam.getSex())){
			return new Message(GlobalConfig.ABNORMAL,damCode+":母羊性别错误");
		}
		if (sire==null){
			return new Message(GlobalConfig.ABNORMAL,sireCode+":该羊不存在");
		}
		if (!SystemM.PUBLIC_SEX_SIRE.equals(sire.getSex())){
			return new Message(GlobalConfig.ABNORMAL,sireCode+":公羊性别错误");
		}
		
		if (!SystemM.BASE_INFO_BREEDING_STATE_DONOR_PREPARE.equals(dam.peelBreedingState())){
			return new Message(GlobalConfig.ABNORMAL,damCode
					+":只有繁殖状态为:供体准备的羊才能添加AI记录,该羊的繁殖状态为:"
							+SystemM.baseInfoBreedingRturnChinese(dam.peelBreedingState()));
		}
		if (donorGroupRepository.findByBaseInfo_idAndProject_id(dam.getId(), projectId)==null){
			return new Message(GlobalConfig.ABNORMAL,damCode+":该羊不在供体组群中,不能添加");
		}
		//校验日期
		Parity parity = parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, dam.getId());
		if (DateUtils.dateSubDate(parity.getStartDate(), joiningDate)>0){
			return GlobalConfig.setAbnormal("配种日期不能大于胎次开始日期,该羊的胎次日期为:"
						+DateUtils.DateToStr(parity.getStartDate()));
		}
		return GlobalConfig.SUCCESS;
	}
	
	@Override
	public void addAi(String damCode, String sireCode, Date joniningDate,Long projectId){
		BaseInfo dam =baseInfoService.findByCodeOrRfid(damCode);
		BaseInfo sire=baseInfoService.findByCodeOrRfid(sireCode);
		Parity parity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, dam.getId());
		//计算后代品种
		Joining joining=new Joining().AI(dam,sire,parity,embryoProjectRepository.findOne(projectId),joniningDate); 
		//计算血统
		Breed childBreed=breedService.findByComparisonbreedIds(dam.getBreed(), sire.getBreed());
		joining.dispose("1", childBreed,"no");
		joiningRepository.save(joining);
		//修改羊只
		dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_DONOR_AI);
		baseInfoRepository.save(dam);
		//TODO 胚胎移植不计算繁殖日期
	}
	
	public Message delVerify(Long id) {
		Joining joining=joiningRepository.findOne(id);
		if (!SystemM.PUBLIC_TRUE.equals(joining.getIsNewestJoining())){
			return new Message(GlobalConfig.ABNORMAL,"不能删除1");
		}
		List<Pregnancy> pregnancies= pregnancyRepository.findByParity_idOrderByPregnancySeqDesc(
				joining.getParity().getId());
		String number=!pregnancies.isEmpty()?pregnancies.get(0).getPregnancySeq():"0";
		if(((int) joining.getJoiningSeq().compareTo(number))<=0){
			return new Message(GlobalConfig.ABNORMAL,"不能删除2");
		}
		if (!SystemM.PUBLIC_TRUE.equals(joining.getParity().getIsNewestParity())){
			return new Message(GlobalConfig.ABNORMAL,"不能删除3");
		}
		return del(id);
	}
	
	@Transactional
	public Message del(Long id) {
		Joining joining=joiningRepository.findOne(id);
		BaseInfo dam=joining.getDam();
		//如果上一次操作为测孕
		if (pregnancyRepository.findByPregnancySeqAndParity_id(
				MyUtils.strParseIntSubOne(joining.getJoiningSeq())
					,joining.getParity().getId())!=null){
			//修改羊只繁殖状态
			dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_UNPREGNANCY+MyUtils.strParseIntSubOne(joining.getJoiningSeq()));
		}
		else {
			//如果上一次操作不为测孕，那么上一次操作为配种或无操作
			if ("1".equals(joining.getJoiningSeq())){
				dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT);
			}else{
				dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_CROSS+MyUtils.strParseIntSubOne(joining.getJoiningSeq()));
				//修改上一次配种为最新配种
			}
		}
		//保存羊只并删除配种记录
		Joining nowJoining=joiningRepository.findByParity_idAndJoiningSeq(
				joining.getParity().getId(), MyUtils.strParseIntSubOne(joining.getJoiningSeq()));
		if (nowJoining!=null){
			nowJoining.setIsNewestJoining(SystemM.PUBLIC_TRUE);
			joiningRepository.save(nowJoining);
		}
		baseInfoRepository.save(dam);
		joiningRepository.delete(id);
		return GlobalConfig.SUCCESS;
	}
	
	@Transactional
	@Override
	public Joining add(Joining joining, String damCode, String sireCode) {
		//获取必要关联表
		BaseInfo dam=baseInfoService.findByCodeOrRfid(damCode.trim());
		BaseInfo sire=baseInfoService.findByCodeOrRfid(sireCode.trim());
	
		Parity nowParity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,dam.getId());
		Joining nowJonining=joiningRepository.findByIsNewestJoiningAndParity_id(SystemM.PUBLIC_TRUE, nowParity.getId());
		//一、配种修改
		String joiningSeq=nowJonining==null?"1":MyUtils.strParseIntPlusOne(nowJonining.getJoiningSeq());
		if (!"1".equals(joiningSeq)){
			nowJonining.setIsNewestJoining(SystemM.PUBLIC_FALSE);
		}
		//根据父号、母号计算血统,并根据血统查询品种
		Breed childBreed=breedService.findByComparisonbreedIds(dam.getBreed(),sire.getBreed());
		joining.dispose(joiningSeq, childBreed,"no");
		//保存胎次到配种
		joining.setParity(nowParity);
		joining.setIsNewestJoining(SystemM.PUBLIC_TRUE);
		joining.setDam(dam);
		joining.setSire(sire);
		//计算基因等级
		joining.setLevel();
		//计算测孕日期 
		joining.PregnancyDate(
				breedParameterRepository.findByName(SystemM.BREED_PREGNANCY_ONE).getParameter(),
				breedParameterRepository.findByName(SystemM.BREED_PREGNANCY_TWO).getParameter());
		//保存选配方案
		joining.setBreedingPlan(isBreedingPlan(dam,sire));
		joiningRepository.save(joining);
		//二、修改胎次
		if ("1".equals(joiningSeq)){
			nowParity.setNonpregnancy(DateUtils.dateSubDate(joining.getJoiningDate(), nowParity.getStartDate()));
		}
		nowParity.setParityType(SystemM.PARITY_TYPE_NB);
		nowParity.setParityNbNumber(nowParity.getParityNbNumber()+1);
		parityRepository.save(nowParity);
		//三、修改羊只
		dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_CROSS+joiningSeq);
		baseInfoService.getRepository().save(dam);
		//转圈
		if (joining.getPaddock()!=null && joining.getPaddock().getId()!=null){
			paddockChangeService.add(joining.getPaddock(), dam.getCode(), joining.getOrg(),joining.getRecorder());
		}
		return joining;
	}
	
	@Override
	public Message updateUiAiVerify(Long id) {
		Joining joining=joiningRepository.findOne(id);
		if (!SystemM.PUBLIC_TRUE.equals(joining.getParity().getIsNewestParity())){
			return GlobalConfig.setAbnormal("该记录胎次已关闭,不能修改");
		}
		if (embryoFlushRepository.findByDonor_idAndProject_id(
				joining.getDam().getId(), joining.getProject().getId())!=null){
			return GlobalConfig.setAbnormal("该羊已冲胚,不能修改AI记录");
		}
		return GlobalConfig.SUCCESS;
	}
	
	@Override
	public Message updateAiVerifys(Long id,String sireCode, Date joiningDate) {
		Joining joining=joiningRepository.findOne(id);
		BaseInfo baseInfo = baseInfoService.findByCodeOrRfid(sireCode);
		if (baseInfo==null){
			return GlobalConfig.setAbnormal("该公羊不存在");
		}
		if (!SystemM.PUBLIC_SEX_SIRE.equals(baseInfo.getSex())){
			return GlobalConfig.setAbnormal("该公羊性别错误");
		}
		if (DateUtils.dateSubDate(joining.getParity().getStartDate(), joiningDate)>0){
			return GlobalConfig.setAbnormal("配种日期不能大于胎次开始日期,该羊的胎次日期为:"
					+DateUtils.DateToStr(joining.getParity().getStartDate()));
		}
		return GlobalConfig.SUCCESS;
	}
	
	@Override
	public Message updateAi(Long id, String sireCode, Date joiningDate) {
		Joining joining=joiningRepository.findOne(id);
		joining.setSire(baseInfoService.findByCodeOrRfid(sireCode));
		joining.setJoiningDate(joiningDate);
		//重新计算后代品种
		joining.setChildBreed(breedService.findByComparisonbreedIds(
				joining.getDam().getBreed(), joining.getSire().getBreed()));
		joiningRepository.save(joining);
		return GlobalConfig.SUCCESS;
	}
	@Override
	public Message joiningSireVerify(String sireCode,Long orgId) {
		BaseInfo sire = baseInfoService.findByCodeOrRfid(sireCode);
		String sys = "";
		if (sire==null){
			return GlobalConfig.setAbnormal(sireCode+":该羊不存在");
		}
		if (!SystemM.NORMAL.equals(sire.getPhysiologyStatus()+"")){
			if((sire.getPhysiologyStatus()+"").equals("2")) {
				sys= "已疾病淘汰";
				sys=sireCode+":"+sys;
			}
			if((sire.getPhysiologyStatus()+"").equals("3")) {
				sys= "已育种淘汰";
				sys=sireCode+":"+sys;
			}
			if((sire.getPhysiologyStatus()+"").equals("4")) {
				sys= "已死亡";
				sys=sireCode+":"+sys;
			}
			if((sire.getPhysiologyStatus()+"").equals("5")) {
				sys= "疾病淘汰待审核";
				sys=sireCode+":"+sys;
			}
			if((sire.getPhysiologyStatus()+"").equals("6")) {
				sys= "育种淘汰待审核";
				sys=sireCode+":"+sys;
			}
			if((sire.getPhysiologyStatus()+"").equals("7")) {
				sys= "死亡待审核";
				sys=sireCode+":"+sys;
			}
			if((sire.getPhysiologyStatus()+"").equals("8")) {
				sys= "审核失败";
			}
			if((sire.getPhysiologyStatus()+"").equals("9")) {
				sys= "销售待审核";
				sys=sireCode+":"+sys;
			}
			if((sire.getPhysiologyStatus()+"").equals("10")) {
				sys= "已销售";
				sys=sireCode+":"+sys;
			}
			if((sire.getPhysiologyStatus()+"").equals("11")) {
				sys= "调拨待审核";
				sys=sireCode+":"+sys;
			}
			if((sire.getPhysiologyStatus()+"").equals("12")) {
				sys= "已调拨";
				sys=sireCode+":"+sys;
			}
			
			//return GlobalConfig.setIsPass(sireCode+":"+sys);
		}
		if(sire.getOrg().getId()!=orgId) {
			if(!sys.equals("")) {
				sys= sys+"且在"+sire.getOrg().getBrief();
			}else {
				sys = sireCode+":"+sire.getOrg().getBrief();
			}
			//return GlobalConfig.setIsPass(sireCode+":该羊在"+sire.getOrg().getBrief());
		}
		if(!sys.equals("")) {
			return GlobalConfig.setIsPass(sys);
		}
		if (!SystemM.PUBLIC_SEX_SIRE.equals(sire.getSex())){
			return GlobalConfig.setAbnormal(sireCode+":该羊性别错误");
		}
		if (sire.getRank()==null){
			return GlobalConfig.setAbnormal(sireCode+":该公羊没有定级");
		}
		return GlobalConfig.SUCCESS;
	}
	
	@Override
	public BreedingPlan isBreedingPlan(BaseInfo dam,BaseInfo sire) {
		List<BreedingPlanDetailDam> breeedingPalnDetaildams= breedingPlanDetailDamService.getRepository()
			      .findByDam_idAndBreedingPlan_flag(dam.getId(), SystemM.PUBLIC_TRUE);
			BreedingPlanDetailSire breedingPlanDetailSire = breedingPlanDetailSireService.getRepository()
				.findByBreedingPlan_idAndSireType(sire.getId(), SystemM.PUBLIC_TRUE);
		BreedingPlan breeding=null;
		if (breedingPlanDetailSire==null){
			return null;
		}
		for (BreedingPlanDetailDam b:breeedingPalnDetaildams){
			if(b.getBreedingPlan().equals(breedingPlanDetailSire.getBreedingPlan())){
				breeding=b.getBreedingPlan();
			}
		}
		return breeding;
	}
}
