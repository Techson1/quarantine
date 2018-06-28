package com.beiqisoft.aoqun.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.BreedingOnHand;
import com.beiqisoft.aoqun.repository.BreedingOnHandRepository;

public interface BreedingOnHandService extends BaseService<BreedingOnHand, BreedingOnHandRepository>{
	/**
	 * 分页获取用户对象
	 * @param breedingOnHand 查询条件
	 * @return
	 */
	Page<BreedingOnHand> find(BreedingOnHand breedingOnHand);
	
	Page<BreedingOnHand> find(BreedingOnHand breedingOnHand, int pageNum);

	List<BreedingOnHand> findByList(BreedingOnHand breedingOnHand);
	
}
