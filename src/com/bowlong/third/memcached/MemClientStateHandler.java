package com.bowlong.third.memcached;

import java.net.InetSocketAddress;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientStateListener;

public class MemClientStateHandler implements MemcachedClientStateListener {

	@Override
	public void onConnected(MemcachedClient mc, InetSocketAddress addr) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onDisconnected(MemcachedClient mc, InetSocketAddress addr) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onException(MemcachedClient mc, Throwable e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onShutDown(MemcachedClient mc) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStarted(MemcachedClient mc) {
		// TODO Auto-generated method stub
	}

}
