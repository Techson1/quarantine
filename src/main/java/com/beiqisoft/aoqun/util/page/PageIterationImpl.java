package com.beiqisoft.aoqun.util.page;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class PageIterationImpl<T> implements PageIteration<T>{

	private Page<T> page;
	private Iterator<T> iterator;
	
	private T t;
	
	@Override
	public PageIteration<T> pageAcquire(Page<T> page) {
		this.page=page;
		this.iterator=page.iterator();
		return this;
	}

	@Override
	public Page<T> getPage() {
		return this.page;
	}

	@Override
	public Iterator<T> iterator() {
		return this.iterator;
	}

	@Override
	public void next() {
		this.t=this.iterator.next();
	}

	@Override
	public T getEntity() {
		return this.t;
	}

	@Override
	public PageIteration<T> listAcquire(List<T> list) {
		this.page=new PageImpl<T>(list);
		return this;
	}

	@Override
	public List<T> getList() {
		return this.page.getContent();
	}
}
