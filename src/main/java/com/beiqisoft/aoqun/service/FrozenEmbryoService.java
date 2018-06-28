package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.FrozenEmbryo;
import com.beiqisoft.aoqun.repository.FrozenEmbryoRepository;

public interface FrozenEmbryoService extends BaseService<FrozenEmbryo, FrozenEmbryoRepository>{
	/**
	 * 分页获取用户对象
	 * @param frozenEmbryo 查询条件
	 * @return
	 */
	Page<FrozenEmbryo> find(FrozenEmbryo frozenEmbryo);
	
	Page<FrozenEmbryo> find(FrozenEmbryo frozenEmbryo, int pageNum);
	
	/**
	 * 添加
	 * */
	Message add(FrozenEmbryo frozenEmbryo);

	/**
	 * 删除校验
	 * */
	Message delVerify(Long id);
	
	/**
	 * 删除
	 * */
	Message delete(FrozenEmbryo frozenEmbryo);

	/**
	 * 修改校验
	 * */
	Message updateVerify(FrozenEmbryo frozenEmbryo);

	/**
	 * 修改
	 * */
	Message update(FrozenEmbryo frozenEmbryo);
}
