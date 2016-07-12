package com.bowlong.third.spring;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

@SuppressWarnings("unchecked")
public abstract class SpringContext {

	protected static final String CONFIG = "app.xml";
	protected static final String PROPERTIES = "app.properties";

	// protected static ApplicationContext context = new
	// FileSystemXmlApplicationContext(
	// CONFIG);
	// protected static ApplicationContext ctx = new
	// ClassPathXmlApplicationContext(
	// CONFIG);

	protected static ApplicationContext ctx;

	// protected static File fp = new File(CONFIG);
	// protected static File fp2 = new File(PROPERTIES);

	// protected static long ctx_lastcheck = 0;
	// protected static long ctx_lastload = 0;
	// private static long elapsed = 1 * 10 * 1000;

	// private static long now() {
	// return System.currentTimeMillis();
	// }

	// private static long lastModified() {
	// long lastModified1 = fp.lastModified();
	// long lastModified2 = fp2.lastModified();
	// return lastModified1 > lastModified2 ? lastModified1 : lastModified2;
	// }

	private static ApplicationContext ctx() {
		if (ctx == null) {
			ctx = new FileSystemXmlApplicationContext(CONFIG);
			// ctx_lastload = lastModified();
			// ctx_lastcheck = now();
			// } else {
			// if (ctx_lastcheck + elapsed < now()) {
			// if (lastModified() > ctx_lastload) {
			// ctx = null;
			// ctx = new FileSystemXmlApplicationContext(CONFIG);
			// ctx_lastload = lastModified();
			// }
			// ctx_lastcheck = now();
			// }
		}
		return ctx;
	}

	public static <T> T getBean(String s) {
		return (T) ctx().getBean(s);
	}

	public static DataSource ds() {
		return getBean("ds");
	}

	public static DataSource ds2() {
		return getBean("ds2");
	}

	public static DataSource ds99() {
		return getBean("ds99");
	}

}
