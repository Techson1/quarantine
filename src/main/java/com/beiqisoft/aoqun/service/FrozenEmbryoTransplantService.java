package com.beiqisoft.aoqun.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.FrozenEmbryoTransplant;
import com.beiqisoft.aoqun.repository.FrozenEmbryoTransplantRepository;

public interface FrozenEmbryoTransplantService extends BaseService<FrozenEmbryoTransplant, FrozenEmbryoTransplantRepository>{
	/**
	 * 分页获取用户对象
	 * @param frozenEmbryoTransplant 查询条件
	 * @return
	 */
	Page<FrozenEmbryoTransplant> find(FrozenEmbryoTransplant frozenEmbryoTransplant);
	
	Page<FrozenEmbryoTransplant> find(FrozenEmbryoTransplant frozenEmbryoTransplant, int pageNum);

	/**
	 * 耳号添加校验
	 * */
	Message codeVerify(Long id, String code);

	/**
	 * 删除校验
	 * */
	Message delVerify(Long id);
	
	/**
	 * 删除
	 * */
	Message delete(FrozenEmbryoTransplant f);

	/**
	 * 修改页面校验
	 * */
	Message updateUiVerify(Long id);

	/**
	 * 添加
	 * */
	Message add(String sheetCode, Integer transNum, Date date,
			Long frozenEmbryoId, Long projectId);

	/**
	 * 绑定耳号
	 * */
	Message code(Long id, String code);

	/**
	 * 冻胚修改校验
	 * */
	Message updateVerify(Long id, String sheetCode, Integer transNum,
			Date date, String code);

	/**
	 * 冻胚移植修改
	 * */
	Message update(Long id, String sheetCode, Integer transNum, Date date,
			String code);
}
