package org.wangdong.springboot.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.beiqisoft.aoqun.Start;
import com.beiqisoft.aoqun.controller.BaseInfoController;
import com.beiqisoft.aoqun.controller.LambingDamController;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.BreedService;
import com.beiqisoft.aoqun.service.DamBreedStateRepService;
import com.beiqisoft.aoqun.service.EarChangeService;
import com.beiqisoft.aoqun.service.JoiningService;
import com.beiqisoft.aoqun.service.OrganizationService;
import com.beiqisoft.aoqun.service.ParityService;
import com.beiqisoft.aoqun.service.PregnancyService;
import com.beiqisoft.aoqun.service.WeightService;
import com.beiqisoft.aoqun.service.guide.LambingDamGuideService;
import com.beiqisoft.aoqun.util.MyUtils;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = Start.class)
//@WebAppConfiguration
public class Aoqun {
	@Test
	public void test1() throws ParseException {
		
	}
	/*@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public WeightService weightService;
	@Autowired
	public ParityService parityService;
	@Autowired
	public PregnancyService pregnancyService;
	@Autowired
	public LambingDamGuideService lambingDamGuideService;
	@Autowired
	public JoiningService joiningService;
	@Autowired
	public LambingDamController lambingDamController;
	@Autowired
	public BaseInfoController baseInfoController;
	@Autowired
	public BreedService breedService;
	@Autowired
	public OrganizationService organizationService;
	@Autowired
	public DamBreedStateRepService damBreedStateRepService;
	@Autowired
	public EarChangeService earChangeService;*/
	

//	@Test
//	public void t2(){
//	}
	
//	@Test
//	public void test(){
//		for (String s:BaseInfoData.earTag()){
//			String[] code = s.split(",");
//			if (code.length==1){
//				continue;
//			}
//			if (code[1]!=null && !"".equals(code[1])){
//				BaseInfo base = baseInfoService.getRepository().findByCode(code[0].trim());
//				baseInfoService.getRepository().save(base.setRfidReturnThis(code[1]));
//			}
//		}
//	}
	
//	@Test
//	public void test(){
//		pregnancyCreate();
//		LambingDamimport();
//	}
	
//	@Test
//	public void datas(){
//		List<BaseInfo> bases=excelBaseInfo();
//		lambingDamGuideService.getRepository().findByFlag(SystemM.PUBLIC_FALSE).stream().forEach(x->{
//			System.out.println(x.getId());
//			for (BaseInfo b:bases){
//				if(x.getId().equals(b.getLambingDam().getId())){
//					System.err.println(b.getLambingDam().getId());
//				}
//			}
//			bases.stream().filter(b -> x.getId().equals( b.getLambingDam().getId())).forEach(b->{
//				System.out.println("羊只:"+b.getLambingDam().getId());
//			});
//		});
//	}
	
