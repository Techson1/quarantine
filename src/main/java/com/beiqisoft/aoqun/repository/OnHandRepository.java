package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.OnHand;

public interface OnHandRepository extends BaseRepository<OnHand>{
	
	@Query(value="SELECT sum(d.num) as sum FROM aoquntest.on_hand d inner join t_paddock k\r\n" + 
			"on d.paddock_id=k.id and k.org_id=:orgId where  DATE_FORMAT(d.ctime,'%Y-%m')=:yearAndMonth \r\n" + 
			" group by DATE_FORMAT(d.ctime,'%Y-%m-%d') order by d.ctime ; ",nativeQuery=true)
	public List<Integer> findReportIndex(@Param("orgId")Long orgId,@Param("yearAndMonth")String yearAndMonth);
}
