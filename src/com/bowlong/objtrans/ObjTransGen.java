package com.bowlong.objtrans;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bowlong.util.StrBuilder;

public class ObjTransGen {

	public static boolean isPublic(java.lang.reflect.Field f) {
		String s = f.toString();
		return s.startsWith("public");
	}

	public static boolean isPrivate(java.lang.reflect.Field f) {
		String s = f.toString();
		return s.startsWith("private");
	}

	public static void main(String[] args) {
		// Field[] fs = BeanUtils.getFields(C1.class);
		// for (Field f : fs) {
		// System.out.println(isPublic(f) + "  " + f.getName() + " "+
		// f.getGenericType());
		// }
		// Map<String, MyField> m1 = MyField.createOnlyPublic(C1.class);
		System.out.println(gen(C1.class, C2.class));

	}

	public static String gen(Class<?> c1, Class<?> c2) {
		StrBuilder sb = StrBuilder.builder();

		Map<String, MyField> m1 = MyField.createWithPublic(c1);
		Map<String, MyField> m2 = MyField.createWithPublic(c2);
		
		sb.pn("public static ${2} convert${1}to${2}(${1} _o1, ${2} _o2) {",
				c1.getSimpleName(), c2.getSimpleName());
		sb.pn("    if(_o1 == null || _o2 == null) return null;");
		Set<Entry<String, MyField>> ets = m1.entrySet();
		for (Entry<String, MyField> en : ets) {
			MyField f2 = m2.get(en.getKey());
			if (f2 == null)
				continue;
			if (!en.getValue().type.equals(f2.type))
				continue;
			sb.pn("    _o2.${1} = _o1.${1};", en.getKey());
		}
		sb.pn("    return _o2;");
		sb.pn("}");

		sb.pn("public static final ${2} convert${1}to${2}(${1} _o1) {",
				c1.getSimpleName(), c2.getSimpleName());
		sb.pn("    if(_o1 == null) return null;");
		sb.pn("    return convert${1}to${2}(_o1, new ${2}());", c1.getSimpleName(), c2.getSimpleName());
		sb.pn("}");
		return sb.str();
	}

	
}
