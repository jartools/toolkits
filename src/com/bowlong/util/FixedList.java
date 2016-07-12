package com.bowlong.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings({ "serial", "unused", "unchecked" })
public class FixedList<E> extends ArrayList<E> {

	protected Object[] values;

	private FixedList() {
		super();
	}

	private FixedList(int size) {
		super();
	}

	public FixedList(Collection<? extends E> c) {
		super(c);

		int count = c.size();
		values = new Object[count];

		int p = 0;
		for (E e : c) {
			values[p++] = e;
		}
	}

	public FixedList(E... c) {
		super(Arrays.asList(c));

		int count = c.length;
		values = new Object[count];

		int p = 0;
		for (E e : c) {
			values[p++] = e;
		}
	}

	public static <E> FixedList<E> create(Collection<? extends E> c) {
		return new FixedList<E>(c);
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E get(int index) {
		// E v = super.get(index);
		E v = (E) values[index];
		return v;
	}

}
