package com.beiqisoft.aoqun.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
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
import com.beiqisoft.aoqun.entity.CodeRegister;
import com.beiqisoft.aoqun.entity.Looks;
import com.beiqisoft.aoqun.entity.OnHandMonth;
import com.beiqisoft.aoqun.entity.Weight;
import com.beiqisoft.aoqun.repository.BaseInfoRepository;
import com.beiqisoft.aoqun.repository.BreedParameterRepository;
import com.beiqisoft.aoqun.repository.CodeRegisterRepository;
import com.beiqisoft.aoqun.repository.JoiningRepository;
import com.beiqisoft.aoqun.repository.LambingDamRepository;
import com.beiqisoft.aoqun.repository.LooksRepository;
import com.beiqisoft.aoqun.repository.RamTrainingRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.BreedService;
import com.beiqisoft.aoqun.service.ParityService;
import com.beiqisoft.aoqun.service.RankTestService;
import com.beiqisoft.aoqun.service.WeightService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.MyUtils;

@Service
public class BaseInfoServiceImpl extends BaseServiceIml<BaseInfo,BaseInfoRepository> implements BaseInfoService{
	@PersistenceContext  
	private EntityManager em;
	
	@Autowired
	public BaseInfoRepository baseInfoRepository;
	@Autowired
	public BreedService breedService;
	@Autowired
	public CodeRegisterRepository codeRegisterRepository;
	@Autowired
	public ParityService parityService;
	@Autowired
	public LooksRepository looksRepository;
	@Autowired
	public LambingDamRepository lambingDamRepository;
	@Autowired
	public JoiningRepository joiningRepository;
	@Autowired
	public RamTrainingRepository ramTrainingRepository;
	@Autowired
	public BreedParameterRepository breedParameterRepository;
	@Autowired
	public RankTestService rankTestService;
	@Autowired
	public WeightService weightService;
	
