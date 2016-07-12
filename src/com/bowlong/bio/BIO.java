package com.bowlong.bio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bowlong.io.ByteInStream;
import com.bowlong.io.ByteOutStream;
import com.bowlong.lang.ByteEx;
import com.bowlong.lang.NumEx;
import com.bowlong.objpool.ByteInPool;
import com.bowlong.objpool.ByteOutPool;
import com.bowlong.util.Ref;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BIO {

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

	public static final String tag(final int tag) {
		switch (tag) {
		case BEGIN:
			return STR_BEGIN;
		case END:
			return STR_END;
		case FALSE:
			return STR_FALSE;
		case NULL:
			return STR_NULL;
		case TRUE:
			return STR_TRUE;
		case BYTE:
			return STR_BYTE;
		case SHORT:
			return STR_SHORT;
		case INT:
			return STR_INT;
		case LONG:
			return STR_LONG;
		case FLOAT:
			return STR_FLOAT;
		case DOUBLE:
			return STR_DOUBLE;
		case MAP:
			return STR_MAP;
		case LIST:
			return STR_LIST;
		case DATE:
			return STR_DATE;
		case BYTES:
			return STR_BYTES;
		case UTF8:
			return STR_UTF8;
		default:
			return "UNKNOW - " + tag;
		}
	}

	public static final ByteOutStream newStream() {
		return ByteOutPool.borrowObject();
	}

	public static final ByteInStream newStream(byte[] b) {
		return ByteInPool.borrowObject(b);
	}

	public static final ByteBuffer newByteBuffer(int capacity) {
		return ByteBuffer.allocate(capacity);
	}

	public static final ByteBuffer newByteBuffer(byte[] b) {
		return ByteBuffer.wrap(b);
	}

	public static final String HEX(byte[] b) {
		return ByteEx.bytesToString(b);
	}

	// /////////////////////
	private static final int __read(final InputStream input) throws IOException {
		int ch = input.read();
		if (ch < 0)
			throw new EOFException();
		return (byte) (ch);
	}

	private static final int __read(final byte[] input, Ref<Integer> pt)
			throws IOException {
		return input[pt.val++];
	}

	private static final int __read(final ByteBuffer input) throws IOException {
		return input.get();
	}

	private static final byte[] __readFully(final byte[] input,
			Ref<Integer> pt, final byte[] r2) throws IOException {
		int size = r2.length;
		for (int n = 0; n < size; n++) {
			r2[n] = input[pt.val++];
		}
		return r2;
	}

	private static final byte[] __readFully(final InputStream input,
			final byte[] r2) throws IOException {
		return NumEx.readFully(input, r2);
	}

	private static final byte[] __readFully(final ByteBuffer input,
			final byte[] r2) throws IOException {
		input.get(r2);
		return r2;
	}

	private static final int __write(final OutputStream output, final byte v)
			throws IOException {
		output.write(v);
		return 1;
	}

	private static final int __write(final ByteBuffer output, final byte v)
			throws IOException {
		output.put(v);
		return 1;
	}

	private static final int __write(final byte[] output, final byte v,
			final Ref<Integer> pt) throws IOException {
		output[pt.val++] = v;
		return 1;
	}

	// private static final int _writeByte(final OutputStream output, final int
	// v)
	// throws IOException {
	// output.write(v);
	// return 1;
	// }

	private static final int __write(final OutputStream output, final byte[] v)
			throws IOException {
		output.write(v);
		return v.length;
	}

	private static final int __write(final ByteBuffer output, final byte[] v)
			throws IOException {
		output.put(v);
		return v.length;
	}

	private static final int __write(final byte[] output, final byte[] v,
			final Ref<Integer> pt) throws IOException {
		for (byte b : v) {
			output[pt.val++] = b;
		}
		return v.length;
	}

	// /////////////////////
	public static final int writeBegin(final OutputStream output)
			throws IOException {
		return __write(output, BEGIN);
	}

	public static final int writeBegin(final ByteBuffer output)
			throws IOException {
		return __write(output, BEGIN);
	}

	public static final int writeBegin(final byte[] output,
			final Ref<Integer> pt) throws IOException {
		return __write(output, BEGIN, pt);
	}

	public static final int writeEnd(final OutputStream output)
			throws IOException {
		return __write(output, END);
	}

	public static final int writeEnd(final ByteBuffer output)
			throws IOException {
		return __write(output, END);
	}

	public static final int writeEnd(final byte[] output, final Ref<Integer> pt)
			throws IOException {
		return __write(output, END, pt);
	}

	public static final int writeNull(final OutputStream output)
			throws IOException {
		return __write(output, NULL);
	}

	public static final int writeNull(final ByteBuffer output)
			throws IOException {
		return __write(output, NULL);
	}

	public static final int writeNull(final byte[] output, final Ref<Integer> pt)
			throws IOException {
		return __write(output, NULL, pt);
	}

	public static final int writeBool(final OutputStream output, final boolean v)
			throws IOException {
		if (v)
			__write(output, TRUE);
		else
			__write(output, FALSE);
		return 1;
	}

	public static final int writeBool(final ByteBuffer output, final boolean v)
			throws IOException {
		if (v)
			__write(output, TRUE);
		else
			__write(output, FALSE);
		return 1;
	}

	public static final int writeBool(final byte[] output, final boolean v,
			final Ref<Integer> pt) throws IOException {
		if (v)
			__write(output, TRUE, pt);
		else
			__write(output, FALSE, pt);
		return 1;
	}

	public static final int writeByte(final OutputStream output, final byte v)
			throws IOException {
		__write(output, BYTE);
		__write(output, v);
		return 2;
	}

	public static final int writeByte(final ByteBuffer output, final byte v)
			throws IOException {
		__write(output, BYTE);
		__write(output, v);
		return 2;
	}

	public static final int writeByte(final byte[] output, final byte v,
			final Ref<Integer> pt) throws IOException {
		__write(output, BYTE, pt);
		__write(output, v, pt);
		return 2;
	}

	public static final int writeShort(final OutputStream output, final short v)
			throws IOException {
		__write(output, SHORT);
		__write(output, (byte) ((v >> 8) & 0xFF));
		__write(output, (byte) ((v >> 0) & 0xFF));
		return 3;
	}

	public static final int writeShort(final ByteBuffer output, final short v)
			throws IOException {
		__write(output, SHORT);
		__write(output, (byte) ((v >> 8) & 0xFF));
		__write(output, (byte) ((v >> 0) & 0xFF));
		return 3;
	}

	public static final int writeShort(final byte[] output, final short v,
			final Ref<Integer> pt) throws IOException {
		__write(output, SHORT, pt);
		__write(output, (byte) ((v >> 8) & 0xFF), pt);
		__write(output, (byte) ((v >> 0) & 0xFF), pt);
		return 3;
	}

	public static final int writeInt(final OutputStream output, final int v)
			throws IOException {
		__write(output, INT);
		__write(output, (byte) ((v >> 24) & 0xff));
		__write(output, (byte) ((v >> 16) & 0xff));
		__write(output, (byte) ((v >> 8) & 0xff));
		__write(output, (byte) ((v >> 0) & 0xff));
		return 5;
	}

	public static final int writeInt(final ByteBuffer output, final int v)
			throws IOException {
		__write(output, INT);
		__write(output, (byte) ((v >> 24) & 0xff));
		__write(output, (byte) ((v >> 16) & 0xff));
		__write(output, (byte) ((v >> 8) & 0xff));
		__write(output, (byte) ((v >> 0) & 0xff));
		return 5;
	}

	public static final int writeInt(final byte[] output, final int v,
			final Ref<Integer> pt) throws IOException {
		__write(output, INT, pt);
		__write(output, (byte) ((v >> 24) & 0xff), pt);
		__write(output, (byte) ((v >> 16) & 0xff), pt);
		__write(output, (byte) ((v >> 8) & 0xff), pt);
		__write(output, (byte) ((v >> 0) & 0xff), pt);
		return 5;
	}

	private static final int _writeInt(final OutputStream output, final int v)
			throws IOException {
		__write(output, (byte) ((v >> 24) & 0xff));
		__write(output, (byte) ((v >> 16) & 0xff));
		__write(output, (byte) ((v >> 8) & 0xff));
		__write(output, (byte) ((v >> 0) & 0xff));
		return 4;
	}

	private static final int _writeInt(final ByteBuffer output, final int v)
			throws IOException {
		__write(output, (byte) ((v >> 24) & 0xff));
		__write(output, (byte) ((v >> 16) & 0xff));
		__write(output, (byte) ((v >> 8) & 0xff));
		__write(output, (byte) ((v >> 0) & 0xff));
		return 4;
	}

	private static final int _writeInt(final byte[] output, final int v,
			final Ref<Integer> pt) throws IOException {
		__write(output, (byte) ((v >> 24) & 0xff), pt);
		__write(output, (byte) ((v >> 16) & 0xff), pt);
		__write(output, (byte) ((v >> 8) & 0xff), pt);
		__write(output, (byte) ((v >> 0) & 0xff), pt);
		return 4;
	}

	public static final int writeLong(final OutputStream output, final long v)
			throws IOException {
		__write(output, LONG);
		__write(output, (byte) ((v >> 56) & 0xff));
		__write(output, (byte) ((v >> 48) & 0xff));
		__write(output, (byte) ((v >> 40) & 0xff));
		__write(output, (byte) ((v >> 32) & 0xff));
		__write(output, (byte) ((v >> 24) & 0xff));
		__write(output, (byte) ((v >> 16) & 0xff));
		__write(output, (byte) ((v >> 8) & 0xff));
		__write(output, (byte) ((v >> 0) & 0xff));
		return 9;
	}

	public static final int writeLong(final ByteBuffer output, final long v)
			throws IOException {
		__write(output, LONG);
		__write(output, (byte) ((v >> 56) & 0xff));
		__write(output, (byte) ((v >> 48) & 0xff));
		__write(output, (byte) ((v >> 40) & 0xff));
		__write(output, (byte) ((v >> 32) & 0xff));
		__write(output, (byte) ((v >> 24) & 0xff));
		__write(output, (byte) ((v >> 16) & 0xff));
		__write(output, (byte) ((v >> 8) & 0xff));
		__write(output, (byte) ((v >> 0) & 0xff));
		return 9;
	}

	public static final int writeLong(final byte[] output, final long v,
			final Ref<Integer> pt) throws IOException {
		__write(output, LONG, pt);
		__write(output, (byte) ((v >> 56) & 0xff), pt);
		__write(output, (byte) ((v >> 48) & 0xff), pt);
		__write(output, (byte) ((v >> 40) & 0xff), pt);
		__write(output, (byte) ((v >> 32) & 0xff), pt);
		__write(output, (byte) ((v >> 24) & 0xff), pt);
		__write(output, (byte) ((v >> 16) & 0xff), pt);
		__write(output, (byte) ((v >> 8) & 0xff), pt);
		__write(output, (byte) ((v >> 0) & 0xff), pt);
		return 9;
	}

	public static final int writeFloat(final OutputStream output, final float f)
			throws IOException {
		int v = Float.floatToIntBits(f);

		__write(output, FLOAT);
		__write(output, (byte) ((v >> 24) & 0xff));
		__write(output, (byte) ((v >> 16) & 0xff));
		__write(output, (byte) ((v >> 8) & 0xff));
		__write(output, (byte) ((v >> 0) & 0xff));
		return 5;
	}

	public static final int writeFloat(final ByteBuffer output, final float f)
			throws IOException {
		int v = Float.floatToIntBits(f);

		__write(output, FLOAT);
		__write(output, (byte) ((v >> 24) & 0xff));
		__write(output, (byte) ((v >> 16) & 0xff));
		__write(output, (byte) ((v >> 8) & 0xff));
		__write(output, (byte) ((v >> 0) & 0xff));
		return 5;
	}

	public static final int writeFloat(final byte[] output, final float f,
			final Ref<Integer> pt) throws IOException {
		int v = Float.floatToIntBits(f);

		__write(output, FLOAT, pt);
		__write(output, (byte) ((v >> 24) & 0xff), pt);
		__write(output, (byte) ((v >> 16) & 0xff), pt);
		__write(output, (byte) ((v >> 8) & 0xff), pt);
		__write(output, (byte) ((v >> 0) & 0xff), pt);
		return 5;
	}

	public static final int writeDouble(final OutputStream output,
			final double d) throws IOException {
		long v = Double.doubleToLongBits(d);

		__write(output, DOUBLE);
		__write(output, (byte) ((v >> 56) & 0xff));
		__write(output, (byte) ((v >> 48) & 0xff));
		__write(output, (byte) ((v >> 40) & 0xff));
		__write(output, (byte) ((v >> 32) & 0xff));
		__write(output, (byte) ((v >> 24) & 0xff));
		__write(output, (byte) ((v >> 16) & 0xff));
		__write(output, (byte) ((v >> 8) & 0xff));
		__write(output, (byte) ((v >> 0) & 0xff));
		return 9;
	}

	public static final int writeDouble(final ByteBuffer output, final double d)
			throws IOException {
		long v = Double.doubleToLongBits(d);

		__write(output, DOUBLE);
		__write(output, (byte) ((v >> 56) & 0xff));
		__write(output, (byte) ((v >> 48) & 0xff));
		__write(output, (byte) ((v >> 40) & 0xff));
		__write(output, (byte) ((v >> 32) & 0xff));
		__write(output, (byte) ((v >> 24) & 0xff));
		__write(output, (byte) ((v >> 16) & 0xff));
		__write(output, (byte) ((v >> 8) & 0xff));
		__write(output, (byte) ((v >> 0) & 0xff));
		return 9;
	}

	public static final int writeDouble(final byte[] output, final double d,
			final Ref<Integer> pt) throws IOException {
		long v = Double.doubleToLongBits(d);

		__write(output, DOUBLE, pt);
		__write(output, (byte) ((v >> 56) & 0xff), pt);
		__write(output, (byte) ((v >> 48) & 0xff), pt);
		__write(output, (byte) ((v >> 40) & 0xff), pt);
		__write(output, (byte) ((v >> 32) & 0xff), pt);
		__write(output, (byte) ((v >> 24) & 0xff), pt);
		__write(output, (byte) ((v >> 16) & 0xff), pt);
		__write(output, (byte) ((v >> 8) & 0xff), pt);
		__write(output, (byte) ((v >> 0) & 0xff), pt);
		return 9;
	}

	public static final int writeDate(final OutputStream output, final Date d)
			throws IOException {
		if (d == null)
			return writeNull(output);

		long v = d.getTime();

		__write(output, DATE);
		__write(output, (byte) ((v >> 56) & 0xff));
		__write(output, (byte) ((v >> 48) & 0xff));
		__write(output, (byte) ((v >> 40) & 0xff));
		__write(output, (byte) ((v >> 32) & 0xff));
		__write(output, (byte) ((v >> 24) & 0xff));
		__write(output, (byte) ((v >> 16) & 0xff));
		__write(output, (byte) ((v >> 8) & 0xff));
		__write(output, (byte) ((v >> 0) & 0xff));
		return 9;
	}

	public static final int writeDate(final ByteBuffer output, final Date d)
			throws IOException {
		if (d == null)
			return writeNull(output);

		long v = d.getTime();

		__write(output, DATE);
		__write(output, (byte) ((v >> 56) & 0xff));
		__write(output, (byte) ((v >> 48) & 0xff));
		__write(output, (byte) ((v >> 40) & 0xff));
		__write(output, (byte) ((v >> 32) & 0xff));
		__write(output, (byte) ((v >> 24) & 0xff));
		__write(output, (byte) ((v >> 16) & 0xff));
		__write(output, (byte) ((v >> 8) & 0xff));
		__write(output, (byte) ((v >> 0) & 0xff));
		return 9;
	}

	public static final int writeDate(final byte[] output, final Date d,
			final Ref<Integer> pt) throws IOException {
		if (d == null)
			return writeNull(output, pt);

		long v = d.getTime();

		__write(output, DATE, pt);
		__write(output, (byte) ((v >> 56) & 0xff), pt);
		__write(output, (byte) ((v >> 48) & 0xff), pt);
		__write(output, (byte) ((v >> 40) & 0xff), pt);
		__write(output, (byte) ((v >> 32) & 0xff), pt);
		__write(output, (byte) ((v >> 24) & 0xff), pt);
		__write(output, (byte) ((v >> 16) & 0xff), pt);
		__write(output, (byte) ((v >> 8) & 0xff), pt);
		__write(output, (byte) ((v >> 0) & 0xff), pt);
		return 9;
	}

	public static final int writeBytes(final OutputStream output, final byte[] v)
			throws IOException {
		if (v == null)
			return writeNull(output);

		int len = __write(output, BYTES);
		len += _writeInt(output, v.length);
		len += __write(output, v);
		return len;
	}

	public static final int writeBytes(final ByteBuffer output, final byte[] v)
			throws IOException {
		if (v == null)
			return writeNull(output);

		int len = __write(output, BYTES);
		len += _writeInt(output, v.length);
		len += __write(output, v);
		return len;
	}

	public static final int writeBytes(final byte[] output, final byte[] v,
			final Ref<Integer> pt) throws IOException {
		if (v == null)
			return writeNull(output, pt);

		int len = __write(output, BYTES, pt);
		len += _writeInt(output, v.length, pt);
		len += __write(output, v, pt);
		return len;
	}

	public static final int writeUTF8(final OutputStream output, final String s)
			throws IOException {
		if (s == null)
			return writeNull(output);

		byte[] v = s.getBytes(_UTF8);
		int len = __write(output, UTF8);
		len += _writeInt(output, v.length);
		len += __write(output, v);
		return len;
	}

	public static final int writeUTF8(final ByteBuffer output, final String s)
			throws IOException {
		if (s == null)
			return writeNull(output);

		byte[] v = s.getBytes(_UTF8);
		int len = __write(output, UTF8);
		len += _writeInt(output, v.length);
		len += __write(output, v);
		return len;
	}

	public static final int writeUTF8(final byte[] output, final String s,
			final Ref<Integer> pt) throws IOException {
		if (s == null)
			return writeNull(output, pt);

		byte[] v = s.getBytes(_UTF8);
		int len = __write(output, UTF8, pt);
		len += _writeInt(output, v.length, pt);
		len += __write(output, v, pt);
		return len;
	}

	public static final int writeList(final OutputStream output, final List v)
			throws Exception {
		if (v == null)
			return writeNull(output);

		int SIZE = v.size();
		__write(output, LIST); // tag
		int len = _writeInt(output, SIZE); // size
		for (Object object : v) { // datas
			len += writeObject(output, object);
		}
		return len;
	}

	public static final int writeList(final ByteBuffer output, final List v)
			throws IOException {
		if (v == null)
			return writeNull(output);

		int SIZE = v.size();
		__write(output, LIST); // tag
		int len = _writeInt(output, SIZE); // size
		for (Object object : v) { // datas
			len += writeObject(output, object);
		}
		return len;
	}

	public static final int writeList(final byte[] output, final List v,
			final Ref<Integer> pt) throws IOException {
		if (v == null)
			return writeNull(output, pt);

		int SIZE = v.size();
		__write(output, LIST, pt); // tag
		int len = _writeInt(output, SIZE, pt); // size
		for (Object object : v) { // datas
			len += writeObject(output, object, pt);
		}
		return len;
	}

	public static final int writeMap(final OutputStream output,
			final Map<Object, Object> v) throws Exception {
		if (v == null)
			return writeNull(output);

		int SIZE = v.size();
		__write(output, MAP); // tag
		int len = _writeInt(output, SIZE); // size
		Set<Entry<Object, Object>> entrys = v.entrySet();
		for (Entry e : entrys) { // datas
			Object _key = e.getKey();
			Object _val = e.getValue();
			len += writeObject(output, _key);
			len += writeObject(output, _val);
		}
		return len;
	}

	public static final int writeMap(final ByteBuffer output,
			final Map<Object, Object> v) throws IOException {
		if (v == null)
			return writeNull(output);

		int SIZE = v.size();
		__write(output, MAP); // tag
		int len = _writeInt(output, SIZE); // size
		Set<Entry<Object, Object>> entrys = v.entrySet();
		for (Entry e : entrys) { // datas
			Object _key = e.getKey();
			Object _val = e.getValue();
			len += writeObject(output, _key);
			len += writeObject(output, _val);
		}
		return len;
	}

	public static final int writeMap(final byte[] output,
			final Map<Object, Object> v, final Ref<Integer> pt)
			throws IOException {
		if (v == null)
			return writeNull(output, pt);

		int SIZE = v.size();
		__write(output, MAP, pt); // tag
		int len = _writeInt(output, SIZE, pt); // size
		Set<Entry<Object, Object>> entrys = v.entrySet();
		for (Entry e : entrys) { // datas
			Object _key = e.getKey();
			Object _val = e.getValue();
			len += writeObject(output, _key, pt);
			len += writeObject(output, _val, pt);
		}
		return len;
	}

	public static final int _writeObject(final OutputStream output,
			final BIOSerial v) throws Exception {
		if (v == null)
			return writeNull(output);

		__write(output, OBJ); // tag
		String clazz = v.getClass().getName();
		// String clazz = v.getClass().getSimpleName();
		writeUTF8(output, clazz);
		int len = v._writeObject(output);
		return len + 1;
	}

	public static final int writeObject(final OutputStream output,
			final Object v) throws Exception {
		if (v == null) {
			return writeNull(output);
		} else if (v instanceof Boolean) {
			boolean val = (Boolean) v;
			return writeBool(output, val);
		} else if (v instanceof Byte) {
			byte val = (Byte) v;
			return writeByte(output, val);
		} else if (v instanceof Short) {
			short val = (Short) v;
			return writeShort(output, val);
		} else if (v instanceof Integer) {
			int val = (Integer) v;
			return writeInt(output, val);
		} else if (v instanceof Long) {
			long val = (Long) v;
			return writeLong(output, val);
		} else if (v instanceof Float) {
			float val = (Float) v;
			return writeFloat(output, val);
		} else if (v instanceof Double) {
			double val = (Double) v;
			return writeDouble(output, val);
		} else if (v instanceof Date) {
			Date val = (Date) v;
			return writeDate(output, val);
		} else if (v instanceof byte[]) {
			byte[] val = (byte[]) v;
			return writeBytes(output, val);
		} else if (v instanceof String) {
			String val = (String) v;
			return writeUTF8(output, val);
		} else if (v instanceof List) {
			List val = (List) v;
			return writeList(output, val);
		} else if (v instanceof Map) {
			Map val = (Map) v;
			return writeMap(output, val);
		} else if (v instanceof BIOSerial) {
			BIOSerial val = (BIOSerial) v;
			return _writeObject(output, val);
		} else {
			throw new IOException("unsupported object:" + v);
		}
	}

	public static final int writeObject(final ByteBuffer output, final Object v)
			throws IOException {
		if (v == null) {
			return writeNull(output);
		} else if (v instanceof Boolean) {
			boolean val = (Boolean) v;
			return writeBool(output, val);
		} else if (v instanceof Byte) {
			byte val = (Byte) v;
			return writeByte(output, val);
		} else if (v instanceof Short) {
			short val = (Short) v;
			return writeShort(output, val);
		} else if (v instanceof Integer) {
			int val = (Integer) v;
			return writeInt(output, val);
		} else if (v instanceof Long) {
			long val = (Long) v;
			return writeLong(output, val);
		} else if (v instanceof Float) {
			float val = (Float) v;
			return writeFloat(output, val);
		} else if (v instanceof Double) {
			double val = (Double) v;
			return writeDouble(output, val);
		} else if (v instanceof Date) {
			Date val = (Date) v;
			return writeDate(output, val);
		} else if (v instanceof byte[]) {
			byte[] val = (byte[]) v;
			return writeBytes(output, val);
		} else if (v instanceof String) {
			String val = (String) v;
			return writeUTF8(output, val);
		} else if (v instanceof List) {
			List val = (List) v;
			return writeList(output, val);
		} else if (v instanceof Map) {
			Map val = (Map) v;
			return writeMap(output, val);
		} else {
			throw new IOException("unsupported object:" + v);
		}
	}

	public static final int writeObject(final byte[] output, final Object v,
			final Ref<Integer> pt) throws IOException {
		if (v == null) {
			return writeNull(output, pt);
		} else if (v instanceof Boolean) {
			boolean val = (Boolean) v;
			return writeBool(output, val, pt);
		} else if (v instanceof Byte) {
			byte val = (Byte) v;
			return writeByte(output, val, pt);
		} else if (v instanceof Short) {
			short val = (Short) v;
			return writeShort(output, val, pt);
		} else if (v instanceof Integer) {
			int val = (Integer) v;
			return writeInt(output, val, pt);
		} else if (v instanceof Long) {
			long val = (Long) v;
			return writeLong(output, val, pt);
		} else if (v instanceof Float) {
			float val = (Float) v;
			return writeFloat(output, val, pt);
		} else if (v instanceof Double) {
			double val = (Double) v;
			return writeDouble(output, val, pt);
		} else if (v instanceof Date) {
			Date val = (Date) v;
			return writeDate(output, val, pt);
		} else if (v instanceof byte[]) {
			byte[] val = (byte[]) v;
			return writeBytes(output, val, pt);
		} else if (v instanceof String) {
			String val = (String) v;
			return writeUTF8(output, val, pt);
		} else if (v instanceof List) {
			List val = (List) v;
			return writeList(output, val, pt);
		} else if (v instanceof Map) {
			Map val = (Map) v;
			return writeMap(output, val, pt);
		} else {
			throw new IOException("unsupported object:" + v);
		}
	}

	// ////////
	public static final boolean readBool(final InputStream input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag != TRUE && tag != FALSE)
			throw new IOException("tag erro :" + tag(tag));
		return _readBool(tag);
	}

	public static final boolean readBool(final ByteBuffer input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag != TRUE && tag != FALSE)
			throw new IOException("tag erro :" + tag(tag));
		return _readBool(tag);
	}

	public static final boolean readBool(final byte[] input) throws IOException {
		return readBool(input, new Ref<Integer>(0));
	}

	public static final boolean readBool(final byte[] input, Ref<Integer> pt)
			throws IOException {
		byte tag = (byte) __read(input, pt);
		if (tag != TRUE && tag != FALSE)
			throw new IOException("tag erro :" + tag(tag));
		return _readBool(tag);
	}

	private static final boolean _readBool(final int v) throws IOException {
		// int v = __read(input);
		if (v == TRUE)
			return true;
		else if (v == FALSE)
			return false;
		throw new IOException("unsupported val:" + tag(v));
	}

	// ////////

	public static final byte readByte(final InputStream input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag != BYTE)
			throw new IOException("tag erro :" + tag(tag));
		return _readByte(input);
	}

	public static final byte readByte(final ByteBuffer input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag != BYTE)
			throw new IOException("tag erro :" + tag(tag));
		return _readByte(input);
	}

	public static final byte readByte(final byte[] input) throws IOException {
		return readByte(input, new Ref<Integer>(0));
	}

	public static final byte readByte(final byte[] input, Ref<Integer> pt)
			throws IOException {
		byte tag = (byte) __read(input, pt);
		if (tag != BYTE)
			throw new IOException("tag erro :" + tag(tag));
		return _readByte(input, pt);
	}

	private static final byte _readByte(final InputStream input)
			throws IOException {
		return (byte) __read(input);
	}

	private static final byte _readByte(final ByteBuffer input)
			throws IOException {
		return (byte) __read(input);
	}

	private static final byte _readByte(final byte[] input, Ref<Integer> pt)
			throws IOException {
		return (byte) __read(input, pt);
	}

	// ////////

	public static final short readShort(final InputStream input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag != SHORT)
			throw new IOException("tag erro :" + tag(tag));
		return _readShort(input);
	}

	public static final short readShort(final ByteBuffer input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag != SHORT)
			throw new IOException("tag erro :" + tag(tag));
		return _readShort(input);
	}

	public static final short readShort(final byte[] input) throws IOException {
		return readShort(input, new Ref<Integer>(0));
	}

	public static final short readShort(final byte[] input, Ref<Integer> pt)
			throws IOException {
		byte tag = (byte) __read(input, pt);
		if (tag != SHORT)
			throw new IOException("tag erro :" + tag(tag));
		return _readShort(input, pt);
	}

	private static final short _readShort(final InputStream input)
			throws IOException {
		return (short) (((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0));
	}

	private static final short _readShort(final ByteBuffer input)
			throws IOException {
		return (short) (((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0));
	}

	private static final short _readShort(final byte[] input, Ref<Integer> pt)
			throws IOException {
		return (short) (((__read(input, pt) & 0xff) << 8) + ((__read(input, pt) & 0xff) << 0));
	}

	// ////////

	public static final int readInt(final InputStream input) throws IOException {
		byte tag = (byte) __read(input);
		if (tag != INT)
			throw new IOException("tag erro :" + tag(tag));
		return _readInt(input);
	}

	public static final int readInt(final ByteBuffer input) throws IOException {
		byte tag = (byte) __read(input);
		if (tag != INT)
			throw new IOException("tag erro :" + tag(tag));
		return _readInt(input);
	}

	public static final int readInt(final byte[] input) throws IOException {
		return readInt(input, new Ref<Integer>(0));
	}

	public static final int readInt(final byte[] input, Ref<Integer> pt)
			throws IOException {
		byte tag = (byte) __read(input, pt);
		if (tag != INT)
			throw new IOException("tag erro :" + tag(tag));
		return _readInt(input, pt);
	}

	private static final int _readInt(final InputStream input)
			throws IOException {
		return (int) ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);
	}

	private static final int _readInt(final ByteBuffer input)
			throws IOException {
		return (int) ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);
	}

	private static final int _readInt(final byte[] input, Ref<Integer> pt)
			throws IOException {
		return (int) ((__read(input, pt) & 0xff) << 24)
				+ ((__read(input, pt) & 0xff) << 16)
				+ ((__read(input, pt) & 0xff) << 8)
				+ ((__read(input, pt) & 0xff) << 0);
	}

	// ////////
	public static final long readLong(final InputStream input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag != LONG)
			throw new IOException("tag erro :" + tag(tag));
		return _readLong(input);
	}

	public static final long readLong(final ByteBuffer input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag != LONG)
			throw new IOException("tag erro :" + tag(tag));
		return _readLong(input);
	}

	public static final long readLong(final byte[] input) throws IOException {
		return readLong(input, new Ref<Integer>(0));
	}

	public static final long readLong(final byte[] input, Ref<Integer> pt)
			throws IOException {
		byte tag = (byte) __read(input, pt);
		if (tag != LONG)
			throw new IOException("tag erro :" + tag(tag));
		return _readLong(input, pt);
	}

	private static final long _readLong(final InputStream input)
			throws IOException {
		long high = ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);
		long low = ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);
		return (high << 32) + (0xffffffffL & low);
	}

	private static final long _readLong(final ByteBuffer input)
			throws IOException {
		long high = ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);
		long low = ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);
		return (high << 32) + (0xffffffffL & low);
	}

	private static final long _readLong(final byte[] input, Ref<Integer> pt)
			throws IOException {
		long high = ((__read(input, pt) & 0xff) << 24)
				+ ((__read(input, pt) & 0xff) << 16)
				+ ((__read(input, pt) & 0xff) << 8)
				+ ((__read(input, pt) & 0xff) << 0);
		long low = ((__read(input, pt) & 0xff) << 24)
				+ ((__read(input, pt) & 0xff) << 16)
				+ ((__read(input, pt) & 0xff) << 8)
				+ ((__read(input, pt) & 0xff) << 0);
		return (high << 32) + (0xffffffffL & low);
	}

	// ////////

	public static final float readFloat(final InputStream input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag != FLOAT)
			throw new IOException("tag erro :" + tag(tag));
		return _readFloat(input);
	}

	public static final float readFloat(final ByteBuffer input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag != FLOAT)
			throw new IOException("tag erro :" + tag(tag));
		return _readFloat(input);
	}

	public static final float readFloat(final byte[] input) throws IOException {
		return readFloat(input, new Ref<Integer>(0));
	}

	public static final float readFloat(final byte[] input, Ref<Integer> pt)
			throws IOException {
		byte tag = (byte) __read(input, pt);
		if (tag != FLOAT)
			throw new IOException("tag erro :" + tag(tag));
		return _readFloat(input, pt);
	}

	private static final float _readFloat(final InputStream input)
			throws IOException {
		int i = (int) ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);

		return Float.intBitsToFloat(i);
	}

	private static final float _readFloat(final ByteBuffer input)
			throws IOException {
		int i = (int) ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);

		return Float.intBitsToFloat(i);
	}

	private static final float _readFloat(final byte[] input, Ref<Integer> pt)
			throws IOException {
		int i = (int) ((__read(input, pt) & 0xff) << 24)
				+ ((__read(input, pt) & 0xff) << 16)
				+ ((__read(input, pt) & 0xff) << 8)
				+ ((__read(input, pt) & 0xff) << 0);

		return Float.intBitsToFloat(i);
	}

	// ////////

	public static final double readDouble(final InputStream input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag != DOUBLE)
			throw new IOException("tag erro :" + tag(tag));
		return _readDouble(input);
	}

	public static final double readDouble(final ByteBuffer input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag != DOUBLE)
			throw new IOException("tag erro :" + tag(tag));
		return _readDouble(input);
	}

	public static final double readDouble(final byte[] input)
			throws IOException {
		return readDouble(input, new Ref<Integer>(0));
	}

	public static final double readDouble(final byte[] input, Ref<Integer> pt)
			throws IOException {
		byte tag = (byte) __read(input, pt);
		if (tag != DOUBLE)
			throw new IOException("tag erro :" + tag(tag));
		return _readDouble(input, pt);
	}

	private static final double _readDouble(final InputStream input)
			throws IOException {
		long high = ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);
		long low = ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);
		long l = (high << 32) + (0xffffffffL & low);

		return Double.longBitsToDouble(l);
	}

	private static final double _readDouble(final ByteBuffer input)
			throws IOException {
		long high = ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);
		long low = ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);
		long l = (high << 32) + (0xffffffffL & low);

		return Double.longBitsToDouble(l);
	}

	private static final double _readDouble(final byte[] input, Ref<Integer> pt)
			throws IOException {
		long high = ((__read(input, pt) & 0xff) << 24)
				+ ((__read(input, pt) & 0xff) << 16)
				+ ((__read(input, pt) & 0xff) << 8)
				+ ((__read(input, pt) & 0xff) << 0);
		long low = ((__read(input, pt) & 0xff) << 24)
				+ ((__read(input, pt) & 0xff) << 16)
				+ ((__read(input, pt) & 0xff) << 8)
				+ ((__read(input, pt) & 0xff) << 0);
		long l = (high << 32) + (0xffffffffL & low);

		return Double.longBitsToDouble(l);
	}

	// ////////

	public static final Date readDate(final InputStream input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag != DATE)
			throw new IOException("tag erro :" + tag(tag));
		return _readDate(input);
	}

	public static final Date readDate(final ByteBuffer input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag != DATE)
			throw new IOException("tag erro :" + tag(tag));
		return _readDate(input);
	}

	public static final Date readDate(final byte[] input) throws IOException {
		return readDate(input, new Ref<Integer>(0));
	}

	public static final Date readDate(final byte[] input, Ref<Integer> pt)
			throws IOException {
		byte tag = (byte) __read(input, pt);
		if (tag != DATE)
			throw new IOException("tag erro :" + tag(tag));
		return _readDate(input, pt);
	}

	private static final Date _readDate(final InputStream input)
			throws IOException {
		long high = ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);
		long low = ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);
		long tm = (high << 32) + (0xffffffffL & low);
		return new Date(tm);
	}

	private static final Date _readDate(final ByteBuffer input)
			throws IOException {
		long high = ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);
		long low = ((__read(input) & 0xff) << 24)
				+ ((__read(input) & 0xff) << 16)
				+ ((__read(input) & 0xff) << 8) + ((__read(input) & 0xff) << 0);
		long tm = (high << 32) + (0xffffffffL & low);
		return new Date(tm);
	}

	private static final Date _readDate(final byte[] input, Ref<Integer> pt)
			throws IOException {
		long high = ((__read(input, pt) & 0xff) << 24)
				+ ((__read(input, pt) & 0xff) << 16)
				+ ((__read(input, pt) & 0xff) << 8)
				+ ((__read(input, pt) & 0xff) << 0);
		long low = ((__read(input, pt) & 0xff) << 24)
				+ ((__read(input, pt) & 0xff) << 16)
				+ ((__read(input, pt) & 0xff) << 8)
				+ ((__read(input, pt) & 0xff) << 0);
		long tm = (high << 32) + (0xffffffffL & low);
		return new Date(tm);
	}

	// ////////

	public static final byte[] readBytes(final InputStream input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag == NULL)
			return null;
		if (tag != BYTES)
			throw new IOException("tag erro :" + tag(tag));
		return _readBytes(input);
	}

	public static final byte[] readBytes(final ByteBuffer input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag == NULL)
			return null;
		if (tag != BYTES)
			throw new IOException("tag erro :" + tag(tag));
		return _readBytes(input);
	}

	public static final byte[] readBytes(final byte[] input) throws IOException {
		return readBytes(input, new Ref<Integer>(0));
	}

	public static final byte[] readBytes(final byte[] input, Ref<Integer> pt)
			throws IOException {
		byte tag = (byte) __read(input, pt);
		if (tag == NULL)
			return null;
		if (tag != BYTES)
			throw new IOException("tag erro :" + tag(tag));
		return _readBytes(input, pt);
	}

	private static final byte[] _readBytes(final InputStream input)
			throws IOException {
		int len = _readInt(input);
		byte[] bytes = new byte[len];
		return __readFully(input, bytes);
	}

	private static final byte[] _readBytes(final ByteBuffer input)
			throws IOException {
		int len = _readInt(input);
		byte[] bytes = new byte[len];
		return __readFully(input, bytes);
	}

	private static final byte[] _readBytes(final byte[] input, Ref<Integer> pt)
			throws IOException {
		int len = _readInt(input, pt);
		byte[] bytes = new byte[len];
		return __readFully(input, pt, bytes);
	}

	// ////////
	public static final String readUTF8(final InputStream input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag == NULL)
			return null;
		if (tag != UTF8)
			throw new IOException("tag erro :" + tag(tag));
		return _readUTF8(input);
	}

	public static final String readUTF8(final ByteBuffer input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag == NULL)
			return null;
		if (tag != UTF8)
			throw new IOException("tag erro :" + tag(tag));
		return _readUTF8(input);
	}

	public static final String readUTF8(final byte[] input) throws IOException {
		return readUTF8(input, new Ref<Integer>(0));
	}

	private static final String _readUTF8(final InputStream input)
			throws IOException {
		byte[] bytes = _readBytes(input);
		return new String(bytes, _UTF8);
	}

	private static final String _readUTF8(final ByteBuffer input)
			throws IOException {
		byte[] bytes = _readBytes(input);
		return new String(bytes, _UTF8);
	}

	public static final String readUTF8(final byte[] input, Ref<Integer> pt)
			throws IOException {
		byte tag = (byte) __read(input, pt);
		if (tag == NULL)
			return null;
		if (tag != UTF8)
			throw new IOException("tag erro :" + tag(tag));
		return _readUTF8(input, pt);
	}

	private static final String _readUTF8(final byte[] input, Ref<Integer> pt)
			throws IOException {
		byte[] bytes = _readBytes(input, pt);
		return new String(bytes, _UTF8);
	}

	// ////////

	public static final Map readMap(final InputStream input) throws Exception {
		byte tag = (byte) __read(input);
		if (tag == NULL)
			return null;
		if (tag != MAP)
			throw new IOException("bytes err: tag=" + tag(tag));
		return _readMap(input);
	}

	public static final Map readMap(final ByteBuffer input) throws IOException {
		byte tag = (byte) __read(input);
		if (tag == NULL)
			return null;
		if (tag != MAP)
			throw new IOException("bytes err: tag=" + tag(tag));
		return _readMap(input);
	}

	public static final Map readMap(final byte[] input) throws IOException {
		return readMap(input, new Ref<Integer>(0));
	}

	public static final Map readMap(final byte[] input, Ref<Integer> pt)
			throws IOException {
		byte tag = (byte) __read(input, pt);
		if (tag != MAP)
			throw new IOException("bytes err: tag=" + tag(tag));
		return _readMap(input, pt);
	}

	public static final Map _readMap(final InputStream input) throws Exception {
		Map map = new HashMap();
		int size = _readInt(input);
		for (int i = 0; i < size; i++) {
			Object key = readObject(input);
			Object value = readObject(input);
			map.put(key, value);
		}
		return map;
	}

	public static final Map _readMap(final ByteBuffer input) throws IOException {
		Map map = new HashMap();
		int size = _readInt(input);
		for (int i = 0; i < size; i++) {
			Object key = readObject(input);
			Object value = readObject(input);
			map.put(key, value);
		}
		return map;
	}

	public static final Map _readMap(final byte[] input, Ref<Integer> pt)
			throws IOException {
		Map map = new HashMap();
		int size = _readInt(input, pt);
		for (int i = 0; i < size; i++) {
			Object key = readObject(input, pt);
			Object value = readObject(input, pt);
			map.put(key, value);
		}
		return map;
	}

	// ////////
	public static final List readList(final InputStream input) throws Exception {
		byte tag = (byte) __read(input);
		if (tag == NULL)
			return null;
		if (tag != LIST)
			throw new IOException("bytes err: tag=" + tag(tag));
		return _readList(input);
	}

	public static final List readList(final ByteBuffer input)
			throws IOException {
		byte tag = (byte) __read(input);
		if (tag == NULL)
			return null;
		if (tag != LIST)
			throw new IOException("bytes err: tag=" + tag(tag));
		return _readList(input);
	}

	public static final List readList(final byte[] input) throws IOException {
		return readList(input, new Ref<Integer>(0));
	}

	public static final List readList(final byte[] input, Ref<Integer> pt)
			throws IOException {
		byte tag = (byte) __read(input, pt);
		if (tag == NULL)
			return null;
		if (tag != LIST)
			throw new IOException("bytes err: tag=" + tag(tag));
		return _readList(input, pt);
	}

	private static final List _readList(final InputStream input)
			throws Exception {
		List list = new ArrayList();
		int size = _readInt(input);
		for (int i = 0; i < size; i++) {
			Object value = readObject(input);
			list.add(value);
		}
		return list;
	}

	private static final List _readList(final ByteBuffer input)
			throws IOException {
		List list = new ArrayList();
		int size = _readInt(input);
		for (int i = 0; i < size; i++) {
			Object value = readObject(input);
			list.add(value);
		}
		return list;
	}

	private static final List _readList(final byte[] input, Ref<Integer> pt)
			throws IOException {
		List list = new ArrayList();
		int size = _readInt(input, pt);
		for (int i = 0; i < size; i++) {
			Object value = readObject(input, pt);
			list.add(value);
		}
		return list;
	}

	private static final Object _readObject(final InputStream input)
			throws Exception {
		String clazz = readUTF8(input);
		BIOSerial serObj = (BIOSerial) Class.forName(clazz).newInstance();
		serObj._readObject(input);
		return serObj;
	}

	// ////////
	public static final Object readObject(final InputStream input)
			throws Exception {
		byte tag = (byte) __read(input);
		switch (tag) {
		case NULL:
			return null;
		case TRUE:
			return true;
		case FALSE:
			return false;
		case BYTE:
			return _readByte(input);
		case SHORT:
			return _readShort(input);
		case INT:
			return _readInt(input);
		case LONG:
			return _readLong(input);
		case FLOAT:
			return _readFloat(input);
		case DOUBLE:
			return _readDouble(input);
		case DATE:
			return _readDate(input);
		case UTF8:
			return _readUTF8(input);
		case BYTES:
			return _readBytes(input);
		case MAP:
			return _readMap(input);
		case LIST:
			return _readList(input);
		case OBJ:
			return _readObject(input);
		default:
			throw new IOException("unsupported tag:" + tag(tag));
		}
	}

	public static final Object readObject(final ByteBuffer input)
			throws IOException {
		byte tag = (byte) __read(input);
		switch (tag) {
		case NULL:
			return null;
		case TRUE:
			return true;
		case FALSE:
			return false;
		case BYTE:
			return _readByte(input);
		case SHORT:
			return _readShort(input);
		case INT:
			return _readInt(input);
		case LONG:
			return _readLong(input);
		case FLOAT:
			return _readFloat(input);
		case DOUBLE:
			return _readDouble(input);
		case DATE:
			return _readDate(input);
		case UTF8:
			return _readUTF8(input);
		case BYTES:
			return _readBytes(input);
		case MAP:
			return _readMap(input);
		case LIST:
			return _readList(input);
		default:
			throw new IOException("unsupported tag:" + tag(tag));
		}
	}

	public static final Object readObject(final byte[] input)
			throws IOException {
		return readObject(input, new Ref<Integer>(0));
	}

	public static final Object readObject(final byte[] input, Ref<Integer> pt)
			throws IOException {
		byte tag = (byte) __read(input, pt);
		switch (tag) {
		case NULL:
			return null;
		case TRUE:
			return true;
		case FALSE:
			return false;
		case BYTE:
			return _readByte(input, pt);
		case SHORT:
			return _readShort(input, pt);
		case INT:
			return _readInt(input, pt);
		case LONG:
			return _readLong(input, pt);
		case FLOAT:
			return _readFloat(input, pt);
		case DOUBLE:
			return _readDouble(input, pt);
		case DATE:
			return _readDate(input, pt);
		case UTF8:
			return _readUTF8(input, pt);
		case BYTES:
			return _readBytes(input, pt);
		case MAP:
			return _readMap(input, pt);
		case LIST:
			return _readList(input, pt);
		default:
			throw new IOException("unsupported tag:" + tag(tag));
		}
	}

	public static final int writeCall(final OutputStream output, final int pid,
			final int cmd, final Map args) throws Exception {
		int len = writeInt(output, pid);
		len += writeInt(output, cmd);
		len += writeMap(output, args);
		return len;
	}

	public static final Map readCall(final InputStream input,
			final Ref<Integer> pid, final Ref<Integer> cmd) throws Exception {
		pid.val = readInt(input);
		cmd.val = readInt(input);
		return readMap(input);
	}

	public static final int writeCall(final ByteBuffer output, final int pid,
			final int cmd, final Map args) throws IOException {
		int len = writeInt(output, pid);
		len += writeInt(output, cmd);
		len += writeMap(output, args);
		return len;
	}

	public static final Map readCall(final ByteBuffer input,
			final Ref<Integer> pid, final Ref<Integer> cmd) throws IOException {
		pid.val = readInt(input);
		cmd.val = readInt(input);
		return readMap(input);
	}

	public static final Map readCall(final byte[] input,
			final Ref<Integer> pid, final Ref<Integer> cmd) throws IOException {
		final Ref<Integer> pt = new Ref<Integer>(0);
		return readCall(input, pt, pid, cmd);
	}

	public static final Map readCall(final byte[] input, final Ref<Integer> pt,
			final Ref<Integer> pid, final Ref<Integer> cmd) throws IOException {
		pid.val = readInt(input, pt);
		cmd.val = readInt(input, pt);
		return readMap(input, pt);
	}

	// /////////////////////

	public int writeTo(final OutputStream output) throws IOException {
		return 0;
	}

	public void readFrom(final InputStream input) throws IOException {
	}

	public static void main(String[] args) throws Exception {
		// {
		// ByteOutStream output = newStream();
		// writeBool(output, false);
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// ByteOutStream input = newStream(bytes);
		// boolean i1 = readBool(input);
		// System.out.println(i1);
		// }

		// {
		// ByteOutStream output = newStream();
		// writeBool(output, false);
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// boolean i1 = readBool(bytes);
		// System.out.println(i1);
		// }

		// {
		// ByteOutStream output = newStream();
		// writeByte(output, (byte) -123);
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// ByteOutStream input = newStream(bytes);
		// byte i1 = readByte(input);
		// System.out.println(i1);
		// }
		// {
		// ByteOutStream output = newStream();
		// writeByte(output, (byte) -123);
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// byte i1 = readByte(bytes);
		// System.out.println(i1);
		// }
		// {
		// ByteOutStream output = newStream();
		// writeShort(output, (short)12300);
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// ByteOutStream input = newStream(bytes);
		// short i1 = readShort(input);
		// System.out.println(i1);
		// }
		// {
		// ByteOutStream output = newStream();
		// writeShort(output, (short)12300);
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// short i1 = readShort(bytes);
		// System.out.println(i1);
		// }
		// {
		// ByteOutStream output = newStream();
		// writeInt(output, -1230000);
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// ByteOutStream input = newStream(bytes);
		// int i1 = readInt(input);
		// System.out.println(i1);
		// }
		// {
		// ByteOutStream output = newStream();
		// writeInt(output, -1230000);
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// int i1 = readInt(bytes);
		// System.out.println(i1);
		// }
		// {
		// ByteOutStream output = newStream();
		// writeLong(output, 1230000);
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// ByteOutStream input = newStream(bytes);
		// long i1 = readLong(input);
		// System.out.println(i1);
		// }
		// {
		// ByteOutStream output = newStream();
		// writeLong(output, 1230000);
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// long i1 = readLong(bytes);
		// System.out.println(i1);
		// }
		// {
		// ByteOutStream output = newStream();
		// writeFloat(output, (float) 3.1415926);
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// ByteOutStream input = newStream(bytes);
		// float i1 = readFloat(input);
		// System.out.println(i1);
		// }
		// {
		// ByteOutStream output = newStream();
		// writeFloat(output, (float) 3.1415926);
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// float i1 = readFloat(bytes);
		// System.out.println(i1);
		// }
		// {
		// ByteOutStream output = newStream();
		// writeDouble(output, 3.1415926123132);
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// ByteOutStream input = newStream(bytes);
		// double i1 = readDouble(input);
		// System.out.println(i1);
		// }
		// {
		// ByteOutStream output = newStream();
		// writeDouble(output, 3.1415926123132);
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// double i1 = readDouble(bytes);
		// System.out.println(i1);
		// }
		// {
		// ByteOutStream output = newStream();
		// writeUTF8(output, "a3.1415926123132");
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// ByteOutStream input = newStream(bytes);
		// String i1 = readUTF8(input);
		// System.out.println(i1);
		// }
		// {
		// ByteOutStream output = newStream();
		// writeUTF8(output, "a3.1415926123132");
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// String i1 = readUTF8(bytes);
		// System.out.println(i1);
		// }
		// {
		// ByteOutStream output = newStream();
		// writeBytes(output, "a3.1415926123132".getBytes());
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// ByteOutStream input = newStream(bytes);
		// byte[] i1 = readBytes(input);
		// System.out.println(HEX(i1));
		// }
		// {
		// ByteOutStream output = newStream();
		// writeBytes(output, "a3.1415926123132".getBytes());
		// byte[] bytes = output.toByteArray();
		// System.out.println(HEX(bytes));
		// byte[] i1 = readBytes(bytes);
		// System.out.println(HEX(i1));
		// }
		// {
		// ByteOutStream output = newStream();
		// writeBool(output, true);
		// writeBool(output, false);
		// writeByte(output, (byte) -123);
		// writeByte(output, (byte) 123);
		// writeShort(output, (short) -12300);
		// writeShort(output, (short) 12300);
		// writeInt(output, -1230000);
		// writeInt(output, 1230000);
		// writeLong(output, -1230000000L);
		// writeLong(output, 1230000000L);
		// writeFloat(output, (float) -3.141592612);
		// writeFloat(output, (float) 3.141592612);
		// writeDouble(output, -3.1415926123132);
		// writeDouble(output, 3.1415926123132);
		// writeUTF8(output, "a3.1415926123132");
		// writeBytes(output, "a3.1415926123132".getBytes());
		//
		// byte[] bytes = output.toByteArray();
		// System.out.println(bytes.length + " " + HEX(bytes));
		// ByteOutStream input = newStream(bytes);
		// System.out.println(readBool(input));
		// System.out.println(readBool(input));
		// System.out.println(readByte(input));
		// System.out.println(readByte(input));
		// System.out.println(readShort(input));
		// System.out.println(readShort(input));
		// System.out.println(readInt(input));
		// System.out.println(readInt(input));
		// System.out.println(readLong(input));
		// System.out.println(readLong(input));
		// System.out.println(readFloat(input));
		// System.out.println(readFloat(input));
		// System.out.println(readDouble(input));
		// System.out.println(readDouble(input));
		// System.out.println(readObject(input));
		// System.out.println(readObject(input));
		// }
		// {
		// ByteOutStream output = newStream();
		// writeBool(output, true);
		// writeBool(output, false);
		// writeByte(output, (byte) -123);
		// writeByte(output, (byte) 123);
		// writeShort(output, (short) -12300);
		// writeShort(output, (short) 12300);
		// writeInt(output, -1230000);
		// writeInt(output, 1230000);
		// writeLong(output, -1230000000L);
		// writeLong(output, 1230000000L);
		// writeFloat(output, (float) -3.141592612);
		// writeFloat(output, (float) 3.141592612);
		// writeDouble(output, -3.1415926123132);
		// writeDouble(output, 3.1415926123132);
		// writeUTF8(output, "a3.1415926123132");
		// writeBytes(output, "a3.1415926123132".getBytes());
		//
		// byte[] bytes = output.toByteArray();
		//
		// Ref<Integer> pt = new Ref<>(0);
		//
		// System.out.println(bytes.length + " " + HEX(bytes));
		// System.out.println(readBool(bytes, pt));
		// System.out.println(readBool(bytes, pt));
		// System.out.println(readByte(bytes, pt));
		// System.out.println(readByte(bytes, pt));
		// System.out.println(readShort(bytes, pt));
		// System.out.println(readShort(bytes, pt));
		// System.out.println(readInt(bytes, pt));
		// System.out.println(readInt(bytes, pt));
		// System.out.println(readLong(bytes, pt));
		// System.out.println(readLong(bytes, pt));
		// System.out.println(readFloat(bytes, pt));
		// System.out.println(readFloat(bytes, pt));
		// System.out.println(readDouble(bytes, pt));
		// System.out.println(readDouble(bytes, pt));
		// System.out.println(readObject(bytes, pt));
		// System.out.println(readObject(bytes, pt));
		// }

		// {
		// List list = new ArrayList();
		// ByteOutStream output = newStream();
		// list.add(true);
		// list.add(false);
		// list.add((byte) -123);
		// list.add((byte) 123);
		// list.add((short) -12300);
		// list.add((short) 12300);
		// list.add(-1230000);
		// list.add(1230000);
		// list.add(-1230000000L);
		// list.add(1230000000L);
		// list.add((float) -3.141592612);
		// list.add((float) 3.141592612);
		// list.add(-3.1415926123132);
		// list.add(3.1415926123132);
		// list.add("a3.1415926123132");
		// list.add("a3.1415926123132".getBytes());
		// writeList(output, list);
		//
		// // [true, false, -123, 123, -12300, 12300, -1230000, 1230000,
		// // -1230000000, 1230000000, -3.1415925, 3.1415925, -3.1415926123132,
		// // 3.1415926123132, a3.1415926123132, [B@4a89eb77]
		//
		// byte[] bytes = output.toByteArray();
		// System.out.println(bytes.length + " " + HEX(bytes));
		// ByteOutStream input = newStream(bytes);
		// System.out.println(readList(input));
		// }
		// {
		// List list = new ArrayList();
		// ByteOutStream output = newStream();
		// list.add(true);
		// list.add(false);
		// list.add((byte) -123);
		// list.add((byte) 123);
		// list.add((short) -12300);
		// list.add((short) 12300);
		// list.add(-1230000);
		// list.add(1230000);
		// list.add(-1230000000L);
		// list.add(1230000000L);
		// list.add((float) -3.141592612);
		// list.add((float) 3.141592612);
		// list.add(-3.1415926123132);
		// list.add(3.1415926123132);
		// list.add("a3.1415926123132");
		// list.add("a3.1415926123132".getBytes());
		// writeObject(output, list);
		//
		// // [true, false, -123, 123, -12300, 12300, -1230000, 1230000,
		// // -1230000000, 1230000000, -3.1415925, 3.1415925, -3.1415926123132,
		// // 3.1415926123132, a3.1415926123132, [B@4a89eb77]
		//
		// byte[] bytes = output.toByteArray();
		// System.out.println(bytes.length + " " + HEX(bytes));
		// System.out.println(readObject(bytes));
		// }
		// {
		// Map map = new HashMap();
		// ByteOutStream output = newStream();
		// map.put(0, true);
		// map.put(1, false);
		// map.put(2, (byte) -123);
		// map.put(3, (byte) 123);
		// map.put(4, (short) -12300);
		// map.put(5, (short) 12300);
		// map.put(6, -1230000);
		// map.put(7, 1230000);
		// map.put(8, -1230000000L);
		// map.put(9, 1230000000L);
		// map.put(10, (float) -3.141592612);
		// map.put(11, (float) 3.141592612);
		// map.put(12, -3.1415926123132);
		// map.put(13, 3.1415926123132);
		// map.put(14, "a3.1415926123132");
		// map.put(15, "a3.1415926123132".getBytes());
		// writeMap(output, map);
		//
		// // {0=true, 1=false, 2=-123, 3=123, 4=-12300, 5=12300, 6=-1230000,
		// // 7=1230000, 8=-1230000000, 9=1230000000, 10=-3.1415925,
		// // 11=3.1415925, 12=-3.1415926123132, 13=3.1415926123132,
		// // 14=a3.1415926123132, 15=[B@4a89eb77}
		//
		// byte[] bytes = output.toByteArray();
		// System.out.println(bytes.length + " " + HEX(bytes));
		// System.out.println(readMap(bytes));
		// }

		// {
		// Map map = new HashMap();
		// ByteOutStream output = newStream();
		// map.put(0, true);
		// map.put(1, false);
		// map.put(2, (byte) -123);
		// map.put(3, (byte) 123);
		// map.put(4, (short) -12300);
		// map.put(5, (short) 12300);
		// map.put(6, -1230000);
		// map.put(7, 1230000);
		// map.put(8, -1230000000L);
		// map.put(9, 1230000000L);
		// map.put(10, (float) -3.141592612);
		// map.put(11, (float) 3.141592612);
		// map.put(12, -3.1415926123132);
		// map.put(13, 3.1415926123132);
		// map.put(14, "a3.1415926123132");
		// map.put(15, "a3.1415926123132".getBytes());
		// writeObject(output, map);
		//
		// // {0=true, 1=false, 2=-123, 3=123, 4=-12300, 5=12300, 6=-1230000,
		// // 7=1230000, 8=-1230000000, 9=1230000000, 10=-3.1415925,
		// // 11=3.1415925, 12=-3.1415926123132, 13=3.1415926123132,
		// // 14=a3.1415926123132, 15=[B@4a89eb77}
		//
		// byte[] bytes = output.toByteArray();
		// System.out.println(bytes.length + " " + HEX(bytes));
		// ByteOutStream input = newStream(bytes);
		// System.out.println(readObject(input));
		// }

		// {
		// Map map = new HashMap();
		// ByteOutStream output = newStream();
		// map.put(0, true);
		// map.put(1, false);
		// map.put(2, (byte) -123);
		// map.put(3, (byte) 123);
		// map.put(4, (short) -12300);
		// map.put(5, (short) 12300);
		// map.put(6, -1230000);
		// map.put(7, 1230000);
		// map.put(8, -1230000000L);
		// map.put(9, 1230000000L);
		// map.put(10, (float) -3.141592612);
		// map.put(11, (float) 3.141592612);
		// map.put(12, -3.1415926123132);
		// map.put(13, 3.1415926123132);
		// map.put(14, "a3.1415926123132");
		// map.put(15, "a3.1415926123132".getBytes());
		// writeObject(output, map);
		//
		// // {0=true, 1=false, 2=-123, 3=123, 4=-12300, 5=12300, 6=-1230000,
		// // 7=1230000, 8=-1230000000, 9=1230000000, 10=-3.1415925,
		// // 11=3.1415925, 12=-3.1415926123132, 13=3.1415926123132,
		// // 14=a3.1415926123132, 15=[B@4a89eb77}
		//
		// byte[] bytes = output.toByteArray();
		// System.out.println(bytes.length + " " + HEX(bytes));
		// System.out.println(readObject(bytes));
		// }

		{
			ByteOutStream output = newStream();
			writeObject(output, new T1());
			// writeUTF8(output, "A");
			byte[] bytes = output.toByteArray();
			System.out.println(new String(bytes));
			System.out.println(bytes.length + " " + HEX(bytes));
			try (ByteInStream in = ByteInPool.borrowObject(bytes);) {
				Object t1 = readObject(in);
				// String t1 = readUTF8(in);
				System.out.println(t1);
			}
		}

	}

	public static class T1 implements BIOSerial {
		public int id;
		public String name;

		@Override
		public void _readObject(InputStream input) throws Exception {
			this.id = readInt(input);
			this.name = readUTF8(input);
		}

		@Override
		public int _writeObject(OutputStream output) throws IOException {
			writeInt(output, id);
			writeUTF8(output, name);
			return 0;
		}

		public String toString() {
			return "T1";
		}
	}
}
