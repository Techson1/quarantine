package com.beiqisoft.aoqun.task.realize;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;

import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.BreedParameter;
import com.beiqisoft.aoqun.entity.BreedingState;
import com.beiqisoft.aoqun.entity.Contact;
import com.beiqisoft.aoqun.entity.Performance;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.BreedParameterService;
import com.beiqisoft.aoqun.service.BreedingOnHandService;
import com.beiqisoft.aoqun.service.BreedingStateService;
import com.beiqisoft.aoqun.service.DamBreedStateRepService;
import com.beiqisoft.aoqun.service.MonthRankOnHandService;
import com.beiqisoft.aoqun.service.OnHandService;
import com.beiqisoft.aoqun.service.PaddockService;
import com.beiqisoft.aoqun.service.ParityService;
import com.beiqisoft.aoqun.service.PerformanceService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.ExecuteTime;
import com.beiqisoft.aoqun.util.MyUtils;

/**
 * BaseInfo定时任务实现类
 * @author 程哲旭
 * @version 1.0
 * @time  2017年11月30日上午14:43:00
 */
public class BaseInfoTaskRealize{
	private BaseInfoService baseInfoService;
	private BreedingStateService breedingStateService;
	private BreedParameterService breedParameterService;
	private PaddockService paddockService;
	private ParityService parityService;
	
	private OnHandService onHandService;
	private MonthRankOnHandService monthRankOnHandService;
	private BreedingOnHandService breedingOnHandService;
	private PerformanceService performanceService;
	private DamBreedStateRepService damBreedStateRepService;
	
	private List<BreedParameter> breedParameters;

	/**任务状态*/
	private boolean taskRealize=true;

	private static BaseInfoTaskRealize realize=new BaseInfoTaskRealize();
	
	private BaseInfoTaskRealize(){
		instantiation();
	}
	
	/**
	 * 获取BaseInfo定时任务实例类
	 * */
	public static BaseInfoTaskRealize getBaseInfoTaskRealize(){
		return realize;
	}
	
	/**
	 * 开启任务执行
	 * */
	public void openRealize(){
		this.taskRealize=true;
	}
	
	/**
	 * 关闭任务执行
	 * */
	public void closeRealize(){
		this.taskRealize=false;
	}
	
	/**
	 * 获取任务状态
	 * */
	public boolean isTaskRealize(){
		return this.taskRealize;
	}
	
	/**
	 * 实例化
	 * */
	public void instantiation(ApplicationContext context){
		this.baseInfoService=context.getBean(BaseInfoService.class);
		this.breedingStateService=context.getBean(BreedingStateService.class);
		this.breedParameterService=context.getBean(BreedParameterService.class);
		this.paddockService=context.getBean(PaddockService.class);
		this.parityService=context.getBean(ParityService.class);
		
		//统计
		this.onHandService=context.getBean(OnHandService.class);
		this.monthRankOnHandService=context.getBean(MonthRankOnHandService.class);
		this.breedingOnHandService=context.getBean(BreedingOnHandService.class);
		this.performanceService=context.getBean(PerformanceService.class);
		this.damBreedStateRepService=context.getBean(DamBreedStateRepService.class);
	}
	
	/**
	 * Service实例化
	 * */
	public void instantiation(){
//		if (ContextUtils.getContext()==null){
//			return ;
//		}
//		if (this.baseInfoService==null){
//			this.baseInfoService=ContextUtils.getContext().getBean(BaseInfoService.class);
//			this.breedingStateService=ContextUtils.getContext().getBean(BreedingStateService.class);
//			this.breedParameterService=ContextUtils.getContext().getBean(BreedParameterService.class);
//			this.paddockService=ContextUtils.getContext().getBean(PaddockService.class);
//			this.parityService=ContextUtils.getContext().getBean(ParityService.class);
//			
//			//统计
//			this.onHandService=ContextUtils.getContext().getBean(OnHandService.class);
//			this.monthRankOnHandService=ContextUtils.getContext().getBean(MonthRankOnHandService.class);
//			this.breedingOnHandService=ContextUtils.getContext().getBean(BreedingOnHandService.class);
//			this.performanceService=ContextUtils.getContext().getBean(PerformanceService.class);
//			this.damBreedStateRepService=ContextUtils.getContext().getBean(DamBreedStateRepService.class);
//		}
	}
	
