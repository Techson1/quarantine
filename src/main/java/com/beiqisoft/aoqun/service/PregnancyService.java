package com.beiqisoft.aoqun.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Pregnancy;
import com.beiqisoft.aoqun.repository.PregnancyRepository;

public interface PregnancyService extends BaseService<Pregnancy, PregnancyRepository>{
	
	/**
	 * 分页获取用户对象
	 * @param pregnancy 查询条件
	 * @return
	 */
	Page<Pregnancy> find(Pregnancy pregnancy);
	
	Page<Pregnancy> find(Pregnancy pregnancy, int pageNum);
	
	/**
	 * 测孕校验
	 *  一、校验母羊是否存在
	 *  二、校验母羊繁殖状态是否为已配种
	 *  三、校验胎次是否存在
	 *  四、校验配种是存在
	 *  五、校验测试时间是否小于配种时间
	 * */
	Message verify(String damCode,BaseInfo dam,Date pregnancyDate);
	
	/**
	 * 测孕添加
	 * 一、添加测孕
	 * 二、修改胎次
	 * 三、修改母羊
	 * */
	Pregnancy add(BaseInfo findByCodeOrRfid, Pregnancy pregnancy);
	
	/**
	 * 页面修改前校验
	 * */
	Message editUiVerify(Pregnancy pregnancy);

	/**
	 * 测孕校验
	 * 一、校验该数据是否能删除
	 * 二、修改母羊繁殖信息
	 * 三、删除测孕记录
	 * */
	Message delVerify(Long id);
	
	/**
	 * 测孕删除
	 * */
	Message delete(Long id);
}
