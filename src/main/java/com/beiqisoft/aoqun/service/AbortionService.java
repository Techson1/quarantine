package com.beiqisoft.aoqun.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.Abortion;
import com.beiqisoft.aoqun.repository.AbortionRepository;

public interface AbortionService extends BaseService<Abortion, AbortionRepository>{
	/**
	 * 分页获取用户对象
	 * @param abortion 查询条件
	 * @return
	 */
	Page<Abortion> find(Abortion abortion);
	
	Page<Abortion> find(Abortion abortion, int pageNum);

	/**
	 * 流产添加前校验
	 * */
	Message addVerify(String code, Date abortionDate);

	/**
	 * 流产添加
	 * */
	Abortion add(String code, Abortion abortion);

	/**
	 * 流产删除校验
	 * */
	Message delVerify(Long id);
	
	/**
	 * 流产删除
	 * */
	Message delete(Abortion abortion);

	/**
	 * 流产界面修改校验
	 * */
	Message updateUiVerify(Long id);

	/**
	 * 流产修改校验
	 * */
	Message updateVerify(Long id, Date data);

	/**
	 * 流产修改
	 * */
	Message update(Abortion abortion);
	
}
