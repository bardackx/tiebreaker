package com.bardackx.tiebreaker;

import java.util.List;

public class TiebreakerEntry<E> {

	private E element;
	private List<?> values;

	TiebreakerEntry(E element, List<?> list) {
		this.element = element;
		this.values = list;
	}

	public E getElement() {
		return element;
	}

	public Object getValue(int i) {
		return values.get(i);
	}

}
