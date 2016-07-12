package com.bowlong.third.assist;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.bowlong.util.NewList;
import com.bowlong.util.NewMap;

public class A2G {
	public static final int METHOD = 0;
	public static final int BEAN = -1;
	public static final int RETURN_STAT = 1;

	public static final String SERVER = "SERVER";
	public static final String CLIENT = "CLIENT";
	public static final String DATA = "DATA";

	public static final boolean isServer(Class<?> c) {
		A2Class b2c = c.getAnnotation(A2Class.class);
		if (b2c == null)
			return false;
		boolean ret = (b2c.type().equals(SERVER));
		return ret;
	}

	public static final boolean isClient(Class<?> c) {
		A2Class b2c = c.getAnnotation(A2Class.class);
		if (b2c == null)
			return false;
		boolean ret = (b2c.type().equals(CLIENT));
		return ret;
	}

	public static final boolean isData(Class<?> c) {
		A2Class b2c = c.getAnnotation(A2Class.class);
		if (b2c == null)
			return false;
		boolean ret = (b2c.type().equals(DATA));
		return ret;
	}

	public static final boolean isConstant(Class<?> c) {
		A2Class b2c = c.getAnnotation(A2Class.class);
		if (b2c == null)
			return false;
		return b2c.constant();
	}

	public static final boolean isServer(Method m) {
		A2Method b2f = m.getAnnotation(A2Method.class);
		if (b2f == null)
			return false;
		boolean ret = (b2f.type().equals(SERVER));
		return ret;
	}

	public static final boolean isClient(Method m) {
		A2Method b2f = m.getAnnotation(A2Method.class);
		if (b2f == null)
			return false;
		boolean ret = (b2f.type().equals(CLIENT));
		return ret;
	}

	// ///////////////////////////////////////////////////////////////
	public static String getRemark(Method m) {
		A2Method b2m = m.getAnnotation(A2Method.class);
		return b2m.remark();
	}

	public static String getOType(Method m, String val) {
		A2Method b2m = m.getAnnotation(A2Method.class);
		String[] params = b2m.params();
		int i = 0;
		for (; i < params.length; i++) {
			if (params[i].equals(val)) {
				break;
			}
		}
		Annotation[][] annotations = m.getParameterAnnotations();
		A2Param b2p = (A2Param) annotations[i][0];
		return b2p.oType();
	}

	public static boolean isOut(Method m, String val) {
		A2Method b2m = m.getAnnotation(A2Method.class);
		String[] params = b2m.params();
		int i = 0;
		for (; i < params.length; i++) {
			if (params[i].equals(val)) {
				break;
			}
		}
		Annotation[][] annotations = m.getParameterAnnotations();
		if (annotations[i].length < 1)
			return false;
		A2Param b2p = (A2Param) annotations[i][0];
		return b2p.isOut();
	}

	public static final String getFieldRemark(Field f) {
		A2Field b2f = f.getAnnotation(A2Field.class);
		if (b2f == null)
			return null;
		return b2f.remark();
	}

	public static final String getFieldType(Field f) {
		if (f == null)
			return "";
		String r = f.getClass().getSimpleName();
		if (r.equals("void")) {
			return "";
		} else if (r.contains("boolean") || r.contains("Boolean")) {
			return "return true;";
		} else if (r.contains("byte") || r.contains("Byte")) {
			return "return 0;";
		} else if (r.contains("short") || r.contains("Short")) {
			return "return 0;";
		} else if (r.contains("int") || r.contains("Integer")) {
			return "return 0;";
		} else if (r.contains("long") || r.contains("Long")) {
			return "return 0L;";
		} else if (r.contains("float") || r.contains("Float")) {
			return "return 0.0;";
		} else if (r.contains("double") || r.contains("Double")) {
			return "return 0.0;";
		} else if (r.contains("String")) {
			return "return \"\";";
		} else if (r.contains("Map") || r.contains("Hashtable")) {
			return "return new NewMap();";
		} else if (r.contains("List") || r.contains("Vector")) {
			return "return new NewList();";
		}
		return r;
	}

	public static final String getReturnType(Method m) {
		if (m == null)
			return "";
		Class<?> rtype = m.getReturnType();
		String srtype = rtype.getSimpleName();
		return srtype;
	}

