package com.beiqisoft.aoqun.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Sales;
import com.beiqisoft.aoqun.entity.rep.SalesRep;

public interface SalesRepository extends BaseRepository<Sales>{

	/**
	 * 根据是否可用查询
	 * */
	List<Sales> findByCheckFlag(String publicFalse);
	/**
	 * 根据分厂id获得销售单数据 
	 * @param orgId
	 * @return List<Sales>
	 */
    List<Sales> findByOrg_idAndCheckFlag(Long orgId,String publicFalse);
	/**
	 * 根据名称查询
	 * */
	Sales findByCode(String code);
	
	/**
	 * 根据名称及分厂查询
	 * */
	Sales findByCodeAndOrg_id(String code,Long orgId);

	@Query(value="SELECT new com.beiqisoft.aoqun.entity.rep.SalesRep(s.item.breed,s.sales.date,count(*),sum(s.price)) "
			+ "FROM SalesDatail s WHERE s.sales.date>=?1 AND s.sales.date<=?2 GROUP BY s.item.breed,s.sales.date")
	List<SalesRep> findByRep(Date startDate,Date endDate);

	@Query(value="SELECT MAX(s.code) FROM t_sales s WHERE s.ctime LIKE ?1 AND s.org_id=?2",nativeQuery=true)
	String findByDate(String year,Long OrgId);
	
	List<Sales> findByCustomerId(Long customerId);
}
