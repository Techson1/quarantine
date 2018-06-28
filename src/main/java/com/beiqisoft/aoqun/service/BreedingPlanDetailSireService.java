package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.BreedingPlanDetailSire;
import com.beiqisoft.aoqun.repository.BreedingPlanDetailSireRepository;

public interface BreedingPlanDetailSireService extends BaseService<BreedingPlanDetailSire, BreedingPlanDetailSireRepository>{
	/**
	 * 分页获取用户对象
	 * @param breedingPlanDetailSire 查询条件
	 * @return
	 */
	Page<BreedingPlanDetailSire> find(BreedingPlanDetailSire breedingPlanDetailSire);
	
	Page<BreedingPlanDetailSire> find(BreedingPlanDetailSire breedingPlanDetailSire, int pageNum);
	
}
