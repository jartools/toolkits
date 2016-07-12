package com.bowlong.third.netty3.httphand;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.handler.codec.http.DefaultHttpChunk;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;

import com.bowlong.third.netty3.ChannelBufferPool;
import com.bowlong.third.netty3.N3B2Helper;

public class N3HttpResponse extends N3HttpResp {

	private static final long serialVersionUID = 1L;

	static Log log = LogFactory.getLog(N3HttpResponse.class);

	/** 需要关闭的地方才关闭 */
	static public void closeChn(Channel chn) {
		closeChn(chn, true);
	}

	/** 需要关闭的地方才关闭 */
	static public void closeChn(Channel chn, boolean isCan) {
		if (!isCan)
			return;
		try {
			chn.disconnect();
			chn.close();
		} catch (Exception e) {
		}
	}

	public static void send(Channel chn, Map<Object, Object> params)
			throws Exception {

		ChannelBuffer cb = ChannelBufferPool.borrowObject();
		N3B2Helper.toBytes(cb, params);
		int length = cb.writerIndex();
		byte[] dst = new byte[length];
		cb.getBytes(0, dst, 0, length);
		send(chn, dst);

		ChannelBufferPool.returnObject(cb);
	}

	public static void send(Channel chn, String content) throws Exception {
		if (content == null) {
			content = "";
		}

		byte[] buff = content.getBytes("UTF-8");
		send(chn, buff);
	}

	// 直接写内容
	public static void send(Channel chn, byte[] buff) throws Exception {
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,
				HttpResponseStatus.OK);
		int len = buff.length;
		ChannelBuffer cb = new DynamicChannelBuffer(len);
		cb.writeBytes(buff);
		response.setContent(cb);
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		int resLen = response.getContent().writerIndex();
		System.out.println(resLen + "===" + len);
		response.setHeader("Content-Length", resLen);
		ChannelFuture f = chn.write(response);
		f.addListener(ChannelFutureListener.CLOSE);
		// closeChannel(chn);
	}

	public static void sendJson(Channel chn, String content) throws Exception {
		if (content == null) {
			content = "";
		}

		byte[] buff = content.getBytes("UTF-8");
		sendJson(chn, buff);
	}

	// 直接写内容JSON
	public static void sendJson(Channel chn, byte[] buff) throws Exception {
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,
				HttpResponseStatus.OK);
		int len = buff.length;
		ChannelBuffer cb = new DynamicChannelBuffer(len);
		cb.writeBytes(buff);
		response.setContent(cb);
		response.setHeader("Content-Type", "application/Json; charset=UTF-8");
		int resLen = response.getContent().writerIndex();
		System.out.println(resLen + "===" + len);
		response.setHeader("Content-Length", resLen);
		ChannelFuture f = chn.write(response);
		f.addListener(ChannelFutureListener.CLOSE);
		// closeChannel(chn);
	}

	public static void sendByChunked(Channel chn, String content)
			throws Exception {
		if (content == null) {
			content = "";
		}

		byte[] buff = content.getBytes("UTF-8");
		sendByChunked(chn, buff);
	}

	// 直接写内容
	public static void sendByChunked(Channel chn, byte[] buff) throws Exception {
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,
				HttpResponseStatus.OK);
		response.setChunked(true);
		response.setHeader(HttpHeaders.Names.TRANSFER_ENCODING,
				HttpHeaders.Values.CHUNKED);
		ChannelFuture f = chn.write(response);

		int len = buff.length;
		ChannelBuffer cb = new DynamicChannelBuffer(len);
		cb.writeBytes(buff);
		HttpChunk chunk = new DefaultHttpChunk(cb);
		f = chn.write(chunk);

		HttpChunk chunk2 = new DefaultHttpChunk(ChannelBuffers.EMPTY_BUFFER);
		f = chn.write(chunk2);
		f.addListener(ChannelFutureListener.CLOSE);
	}

	// 直接Head
	public static void sendHead(Channel chn, String head) throws Exception {
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,
				HttpResponseStatus.OK);
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		response.setHeader("cpparam", head);
		int resLen = response.getContent().writerIndex();
		response.setHeader("Content-Length", resLen);
		ChannelFuture f = chn.write(response);
		f.addListener(ChannelFutureListener.CLOSE);
		// closeChannel(chn);
	}
}
