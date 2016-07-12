package com.bowlong.net.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;

import com.bowlong.io.FileCache;
import com.bowlong.third.servlet.UISupport;
import com.bowlong.util.LRUCache;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
@SuppressWarnings("all")
public class WebFileHandler implements HttpHandler {
	static final Map<String, FileCache> CACHE = new LRUCache<>(128);

	public void handle(HttpExchange exchange) throws IOException {
		String requestMethod = exchange.getRequestMethod();
		if (requestMethod.equals(UISupport.Method.GET)) {
			URI uri = exchange.getRequestURI();
			String fname = "." + uri.toString();

			byte[] content = null;
			FileCache fc = CACHE.get(fname);
			if (fc != null) {
				content = fc.getData();
			} else {
				try {
					fc = new FileCache(fname);
					content = fc.getData();
					CACHE.put(fname, fc);
				} catch (Exception e) {
					content = null;
				}
			}

			Headers responseHeaders = exchange.getResponseHeaders();
			OutputStream responseBody = exchange.getResponseBody();
			if (content == null || content.length <= 0) {
				responseHeaders.set(UISupport.ContentType,
						UISupport.MimeType.text);
				exchange.sendResponseHeaders(UISupport.Code.SC_NOT_FOUND, 0);
				responseBody.close();
				return;
			} else {
				responseHeaders.set(UISupport.ContentType,
						UISupport.MimeType.mid);
				responseHeaders.set(UISupport.ContentLength, content.length
						+ "");
				exchange.sendResponseHeaders(200, 0);
				responseBody = exchange.getResponseBody();
				responseBody.write(content);
				responseBody.close();
				return;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		WebServer ws = new WebServer();
		ws.start(new WebFileHandler());
	}
}