	public int getParameters(String parm){
		if (this.breedParameters==null){
			this.breedParameters=(List<BreedParameter>) breedParameterService.getRepository().findAll();
		}
		return breedParameters.stream().filter(x-> parm.equals(x.getName()))
				.collect(Collectors.toList()).get(0).getParameter();
	}
	
	/**
	 * 任务
	 * */
	public void task(){
		if (!isTaskRealize()) return;
		System.out.println("开始任务");
		ExecuteTime.getExecuteTime().inStartTime();
		//处理绩效考核
		performance();
		System.out.println("完成绩效考核");
		//存栏统计
		paddockNumber();
		System.out.println("完成存栏统计");
		//计算羊只库存状态
		bornStatus();
		System.out.println("完成羊只库存状态");
		//计算羊只月龄
		moonAge();
		System.out.println("完成羊只月龄");
		//处理羊只繁殖状态
		breedingState();
		System.out.println("完成羊只繁殖状态");
		//处理羊只繁殖状态
		ExecuteTime.getExecuteTime().inEndTime();
		System.out.println("结束任务");
	}
	
	/**
	 * 计算羊只月龄
	 * */
	public void moonAge(){
		List<BaseInfo> baseInfos = new ArrayList<>();
		for (BaseInfo b:baseInfoService.getRepository()
				.findByPhysiologyStatusAndFlag(MyUtils.strToLong(SystemM.NORMAL),SystemM.PUBLIC_FALSE)){
			b.setMoonAge(DateUtils.dateToAge(b.getBirthDay()));
			baseInfos.add(b);
		}
		baseInfoService.getRepository().save(baseInfos);
	}
	
	/**
	 * 计算羊只繁殖状态
	 * */
	public void breedingState(){
		List<BreedingState> breedingStates=(List<BreedingState>) breedingStateService.getRepository().findAll();
		
		for (BreedingState b:breedingStates){
			//处理供体恢复
			if (SystemM.BASE_INFO_BREEDING_STATE_DONOR_PREPARE.equals(b.getState())){
				if (DateUtils.dateSubDate(new Date(),b.getDate())>=getParameters(SystemM.BREED_PARAMETER_DONOR_REPLY)){
					baseInfoService.getRepository().save(b.getBaseInfo()
							.setBreedingStateReutrnThis(SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT));
					breedingStateService.getRepository().delete(b);
				}
			}
		}
	}
	
	/**
	 * 存栏统计
	 * */
	public void paddockNumber(){
		//存栏统计
		onHandService.getRepository().save(paddockService.getRepository().findByGroupBy());
		//定级月龄存栏
		monthRankOnHandService.getRepository().save(baseInfoService.getRepository().findOnHandByBreedAndMoonAge());
		//繁殖状态存栏表
		breedingOnHandService.getRepository().save(breedingOnHandService.getRepository().findByStatistics());
		//母羊状态日报表
		damBreedStateRepService.getRepository().save(damBreedStateRepService.statistics());
	}
	
