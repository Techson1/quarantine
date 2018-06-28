package com.beiqisoft.aoqun.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.MonthRankOnHand;
import com.beiqisoft.aoqun.repository.MonthRankOnHandRepository;

public interface MonthRankOnHandService extends BaseService<MonthRankOnHand, MonthRankOnHandRepository>{
	/**
	 * 分页获取用户对象
	 * @param monthRankOnHand 查询条件
	 * @return
	 */
	Page<MonthRankOnHand> find(MonthRankOnHand monthRankOnHand);
	
	Page<MonthRankOnHand> find(MonthRankOnHand monthRankOnHand, int pageNum);
	
	List<MonthRankOnHand> findList(MonthRankOnHand monthRankOnHand);
	
}
