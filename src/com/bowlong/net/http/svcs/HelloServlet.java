package com.bowlong.net.http.svcs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bowlong.net.http.HttpServlet;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
@SuppressWarnings("all")
public class HelloServlet extends HttpServlet {

	@Override
	protected void doGet(HttpExchange exchange) throws IOException {
		String requestMethod = exchange.getRequestMethod();
		if (requestMethod.equalsIgnoreCase("GET")) {
			Headers responseHeaders = exchange.getResponseHeaders();
			responseHeaders.set("Content-Type", "text/plain");
			exchange.sendResponseHeaders(200, 0);

			OutputStream responseBody = exchange.getResponseBody();
			Headers requestHeaders = exchange.getRequestHeaders();
			Set<String> keySet = requestHeaders.keySet();
			Iterator<String> iter = keySet.iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				List<String> values = requestHeaders.get(key);
				String s = key + " = " + values.toString() + "\n";
				responseBody.write(s.getBytes());
			}
			responseBody.close();
		}
	}
}
