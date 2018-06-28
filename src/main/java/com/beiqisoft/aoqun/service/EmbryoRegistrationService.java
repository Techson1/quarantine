package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.EmbryoRegistration;
import com.beiqisoft.aoqun.repository.EmbryoRegistrationRepository;

public interface EmbryoRegistrationService extends BaseService<EmbryoRegistration, EmbryoRegistrationRepository>{
	/**
	 * 分页获取用户对象
	 * @param embryoRegistration 查询条件
	 * @return
	 */
	Page<EmbryoRegistration> find(EmbryoRegistration embryoRegistration);
	
	Page<EmbryoRegistration> find(EmbryoRegistration embryoRegistration, int pageNum);

	/**冻胚编码添加*/
	String add(EmbryoRegistration embryoRegistration, int i);
	
	/**冻胚编码查询*/
	Message findByOne(Long breedId);
	
}