	/**
	 * 一、 查询lambingDamGuide下正确的母羊产羔表
	 * 二、 if(lambingDamController.addVerify().isCodeEqNormal()){ 调用接口判断是否允许添加母羊产羔记录
	 * 		1.添加母羊产羔记录并保存记录
	 * 		2.查找该产羔母羊下的所有记录
	 * 		for (BaseInfo b:bases){
	 * 			1).添加羔羊记录
	 * 		}
	 * }
	 * else{
	 * 		保存记录为不能修改,及错误原因
	 * }
	 * 
	 * */
//	public void LambingDamimport(){
//		List<BaseInfo> bases=excelBaseInfo();
//		lambingDamGuideService.getRepository().findByFlag(SystemM.PUBLIC_FALSE).parallelStream().forEach(x->{
//			BaseInfo base = baseInfoService.getRepository().findOne(x.getDamId());
//			Message message = lambingDamController.addVerify(base.getCode(), x.getBornDate());
//			if (message.isCodeEqNormal()){
//				System.out.println("母羊"+x.getId());
//				LambingDam lambingDam = lambingDamController.appAdd(transition(x), base.getCode());
//				//System.err.println(lambingDam.getId());
//				bases.stream().filter(b -> x.getId().equals(b.getLambingDam().getId())).forEach(b->{
//					b.setLambingDam(lambingDam);
//					Message lambMessage =baseInfoController.lambSaveVerify(b);
//					if (lambMessage.isCodeEqNormal()){
//						baseInfoController.lambSave(b, SystemM.CODE_BIRTH_STATE_NORMAL);
//						System.out.println(b.getCode()+"添加成功");
//					}else{
//						System.out.println(b.getCode()+":"+lambMessage.getMsg());
//					}
//				});
//			}else{
//				x.setFlag(SystemM.PUBLIC_FALSE);
//				x.setError(message.getMsg());
//				lambingDamGuideService.getRepository().save(x);
//			}
//		});;
//	}
	
//	public void testtt(){
//		List<BaseInfo> bases=excelBaseInfo();
//		LambingDam l = new LambingDam();
//		l.setId(27213L);
//		bases.parallelStream().filter(x -> 27213L==x.getLambingDam().getId()).forEach(x->{
//			System.out.println(x.getLambingDam().getId());
//		});
//	}
	
	
//	public List<BaseInfo> excelBaseInfo(){
//		List<BaseInfo> bases = new ArrayList<BaseInfo>();
//		Arrays.asList(BaseInfoData.baseInfoDate()).parallelStream().forEach(data->{
//			String[] s= data.split(",");
//			BaseInfo base = new BaseInfo();
//			base.setBreed(breedService.getRepository().findOne((MyUtils.strToLong(s[0]))));
//			base.setCode(s[1]);
//			base.setRfid(s[2]);
//			base.setBirthDay(DateUtils.StrToDate(s[3]));
//			base.setSex(s[4]);
//			base.setDamCode(baseInfoService.getRepository().findOne(MyUtils.strToLong(s[5])).getCode());
//			base.setSireCode(baseInfoService.getRepository().findOne(MyUtils.strToLong(s[6])).getCode());
//			base.setInitialWeigh(MyUtils.strToDoble(s[7]));
//			//品相
//			Looks looks= new Looks();
//			looks.setSplash(s[8]);
//			looks.setHorn(s[9]);
//			looks.setFeature(s[10]);
//			looks.setHair(s[11]);
//			looks.setTooth(s[14]);
//			looks.setFootColor(s[15]);
//			looks.setMouthColor(s[16]);
//			looks.setFats(s[18]);
//			base.setLooks(looks);
//			//muyang
//			LambingDam l = new LambingDam();
//			l.setId(MyUtils.strToLong(s[20]));
//			base.setLambingDam(l);
//			Organization org= new Organization();
//			org.setId(MyUtils.strToLong(s[21]));
//			base.setOrg(org);
//			bases.add(base);
//		});
//		return bases;
//	}
	
	
//	public LambingDam transition(LambingDamGuide x){
//		LambingDam lambingDam = new LambingDam();
//		lambingDam.setBornDate(x.getBornDate());
//		lambingDam.setEasyFlag(x.getEasyFlag());
//		lambingDam.setMotherhood(x.getMotherhood());
//		lambingDam.setMastitis(x.getMasitis());
//		lambingDam.setBreas(x.getBreas());
//		Organization org = new Organization();
//		org.setId(20L);
//		lambingDam.setOrg(org);
//		return lambingDam;
//	}
	
	
//	public void lambingDam(){
//		List<LambingDamGuide> lambingDamGuides = (List<LambingDamGuide>) lambingDamGuideService.getRepository().findAll();
//		lambingDamGuides.parallelStream().forEach(x->{
//			Parity parity = parityService.getRepository()
//					.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,x.getDamId());
//			if (parity!=null){
//				Joining j = joiningService.getRepository().findByIsNewestJoiningAndParity_id(SystemM.PUBLIC_TRUE, parity.getId());
//				if (j!=null){
//					int day = DateUtils.dateSubDate(x.getBornDate(), j.getJoiningDate());
//					x.setJoining(j.getId());
//					if (Math.abs(147-day)>5){
//						System.out.println(x.getId()+":"+(147-day));
//					}
//					lambingDamGuideService.getRepository().save(x);
//				}
//				else System.out.println("配种为空:"+x.getId());
//			}
//			else System.out.println("胎次为空:"+x.getId());
//		});
//	}

//	public void pregnancyCreate() {
//		baseInfoService.getRepository().findByIdIn(data()).parallelStream().forEach(x->{
//			if (x.getBreedingState()!=null){
//				System.out.println(x.getCode());
//				if (SystemM.BASE_INFO_BREEDING_STATE_UNPREGNANCY.equals(x.peelBreedingState())){
//					Parity parity = parityService.getRepository()
//										.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, x.getId());
//					Pregnancy p= pregnancyService.getRepository().findByPregnancySeqAndParity_id(subString(x.getBreedingState()), parity.getId());
//					p.setResult(SystemM.RESULTS_PREGNANCY);
//					pregnancyService.getRepository().save(p);
//					x.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_GESTATION+subString(x.getBreedingState().toString()));
//					baseInfoService.getRepository().save(x);
//				}
//			}else{
//				System.out.println(x.getId());
//			}
//		});;
//	}
	
//	public String subString(String str){
//		return str.substring(str.length()-(str.length()-2));
//	}

