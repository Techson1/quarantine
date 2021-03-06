package com.beiqisoft.aoqun.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.SalesDatail;
import com.beiqisoft.aoqun.repository.SalesDatailRepository;

public interface SalesDatailService extends BaseService<SalesDatail, SalesDatailRepository>{
	/**
	 * 分页获取用户对象
	 * @param salesDatail 查询条件
	 * @return
	 */
	Page<SalesDatail> find(SalesDatail salesDatail);
	
	Page<SalesDatail> find(SalesDatail salesDatail, int pageNum);

	/**
	 * 添加
	 * */
	void add(SalesDatail salesDatail);
     
	/**
	 * 羊只明细添加2.o
	 * @param earTag
	 * @param salesDatail
	 */
	void  addDetail(String earTag,SalesDatail salesDatail);

	/**
	 * 查询
	 * */
	List<SalesDatail> findList(SalesDatail salesDatail);

	/**
	 * 校验
	 * */
	Message addVerify(SalesDatail salesDatail, String earTag);
	
	/**
	 * 校验2.0 增加场区校验
	 * @param salesDatail
	 * @param earTag
	 * @param orgid
	 * @return
	 */
	Message addVerifyOrgid(SalesDatail salesDatail, String earTag,String orgid);

	/**
	 * 删除
	 * */
	Message delete(Long[] ids);

	/**
	 * 查找
	 * */
	Page<SalesDatail> inventory(SalesDatail salesDatail);

	/**
	 * 审核
	 * */
	void audit(Long id,String flag);
	
}
