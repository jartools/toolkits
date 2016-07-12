package com.bowlong.third.assist;

import java.util.List;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import com.bowlong.util.NewList;
import com.bowlong.util.NewMap;

@SuppressWarnings("unchecked")
public class JavaAssist {
	static ClassPool pool = ClassPool.getDefault();

	public static Map<String, List<Map<String, String>>> getMethods(
			Class<?> clazz) {
		Map<String, List<Map<String, String>>> r2 = new NewMap<String, List<Map<String, String>>>();
		try {
			CtClass cc = pool.get(clazz.getName());

			CtMethod[] methods = cc.getDeclaredMethods();
			for (CtMethod cm : methods) {
				r2.put(cm.getName(), getParamters(cc, cm));
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return r2;
	}

	public static List<Map<String, String>> getParamters(Class<?> clazz,
			String methodName) throws NotFoundException {
		CtClass cc = pool.get(clazz.getName());
		CtMethod cm = cc.getDeclaredMethod(methodName);
		return getParamters(cc, cm);
	}

	public static List<Map<String, String>> getParamters(CtClass cc, CtMethod cm)
			throws NotFoundException {
		List<Map<String, String>> r2 = new NewList<Map<String, String>>();
		try {

			// 使用javaassist的反射方法获取方法的参数名
			MethodInfo methodInfo = cm.getMethodInfo();
			CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
			LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
					.getAttribute(LocalVariableAttribute.tag);
			if (attr == null) {
				return r2;
			}
			String[] paramNames = new String[cm.getParameterTypes().length];
			int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
			for (int i = 0; i < paramNames.length; i++)
				paramNames[i] = attr.variableName(i + pos);
			// paramNames即参数名
			for (int i = 0; i < paramNames.length; i++) {
				String _t = cm.getParameterTypes()[i].getName();
				String _n = paramNames[i];
				r2.add(NewMap.newly(_t, _n));
			}
		} catch (Exception e) {
		}

		return r2;
	}

	public static List<String> paramters(Class<?> clazz, String methodName) {
		try {

			CtClass cc = pool.get(clazz.getName());
			CtMethod cm = cc.getDeclaredMethod(methodName);
			return paramters(cc, cm);
		} catch (Exception e) {
		}
		return new NewList<String>();
	}

	public static List<String> paramters(CtClass cc, CtMethod cm)
			throws NotFoundException {
		List<String> r2 = new NewList<String>();
		try {

			// 使用javaassist的反射方法获取方法的参数名
			MethodInfo methodInfo = cm.getMethodInfo();
			CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
			LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
					.getAttribute(LocalVariableAttribute.tag);
			if (attr == null) {
				return r2;
			}
			String[] paramNames = new String[cm.getParameterTypes().length];
			int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
			for (int i = 0; i < paramNames.length; i++)
				paramNames[i] = attr.variableName(i + pos);
			// paramNames即参数名
			for (int i = 0; i < paramNames.length; i++) {
				String _n = paramNames[i];
				r2.add(_n);
			}
		} catch (Exception e) {
		}

		return r2;
	}

	public static void p() {

	}

	public static void main(String[] args) {
		System.out.println(getMethods(JavaAssist.class));
	}
}