	// @Autowired
	// public PaddockChangeService paddockChangeService;
	// @Autowired
	// public BaseInfoService baseInfoService;
	// @Autowired
	// public WeightService weightService;
	// @Autowired
	// public PvgService pvgService;
	// @Autowired
	// public LambingDamService lambingDamService;
	// @Autowired
	// public MonthRankOnHandService monthRankOnHandService;
	// @Autowired
	// public OnHandService onHandService;
	// @Autowired
	// public PaddockService paddockService;
	// @Autowired
	// public JoiningLeadRepository joiningLeadRepository;
	// @Autowired
	// public LambingDamGuideService lambingDamGuideService;
	// @Autowired
	// public PregnancyGuideService pregnancyGuideService;
	// @Autowired
	// public JoiningGuideService joiningGuideService;
	// @Autowired
	// public ParityService parityService;
	// @Autowired
	// public OrganizationService organizationService;
	// @Autowired
	// public BreedingPlanService breedingPlanService;
	// @Autowired
	// public JoiningRepository joiningRepository;
	// @Autowired
	// public PregnancyRepository pregnancyRepository;
	// @Autowired
	// public LambingDamRepository lambingDamRepository;
	// @Autowired
	// public WeaningRepository weaningRepository;
	// @Autowired
	// public BreedingWeedRepository breedingWeedRepository;
	// @Autowired
	// public IllnessWeedRepository illnessWeedRepository;
	// @Autowired
	// public DeathDisposalRepository deathDisposalRepository;
	// @Autowired
	// public BreedService breedService;
	// @Test
	// public void test1(){
	//
	// }

	// @Test
	// public void test1(){
	// List<BaseInfo> bases = (List<BaseInfo>)
	// baseInfoService.getRepository().findAll();
	// //List<Weight> ws = new ArrayList<>();
	// bases.parallelStream().forEach(x->{
	// System.out.println(x.getCode());
	// Weight w= weightService.getRepository().findByBase_idAndType(x.getId(),
	// SystemM.WEIGHT_TYPE_INITIAL);
	// if (w==null){
	// weightService.getRepository().save(new Weight().addBirthWeight(x));
	// }
	// });
	// }

	// 生成胎次
	// @Test
	// public void test9(){
	// List<BaseInfo> list = new ArrayList<BaseInfo>();
	// for (BaseInfo
	// b:baseInfoService.getRepository().findByPhysiologyStatusAndFlag(1L,
	// SystemM.PUBLIC_FALSE)){
	// if (DateUtils.dateSubDate(new Date(), b.getBirthDay())>=240){
	// if(b.getSex().equals("2")){
	// System.out.println(b.getBirthDay());
	// b.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT);
	// parityService.addHistory(b);
	// list.add(b);
	// }
	// }
	// }
	// baseInfoService.getRepository().save(list);
	// }

