package com.bowlong.net.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.bowlong.net.http.svcs.HelloServlet;
import com.sun.net.httpserver.HttpServer;
@SuppressWarnings("all")
public class HttpServerDemo {
	public static void main(String[] args) throws IOException {
		InetSocketAddress addr = new InetSocketAddress(8080);
		HttpServer server = HttpServer.create(addr, 0);

		// 注册服务
		MyHandler.SVCs.put("/hello", new HelloServlet());

		// 注册context
		server.createContext("/", new MyHandler());
		server.setExecutor(Executors.newCachedThreadPool());
		server.start();
		System.out.println("Server is listening on port 8080");
	}
}