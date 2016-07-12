package com.bowlong.lang;

public class ANSI {

	public static final char[] DIGHT = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9' };

	public static final char[] LOWER = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z' };

	public static final char[] UPPER = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z' };

	public static final boolean withIn(final char[] cs, final char c) {
		for (char ch : cs) {
			if (c == ch)
				return true;
		}
		return false;

	}

	public static final boolean isDigit(final char c) {
		return withIn(DIGHT, c);
	}

	public static final boolean isLowder(final char c) {
		return withIn(LOWER, c);
	}

	public static final boolean isUpper(final char c) {
		return withIn(UPPER, c);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(DIGHT.length);
		System.out.println(LOWER.length);
		System.out.println(UPPER.length);
		System.out.println('z' - 'a');

		System.out.println('Z' - 'A');
	}

}
