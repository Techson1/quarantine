package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.RamTraining;
import com.beiqisoft.aoqun.repository.RamTrainingRepository;

public interface RamTrainingService extends BaseService<RamTraining, RamTrainingRepository>{
	/**
	 * 分页获取用户对象
	 * @param ramTraining 查询条件
	 * @return
	 */
	Page<RamTraining> find(RamTraining ramTraining);
	
	Page<RamTraining> find(RamTraining ramTraining, int pageNum);

	/**
	 * 添加修改校验
	 * */
	Message saveVerify(String earTag);
	
}
