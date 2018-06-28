package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.BreedingPlanDetailSire;

public interface BreedingPlanDetailSireRepository extends BaseRepository<BreedingPlanDetailSire>{
	
	List<BreedingPlanDetailSire> findByBreedingPlan_id(Long breedingPlan);
	
	/**
	 * 根据羊只id及选配id查找
	 * */
	BreedingPlanDetailSire findBySire_idAndBreedingPlan_id(Long sireId,Long breedingPlan);

	/**
	 * 根据选配id及公羊类型选择
	 * */
	BreedingPlanDetailSire findByBreedingPlan_idAndSireType(Long id, String sireType);
}
