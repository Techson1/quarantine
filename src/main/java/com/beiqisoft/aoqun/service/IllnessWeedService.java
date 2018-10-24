package com.beiqisoft.aoqun.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.IllnessWeed;
import com.beiqisoft.aoqun.repository.IllnessWeedRepository;

public interface IllnessWeedService extends BaseService<IllnessWeed, IllnessWeedRepository>{
	/**
	 * 分页获取用户对象
	 * @param illnessWeed 查询条件
	 * @return
	 */
	Page<IllnessWeed> find(IllnessWeed illnessWeed);
	
	/**
	 * 导出数据结果集
	 * @param illnessWeed
	 * @return
	 */
	List<IllnessWeed> findAll(IllnessWeed illnessWeed);
	Page<IllnessWeed> find(IllnessWeed illnessWeed, int pageNum);

	/**
	 * 疾病淘汰添加
	 * */
	IllnessWeed add(IllnessWeed illnessWeed, String earTag);

	Message delete(Long id);

	Message update(IllnessWeed illnessWeed);

	/**
	 * 修改删除校验
	 * */
	Message updateAndDelVerify(Long id);
	
}
