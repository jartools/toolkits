package com.bowlong.bio1x;

import java.io.InputStream;
import java.io.OutputStream;

public interface BIOSerial {

	public void _readObject(InputStream input) throws Exception;

	public int _writeObject(OutputStream output) throws Exception;
	
}
