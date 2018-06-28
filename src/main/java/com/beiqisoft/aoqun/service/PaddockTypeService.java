package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.PaddockType;
import com.beiqisoft.aoqun.repository.PaddockTypeRepository;

/**@deprecated*/
public interface PaddockTypeService extends BaseService<PaddockType, PaddockTypeRepository>{
	/**
	 * 分页获取用户对象
	 * @param paddockType 查询条件
	 * @return
	 */
	Page<PaddockType> find(PaddockType paddockType);
	
	Page<PaddockType> find(PaddockType paddockType, int pageNum);
	
	/**
	 * 保存并返回Message
	 * @param paddockType paddockType
	 * @return Message
	 * */
	Message saveReturnMessage(PaddockType paddockType);
	
}
