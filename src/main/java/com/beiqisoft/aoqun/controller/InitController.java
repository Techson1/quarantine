package com.beiqisoft.aoqun.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.BreedParameter;
import com.beiqisoft.aoqun.entity.Contact;
import com.beiqisoft.aoqun.entity.Joining;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.entity.Pregnancy;
import com.beiqisoft.aoqun.entity.guide.JoiningGuide;
import com.beiqisoft.aoqun.entity.guide.JoiningLead;
import com.beiqisoft.aoqun.entity.guide.PregnancyGuide;
import com.beiqisoft.aoqun.service.BreedService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.MyUtils;

/**
 * 数据初始化访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "init")
public class InitController extends BaseController<Breed,BreedService>{

	private List<BreedParameter> breedParameters;
	public int i=0;
	
	@RequestMapping(value ="initDataBase")
    public Message initDataBase(Breed breed) throws InterruptedException{
		BreedTableInit();
		ContactTableInit();
		deathDisposal();
		baseInfo();
		paddock();
		return SUCCESS;
    }
	
	/**
	 * 淘汰初始化
	 * type:3疾病淘汰，2育种挑剔 ，1死亡淘汰
	 * 原始数据库表：deathDisposal
	 * 
	 * */
	private void deathDisposal(){
		
	}
	
	/**
	 * 栋栏初始化
	 * */
	private void paddock(){
		paddockService.getRepository().findAll().forEach(x ->{
			if ("-1".equals(x.getFlag())){
				x.setFlag(SystemM.PUBLIC_FALSE);
				paddockService.getRepository().save(x);
			}
		});
	}
	
	/**
	 * 品种数据初始化
	 * */
	private void BreedTableInit(){
		List<Breed> breeds= (List<Breed>) breedService.getRepository().findAll();
		for (Breed b:breeds){
			if (b.getBreedType().equals("1")){
				b.setBreedType(SystemM.BREED_PUREBRED);
			}
			if (b.getBreedType().equals("2")){
				b.setBreedType(SystemM.BREED_HYBRIDIZATION);
			}
			if (b.getBreedType().equals("3")){
				b.setBreedType(SystemM.BREED_CROSSBREED);
			}
			breedService.getRepository().save(b);
		}
	}
	/**
	 * 员工数据初始化
	 * */
	private void ContactTableInit(){
		List<Contact> contacts=(List<Contact>) contactService.getRepository().findAll();
		for (Contact c:contacts){
			if ("-1".equals(c.getFlag())){
				c.setFlag(SystemM.PUBLIC_FALSE);
			} 
			contactService.getRepository().save(c);
		}
	}
	
	/**
	 * 羊只数据处理
	 * */
	@RequestMapping(value ="baseInfo")
    public String baseInfo() throws InterruptedException{
		List<BaseInfo> list =(List<BaseInfo>) baseInfoService.getRepository().findAll();
		for (BaseInfo b:list){
			b.setPhysiologyStatus(MyUtils.strToLong(SystemM.NORMAL));
			b.setFlag(SystemM.PUBLIC_FALSE);
			if ("0".equals(b.getLifeStatusId())){
				b.setPhysiologyStatus(MyUtils.strToLong(SystemM.NORMAL));
			}
			if ("1".equals(b.getLifeStatusId())){
				b.setPhysiologyStatus(MyUtils.strToLong(SystemM.DELIVERY));
			}
			if ("2".equals(b.getLifeStatusId())){
				b.setPhysiologyStatus(MyUtils.strToLong(SystemM.DEATH));
			}
			if ("3".equals(b.getLifeStatusId())){
				b.setFlag(SystemM.PUBLIC_TRUE);
			}
		}
		baseInfoService.getRepository().save(list);
		return "1";
    }
	
	public int getParameters(String parm){
		if (this.breedParameters==null){
			this.breedParameters=(List<BreedParameter>) breedParameterService.getRepository().findAll();
		}
		return breedParameters.stream().filter(x-> parm.equals(x.getName()))
				.collect(Collectors.toList()).get(0).getParameter();
	}
	
	@RequestMapping(value ="baseInfoCrosst")
	public String baseInfoCrosst(){
		for (BaseInfo b:baseInfoService.getRepository().findByPhysiologyStatusAndFlag(MyUtils.strToLong(SystemM.NORMAL),SystemM.PUBLIC_FALSE)){
			int dayAge=DateUtils.dateToDayAge(b.getBirthDay());
			//计算母羊性成熟
			if(dayAge>=getParameters(SystemM.BREED_PARAMETER_CROSS)){
				if (SystemM.BASE_INFO_BREEDING_STATE_STATELESS.equals(b.getBreedingState()) 
						&& SystemM.PUBLIC_SEX_DAM.equals(b.getSex())){
					//修改羊只状态
					b.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT);
					baseInfoService.getRepository().save(b);
					//添加胎次
					parityService.addHistory(b);
				}
			}
		}
		return "2";
	}
	
	@RequestMapping(value="joiningGuide")
	public String joiningGuide(){
		List<Long> list= joiningLeadService.getRepository().findByType("0")
				.stream().map(x -> x.getJoiningDamId())
				.collect(Collectors.toList());
		list.stream().distinct().forEach(x ->{
			Parity p = parityService.getRepository().findByIsNewestParityAndDam_id("1", x);
			if (p==null){
				//胎次为空
			}
			else{
				joAdd(x,p);
			}
		});
		return "3";
	}
	
	private void joAdd(Long id,Parity p){
		List<JoiningLead> lead= joiningLeadService.getRepository().findByDamOrderByDate(id);
		i=0;
		for (JoiningLead t:lead){		
			//添加配种
			Joining jo= new Joining();
			JoiningGuide j= joiningGuideService.getRepository().findOne(t.getId());
			if (j==null){
				return ;
			}
			i++;
			jo.setId(t.getId());
			jo.setParity(p);
			BaseInfo baseInfo =baseInfoService.getRepository().findOne(j.getDamId());
			jo.setDam(baseInfo);
			jo.setSire(new BaseInfo(j.getSireId()));
			jo.setBreedingPlan(breedingPlanService.getRepository().findOne(j.getBreedingPlanId()));
			jo.setOrg(organizationService.getRepository().findOne(j.getOrgId()));
			jo.setChildBreed(breedService.getRepository().findOne(j.getChildBreedId()));
			jo.setJoiningDate(j.getJoiningDate());
			jo.setJoiningSeq(""+i);
			jo.setOnePregnancyDate(DateUtils.dateAddInteger(j.getJoiningDate(), 30));
			jo.setTwoPregnancyDate(DateUtils.dateAddInteger(j.getJoiningDate(), 45));
			//TODO 基因等级未处理
			jo.setGeneticLevel("");
			joiningService.getRepository().save(jo);
			baseInfo.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_CROSS+i);
			//添加测孕
			if (t.getPregnancy()!=null){
				Pregnancy pre = new Pregnancy();
				pre.setParity(p);
				pre.setDam(baseInfo);
				pre.setPregnancySeq(""+i);
				PregnancyGuide pg = pregnancyGuideService.getRepository().findOne(t.getPregnancy());
				pre.setId(t.getPregnancy());
				pre.setPregnancyDate(pg.getDate());
				pre.setResult(pg.getResult());
				pre.setPredictDate(DateUtils.dateAddInteger(pg.getDate(),140 ));
				if (!"怀孕".equals(pre.getResult())){
					baseInfo.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_UNPREGNANCY+i);
					jo.setResult(SystemM.RESULTS_UNPREGNANCY);
					pre.setResult(SystemM.RESULTS_UNPREGNANCY);
					pregnancyService.getRepository().save(pre);
				}
				else{
					baseInfo.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_GESTATION+i);
					jo.setResult(SystemM.RESULTS_PREGNANCY);
					pre.setResult(SystemM.RESULTS_PREGNANCY);
					pregnancyService.getRepository().save(pre);
					return;
				}
			}
			baseInfoService.getRepository().save(baseInfo);
		}
	}
}
