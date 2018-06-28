package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.BreedingPlan;

public interface BreedingPlanRepository extends BaseRepository<BreedingPlan>{

	List<BreedingPlan> findByFlag(String flag);
	
	
}
