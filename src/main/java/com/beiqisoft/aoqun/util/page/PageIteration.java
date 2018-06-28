package com.beiqisoft.aoqun.util.page;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.springframework.data.domain.Page;

public interface PageIteration<T> extends Iterable<T> {
	
	public PageIteration<T> pageAcquire(Page<T> page);
	
	public Page<T> getPage();
	
	public List<T> getList();
	
	public void next();
	
	public T getEntity();
	
	public PageIteration<T> listAcquire(List<T> list);
	
	default Page<T> iteration(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
        return getPage();
    }
	
	default List<T> iterationList(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
        return getList();
    }
}
