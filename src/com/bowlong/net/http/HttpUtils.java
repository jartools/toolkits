package com.bowlong.net.http;

import java.io.IOException;
import java.net.URI;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.servlet.ServletInputStream;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("all")
public class HttpUtils {
	public HttpUtils() {
	}

	static public Hashtable parseQueryString(String s) {

		String valArray[] = null;

		if (s == null) {
			throw new IllegalArgumentException();
		}
		Hashtable ht = new Hashtable();
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(s, "&");
		while (st.hasMoreTokens()) {
			String pair = (String) st.nextToken();
			int pos = pair.indexOf('=');
			if (pos == -1) {
				throw new IllegalArgumentException();
			}
			String key = parseName(pair.substring(0, pos), sb);
			String val = parseName(pair.substring(pos + 1, pair.length()), sb);
			if (ht.containsKey(key)) {
				String oldVals[] = (String[]) ht.get(key);
				valArray = new String[oldVals.length + 1];
				for (int i = 0; i < oldVals.length; i++)
					valArray[i] = oldVals[i];
				valArray[oldVals.length] = val;
			} else {
				valArray = new String[1];
				valArray[0] = val;
			}
			ht.put(key, valArray);
		}
		return ht;
	}

	static public Hashtable parsePostData(int len, ServletInputStream in) {
		if (len <= 0) {
			return new Hashtable(); // cheap hack to return an empty hash
		}

		if (in == null) {
			throw new IllegalArgumentException();
		}

		//
		// Make sure we read the entire POSTed body.
		//
		byte[] postedBytes = new byte[len];
		try {
			int offset = 0;

			do {
				int inputLen = in.read(postedBytes, offset, len - offset);
				if (inputLen <= 0) {
					String msg = "err.io.short_read";
					throw new IllegalArgumentException(msg);
				}
				offset += inputLen;
			} while ((len - offset) > 0);

		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		try {
			String postedBody = new String(postedBytes, 0, len, "8859_1");
			return parseQueryString(postedBody);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	static private String parseName(String s, StringBuffer sb) {
		sb.setLength(0);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				break;
			case '%':
				try {
					sb.append((char) Integer.parseInt(
							s.substring(i + 1, i + 3), 16));
					i += 2;
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

	public static URI getRequestURL(HttpExchange req) {
		return req.getRequestURI();
	}
}
