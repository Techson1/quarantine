package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Fake;

public interface FakeRepository extends BaseRepository<Fake>{

	Fake findByBase_id(Long id);
}
