package com.bowlong.third.netty3.socket;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerBossPool;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioWorkerPool;

public class N3BootstrapSocketServer{
	static Log log = LogFactory.getLog(N3BootstrapSocketServer.class);

	public static void start(final String host, final int port,
			final boolean nodelay, final boolean alive,
			ChannelPipelineFactory pipeline) {

		ServerBootstrap bstrap = initServer(nodelay, alive, pipeline);
		bindServerSocketAddress(host, port, bstrap);

	}

	public static ServerBootstrap initServer(final boolean nodelay,
			final boolean alive, ChannelPipelineFactory pipeline) {

		ExecutorService executor = Executors.newCachedThreadPool();
		NioServerBossPool bossPool = new NioServerBossPool(executor, 2);
		NioWorkerPool workerPool = new NioWorkerPool(executor, 32);
		ChannelFactory factory = new NioServerSocketChannelFactory(bossPool,
				workerPool);

		ServerBootstrap bstrap = new ServerBootstrap(factory);

		bstrap.setPipelineFactory(pipeline);

		bstrap.setOption("child.tcpNoDelay", nodelay);
		bstrap.setOption("child.keepAlive", alive);

		return bstrap;
	}

	public static Channel bindServerSocketAddress(final String host,
			final int port, ServerBootstrap bstrap) {
		InetSocketAddress addr_ = null;
		if (host == null || "".equals(host)) {
			addr_ = new InetSocketAddress(port);
		} else {
			addr_ = new InetSocketAddress(host, port);
		}
		Channel chn = bstrap.bind(addr_);
		return chn;
	}
}