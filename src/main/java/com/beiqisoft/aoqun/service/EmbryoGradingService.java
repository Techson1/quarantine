package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.EmbryoGrading;
import com.beiqisoft.aoqun.repository.EmbryoGradingRepository;

public interface EmbryoGradingService extends BaseService<EmbryoGrading, EmbryoGradingRepository>{
	/**
	 * 分页获取用户对象
	 * @param embryoGrading 查询条件
	 * @return
	 */
	Page<EmbryoGrading> find(EmbryoGrading embryoGrading);
	
	Page<EmbryoGrading> find(EmbryoGrading embryoGrading, int pageNum);
	
}
