package com.bowlong.third.groovy;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;

@SuppressWarnings("rawtypes")
public class StringTemplate extends UISupport {
	public StringTemplate(final String fn, final Charset charset)
			throws CompilationFailedException, ClassNotFoundException,
			IOException {
		super(fn, charset);
	}

	public StringTemplate(final File f, final Charset charset)
			throws CompilationFailedException, ClassNotFoundException,
			IOException {
		super(f, charset);
	}

	public StringTemplate(final String templateText)
			throws CompilationFailedException, ClassNotFoundException,
			IOException {
		super(templateText);
	}

	public String make(final Map map) throws CompilationFailedException,
			IOException {
		return super.make(map);
	}
}