	public Page<BaseInfo> find(final BaseInfo baseInfo) {
		return baseInfoRepository.findAll(new Specification<BaseInfo>() {
			public Predicate toPredicate(Root<BaseInfo> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(baseInfo,root,criteriaBuilder);
				
				if(baseInfo.getStartMoonAge()!=null && !"".equals(baseInfo.getStartMoonAge())){
					list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("moonAge")
							.as(Integer.class),Integer.parseInt(baseInfo.getStartMoonAge())));
				}
				if (baseInfo.getEndMoonAge()!=null && !"".equals(baseInfo.getEndMoonAge())){
					list.add(criteriaBuilder.lessThanOrEqualTo(root.get("moonAge")
							.as(Integer.class),Integer.parseInt(baseInfo.getEndMoonAge())));
				}
				if (baseInfo.getBreedingStateDetail()!=null && !"".equals(baseInfo.getBreedingStateDetail())){
					list.add(criteriaBuilder.equal(root.get("breedingState"), baseInfo.getBreedingStateDetail()));
				}
				if (baseInfo.getBreedingState()!=null && !"".equals(baseInfo.getBreedingState())){
					list.add(criteriaBuilder.equal(criteriaBuilder
							.substring(root.get("breedingState"), 1, 2),baseInfo.getBreedingState()));
				}
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(baseInfo.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "birthDay"));
	}
	public Page<BaseInfo> find1(final BaseInfo baseInfo) {
		return baseInfoRepository.findAll(new Specification<BaseInfo>() {
			public Predicate toPredicate(Root<BaseInfo> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(baseInfo,root,criteriaBuilder);
				
				if(baseInfo.getStartMoonAge()!=null && !"".equals(baseInfo.getStartMoonAge())){
					list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("moonAge")
							.as(Integer.class),Integer.parseInt(baseInfo.getStartMoonAge())));
				}
				if (baseInfo.getEndMoonAge()!=null && !"".equals(baseInfo.getEndMoonAge())){
					list.add(criteriaBuilder.lessThanOrEqualTo(root.get("moonAge")
							.as(Integer.class),Integer.parseInt(baseInfo.getEndMoonAge())));
				}
				if (baseInfo.getBreedingStateDetail()!=null && !"".equals(baseInfo.getBreedingStateDetail())){
					list.add(criteriaBuilder.equal(root.get("breedingState"), baseInfo.getBreedingStateDetail()));
				}
				if (baseInfo.getBreedingState()!=null && !"".equals(baseInfo.getBreedingState())){
					System.out.println("查询");
					list.add(criteriaBuilder.equal(criteriaBuilder
							.substring(root.get("breedingState"), 1, 2),baseInfo.getBreedingState()));
				}
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(baseInfo.getPageNum(), 150, Sort.Direction.DESC, "birthDay"));
	}
	public Page<BaseInfo> find(BaseInfo baseInfo, int size) {
		return baseInfoRepository.findAll(new Specification<BaseInfo>() {
			public Predicate toPredicate(Root<BaseInfo> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(baseInfo,root,criteriaBuilder);
				
				if(baseInfo.getStartMoonAge()!=null && !"".equals(baseInfo.getStartMoonAge())){
					list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("moonAge")
							.as(String.class),baseInfo.getStartMoonAge()));
				}
				if (baseInfo.getEndMoonAge()!=null && !"".equals(baseInfo.getEndMoonAge())){
					list.add(criteriaBuilder.lessThanOrEqualTo(root.get("moonAge")
							.as(String.class),baseInfo.getEndMoonAge()));
				}
				if (baseInfo.getBreedingStateDetail()!=null && !"".equals(baseInfo.getBreedingStateDetail())){
					list.add(criteriaBuilder.equal(root.get("breedingState"), baseInfo.getBreedingStateDetail()));
				}
				if (baseInfo.getBreedingState()!=null && !"".equals(baseInfo.getBreedingState())){
					System.out.println("查询");
					list.add(criteriaBuilder.equal(criteriaBuilder
							.substring(root.get("breedingState"), 1, 2),baseInfo.getBreedingState()));
				}
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(baseInfo.getPageNum(), size, Sort.Direction.DESC, "birthDay"));
	}

	@Override
	public Page<BaseInfo> lambFind(BaseInfo baseInfo) {
		return baseInfoRepository.findAll((root,query,criteriaBuilder)->{
			List<Predicate> list = getEntityPredicate(baseInfo,root,criteriaBuilder);
			list.add(criteriaBuilder.isNotNull(root.join("lambingDam", JoinType.INNER).get("id")));
			query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
			return query.getRestriction();
		},new PageRequest(baseInfo.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "birthDay","ctime"));
	}
	
	public BaseInfoRepository getRepository() {
		return baseInfoRepository;
	}

	@Transactional
	public boolean baseInfoRegisteradd(BaseInfo baseInfo) {
		BaseInfo dam=baseInfoRepository.findByCode(baseInfo.getDamCode());
		BaseInfo Sire=baseInfoRepository.findByCode(baseInfo.getSireCode());
		baseInfo.setPhysiologyStatus(MyUtils.strToLong(SystemM.NORMAL));
		if (dam==null && baseInfo.getDamCode()!=null && !"".equals(baseInfo.getDamCode())){
			dam=new BaseInfo(baseInfo.getDamCode(),baseInfo.getDamBirthDay(),baseInfo.getDamBreed());
			baseInfoRepository.save(dam);
		}
		if (Sire==null && baseInfo.getSireCode()!=null && !"".equals(baseInfo.getSireCode())){
			Sire=new BaseInfo(baseInfo.getSireCode(),baseInfo.getSireBirthDay(),baseInfo.getSireBreed());
			baseInfoRepository.save(Sire);
		}
		int damDay=breedParameterRepository.findByName(SystemM.BREED_PARAMETER_CROSS).getParameter();
		int sireDay=breedParameterRepository.findByName(SystemM.SEMEN_DAY_AGE).getParameter();
		int growDay=breedParameterRepository.findByName(SystemM.BREED_PARAMETER_GROW).getParameter();
		int youthDay=breedParameterRepository.findByName(SystemM.BREED_PARAMETER_YOUTH).getParameter();
		baseInfoRepository.save(baseInfo.registerAddReturnThis(damDay,sireDay,growDay,youthDay,dam,Sire));
		if (DateUtils.dateSubDate(new Date(), baseInfo.getBirthDay())>damDay){
			parityService.add(baseInfo);
		}
		//添加原始体重出生记录
		weightService.addBirthWeight(baseInfo);
		//TODO 羊只定级添加
		return true;
	}
	