	// 生成配种胎次
	// @Test
	// public void test11(){
	// List<BaseInfo> list = new ArrayList<BaseInfo>();
	// List<Long> ids = joiningGuideService.getRepository().findByDam();
	// //System.err.println(ids);
	// Long[] idl= new Long[ids.size()];
	// for (BaseInfo
	// b:baseInfoService.getRepository().findByIdIn(ids.toArray(idl))){
	// System.out.println(b.getId());
	// if ("".equals(b.getBreedingState()) || b.getBreedingState()==null ){
	// b.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT);
	// parityService.addHistory(b);
	// list.add(b);
	// }
	// }
	// baseInfoService.getRepository().save(list);
	// }

	/**
	 * 一、将配种、测孕、产羔生成一张表 二、查找大表中的母羊 "SELECT j.joining_dam_id FROM JoiningLead j
	 * GROUP BY j.joining_dam_id 三、根据母羊id分组 for(Long id:damId){ 四、把大表中所有配种母羊id为
	 * id的查找并按照配种时间升序排序 int crossNumber=1; for(){ 记录 五、将配种记录写入Jining中并把母羊繁殖状态置位
	 * ‘配’+crossNumber 六、判断是否存在测孕记录 if(存在){
	 * 七、将测孕记录导入到pregnancy表中，并且将羊只的繁殖状态改为测孕表中的状态 （Result:怀孕未孕）+crossNumber
	 * 八、判断是否存在产羔记录 if(存在){ 九、将产羔记录添加到lambing中 十、添加断奶记录 十一、添加胎次，并且将羊只繁殖状态改为空怀
	 * crossNumber=1; } } else{ crossNumber+1; 七、将配种序号+1 } } }
	 */
	// @Test
	// public void lead(){
	// //遍历母羊
	// for (Long damId:joiningLeadRepository.findByDam()){
	// System.out.println(damId);
	// //遍历所有大表下母只记录
	// BaseInfo base = baseInfoService.getRepository().findOne(damId);
	// if (35033L!=base.getId()) continue;
	// int crossNumber=1;
	// for (JoiningLead j: joiningLeadRepository.findByJoiningDamId(damId)){
	// //添加配种记录
	// cross(j,crossNumber,base);
	// //判断是是否存在测孕
	// if (j.getPregnancy()!=null){
	//
	// //添加测孕
	// pregnancy(j,crossNumber,base);
	// crossNumber++;
	// //判断是存在产羔
	// if(j.getLambing_id()!=null){
	// //添加产羔
	// LambingDam lambingDam = new LambingDam();
	// lambing(j,crossNumber,base,lambingDam);
	// //添加断奶及胎次
	// weaning(base,lambingDam);
	// crossNumber=1;
	// }
	// }else{
	// crossNumber++;
	// }
	// }
	// }
	// }

	/**
	 * 添加配种记录 一、将记录导入joining表中 二、判断crossNumber是否为1 if(1==crossNumber){
	 * 三、将setJoiningSeq("1");配种序号 四、将setIsNewestJoining("1");是否最新状态 } else{
	 * 三、将setJoiningSeq(crossNumber);配种序号 四、查找上一次配种记录将setIsNewestJoining("0");
	 * 五、将setIsNewestJoining("1");是否最新状态 }
	 * 六、将setParity(parityRepository.findBy...);胎次
	 * 七、计算胎次的空怀天数,并且将自然繁殖胎次+1;(可以不执行) 七、改变羊只繁殖状态
	 */
	// public void cross(JoiningLead j,int crossNumber,BaseInfo base){
	// Joining joining = new Joining();
	// JoiningGuide joiningGuide=
	// joiningGuideService.getRepository().findOne(j.getJoiningId());
	// //一、
	// joining.setId(joiningGuide.getId());
	// joining.setDam(base);
	// joining.setSire(baseInfoService.getRepository().findOne(joiningGuide.getSireId()));
	// joining.setJoiningDate(joiningGuide.getJoiningDate());
	// if (joiningGuide.getChildBreedId()!=null){
	// joining.setChildBreed(breedService.getRepository().findOne(joiningGuide.getChildBreedId()));
	// }
	// joining.setJoiningType(joiningGuide.getJoiningType());
	// joining.setOrg(organizationService.getRepository().findOne(joiningGuide.getOrgId()));
	// //joining.setBreedingPlan(breedingPlanService.getRepository().findOne(joiningGuide.getBreedingPlanId()));
	//
	// Parity parity
	// =parityService.getRepository().findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,
	// base.getId());
	//
	// if (parity==null){
	// System.out.println("胎次为空");
	// }
	// //二、
	// if(1 == crossNumber){
	// joining.setJoiningSeq("1");
	// joining.setIsNewestJoining("1");
	// }else{
	// joining.setJoiningSeq(crossNumber+"");
	// joining.setIsNewestJoining("1");
	// //修改上一次配种
	// Joining joininged=
	// joiningRepository.findByIsNewestJoiningAndParity_id(SystemM.PUBLIC_TRUE,
	// parity.getId());
	// joininged.setIsNewestJoining("0");
	// joiningRepository.save(joininged);
	// }
	// //六
	// joining.setParity(parity);
	// //七
	// base.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_CROSS+crossNumber);
	//
	// //保存
	// baseInfoService.getRepository().save(base);
	// joiningRepository.save(joining);
	// }

