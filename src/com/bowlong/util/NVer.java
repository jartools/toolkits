package com.bowlong.util;

import com.bowlong.lang.NumEx;

public class NVer implements Comparable<NVer> {
	public static final int LESS_THAN = -1;
	public static final int EQUAL = 0;
	public static final int GREATER_THAN = 1;

	protected int n1;
	protected int n2;
	protected int n3;
	protected int n4;

	public NVer(final double d) {
		this(String.valueOf(d));
	}

	public NVer(final String str) {
		String[] ss = str.split("\\.");
		if (ss == null || ss.length <= 0)
			return;
		if (ss.length >= 1) {
			n1 = NumEx.stringToInt(ss[0]);
		}
		if (ss.length >= 2) {
			n2 = NumEx.stringToInt(ss[1]);
		}
		if (ss.length >= 3) {
			n3 = NumEx.stringToInt(ss[2]);
		}
		if (ss.length >= 4) {
			n4 = NumEx.stringToInt(ss[3]);
		}
	}

	public NVer(final int v1, final int v2, final int v3, final int v4) {
		this.n1 = v1;
		this.n2 = v2;
		this.n3 = v3;
		this.n4 = v4;
	}

	@Override
	public String toString() {
		return String.format("%d.%d.%d.%d", n1, n2, n3, n4);
	}

	@Override
	public int compareTo(NVer v2) {
		if (n1 > v2.n1)
			return GREATER_THAN;
		if (n2 > v2.n2)
			return GREATER_THAN;
		if (n3 > v2.n3)
			return GREATER_THAN;
		if (n4 > v2.n4)
			return GREATER_THAN;

		if (n1 == v2.n1 && n2 == v2.n2 && n3 == v2.n3 && n4 == v2.n4)
			return EQUAL;

		return LESS_THAN;
	}

	public static String cStr(final int i) {
		switch (i) {
		case GREATER_THAN:
			return "GREATER_THAN";
		case EQUAL:
			return "EQUAL";
		default:
			return "LESS_THAN";
		}
	}

	public static void main(String[] args) {
		NVer o1 = new NVer(123, 23, 45, 55);
		NVer o2 = new NVer(0.145);
		int c = o1.compareTo(o2);
		System.out.println(c);
		System.out.println(o1.toString());
		System.out.println(o2.toString());
		System.out.println(NVer.cStr(c));
	}
}
