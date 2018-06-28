package com.beiqisoft.aoqun.util.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class PageRewrite<T> {
	
	/**数据*/
	private List<T> content;
	
	private boolean last;
	private long totalElements;
	private int totalPages;
	private int number;
	private int size;
	private int first;
	private int numberOfElements;
	
	public PageRewrite(Page<T> page){
		this.last=page.isLast();
		this.totalElements=page.getTotalElements();
		this.totalPages=page.getTotalPages();
		this.number=page.getNumber();
		this.size=page.getSize();
		this.numberOfElements=page.getNumberOfElements();
		listRewrite(page);
	}
	
	private void listRewrite(Page<T> page){
		content = new ArrayList<>();
		for (T t:page){
			content.add(t);
		}
	}
}