	@Override
	public Message verify(BaseInfo dam, BaseInfo sire, BaseInfo baseInfo) {
		//一、校验父号母号是否存在
		if (dam==null && sire==null){
			return GlobalConfig.SUCCESS;
		}
		//二、校验母羊
		if (dam!=null && sire==null){
			if(dam.getSex().equals(SystemM.PUBLIC_SEX_SIRE)){
				return new Message(GlobalConfig.ABNORMAL,"母羊性别错误");
			}
			if(DateUtils.dateSubDate(baseInfo.getBirthDay(),dam.getBirthDay())<395){
				return new Message(GlobalConfig.ABNORMAL,"母羊月龄必须大于自身13个月");
			}
			if (!dam.getBreed().getId().equals(baseInfo.getBreed().getId())){
				return new Message(GlobalConfig.ABNORMAL,"该羊的品种必须和母羊品种一致,母羊的品种是:"+dam.getBreed().getBreedName());
			}
			return GlobalConfig.SUCCESS;
		}
		//三、校验公羊
		if (dam==null && sire!=null){
			if(sire.getSex().equals(SystemM.PUBLIC_SEX_DAM)){
				return new Message(GlobalConfig.ABNORMAL,"公羊性别错误");
			}
			if(DateUtils.dateSubDate(baseInfo.getBirthDay(),sire.getBirthDay())<395){
				return new Message(GlobalConfig.ABNORMAL,"公羊月龄必须大于自身13个月");
			}
			if (!sire.getBreed().getId().equals(baseInfo.getBreed().getId())){
				return new Message(GlobalConfig.ABNORMAL,"该羊的品种必须和公羊品种一致,公羊的品种是:"+sire.getBreed().getBreedName());
			}
			return GlobalConfig.SUCCESS;
		}
		//四、校验母羊、公羊及后代品种
		if(dam.getSex().equals(SystemM.PUBLIC_SEX_SIRE)){
			return new Message(GlobalConfig.ABNORMAL,"母羊性别错误");
		}
		if(sire.getSex().equals(SystemM.PUBLIC_SEX_DAM)){
			return new Message(GlobalConfig.ABNORMAL,"公羊性别错误");
		}
		if(DateUtils.dateSubDate(baseInfo.getBirthDay(),dam.getBirthDay())<395){
			return new Message(GlobalConfig.ABNORMAL,"母羊月龄必须大于自身13个月");
		}
		if(DateUtils.dateSubDate(baseInfo.getBirthDay(),sire.getBirthDay())<395){
			return new Message(GlobalConfig.ABNORMAL,"公羊月龄必须大于自身13个月");
		}
		//根据父母品种计算血统,并且根据血统查找品种
		Breed breed=breedService.findByComparisonbreedIds(dam.getBreed(), sire.getBreed());
		if (!baseInfo.getBreed().getId().equals(breed.getId())){
			return new Message(GlobalConfig.ABNORMAL,"该羊的母亲的品种是:"+dam.getBreed().getBreedName()
					+",该羊的父亲的品种是:"+sire.getBreed().getBreedName()+",所以该羊的品种应为:"+breed.getBreedName());
		}
		return GlobalConfig.SUCCESS;
	}
	