	public static final String getReturnVal(Method m) {
		if (m == null)
			return "";
		Class<?> rtype = m.getReturnType();
		String r = rtype.getSimpleName();
		if (r.equals("void")) {
			return "";
		} else if (r.contains("boolean") || r.contains("Boolean")) {
			return "return true;";
		} else if (r.contains("byte") || r.contains("Byte")) {
			return "return 0;";
		} else if (r.contains("short") || r.contains("Short")) {
			return "return 0;";
		} else if (r.contains("int") || r.contains("Integer")) {
			return "return 0;";
		} else if (r.contains("long") || r.contains("Long")) {
			return "return 0L;";
		} else if (r.contains("float") || r.contains("Float")) {
			return "return 0.0;";
		} else if (r.contains("double") || r.contains("Double")) {
			return "return 0.0;";
		} else if (r.contains("String")) {
			return "return \"\";";
		} else if (r.contains("Map") || r.contains("Hashtable")) {
			return "return new NewMap();";
		} else if (r.contains("List") || r.contains("Vector")) {
			return "return new NewList();";
		}
		return r;
	}

	public static final String getMapType(String r) {
		if (r.equals("void")) {
			return "";
		} else if (r.equals("boolean") || r.equals("Boolean")) {
			return "getBoolean";
		} else if (r.equals("byte") || r.equals("Byte")) {
			return "getByte";
		} else if (r.equals("short") || r.equals("Short")) {
			return "getShort";
		} else if (r.equals("int") || r.equals("Integer")) {
			return "getInt";
		} else if (r.equals("long") || r.equals("Long")) {
			return "getLong";
		} else if (r.equals("float") || r.equals("Float")) {
			return "getFloat";
		} else if (r.equals("double") || r.equals("Double")) {
			return "getDouble";
		} else if (r.equals("String")) {
			return "getString";
		} else if (r.equals("Map") || r.equals("Hashtable")
				|| r.equals("HashMap") || r.equals("NewMap")) {
			return "getMap";
		} else if (r.equals("List") || r.equals("Vector")
				|| r.equals("ArrayList") || r.equals("NewList")) {
			return "getList";
		}
		return "getObject";
	}

	public static final String getCsMapType(String r) {
		if (r.equals("void")) {
			return "";
		} else if (r.contains("bool") || r.contains("Boolean")) {
			return "getBool";
		} else if (r.contains("byte") || r.contains("Byte")) {
			return "getByte";
		} else if (r.contains("short") || r.contains("Short")) {
			return "getShort";
		} else if (r.contains("int") || r.contains("Integer")) {
			return "getInt";
		} else if (r.contains("long") || r.contains("Long")) {
			return "getLong";
		} else if (r.contains("float") || r.contains("Float")) {
			return "getFloat";
		} else if (r.contains("double") || r.contains("Double")) {
			return "getDouble";
		} else if (r.contains("string") || r.contains("String")) {
			return "getString";
		} else if (r.contains("Map") || r.contains("Hashtable")) {
			return "getMap";
		} else if (r.contains("List") || r.contains("Vector")) {
			return "getList";
		}
		return "getObject";
	}

