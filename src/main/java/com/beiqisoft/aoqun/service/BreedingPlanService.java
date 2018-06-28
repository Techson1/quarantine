package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.BreedingPlan;
import com.beiqisoft.aoqun.repository.BreedingPlanRepository;

public interface BreedingPlanService extends BaseService<BreedingPlan, BreedingPlanRepository>{
	/**
	 * 分页获取用户对象
	 * @param breedingPlan 查询条件
	 * @return
	 */
	Page<BreedingPlan> find(BreedingPlan breedingPlan);
	
	Page<BreedingPlan> find(BreedingPlan breedingPlan, int pageNum);
	
	/**
	 * 自定义api动态查询
	 * */
	Page<BreedingPlan> Specialfind(BreedingPlan breedingPlan);
}
