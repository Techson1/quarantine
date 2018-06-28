package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Weight;
import com.beiqisoft.aoqun.repository.WeightRepository;

public interface WeightService extends BaseService<Weight, WeightRepository>{
	/**
	 * 分页获取用户对象
	 * @param wigth 查询条件
	 * @return
	 */
	Page<Weight> find(Weight wigth);
	
	Page<Weight> find(Weight wigth, int pageNum);

	/**
	 * 添加校验
	 * */
	Message addVerify(Weight wigth, String earTag,String type);

	/**
	 * 刷新日增重
	 * */
	void refresh(BaseInfo base);

	/**
	 * 出生重添加
	 * */
	void addBirthWeight(BaseInfo baseInfo);
	
}
