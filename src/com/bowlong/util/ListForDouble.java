package com.bowlong.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import com.bowlong.lang.NumEx;
import com.bowlong.lang.StrEx;
import com.bowlong.objpool.StringBufPool;

public class ListForDouble implements List<Double> {
	protected List<Double> list = new ArrayList<>();

	public ListForDouble() {
	}

	// 1.0,2.0,3.1,4.2,5.3,6.4
	public ListForDouble(String str) {
		if (StrEx.isEmpty(str))
			return;

		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			String token = st.nextToken(",");
			double val = NumEx.stringToDouble(token);
			list.add(val);
		}
	}

	public static final ListForDouble create(String str) {
		if (str.startsWith("[")) // 检查是否可能是json
			return createJson(str);

		return new ListForDouble(str);
	}

	public static final ListForDouble createJson(String json) {
		if (json.length() > 2)
			json = json.substring(1, json.length() - 1);
		return new ListForDouble(json);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<Double> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean add(Double e) {
		return list.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Double> c) {
		return list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Double> c) {
		return list.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public Double get(int index) {
		return list.get(index);
	}

	@Override
	public Double set(int index, Double element) {
		return list.set(index, element);
	}

	@Override
	public void add(int index, Double element) {
		list.add(index, element);
	}

	@Override
	public Double remove(int index) {
		return list.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<Double> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<Double> listIterator(int index) {
		return list.listIterator(index);
	}

	@Override
	public List<Double> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	@Override
	public String toString() {
		StringBuffer sb = StringBufPool.borrowObject();
		try {
			return str(sb).toString();
		} finally {
			StringBufPool.returnObject(sb);
		}
	}

	public String toJSON() {
		StringBuffer sb = StringBufPool.borrowObject();
		try {
			sb.append("[");
			str(sb).toString();
			sb.append("]");
			return sb.toString();
		} finally {
			StringBufPool.returnObject(sb);
		}
	}

	public StringBuffer str(StringBuffer sb) {
		for (Double val : list) {
			sb.append(val).append(",");
		}
		if (sb.length() > 1)
			sb.deleteCharAt(sb.length() - 1);

		return sb;
	}

	public static void main(String[] args) {
		List<Double> il = new ListForDouble("1.0,2.0,3.1,4.2,5.3,6.4");
		System.out.println(il.size() + " : " + il.toString());

		List<Double> i2 = new ListForDouble(il.toString());
		System.out.println(i2.size() + " : " + i2.toString());
	}

}
