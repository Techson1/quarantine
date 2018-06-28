package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Allot;
import com.beiqisoft.aoqun.repository.AllotRepository;

public interface AllotService extends BaseService<Allot, AllotRepository>{
	/**
	 * 分页获取用户对象
	 * @param allot 查询条件
	 * @return
	 */
	Page<Allot> find(Allot allot);
	
	Page<Allot> find(Allot allot, int pageNum);
	
}
