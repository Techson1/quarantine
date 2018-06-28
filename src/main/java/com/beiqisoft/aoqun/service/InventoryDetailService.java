package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.InventoryDetail;
import com.beiqisoft.aoqun.repository.InventoryDetailRepository;

public interface InventoryDetailService extends BaseService<InventoryDetail, InventoryDetailRepository>{
	/**
	 * 分页获取用户对象
	 * @param inventoryDetail 查询条件
	 * @return
	 */
	Page<InventoryDetail> find(InventoryDetail inventoryDetail);
	
	Page<InventoryDetail> find(InventoryDetail inventoryDetail, int pageNum);

	/**
	 * 盘点校验
	 * 一、根据耳号查找羊只id
	 * 二、判断羊只
	 * 		1.判断羊只是否存在
	 * 	 	2.判读羊只是否为本场羊只
	 * 		3.判断羊只状态是否正常
	 * 		4.判断羊只圈舍与所在圈舍是否相同(预留条件)
	 * 		5.判读羊只是否已盘点
	 * */
	Message verify(String code,Long paddockId,Long orgId);

	/**
	 * 盘点添加
	 * 一、添加到盘点明细中
	 * 二、判读羊只圈舍与盘点羊只圈舍是否相同,不相同则添加一条转栏记录
	 * */
	InventoryDetail add(String code, Long paddockId,Long inventoryId,String recorder);
	
}
