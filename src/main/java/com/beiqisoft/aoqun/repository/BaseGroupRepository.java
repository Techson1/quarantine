package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.BaseGroup;

public interface BaseGroupRepository extends BaseRepository<BaseGroup>{

	BaseGroup findByName(String name);
}
