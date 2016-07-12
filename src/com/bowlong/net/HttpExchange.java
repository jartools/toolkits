package com.bowlong.net;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpUtils;

import com.bowlong.lang.NumEx;
import com.bowlong.net.ReUsableSocket;
import com.bowlong.third.servlet.UISupport;

@SuppressWarnings("deprecation")
public class HttpExchange {
	public static final String KeepAlive = "Keep-Alive";
	public static final String Close = "Close";

	private final ReUsableSocket socket;

	protected BufferedReader reader() {
		return socket.reader();
	}

	protected BufferedWriter writer() {
		return socket.writer();
	}

	protected InputStream in() {
		return socket.in();
	}

	protected OutputStream out() {
		return socket.out();
	}

	public HttpExchange(ReUsableSocket socket) {
		this.socket = socket;

		this.responseHeaders.put("Connection", KeepAlive);
		this.responseHeaders.put("Server", "x");
		this.responseHeaders.put("Content-Type", "text/html;charset=utf-8");

		parseCommandLine();
	}

	private List<String> requestBuffer = new Vector<String>();

	private String protocal = null;
	private String method = null;
	private String requestUrl = null;

	public boolean isHttpRequest() {
		parseCommandLine();
		return protocal != null && method != null && requestUrl != null;
	}

	private Map<String, String> requestHeaders = null;
	private Map<String, String> requestParams = null;

	// GET /search?hl=en&q=HTTP&btnG=Google+Search HTTP/1.1
	// Host: www.google.com
	// User-Agent: Mozilla/5.0 Galeon/1.2.0 (X11; Linux i686; U;) Gecko/20020326
	// Accept: text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,
	// text/plain;q=0.8, video/x-mng,image/png,image/jpeg,image/gif;q=0.2,
	// text/css,*/*;q=0.1
	// Accept-Language: en
	// Accept-Encoding: gzip, deflate, compress;q=0.9
	// Accept-Charset: ISO-8859-1, utf-8;q=0.66, *;q=0.66
	// Keep-Alive: 300
	// Connection: keep-alive

	public int processRequest() throws IOException {
		requestBuffer.clear();
		this.protocal = null;
		this.method = null;
		this.requestUrl = null;
		if (requestHeaders != null && !requestHeaders.isEmpty())
			requestHeaders.clear();
		if (requestParams != null && !requestParams.isEmpty())
			requestParams.clear();

		BufferedReader r = reader();
		int i = 0;
		for (i = 0; i < 1000; i++) {
			String li = r.readLine();
			if (li == null || li.isEmpty())
				break;
			requestBuffer.add(li);
		}
		return i;
	}

	public boolean isKeepAlive() {
		String Connection = getRequestHeader(UISupport.Connection);
		return Connection.trim().toLowerCase().equals(KeepAlive.toLowerCase());
	}

