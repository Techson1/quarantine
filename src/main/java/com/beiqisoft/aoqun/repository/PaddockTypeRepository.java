package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.PaddockType;

/**@deprecated*/
public interface PaddockTypeRepository extends BaseRepository<PaddockType>{
	
	 PaddockType findByPaddockTypeNameAndOrg_id(String name,Long orgId);
}
