package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Looks;

public interface LooksRepository extends BaseRepository<Looks>{
	
	Looks findByBase_id(Long id);
}
