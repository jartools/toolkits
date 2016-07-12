package com.bowlong.mem;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.bowlong.io.ByteOutStream;
import com.bowlong.objpool.ByteOutPool;

public class DirectMemory {
	protected static ByteBuffer alloc(int byteSize) {
		ByteBuffer buff = ByteBuffer.allocateDirect(byteSize);
		return buff;
	}

	public static void main(String[] args) throws IOException {
		try (ByteOutStream baos = ByteOutPool.borrowObject();) {
			DataOutputStream dos = new DataOutputStream(baos);
			dos.writeInt(123);
			dos.writeDouble(4.123);
			dos.flush();

			ByteBuffer buf = ByteBuffer.wrap(baos.toByteArray());
			System.out.println(buf.getInt());
			System.out.println(buf.getDouble());
		}
	}
}
