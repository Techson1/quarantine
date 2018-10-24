package com.beiqisoft.aoqun.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.DeathDisposal;
import com.beiqisoft.aoqun.entity.rep.DelRep;
import com.beiqisoft.aoqun.repository.DeathDisposalRepository;

public interface DeathdisposalService extends BaseService<DeathDisposal, DeathDisposalRepository>{
	/**
	 * 分页获取用户对象
	 * @param deathdisposal 查询条件
	 * @return
	 */
	Page<DeathDisposal> find(DeathDisposal deathdisposal);
	
	/**
	 * 获取用户对象
	 * @param deathdisposal 查询条件
	 * @return
	 */
	List<DeathDisposal> findAll(DeathDisposal deathdisposal);
	Page<DeathDisposal> find(DeathDisposal deathdisposal, int pageNum);

	/**
	 * 添加
	 * */
	DeathDisposal add(DeathDisposal deathdisposal, String earTag);

	/**
	 * 删除
	 * */
	Message delete(Long id);

	/**
	 * 修改
	 * */
	Message update(DeathDisposal deathdisposal);

	/**
	 * 修改校验
	 * */
	Message updateAndDelVerify(Long id);

	/**
	 * 统计
	 * */
	List<DeathDisposal> deathForm(String type, Date startDate, Date endDate);
	
	/**
	 * 死亡统计
	 * */
	List<DelRep> delRepList(String type, Date startDate, Date endDate , Long OrgId);
}
