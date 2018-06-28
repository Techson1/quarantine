package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.BaseGroupDetail;

public interface BaseGroupDetailRepository extends BaseRepository<BaseGroupDetail>{

	BaseGroupDetail findByBase_codeAndBaseGroup_id(String code, Long id);
	
	BaseGroupDetail findByBase_rfidAndBaseGroup_id(String code, Long id);
}
