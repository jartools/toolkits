package com.bowlong.net;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Shutdown extends Thread {
	static Log log = LogFactory.getLog(Shutdown.class);
	public ServerSocket ssocket = null;

	public Shutdown(int port) throws Exception {
		InetAddress addr = InetAddress.getByName("127.0.0.1");

		try (Socket socket = new Socket(addr, port);) {
			socket.getInputStream().read();
			Thread.sleep(1000);
		} catch (Exception e) {
		}

		ssocket = new ServerSocket(port, 2, addr);
	}

	@Override
	public void run() {
		try {
			ssocket.accept();
			int tm = 330;
			while (true) {
				log.info("tm:" + tm);
				if (tm-- <= 0)
					break;

				Thread.sleep(1000);
			}
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
