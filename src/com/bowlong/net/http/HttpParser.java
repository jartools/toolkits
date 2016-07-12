package com.bowlong.net.http;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class HttpParser {

	public static Map<String, List<String>> parse(byte[] b, boolean isMutipart,
			byte[] boundary, String charset) {
		Map<String, List<String>> ret = new Hashtable<String, List<String>>();
		try {
			if (b == null)
				return ret;
			if (isMutipart) {
				if (boundary == null || boundary.length <= 0)
					return ret;

				return parseMutiPart(b, boundary, charset);
			} else {
				return parseQueryString(b, charset);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ret;
		}
	}

	public static Map<String, List<String>> parseQueryString(byte[] b,
			String charset) throws Exception {
		String qs = new String(b);
		return parseQueryString(qs, charset);
	}

	public static Map<String, List<String>> parseQueryString(String qs,
			String charset) {
		Map<String, List<String>> ret = new Hashtable<String, List<String>>();
		try {
			String s = URLDecoder.decode(qs, charset);
			String valArray[] = null;
			if (s == null)
				throw new IllegalArgumentException();
			Hashtable<String, Object> ht = new Hashtable<String, Object>();
			StringBuffer sb = new StringBuffer();
			String key;
			for (StringTokenizer st = new StringTokenizer(s, "&"); st
					.hasMoreTokens(); ht.put(key, valArray)) {
				String pair = st.nextToken();
				int pos = pair.indexOf('=');
				if (pos == -1)
					throw new IllegalArgumentException();
				key = parseName(pair.substring(0, pos), sb);
				String val = parseName(pair.substring(pos + 1, pair.length()),
						sb);
				if (ht.containsKey(key)) {
					String oldVals[] = (String[]) (String[]) ht.get(key);
					valArray = new String[oldVals.length + 1];
					for (int i = 0; i < oldVals.length; i++)
						valArray[i] = oldVals[i];

					valArray[oldVals.length] = val;
				} else {
					valArray = new String[1];
					valArray[0] = val;
				}
			}

			Enumeration<String> keys = ht.keys();
			int times = 1000;
			while (keys.hasMoreElements()) {
				if(times-- <= 0)
					break;
				String k = keys.nextElement();
				Object o = ht.get(k);
				List<String> var = new Vector<String>();
				if (o instanceof String[]) {
					String[] ss = (String[]) o;
					for (String v : ss) {
						var.add(v);
					}
				} else {
					var.add((String) o);
				}
				ret.put(k, var);
			}
		} catch (Exception e) {
		}

		return ret;
	}

	private static String parseName(String s, StringBuffer sb) {
		sb.setLength(0);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case 43: // '+'
				sb.append(' ');
				break;

			case 37: // '%'
				try {
					sb.append((char) Integer.parseInt(
							s.substring(i + 1, i + 3), 16));
					i += 2;
					break;
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException();
				} catch (StringIndexOutOfBoundsException e) {
					String rest = s.substring(i);
					sb.append(rest);
					if (rest.length() == 2)
						i++;
				}
				break;

			default:
				sb.append(c);
				break;
			}
		}

		return sb.toString();
	}

	/**
	 * str=str str=Map str=str Map result {key=value, key={name=filename,
	 * bin=[byte[]}, key=value} str=str str=byte[]
	 * 
	 * 例如:{btn=提交查询内容, b={name=D:\Visual_Studio_2005_cn_sn.txt, bin=[B@19f953d},
	 * a=xxx}
	 */
	public static Map parseMutiPart(byte[] buf, byte[] boundary, String charset) {
		Map result = new HashMap();
		try {

			List Form = HexSplit(buf, boundary);
			for (int i = 0; i < Form.size(); i++) {
				String szName = null;
				String szFileName = null;
				String szValue = null;

				byte[] part = (byte[]) Form.get(i);
				byte[] name = HexBetween(part, _NAME_BEGIN, _NAME_END);
				byte[] filename = HexBetween(part, _FILENAME_BEGIN,
						_FILENAME_END);
				byte[] bin = null;
				int varBegin = HexIndexOf(part, _DATA_BEGIN, -1);
				int varEnd = part.length - 4;

				if (name == null || name.length < 1)
					continue;

				if (varBegin < 0)
					continue;

				varBegin = varBegin + 4;
				bin = new byte[varEnd - varBegin];

				System.arraycopy(part, varBegin, bin, 0, bin.length);
				if (name != null) {
					szName = new String(name, charset);
				}
				if (filename != null) {
					szFileName = new String(filename, charset);
					Map filePart = new HashMap();
					filePart.put("name", szFileName);
					filePart.put("bin", bin);

					result.put(szName, filePart);
				} else {
					szValue = new String(bin, charset);

					Object Exist = result.get(szName);

					if (Exist == null) {
						result.put(szName, szValue);
					} else {
						if (Exist instanceof String) {
							String[] nv = new String[2];
							nv[0] = (String) Exist;
							nv[1] = szValue;
							result.put(szName, nv);
						} else {
							String[] ov = (String[]) Exist;
							String[] nv = new String[ov.length + 1];
							for (int j = 0; j < ov.length; j++) {
								nv[j] = ov[j];
							}
							nv[ov.length] = szValue;
							result.put(szName, nv);
						}
					}
				}
			}
		} catch (Exception e) {
			result = new HashMap();
			e.printStackTrace();
		}

		Map<String, List<String>> ret = new Hashtable<String, List<String>>();
		Iterator<String> keys = result.keySet().iterator();
		int times = 1000;
		while (keys.hasNext()) {
			if(times-- <= 0)
				break;
			String k = keys.next();
			Object o = result.get(k);
			List<String> var = new Vector<String>();
			if (o instanceof String[]) {
				String[] ss = (String[]) o;
				for (String v : ss) {
					var.add(v);
				}
			} else {
				var.add((String) o);
			}
			ret.put(k, var);
		}

		return ret;
	}

	// ///////////////////////////////////////////////////////////////
	private static byte[] _DATA_BEGIN = { '\r', '\n', '\r', '\n' };
	private static byte[] _NAME_BEGIN = { 'n', 'a', 'm', 'e', '=', '\"' };
	private static byte[] _NAME_END = { '\"' };
	private static byte[] _FILENAME_BEGIN = { 'f', 'i', 'l', 'e', 'n', 'a',
			'm', 'e', '=', '\"' };
	private static byte[] _FILENAME_END = { '\"' };

	// ///////////////////////////////////////////////////////////////
	private static List HexSplit(byte[] b, byte[] spline) {
		List ret = new ArrayList();

		int old = 0;
		int pos = -1;

		int times = 1000;
		while (true) {
			if(times-- <= 0)
				break;
			pos = HexIndexOf(b, spline, old);
			if ((pos < 0) || (pos < old))
				break;

			if (pos == 0) {
				old = pos + spline.length;
				continue;
			}

			byte[] dest = new byte[pos - old];
			System.arraycopy(b, old, dest, 0, dest.length);
			ret.add(dest);

			old = pos + spline.length;
		}

		return ret;
	}

	private static int HexIndexOf(byte[] b, byte[] sp, int start) {
		int len = b.length;
		int size = sp.length;
		int count = 0;
		start = start < 0 ? 0 : start;
		for (int i = start; i < len - size; i++) {
			count = 0;
			for (int j = 0; j < size; j++)
				if (b[i + j] == sp[j])
					count++;

			if (count >= size)
				return i;
		}

		return -1;
	}

	private static byte[] HexBetween(byte[] src, byte[] begin, byte[] end) {
		int b = -1, e = -1;
		b = HexIndexOf(src, begin, -1);
		if (b < 0)
			return null;

		e = HexIndexOf(src, end, b + begin.length);
		if (b < 0)
			return null;

		b = b + begin.length;

		byte[] result = new byte[e - b];
		System.arraycopy(src, b, result, 0, result.length);

		return result;
	}

}