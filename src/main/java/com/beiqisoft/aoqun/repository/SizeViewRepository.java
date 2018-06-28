package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.SizeView;

public interface SizeViewRepository extends BaseRepository<SizeView>{
	
	SizeView findByBase_id(Long id);
}
