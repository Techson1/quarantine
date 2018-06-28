package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.AllotDetail;
import com.beiqisoft.aoqun.repository.AllotDetailRepository;

public interface AllotDetailService extends BaseService<AllotDetail, AllotDetailRepository>{
	/**
	 * 分页获取用户对象
	 * @param allotDetail 查询条件
	 * @return
	 */
	Page<AllotDetail> find(AllotDetail allotDetail);
	
	Page<AllotDetail> find(AllotDetail allotDetail, int pageNum);

	/**
	 * 添加校验
	 * */
	Message addVerify(String code,Long orgId,Long allotId);

	/**
	 * 添加
	 * */
	AllotDetail add(String code, Long orgId, Long id,String recorder);

	
	/**
	 * 审核
	 * */
	AllotDetail audit(String flag, Long orgId,Long paddockId,String operator,
			AllotDetail allotDetail);

	/**
	 * 明细删除
	 * */
	Message delete(Long id);
	
}
