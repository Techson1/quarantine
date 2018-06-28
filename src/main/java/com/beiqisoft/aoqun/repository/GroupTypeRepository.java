package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.GroupType;

/**
 * @deprecated
 * */
public interface GroupTypeRepository extends BaseRepository<GroupType>{
	
	/**
	 * 根据用途、品种、性别、标志查询表
	 * */
	GroupType findByPurposeAndSexAndBreed_idAndSign(String purpose,String sex,Long breedId,String sign);
}
