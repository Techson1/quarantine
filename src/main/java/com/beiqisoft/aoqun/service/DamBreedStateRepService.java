package com.beiqisoft.aoqun.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.rep.DamBreedStateRep;
import com.beiqisoft.aoqun.repository.DamBreedStateRepRepository;

public interface DamBreedStateRepService extends BaseService<DamBreedStateRep, DamBreedStateRepRepository>{
	/**
	 * 分页获取用户对象
	 * @param damBreedStateRep 查询条件
	 * @return
	 */
	Page<DamBreedStateRep> find(DamBreedStateRep damBreedStateRep);
	
	Page<DamBreedStateRep> find(DamBreedStateRep damBreedStateRep, int pageNum);

	List<DamBreedStateRep> statistics();
}