	@Override
	public Message codeAndRfidVerify(String code, Long rfid,Long breedId) {
		//一、判断该可视耳号与电子耳标是否存在BaseInfo表中使用
		if (baseInfoRepository.findByCode(code)!=null){
			return new Message(GlobalConfig.ABNORMAL,"该可视耳号已与羊只绑定,请更换可视耳号");
		}
		if (baseInfoRepository.findByRfid(""+rfid)!=null){
			return new Message(GlobalConfig.ABNORMAL,"该电子耳号已于羊只绑定,请更换电子耳号");
		}
		
		//二、判断该可视耳标与电子耳标是否在CodeRegister中存在
		CodeRegister codeRegister=codeRegisterRepository.findByCode(code);
		CodeRegister rfidRegister = null;
		if (rfid!=null && !"".equals(rfid)){
			rfidRegister=codeRegisterRepository.findByVisualCode(rfid);
		}
		if (codeRegister==null){
			return new Message(GlobalConfig.ABNORMAL,"该可视耳标不存在,是否确认添加");
		}
		if (rfidRegister==null && rfid!=null && !"".equals(rfid)){
			return new Message(GlobalConfig.ABNORMAL,"该电子耳标不存在,是否确认添加");
		}
		//三、判读可视耳标与电子耳标是否已用
		if (SystemM.PUBLIC_TRUE.equals(codeRegister.getUseState())){
			return new Message(GlobalConfig.ABNORMAL,"该可视耳标已使用,请更换可视耳标");
		}
		if(rfid!=null && !"".equals(rfid) && SystemM.PUBLIC_TRUE.equals(rfidRegister.getUseState())){
			return new Message(GlobalConfig.ABNORMAL,"该电子耳标已使用,请更换电子耳标");
		}
		//四、判断可视耳标、电子耳标及羊只品种id是否相同
		if (!codeRegister.getBreed().getId().equals(breedId)){
			return new Message(GlobalConfig.ABNORMAL,"该可视耳标品种与该羊的品种不相符,请更换可视耳号");
		}
		if (rfid!=null && "".equals(rfid) &&  !rfidRegister.getBreed().getId().equals(breedId)){
			return new Message(GlobalConfig.ABNORMAL,"该电子耳标品种与该羊的品种不相符,请更换电子耳号");
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Page<BaseInfo> findList(BaseInfo baseInfo) {
		return baseInfoRepository.findAll(new Specification<BaseInfo>() {
			public Predicate toPredicate(Root<BaseInfo> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(baseInfo,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(baseInfo.getPageNum(), GlobalConfig.PAGE_MAX_SIZE, Sort.Direction.DESC, "ctime"));
	}

	@Override
	public BaseInfo findByCodeOrRfid(String code) {
		return baseInfoRepository.findByCodeOrRfid(code,code);
	}
	public BaseInfo findByCodeOrRfidAndOrgId(String code,Long orgId) {
		return baseInfoRepository.findByCodeOrRfidAndOrgId(code,code,orgId);
	}

	@Override
	public Message lambSaveVerify(BaseInfo lamb) {
		if (lamb.getCode()!=null && codeRegisterRepository.findByCode(lamb.getCode())==null){
			return new Message(GlobalConfig.ABNORMAL,"该羊的可视耳号不存在");
		}
		if ("".equals(lamb.getRfid())){
			lamb.setRfid(null);
		}
		if (lamb.getRfid()!=null && codeRegisterRepository.findByVisualCode(Long.parseLong(lamb.getRfid()))==null){
			return new Message(GlobalConfig.ABNORMAL,"该羊的电子耳号不存在");
		}
		
		if(lamb.getCode()!=null && findByCodeOrRfid(lamb.getCode())!=null){
			return new Message(GlobalConfig.ABNORMAL,"该羊可视耳号已使用不能添加");
		}
		
		if (lamb.getRfid()!=null){
			if(findByCodeOrRfid(lamb.getRfid())!=null){
				return new Message(GlobalConfig.ABNORMAL,"该羊电子耳号已使用不能添加");
			}
		}
		/*if(lamb.getInitialWeigh()==null){
			return new Message(GlobalConfig.ABNORMAL,"出生重为必填项");
		}*/
		return GlobalConfig.SUCCESS;
	}

	@Transactional
	@Override
	public Message lambSave(BaseInfo baseInfo) {
		baseInfo.setDam(findByCodeOrRfid(baseInfo.getDamCode()));
		baseInfo.setSire(findByCodeOrRfid(baseInfo.getSireCode()));
		baseInfo.setPhysiologyStatus(MyUtils.strToLong(SystemM.NORMAL));
		if (baseInfo.getDam()!=null){
			baseInfo.setPaddock(baseInfo.getDam().getPaddock());
		}
		if (SystemM.CODE_BIRTH_STATE_NORMAL.equals(baseInfo.getState())){
			baseInfo.setFlag(SystemM.PUBLIC_FALSE);
		}
		else{
			baseInfo.setFlag(SystemM.PUBLIC_TRUE);
		}
		baseInfo.setLevel();
		if (baseInfo.getEwesCode()!=null && !"".equals(baseInfo.getEwesCode())){
			baseInfo.setEwes(findByCodeOrRfid(baseInfo.getEwesCode()));
		}
		if (baseInfo.getFosterDamCode()!=null && !"".equals(baseInfo.getFosterDamCode())){
			baseInfo.setFosterDam(findByCodeOrRfid(baseInfo.getFosterDamCode()));
		}
		//羔羊登记录入月龄
		baseInfo.setMoonAge(DateUtils.dateToAge(baseInfo.getBirthDay()));
		//添加品相
		Looks look=baseInfo.getLooks();
		look.setBase(baseInfo);
		looksRepository.save(look);
		baseInfoRepository.save(baseInfo
				.setBreedingStateReutrnThis(SystemM.BASE_INFO_BREEDING_STATE_STATELESS));
		
		codeRegisterRepository.save(codeRegisterRepository
				.findByCode(baseInfo.getCode()).setUseStateReturnThis(SystemM.PUBLIC_TRUE));
		if (baseInfo.getRfid()!=null && !"".equals(baseInfo.getRfid())){
		  CodeRegister codeRegister=codeRegisterRepository
					.findByVisualCode(MyUtils.strToLong(baseInfo.getRfid()));
		  if (codeRegister!=null){
			  codeRegisterRepository.save(codeRegister .setUseStateReturnThis(SystemM.PUBLIC_TRUE));
		  }
		}
		
		//修改母羊产羔id
		lambingDamRepository.save(lambingDamRepository.findOne(
				baseInfo.getLambingDam().getId()).addBase(baseInfo));
		
		//修改同胎数
		List<BaseInfo> bases = baseInfoRepository.findByLambingDam_id(baseInfo.getLambingDam().getId());
		for (BaseInfo b:bases){
			b.setTheSameFetus(bases.size()+"");
		}
		baseInfoRepository.save(bases);
		
		//断奶重添加
		weightService.addBirthWeight(baseInfo);
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Page<BaseInfo> findByLambingDam(Long lambingDamId,Integer page) {
		Page<BaseInfo> baseInfos=baseInfoRepository.findByLambingDam_id(lambingDamId,pageable(page, "ctime"));
		baseInfos.forEach(x -> x.setLooks(looksRepository.findByBase_id(x.getId())));
		return baseInfos;
	}

	@Override
	public boolean findByEarTagAndSex(String earTag, String sex) {
		BaseInfo baseInfo= findByCodeOrRfid(earTag);
		if (baseInfo==null){
			return false;
		}
		if (!sex.equals(baseInfo.getSex())){
			return false;
		}
		return flagVerify(earTag).isCodeEqNormal();
	}
	
	

	@Transactional
	@Override
	public Message lambDel(Long id){
		BaseInfo base=baseInfoRepository.findOne(id);
		looksRepository.delete(looksRepository.findByBase_id(base.getId()));
		lambingDamRepository.save(base.getLambingDam().delBase(base));
		//删除
		Weight weight= weightService.getRepository().findByBase_idAndType(id,SystemM.WEIGHT_TYPE_INITIAL);
		if (weight!=null){
			weightService.getRepository().delete(weight.getId());
		}
		
		Long lambingDamId=base.getLambingDam().getId();
		List<BaseInfo> bases = baseInfoRepository.findByLambingDam_id(lambingDamId);
		for (BaseInfo b:bases){
			b.setTheSameFetus(bases.size()-1+"");
		}
		baseInfoRepository.save(bases);
		baseInfoRepository.delete(base);
		//修改可视耳标
		codeRegisterRepository.save(codeRegisterRepository.findByCode(base.getCode()).setUseStateReturnThis(SystemM.PUBLIC_FALSE)); 
		if(base.getRfid()!=null && !"".equals(base.getRfid())){
			codeRegisterRepository.save(codeRegisterRepository
					.findByVisualCode(MyUtils.strToLong(base.getRfid()))
					.setUseStateReturnThis(SystemM.PUBLIC_FALSE));
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message lambEditVerify(Long id) {
		BaseInfo base=baseInfoRepository.findOne(id);
		if(joiningRepository.findByDam_id(base.getId()).isEmpty()){
			return GlobalConfig.setAbnormal("不能修改羔羊");
		}
		if (ramTrainingRepository.findByRam_id(base.getId()).isEmpty()){
			return GlobalConfig.setAbnormal("不能修改羔羊");
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message lambEdit(BaseInfo lamb) {
		BaseInfo lambBase = baseInfoRepository.findOne(lamb.getId());
		lambBase.setInitialWeigh(lamb.getInitialWeigh());
		
		//添加出生重
		
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Page<BaseInfo> market(BaseInfo baseInfo) {
		return baseInfoRepository.findAll((root,query,criteriaBuilder) ->{
			List<Predicate> list = getEntityPredicate(baseInfo,root,criteriaBuilder);
			list.add(criteriaBuilder.equal(root.join("rank", JoinType.LEFT).get("rank"), SystemM.RANKTEST_RANK_MARKET));
			if(baseInfo.getStartMoonAge()!=null && !"".equals(baseInfo.getStartMoonAge())){
				list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("moonAge")
						.as(Integer.class),Integer.parseInt(baseInfo.getStartMoonAge())));
			}
			if (baseInfo.getEndMoonAge()!=null && !"".equals(baseInfo.getEndMoonAge())){
				list.add(criteriaBuilder.lessThanOrEqualTo(root.get("moonAge")
						.as(Integer.class),Integer.parseInt(baseInfo.getStartMoonAge())));
			}
			if (baseInfo.getBreedingStateDetail()!=null && !"".equals(baseInfo.getBreedingStateDetail())){
				list.add(criteriaBuilder.equal(root.get("breedingState"), baseInfo.getBreedingStateDetail()));
			}
			if (baseInfo.getBreedingState()!=null && !"".equals(baseInfo.getBreedingState())){
				list.add(criteriaBuilder.equal(criteriaBuilder
						.substring(root.get("breedingState"), 1, 2),baseInfo.getBreedingState()));
			}
			query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
			return query.getRestriction();
		},new PageRequest(baseInfo.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}

	@Override
	public Message codeVerify(String code) {
		BaseInfo base=findByCodeOrRfid(code);
		if (base==null){
			return GlobalConfig.setAbnormal("该羊不存在");
		}
		if (!SystemM.NORMAL.equals(base.getPhysiologyStatus()+"")){
			return GlobalConfig.setAbnormal("该羊不在场,不能进行操作");
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Page<BaseInfo> findByAudit(BaseInfo baseInfo,
			Date startDeliveryDate, Date endDeliveryDate) {
		return baseInfoRepository.findAll((root,query,criteriaBuilder) ->{
			List<Predicate> list = getEntityPredicate(baseInfo,root,criteriaBuilder);
			if (startDeliveryDate!=null){
				list.add(criteriaBuilder.greaterThanOrEqualTo(
						root.get("deliveryDate").as(Date.class),startDeliveryDate));
			}
			if (endDeliveryDate!=null){
				list.add(criteriaBuilder.lessThanOrEqualTo(
						root.get("ctime").as(Date.class),(Date) endDeliveryDate));
			}
			
			query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
			return query.getRestriction();
		},new PageRequest(baseInfo.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "deliveryDate"));
	}

	@Override
	public Message flagVerify(String earTag) {
		BaseInfo base= findByCodeOrRfid(earTag);
		if (base==null){
			return GlobalConfig.setAbnormal(earTag+":该羊不存在");
		}
		if (SystemM.PUBLIC_TRUE.equals(base.getFlag())){
			return GlobalConfig.setIsPass(earTag+":该羊是归档羊");
		}
		
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message flagVerify(BaseInfo base) {
		if (SystemM.PUBLIC_TRUE.equals(base.getFlag())){
			return GlobalConfig.setAbnormal(base.getCode()+":该羊是归档羊");
		}
		if (SystemM.PUBLIC_TRUE.equals(base.getFlag())){
			return GlobalConfig.setAbnormal(base.getCode()+":该羊是归档羊");
		}
		if (!SystemM.NORMAL.equals(base.getPhysiologyStatus()+"")){
			return GlobalConfig.setAbnormal(base.getCode()+":该羊不在库");
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public void findByGenealogy(Map<String, Object> baseMap,BaseInfo base) {
		baseMap.put("base", base);
		baseMap.put("looks", looksRepository.findByBase_id(base.getId()));
	}

	@Override
	public void moonAgeEdit(String code, Date date){
		BaseInfo base = findByCodeOrRfid(code);
		base.setMoonAge(DateUtils.dateToAge(date, base.getBirthDay()));
		baseInfoRepository.save(base);
	}
	/*@Override
	public Integer testFirstNum(Long id, String sex, Date startDate) {
		String hql ="Select count(*) From BaseInfo b where 1=1 "  
	             +" and b.org.id = :orgid "  
	             +" and b.sex = :sex "  
	             +" and b.birthDay <=:startDate ";  
		
		return (Integer) session.createQuery(hql).setParameter("startDate", startDate)
				.setParameter("sex", sex)
				.setParameter("orgid", id).uniqueResult();
	}*/
	@Override
	public List<BaseInfo> find2(final BaseInfo baseInfo) {
		return baseInfoRepository.findAll(new Specification<BaseInfo>() {
			public Predicate toPredicate(Root<BaseInfo> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(baseInfo,root,criteriaBuilder);
				
				if(baseInfo.getStartMoonAge()!=null && !"".equals(baseInfo.getStartMoonAge())){
					list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("moonAge")
							.as(String.class),baseInfo.getStartMoonAge()));
				}
				if (baseInfo.getEndMoonAge()!=null && !"".equals(baseInfo.getEndMoonAge())){
					list.add(criteriaBuilder.lessThanOrEqualTo(root.get("moonAge")
							.as(String.class),baseInfo.getEndMoonAge()));
				}
				if (baseInfo.getBreedingStateDetail()!=null && !"".equals(baseInfo.getBreedingStateDetail())){
					list.add(criteriaBuilder.equal(root.get("breedingState"), baseInfo.getBreedingStateDetail()));
				}
				if (baseInfo.getBreedingState()!=null && !"".equals(baseInfo.getBreedingState())){
					System.out.println("查询");
					list.add(criteriaBuilder.equal(criteriaBuilder
							.substring(root.get("breedingState"), 1, 2),baseInfo.getBreedingState()));
				}
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		});
	}
	@Override
	public BaseInfo findByCode(String code) {
		return baseInfoRepository.findByCode(code);
	}
	@Override
	public BaseInfo findByRfid(String rfid) {
		return baseInfoRepository.findByRfid(rfid);
	}
}