	public static final String getNethodName(Method m) {
		if (m == null)
			return "";
		String ret = m.getName();
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static final NewList<NewMap<String, String>> getParameters(Method m) {
		if (m == null)
			return new NewList<NewMap<String, String>>();
		Class<?>[] types = m.getParameterTypes();
		A2Method b2m = m.getAnnotation(A2Method.class);
		String[] params = b2m.params();
		NewList<NewMap<String, String>> ret = new NewList<NewMap<String, String>>();
		for (int i = 0; i < types.length; i++) {
			String type = types[i].getSimpleName();
			String value = params[i];
			ret.add(NewMap.create().putE(type, value));
		}

		return ret;
	}

	public static final String getRemark(Field f) {
		A2Field a = f.getAnnotation(A2Field.class);
		if (a == null)
			return "";
		String remark = a.remark();
		if (!remark.isEmpty())
			remark = "// " + remark;
		return remark;
	}

	public static final String getDef(Field f) {
		A2Field a = f.getAnnotation(A2Field.class);
		if (a == null)
			return "";
		return a.def();
	}

	public static final String getType(Field f) {
		if (f == null)
			return "";
		Class<?> rtype = f.getType();
		String r = rtype.getSimpleName();
		if (r.equals("void")) {
			return "";
		} else if (r.contains("byte[]") || r.contains("[B")) {
			return "byte[]";
		} else if (r.contains("boolean") || r.contains("Boolean")) {
			return "boolean";
		} else if (r.contains("byte") || r.contains("Byte")) {
			return "byte";
		} else if (r.contains("short") || r.contains("Short")) {
			return "short";
		} else if (r.contains("int") || r.contains("Integer")) {
			return "int";
		} else if (r.contains("long") || r.contains("Long")) {
			return "long";
		} else if (r.contains("float") || r.contains("Float")) {
			return "float";
		} else if (r.contains("double") || r.contains("Double")) {
			return "double";
		} else if (r.contains("String") || r.contains("string")) {
			return "String";
		} else if (r.contains("Map") || r.contains("Hashtable")) {
			return "Map";
		} else if (r.contains("List") || r.contains("Vector")) {
			return "List";
		}
		return r;
	}

	public static final String getJType(Field f) {
		if (f == null)
			return "";
		Class<?> rtype = f.getType();
		String r = rtype.getSimpleName();
		if (r.equals("void")) {
			return "";
		} else if (r.contains("byte[]") || r.contains("[B")) {
			return "byte[]";
		} else if (r.contains("boolean") || r.contains("Boolean")) {
			return "Boolean";
		} else if (r.contains("byte") || r.contains("Byte")) {
			return "Byte";
		} else if (r.contains("short") || r.contains("Short")) {
			return "Short";
		} else if (r.contains("int") || r.contains("Integer")) {
			return "Integer";
		} else if (r.contains("long") || r.contains("Long")) {
			return "Long";
		} else if (r.contains("float") || r.contains("Float")) {
			return "Float";
		} else if (r.contains("double") || r.contains("Double")) {
			return "Double";
		} else if (r.contains("String")) {
			return "String";
		} else if (r.contains("Map") || r.contains("Hashtable")) {
			return "Map";
		} else if (r.contains("List") || r.contains("Vector")) {
			return "List";
		}
		return r;
	}

	public static final String getCsType(Field f) {
		if (f == null)
			return "";
		Class<?> rtype = f.getType();
		String r = rtype.getSimpleName();
		if (r.equals("void")) {
			return "";
		} else if (r.contains("byte[]") || r.contains("[B")) {
			return "byte[]";
		} else if (r.contains("boolean") || r.contains("Boolean")) {
			return "bool";
		} else if (r.contains("byte") || r.contains("Byte")) {
			return "byte";
		} else if (r.contains("short") || r.contains("Short")) {
			return "short";
		} else if (r.contains("int") || r.contains("Integer")) {
			return "int";
		} else if (r.contains("long") || r.contains("Long")) {
			return "long";
		} else if (r.contains("float") || r.contains("Float")) {
			return "float";
		} else if (r.contains("double") || r.contains("Double")) {
			return "double";
		} else if (r.contains("String") || r.contains("string")) {
			return "string";
		} else if (r.contains("Map") || r.contains("Hashtable")) {
			return "Hashtable";
		} else if (r.contains("List") || r.contains("Vector")) {
			return "ArrayList";
		}
		return r;
	}

	public static final String getListType(Field f) {
		ParameterizedType pt = (ParameterizedType) f.getGenericType();
		if (pt.getActualTypeArguments().length <= 0)
			return "";
		Type type = pt.getActualTypeArguments()[0];
		String str = type.toString();
		if (str.equals("boolean") || str.contains(".Boolean"))
			return "Boolean";
		else if (str.equals("byte[]") || str.contains("[B"))
			return "byte[]";
		else if (str.equals("byte") || str.contains(".Byte"))
			return "Byte";
		else if (str.equals("short") || str.contains(".Short"))
			return "Short";
		else if (str.equals("int") || str.contains(".Integer"))
			return "Integer";
		else if (str.equals("long") || str.contains(".Long"))
			return "Long";
		else if (str.equals("float") || str.contains(".Float"))
			return "Float";
		else if (str.equals("double") || str.contains(".Double"))
			return "Double";
		else if (str.equals("String") || str.equals("class java.lang.String"))
			return "String";

		int pos = str.indexOf("$");
		return str.substring(pos + 1, str.length());
	}

	public static final boolean isBType(String str) {
		if (str.equals("void")) {
			return true;
		} else if (str.equals("byte[]")) {
			return true;
		} else if (str.equals("boolean") || str.contains("Boolean")) {
			return true;
		} else if (str.equals("byte") || str.contains("Byte")) {
			return true;
		} else if (str.equals("short") || str.contains("Short")) {
			return true;
		} else if (str.equals("int") || str.contains("Integer")) {
			return true;
		} else if (str.equals("long") || str.contains("Long")) {
			return true;
		} else if (str.equals("float") || str.contains("Float")) {
			return true;
		} else if (str.equals("double") || str.contains("Double")) {
			return true;
		} else if (str.equals("String") || str.equals("class java.lang.String")){
			return true;
		}
		return false;
	}

	public static final String getBioW(Field f) {
		if (f == null)
			return "";
		Class<?> rtype = f.getType();
		String r = rtype.getSimpleName();
		return getBioW(r);
	}

	public static final String getBioW(String r) {
		if (r == null)
			return "";
		if (r.equals("void")) {
			return "";
		} else if (r.equals("byte[]") || r.contains("[B")) {
			return "writeBytes";
		} else if (r.equals("boolean") || r.contains("Boolean")) {
			return "writeBool";
		} else if (r.equals("byte") || r.contains("Byte")) {
			return "writeByte";
		} else if (r.equals("short") || r.contains("Short")) {
			return "writeShort";
		} else if (r.equals("int") || r.contains("Integer")) {
			return "writeInt";
		} else if (r.equals("long") || r.contains("Long")) {
			return "writeLong";
		} else if (r.equals("float") || r.contains("Float")) {
			return "writeDouble";
		} else if (r.equals("double") || r.contains("Double")) {
			return "writeDouble";
		} else if (r.equals("String")) {
			return "writeString";
		} else if (r.equals("Map") || r.contains("Hashtable")) {
			return "writeMap";
		} else if (r.equals("List") || r.contains("Vector")) {
			return "writeList";
		}
		return r + "";
	}

	public static final String getBioR(Field f) {
		if (f == null)
			return "";
		Class<?> rtype = f.getType();
		String r = rtype.getSimpleName();
		return getBioR(r);
	}

	public static final String getBioR(String r) {
		if (r == null)
			return "";
		if (r.equals("void")) {
			return "";
		} else if (r.equals("byte[]") || r.contains("[B")) {
			return "readBytes";
		} else if (r.equals("boolean") || r.contains("Boolean")) {
			return "readBool";
		} else if (r.equals("byte") || r.contains("Byte")) {
			return "readByte";
		} else if (r.equals("short") || r.contains("Short")) {
			return "readShort";
		} else if (r.equals("int") || r.contains("Integer")) {
			return "readInt";
		} else if (r.equals("long") || r.contains("Long")) {
			return "readLong";
		} else if (r.equals("float") || r.contains("Float")) {
			return "readDouble";
		} else if (r.equals("double") || r.contains("Double")) {
			return "readDouble";
		} else if (r.equals("String")) {
			return "readString";
		} else if (r.equals("Map") || r.contains("Hashtable")) {
			return "readMap";
		} else if (r.equals("List") || r.contains("Vector")) {
			return "readList";
		}
		return r + ".readInstance";
	}

	public static final String getGval(Field f) {
		if (f == null)
			return "";
		Class<?> rtype = f.getType();
		String r = rtype.getSimpleName();
		return getGval(r);
	}

	public static final String getGval(String r) {
		if (r == null || r.isEmpty())
			return "";
		if (r.equals("void")) {
			return "";
		} else if (r.equals("byte[]") || r.contains("[B")) {
			return "bytesVal";
		} else if (r.equals("boolean") || r.contains("Boolean")) {
			return "boolVal";
		} else if (r.equals("byte") || r.contains("Byte")) {
			return "byteVal";
		} else if (r.equals("short") || r.contains("Short")) {
			return "shortVal";
		} else if (r.equals("int") || r.contains("Integer")) {
			return "intVal";
		} else if (r.equals("long") || r.contains("Long")) {
			return "longVal";
		} else if (r.equals("float") || r.contains("Float")) {
			return "doubleVal";
		} else if (r.equals("double") || r.contains("Double")) {
			return "doubleVal";
		} else if (r.contains("String") || r.contains("string")) {
			return "stringVal";
		} else if (r.equals("Map") || r.contains("Hashtable")) {
			return "map";
		} else if (r.equals("List") || r.contains("Vector")) {
			return "list";
		}
		return r + "._G";
	}
}
