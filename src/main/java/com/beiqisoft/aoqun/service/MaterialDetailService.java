package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.MaterialDetail;
import com.beiqisoft.aoqun.repository.MaterialDetailRepository;

public interface MaterialDetailService extends BaseService<MaterialDetail, MaterialDetailRepository>{
	/**
	 * 分页获取用户对象
	 * @param materialDetail 查询条件
	 * @return
	 */
	Page<MaterialDetail> find(MaterialDetail materialDetail);
	
	Page<MaterialDetail> find(MaterialDetail materialDetail, int pageNum);

	/**
	 * 校验原料
	 * */
	Message addVerify(MaterialDetail materialDetail);
	
}
