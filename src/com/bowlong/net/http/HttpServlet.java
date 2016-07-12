package com.bowlong.net.http;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
@SuppressWarnings("all")
public class HttpServlet implements Servlet {
	public static final String METHOD_DELETE = "DELETE";
	public static final String METHOD_HEAD = "HEAD";
	public static final String METHOD_GET = "GET";
	public static final String METHOD_OPTIONS = "OPTIONS";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_PUT = "PUT";
	public static final String METHOD_TRACE = "TRACE";

	public static final String HEADER_IFMODSINCE = "If-Modified-Since";
	public static final String HEADER_LASTMOD = "Last-Modified";

	public HttpServlet() {
	}

	protected void doGet(HttpExchange exchange) throws IOException {
	}

	protected void doPost(HttpExchange exchange) throws IOException {
	}

	@Override
	public void service(HttpExchange exchange) throws IOException {
		String method = exchange.getRequestMethod();
		if (method.equals(METHOD_GET)) {
			doGet(exchange);
		} else if (method.equals(METHOD_POST)) {
			doPost(exchange);
		}
	}

	@Override
	public void init() throws IOException {
	}

	@Override
	public void destroy() {
	}
}
