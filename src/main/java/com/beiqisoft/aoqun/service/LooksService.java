package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.Looks;
import com.beiqisoft.aoqun.repository.LooksRepository;

public interface LooksService extends BaseService<Looks, LooksRepository>{
	/**
	 * 分页获取用户对象
	 * @param looks 查询条件
	 * @return
	 */
	Page<Looks> find(Looks looks);
	
	Page<Looks> find(Looks looks, int pageNum);

	Message verify(String code,Long orgId);
	
}
