package com.bowlong.lang;

import com.bowlong.lang.NumEx;

public class PseudoRandom {
	protected final long seed;
	protected final double[] seeds;
	protected int ptr = 0;

	public PseudoRandom(long seed) {
		this.seed = (NumEx.LONG_MAX_VALUE - seed) * (1 - (seed % 100000));

		String str = String.valueOf(this.seed);
		int digit = str.length();

		this.seeds = new double[digit];
		for (int i = 0; i < digit - 3; i++) {
			String s2 = str.substring(i, i + 3);
			this.seeds[i] = 1 - (NumEx.stringToDouble(s2) / 999);
		}
	}

	public double next() {
		if (ptr++ >= seeds.length - 1) {
			ptr = 0;
		}
		return seeds[ptr];
	}

	public int nextInt() {
		return (int) (NumEx.INT_MAX_VALUE * next());
	}

	public double nextDouble() {
		return (double) (NumEx.LONG_MAX_VALUE * next());
	}

	public int nextInt(int min, int max) {
		int v = (int) (min + ((max - min) * next()));
		return v > max ? v - max : v;
	}

	public static void main(String[] args) {
		PseudoRandom fr = new PseudoRandom(1234567890);
		for (int i = 0; i < 100; i++) {
			System.out.println(i + " : " + fr.nextInt(-100, 100));
		}
	}
}
