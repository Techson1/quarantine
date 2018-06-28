package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.ImmuneHealth;
import com.beiqisoft.aoqun.repository.ImmuneHealthRepository;

public interface ImmuneHealthService extends BaseService<ImmuneHealth, ImmuneHealthRepository>{
	/**
	 * 分页获取用户对象
	 * @param immuneHealth 查询条件
	 * @return
	 */
	Page<ImmuneHealth> find(ImmuneHealth immuneHealth);
	
	Page<ImmuneHealth> find(ImmuneHealth immuneHealth, int pageNum);
	
}
