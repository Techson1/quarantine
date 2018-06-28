package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.EarChange;
import com.beiqisoft.aoqun.repository.EarChangeRepository;

public interface EarChangeService extends BaseService<EarChange, EarChangeRepository>{
	/**
	 * 分页获取用户对象
	 * @param earChange 查询条件
	 * @return
	 */
	Page<EarChange> find(EarChange earChange);
	
	Page<EarChange> find(EarChange earChange, int pageNum);

	/**
	 * 新戴标检查
	 * */
	Message newVerify(BaseInfo base, String rfid);

	/**
	 * 新戴标添加
	 * */
	BaseInfo newAdd(BaseInfo base, String rfid);

	/**
	 * 新戴表删除
	 * */
	Message newDel(BaseInfo findByCodeOrRfid, String rfid);

	/**
	 * 补戴标校验
	 * */
	Message changeVerify(BaseInfo base, String rfid);

	/**
	 * 补戴标添加
	 * 一、添加补标记录
	 * 二、修改羊只记录
	 * 三、修改耳标库存电子耳号改为已用
	 * */
	EarChange changeAdd(BaseInfo findByCodeOrRfid, String rfid, String cause,String recorder);
	
}
