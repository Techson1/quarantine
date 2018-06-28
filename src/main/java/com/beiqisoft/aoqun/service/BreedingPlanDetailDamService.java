package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BreedingPlanDetailDam;
import com.beiqisoft.aoqun.repository.BreedingPlanDetailDamRepository;

public interface BreedingPlanDetailDamService extends BaseService<BreedingPlanDetailDam, BreedingPlanDetailDamRepository>{
	/**
	 * 分页获取用户对象
	 * @param breedingPlanDetailDam 查询条件
	 * @return
	 */
	Page<BreedingPlanDetailDam> find(BreedingPlanDetailDam breedingPlanDetailDam);
	
	Page<BreedingPlanDetailDam> find(BreedingPlanDetailDam breedingPlanDetailDam, int pageNum);
	
	/**
	 * 批量添加母羊明细
	 * 	一、添加选配母羊明细
	 * 		1.根据母羊id查询母羊
	 * 		3.添加
	 *  二、修改BaseInfo
	 *  	1.如果BaseInfo表中的breedingState字段为--修改为“空怀” 
	 * */
	Message saves(BreedingPlanDetailDam breedingPlanDetailDam,String[] code);
	
}
