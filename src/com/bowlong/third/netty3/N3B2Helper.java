package com.bowlong.third.netty3;

import java.util.List;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import com.bowlong.util.NewList;
import com.bowlong.util.NewMap;

@SuppressWarnings("rawtypes")
public class N3B2Helper {
	public static final byte[] toBytes(Map o) throws Exception {
		ChannelBuffer os = ChannelBufferPool.borrowObject();
		try {
			toBytes(os, o);
			int offset = os.writerIndex();
			byte[] result = new byte[offset];
			os.getBytes(0, result, 0, offset);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			ChannelBufferPool.returnObject(os);
		}
	}

	public static final void toBytes(ChannelBuffer os, Map o) throws Exception {
		N3B2OutputStream.writeObject(os, o);
	}

	public static final NewMap toMap(ChannelBuffer is) throws Exception {
		NewMap v = (NewMap) N3B2InputStream.readObject(is);
		return v;
	}

	public static final NewMap toNewMapNoExcept(final ChannelBuffer buf){
		NewMap ret = null;
		try {
			ret = toNewMap(buf);
		} catch (Exception e) {
		}
		return ret = (ret == null) ? new NewMap() : ret;
	}

	public static final NewMap toNewMap(final ChannelBuffer buf) throws Exception {
		NewMap v = N3B2InputStream.readNewMap(buf);
		return v;
	}

	// ////////////////////
	public static final byte[] toBytes(List o) throws Exception {
		ChannelBuffer os = ChannelBufferPool.borrowObject();
		try {

			toBytes(os, o);
			int offset = os.writerIndex();
			byte[] result = new byte[offset];
			os.getBytes(0, result, 0, offset);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			ChannelBufferPool.returnObject(os);
		}
	}

	public static final void toBytes(ChannelBuffer os, List o) throws Exception {
		N3B2OutputStream.writeObject(os, o);
	}

	public static final NewList toList(ChannelBuffer is) throws Exception {
		NewList v = (NewList) N3B2InputStream.readObject(is);
		return v;
	}

}
