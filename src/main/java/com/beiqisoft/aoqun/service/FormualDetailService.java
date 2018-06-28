package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.FormualDetail;
import com.beiqisoft.aoqun.repository.FormualDetailRepository;

public interface FormualDetailService extends BaseService<FormualDetail, FormualDetailRepository>{
	/**
	 * 分页获取用户对象
	 * @param formualDetail 查询条件
	 * @return
	 */
	Page<FormualDetail> find(FormualDetail formualDetail);
	
	Page<FormualDetail> find(FormualDetail formualDetail, int pageNum);

	/**
	 * 明细添加校验
	 */
	Message addVerify(FormualDetail formualDetail);
	
}
