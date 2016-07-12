package com.bowlong.bio;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bowlong.lang.ByteEx;
import com.bowlong.util.NewMap;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BIOInputStream extends DataInputStream implements BIOType {

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

	public BIOInputStream(InputStream in) {
		super(in);
	}

	// ////////
	public boolean getBool() throws IOException {
		byte tag = super.readByte();
		if (tag != TRUE && tag != FALSE)
			throw new IOException("tag erro :" + tag(tag));
		if (tag == TRUE)
			return true;
		else if (tag == FALSE)
			return false;
		else
			throw new IOException("unsupported val:" + tag(tag));
	}

	// ////////

	public byte getByte() throws IOException {
		byte tag = super.readByte();
		if (tag != BYTE)
			throw new IOException("tag erro :" + tag(tag));
		return super.readByte();
	}

	// ////////

	public short getShort() throws IOException {
		byte tag = super.readByte();
		if (tag != SHORT)
			throw new IOException("tag erro :" + tag(tag));
		return super.readShort();
	}

	// ////////

	public int getInt() throws IOException {
		byte tag = super.readByte();
		if (tag != INT)
			throw new IOException("tag erro :" + tag(tag));
		return super.readInt();
	}

	// ////////
	public long getLong() throws IOException {
		byte tag = super.readByte();
		if (tag != LONG)
			throw new IOException("tag erro :" + tag(tag));
		return super.readLong();
	}

	// ////////

	public float getFloat() throws IOException {
		byte tag = super.readByte();
		if (tag != FLOAT)
			throw new IOException("tag erro :" + tag(tag));
		return super.readFloat();
	}

	// ////////

	public double getDouble() throws IOException {
		byte tag = super.readByte();
		if (tag != DOUBLE)
			throw new IOException("tag erro :" + tag(tag));
		return super.readDouble();
	}

	// ////////

	public Date getDate() throws IOException {
		byte tag = super.readByte();
		if (tag == NULL)
			return null;
		if (tag != DATE)
			throw new IOException("tag erro :" + tag(tag));
		return _getDate();
	}

	public Date _getDate() throws IOException {
		long tm = super.readLong();
		return new Date(tm);
	}

	// ////////

	public byte[] getBytes() throws IOException {
		byte tag = super.readByte();
		if (tag == NULL)
			return null;
		if (tag != BYTES)
			throw new IOException("tag erro :" + tag(tag));
		return _getBytes();
	}

	public byte[] _getBytes() throws IOException {
		int length = super.readInt();
		byte[] bytes = new byte[length];
		super.readFully(bytes);
		return bytes;
	}

	// ////////
	public String getUTF8() throws IOException {
		byte tag = super.readByte();
		if (tag == NULL)
			return null;
		if (tag != UTF8)
			throw new IOException("tag erro :" + tag(tag));
		return _getUTF8();
	}

	public String _getUTF8() throws IOException {
		int length = super.readInt();
		byte[] bytes = new byte[length];
		super.readFully(bytes);
		return new String(bytes, _UTF8);
	}

	// ////////

	public NewMap getMap() throws Exception {
		byte tag = super.readByte();
		if (tag == NULL)
			return null;
		if (tag != MAP)
			throw new IOException("bytes err: tag=" + tag(tag));
		return _getMap();
	}

	public NewMap _getMap() throws Exception {
		NewMap map = new NewMap();
		int size = super.readInt();
		for (int i = 0; i < size; i++) {
			Object key = getObject();
			Object value = getObject();
			map.put(key, value);
		}
		return map;
	}

	// ////////
	public List getList() throws Exception {
		byte tag = super.readByte();
		if (tag == NULL)
			return null;
		if (tag != LIST)
			throw new IOException("bytes err: tag=" + tag(tag));
		return _getList();
	}

	private List _getList() throws Exception {
		List list = new ArrayList();
		int size = super.readInt();
		for (int i = 0; i < size; i++) {
			Object value = getObject();
			list.add(value);
		}
		return list;
	}

	public Object getObj() throws Exception {
		byte tag = super.readByte();
		if (tag == NULL)
			return null;
		if (tag != OBJ)
			throw new IOException("bytes err: tag=" + tag(tag));
		return _getList();
	}

	private Object _getObj() throws Exception {
		String clazz = getUTF8();
		BIOSerial serObj = (BIOSerial) Class.forName(clazz).newInstance();
		serObj._readObject(this);
		return serObj;
	}

	// ////////
	public Object getObject() throws Exception {
		byte tag = super.readByte();
		switch (tag) {
		case NULL:
			return null;
		case TRUE:
			return true;
		case FALSE:
			return false;
		case BYTE:
			return super.readByte();
		case SHORT:
			return super.readShort();
		case INT:
			return super.readInt();
		case LONG:
			return super.readLong();
		case FLOAT:
			return super.readFloat();
		case DOUBLE:
			return super.readDouble();
		case DATE:
			return _getDate();
		case UTF8:
			return _getUTF8();
		case BYTES:
			return _getBytes();
		case MAP:
			return _getMap();
		case LIST:
			return _getList();
		case OBJ:
			return _getObj();
		default:
			throw new IOException("unsupported tag:" + tag(tag));
		}
	}

	// /////////////////////

}
