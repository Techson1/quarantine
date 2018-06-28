package org.wangdong.springboot.test;

public class PerformanceTest {
	
//	@Autowired
//	public PaddockChangeService paddockChangeService;
//	@Autowired
//	public BaseInfoService baseInfoService;
//	@Autowired
//	public WeightService weightService;
//	@Autowired
//	public PvgService pvgService;
//	@Autowired
//	public LambingDamService lambingDamService;
//	@Autowired
//	public MonthRankOnHandService monthRankOnHandService;
//	@Autowired
//	public OnHandService onHandService;
//	@Autowired
//	public PaddockService paddockService;
//	@Autowired
//	public JoiningLeadRepository joiningLeadRepository;
//	@Autowired
//	public LambingDamGuideService lambingDamGuideService;
//	@Autowired
//	public PregnancyGuideService pregnancyGuideService;
//	@Autowired
//	public JoiningGuideService joiningGuideService;
//	@Autowired
//	public ParityService parityService;
//	@Autowired
//	public OrganizationService organizationService;
//	@Autowired
//	public BreedingPlanService breedingPlanService;
//	@Autowired
//	public JoiningRepository joiningRepository;
//	@Autowired
//	public PregnancyRepository pregnancyRepository;
//	@Autowired
//	public LambingDamRepository lambingDamRepository;
//	@Autowired
//	public WeaningRepository weaningRepository;
//	@Autowired
//	public BreedingWeedRepository breedingWeedRepository;
//	@Autowired
//	public IllnessWeedRepository illnessWeedRepository;
//	@Autowired
//	public DeathDisposalRepository deathDisposalRepository;
//	@Autowired
//	public BreedService breedService;
	
//	@Test
//	public void test1(){
//		
//	}
	
//	@Test
//	public void test1(){
//		List<BaseInfo> bases = new ArrayList<>();
//		joiningLeadRepository.findByDam().stream().parallel().forEach(x->{
//			lead(x,bases);
//		});
//		baseInfoService.getRepository().save(bases);
//	}
//	
//	@Test
//	public void test(){
//		List<BaseInfo> bases = new ArrayList<>();
//    	//遍历母羊
//    	for (Long damId:joiningLeadRepository.findByDam()){
//    		if (35033L==damId) continue;
//    		//遍历所有大表下母只记录
//    		lead(damId,bases);
//    	}
//    	baseInfoService.getRepository().save(bases);
//	}
//	
//    /**
//     * 一、将配种、测孕、产羔生成一张表
//     * 二、查找大表中的母羊 "SELECT j.joining_dam_id FROM JoiningLead j GROUP BY j.joining_dam_id
//     * 三、根据母羊id分组
//     * for(Long id:damId){
//     * 四、把大表中所有配种母羊id为 id的查找并按照配种时间升序排序 
//     * 	int crossNumber=1;
//     * 	for(){ 记录
//     * 		五、将配种记录写入Jining中并把母羊繁殖状态置位 ‘配’+crossNumber
//     * 		六、判断是否存在测孕记录
//     * 			if(存在){
//     * 				七、将测孕记录导入到pregnancy表中，并且将羊只的繁殖状态改为测孕表中的状态 （Result:怀孕未孕）+crossNumber
//     * 				八、判断是否存在产羔记录
//     * 				if(存在){
//     * 					九、将产羔记录添加到lambing中
//     * 					十、添加断奶记录
//     * 					十一、添加胎次，并且将羊只繁殖状态改为空怀
//     * 					crossNumber=1;
//     * 				}
//     *			}
//     *			else{
//     *				crossNumber+1;
//     *				七、将配种序号+1
//     *			}
//     * 	}
//     * }
//     * */
//    public void lead(Long damId,List<BaseInfo> bases){
//    	BaseInfo base = baseInfoService.getRepository().findOne(damId);
//    	System.out.println(base.getId());
//    	bases.add(base);
//    	//遍历母羊
//    	int crossNumber=1;
//    	for (JoiningLead j: joiningLeadRepository.findByJoiningDamId(damId)){
//    		Parity parity =parityService.getRepository().findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, base.getId());
//    		//添加配种记录
//    		cross(j,crossNumber,base,parity);
//    		//判断是是否存在测孕
//    		if (j.getPregnancy()!=null){
//    			//添加测孕
//    			pregnancy(j,crossNumber,base,parity);
//    			crossNumber++;
//    			//判断是存在产羔
//    			if(j.getLambing_id()!=null){
//    				//添加产羔
//    				LambingDam lambingDam = new LambingDam();
//    				lambing(j,crossNumber,base,lambingDam,parity);
//    				//添加断奶及胎次
//    				weaning(base,lambingDam,parity);
//    				crossNumber=1;
//    			}
//    		}else{
//    			crossNumber++;
//    		}
//    	}
//    }
//    
//    /**
//     * 添加配种记录
//     * 一、将记录导入joining表中
//     * 二、判断crossNumber是否为1
//     * 	if(1==crossNumber){
//     * 		三、将setJoiningSeq("1");配种序号
//     * 		四、将setIsNewestJoining("1");是否最新状态
//     * 	}
//     * 	else{
//     * 		三、将setJoiningSeq(crossNumber);配种序号
//     * 		四、查找上一次配种记录将setIsNewestJoining("0");
//     * 		五、将setIsNewestJoining("1");是否最新状态
//     *  }
//     *  六、将setParity(parityRepository.findBy...);胎次
//     *  七、计算胎次的空怀天数,并且将自然繁殖胎次+1;(可以不执行)
//     *  七、改变羊只繁殖状态
//     * */
//    public void cross(JoiningLead j,int crossNumber,BaseInfo base,Parity parity){
//    	Joining joining = new Joining();
//    	JoiningGuide joiningGuide= joiningGuideService.getRepository().findOne(j.getJoiningId());
//    	//一、
//    	joining.setId(joiningGuide.getId());
//    	joining.setDam(base);
//    	joining.setSire(baseInfoService.getRepository().findOne(joiningGuide.getSireId()));
//    	joining.setJoiningDate(joiningGuide.getJoiningDate());
//    	if (joiningGuide.getChildBreedId()!=null){
//    		joining.setChildBreed(breedService.getRepository().findOne(joiningGuide.getChildBreedId()));
//    	}
//    	joining.setJoiningType(joiningGuide.getJoiningType());
//    	joining.setOrg(organizationService.getRepository().findOne(joiningGuide.getOrgId()));
//    	if (joiningGuide.getBreedingPlanId()!=null){
//    		joining.setBreedingPlan(breedingPlanService.getRepository().findOne(joiningGuide.getBreedingPlanId()));
//    	}
//    	
//    	if (parity==null){
//    		System.out.println("胎次为空");
//    	}
//    	//二、
//    	if(1 == crossNumber){
//    		joining.setJoiningSeq("1");
//    		joining.setIsNewestJoining("1");
//    	}else{
//    		joining.setJoiningSeq(crossNumber+"");
//    		joining.setIsNewestJoining("1");
//    		//修改上一次配种 
//    		Joining joininged= joiningRepository.findByIsNewestJoiningAndParity_id(SystemM.PUBLIC_TRUE, parity.getId());
//    		joininged.setIsNewestJoining("0");
//    		joiningRepository.save(joininged);
//    	}
//    	//六
//    	joining.setParity(parity);
//    	//七
//    	base.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_CROSS+crossNumber);
//    	
//    	//保存
//    	//baseInfoService.getRepository().save(base);
//    	joiningRepository.save(joining);
//    }
//    
//    /**
//     * 添加测孕
//     * 一、将记录导入到Pregnancy表中
//     * 二、获取胎次parity
//     * 三、判断测孕结果是否为怀孕
//     * if(Result.equals("怀孕")){
//     * 		四、计算胎次配种时间(当前测孕时间-第一次配种时间) //可以不执行
//     * 		四、改变羊只繁殖状态
//     * }
//     * else{
//     * 		四、改变羊只繁殖状态
//     * }
//     * 五、setParity(parity)//保存测孕
//     * */
//    public void pregnancy(JoiningLead j,int crossNumber,BaseInfo base,Parity parity){
//    	Pregnancy pregnancy = new Pregnancy();
//    	PregnancyGuide pregnancyGuide = pregnancyGuideService.getRepository().findOne(j.getPregnancy());
//    	
//    	// 一、
//    	pregnancy.setDam(base);
//    	pregnancy.setResult(pregnancyGuide.getResult());
//    	pregnancy.setOrg(organizationService.getRepository().findOne(pregnancyGuide.getOrgId()));
//    	pregnancy.setPregnancySeq(crossNumber+"");
//    	pregnancy.setPregnancyDate(pregnancyGuide.getDate());	
//    	pregnancy.setJoiningDate(j.getJoining_date());
//    	
//    	
//    	//三、
//    	if("怀孕".equals(pregnancy.getResult())){
//    		base.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_GESTATION+crossNumber);
//    	}
//    	else{
//    		base.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_UNPREGNANCY+crossNumber);
//    	}
//    	//四、
//    	pregnancy.setParity(parity);
//    	
//    	//保存
//    	pregnancyRepository.save(pregnancy);
//    	//baseInfoService.getRepository().save(base);
//    	//parityService.getRepository().save(parity);
//    }
//    
//    /**
//     * 添加产羔记录
//     * 一、保存产羔记录
//     * 二、获取胎次记录
//     * 三、计算胎次妊娠添加//可以不执行
//     * 四、修改羊只繁殖状态为哺乳
//     * */
//    public void lambing(JoiningLead j,int crossNumber,BaseInfo base,LambingDam lambingDam,Parity parity){
//    	
//    	LambingDamGuide lambingDamGuide = lambingDamGuideService.getRepository().findOne(j.getLambing_id());
//    	//一、
//    	lambingDam.setDam(base);
//    	lambingDam.setBornDate(lambingDamGuide.getBornDate());
//    	lambingDam.setBornCountF(Integer.parseInt(lambingDamGuide.getBornConunt1()));
//    	lambingDam.setAliveCountF(Integer.parseInt(lambingDamGuide.getAliveCount1()));
//    	lambingDam.setDeadCountF(Integer.parseInt(lambingDamGuide.getDeadCount1()));
//    	lambingDam.setBadCountF(Integer.parseInt(lambingDamGuide.getBadCount1()));
//    	lambingDam.setBornCountM(Integer.parseInt(lambingDamGuide.getBornConunt2()));
//    	lambingDam.setAliveCountM(Integer.parseInt(lambingDamGuide.getAliveCount2()));
//    	lambingDam.setDeadCountM(Integer.parseInt(lambingDamGuide.getDeadCount2()));
//    	lambingDam.setBadCountM(Integer.parseInt(lambingDamGuide.getBadCount2()));
//    	
//    	lambingDam.setOrg(organizationService.getRepository().findOne(lambingDamGuide.getOrgId()));
//    	
//    	lambingDam.setMastitis(lambingDamGuide.getMasitis());
//    	lambingDam.setMotherhood(lambingDamGuide.getMotherhood());
//    	lambingDam.setBreas(lambingDamGuide.getBreas());
//    	
//    	//二、
//    	lambingDam.setParity(parity);
//    	
//    	//四、
//    	base.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_LACTATION);
//    	
//    	//五
//    	lambingDamRepository.save(lambingDam);
//    	//baseInfoService.getRepository().save(base); 
//    	//parityService.getRepository().save(parity);
//    }
//    
//    /**
//     * 添加断奶
//     * 	一、生成胎次
//     * 	二、修改羊只繁殖状态为空怀
//     * 
//     * 添加胎次
//     * 一、获取胎次
//     * 二、修改胎次为已关闭，修改胎次关闭日期（断奶日期）
//     * 三、添加一条胎次记录
//     * 四、新胎次记录parityMaxNumber+1//总胎次数
//     * */
//    public void weaning(BaseInfo base,LambingDam lambingDam,Parity parity){
//    	Weaning weaning = new Weaning();
//    	weaning.setDam(base);
//    	weaning.setOrg(lambingDam.getOrg());
//    	
//    	if (lambingDam.getBornDate().before(DateUtils.StrToDate("2016-12-31"))){
//    		weaning.setWeaningDate(DateUtils.dateAddInteger(lambingDam.getBornDate(), 90));
//    	}else{
//    		weaning.setWeaningDate(DateUtils.dateAddInteger(lambingDam.getBornDate(), 60));
//    	}
//    	base.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT);
//    	
//    	//修改胎次
//    	//Parity parity =parityService.getRepository().findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, base.getId());
//    	weaning.setParity(parity);
//    	
//    	parity.setIsClosed(SystemM.PUBLIC_TRUE);
//    	parity.setIsNewestParity(SystemM.PUBLIC_FALSE);
//    	parity.setClosedDate(weaning.getWeaningDate());
//    	
//    	//添加胎次
//    	parityService.add(base, parity);
//    	//baseInfoService.getRepository().save(base);
//    	weaningRepository.save(weaning);
//    	parityService.getRepository().save(parity);
//    }
}