	/**
	 * 添加测孕 一、将记录导入到Pregnancy表中 二、获取胎次parity 三、判断测孕结果是否为怀孕
	 * if(Result.equals("怀孕")){ 四、计算胎次配种时间(当前测孕时间-第一次配种时间) //可以不执行 四、改变羊只繁殖状态 }
	 * else{ 四、改变羊只繁殖状态 } 五、setParity(parity)//保存测孕
	 */
	// public void pregnancy(JoiningLead j,int crossNumber,BaseInfo base){
	// Pregnancy pregnancy = new Pregnancy();
	// PregnancyGuide pregnancyGuide =
	// pregnancyGuideService.getRepository().findOne(j.getPregnancy());
	//
	// // 一、
	// pregnancy.setDam(base);
	// pregnancy.setResult(pregnancyGuide.getResult());
	// pregnancy.setOrg(organizationService.getRepository().findOne(pregnancyGuide.getOrgId()));
	//
	// //二、
	// Parity parity
	// =parityService.getRepository().findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,
	// base.getId());
	//
	// //三、
	// if("怀孕".equals(pregnancy.getResult())){
	// base.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_GESTATION+crossNumber);
	// }
	// else{
	// base.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_UNPREGNANCY+crossNumber);
	// }
	// //四、
	// pregnancy.setParity(parity);
	//
	// //保存
	// pregnancyRepository.save(pregnancy);
	// baseInfoService.getRepository().save(base);
	// parityService.getRepository().save(parity);
	// }

	/**
	 * 添加产羔记录 一、保存产羔记录 二、获取胎次记录 三、计算胎次妊娠添加//可以不执行 四、修改羊只繁殖状态为哺乳
	 */
	// public void lambing(JoiningLead j,int crossNumber,BaseInfo
	// base,LambingDam lambingDam){
	//
	// LambingDamGuide lambingDamGuide =
	// lambingDamGuideService.getRepository().findOne(j.getLambing_id());
	// //一、
	// lambingDam.setDam(base);
	// lambingDam.setBornDate(lambingDamGuide.getBornDate());
	// lambingDam.setBornCountF(Integer.parseInt(lambingDamGuide.getBornConunt1()));
	// lambingDam.setAliveCountF(Integer.parseInt(lambingDamGuide.getAliveCount1()));
	// lambingDam.setDeadCountF(Integer.parseInt(lambingDamGuide.getDeadCount1()));
	// lambingDam.setBadCountF(Integer.parseInt(lambingDamGuide.getBadCount1()));
	// lambingDam.setBornCountM(Integer.parseInt(lambingDamGuide.getBornConunt2()));
	// lambingDam.setAliveCountM(Integer.parseInt(lambingDamGuide.getAliveCount2()));
	// lambingDam.setDeadCountM(Integer.parseInt(lambingDamGuide.getDeadCount2()));
	// lambingDam.setBadCountM(Integer.parseInt(lambingDamGuide.getBadCount2()));
	//
	// lambingDam.setOrg(organizationService.getRepository().findOne(lambingDamGuide.getOrgId()));
	//
	// lambingDam.setMastitis(lambingDamGuide.getMasitis());
	// lambingDam.setMotherhood(lambingDamGuide.getMotherhood());
	// lambingDam.setBreas(lambingDamGuide.getBreas());
	//
	// //二、
	// Parity parity
	// =parityService.getRepository().findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,
	// base.getId());
	// lambingDam.setParity(parity);
	//
	// //四、
	// base.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_LACTATION);
	//
	// //五
	// lambingDamRepository.save(lambingDam);
	// baseInfoService.getRepository().save(base);
	// parityService.getRepository().save(parity);
	// }