	/**
	 * 计算羊只生理状态及繁殖状态
	 * */
	public void bornStatus(){
		List<BaseInfo> baseList = new ArrayList<BaseInfo>();
		for (BaseInfo b:baseInfoService.getRepository()
				.findByPhysiologyStatusAndFlag(MyUtils.strToLong(SystemM.NORMAL),SystemM.PUBLIC_FALSE)){
			int dayAge=DateUtils.dateToDayAge(b.getBirthDay());
			//计算母羊性成熟
			if(dayAge>=getParameters(SystemM.BREED_PARAMETER_CROSS)){
				if (SystemM.BASE_INFO_BREEDING_STATE_STATELESS.equals(b.getBreedingState()) 
						&& SystemM.PUBLIC_SEX_DAM.equals(b.getSex())){
					//修改羊只状态
					b.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT);
					//添加胎次
					parityService.add(b);
					baseList.add(b);
				}
			}
			//计算公羊性成熟
			if(dayAge>=getParameters(SystemM.SEMEN_DAY_AGE)){
				if(SystemM.BASE_INFO_BREEDING_STATE_STATELESS.equals(b.getBreedingState())
						&& SystemM.PUBLIC_SEX_SIRE.equals(b.getSex())){
					b.setBreedingState(SystemM.BASE_INFO_BREEDING_NOT_SEMEN);
					baseList.add(b);
				}
			}
			//计算羔羊
			if (dayAge<getParameters(SystemM.BREED_PARAMETER_YOUTH)){
				b.setBornStatus(SystemM.BASE_INFO_PHYSIOLOGY_STATUS_LAMB);
				baseList.add(b);
				continue;
			}
			//计算成年羊
			if (dayAge>=getParameters(SystemM.BREED_PARAMETER_GROW)){
				//该羊的生理状态不是成年羊
				if (!SystemM.BASE_INFO_PHYSIOLOGY_STATUS_GROW.equals(b.getBornStatus())){
					b.setBornStatus(SystemM.BASE_INFO_PHYSIOLOGY_STATUS_GROW);
					baseList.add(b);
					continue;
				}
			}
			else{
				b.setBornStatus(SystemM.BASE_INFO_PHYSIOLOGY_STATUS_YOUTH);
				baseList.add(b);
			}
		}
		baseInfoService.getRepository().save(baseList);
	}
	
	/**
	 * 处理绩效考核
	 * */
	public void performance(){
		Calendar calendar = Calendar.getInstance();
		Integer year = calendar.get(Calendar.YEAR);
		Integer month = calendar.get(Calendar.MONTH )+1;
		Date date = DateUtils.dateAddInteger(new Date(), -1);
		List<Performance> performances = new ArrayList<>();
		//存货量考核
		for (Contact c:performanceService.getRepository().inventory()){
		    Performance p=performanceService.getRepository()
		    				.findByYearAndMonthAndContact_idAndType(year,month,c.getId(),SystemM.PERFORMANCE_TYPE_FEEDING_COUNT);
		    if (p == null)
		    	p = new Performance(c,SystemM.PERFORMANCE_TYPE_FEEDING_COUNT,date,year,month).init().dayWrite(c.getCount()+"");
		    else
		    	p.dayWrite(c.getCount()+"");
		    performances.add(p);
		}
		//死亡考核
		for (Contact c:performanceService.getRepository().death(DateUtils.dateAddInteger(new Date(), -1),new Date())){
			Performance p=performanceService.getRepository()
					.findByYearAndMonthAndContact_idAndType(year,month,c.getId(),SystemM.PERFORMANCE_TYPE_DEATH);
		    if (p == null)
		    	p = new Performance(c,SystemM.PERFORMANCE_TYPE_DEATH,date,year,month).init().dayWrite(c.getCount()+"");
		    else
		    	p.dayWrite(c.getCount()+"");
		    performances.add(p);
		}
		//疾病淘汰考核
		for (Contact c:performanceService.getRepository().illness(DateUtils.dateAddInteger(new Date(), -1),new Date())){
			Performance p=performanceService.getRepository()
					.findByYearAndMonthAndContact_idAndType(year,month,c.getId(),SystemM.PERFORMANCE_TYPE_ILLNESS);
		    if (p == null)
		    	p = new Performance(c,SystemM.PERFORMANCE_TYPE_ILLNESS,date,year,month).init().dayWrite(c.getCount()+"");
		    else
		    	p.dayWrite(c.getCount()+"");
		    performances.add(p);
		}
		//育种淘汰考核
		for (Contact c:performanceService.getRepository().breeding(DateUtils.dateAddInteger(new Date(), -1),new Date())){
			Performance p=performanceService.getRepository()
					.findByYearAndMonthAndContact_idAndType(year,month,c.getId(),SystemM.PERFORMANCE_TYPE_BREEDING);
		    if (p == null)
		    	p = new Performance(c,SystemM.PERFORMANCE_TYPE_BREEDING,date,year,month).init().dayWrite(c.getCount()+"");
		    else
		    	p.dayWrite(c.getCount()+"");
		    performances.add(p);
		}
		performanceService.getRepository().save(performances);
	}
}
