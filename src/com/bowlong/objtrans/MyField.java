package com.bowlong.objtrans;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.bowlong.reflect.BeanUtils;

public class MyField {
	public String name;
	public Type type;
	public boolean isPublic;
	public boolean isProtected;
	public boolean isPrivate;

	public String toString() {
		String s;
		if (isPublic)
			s = "public";
		else if (isProtected)
			s = "protected";
		else if (isPrivate)
			s = "private";
		else
			s = "unknow";
		return "[" + s + " name:" + name + ",type:" + type + "]";
	}

	public static final boolean isPublic(java.lang.reflect.Field f) {
		String s = f.toString();
		return s.startsWith("public");
	}

	public static final boolean isProtected(java.lang.reflect.Field f) {
		String s = f.toString();
		return s.startsWith("protected");
	}

	public static final boolean isPrivate(java.lang.reflect.Field f) {
		String s = f.toString();
		return s.startsWith("private");
	}

	public static MyField create(java.lang.reflect.Field f) {
		if (f == null)
			return null;

		MyField r2 = new MyField();
		r2.name = f.getName();
		r2.type = f.getGenericType();
		r2.isPublic = isPublic(f);
		r2.isProtected = isProtected(f);
		r2.isPrivate = isPrivate(f);
		return r2;
	}

	public static Map<String, MyField> create(Class<?> c) {
		Map<String, MyField> r2 = new HashMap<>();
		if (c == null)
			return r2;

		Field[] fs = BeanUtils.getFields(c);
		for (Field f : fs) {
			MyField mf = create(f);
			if (mf == null)
				continue;
			r2.put(mf.name, mf);
		}
		return r2;
	}

	public static Map<String, MyField> createWithPublic(Class<?> c) {
		Map<String, MyField> r2 = new HashMap<>();
		if (c == null)
			return r2;

		Field[] fs = BeanUtils.getFields(c);
		for (Field f : fs) {
			MyField mf = create(f);
			if (mf == null)
				continue;
			if (!mf.isPublic)
				continue;
			r2.put(mf.name, mf);
		}
		return r2;
	}

}
