package com.bowlong.net.proto.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

import com.bowlong.util.NewList;
import com.bowlong.util.NewMap;
import com.bowlong.util.StrBuilder;

@SuppressWarnings({ "unused", "resource" })
public class Bio2GCSharpNative {

	/**
	 * @param args
	 * @return
	 */
	public static String b2g(Class<?> c, boolean src) {
		B2Class B2C = c.getAnnotation(B2Class.class);
		String namespace = "";
		if (B2C != null) {
			namespace = B2C.namespace();
		}

		// String p = "gen_b2g";
		String p = (src ? "src/" : "") + "gen_b2g";
		if (namespace != null && !namespace.isEmpty()) {
			p = p + "/" + namespace;
		}
		File path = new File(p);
		if (!path.exists())
			path.mkdirs();

		Class<?>[] classes = c.getDeclaredClasses();
		StrBuilder sb = new StrBuilder();

		sb.pn("using System;");
		sb.pn("using System.Collections;");
		sb.pn("using Toolkit;");
		sb.pn("");

		sb.pn("namespace ${1} {", c.getSimpleName());
		sb.pn("");

		for (Class<?> class1 : classes) {
			String sname = class1.getSimpleName();
			if (B2G.isServer(class1)) {
				g2s_call(class1, namespace, sb);
			}
		}

		String sname = c.getSimpleName();
		sb.pn("}");

		writeFile(p + "/CallNative" + sname + ".cs", sb.toString());

		System.out.println(sb);
		return sb.toString();
	}

	// 生成客户端接口
	public static void g2s_call(Class<?> c, String namespace, StrBuilder sb) {
		String sname = c.getSimpleName();
		Method[] methods = c.getMethods();
		String cname = c.getSimpleName();
		sb.pn("public abstract class CallNative${1} {", cname);

		StrBuilder sb2 = new StrBuilder();
		for (Method m : methods) {
			String rtype = B2G.getReturnType(m);
			if (B2G.isServer(m) && rtype.equals("void"))
				continue;

			String mname = B2G.getNethodName(m);
			int hmname = mname.hashCode();
			sb2.ap("${1},", hmname);
		}
		sb2.removeRight(1);
		String s = sb2.toString();

		sb.pn("");
		sb.pn("    public static NewSet CMD = NewSet.create(${1});", s);
		sb.pn("");

		sb.pn("    // //////////////////////////////////////////////");
		sb.pn("    // 逻辑分发");
		sb.pn("    // //////////////////////////////////////////////");
		sb.pn("");
		sb.pn("    public void disp(int cmd, int succ, string msg, object attach) {");
		sb.pn("        switch (cmd) {");
		for (Method m : methods) {
			String remark = B2G.getRemark(m);
			String srtype = B2G.getReturnType(m);
			String mname = B2G.getNethodName(m);
			int hmname = mname.hashCode();
			if (B2G.isServer(m)) {
				if (!srtype.equals("void")) {
					sb.pn("            case ${1}: { //  ${2}", hmname, remark);
					sb.pn("                on${1}(cmd, succ, msg, attach);", upper1(mname));
					sb.pn("                return;");
					sb.pn("            }");
				}
			}
		}
		sb.pn("        }");
		sb.pn("        throw new Exception(\" cmd: \" + cmd +  \" not found processor.\");");
		sb.pn("    }");
		sb.pn("");
		sb.pn("");
		sb.pn("    // //////////////////////////////////////////////");
		sb.pn("    // 需要实现的接口");
		sb.pn("    // //////////////////////////////////////////////");
		sb.pn("");
		sb.pn("    public abstract void OnExcept(Exception e, int stat, string msg);");
		for (Method m : methods) {
			String remark = B2G.getRemark(m);
			// String oType = B2G.getOType(m);
			String srtype = B2G.getReturnType(m);
			String mname = B2G.getNethodName(m);
			NewList<NewMap<String, String>> params = B2G.getParameters(m);

			// 解析参数函数
			sb.pn("    // ${1}", remark);
			if (B2G.isServer(m)) {
				if (!srtype.equals("void")) {
					StrBuilder msb = new StrBuilder();
					for (NewMap<String, String> m1 : params) {
						String key = (String) m1.getKey();
						String val = (String) m1.getValue();
						String hval = val.hashCode() + "";
						String p = B2G.getMapType(key);
						boolean isOut = B2G.isOut(m, val);
						if (isOut) {
							if (p.equals("getObject")) {
								msb.ap("${1} ${2}, ", key, val);
							}
						}
					}

					sb.pn("    public abstract void on${1}(int cmd, int succ, string msg, object attach);",
							upper1(mname), msb, srtype);
				}
			}
			sb.pn("");
		}
		sb.pn("    }");
//		sb.pn("}");
	}

	public static String upper1(String s) {
		if (s == null || s.isEmpty())
			return s;
		int len = s.length();
		return s.substring(0, 1).toUpperCase() + s.substring(1, len);
	}

	public static void writeFile(String f, String str) {
		try (FileOutputStream out = new FileOutputStream(new File(f));
				OutputStreamWriter osw = new OutputStreamWriter(out,
						Charset.forName("UTF8"));) {
			osw.write(str, 0, str.length());
			osw.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
