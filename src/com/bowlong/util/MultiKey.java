package com.bowlong.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MultiKey<K> implements Serializable {
	private static final long serialVersionUID = 4465448607415788805L;

	private final K[] keys;
	private transient int hashCode;

	@SuppressWarnings("unchecked")
	public MultiKey(final K key1, final K key2) {
		this((K[]) new Object[] { key1, key2 }, false);
	}

	@SuppressWarnings("unchecked")
	public MultiKey(final K key1, final K key2, final K key3) {
		this((K[]) new Object[] { key1, key2, key3 }, false);
	}

	@SuppressWarnings("unchecked")
	public MultiKey(final K key1, final K key2, final K key3, final K key4) {
		this((K[]) new Object[] { key1, key2, key3, key4 }, false);
	}

	@SuppressWarnings("unchecked")
	public MultiKey(final K key1, final K key2, final K key3, final K key4,
			final K key5) {
		this((K[]) new Object[] { key1, key2, key3, key4, key5 }, false);
	}

	public MultiKey(final K[] keys) {
		this(keys, true);
	}

	public MultiKey(final K[] keys, final boolean makeClone) {
		super();
		if (keys == null) {
			throw new IllegalArgumentException(
					"The array of keys must not be null");
		}
		if (makeClone) {
			this.keys = keys.clone();
		} else {
			this.keys = keys;
		}

		calculateHashCode(keys);
	}

	public K[] getKeys() {
		return keys.clone();
	}

	public K getKey(final int index) {
		return keys[index];
	}

	public int size() {
		return keys.length;
	}

	@Override
	public boolean equals(final Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof MultiKey) {
			final MultiKey<?> otherMulti = (MultiKey<?>) other;
			return Arrays.equals(keys, otherMulti.keys);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public String toString() {
		return "MultiKey" + Arrays.toString(keys);
	}

	private void calculateHashCode(final Object[] keys) {
		int total = 0;
		for (final Object key : keys) {
			if (key != null) {
				total ^= key.hashCode();
			}
		}
		hashCode = total;
	}

	private Object readResolve() {
		calculateHashCode(keys);
		return this;
	}
	
	public static void main(String[] args) {
		 Map<MultiKey<Integer>, String> map = new HashMap<>();
		 MultiKey<Integer> multiKey = new MultiKey<>(1, 1);
		 map.put(multiKey, "localizedText x");

		 // later retireve the localized text
		 MultiKey<Integer> multiKey2 = new MultiKey<Integer>(1, 1);
		 String localizedText = (String) map.get(multiKey2);
		 
		 System.out.println(localizedText);
	}
}
