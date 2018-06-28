package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.GeneralVeternary;
import com.beiqisoft.aoqun.repository.GeneralVeternaryRepository;

public interface GeneralVeternaryService extends BaseService<GeneralVeternary, GeneralVeternaryRepository>{
	/**
	 * 分页获取用户对象
	 * @param generalVeternary 查询条件
	 * @return
	 */
	Page<GeneralVeternary> find(GeneralVeternary generalVeternary);
	
	Page<GeneralVeternary> find(GeneralVeternary generalVeternary, int pageNum);

	/**
	 * 添加
	 * */
	GeneralVeternary add(GeneralVeternary generalVeternary, String earTag);

	/**
	 * 修改校验
	 * */
	Message updateVerify(Long id);

	/**
	 * 添加校验
	 * */
	Message addVerify(String earTag);
	
}
