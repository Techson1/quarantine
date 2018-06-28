package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.GroupChange;

public interface GroupChangeRepository extends BaseRepository<GroupChange>{

	List<GroupChange> findByBase_code(String earTag);
}
