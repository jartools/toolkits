package com.bowlong.net.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
@SuppressWarnings("all")
public class WebServer {
	
	public final void start(final HttpHandler handler) throws IOException {
		final int port = 8080;
		start(port, handler);
		System.out.println("Server is listening on port 8080");
	}

	public final void start(final int port, final HttpHandler handler)
			throws IOException {
		final int backlog = 0;
		start(port, backlog, handler);
	}

	public final void start(final int port, final int backlog,
			final HttpHandler handler) throws IOException {
		InetSocketAddress addr = new InetSocketAddress(port);
		HttpServer server = HttpServer.create(addr, backlog);

		server.createContext("/", handler);
		server.setExecutor(Executors.newCachedThreadPool());
		server.start();
		// System.out.println("Server is listening on port 8080");
	}

	// public static void main(String[] args) throws IOException {
	// InetSocketAddress addr = new InetSocketAddress(8080);
	// HttpServer server = HttpServer.create(addr, 0);
	//
	// server.createContext("/", new MyHandler());
	// server.setExecutor(Executors.newCachedThreadPool());
	// server.start();
	// System.out.println("Server is listening on port 8080");
	// }
}

// class MyHandler implements HttpHandler {
// public void handle(HttpExchange exchange) throws IOException {
// String requestMethod = exchange.getRequestMethod();
// if (requestMethod.equalsIgnoreCase("GET")) {
// Headers responseHeaders = exchange.getResponseHeaders();
// responseHeaders.set("Content-Type", "text/plain");
// exchange.sendResponseHeaders(200, 0);
//
// OutputStream responseBody = exchange.getResponseBody();
// Headers requestHeaders = exchange.getRequestHeaders();
// Set<String> keySet = requestHeaders.keySet();
// Iterator<String> iter = keySet.iterator();
// while (iter.hasNext()) {
// String key = iter.next();
// List values = requestHeaders.get(key);
// String s = key + " = " + values.toString() + "\n";
// responseBody.write(s.getBytes());
// }
// responseBody.close();
// }
// }
// }