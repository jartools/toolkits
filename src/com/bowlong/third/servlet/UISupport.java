package com.bowlong.third.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bowlong.Toolkit;
import com.bowlong.json.MyJson;
import com.bowlong.lang.NumEx;
import com.bowlong.util.DateEx;
import com.bowlong.util.NewMap;

@SuppressWarnings("serial")
public abstract class UISupport extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		this.request = request;
		this.response = response;
		this.method = Method.GET;
		this.session = this.request.getSession(true);
		doLogic();
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		this.request = request;
		this.response = response;
		this.method = Method.POST;
		this.session = this.request.getSession(true);
		doLogic();
	}

	// ///////////////////////////
	protected String method;
	protected HttpSession session;
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	public abstract int doLogic();

	// ///////////////////////////
	public final void setMaxInactiveInterval(int inv) {
		if (this.session == null)
			return;
		this.session.setMaxInactiveInterval(inv);
	}

	public final void setSession(final String key, final Object val) {
		if (this.session == null)
			return;
		this.session.setAttribute(key, val);
	}

	public final Object getSessionVal(final String key) {
		if (this.session == null)
			return null;
		return this.session.getAttribute(key);
	}

	public final String getHeader(final String key) {
		if (this.request == null)
			return "";
		return this.request.getHeader(key);
	}

	public final void setHeader(final String key, final String val) {
		if (this.response == null)
			return;
		this.response.setHeader(key, val);
	}

	public final String getContentType() {
		if (this.request == null)
			return "";
		return this.request.getContentType();
	}

	public final void setContentType(final String type) {
		this.response.setContentType(type);
	}

	public final void setContentLength(final int len) {
		this.response.setContentLength(len);
	}

	public final int getContentLength() {
		if (this.request == null)
			return 0;
		return this.request.getContentLength();
	}

	public final void setCharacterEncoding(final String encoding) {
		if (this.response == null)
			return;
		this.response.setCharacterEncoding(encoding);
	}

	// ///////////////////////////
	public final String getString(final String key) {
		return getString(request, key);
	}

	public final boolean getBool(final String key) {
		return getBool(request, key);
	}

	public final byte getByte(final String key) {
		return getByte(request, key);
	}

	public final short getShort(final String key) {
		return getShort(request, key);
	}

	public final int getInt(final String key) {
		return getInt(request, key);
	}

	public final float getFloat(final String key) {
		return getFloat(request, key);
	}

	public final double getDouble(final String key) {
		return getDouble(request, key);
	}

	public final Date getDate(final String key) {
		return getDate(request, key);
	}

	public final Date getDate(final String key, final String fmt) {
		return getDate(request, key, fmt);
	}

	public final byte[] readFullyNoExcept() {
		try {
			return readFully();
		} catch (IOException e) {
			return new byte[0];
		}
	}

	public final byte[] readFully() throws IOException {
		if (!method.equals(Method.POST))
			return new byte[0];

		int len = request.getContentLength();
		InputStream input = request.getInputStream();
		if (len > 0) {
			return NumEx.readFully(input, len);
		} else {
			return Toolkit.readStream(input);
		}
	}

	public final void writeJsonp(Map<?, ?> map) throws IOException {
		final String callback = getString(JSON_CALLBACK);
		writeJsonp(callback, map);
	}

	public final void writeJsonp(final String callback, Map<?, ?> map)
			throws IOException {
		if (callback == null || callback.isEmpty() || map == null)
			return;
		response.setContentType("text/javascript");
		final PrintWriter wr = response.getWriter();
		wr.append(callback).append("(").append(MyJson.toJSONString(map))
				.append(");").flush();
	}

	// ///////////////////////////
	public final static String getString(final HttpServletRequest request,
			final String key) {
		if (request == null || key == null)
			return "";
		String val = request.getParameter(key);
		return val == null ? "" : val;
	}

	public static final boolean getBool(final HttpServletRequest request,
			final String key) {
		String str = getString(request, key);
		return NumEx.stringToBool(str);
	}

	public static final byte getByte(final HttpServletRequest request,
			final String key) {
		String str = getString(request, key);
		return NumEx.stringToByte(str);
	}

	public static final short getShort(final HttpServletRequest request,
			final String key) {
		String str = getString(request, key);
		return NumEx.stringToShort(str);
	}

	public static final int getInt(final HttpServletRequest request,
			final String key) {
		String str = getString(request, key);
		return NumEx.stringToInt(str);
	}

	public static final float getFloat(final HttpServletRequest request,
			final String key) {
		String str = getString(request, key);
		return NumEx.stringToFloat(str);
	}

	public static final double getDouble(final HttpServletRequest request,
			final String key) {
		String str = getString(request, key);
		return NumEx.stringToDouble(str);
	}

	public static final Date getDate(final HttpServletRequest request,
			final String key) {
		return getDate(request, key, DateEx.fmt_yyyy_MM_dd_HH_mm_ss);
	}

	public static final Date getDate(final HttpServletRequest request,
			final String key, final String fmt) {
		String str = getString(request, key);
		return DateEx.parse2Date(str, fmt);
	}

	public static final byte[] readFullyNoExcept(
			final HttpServletRequest request) {
		try {
			return readFully(request);
		} catch (IOException e) {
			return new byte[0];
		}
	}

	public static final byte[] readFully(final HttpServletRequest request)
			throws IOException {
		if (request == null)
			return new byte[0];

		String method = request.getMethod();
		if (!method.equals(Method.POST))
			return new byte[0];

		int len = request.getContentLength();
		InputStream input = request.getInputStream();
		if (len > 0) {
			return NumEx.readFully(input, len);
		} else {
			return Toolkit.readStream(input);
		}
	}

	public static final class Version {
		public static final String HTTP_09 = "HTTP/0.9";
		public static final String HTTP_10 = "HTTP/1.0";
		public static final String HTTP_11 = "HTTP/1.1";
	}

	// ///////////////////////////
	public static final class Method {
		public static final String DELETE = "DELETE";
		public static final String HEAD = "HEAD";
		public static final String GET = "GET";
		public static final String OPTIONS = "OPTIONS";
		public static final String POST = "POST";
		public static final String PUT = "PUT";
		public static final String TRACE = "TRACE";
		public static final String IFMODSINCE = "If-Modified-Since";
		public static final String LASTMOD = "Last-Modified";
	}

	// ///////////////////////////

	public static final String Accept = "Accept"; // 指定客户端能够接收的内容类型 Accept:
													// text/plain, text/html
	public static final String AcceptCharset = "Accept-Charset";// 浏览器可以接受的字符编码集。
																// Accept-Charset:
																// iso-8859-5
	public static final String AcceptEncoding = "Accept-Encoding";// 指定浏览器可以支持的web服务器返回内容压缩编码类型。
																	// Accept-Encoding:
																	// compress,
																	// gzip
	public static final String AcceptLanguage = "Accept-Language";// 浏览器可接受的语言
																	// Accept-Language:
																	// en,zh
	public static final String AcceptRanges = "Accept-Ranges";// 可以请求网页实体的一个或者多个子范围字段
																// Accept-Ranges:
																// bytes
	public static final String Authorization = "Authorization";// HTTP授权的授权证书
																// Authorization:
																// Basic
	// QWxhZGRpbjpvcGVuIHNlc2FtZQ==
	public static final String CacheControl = "Cache-Control";;// 指定请求和响应遵循的缓存机制
																// Cache-Control:
																// no-cache
	public static final String Connection = "Connection";// 表示是否需要持久连接。（HTTP
															// 1.1默认进行持久连接）
	// Connection: close
	public static final String Cookie = "Cookie";// HTTP请求发送时，会把保存在该请求域名下的所有cookie值一起发送给web服务器。
	// Cookie: $Version=1; Skin=new;
	public static final String ContentLength = "Content-Length";// 请求的内容长度
																// Content-Length:
																// 348
	public static final String ContentType = "Content-Type";// 请求的与实体对应的MIME信息
															// Content-Type:
															// application/x-www-form-urlencoded
	public static final String Date = "Date";// 请求发送的日期和时间 Date: Tue, 15 Nov
												// 2010 08:12:31 GMT
	public static final String Expect = "Expect";// 请求的特定的服务器行为 Expect:
													// 100-continue
	public static final String From = "From";// 发出请求的用户的Email From:
												// user@email.com
	public static final String Host = "Host";// 指定请求的服务器的域名和端口号 Host:
												// www.zcmhi.com
	public static final String IfMatch = "If-Match";// 只有请求内容与实体相匹配才有效 If-Match:
													// “737060cd8c284d8af7ad3082f209582d”
	public static final String IfModifiedSince = "If-Modified-Since";// 如果请求的部分在指定时间之后被修改则请求成功，未被修改则返回304代码
																		// If-Modified-Since:
																		// Sat,
																		// 29
																		// Oct
																		// 2010
																		// 19:43:31
																		// GMT
	public static final String IfNoneMatch = "If-None-Match";// 如果内容未改变返回304代码，参数为服务器先前发送的Etag，与服务器回应的Etag比较判断是否改变
																// If-None-Match:
																// “737060cd8c284d8af7ad3082f209582d”
	public static final String IfRange = "If-Range";// 如果实体未改变，服务器发送客户端丢失的部分，否则发送整个实体。参数也为Etag
													// If-Range:
													// “737060cd8c284d8af7ad3082f209582d”
	public static final String IfUnmodifiedSince = "If-Unmodified-Since";// 只在实体在指定时间之后未被修改才请求成功
																			// If-Unmodified-Since:
																			// Sat,
																			// 29
																			// Oct
																			// 2010
																			// 19:43:31
																			// GMT
	public static final String MaxForwards = "Max-Forwards";// 限制信息通过代理和网关传送的时间
															// Max-Forwards: 10
	public static final String Pragma = "Pragma";// 用来包含实现特定的指令 Pragma: no-cache
	public static final String ProxyAuthorization = "Proxy-Authorization";// 连接到代理的授权证书
																			// Proxy-Authorization:
																			// Basic
																			// QWxhZGRpbjpvcGVuIHNlc2FtZQ==
	public static final String Range = "Range";// 只请求实体的一部分，指定范围 Range:
												// bytes=500-999
	public static final String Referer = "Referer";// 先前网页的地址，当前请求网页紧随其后,即来路
													// Referer:
													// http://www.zcmhi.com/archives/71.html
	public static final String TE = "TE";// 客户端愿意接受的传输编码，并通知服务器接受接受尾加头信息 TE:
											// trailers,deflate;q=0.5
	public static final String Upgrade = "Upgrade";// 向服务器指定某种传输协议以便服务器进行转换（如果支持）
													// Upgrade: HTTP/2.0,
													// SHTTP/1.3, IRC/6.9,
													// RTA/x11
	public static final String UserAgent = "User-Agent";// User-Agent的内容包含发出请求的用户信息
														// User-Agent:
														// Mozilla/5.0 (Linux;
														// X11)
	public static final String Via = "Via";// 通知中间网关或代理服务器地址，通信协议 Via: 1.0 fred,
											// 1.1 nowhere.com (Apache/1.1)
	public static final String Warning = "Warning";// 关于消息实体的警告信息 Warn: 199
													// Miscellaneous warning

	// public static final String AcceptRanges = "Accept-Ranges";
	// //表明服务器是否支持指定范围请求及哪种类型的分段请求 Accept-Ranges: bytes
	public static final String Age = "Age";// 从原始服务器到代理缓存形成的估算时间（以秒计，非负） Age: 12
	public static final String Allow = "Allow";// 对某网络资源的有效的请求行为，不允许则返回405
												// Allow: GET, HEAD
	// public static final String CacheControl = "Cache-Control";//
	// 告诉所有的缓存机制是否可以缓存及哪种类型 Cache-Control: no-cache
	public static final String ContentEncoding = "Content-Encoding";// web服务器支持的返回内容压缩编码类型。
																	// Content-Encoding:
																	// gzip
	public static final String ContentLanguage = "Content-Language";// 响应体的语言
																	// Content-Language:
																	// en,zh
	// public static final String ContentLength = "Content-Length";// 响应体的长度
	// Content-Length: 348
	public static final String ContentLocation = "Content-Location";// 请求资源可替代的备用的另一地址
																	// Content-Location:
																	// /index.htm
	public static final String ContentMD5 = "Content-MD5";// 返回资源的MD5校验值
															// Content-MD5:
															// Q2hlY2sgSW50ZWdyaXR5IQ==
	public static final String ContentRange = "Content-Range";// 在整个返回体中本部分的字节位置
																// Content-Range:
																// bytes
																// 21010-47021/47022
	// public static final String ContentType = "Content-Type";// 返回内容的MIME类型
	// Content-Type: text/html; charset=utf-8
	// public static final String Date = "Date";// 原始服务器消息发出的时间 Date: Tue, 15
	// Nov 2010 08:12:31 GMT
	public static final String ETag = "ETag";// 请求变量的实体标签的当前值 ETag:
												// “737060cd8c284d8af7ad3082f209582d”
	public static final String Expires = "Expires";// 响应过期的日期和时间 Expires: Thu,
													// 01 Dec 2010 16:00:00 GMT
	public static final String LastModified = "Last-Modified";// 请求资源的最后修改时间
																// Last-Modified:
																// Tue, 15 Nov
																// 2010 12:45:26
																// GMT
	public static final String Location = "Location";// 用来重定向接收方到非请求URL的位置来完成请求或标识新的资源
														// Location:
														// http://www.zcmhi.com/archives/94.html
	// public static final String Pragma = "Pragma";// 包括实现特定的指令，它可应用到响应链上的任何接收方
	// Pragma: no-cache
	public static final String ProxyAuthenticate = "Proxy-Authenticate";// 它指出认证方案和可应用到代理的该URL上的参数
																		// Proxy-Authenticate:
																		// Basic
	public static final String Refresh = "Refresh";// 应用于重定向或一个新的资源被创造，在5秒之后重定向（由网景提出，被大部分浏览器支持）
	// Refresh: 5; url= http://www.zcmhi.com/archives/94.html
	public static final String RetryAfter = "Retry-After";// 如果实体暂时不可取，通知客户端在指定时间之后再次尝试
															// Retry-After: 120
	public static final String Server = "Server";// web服务器软件名称 Server:
													// Apache/1.3.27 (Unix)
													// (Red-Hat/Linux)
	public static final String SetCookie = "Set-Cookie";// 设置Http Cookie
														// Set-Cookie:
														// UserID=JohnDoe;
														// Max-Age=3600;
														// Version=1
	public static final String Trailer = "Trailer";// 指出头域在分块传输编码的尾部存在 Trailer:
													// Max-Forwards
	public static final String TransferEncoding = "Transfer-Encoding";// 文件传输编码
																		// Transfer-Encoding:chunked
	public static final String Vary = "Vary";// 告诉下游代理是使用缓存响应还是从原始服务器请求 Vary: *
	// public static final String Via = "Via";// 告知代理客户端响应是通过哪里发送的 Via: 1.0
	// fred, 1.1 nowhere.com (Apache/1.1)
	// public static final String Warning = "Warning";// 警告实体可能存在的问题 Warning:
	// 199 Miscellaneous warning
	public static final String WWWAuthenticate = "WWW-Authenticate";// 表明客户端请求实体应该使用的授权方案
																	// WWW-Authenticate:
																	// Basic

	public static final String JSON_CALLBACK = "cb";

	public static class Code {
		public static final int SC_CONTINUE = 100;
		public static final int SC_SWITCHING_PROTOCOLS = 101;
		public static final int SC_OK = 200;
		public static final int SC_CREATED = 201;
		public static final int SC_ACCEPTED = 202;
		public static final int SC_NON_AUTHORITATIVE_INFORMATION = 203;
		public static final int SC_NO_CONTENT = 204;
		public static final int SC_RESET_CONTENT = 205;
		public static final int SC_PARTIAL_CONTENT = 206;
		public static final int SC_MULTIPLE_CHOICES = 300;
		public static final int SC_MOVED_PERMANENTLY = 301;
		public static final int SC_MOVED_TEMPORARILY = 302;
		public static final int SC_FOUND = 302;
		public static final int SC_SEE_OTHER = 303;
		public static final int SC_NOT_MODIFIED = 304;
		public static final int SC_USE_PROXY = 305;
		public static final int SC_TEMPORARY_REDIRECT = 307;
		public static final int SC_BAD_REQUEST = 400;
		public static final int SC_UNAUTHORIZED = 401;
		public static final int SC_PAYMENT_REQUIRED = 402;
		public static final int SC_FORBIDDEN = 403;
		public static final int SC_NOT_FOUND = 404;
		public static final int SC_METHOD_NOT_ALLOWED = 405;
		public static final int SC_NOT_ACCEPTABLE = 406;
		public static final int SC_PROXY_AUTHENTICATION_REQUIRED = 407;
		public static final int SC_REQUEST_TIMEOUT = 408;
		public static final int SC_CONFLICT = 409;
		public static final int SC_GONE = 410;
		public static final int SC_LENGTH_REQUIRED = 411;
		public static final int SC_PRECONDITION_FAILED = 412;
		public static final int SC_REQUEST_ENTITY_TOO_LARGE = 413;
		public static final int SC_REQUEST_URI_TOO_LONG = 414;
		public static final int SC_UNSUPPORTED_MEDIA_TYPE = 415;
		public static final int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
		public static final int SC_EXPECTATION_FAILED = 417;
		public static final int SC_INTERNAL_SERVER_ERROR = 500;
		public static final int SC_NOT_IMPLEMENTED = 501;
		public static final int SC_BAD_GATEWAY = 502;
		public static final int SC_SERVICE_UNAVAILABLE = 503;
		public static final int SC_GATEWAY_TIMEOUT = 504;
		public static final int SC_HTTP_VERSION_NOT_SUPPORTED = 505;
		// ///////////////////////////

		public static final NewMap<Integer, String> codes = new NewMap<Integer, String>()
				.add(100, "Continue").add(101, "Switching Protocols")
				.add(200, "OK").add(201, "Created").add(202, "Accepted")
				.add(203, "Non-Authoritative Information")
				.add(204, "No Content").add(205, "Reset Content")
				.add(206, "Partial Content").add(300, "Multiple Choices")
				.add(301, "Moved Permanently").add(302, "Found")
				.add(303, "See Other").add(304, "Not Modified")
				.add(305, "Use Proxy").add(307, "Temporary Redirect")
				.add(400, "Bad Request").add(401, "Unauthorized")
				.add(402, "Payment Required").add(403, "Forbidden")
				.add(404, "Not Found").add(405, "Method Not Allowed")
				.add(406, "Not Acceptable")
				.add(407, "Proxy Authentication Required")
				.add(408, "Request Timeout").add(409, "Conflict")
				.add(410, "Gone").add(411, "Length Required")
				.add(412, "Precondition Failed")
				.add(413, "Request Entity Too Large")
				.add(414, "Request-URI Too Long")
				.add(415, "Unsupported Media Type")
				.add(416, "Requested Range Not Satisfiable")
				.add(417, "Expectation Failed")
				.add(500, "Internal Server Error").add(501, "Not Implemented")
				.add(502, "Bad Gateway").add(503, "Service Unavailable")
				.add(504, "Gateway Timeout")
				.add(505, "HTTP Version Not Supported");

		public static final String getString(int code) {
			String str = codes.get(code);
			if (str == null)
				str = "Unknow.";
			return str;
		}
	}

	public static class MimeType {
		public static final String _123 = "application/vnd.lotus-1-2-3";
		public static final String _3DML = "text/vnd.in3d.3dml";
		public static final String _3DS = "image/x-3ds";
		public static final String _3G2 = "video/3gpp2";
		public static final String _3GP = "video/3gpp";
		public static final String _7Z = "application/x-7z-compressed";
		public static final String AAB = "application/x-authorware-bin";
		public static final String AAC = "audio/x-aac";
		public static final String AAM = "application/x-authorware-map";
		public static final String AAS = "application/x-authorware-seg";
		public static final String ABS = "audio/x-mpeg";
		public static final String ABW = "application/x-abiword";
		public static final String AC = "application/pkix-attr-cert";
		public static final String ACC = "application/vnd.americandynamics.acc";
		public static final String ACE = "application/x-ace-compressed";
		public static final String ACU = "application/vnd.acucobol";
		public static final String ACUTC = "application/vnd.acucorp";
		public static final String ADP = "audio/adpcm";
		public static final String AEP = "application/vnd.audiograph";
		public static final String AFM = "application/x-font-type1";
		public static final String AFP = "application/vnd.ibm.modcap";
		public static final String AHEAD = "application/vnd.ahead.space";
		public static final String AI = "application/postscript";
		public static final String AIF = "audio/x-aiff";
		public static final String AIFC = "audio/x-aiff";
		public static final String AIFF = "audio/x-aiff";
		public static final String AIM = "application/x-aim";
		public static final String AIR = "application/vnd.adobe.air-application-installer-package+zip";
		public static final String AIT = "application/vnd.dvb.ait";
		public static final String AMI = "application/vnd.amiga.ami";
		public static final String ANX = "application/annodex";
		public static final String APK = "application/vnd.android.package-archive";
		public static final String APPCACHE = "text/cache-manifest";
		public static final String APPLICATION = "application/x-ms-application";
		public static final String APR = "application/vnd.lotus-approach";
		public static final String ARC = "application/x-freearc";
		public static final String ART = "image/x-jg";
		public static final String ASC = "application/pgp-signature";
		public static final String ASF = "video/x-ms-asf";
		public static final String ASM = "text/x-asm";
		public static final String ASO = "application/vnd.accpac.simply.aso";
		public static final String ASX = "video/x-ms-asf";
		public static final String ATC = "application/vnd.acucorp";
		public static final String ATOM = "application/atom+xml";
		public static final String ATOMCAT = "application/atomcat+xml";
		public static final String ATOMSVC = "application/atomsvc+xml";
		public static final String ATX = "application/vnd.antix.game-component";
		public static final String AU = "audio/basic";
		public static final String AVI = "video/x-msvideo";
		public static final String AVX = "video/x-rad-screenplay";
		public static final String AW = "application/applixware";
		public static final String AXA = "audio/annodex";
		public static final String AXV = "video/annodex";
		public static final String AZF = "application/vnd.airzip.filesecure.azf";
		public static final String AZS = "application/vnd.airzip.filesecure.azs";
		public static final String AZW = "application/vnd.amazon.ebook";
		public static final String BAT = "application/x-msdownload";
		public static final String BCPIO = "application/x-bcpio";
		public static final String BDF = "application/x-font-bdf";
		public static final String BDM = "application/vnd.syncml.dm+wbxml";
		public static final String BED = "application/vnd.realvnc.bed";
		public static final String BH2 = "application/vnd.fujitsu.oasysprs";
		public static final String BIN = "application/octet-stream";
		public static final String BLB = "application/x-blorb";
		public static final String BLORB = "application/x-blorb";
		public static final String BMI = "application/vnd.bmi";
		public static final String BMP = "image/bmp";
		public static final String BODY = "text/html";
		public static final String BOOK = "application/vnd.framemaker";
		public static final String BOX = "application/vnd.previewsystems.box";
		public static final String BOZ = "application/x-bzip2";
		public static final String BPK = "application/octet-stream";
		public static final String BTIF = "image/prs.btif";
		public static final String BZ = "application/x-bzip";
		public static final String BZ2 = "application/x-bzip2";
		public static final String C = "text/x-c";
		public static final String C11AMC = "application/vnd.cluetrust.cartomobile-config";
		public static final String C11AMZ = "application/vnd.cluetrust.cartomobile-config-pkg";
		public static final String C4D = "application/vnd.clonk.c4group";
		public static final String C4F = "application/vnd.clonk.c4group";
		public static final String C4G = "application/vnd.clonk.c4group";
		public static final String C4P = "application/vnd.clonk.c4group";
		public static final String C4U = "application/vnd.clonk.c4group";
		public static final String CAB = "application/vnd.ms-cab-compressed";
		public static final String CAF = "audio/x-caf";
		public static final String CAP = "application/vnd.tcpdump.pcap";
		public static final String CAR = "application/vnd.curl.car";
		public static final String CAT = "application/vnd.ms-pki.seccat";
		public static final String CB7 = "application/x-cbr";
		public static final String CBA = "application/x-cbr";
		public static final String CBR = "application/x-cbr";
		public static final String CBT = "application/x-cbr";
		public static final String CBZ = "application/x-cbr";
		public static final String CC = "text/x-c";
		public static final String CCT = "application/x-director";
		public static final String CCXML = "application/ccxml+xml";
		public static final String cdbcmsg = "application/vnd.contact.cmsg";
		public static final String CDF = "application/x-cdf";
		public static final String CDKEY = "application/vnd.mediastation.cdkey";
		public static final String CDMIA = "application/cdmi-capability";
		public static final String CDMIC = "application/cdmi-container";
		public static final String CDMID = "application/cdmi-domain";
		public static final String CDMIO = "application/cdmi-object";
		public static final String CDMIQ = "application/cdmi-queue";
		public static final String CDX = "chemical/x-cdx";
		public static final String CDXML = "application/vnd.chemdraw+xml";
		public static final String CDY = "application/vnd.cinderella";
		public static final String CER = "application/pkix-cert";
		public static final String CFS = "application/x-cfs-compressed";
		public static final String CGM = "image/cgm";
		public static final String CHAT = "application/x-chat";
		public static final String CHM = "application/vnd.ms-htmlhelp";
		public static final String CHRT = "application/vnd.kde.kchart";
		public static final String CIF = "chemical/x-cif";
		public static final String CII = "application/vnd.anser-web-certificate-issue-initiation";
		public static final String CIL = "application/vnd.ms-artgalry";
		public static final String CLA = "application/vnd.claymore";
		public static final String CLASS = "application/java";
		public static final String CLKK = "application/vnd.crick.clicker.keyboard";
		public static final String CLKP = "application/vnd.crick.clicker.palette";
		public static final String CLKT = "application/vnd.crick.clicker.template";
		public static final String CLKW = "application/vnd.crick.clicker.wordbank";
		public static final String CLKX = "application/vnd.crick.clicker";
		public static final String CLP = "application/x-msclip";
		public static final String CMC = "application/vnd.cosmocaller";
		public static final String CMDF = "chemical/x-cmdf";
		public static final String CML = "chemical/x-cml";
		public static final String CMP = "application/vnd.yellowriver-custom-menu";
		public static final String CMX = "image/x-cmx";
		public static final String COD = "application/vnd.rim.cod";
		public static final String COM = "application/x-msdownload";
		public static final String CONF = "text/plain";
		public static final String CPIO = "application/x-cpio";
		public static final String CPP = "text/x-c";
		public static final String CPT = "application/mac-compactpro";
		public static final String CRD = "application/x-mscardfile";
		public static final String CRL = "application/pkix-crl";
		public static final String CRT = "application/x-x509-ca-cert";
		public static final String CRYPTONOTE = "application/vnd.rig.cryptonote";
		public static final String CSH = "application/x-csh";
		public static final String CSML = "chemical/x-csml";
		public static final String CSP = "application/vnd.commonspace";
		public static final String CSS = "text/css";
		public static final String CST = "application/x-director";
		public static final String CSV = "text/csv";
		public static final String CU = "application/cu-seeme";
		public static final String CURL = "text/vnd.curl";
		public static final String CWW = "application/prs.cww";
		public static final String CXT = "application/x-director";
		public static final String CXX = "text/x-c";
		public static final String DAE = "model/vnd.collada+xml";
		public static final String DAF = "application/vnd.mobius.daf";
		public static final String DART = "application/vnd.dart";
		public static final String DATALESS = "application/vnd.fdsn.seed";
		public static final String DAVMOUNT = "application/davmount+xml";
		public static final String DBK = "application/docbook+xml";
		public static final String DCR = "application/x-director";
		public static final String DCURL = "text/vnd.curl.dcurl";
		public static final String DD2 = "application/vnd.oma.dd2+xml";
		public static final String DDD = "application/vnd.fujixerox.ddd";
		public static final String DEB = "application/x-debian-package";
		public static final String DEF = "text/plain";
		public static final String DEPLOY = "application/octet-stream";
		public static final String DER = "application/x-x509-ca-cert";
		public static final String DFAC = "application/vnd.dreamfactory";
		public static final String DGC = "application/x-dgc-compressed";
		public static final String DIB = "image/bmp";
		public static final String DIC = "text/x-c";
		public static final String DIR = "application/x-director";
		public static final String DIS = "application/vnd.mobius.dis";
		public static final String DIST = "application/octet-stream";
		public static final String DISTZ = "application/octet-stream";
		public static final String DJV = "image/vnd.djvu";
		public static final String DJVU = "image/vnd.djvu";
		public static final String DLL = "application/x-msdownload";
		public static final String DMG = "application/x-apple-diskimage";
		public static final String DMP = "application/vnd.tcpdump.pcap";
		public static final String DMS = "application/octet-stream";
		public static final String DNA = "application/vnd.dna";
		public static final String DOC = "application/msword";
		public static final String DOCM = "application/vnd.ms-word.document.macroenabled.12";
		public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		public static final String dot = "application/msword";
		public static final String DOTM = "application/vnd.ms-word.template.macroenabled.12";
		public static final String DOTX = "application/vnd.openxmlformats-officedocument.wordprocessingml.template";
		public static final String DP = "application/vnd.osgi.dp";
		public static final String DPG = "application/vnd.dpgraph";
		public static final String DRA = "audio/vnd.dra";
		public static final String DSC = "text/prs.lines.tag";
		public static final String DSSC = "application/dssc+der";
		public static final String DTB = "application/x-dtbook+xml";
		public static final String DTD = "application/xml-dtd";
		public static final String DTS = "audio/vnd.dts";
		public static final String DTSHD = "audio/vnd.dts.hd";
		public static final String DUMP = "application/octet-stream";
		public static final String DV = "video/x-dv";
		public static final String DVB = "video/vnd.dvb.file";
		public static final String DVI = "application/x-dvi";
		public static final String DWF = "model/vnd.dwf";
		public static final String DWG = "image/vnd.dwg";
		public static final String DXF = "image/vnd.dxf";
		public static final String DXP = "application/vnd.spotfire.dxp";
		public static final String DXR = "application/x-director";
		public static final String ECELP4800 = "audio/vnd.nuera.ecelp4800";
		public static final String ECELP7470 = "audio/vnd.nuera.ecelp7470";
		public static final String ECELP9600 = "audio/vnd.nuera.ecelp9600";
		public static final String ECMA = "application/ecmascript";
		public static final String EDM = "application/vnd.novadigm.edm";
		public static final String EDX = "application/vnd.novadigm.edx";
		public static final String EFIF = "application/vnd.picsel";
		public static final String EI6 = "application/vnd.pg.osasli";
		public static final String ELC = "application/octet-stream";
		public static final String EMF = "application/x-msmetafile";
		public static final String EML = "message/rfc822";
		public static final String EMMA = "application/emma+xml";
		public static final String EMZ = "application/x-msmetafile";
		public static final String EOL = "audio/vnd.digital-winds";
		public static final String EOT = "application/vnd.ms-fontobject";
		public static final String EPS = "application/postscript";
		public static final String EPUB = "application/epub+zip";
		public static final String ES3 = "application/vnd.eszigno3+xml";
		public static final String ESA = "application/vnd.osgi.subsystem";
		public static final String ESF = "application/vnd.epson.esf";
		public static final String ET3 = "application/vnd.eszigno3+xml";
		public static final String ETX = "text/x-setext";
		public static final String EVA = "application/x-eva";
		public static final String EVY = "application/x-envoy";
		public static final String EXE = "application/octet-stream";
		public static final String EXI = "application/exi";
		public static final String EXT = "application/vnd.novadigm.ext";
		public static final String EZ = "application/andrew-inset";
		public static final String EZ2 = "application/vnd.ezpix-album";
		public static final String EZ3 = "application/vnd.ezpix-package";
		public static final String F = "text/x-fortran";
		public static final String F4V = "video/x-f4v";
		public static final String F77 = "text/x-fortran";
		public static final String F90 = "text/x-fortran";
		public static final String FBS = "image/vnd.fastbidsheet";
		public static final String FCDT = "application/vnd.adobe.formscentral.fcdt";
		public static final String FCS = "application/vnd.isac.fcs";
		public static final String FDF = "application/vnd.fdf";
		public static final String FE_LAUNCH = "application/vnd.denovo.fcselayout-link";
		public static final String FG5 = "application/vnd.fujitsu.oasysgp";
		public static final String FGD = "application/x-director";
		public static final String FH = "image/x-freehand";
		public static final String FH4 = "image/x-freehand";
		public static final String FH5 = "image/x-freehand";
		public static final String FH7 = "image/x-freehand";
		public static final String FHC = "image/x-freehand";
		public static final String FIG = "application/x-xfig";
		public static final String FLAC = "audio/flac";
		public static final String FLI = "video/x-fli";
		public static final String FLO = "application/vnd.micrografx.flo";
		public static final String FLV = "video/x-flv";
		public static final String FLW = "application/vnd.kde.kivio";
		public static final String FLX = "text/vnd.fmi.flexstor";
		public static final String FLY = "text/vnd.fly";
		public static final String FM = "application/vnd.framemaker";
		public static final String FNC = "application/vnd.frogans.fnc";
		public static final String FOR = "text/x-fortran";
		public static final String FPX = "image/vnd.fpx";
		public static final String FRAME = "application/vnd.framemaker";
		public static final String FSC = "application/vnd.fsc.weblaunch";
		public static final String FST = "image/vnd.fst";
		public static final String FTC = "application/vnd.fluxtime.clip";
		public static final String FTI = "application/vnd.anser-web-funds-transfer-initiation";
		public static final String FVT = "video/vnd.fvt";
		public static final String FXP = "application/vnd.adobe.fxp";
		public static final String FXPL = "application/vnd.adobe.fxp";
		public static final String FZS = "application/vnd.fuzzysheet";
		public static final String G2W = "application/vnd.geoplan";
		public static final String G3 = "image/g3fax";
		public static final String g3w = "application/vnd.geospace";
		public static final String gac = "application/vnd.groove-account";
		public static final String gam = "application/x-tads";
		public static final String gbr = "application/rpki-ghostbusters";
		public static final String gca = "application/x-gca-compressed";
		public static final String gdl = "model/vnd.gdl";
		public static final String geo = "application/vnd.dynageo";
		public static final String gex = "application/vnd.geometry-explorer";
		public static final String ggb = "application/vnd.geogebra.file";
		public static final String ggt = "application/vnd.geogebra.tool";
		public static final String ghf = "application/vnd.groove-help";
		public static final String gif = "image/gif";
		public static final String gim = "application/vnd.groove-identity-message";
		public static final String gml = "application/gml+xml";
		public static final String gmx = "application/vnd.gmx";
		public static final String gnumeric = "application/x-gnumeric";
		public static final String gph = "application/vnd.flographit";
		public static final String gpx = "application/gpx+xml";
		public static final String gqf = "application/vnd.grafeq";
		public static final String gqs = "application/vnd.grafeq";
		public static final String gram = "application/srgs";
		public static final String gramps = "application/x-gramps-xml";
		public static final String gre = "application/vnd.geometry-explorer";
		public static final String grv = "application/vnd.groove-injector";
		public static final String grxml = "application/srgs+xml";
		public static final String gsf = "application/x-font-ghostscript";
		public static final String gtar = "application/x-gtar";
		public static final String gtm = "application/vnd.groove-tool-message";
		public static final String gtw = "model/vnd.gtw";
		public static final String gv = "text/vnd.graphviz";
		public static final String gxf = "application/gxf";
		public static final String gxt = "application/vnd.geonext";
		public static final String gz = "application/x-gzip";
		public static final String h = "text/x-c";
		public static final String h261 = "video/h261";
		public static final String h263 = "video/h263";
		public static final String h264 = "video/h264";
		public static final String hal = "application/vnd.hal+xml";
		public static final String hbci = "application/vnd.hbci";
		public static final String hdf = "application/x-hdf";
		public static final String hh = "text/x-c";
		public static final String hlp = "application/winhlp";
		public static final String hpgl = "application/vnd.hp-hpgl";
		public static final String hpid = "application/vnd.hp-hpid";
		public static final String hps = "application/vnd.hp-hps";
		public static final String hqx = "application/mac-binhex40";
		public static final String htc = "text/x-component";
		public static final String htke = "application/vnd.kenameaapp";
		public static final String htm = "text/html";
		public static final String html = "text/html";
		public static final String hvd = "application/vnd.yamaha.hv-dic";
		public static final String hvp = "application/vnd.yamaha.hv-voice";
		public static final String hvs = "application/vnd.yamaha.hv-script";
		public static final String i2g = "application/vnd.intergeo";
		public static final String icc = "application/vnd.iccprofile";
		public static final String ice = "x-conference/x-cooltalk";
		public static final String icm = "application/vnd.iccprofile";
		public static final String ico = "image/x-icon";
		public static final String ics = "text/calendar";
		public static final String ief = "image/ief";
		public static final String ifb = "text/calendar";
		public static final String ifm = "application/vnd.shana.informed.formdata";
		public static final String iges = "model/iges";
		public static final String igl = "application/vnd.igloader";
		public static final String igm = "application/vnd.insors.igm";
		public static final String igs = "model/iges";
		public static final String igx = "application/vnd.micrografx.igx";
		public static final String iif = "application/vnd.shana.informed.interchange";
		public static final String imp = "application/vnd.accpac.simply.imp";
		public static final String ims = "application/vnd.ms-ims";
		public static final String in = "text/plain";
		public static final String ink = "application/inkml+xml";
		public static final String inkml = "application/inkml+xml";
		public static final String install = "application/x-install-instructions";
		public static final String iota = "application/vnd.astraea-software.iota";
		public static final String ipfix = "application/ipfix";
		public static final String ipk = "application/vnd.shana.informed.package";
		public static final String irm = "application/vnd.ibm.rights-management";
		public static final String irp = "application/vnd.irepository.package+xml";
		public static final String iso = "application/x-iso9660-image";
		public static final String itp = "application/vnd.shana.informed.formtemplate";
		public static final String ivp = "application/vnd.immervision-ivp";
		public static final String ivu = "application/vnd.immervision-ivu";
		public static final String jad = "text/vnd.sun.j2me.app-descriptor";
		public static final String jam = "application/vnd.jam";
		public static final String jar = "application/java-archive";
		public static final String java = "text/x-java-source";
		public static final String jisp = "application/vnd.jisp";
		public static final String jlt = "application/vnd.hp-jlyt";
		public static final String jnlp = "application/x-java-jnlp-file";
		public static final String joda = "application/vnd.joost.joda-archive";
		public static final String jpe = "image/jpeg";
		public static final String jpeg = "image/jpeg";
		public static final String jpg = "image/jpeg";
		public static final String jpgm = "video/jpm";
		public static final String jpgv = "video/jpeg";
		public static final String jpm = "video/jpm";
		public static final String js = "application/javascript";
		public static final String jsf = "text/plain";
		public static final String json = "application/json";
		public static final String jsonml = "application/jsonml+json";
		public static final String jspf = "text/plain";
		public static final String kar = "audio/midi";
		public static final String karbon = "application/vnd.kde.karbon";
		public static final String kfo = "application/vnd.kde.kformula";
		public static final String kia = "application/vnd.kidspiration";
		public static final String kml = "application/vnd.google-earth.kml+xml";
		public static final String kmz = "application/vnd.google-earth.kmz";
		public static final String kne = "application/vnd.kinar";
		public static final String knp = "application/vnd.kinar";
		public static final String kon = "application/vnd.kde.kontour";
		public static final String kpr = "application/vnd.kde.kpresenter";
		public static final String kpt = "application/vnd.kde.kpresenter";
		public static final String kpxx = "application/vnd.ds-keypoint";
		public static final String ksp = "application/vnd.kde.kspread";
		public static final String ktr = "application/vnd.kahootz";
		public static final String ktx = "image/ktx";
		public static final String ktz = "application/vnd.kahootz";
		public static final String kwd = "application/vnd.kde.kword";
		public static final String kwt = "application/vnd.kde.kword";
		public static final String lasxml = "application/vnd.las.las+xml";
		public static final String latex = "application/x-latex";
		public static final String lbd = "application/vnd.llamagraphics.life-balance.desktop";
		public static final String lbe = "application/vnd.llamagraphics.life-balance.exchange+xml";
		public static final String les = "application/vnd.hhe.lesson-player";
		public static final String lha = "application/x-lzh-compressed";
		public static final String link66 = "application/vnd.route66.link66+xml";
		public static final String list = "text/plain";
		public static final String list3820 = "application/vnd.ibm.modcap";
		public static final String listafp = "application/vnd.ibm.modcap";
		public static final String lnk = "application/x-ms-shortcut";
		public static final String log = "text/plain";
		public static final String lostxml = "application/lost+xml";
		public static final String lrf = "application/octet-stream";
		public static final String lrm = "application/vnd.ms-lrm";
		public static final String ltf = "application/vnd.frogans.ltf";
		public static final String lvp = "audio/vnd.lucent.voice";
		public static final String lwp = "application/vnd.lotus-wordpro";
		public static final String lzh = "application/x-lzh-compressed";
		public static final String m13 = "application/x-msmediaview";
		public static final String m14 = "application/x-msmediaview";
		public static final String m1v = "video/mpeg";
		public static final String m21 = "application/mp21";
		public static final String m2a = "audio/mpeg";
		public static final String m2v = "video/mpeg";
		public static final String m3a = "audio/mpeg";
		public static final String m3u = "audio/x-mpegurl";
		public static final String m3u8 = "application/vnd.apple.mpegurl";
		public static final String m4a = "audio/mp4";
		public static final String m4b = "audio/mp4";
		public static final String m4r = "audio/mp4";
		public static final String m4u = "video/vnd.mpegurl";
		public static final String m4v = "video/mp4";
		public static final String ma = "application/mathematica";
		public static final String mac = "image/x-macpaint";
		public static final String mads = "application/mads+xml";
		public static final String mag = "application/vnd.ecowin.chart";
		public static final String maker = "application/vnd.framemaker";
		public static final String man = "text/troff";
		public static final String mar = "application/octet-stream";
		public static final String mathml = "application/mathml+xml";
		public static final String mb = "application/mathematica";
		public static final String mbk = "application/vnd.mobius.mbk";
		public static final String mbox = "application/mbox";
		public static final String mc1 = "application/vnd.medcalcdata";
		public static final String mcd = "application/vnd.mcd";
		public static final String mcurl = "text/vnd.curl.mcurl";
		public static final String mdb = "application/x-msaccess";
		public static final String mdi = "image/vnd.ms-modi";
		public static final String me = "text/troff";
		public static final String mesh = "model/mesh";
		public static final String meta4 = "application/metalink4+xml";
		public static final String metalink = "application/metalink+xml";
		public static final String mets = "application/mets+xml";
		public static final String mfm = "application/vnd.mfmp";
		public static final String mft = "application/rpki-manifest";
		public static final String mgp = "application/vnd.osgeo.mapguide.package";
		public static final String mgz = "application/vnd.proteus.magazine";
		public static final String mid = "audio/midi";
		public static final String midi = "audio/midi";
		public static final String mie = "application/x-mie";
		public static final String mif = "application/x-mif";
		public static final String mime = "message/rfc822";
		public static final String mj2 = "video/mj2";
		public static final String mjp2 = "video/mj2";
		public static final String mk3d = "video/x-matroska";
		public static final String mka = "audio/x-matroska";
		public static final String mks = "video/x-matroska";
		public static final String mkv = "video/x-matroska";
		public static final String mlp = "application/vnd.dolby.mlp";
		public static final String mmd = "application/vnd.chipnuts.karaoke-mmd";
		public static final String mmf = "application/vnd.smaf";
		public static final String mmr = "image/vnd.fujixerox.edmics-mmr";
		public static final String mng = "video/x-mng";
		public static final String mny = "application/x-msmoney";
		public static final String mobi = "application/x-mobipocket-ebook";
		public static final String mods = "application/mods+xml";
		public static final String mov = "video/quicktime";
		public static final String movie = "video/x-sgi-movie";
		public static final String mp1 = "audio/mpeg";
		public static final String mp2 = "audio/mpeg";
		public static final String mp21 = "application/mp21";
		public static final String mp2a = "audio/mpeg";
		public static final String mp3 = "audio/mpeg";
		public static final String mp4 = "video/mp4";
		public static final String mp4a = "audio/mp4";
		public static final String mp4s = "application/mp4";
		public static final String mp4v = "video/mp4";
		public static final String mpa = "audio/mpeg";
		public static final String mpc = "application/vnd.mophun.certificate";
		public static final String mpe = "video/mpeg";
		public static final String mpeg = "video/mpeg";
		public static final String mpega = "audio/x-mpeg";
		public static final String mpg = "video/mpeg";
		public static final String mpg4 = "video/mp4";
		public static final String mpga = "audio/mpeg";
		public static final String mpkg = "application/vnd.apple.installer+xml";
		public static final String mpm = "application/vnd.blueice.multipass";
		public static final String mpn = "application/vnd.mophun.application";
		public static final String mpp = "application/vnd.ms-project";
		public static final String mpt = "application/vnd.ms-project";
		public static final String mpv2 = "video/mpeg2";
		public static final String mpy = "application/vnd.ibm.minipay";
		public static final String mqy = "application/vnd.mobius.mqy";
		public static final String mrc = "application/marc";
		public static final String mrcx = "application/marcxml+xml";
		public static final String ms = "text/troff";
		public static final String mscml = "application/mediaservercontrol+xml";
		public static final String mseed = "application/vnd.fdsn.mseed";
		public static final String mseq = "application/vnd.mseq";
		public static final String msf = "application/vnd.epson.msf";
		public static final String msh = "model/mesh";
		public static final String msi = "application/x-msdownload";
		public static final String msl = "application/vnd.mobius.msl";
		public static final String msty = "application/vnd.muvee.style";
		public static final String mts = "model/vnd.mts";
		public static final String mus = "application/vnd.musician";
		public static final String musicxml = "application/vnd.recordare.musicxml+xml";
		public static final String mvb = "application/x-msmediaview";
		public static final String mwf = "application/vnd.mfer";
		public static final String mxf = "application/mxf";
		public static final String mxl = "application/vnd.recordare.musicxml";
		public static final String MXML = "application/xv+xml";
		public static final String MXS = "application/vnd.triscape.mxs";
		public static final String MXU = "video/vnd.mpegurl";
		public static final String N_GAGE = "application/vnd.nokia.n-gage.symbian.install";
		public static final String N3 = "text/n3";
		public static final String NB = "application/mathematica";
		public static final String NBP = "application/vnd.wolfram.player";
		public static final String NC = "application/x-netcdf";
		public static final String NCX = "application/x-dtbncx+xml";
		public static final String NFO = "text/x-nfo";
		public static final String NGDAT = "application/vnd.nokia.n-gage.data";
		public static final String NITF = "application/vnd.nitf";
		public static final String NLU = "application/vnd.neurolanguage.nlu";
		public static final String NML = "application/vnd.enliven";
		public static final String NND = "application/vnd.noblenet-directory";
		public static final String NNS = "application/vnd.noblenet-sealer";
		public static final String NNW = "application/vnd.noblenet-web";
		public static final String NPX = "image/vnd.net-fpx";
		public static final String NSC = "application/x-conference";
		public static final String NSF = "application/vnd.lotus-notes";
		public static final String NTF = "application/vnd.nitf";
		public static final String NZB = "application/x-nzb";
		public static final String OA2 = "application/vnd.fujitsu.oasys2";
		public static final String OA3 = "application/vnd.fujitsu.oasys3";
		public static final String OAS = "application/vnd.fujitsu.oasys";
		public static final String OBD = "application/x-msbinder";
		public static final String OBJ = "application/x-tgif";
		public static final String ODA = "application/oda";
		public static final String odb = "application/vnd.oasis.opendocument.database";
		public static final String odc = "application/vnd.oasis.opendocument.chart";
		public static final String odf = "application/vnd.oasis.opendocument.formula";
		public static final String odft = "application/vnd.oasis.opendocument.formula-template";
		public static final String odg = "application/vnd.oasis.opendocument.graphics";
		public static final String odi = "application/vnd.oasis.opendocument.image";
		public static final String odm = "application/vnd.oasis.opendocument.text-master";
		public static final String odp = "application/vnd.oasis.opendocument.presentation";
		public static final String ods = "application/vnd.oasis.opendocument.spreadsheet";
		public static final String odt = "application/vnd.oasis.opendocument.text";
		public static final String oga = "audio/ogg";
		public static final String ogg = "audio/ogg";
		public static final String ogv = "video/ogg";
		public static final String ogx = "application/ogg";
		public static final String omdoc = "application/omdoc+xml";
		public static final String onepkg = "application/onenote";
		public static final String onetmp = "application/onenote";
		public static final String onetoc = "application/onenote";
		public static final String onetoc2 = "application/onenote";
		public static final String opf = "application/oebps-package+xml";
		public static final String opml = "text/x-opml";
		public static final String oprc = "application/vnd.palm";
		public static final String org = "application/vnd.lotus-organizer";
		public static final String osf = "application/vnd.yamaha.openscoreformat";
		public static final String osfpvg = "application/vnd.yamaha.openscoreformat.osfpvg+xml";
		public static final String otc = "application/vnd.oasis.opendocument.chart-template";
		public static final String otf = "application/x-font-otf";
		public static final String otg = "application/vnd.oasis.opendocument.graphics-template";
		public static final String oth = "application/vnd.oasis.opendocument.text-web";
		public static final String oti = "application/vnd.oasis.opendocument.image-template";
		public static final String otp = "application/vnd.oasis.opendocument.presentation-template";
		public static final String ots = "application/vnd.oasis.opendocument.spreadsheet-template";
		public static final String ott = "application/vnd.oasis.opendocument.text-template";
		public static final String oxps = "application/oxps";
		public static final String oxt = "application/vnd.openofficeorg.extension";
		public static final String p = "text/x-pascal";
		public static final String p10 = "application/pkcs10";
		public static final String p12 = "application/x-pkcs12";
		public static final String p7b = "application/x-pkcs7-certificates";
		public static final String p7c = "application/pkcs7-mime";
		public static final String p7m = "application/pkcs7-mime";
		public static final String p7r = "application/x-pkcs7-certreqresp";
		public static final String p7s = "application/pkcs7-signature";
		public static final String p8 = "application/pkcs8";
		public static final String pas = "text/x-pascal";
		public static final String paw = "application/vnd.pawaafile";
		public static final String pbd = "application/vnd.powerbuilder6";
		public static final String pbm = "image/x-portable-bitmap";
		public static final String pcap = "application/vnd.tcpdump.pcap";
		public static final String pcf = "application/x-font-pcf";
		public static final String pcl = "application/vnd.hp-pcl";
		public static final String pclxl = "application/vnd.hp-pclxl";
		public static final String pct = "image/pict";
		public static final String pcurl = "application/vnd.curl.pcurl";
		public static final String pcx = "image/x-pcx";
		public static final String pdb = "application/vnd.palm";
		public static final String pdf = "application/pdf";
		public static final String pfa = "application/x-font-type1";
		public static final String pfb = "application/x-font-type1";
		public static final String pfm = "application/x-font-type1";
		public static final String pfr = "application/font-tdpfr";
		public static final String pfx = "application/x-pkcs12";
		public static final String pgm = "image/x-portable-graymap";
		public static final String pgn = "application/x-chess-pgn";
		public static final String pgp = "application/pgp-encrypted";
		public static final String pic = "image/pict";
		public static final String pict = "image/pict";
		public static final String pkg = "application/octet-stream";
		public static final String pki = "application/pkixcmp";
		public static final String pkipath = "application/pkix-pkipath";
		public static final String plb = "application/vnd.3gpp.pic-bw-large";
		public static final String plc = "application/vnd.mobius.plc";
		public static final String plf = "application/vnd.pocketlearn";
		public static final String pls = "audio/x-scpls";
		public static final String pml = "application/vnd.ctc-posml";
		public static final String png = "image/png";
		public static final String pnm = "image/x-portable-anymap";
		public static final String pnt = "image/x-macpaint";
		public static final String portpkg = "application/vnd.macports.portpkg";
		public static final String pot = "application/vnd.ms-powerpoint";
		public static final String potm = "application/vnd.ms-powerpoint.template.macroenabled.12";
		public static final String potx = "application/vnd.openxmlformats-officedocument.presentationml.template";
		public static final String ppam = "application/vnd.ms-powerpoint.addin.macroenabled.12";
		public static final String ppd = "application/vnd.cups-ppd";
		public static final String ppm = "image/x-portable-pixmap";
		public static final String pps = "application/vnd.ms-powerpoint";
		public static final String ppsm = "application/vnd.ms-powerpoint.slideshow.macroenabled.12";
		public static final String ppsx = "application/vnd.openxmlformats-officedocument.presentationml.slideshow";
		public static final String ppt = "application/vnd.ms-powerpoint";
		public static final String pptm = "application/vnd.ms-powerpoint.presentation.macroenabled.12";
		public static final String pptx = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
		public static final String pqa = "application/vnd.palm";
		public static final String prc = "application/x-mobipocket-ebook";
		public static final String pre = "application/vnd.lotus-freelance";
		public static final String prf = "application/pics-rules";
		public static final String ps = "application/postscript";
		public static final String psb = "application/vnd.3gpp.pic-bw-small";
		public static final String psd = "image/vnd.adobe.photoshop";
		public static final String psf = "application/x-font-linux-psf";
		public static final String pskcxml = "application/pskc+xml";
		public static final String ptid = "application/vnd.pvi.ptid1";
		public static final String pub = "application/x-mspublisher";
		public static final String pvb = "application/vnd.3gpp.pic-bw-var";
		public static final String pwn = "application/vnd.3m.post-it-notes";
		public static final String pya = "audio/vnd.ms-playready.media.pya";
		public static final String pyv = "video/vnd.ms-playready.media.pyv";
		public static final String qam = "application/vnd.epson.quickanime";
		public static final String qbo = "application/vnd.intu.qbo";
		public static final String qfx = "application/vnd.intu.qfx";
		public static final String qps = "application/vnd.publishare-delta-tree";
		public static final String qt = "video/quicktime";
		public static final String qti = "image/x-quicktime";
		public static final String qtif = "image/x-quicktime";
		public static final String qwd = "application/vnd.quark.quarkxpress";
		public static final String qwt = "application/vnd.quark.quarkxpress";
		public static final String qxb = "application/vnd.quark.quarkxpress";
		public static final String qxd = "application/vnd.quark.quarkxpress";
		public static final String qxl = "application/vnd.quark.quarkxpress";
		public static final String qxt = "application/vnd.quark.quarkxpress";
		public static final String ra = "audio/x-pn-realaudio";
		public static final String ram = "audio/x-pn-realaudio";
		public static final String rar = "application/x-rar-compressed";
		public static final String ras = "image/x-cmu-raster";
		public static final String rcprofile = "application/vnd.ipunplugged.rcprofile";
		public static final String rdf = "application/rdf+xml";
		public static final String rdz = "application/vnd.data-vision.rdz";
		public static final String rep = "application/vnd.businessobjects";
		public static final String res = "application/x-dtbresource+xml";
		public static final String rgb = "image/x-rgb";
		public static final String rif = "application/reginfo+xml";
		public static final String rip = "audio/vnd.rip";
		public static final String ris = "application/x-research-info-systems";
		public static final String rl = "application/resource-lists+xml";
		public static final String rlc = "image/vnd.fujixerox.edmics-rlc";
		public static final String rld = "application/resource-lists-diff+xml";
		public static final String rm = "application/vnd.rn-realmedia";
		public static final String rmi = "audio/midi";
		public static final String rmp = "audio/x-pn-realaudio-plugin";
		public static final String rms = "application/vnd.jcp.javame.midlet-rms";
		public static final String rmvb = "application/vnd.rn-realmedia-vbr";
		public static final String rnc = "application/relax-ng-compact-syntax";
		public static final String roa = "application/rpki-roa";
		public static final String roff = "text/troff";
		public static final String rp9 = "application/vnd.cloanto.rp9";
		public static final String rpss = "application/vnd.nokia.radio-presets";
		public static final String rpst = "application/vnd.nokia.radio-preset";
		public static final String rq = "application/sparql-query";
		public static final String rs = "application/rls-services+xml";
		public static final String rsd = "application/rsd+xml";
		public static final String rss = "application/rss+xml";
		public static final String rtf = "application/rtf";
		public static final String rtx = "text/richtext";
		public static final String s = "text/x-asm";
		public static final String s3m = "audio/s3m";
		public static final String saf = "application/vnd.yamaha.smaf-audio";
		public static final String sbml = "application/sbml+xml";
		public static final String sc = "application/vnd.ibm.secure-container";
		public static final String scd = "application/x-msschedule";
		public static final String scm = "application/vnd.lotus-screencam";
		public static final String scq = "application/scvp-cv-request";
		public static final String scs = "application/scvp-cv-response";
		public static final String scurl = "text/vnd.curl.scurl";
		public static final String sda = "application/vnd.stardivision.draw";
		public static final String sdc = "application/vnd.stardivision.calc";
		public static final String sdd = "application/vnd.stardivision.impress";
		public static final String sdkd = "application/vnd.solent.sdkm+xml";
		public static final String sdkm = "application/vnd.solent.sdkm+xml";
		public static final String sdp = "application/sdp";
		public static final String sdw = "application/vnd.stardivision.writer";
		public static final String see = "application/vnd.seemail";
		public static final String seed = "application/vnd.fdsn.seed";
		public static final String sema = "application/vnd.sema";
		public static final String semd = "application/vnd.semd";
		public static final String semf = "application/vnd.semf";
		public static final String ser = "application/java-serialized-object";
		public static final String setpay = "application/set-payment-initiation";
		public static final String setreg = "application/set-registration-initiation";
		public static final String sfd_hdstx = "application/vnd.hydrostatix.sof-data";
		public static final String sfs = "application/vnd.spotfire.sfs";
		public static final String sfv = "text/x-sfv";
		public static final String sgi = "image/sgi";
		public static final String sgl = "application/vnd.stardivision.writer-global";
		public static final String sgm = "text/sgml";
		public static final String sgml = "text/sgml";
		public static final String sh = "application/x-sh";
		public static final String shar = "application/x-shar";
		public static final String shf = "application/shf+xml";
		public static final String sig = "application/pgp-signature";
		public static final String sil = "audio/silk";
		public static final String silo = "model/mesh";
		public static final String sis = "application/vnd.symbian.install";
		public static final String sisx = "application/vnd.symbian.install";
		public static final String sit = "application/x-stuffit";
		public static final String sitx = "application/x-stuffitx";
		public static final String skd = "application/vnd.koan";
		public static final String skm = "application/vnd.koan";
		public static final String skp = "application/vnd.koan";
		public static final String skt = "application/vnd.koan";
		public static final String sldm = "application/vnd.ms-powerpoint.slide.macroenabled.12";
		public static final String sldx = "application/vnd.openxmlformats-officedocument.presentationml.slide";
		public static final String slt = "application/vnd.epson.salt";
		public static final String sm = "application/vnd.stepmania.stepchart";
		public static final String smf = "application/vnd.stardivision.math";
		public static final String smi = "application/smil+xml";
		public static final String smil = "application/smil+xml";
		public static final String smv = "video/x-smv";
		public static final String smzip = "application/vnd.stepmania.package";
		public static final String snd = "audio/basic";
		public static final String snf = "application/x-font-snf";
		public static final String so = "application/octet-stream";
		public static final String spc = "application/x-pkcs7-certificates";
		public static final String spf = "application/vnd.yamaha.smaf-phrase";
		public static final String spl = "application/x-futuresplash";
		public static final String spot = "text/vnd.in3d.spot";
		public static final String spp = "application/scvp-vp-response";
		public static final String spq = "application/scvp-vp-request";
		public static final String spx = "audio/ogg";
		public static final String sql = "application/x-sql";
		public static final String src = "application/x-wais-source";
		public static final String srt = "application/x-subrip";
		public static final String sru = "application/sru+xml";
		public static final String srx = "application/sparql-results+xml";
		public static final String ssdl = "application/ssdl+xml";
		public static final String sse = "application/vnd.kodak-descriptor";
		public static final String ssf = "application/vnd.epson.ssf";
		public static final String ssml = "application/ssml+xml";
		public static final String st = "application/vnd.sailingtracker.track";
		public static final String stc = "application/vnd.sun.xml.calc.template";
		public static final String std = "application/vnd.sun.xml.draw.template";
		public static final String stf = "application/vnd.wt.stf";
		public static final String sti = "application/vnd.sun.xml.impress.template";
		public static final String stk = "application/hyperstudio";
		public static final String stl = "application/vnd.ms-pki.stl";
		public static final String str = "application/vnd.pg.format";
		public static final String stw = "application/vnd.sun.xml.writer.template";
		public static final String sub = "text/vnd.dvb.subtitle";
		public static final String sus = "application/vnd.sus-calendar";
		public static final String susp = "application/vnd.sus-calendar";
		public static final String sv4cpio = "application/x-sv4cpio";
		public static final String sv4crc = "application/x-sv4crc";
		public static final String svc = "application/vnd.dvb.service";
		public static final String svd = "application/vnd.svd";
		public static final String svg = "image/svg+xml";
		public static final String svgz = "image/svg+xml";
		public static final String swa = "application/x-director";
		public static final String swf = "application/x-shockwave-flash";
		public static final String swi = "application/vnd.aristanetworks.swi";
		public static final String sxc = "application/vnd.sun.xml.calc";
		public static final String sxd = "application/vnd.sun.xml.draw";
		public static final String sxg = "application/vnd.sun.xml.writer.global";
		public static final String sxi = "application/vnd.sun.xml.impress";
		public static final String sxm = "application/vnd.sun.xml.math";
		public static final String sxw = "application/vnd.sun.xml.writer";
		public static final String t = "text/troff";
		public static final String t3 = "application/x-t3vm-image";
		public static final String taglet = "application/vnd.mynfc";
		public static final String tao = "application/vnd.tao.intent-module-archive";
		public static final String tar = "application/x-tar";
		public static final String tcap = "application/vnd.3gpp2.tcap";
		public static final String tcl = "application/x-tcl";
		public static final String teacher = "application/vnd.smart.teacher";
		public static final String tei = "application/tei+xml";
		public static final String teicorpus = "application/tei+xml";
		public static final String tex = "application/x-tex";
		public static final String texi = "application/x-texinfo";
		public static final String texinfo = "application/x-texinfo";
		public static final String text = "text/plain";
		public static final String tfi = "application/thraud+xml";
		public static final String tfm = "application/x-tex-tfm";
		public static final String tga = "image/x-tga";
		public static final String thmx = "application/vnd.ms-officetheme";
		public static final String tif = "image/tiff";
		public static final String tiff = "image/tiff";
		public static final String tmo = "application/vnd.tmobile-livetv";
		public static final String torrent = "application/x-bittorrent";
		public static final String tpl = "application/vnd.groove-tool-template";
		public static final String tpt = "application/vnd.trid.tpt";
		public static final String tr = "text/troff";
		public static final String tra = "application/vnd.trueapp";
		public static final String trm = "application/x-msterminal";
		public static final String tsd = "application/timestamped-data";
		public static final String tsv = "text/tab-separated-values";
		public static final String ttc = "application/x-font-ttf";
		public static final String ttf = "application/x-font-ttf";
		public static final String ttl = "text/turtle";
		public static final String twd = "application/vnd.simtech-mindmapper";
		public static final String twds = "application/vnd.simtech-mindmapper";
		public static final String txd = "application/vnd.genomatix.tuxedo";
		public static final String txf = "application/vnd.mobius.txf";
		public static final String txt = "text/plain";
		public static final String u32 = "application/x-authorware-bin";
		public static final String udeb = "application/x-debian-package";
		public static final String ufd = "application/vnd.ufdl";
		public static final String ufdl = "application/vnd.ufdl";
		public static final String ulw = "audio/basic";
		public static final String ulx = "application/x-glulx";
		public static final String umj = "application/vnd.umajin";
		public static final String unityweb = "application/vnd.unity";
		public static final String uoml = "application/vnd.uoml+xml";
		public static final String uri = "text/uri-list";
		public static final String uris = "text/uri-list";
		public static final String urls = "text/uri-list";
		public static final String ustar = "application/x-ustar";
		public static final String utz = "application/vnd.uiq.theme";
		public static final String uu = "text/x-uuencode";
		public static final String uva = "audio/vnd.dece.audio";
		public static final String uvd = "application/vnd.dece.data";
		public static final String uvf = "application/vnd.dece.data";
		public static final String uvg = "image/vnd.dece.graphic";
		public static final String uvh = "video/vnd.dece.hd";
		public static final String uvi = "image/vnd.dece.graphic";
		public static final String uvm = "video/vnd.dece.mobile";
		public static final String uvp = "video/vnd.dece.pd";
		public static final String uvs = "video/vnd.dece.sd";
		public static final String uvt = "application/vnd.dece.ttml+xml";
		public static final String uvu = "video/vnd.uvvu.mp4";
		public static final String uvv = "video/vnd.dece.video";
		public static final String uvva = "audio/vnd.dece.audio";
		public static final String uvvd = "application/vnd.dece.data";
		public static final String uvvf = "application/vnd.dece.data";
		public static final String uvvg = "image/vnd.dece.graphic";
		public static final String uvvh = "video/vnd.dece.hd";
		public static final String uvvi = "image/vnd.dece.graphic";
		public static final String uvvm = "video/vnd.dece.mobile";
		public static final String uvvp = "video/vnd.dece.pd";
		public static final String uvvs = "video/vnd.dece.sd";
		public static final String uvvt = "application/vnd.dece.ttml+xml";
		public static final String uvvu = "video/vnd.uvvu.mp4";
		public static final String uvvv = "video/vnd.dece.video";
		public static final String uvvx = "application/vnd.dece.unspecified";
		public static final String uvvz = "application/vnd.dece.zip";
		public static final String uvx = "application/vnd.dece.unspecified";
		public static final String uvz = "application/vnd.dece.zip";
		public static final String vcard = "text/vcard";
		public static final String vcd = "application/x-cdlink";
		public static final String vcf = "text/x-vcard";
		public static final String vcg = "application/vnd.groove-vcard";
		public static final String vcs = "text/x-vcalendar";
		public static final String vcx = "application/vnd.vcx";
		public static final String vis = "application/vnd.visionary";
		public static final String viv = "video/vnd.vivo";
		public static final String vob = "video/x-ms-vob";
		public static final String vor = "application/vnd.stardivision.writer";
		public static final String vox = "application/x-authorware-bin";
		public static final String vrml = "model/vrml";
		public static final String vsd = "application/vnd.visio";
		public static final String vsf = "application/vnd.vsf";
		public static final String vss = "application/vnd.visio";
		public static final String vst = "application/vnd.visio";
		public static final String vsw = "application/vnd.visio";
		public static final String vtu = "model/vnd.vtu";
		public static final String vxml = "application/voicexml+xml";
		public static final String w3d = "application/x-director";
		public static final String wad = "application/x-doom";
		public static final String wav = "audio/x-wav";
		public static final String wax = "audio/x-ms-wax";
		public static final String wbmp = "image/vnd.wap.wbmp";
		public static final String wbs = "application/vnd.criticaltools.wbs+xml";
		public static final String wbxml = "application/vnd.wap.wbxml";
		public static final String wcm = "application/vnd.ms-works";
		public static final String wdb = "application/vnd.ms-works";
		public static final String wdp = "image/vnd.ms-photo";
		public static final String weba = "audio/webm";
		public static final String webm = "video/webm";
		public static final String webp = "image/webp";
		public static final String wg = "application/vnd.pmi.widget";
		public static final String wgt = "application/widget";
		public static final String wks = "application/vnd.ms-works";
		public static final String wm = "video/x-ms-wm";
		public static final String wma = "audio/x-ms-wma";
		public static final String wmd = "application/x-ms-wmd";
		public static final String wmf = "application/x-msmetafile";
		public static final String wml = "text/vnd.wap.wml";
		public static final String wmlc = "application/vnd.wap.wmlc";
		public static final String wmls = "text/vnd.wap.wmlscript";
		public static final String wmlsc = "application/vnd.wap.wmlscriptc";
		public static final String wmv = "video/x-ms-wmv";
		public static final String wmx = "video/x-ms-wmx";
		public static final String wmz = "application/x-msmetafile";
		public static final String woff = "application/x-font-woff";
		public static final String wpd = "application/vnd.wordperfect";
		public static final String wpl = "application/vnd.ms-wpl";
		public static final String wps = "application/vnd.ms-works";
		public static final String wqd = "application/vnd.wqd";
		public static final String wri = "application/x-mswrite";
		public static final String wrl = "model/vrml";
		public static final String wsdl = "application/wsdl+xml";
		public static final String wspolicy = "application/wspolicy+xml";
		public static final String wtb = "application/vnd.webturbo";
		public static final String wvx = "video/x-ms-wvx";
		public static final String x32 = "application/x-authorware-bin";
		public static final String x3d = "model/x3d+xml";
		public static final String x3db = "model/x3d+binary";
		public static final String x3dbz = "model/x3d+binary";
		public static final String x3dv = "model/x3d+vrml";
		public static final String x3dvz = "model/x3d+vrml";
		public static final String x3dz = "model/x3d+xml";
		public static final String xaml = "application/xaml+xml";
		public static final String xap = "application/x-silverlight-app";
		public static final String xar = "application/vnd.xara";
		public static final String xbap = "application/x-ms-xbap";
		public static final String xbd = "application/vnd.fujixerox.docuworks.binder";
		public static final String xbm = "image/x-xbitmap";
		public static final String xdf = "application/xcap-diff+xml";
		public static final String xdm = "application/vnd.syncml.dm+xml";
		public static final String xdp = "application/vnd.adobe.xdp+xml";
		public static final String xdssc = "application/dssc+xml";
		public static final String xdw = "application/vnd.fujixerox.docuworks";
		public static final String xenc = "application/xenc+xml";
		public static final String xer = "application/patch-ops-error+xml";
		public static final String xfdf = "application/vnd.adobe.xfdf";
		public static final String xfdl = "application/vnd.xfdl";
		public static final String xht = "application/xhtml+xml";
		public static final String xhtml = "application/xhtml+xml";
		public static final String xhvml = "application/xv+xml";
		public static final String xif = "image/vnd.xiff";
		public static final String xla = "application/vnd.ms-excel";
		public static final String xlam = "application/vnd.ms-excel.addin.macroenabled.12";
		public static final String xlc = "application/vnd.ms-excel";
		public static final String xlf = "application/x-xliff+xml";
		public static final String xlm = "application/vnd.ms-excel";
		public static final String xls = "application/vnd.ms-excel";
		public static final String xlsb = "application/vnd.ms-excel.sheet.binary.macroenabled.12";
		public static final String xlsm = "application/vnd.ms-excel.sheet.macroenabled.12";
		public static final String xlsx = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		public static final String xlt = "application/vnd.ms-excel";
		public static final String xltm = "application/vnd.ms-excel.template.macroenabled.12";
		public static final String xltx = "application/vnd.openxmlformats-officedocument.spreadsheetml.template";
		public static final String xlw = "application/vnd.ms-excel";
		public static final String xm = "audio/xm";
		public static final String xml = "application/xml";
		public static final String xo = "application/vnd.olpc-sugar";
		public static final String xop = "application/xop+xml";
		public static final String xpi = "application/x-xpinstall";
		public static final String xpl = "application/xproc+xml";
		public static final String xpm = "image/x-xpixmap";
		public static final String xpr = "application/vnd.is-xpr";
		public static final String xps = "application/vnd.ms-xpsdocument";
		public static final String xpw = "application/vnd.intercon.formnet";
		public static final String xpx = "application/vnd.intercon.formnet";
		public static final String xsl = "application/xml";
		public static final String xslt = "application/xslt+xml";
		public static final String xsm = "application/vnd.syncml+xml";
		public static final String xspf = "application/xspf+xml";
		public static final String xul = "application/vnd.mozilla.xul+xml";
		public static final String xvm = "application/xv+xml";
		public static final String xvml = "application/xv+xml";
		public static final String xwd = "image/x-xwindowdump";
		public static final String xyz = "chemical/x-xyz";
		public static final String xz = "application/x-xz";
		public static final String yang = "application/yang";
		public static final String yin = "application/yin+xml";
		public static final String z = "application/x-compress";
		public static final String Z = "application/x-compress";
		public static final String z1 = "application/x-zmachine";
		public static final String z2 = "application/x-zmachine";
		public static final String z3 = "application/x-zmachine";
		public static final String z4 = "application/x-zmachine";
		public static final String z5 = "application/x-zmachine";
		public static final String z6 = "application/x-zmachine";
		public static final String z7 = "application/x-zmachine";
		public static final String z8 = "application/x-zmachine";
		public static final String zaz = "application/vnd.zzazz.deck+xml";
		public static final String zip = "application/zip";
		public static final String zir = "application/vnd.zul";
		public static final String zirz = "application/vnd.zul";

	}
}
