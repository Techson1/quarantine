package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.OnHandMonth;
import com.beiqisoft.aoqun.repository.OnHandMonthRepository;

public interface OnHandMonthService extends BaseService<OnHandMonth, OnHandMonthRepository>{
	/**
	 * 分页获取用户对象
	 * @param onHandMonth 查询条件
	 * @return
	 */
	Page<OnHandMonth> find(OnHandMonth onHandMonth);
	
	Page<OnHandMonth> find(OnHandMonth onHandMonth, int pageNum);
	
}
