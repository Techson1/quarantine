package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.CodePurchaseOrder;
import com.beiqisoft.aoqun.repository.CodePurchaseOrderRepository;

@SuppressWarnings("deprecation")
public interface CodePurchaseOrderService extends BaseService<CodePurchaseOrder, CodePurchaseOrderRepository>{
	/**
	 * 分页获取用户对象
	 * @param codePurchaseOrder 查询条件
	 * @return
	 */
	Page<CodePurchaseOrder> find(CodePurchaseOrder codePurchaseOrder);
	
	Page<CodePurchaseOrder> find(CodePurchaseOrder codePurchaseOrder, int pageNum);
	
}
