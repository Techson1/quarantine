package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.repository.ParityRepository;

public interface ParityService extends BaseService<Parity, ParityRepository>{
	/**
	 * 分页获取用户对象
	 * @param parity 查询条件
	 * @return
	 */
	Page<Parity> find(Parity parity);
	
	Page<Parity> find(Parity parity, int pageNum);

	/**
	 * 初次添加羊只胎次
	 * 		问题:初始化胎次,开始日期应该是什么日期？需要传输过来吗？
	 * */
	Message add(BaseInfo dam);
	
	/**
	 *添加胎次 
	 * */
	Message add(BaseInfo dam,Parity parity);
	
	Message addHistory(BaseInfo dam);
	
}
