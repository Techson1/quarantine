package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.BreedingPlan;
import com.beiqisoft.aoqun.entity.BreedingPlanDetailSire;
import com.beiqisoft.aoqun.service.BreedingPlanService;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 选配方案访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time  2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "breedingPlan")
public class BreedingPlanController extends BaseController<BreedingPlan,BreedingPlanService> {
	
	@JSON(type=BreedingPlan.class,filter="breedingPlanDetailDams,breedingPlanDetailSires,org")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=BaseInfo.class,include="code")
	@JSON(type=BreedingPlanDetailSire.class,include="sire,sireCode")
	@RequestMapping(value ="list")
    public Page<BreedingPlan> list(BreedingPlan breedingPlan) throws InterruptedException{
		breedingPlan.setLastModifyTime(null);
		startTime();
		Page<BreedingPlan> breedingPlanPage= page.pageAcquire(breedingPlanService.find(breedingPlan)).iteration(x ->{
			x.setBreedingDamCount(breedingPlanDetailDamService.getRepository().findByBreedingPlan_idCount(x.getId())+"");
			x.isRequest();
		});
		endTime();
		return breedingPlanPage;
    }
	
	@RequestMapping(value="findOne/{id}")
	public BreedingPlan findByOne(@PathVariable Long id){
		BreedingPlan breedingPlan = breedingPlanService.getRepository().findOne(id);
		return breedingPlan;
	}
	
	/**
	 * 测试方法
	 * */
	@RequestMapping(value ="testList")
	public Page<BreedingPlan> testList(BreedingPlan breedingPlan) throws InterruptedException{
		return breedingPlanService.Specialfind(breedingPlan);
	}
	
	/**
	 * 修改是否存档
	 * */
	@RequestMapping(value ="updateFlag/{id}/{flag}")
	public Message updateFlag(@PathVariable Long id,@PathVariable String flag){
		breedingPlanService.getRepository().save(breedingPlanService.getRepository().findOne(id).setFlagReturnThis(flag));
		return SUCCESS;
	}
	
	/**
	 * 可用列表查询
	 * */
	@JSON(type=BreedingPlan.class,include="id,name")
	@RequestMapping(value="flagList")
	public List<BreedingPlan> flagList(){
		return breedingPlanService.getRepository().findByFlag(SystemM.PUBLIC_TRUE);
	}
	
	/**
	 * 查询
	 * */
	@JSON(type=BaseInfo.class,include="code,breed,birthDay,recorder")
	@JSON(type=BreedingPlan.class,filter="org")
	@JSON(type=Breed.class,include="id,breedName")
	@RequestMapping(value="planOne/{id}")
	public BreedingPlan planOne(@PathVariable Long id) {
		return breedingPlanService.getRepository().findOne(id).isRequest();
	}
}
