package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.Fake;
import com.beiqisoft.aoqun.repository.FakeRepository;

public interface FakeService extends BaseService<Fake, FakeRepository>{
	/**
	 * 分页获取用户对象
	 * @param fake 查询条件
	 * @return
	 */
	Page<Fake> find(Fake fake);
	
	Page<Fake> find(Fake fake, int pageNum);

	/**
	 * 添加校验
	 * */
	Message addVerify(String earTag, String sex);
}
