package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Sterilize;
import com.beiqisoft.aoqun.repository.SterilizeRepository;

public interface SterilizeService extends BaseService<Sterilize, SterilizeRepository>{
	/**
	 * 分页获取用户对象
	 * @param sterilize 查询条件
	 * @return
	 */
	Page<Sterilize> find(Sterilize sterilize);
	
	Page<Sterilize> find(Sterilize sterilize, int pageNum);
	
}
