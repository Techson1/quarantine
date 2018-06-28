package com.beiqisoft.aoqun.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.Weaning;
import com.beiqisoft.aoqun.repository.WeaningRepository;

public interface WeaningService extends BaseService<Weaning, WeaningRepository>{
	/**
	 * 分页获取用户对象
	 * @param weaning 查询条件
	 * @return
	 */
	Page<Weaning> find(Weaning weaning);
	
	Page<Weaning> find(Weaning weaning, int pageNum);

	/**
	 * 校验
	 * */
	Message addVerify(String code, Date weaningDate);

	/**
	 * 添加
	 * */
	Weaning add(String code, Weaning weaning);

	/**
	 * 删除校验
	 * */
	Message delVerify(Long id);

	/**
	 * 删除
	 * */
	Message delete(Long id);

	/**
	 * 断奶界面修改校验
	 * */
	Message updateUiVerify(Long id);

	/**
	 * 断奶修改校验
	 * */
	Message updateVerify(Long id, Date date);

	/**
	 * 断奶修改
	 * */
	Message update(Weaning weaning);
	
}
