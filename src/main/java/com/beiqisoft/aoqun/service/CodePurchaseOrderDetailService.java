package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.CodePurchaseOrderDetail;
import com.beiqisoft.aoqun.repository.CodePurchaseOrderDetailRepository;

/**
 *  @deprecated
 * */
public interface CodePurchaseOrderDetailService extends BaseService<CodePurchaseOrderDetail, CodePurchaseOrderDetailRepository>{
	/**
	 * 分页获取用户对象
	 * @param codePurchaseOrderDetail 查询条件
	 * @return
	 */
	Page<CodePurchaseOrderDetail> find(CodePurchaseOrderDetail codePurchaseOrderDetail);
	
	Page<CodePurchaseOrderDetail> find(CodePurchaseOrderDetail codePurchaseOrderDetail, int pageNum);
	
}
