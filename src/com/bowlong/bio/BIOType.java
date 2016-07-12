package com.bowlong.bio;

import java.nio.charset.Charset;

public interface BIOType {
	public static final Charset _UTF8 = Charset.forName("UTF-8");
	
	public static final byte BEGIN = -127;
	public static final byte END = -128;

	public static final byte FALSE = -1;
	public static final byte NULL = 0;
	public static final byte TRUE = 1;
	public static final byte BYTE = 2;
	public static final byte SHORT = 3;
	public static final byte INT = 4;
	public static final byte LONG = 5;
	public static final byte FLOAT = 6;
	public static final byte DOUBLE = 7;
	public static final byte MAP = 8;
	public static final byte LIST = 9;
	public static final byte DATE = 10;
	public static final byte BYTES = 11;
	public static final byte UTF8 = 12;
	public static final byte OBJ = 13;
	public static final byte REF = 14;
	
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
	public static final String STR_REF = "REF - " + REF;
	
	
}
