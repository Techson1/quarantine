package com.beiqisoft.aoqun.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.OnHand;
import com.beiqisoft.aoqun.repository.OnHandRepository;

public interface OnHandService extends BaseService<OnHand, OnHandRepository>{
	/**
	 * 分页获取用户对象
	 * @param onHand 查询条件
	 * @return
	 */
	Page<OnHand> find(OnHand onHand);
	
	Page<OnHand> find(OnHand onHand, int pageNum);
	/**
	 * 首页存栏情况统计报表使用
	 * @param orgi
	 * @param yearAndmonth
	 * @return
	 */
	List<Integer> findReportIndex(Long orgId,String yearAndmonth);
}
