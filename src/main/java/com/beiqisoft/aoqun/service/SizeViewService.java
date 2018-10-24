package com.beiqisoft.aoqun.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.SizeView;
import com.beiqisoft.aoqun.repository.SizeViewRepository;

public interface SizeViewService extends BaseService<SizeView, SizeViewRepository>{
	/**
	 * 分页获取用户对象
	 * @param sizeView 查询条件
	 * @return
	 */
	Page<SizeView> find(SizeView sizeView);
	
	Page<SizeView> find(SizeView sizeView, int pageNum);
	List<SizeView> findAll(SizeView sizeView);
	/**
	 * 体尺添加
	 * */
	SizeView add(SizeView sizeView, String code);

	/**校验*/
	Message verify(String earTag,Long orgId);
}
