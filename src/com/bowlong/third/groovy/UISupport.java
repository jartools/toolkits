package com.bowlong.third.groovy;

import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;

@SuppressWarnings("rawtypes")
public class UISupport {

	protected final SimpleTemplateEngine engine;
	protected final Template template;

	public UISupport(final String fn, final Charset charset)
			throws CompilationFailedException, ClassNotFoundException,
			IOException {
		File f = new File(fn);
		try (InputStreamReader reader = new InputStreamReader(
				new FileInputStream(f), charset);) {
			this.engine = new SimpleTemplateEngine();
			this.template = engine.createTemplate(reader);
			reader.close();
		}
	}

	public UISupport(final File f, final Charset charset)
			throws CompilationFailedException, ClassNotFoundException,
			IOException {
		try (InputStreamReader reader = new InputStreamReader(
				new FileInputStream(f), charset);) {
			this.engine = new SimpleTemplateEngine();
			this.template = engine.createTemplate(reader);
			reader.close();
		}
	}

	public UISupport(final String templateText)
			throws CompilationFailedException, ClassNotFoundException,
			IOException {
		try (StringReader reader = new StringReader(templateText);) {
			this.engine = new SimpleTemplateEngine();
			this.template = engine.createTemplate(reader);
			reader.close();
		}
	}

	public UISupport(final Reader reader) throws CompilationFailedException,
			ClassNotFoundException, IOException {
		this.engine = new SimpleTemplateEngine();
		this.template = engine.createTemplate(reader);
	}

	public String make(Map map) throws IOException, CompilationFailedException,
			IOException {
		return make(template, map);
	}

	public static final String make(final String templateText, final Map map)
			throws CompilationFailedException, ClassNotFoundException,
			IOException {
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		Template template = engine.createTemplate(templateText);
		return make(template, map);
	}

	public static final String make(final File f, final Map map)
			throws CompilationFailedException, ClassNotFoundException,
			IOException {
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		Template template = engine.createTemplate(f);
		return make(template, map);
	}

	public static final String make(final Reader reader, final Map map)
			throws CompilationFailedException, ClassNotFoundException,
			IOException {
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		Template template = engine.createTemplate(reader);
		return make(template, map);
	}

	public static final String make(final Template template, final Map map)
			throws IOException {
		Writable writable = template.make(map);
		return writeTo(writable);
	}

	public static final String writeTo(final Writable writable)
			throws IOException {
		StringWriter ssw = new StringWriter();
		writable.writeTo(ssw);
		return ssw.getBuffer().toString();
	}

}
