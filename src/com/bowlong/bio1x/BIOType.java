package com.bowlong.bio1x;

import java.nio.charset.Charset;

public interface BIOType {
	public static final Charset _UTF8 = Charset.forName("UTF-8");

	public static final byte BEGIN = -127;
	public static final byte END = -128;

	public static final byte FALSE = -1;
	public static final byte NULL = 0;
	public static final byte TRUE = 1;
	public static final byte BYTE_0 = 2;
	public static final byte BYTE = 3;
	public static final byte SHORT_0 = 4;
	public static final byte SHORT = 5;
	public static final byte INT_0 = 6;
	public static final byte INT = 7;
	public static final byte LONG_0 = 8;
	public static final byte LONG = 9;
	public static final byte FLOAT_0 = 10;
	public static final byte FLOAT = 11;
	public static final byte DOUBLE_0 = 12;
	public static final byte DOUBLE = 13;
	public static final byte MAP_0 = 14;
	public static final byte MAP = 15;
	public static final byte LIST_0 = 16;
	public static final byte LIST = 17;
	public static final byte DATE = 18;
	public static final byte BYTES_0 = 19;
	public static final byte BYTES = 20;
	public static final byte UTF8_0 = 21;
	public static final byte UTF8 = 22;
	public static final byte OBJ = 23;
	// public static final byte REF = 24;

	public static final String STR_BEGIN = "BEGIN - " + BEGIN;
	public static final String STR_END = "END - " + END;
	public static final String STR_FALSE = "FALSE - " + FALSE;
	public static final String STR_NULL = "NULL - " + NULL;
	public static final String STR_TRUE = "TRUE - " + TRUE;
	public static final String STR_BYTE = "BYTE - " + BYTE;
	public static final String STR_SHORT = "SHORT - " + SHORT;
	public static final String STR_INT = "INT - " + INT;
	public static final String STR_LONG = "LONG - " + LONG;
	public static final String STR_FLOAT = "FLOAT - " + FLOAT;
	public static final String STR_DOUBLE = "DOUBLE - " + DOUBLE;
	public static final String STR_MAP = "MAP - " + MAP;
	public static final String STR_LIST = "LIST - " + LIST;
	public static final String STR_DATE = "DATE - " + DATE;
	public static final String STR_BYTES = "BYTES - " + BYTES;
	public static final String STR_UTF8 = "UTF8 - " + UTF8;
	public static final String STR_OBJ = "OBJ - " + OBJ;
	// public static final String STR_REF = "REF - " + REF;

}
