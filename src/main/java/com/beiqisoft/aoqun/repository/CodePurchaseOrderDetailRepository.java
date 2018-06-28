package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.CodePurchaseOrderDetail;

/**
 *  @deprecated
 * */
public interface CodePurchaseOrderDetailRepository extends BaseRepository<CodePurchaseOrderDetail>{
	
	/**
	 *根据耳标单id分页查询耳标单明细
	 *@param id
	 *			id
	 *@param pageable
	 *			分页列表
	 *@return Page<CodePurchaseOrderDetail>
	 * 
	 **/
	//Page<CodePurchaseOrderDetail> findByCodePurchaseOrder_id(Long id);
	Page<CodePurchaseOrderDetail> findByCodePurchaseOrder_id(Long id,Pageable pageable);
	
	/**
	 * 根据开始耳标、结束耳标、前缀查询电子耳标号
	 * @param prefix
	 * 			前缀
	 * @param startCode
	 * 			开始耳标
	 * @param endCode
	 * 			结束耳标
	 * @return List<CodePurchaseOrderDetail>
	 * 
	 * */
	List<CodePurchaseOrderDetail> findByPrefixAndCodeGreaterThanAndCodeLessThan(String prefix,int startCode,int endCode);
	
	/**
	 * 根据开始耳标、结束耳标、查询可视耳标号
	 * * @param startCode
	 * 			开始耳标
	 * @param endCode
	 * 			结束耳标
	 * @return List<CodePurchaseOrderDetail>
	 * */
	List<CodePurchaseOrderDetail> findByCodeGreaterThanAndCodeLessThan(Long startCode,Long endCode);
}
