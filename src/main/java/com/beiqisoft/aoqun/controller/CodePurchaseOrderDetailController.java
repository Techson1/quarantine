package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.CodePurchaseOrderDetail;
import com.beiqisoft.aoqun.service.CodePurchaseOrderDetailService;

/**
 * 耳标订购单明细访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 * @deprecated
 */
@RestController
@RequestMapping(value = "codePurchaseOrderDetail")
public class CodePurchaseOrderDetailController extends BaseController<CodePurchaseOrderDetail,CodePurchaseOrderDetailService> {
	@RequestMapping(value ="list")
    public Page<CodePurchaseOrderDetail> list(CodePurchaseOrderDetail codePurchaseOrderDetail) throws InterruptedException{
		return codePurchaseOrderDetailService.find(codePurchaseOrderDetail);
    }
	
	/**
	 * 根据耳标单id查询耳标明细
	 *
	 * @deprecated
	 * 
	 * @param id
	 * 			耳标单id
	 * @param pageNum
	 * 			页数
	 * @return Page<CodePurchaseOrderDetail>
	 * */
	@RequestMapping(value ="codePurchaseOrderList")
	public Page<CodePurchaseOrderDetail> codePurchaseOrderList(Long id,Integer pageNum){
		pageNum--;
		return codePurchaseOrderDetailService.getRepository().findByCodePurchaseOrder_id(id, new PageRequest(pageNum, GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	/**
	 * 添加耳标明细
	 * */
	@RequestMapping(value ="addCodes")
	public Message addCodes(String prefix,int startCode,int number,int endCode){
		for (int i=startCode;i<endCode;i++){
			CodePurchaseOrderDetail codePurchaseOrderDetail=new CodePurchaseOrderDetail(prefix,i);
			codePurchaseOrderDetailService.getRepository().save(codePurchaseOrderDetail);
		}
		return SUCCESS;
	}
	
	/**
	 * 校验电子耳标号段
	 * 		开始号段和结束号段中是否存中间数
	 * @param prefix
	 * 			前缀
	 * @param startCode	
	 * 			开始号段
	 * @param endCode
	 * 			结束号段
	 * @return Message
	 * 			如果数据重复则分返回101,并且提示重复数据,否则返回100
	 * */
	@RequestMapping(value ="codeVerify")
	public Message codeVerify(String prefix,int startCode,int endCode){
		List<CodePurchaseOrderDetail> codePurchaseOrderDetails=codePurchaseOrderDetailService
				.getRepository().findByPrefixAndCodeGreaterThanAndCodeLessThan(prefix,startCode,endCode);
		 if (!codePurchaseOrderDetails.isEmpty()){
			 return new Message(ABNORMAL,"数据重复,数据重"+codePurchaseOrderDetails.get(0).getPrefixCode()+"后开始重复");
		 }
		 return SUCCESS;
	}
	
	/**
	 * 校验可视耳标号段
	 * @param startCode	
	 * 			开始号段
	 * @param endCode
	 * 			结束号段
	 * @return Message
	 * 			如果数据重复则分返回101,并且提示重复数据,否则返回100
	 * */
	@RequestMapping(value ="visualCodeVerify")
	public Message visualCodeVerify(Long startCode,Long endCode){
		List<CodePurchaseOrderDetail> codePurchaseOrderDetails=codePurchaseOrderDetailService
				.getRepository().findByCodeGreaterThanAndCodeLessThan(startCode,endCode);
		if (!codePurchaseOrderDetails.isEmpty()){
			 return new Message(ABNORMAL,"数据重复,重复数据"+codePurchaseOrderDetails.get(0).getVisualCode()+"后开始重复");
		}
		return SUCCESS;
	}
	
	
}
