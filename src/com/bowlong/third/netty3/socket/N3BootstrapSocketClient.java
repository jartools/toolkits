package com.bowlong.third.netty3.socket;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioClientBossPool;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioWorkerPool;

public class N3BootstrapSocketClient{
	static Log log = LogFactory.getLog(N3BootstrapSocketClient.class);

	public static void start(final String host, final int port,
			final boolean nodelay, final boolean alive,
			ChannelPipelineFactory pipeline) {

		ClientBootstrap bstrap = initClient(nodelay, alive, pipeline);
		connetServer(host, port, bstrap);
	}

	public static ClientBootstrap initClient(final boolean nodelay,
			final boolean alive, ChannelPipelineFactory pipeline) {

		ExecutorService executor = Executors.newCachedThreadPool();
		NioClientBossPool bossPool = new NioClientBossPool(executor, 2);
		NioWorkerPool workerPool = new NioWorkerPool(executor, 32);
		ChannelFactory factory = new NioClientSocketChannelFactory(bossPool,
				workerPool);

		ClientBootstrap bstrap = new ClientBootstrap(factory);

		bstrap.setPipelineFactory(pipeline);

		bstrap.setOption("child.tcpNoDelay", nodelay);
		bstrap.setOption("child.keepAlive", alive);
		return bstrap;
	}

	public static ChannelFuture connetServer(final String host, final int port,
			ClientBootstrap bstrap) {
		InetSocketAddress addr_ = null;
		if (host == null || "".equals(host)) {
			addr_ = new InetSocketAddress(port);
		} else {
			addr_ = new InetSocketAddress(host, port);
		}
		ChannelFuture future = bstrap.connect(addr_);
		future.awaitUninterruptibly();
		return future;
	}
}