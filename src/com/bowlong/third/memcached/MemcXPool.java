package com.bowlong.third.memcached;

import java.io.IOException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;

import com.bowlong.objpool.AbstractQueueObjPool;

public class MemcXPool extends AbstractQueueObjPool<MemcachedClient> {

	public MemcXPool() {
	}

	public MemcXPool(int num) {
		for (int i = 0; i < num; i++) {
			returnObj(createObj());
		}
	}

	@Override
	public final MemcachedClient createObj() {
		try {
			MemcachedClient r2 = mcb.build();
//			r2.addStateListener(mcsh);
			return r2;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public final MemcachedClient resetObj(MemcachedClient mc) {
		return mc;
	}

	@Override
	public final MemcachedClient destoryObj(MemcachedClient mc) {
		try {
			mc.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mc;
	}

	// //
	public static final MemcXPool POOL = new MemcXPool();

//	public static MemClientStateHandler mcsh = new MemClientStateHandler();
	public static XMemcachedClientBuilder mcb;

	public static final MemcachedClient borrowObject() {
		return POOL.borrow();
	}

	public static final void returnObject(MemcachedClient mc) {
		POOL.returnObj(mc);
	}

}