	/**
	 * 添加断奶 一、生成胎次 二、修改羊只繁殖状态为空怀
	 * 
	 * 添加胎次 一、获取胎次 二、修改胎次为已关闭，修改胎次关闭日期（断奶日期） 三、添加一条胎次记录
	 * 四、新胎次记录parityMaxNumber+1//总胎次数
	 */
	// public void weaning(BaseInfo base,LambingDam lambingDam){
	// Weaning weaning = new Weaning();
	// weaning.setDam(base);
	// weaning.setOrg(lambingDam.getOrg());
	//
	// if (lambingDam.getBornDate().before(DateUtils.StrToDate("2016-12-31"))){
	// weaning.setWeaningDate(DateUtils.dateAddInteger(lambingDam.getBornDate(),
	// 90));
	// }else{
	// weaning.setWeaningDate(DateUtils.dateAddInteger(lambingDam.getBornDate(),
	// 60));
	// }
	// base.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT);
	//
	// //修改胎次
	// Parity parity
	// =parityService.getRepository().findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,
	// base.getId());
	// weaning.setParity(parity);
	//
	// parity.setIsClosed(SystemM.PUBLIC_TRUE);
	// parity.setIsNewestParity(SystemM.PUBLIC_FALSE);
	// parity.setClosedDate(weaning.getWeaningDate());
	//
	// //添加胎次
	// parityService.add(base, parity);
	// baseInfoService.getRepository().save(base);
	// weaningRepository.save(weaning);
	// parityService.getRepository().save(parity);
	// }
	//
	/**
	 * 羊只库存状态
	 */
	// @Test
	// public void physiologyStatus(){
	// List<BreedingWeed> breedingWeeds= (List<BreedingWeed>)
	// breedingWeedRepository.findAll();
	// List<BaseInfo> bases= new ArrayList<>();
	// for (BreedingWeed b:breedingWeeds){
	// BaseInfo base = b.getBase();
	// base.setPhysiologyStatus(MyUtils.strToLong(SystemM.DEATH_BREEDING));
	// base.setIsAudit(SystemM.PUBLIC_TRUE);
	// bases.add(base);
	// }
	// List<IllnessWeed> illnessWeeds= (List<IllnessWeed>)
	// illnessWeedRepository.findAll();
	// for (IllnessWeed b:illnessWeeds){
	// BaseInfo base = b.getBase();
	// base.setPhysiologyStatus(MyUtils.strToLong(SystemM.DEATH_WEED_OUT));
	// base.setIsAudit(SystemM.PUBLIC_TRUE);
	// bases.add(base);
	// }
	// List<DeathDisposal> deathDisposals = (List<DeathDisposal>)
	// deathDisposalRepository.findAll();
	// for (DeathDisposal b:deathDisposals){
	// BaseInfo base = b.getBase();
	// base.setPhysiologyStatus(MyUtils.strToLong(SystemM.DEATH));
	// base.setIsAudit(SystemM.PUBLIC_TRUE);
	// bases.add(base);
	// }
	// baseInfoService.getRepository().save(bases);
	// }

	// @Test
	// public void lambing(){
	// List<Lambing> lambings =(List<Lambing>) lambingRepository.findAll();
	// List<BaseInfo> bases = new ArrayList<>();
	// for (Lambing l:lambings){
	// System.out.println(l.getBase().getCode());
	// //l.getBase().setLambingDam(l.getLambingDam());
	// l.getBase().setState(SystemM.CODE_BIRTH_STATE_NORMAL);
	// bases.add(l.getBase());
	// }
	// baseInfoService.getRepository().save(bases);
	// }
}