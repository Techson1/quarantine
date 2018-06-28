package com.beiqisoft.aoqun.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.BaseGroupDetail;
import com.beiqisoft.aoqun.repository.BaseGroupDetailRepository;

public interface BaseGroupDetailService extends BaseService<BaseGroupDetail, BaseGroupDetailRepository>{
	/**
	 * 分页获取用户对象
	 * @param baseGroupDetail 查询条件
	 * @return
	 */
	Page<BaseGroupDetail> find(BaseGroupDetail baseGroupDetail);
	
	Page<BaseGroupDetail> find(BaseGroupDetail baseGroupDetail, int pageNum);

	/**
	 * 不分页查询
	 * */
	List<BaseGroupDetail> findByList(BaseGroupDetail baseGroupDetail);
	
}