	private synchronized void parseCommandLine() {
		try {
			// System.out.println(socket + "   " + socket.getSocket());

			if (protocal != null && method != null && requestUrl != null) {
				return;
			}
			if (requestBuffer.isEmpty())
				return;

			String cstr = requestBuffer.get(0);
			if (cstr == null || cstr.isEmpty()) {
				System.out.println("cstr null.");
				return;
			}
			// System.out.println("command line:" + cstr);
			String[] ss = cstr.split(" ");
			if (ss == null || ss.length != 3)
				return;
			this.method = ss[0];
			this.requestUrl = ss[1];
			this.protocal = ss[2];
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private synchronized Map<String, String> parseRequestHeaders() {
		if (requestHeaders != null && !requestHeaders.isEmpty())
			return requestHeaders;

		try {
			requestHeaders = new Hashtable<>();
			if (requestBuffer.isEmpty())
				return requestHeaders;

			parseCommandLine();

			int size = requestBuffer.size();
			for (int i = 1; i < 100; i++) {
				if (i >= size)
					break;
				String str = requestBuffer.get(i);
				if (str == null || str.isEmpty())
					break;

				String[] ss = str.split(": ");
				if (ss == null || ss.length != 2)
					break;

				String key = ss[0];
				String val = ss[1];
				requestHeaders.put(key, val);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestHeaders;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private synchronized Map<String, String> parseRequestParams() {
		if (requestParams != null && requestParams.isEmpty())
			return requestParams;

		try {
			requestParams = new Hashtable<>();

			parseCommandLine();
			String qs = URLDecoder.decode(getRequestURL());
			Hashtable ht = HttpUtils.parseQueryString(qs);
			Enumeration keys = ht.keys();
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				List<Object> vals = (List<Object>) ht.get(key);
				Object val = vals.get(0);
				requestParams.put((String) key, (String) val);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestParams;
	}

	public ReUsableSocket socket() {
		return socket;
	}

	public String getProtocol() {
		parseCommandLine();
		return this.protocal;
	}

	public String getMethod() {
		parseCommandLine();
		return this.method;
	}

	public String getRequestURL() {
		parseCommandLine();
		return this.requestUrl;
	}

	public String getRequestHeader(String key) {
		return parseRequestHeaders().get(key);
	}

	public Map<String, String> getRequestHeaders() {
		return parseRequestHeaders();
	}

	public String getAccept() {
		return getRequestHeader(UISupport.Accept);
	}

	public String getCookie() {
		return getRequestHeader(UISupport.Cookie);
	}

	public String getUserAgent() {
		return getRequestHeader(UISupport.UserAgent);
	}

	public int getContentLength() {
		String str = getRequestHeader(UISupport.ContentLength);
		return NumEx.stringToInt(str);
	}

	public String getContentType() {
		return getRequestHeader(UISupport.ContentType);
	}

	public InputStream getRequestBody() {
		return in();
	}

	public BufferedReader getRequestReader() {
		return reader();
	}

	// /search?hl=en&q=HTTP&btnG=Google+Search
	public String getString(String key) {
		Map<String, String> params = parseRequestParams();
		return params.get(key);
	}

	public boolean getBool(String key) {
		String s = getString(key);
		return NumEx.stringToBool(s);
	}

	public int getInt(String key) {
		String s = getString(key);
		return NumEx.stringToInt(s);
	}

	// HTTP/1.1 400 Bad Request

	// HTTP/1.1 200 OK
	// Date: Mon, 19 Aug 2013 09:33:32 GMT
	// Server: BWS/1.0
	// Content-Length: 10443
	// Content-Type: text/html;charset=utf-8
	// Cache-Control: private
	// BDPAGETYPE: 1
	// BDUSERID: 0
	// BDQID: 0x99e8d52e000cdaca
	// Set-Cookie: BDSVRTM=5; path=/
	// Set-Cookie: H_PS_PSSID=1449_3138_2785_3058_2981_3086_2701; path=/;
	// domain=.baidu.com
	// Set-Cookie: BAIDUID=28407EA149259AC752A1ABCC23E40428:FG=1; expires=Mon,
	// 19-Aug-43 09:33:32 GMT; path=/; domain=.baidu.com
	// Expires: Mon, 19 Aug 2013 09:33:32 GMT
	// P3P: CP=" OTI DSP COR IVA OUR IND COM "
	// Connection: Keep-Alive

	private int responseCode = 200;
	private Map<String, String> responseHeaders = new Hashtable<String, String>();

	public void setResponseCode(int code) {
		this.responseCode = code;
	}

	public void setResponseContentType(String type) {
		responseHeaders.put(UISupport.ContentType, type);
	}

	public void setResponseContentLength(int len) {
		responseHeaders.put(UISupport.ContentLength, String.valueOf(len));
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCookie(String cookie) {
		responseHeaders.put(UISupport.SetCookie, cookie);
	}

	public String getResponseCookie() {
		return responseHeaders.get(UISupport.SetCookie);
	}

	public void setResponseHeader(String key, String value) {
		responseHeaders.put(key, value);
	}

	public String getResponseHeader(String key) {
		return responseHeaders.get(key);
	}

	public Map<String, String> getResponseHeaders() {
		return responseHeaders;
	}

	public void sendResponseHeaders(int code) throws IOException {
		String codeStr = UISupport.Code.getString(code);
		StringBuffer sb = new StringBuffer();
		// command line
		sb.append(UISupport.Version.HTTP_11);
		sb.append(" ");
		sb.append(code);
		sb.append(" ");
		sb.append(codeStr);
		sb.append("\r\n");
		// headers
		Set<Entry<String, String>> entrys = responseHeaders.entrySet();
		for (Entry<String, String> en : entrys) {
			sb.append(en.getKey());
			sb.append(": ");
			sb.append(en.getValue());
			sb.append("\r\n");
		}
		sb.append("\r\n");

		// System.out.println(sb);
		writer().write(sb.toString());
	}

	public OutputStream getResponseBody() {
		return this.out();
	}

	public BufferedWriter getResponseWriter() {
		return this.writer();
	}

	public void flush() throws IOException {
		this.writer().flush();
	}

	public void flush(String str) throws IOException {
		this.writer().write(str);
		this.writer().flush();
	}

	public void flush(byte[] buff) throws IOException {
		this.out().write(buff);
		this.out().flush();
	}

	// public void sendResponseBody(byte[] buff) {
	// }

}
