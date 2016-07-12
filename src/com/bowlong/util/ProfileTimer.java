package com.bowlong.util;

public class ProfileTimer {
	public final void start() {
		start_ = System.currentTimeMillis();
	}

	public final void stop() {
		stop_ = System.currentTimeMillis();
	}

	public final long elapsed() {
		return stop_ - start_;
	}

	public final long stopElapsed() {
		stop();
		return elapsed();
	}

	public final long stopShow() {
		stop();
		long e = elapsed();
		System.out.println(e);
		return e;
	}

	private long start_;
	private long stop_;
}
