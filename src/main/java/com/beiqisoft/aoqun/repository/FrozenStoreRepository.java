package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.FrozenStore;

public interface FrozenStoreRepository extends BaseRepository<FrozenStore>{

	FrozenStore findByName(String name);

	
	/**查询可用罐号*/
	List<FrozenStore> findByFlagAndOrg_id(String flag, Long orgId);
}
