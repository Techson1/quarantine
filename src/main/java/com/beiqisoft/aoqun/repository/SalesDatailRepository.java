package com.beiqisoft.aoqun.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.SalesDatail;

public interface SalesDatailRepository extends BaseRepository<SalesDatail>{

	/**
	 * 根据销售单及耳号查询
	 * */
	SalesDatail findBySales_idAndItem_id(Long salesId, Long id);

	/**
	 * 根据销售单
	 * */
	SalesDatail findByItem_id(Long itemId);
	
	/**
	 * 根据销售单号查询羊只
	 */
	List<SalesDatail> findBySales_id(Long salesId);
	/**
	 * 查询有多少只羊已确认
	 * @param salesId
	 * @param check_status 
	 * @return
	 */
	List<SalesDatail> findBySalesIdAndCheckStatus(Long salesId,String check_status);

	@Query("select count(*) from SalesDatail s where s.item.org.id = ?1 And s.item.sex=?2 And s.item.birthDay between ?3 And ?4")
	Integer findSaleNumByItem_org_idAndItem_SexAndItem_birthDayBetween(Long id, String sex , Date startDate, Date monthOver);
	
}
