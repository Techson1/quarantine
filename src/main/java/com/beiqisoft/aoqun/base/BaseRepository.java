package com.beiqisoft.aoqun.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beiqisoft.aoqun.entity.Paddock;

public interface BaseRepository<T> extends PagingAndSortingRepository<T, Long>,JpaSpecificationExecutor<T> {
	List<T> findByOrderByIdDesc();
	
	

}
