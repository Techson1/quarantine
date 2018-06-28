package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Lambing;

/**
 * @deprecated
 * */
public interface LambingRepository extends BaseRepository<Lambing>{

	List<Lambing> findByLambingDam_id(Long id);
}
