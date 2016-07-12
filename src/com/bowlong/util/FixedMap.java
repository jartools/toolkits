package com.bowlong.util;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({ "serial", "unused" })
public class FixedMap<K, V> extends HashMap<K, V> {

	private FixedMap() {
		super();
	}

	private FixedMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	private FixedMap(int initialCapacity) {
		super(initialCapacity);
	}

	public FixedMap(Map<? extends K, ? extends V> m) {
		super(m);
	}

	public static <K, V> FixedMap<K, V> create(Map<? extends K, ? extends V> m) {
		return new FixedMap<K, V>(m);
	}

	@Override
	public V put(K key, V value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V get(Object key) {
		V v = super.get(key);
		return v;
	}

}
