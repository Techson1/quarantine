package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.CodeRegister;

public interface CodeRegisterRepository extends BaseRepository<CodeRegister>{
	
	/**
	 * 根据开始耳标、结束耳标、前缀查询可视耳标号
	 * @param prefix
	 * 			前缀
	 * @param startCode
	 * 			开始耳标
	 * @param endCode
	 * 			结束耳标
	 * @return List<CodePurchaseOrderDetail>
	 * 
	 * */
	//List<CodeRegister> findByPrefixAndCodeGreaterThanEqualAndCodeLessThanEqual(String prefix,Integer startCode,Integer endCode);
	
	/**
	 * 根据可视耳号集查询可视耳号
	 * @param String[] code
	 * */
	List<CodeRegister> findByCodeIn(String[] code);
	
	/**
	 * 根据开始耳标、结束耳标、查询电子耳标号
	 * @param visualStartCode
	 * 			开始耳标
	 * @param visualEndCode
	 * 			结束耳标
	 * @return List<CodePurchaseOrderDetail>
	 * */
	List<CodeRegister> findByVisualCodeGreaterThanEqualAndVisualCodeLessThanEqual(Long visualStartCode,Long visualEndCode);

	/**
	 * 根据可视耳标查询
	 * @param code
	 * 			可视耳标
	 * @return CodeRegister
	 * */
	CodeRegister findByCode(String code);
	
	/**
	 * 根据电子耳标查询
	 * @param visualCode
	 * 			电子耳标
	 * @return CodeRegister
	 * */
	CodeRegister findByVisualCode(Long visualCode);

	
	/**
	 * 根据出生状态和耳标类型查询耳号及未用耳标查询耳标
	 * @param state
	 * 			出生状态
	 * @param type
	 * 			耳标类型
	 * @param useState
	 * 			使用状态
	 * @param sex
	 * 			性别
	 * @param orgId
	 * 			分厂id
	 * @return List<CodeRegister>
	 */
	 List<CodeRegister> findByStateAndTypeAndUseStateAndSexAndOrg_idOrderByCode(String state,String type,String useState,String sex,Long orgId);
	 
   /**
	* 根据出生状态和耳标类型查询耳号及未用耳标查询耳标
	* @param state
	* 			出生状态
	* @param type
	* 			耳标类型
	* @param useState
	* 			使用状态
	* @param breedId
	* @return List<CodeRegister>
	*/
	List<CodeRegister> findByStateAndTypeAndUseStateAndBreed_idAndSexAndOrg_idOrderByCode(String state,String type,String useState,Long breedId,String sex,Long orgId);

}
