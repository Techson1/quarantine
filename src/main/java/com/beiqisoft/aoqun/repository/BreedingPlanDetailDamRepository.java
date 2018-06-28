package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.BreedingPlanDetailDam;

public interface BreedingPlanDetailDamRepository extends BaseRepository<BreedingPlanDetailDam>{
	
	List<BreedingPlanDetailDam> findByBreedingPlan_id(Long breedingPlan);

	/**
	 * 防止历史数据出现问题
	 * */
	List<BreedingPlanDetailDam> findByDam_idAndBreedingPlan_flag(Long id, String publicFalse);
	@Query("select count(*) from BreedingPlanDetailDam d where d.breedingPlan.id=?1")
	Integer findByBreedingPlan_idCount(Long id);
}
