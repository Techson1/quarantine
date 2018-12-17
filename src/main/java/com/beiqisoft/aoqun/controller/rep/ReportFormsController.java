package com.beiqisoft.aoqun.controller.rep;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.LambingDam;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.entity.rep.DelRep;
import com.beiqisoft.aoqun.entity.rep.JoiningRep;
import com.beiqisoft.aoqun.entity.rep.LambingRep;
import com.beiqisoft.aoqun.service.BreedService;
import com.beiqisoft.aoqun.util.MyUtils;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "reportForms")
public class ReportFormsController extends BaseController<Breed,BreedService>{
	
	/**
	 * 死淘记录查询
	 * */
	@RequestMapping(value ="delList")
	public List<DelRep> delList(String type,Date startDate,Date endDate,Long orgId){
		 return deathdisposalService.delRepList(type, startDate, endDate,orgId);
	}
	
	/**
	 * 配种记录查询
	 * */
	@RequestMapping(value="joiningRepList")
	public List<JoiningRep> joiningRepList(Date startDate,Date endDate ,Long orgId){
		List<JoiningRep> joiningReps = new ArrayList<>();
		JoiningRep joining = new JoiningRep();
		for (Object o:joiningService.getRepository().joiningRep(startDate,endDate,orgId)){
			Object[] params = (Object[]) o;
			JoiningRep joingRep=new JoiningRep(params);
			joining.Add(joingRep);
			joiningReps.add(joingRep);
		}
		joiningReps.add(joining);
		return joiningReps;
	}
	
	/***/
	@RequestMapping(value="lambingDamRepList")
	public List<LambingRep> lambingDamRepList(Date startDate,Date endDate){
		List<LambingRep> lambingReps = new ArrayList<>();
		return lambingReps;
	}
	
	/**
	 * 母羊胎次统计表
	 */
	@JSON(type=Parity.class,include="code,breedName,birthDay,day,parityMaxNumber,"
			+ "joiningSql,bornTimes,aliveCount,deadCount,badCount,number,survival,"
			+ "closedTyep,startDate,closedDate")
	@RequestMapping(value="parityStatistics")
	public Map<String,Object> parityStatistics(Date startDate,Date endDate,Long orgId,Integer pageNum,String closedTyep){
		Map<String,Object> map = new HashMap<>();
		parityT t = new parityT();
		List<Parity> list = parityService.getRepository()
				.findByParityStatistics(startDate,endDate,orgId);
		paritylist(t,list);
		t.sum=list.size();
		t.lambingDamNum=(int) list.stream().map(x->x.getLambingDamId()).distinct().count();
		map.put("count", t.count());
		map.put("detail", listToPage(list,pageNum));
		return map;
	}
	
	/**
	 * 加载
	 * */
	private void paritylist(parityT t,List<Parity> list){
		list.stream().forEach(x->{
			LambingDam lambingDam = lambingDamService.getRepository().findByParityId(x.getId());
			x.setJoiningSql(joiningService.getRepository().joiningMaxparity(x.getId()));
			if (lambingDam!=null){
				x.setLambingDamId(x.getId());
				x.setBornTimes(lambingDam.getBornTimes());
				x.setAliveCount(lambingDam.getAliveCountM()+lambingDam.getAliveCountF());
				x.setBadCount(lambingDam.getBadCountM()+lambingDam.getBadCountF());
				x.setDeadCount(lambingDam.getDeadCountM()+lambingDam.getDeadCountF());
				x.setNumber(baseInfoService.getRepository().findBySurvival(lambingDam.getId()));
				x.setSurvival(MyUtils.percentage(lambingDam.getBornTimes(),MyUtils.strToLong(x.getNumber())));
				x.setClosedTyep("断奶");
				t.bornTimes+=x.getBornTimes();
				t.aliveCount+=x.getAliveCount();
				t.badCount+=x.getBadCount();
				t.deadCount+=x.getDeadCount();
				t.number+= MyUtils.strToInt(x.getNumber());
				t.parityDay+=x.getDay();
			}
			else{
				x.setClosedTyep("流产");
			}
		});
	}
	
	/**
	 * 一、将list进行分页
	 * 二、将list封装为page
	 * @param list 
	 * 			数据集合
	 * @param page
	 * 			第几页
	 * @return page分页数据
	 * */
	private <T> Page<T> listToPage(List<T> list,Integer page){
		Long total = (long) list.size();
		return new PageImpl<T>(list.stream()
				.skip(page*GlobalConfig.PAGE_SIZE)
				.limit(GlobalConfig.PAGE_SIZE)
				.collect(Collectors.toList()),pageable(page),total);
	}
}

class parityT{
	/**总胎次数*/
    public int sum=0;
    /**总产羔数*/
    public int bornTimes=0;
    /**总活羔数*/
    public int aliveCount=0;
    /**总畸形数*/
    public int badCount=0;
    /**总死胎数*/
    public int deadCount=0;
    /**总61月龄成活数*/
    public int number=0;
    /**产羔母羊数*/
    public int lambingDamNum=0;
    /**总胎次天数*/
    public int parityDay=0;
    
    /**单只母羊产羔数*/
    public String damBornAverage;
    /**单只母羊产活羔数*/
    public String damAliveAverage;
    /**每胎产羔数*/
    public String parityBornAverage;
    /**每胎成活数*/
    public String parityAliveAverage;
    /**总61月龄成活率*/
    public String survivalAverage;
    /**平均胎次天数*/
    public String parityAverage;
    
    public parityT count(){
    	System.out.println("SUM:"+sum);
    	this.damBornAverage=MyUtils.average(bornTimes,lambingDamNum);
    	this.damAliveAverage=MyUtils.average(aliveCount,lambingDamNum);
    	this.parityBornAverage=MyUtils.average(bornTimes,sum);
    	
    	this.parityAliveAverage=MyUtils.average(aliveCount,sum);
    	
    	this.survivalAverage=MyUtils.average(number,aliveCount);
    	this.parityAverage=MyUtils.average(parityDay, sum);
    	return this;
    }
}


