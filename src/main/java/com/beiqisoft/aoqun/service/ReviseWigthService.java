package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.ReviseWigth;
import com.beiqisoft.aoqun.repository.ReviseWigthRepository;

public interface ReviseWigthService extends BaseService<ReviseWigth, ReviseWigthRepository>{
	/**
	 * 分页获取用户对象
	 * @param reviseWigth 查询条件
	 * @return
	 */
	Page<ReviseWigth> find(ReviseWigth reviseWigth);
	
	Page<ReviseWigth> find(ReviseWigth reviseWigth, int pageNum);

	void refresh(ReviseWigth reviseWigth);

	/**
	 * 添加或修改矫正数据
	 * */
	void save(BaseInfo base);
	
}
