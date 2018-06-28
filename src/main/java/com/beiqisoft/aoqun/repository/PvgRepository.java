package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Pvg;

public interface PvgRepository  extends BaseRepository<Pvg>{
	List<Pvg> findByParentIsNull();
	List<Pvg> findByParentIsNotNull();
	List<Pvg> findByType(int type);
/*	@Query("select pvg from Pvg pvg where and pvg.type!=?1")
	List<Pvg> findParents(int type);*/
	List<Pvg> findByIdIn(Long[] ids);
	
	List<Pvg> findByNameIn(String[] pvgs);
	
}
