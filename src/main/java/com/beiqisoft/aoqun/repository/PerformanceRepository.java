package com.beiqisoft.aoqun.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Contact;
import com.beiqisoft.aoqun.entity.Performance;

public interface PerformanceRepository extends BaseRepository<Performance>{

	@Query(value="SELECT new Contact(b.paddock.contact,COUNT(b.id)) FROM BaseInfo b "
			+ "WHERE b.physiologyStatus = 1 AND b.paddock.contact.id IS NOT NULL "
			+ "GROUP BY b.paddock.contact.id")
	List<Contact> inventory();

	Performance findByYearAndMonthAndContact_idAndType(Integer year, Integer month, Long id,String type);

	@Query(value="SELECT new Contact(d.base.paddock.contact,COUNT(d.id)) FROM DeathDisposal d "
			+ "WHERE d.base.paddock.contact.id IS NOT NULL AND d.date>=?1 AND d.date<=?2 "
			+ "GROUP BY d.base.paddock.contact.id")
	List<Contact> death(Date stateDate , Date endDate);
	
	@Query(value="SELECT new Contact(d.base.paddock.contact,COUNT(d.id)) FROM IllnessWeed d "
			+ "WHERE d.base.paddock.contact.id IS NOT NULL AND d.date>=?1 AND d.date<=?2 "
			+ "GROUP BY d.base.paddock.contact.id")
	List<Contact> illness(Date stateDate , Date endDate);
	
	@Query(value="SELECT new Contact(d.base.paddock.contact,COUNT(d.id)) FROM BreedingWeed d "
			+ "WHERE d.base.paddock.contact.id IS NOT NULL AND d.date>=?1 AND d.date<=?2 "
			+ "GROUP BY d.base.paddock.contact.id")
	List<Contact> breeding(Date stateDate,Date endDate);
}
