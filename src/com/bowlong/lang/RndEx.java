package com.bowlong.lang;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.bowlong.objpool.StringBufPool;

public final class RndEx {
	// private static final Random rnd = new Random(System.currentTimeMillis());
	private static final Random rnd = new Random(RndEx.randomNum());

	public static final int randomNum() {
		return randomUUID().hashCode();
	}

	public static final String randomUUID() {
		return UUID.randomUUID().toString();
	}

	public static final boolean nextBoolean() {
		return rnd.nextBoolean();
	}

	public static final byte[] nextBytes(final int len) {
		byte[] bytes = new byte[len];
		rnd.nextBytes(bytes);
		return bytes;
	}

	public static final double nextDouble() {
		return rnd.nextDouble();
	}

	public static final double nextGaussian() {
		return rnd.nextGaussian();
	}

	public static final double nextFloat() {
		return rnd.nextFloat();
	}

	public static final int nextInt() {
		return rnd.nextInt();
	}

	public static final int nextInt(final int n) {
		return rnd.nextInt(n);
	}

	public static final int nextInt(final int f, final int t) {
		return rnd.nextInt(t - f) + f;
	}

	public static final long nextLong() {
		return rnd.nextLong();
	}

	public static final int nextInt(final int[] args) {
		int i = nextInt(args.length);
		return args[i];
	}

	public static final int nextInt(final List<Integer> args) {
		int i = nextInt(args.size());
		return args.get(i);
	}

	public static final int nextInt2(final int... args) {
		int i = nextInt(args.length);
		return args[i];
	}

	public static final byte[] nextBytes(byte[] b) {
		if (b == null)
			return null;
		rnd.nextBytes(b);
		return b;
	}

	public static final String nextString(int num) {
		if (num <= 0)
			return "";
		StringBuffer sb = StringBufPool.borrowObject();
		try {
			for (int i = 0; i < num; i++) {
				int ch = RndEx.nextInt(33, 127);
				sb.append((char) ch);
			}
			return sb.toString();
		} finally {
			StringBufPool.returnObject(sb);
		}
	}

	/*** 从给定的字符里面随机得一个字符 **/
	static public final String nextString(final String org) {
		if (StrEx.isEmpty(org))
			return "";
		int len = org.length();
		int rnd = nextInt(len);
		return org.substring(rnd, rnd + 1);
	}

	/*** 从给定的字符里面随机得字符 **/
	static public final String nextString(final String org, int rndLen) {
		if (rndLen <= 0 || StrEx.isEmpty(org))
			return "";
		StringBuffer buff = StringBufPool.borrowObject();
		try {
			for (int i = 0; i < rndLen; i++) {
				buff.append(nextString(org));
			}
			return buff.toString();
		} catch (Exception e) {
			return "";
		} finally {
			StringBufPool.returnObject(buff);
		}
	}
}
