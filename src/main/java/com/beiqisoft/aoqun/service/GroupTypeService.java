package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.GroupType;
import com.beiqisoft.aoqun.repository.GroupTypeRepository;

/**
 * @deprecated
 * */
public interface GroupTypeService extends BaseService<GroupType, GroupTypeRepository>{
	/**
	 * 分页获取用户对象
	 * @param groupType 查询条件
	 * @return
	 */
	Page<GroupType> find(GroupType groupType);
	
	Page<GroupType> find(GroupType groupType, int pageNum);
	
	/**
	 *  标志校验
	 * 		标志校验,如果用途,品种,性别,标志存在则返回false,否则返回true;
	 * @param purpose
	 * 			用途
	 * @param sex
	 * 			性别
	 * @param breed.id
	 * 			品种
	 * @param sign
	 * 			标志
	 * @return boolean
	 * 			存在:false
	 * 			不存在:true
	 * */
	boolean SignVerify(GroupType groupType);
	
	
	/**
	 * 根据品种id及性别查询定级定价表
	 * @param breedId
	 * 			品种id
	 * @param sex
	 * 			性别
	 * return Page<GroupTYpe>
	 * */
	Page<GroupType> findByGroup(String breedId,String sex);
	
}
