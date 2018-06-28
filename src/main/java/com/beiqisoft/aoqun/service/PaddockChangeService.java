package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.PaddockChange;
import com.beiqisoft.aoqun.repository.PaddockChangeRepository;

public interface PaddockChangeService extends BaseService<PaddockChange, PaddockChangeRepository>{
	/**
	 * 分页获取用户对象
	 * @param paddockChange 查询条件
	 * @return
	 */
	Page<PaddockChange> find(PaddockChange paddockChange);
	
	Page<PaddockChange> find(PaddockChange paddockChange, int pageNum);

	/**
	 * 转圈添加
	 * */
	PaddockChange add(Paddock paddock, String earTag,Organization org,String recorder);

	/**
	 * 批量添加
	 * @param recorder 
	 * */
	void addAll(Paddock fromPaddock, Paddock toPaddock,String recorder, Organization org);

	Message addVerify(String earTag,Long paddockId);
	
}
