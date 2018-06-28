package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.ImmunePlan;
import com.beiqisoft.aoqun.repository.ImmunePlanRepository;

public interface ImmunePlanService extends BaseService<ImmunePlan, ImmunePlanRepository>{
	/**
	 * 分页获取用户对象
	 * @param immunePlan 查询条件
	 * @return
	 */
	Page<ImmunePlan> find(ImmunePlan immunePlan);
	
	Page<ImmunePlan> find(ImmunePlan immunePlan, int pageNum);
	
}
