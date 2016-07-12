package com.bowlong.bio;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bowlong.lang.ByteEx;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BIOOutputStream extends DataOutputStream implements BIOType {

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
		case OBJ:
			return STR_OBJ;
		default:
			return "UNKNOW - " + tag;
		}
	}

	public static final String HEX(byte[] b) {
		return ByteEx.bytesToString(b);
	}

	public BIOOutputStream(OutputStream out) {
		super(out);
	}

	// /////////////////////
	public final int putBegin() throws IOException {
		super.writeByte(BEGIN);
		return written;
	}

	public final int putEnd() throws IOException {
		super.writeByte(END);
		return written;
	}

	public final int putNull() throws IOException {
		super.writeByte(NULL);
		return written;
	}

	public final int putBool(final boolean v) throws IOException {
		if (v)
			super.writeByte(TRUE);
		else
			super.writeByte(FALSE);
		return written;
	}

	public final int putByte(final byte v) throws IOException {
		super.writeByte(BYTE);
		super.writeByte(v);
		return written;
	}

	public final int putShort(final short v) throws IOException {
		super.writeByte(SHORT);
		super.writeShort(v);
		return written;
	}

	public final int putInt(final int v) throws IOException {
		super.writeByte(INT);
		super.writeInt(v);
		return written;
	}

	public final int putLong(final long v) throws IOException {
		super.writeByte(LONG);
		super.writeLong(v);
		return written;
	}

	public final int putFloat(final float f) throws IOException {
		super.writeByte(FLOAT);
		super.writeFloat(f);
		return written;
	}

	public final int putDouble(final double d) throws IOException {
		super.writeByte(DOUBLE);
		super.writeDouble(d);
		return written;
	}

	public final int putDate(final Date d) throws IOException {
		if (d == null)
			return putNull();

		long v = d.getTime();

		super.writeByte(DATE);
		super.writeLong(v);
		return written;
	}

	public final int putBytes(final byte[] v) throws IOException {
		if (v == null)
			return putNull();

		super.writeByte(BYTES);
		super.writeInt(v.length);
		super.write(v);
		return written;
	}

	public final int putUTF8(final String s) throws IOException {
		if (s == null)
			return putNull();

		byte[] v = s.getBytes(_UTF8);
		super.writeByte(UTF8);
		super.writeInt(v.length);
		super.write(v);
		return written;
	}

	public final int putList(final List v) throws Exception {
		if (v == null)
			return putNull();

		int SIZE = v.size();
		super.writeByte(LIST); // tag
		super.writeInt(SIZE);// size
		for (Object object : v) { // datas
			putObject(object);
		}
		return written;
	}

	public final int putMap(final Map<Object, Object> v) throws Exception {
		if (v == null)
			return putNull();

		int SIZE = v.size();
		super.writeByte(MAP); // tag
		super.writeInt(SIZE); // size
		Set<Entry<Object, Object>> entrys = v.entrySet();
		for (Entry e : entrys) { // datas
			Object _key = e.getKey();
			Object _val = e.getValue();
			putObject(_key);
			putObject(_val);
		}
		return written;
	}

	public final int putObj(final BIOSerial v) throws Exception {
		if (v == null)
			return putNull();

		super.writeByte(OBJ); // tag
		String clazz = v.getClass().getName();
		putUTF8(clazz);
		v._writeObject(this);
		return written;
	}

	public final int putObject(final Object v) throws Exception {
		if (v == null) {
			return putNull();
		} else if (v instanceof Boolean) {
			boolean val = (Boolean) v;
			return putBool(val);
		} else if (v instanceof Byte) {
			byte val = (Byte) v;
			return putByte(val);
		} else if (v instanceof Short) {
			short val = (Short) v;
			return putShort(val);
		} else if (v instanceof Integer) {
			int val = (Integer) v;
			return putInt(val);
		} else if (v instanceof Long) {
			long val = (Long) v;
			return putLong(val);
		} else if (v instanceof Float) {
			float val = (Float) v;
			return putFloat(val);
		} else if (v instanceof Double) {
			double val = (Double) v;
			return putDouble(val);
		} else if (v instanceof Date) {
			Date val = (Date) v;
			return putDate(val);
		} else if (v instanceof byte[]) {
			byte[] val = (byte[]) v;
			return putBytes(val);
		} else if (v instanceof String) {
			String val = (String) v;
			return putUTF8(val);
		} else if (v instanceof List) {
			List val = (List) v;
			return putList(val);
		} else if (v instanceof Map) {
			Map val = (Map) v;
			return putMap(val);
		} else if (v instanceof BIOSerial) {
			BIOSerial val = (BIOSerial) v;
			return putObj(val);
		} else {
			throw new IOException("unsupported object:" + v);
		}
	}

	// /////////////////////

}
