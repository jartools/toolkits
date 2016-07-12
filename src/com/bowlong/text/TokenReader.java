package com.bowlong.text;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.bowlong.lang.NumEx;

public class TokenReader {

	protected final StringTokenizer st;

	public TokenReader(final String str, final String delim) {
		this.st = new StringTokenizer(str, delim);
	}

	public boolean hashNext() {
		return this.st.hasMoreTokens();
	}

	public String nextToken() {
		return this.st.nextToken();
	}

	public int nextInt() {
		return NumEx.stringToInt(nextToken());
	}

	public long nextLong() {
		return NumEx.stringToLong(nextToken());
	}

	public float nextFloat() {
		return NumEx.stringToFloat(nextToken());
	}

	public double nextDouble() {
		return NumEx.stringToDouble(nextToken());
	}

	public List<String> tokens() {
		List<String> r2 = new ArrayList<String>();
		while (hashNext()) {
			r2.add(nextToken());
		}
		return r2;
	}

	public List<Integer> ints() {
		List<Integer> r2 = new ArrayList<Integer>();
		while (hashNext()) {
			r2.add(nextInt());
		}
		return r2;
	}

	public static void main(String[] args) {
		TokenReader tr = new TokenReader("this,1,game.,123.123", ",");
		System.out.println(tr.nextToken());
		System.out.println(tr.nextInt());
		System.out.println(tr.nextToken());
		System.out.println(tr.nextDouble());
//		while (tr.hashNext()) {
//			System.out.println(tr.nextToken());
//		}
	}
}
