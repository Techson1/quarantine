package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.CodePurchaseOrder;
import com.beiqisoft.aoqun.service.CodePurchaseOrderService;

/**
 * 耳标订购单访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 * @deprecated
 */
@RestController
@RequestMapping(value = "codePurchaseOrder")
public class CodePurchaseOrderController extends BaseController<CodePurchaseOrder,CodePurchaseOrderService> {
	@RequestMapping(value ="list")
    public Page<CodePurchaseOrder> list(CodePurchaseOrder codePurchaseOrder) throws InterruptedException{
		return codePurchaseOrderService.find(codePurchaseOrder);
    }
}