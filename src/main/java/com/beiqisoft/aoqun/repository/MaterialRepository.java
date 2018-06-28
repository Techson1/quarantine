package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Material;

public interface MaterialRepository extends BaseRepository<Material>{

	Material findByMaterialNameAndOrg_id(String materialName,Long orgId);

	/**
	 * 查询可用字段下的type
	 * */
	List<Material> findByIsUsedAndType(String isUsed, String materialBlend);

	/**
	 * 查询可用原料
	 * */
	List<Material> findByIsUsed(String publicTrue);
}
