package com.beiqisoft.aoqun.service;

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
	
}
