package com.bowlong.lang;

public class MathRandom {
	public static final double nextDouble() {
		return Math.random();
	}

	public static final float nextFloat() {
		return (float) Math.random();
	}

	public static final int nextInt() {
		return (int) (nextDouble() * Integer.MAX_VALUE)
				- (Integer.MAX_VALUE >> 1);
	}

	public static final int nextInt(int max) {
		return (int) (nextDouble() * max);
	}

	public static final int nextInt(int min, int max) {
		return (int) (nextDouble() * (max - min)) + min;
	}

	public static final boolean nextBoolean() {
		return nextDouble() * 2 > 1;
	}
}
