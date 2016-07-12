package com.bowlong.third.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class MemcX {
	// AddrUtil.getAddresses("server1:11211 server2:11211 server3:11211")
	protected static final String LOCAL = "127.0.0.1:11211";

	protected XMemcachedClientBuilder mcb;

	public MemcX(int poolNum) {
		this(LOCAL, poolNum);
	}

	public MemcX(final String servers, int poolNum) {
		mcb = new XMemcachedClientBuilder(AddrUtil.getAddresses(servers));
		MemcXPool.mcb = mcb;
	}

	protected final MemcachedClient mc() throws IOException {
		return MemcXPool.borrowObject();
	}

	protected final void returnMc(MemcachedClient mc) {
		MemcXPool.returnObject(mc);
	}

	// set、add或者replace。
	// 设置key对应的项为value，无论key是否已经存在
	public boolean set(final String key, final int exp, final Object value)
			throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.set(key, exp, value);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public boolean set(String key, Object value, int exp, long timeout)
			throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.set(key, exp, value, timeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	// 添加key-value缓存项，仅在key不存在的情况下才能添加成功
	public boolean add(final String key, final int exp, final Object value)
			throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.add(key, exp, value);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public boolean add(final String key, final int exp, final Object value,
			long timeout) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.add(key, exp, value, timeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	// replace表示在服务器已经拥有该关键字的情况下，替换原有内容。
	public boolean replace(final String key, final int exp, final Object value)
			throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.replace(key, exp, value);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public boolean replace(final String key, final int exp, final Object value,
			final long timeout) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.replace(key, exp, value, timeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	// 类似append，是将value附加到key对应的缓存项前面，这一操作仅对String有实际意义
	public boolean append(String key, Object value) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.append(key, value);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public boolean append(String key, Object value, long timeout)
			throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.append(key, value, timeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	// 类似append，是将value附加到key对应的缓存项前面，这一操作仅对String有实际意义
	public boolean prepend(String key, Object value) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.prepend(key, value);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public boolean prepend(String key, Object value, long timeout)
			throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.prepend(key, value, timeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	// cas原子替换key对应的value，当且仅当cas值相等的时候替换成功
	public boolean cas(final String key, final int exp, final Object value,
			final long cas) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.cas(key, exp, value, cas);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public boolean cas(final String key, final int exp, final Object value,
			final long cas, long timeout) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.cas(key, exp, value, cas, timeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	// 读取命令
	// get命令
	public Object get(String key) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.get(key);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public Object get(String key, int exp, long timeout) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.get(key, timeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	// 类似getMulti，但是返回数据项的cas值，返回的map中value存储的是GetsResponse对象
	public GetsResponse<Object> gets(String keys) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.gets(keys);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public GetsResponse<Object> gets(String keys, int timeout) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.gets(keys, timeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	// 删除命令
	public boolean delete(String key) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.delete(key);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public boolean delete(String key, long opTimeout) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.delete(key, opTimeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public boolean delete(String key, long cas, long opTimeout)
			throws Exception {
		final MemcachedClient mc = mc();
		try {
			//return mc.delete(key, cas, opTimeout);
			return mc.delete(key, cas);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	// incr命令 递增key对应的value
	public long incr(final String key, final long delta) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.incr(key, delta);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public long incr(final String key, final long delta, int num)
			throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.incr(key, delta, num);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public long incr(final String key, final long delta, int num, long timeout)
			throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.incr(key, delta, num, timeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public long incr(final String key, final long delta, int num, long timeout,
			int exp) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.incr(key, delta, num, timeout, exp);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	// decr命令 递减key对应的value
	public long decr(final String key, final long delta) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.decr(key, delta);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public long decr(final String key, final long delta, final int num)
			throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.decr(key, delta, num);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public long decr(final String key, final long delta, final int num,
			final long timeout) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.decr(key, delta, num, timeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public long decr(final String key, final long delta, final int num,
			final long timeout, int exp) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.decr(key, delta, num, timeout, exp);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	// final String key, int exp
	public boolean touch(final String key, int exp) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.touch(key, exp);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public boolean touch(final String key, int exp, long opTimeout)
			throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.touch(key, exp, opTimeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public boolean getAndTouch(final String key, int exp) throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.getAndTouch(key, exp);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public boolean getAndTouch(final String key, int exp, long opTimeout)
			throws Exception {
		final MemcachedClient mc = mc();
		try {
			return mc.getAndTouch(key, exp, opTimeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	// flush_all命令
	public void flushAll() throws Exception {
		final MemcachedClient mc = mc();
		try {
			mc.flushAll();
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public void flushAll(long timeout) throws Exception {
		final MemcachedClient mc = mc();
		try {
			mc.flushAll(timeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	// stats
	public void flushAll(InetSocketAddress address) throws Exception {
		final MemcachedClient mc = mc();
		try {
			mc.stats(address);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

	public void flushAll(InetSocketAddress address, long timeout)
			throws Exception {
		final MemcachedClient mc = mc();
		try {
			mc.stats(address, timeout);
		} catch (Exception e) {
			throw e;
		} finally {
			returnMc(mc);
		}
	}

}
