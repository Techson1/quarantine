package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.DeathDisposalReason;

public interface DeathDisposalReasonRepository extends BaseRepository<DeathDisposalReason>{
	
	/**
	 * 根据父id查询疾病类型表
	 * @param Parent
	 * 			父id
	 * @return List<DeathDisposalReason>
	 * */
	List<DeathDisposalReason> findByParent_id(Long Parent);
	
	/**
	 * 根据疾病名称查询疾病
	 * @param name
	 * 			疾病名称
	 * @return DeathDisposalReason
	 * */
	DeathDisposalReason findByName(String name);
	
	/**
	 * 根据疾病名称查询疾病
	 * @param name
	 * 			疾病名称
	 * @return DeathDisposalReason
	 * */
	DeathDisposalReason findByNameAndType(String name,String type);
	
	/**
	 * 查找父疾病列表
	 * 
	 * @param type
	 * 			疾病类型
	 * 				淘汰:2
	 * 				育种:3
	 * 				死亡:4
	 * @return List<DeathDisposalReason>
	 * */
	List<DeathDisposalReason> findByParentIsNullAndType(String type);
	
	/**
	 * 查找可用父疾病列表
	 * 
	 * @param type
	 * 			疾病类型
	 * 				淘汰:2
	 * 				育种:3
	 * 				死亡:4
	 * @param flage
	 * 			是否可用
	 * 				可用:1
	 * 				存档:0
	 * @return List<DeathDisposalReason>
	 * */
	List<DeathDisposalReason> findByParentIsNullAndFlagAndType(String flag,String Type);
	
	/**
	 * 测试类
	 * */
	Page<DeathDisposalReason> findByParentIsNullAndFlagAndType(String flag,String Type,Pageable pageable);
	
	/**
	 * 可用查询
	 * */
	List<DeathDisposalReason> findByAndFlag(String flag);

	/**
	 * 测试类
	 * */
	Page<DeathDisposalReason> findByParentIsNullAndType(String Type,Pageable pageable);
}
