package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Abortion;

public interface AbortionRepository extends BaseRepository<Abortion>{

	Abortion findByParity_id(Long id);
}
