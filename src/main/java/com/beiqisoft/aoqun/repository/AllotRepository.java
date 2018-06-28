package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Allot;

public interface AllotRepository extends BaseRepository<Allot>{

	List<Allot> findByToOrg_idAndFlag(Long orgId, String flag);

	List<Allot> findByFromOrg_idAndFlag(Long orgId, String publicTrue);

}
