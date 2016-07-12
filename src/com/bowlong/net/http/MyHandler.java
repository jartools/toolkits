package com.bowlong.net.http;

import java.io.IOException;
import java.net.URI;
import java.util.Hashtable;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
@SuppressWarnings("all")
public class MyHandler implements HttpHandler {
	public static Hashtable<String, HttpServlet> SVCs = new Hashtable<>();

	public void handle(HttpExchange exchange) throws IOException {
		URI uri = exchange.getRequestURI();
		String path = uri.getPath();
		HttpServlet svc = SVCs.get(path);
		svc.service(exchange);

	}
}
