package com.bowlong.net.http;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("all")
public interface Servlet {
	public void init() throws IOException;

	public void service(HttpExchange exchange) throws IOException;

	public void destroy();
}
