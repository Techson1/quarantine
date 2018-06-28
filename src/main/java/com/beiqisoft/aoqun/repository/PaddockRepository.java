package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.OnHand;
import com.beiqisoft.aoqun.entity.Paddock;

public interface PaddockRepository extends BaseRepository<Paddock>{

	Paddock findByName(String paddockName);
	
	@Query(value = "SELECT new OnHand(b.paddock,count(*)) "
			+ "FROM BaseInfo b "
			+ "WHERE b.physiologyStatus=1 AND b.flag='0' "
			+ "GROUP BY b.paddock,b.org.id")
	List<OnHand> findByGroupBy();
	
	@Query(value = "SELECT new OnHand(b.paddock,count(*)) "
			+ "FROM BaseInfo b "
			+ "WHERE b.physiologyStatus=1 AND b.flag='0' "
			+ "AND b.org.id = ?1 "
			+ "GROUP BY b.paddock ORDER BY b.paddock.name ASC")
	List<OnHand> findByGroupByAndOrg(Long orgId);
	
	@Query(value = "SELECT new Paddock(p.name,p.flag) FROM Paddock p")
	List<Paddock> findByTest();

	/**可用查询*/
	List<Paddock> findByFlag(String publicTrue);
	
	/**可用查询*/
	List<Paddock> findByFlagAndOrg_id(String flag,Long orgId);
	
}
