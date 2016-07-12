package com.bowlong.script.js;

import java.io.File;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.bowlong.io.FileEx;

public class Shell {
	static final ScriptEngineManager mgr = new ScriptEngineManager();
	static final ScriptEngine engine = mgr.getEngineByExtension("js");
	static final Compilable compilable = (Compilable) engine;

	public static final Bindings createBindings() {
		final Bindings binding = engine.createBindings();
		return binding;
	}

	public static final CompiledScript compile(String script) throws Exception {
		return compilable.compile(script);
	}

	public static final CompiledScript compile(File f) throws Exception {
		String str = FileEx.readText(f);
		return compile(str);
	}

	public static final Object exec(CompiledScript script, Bindings b)
			throws Exception {
		return script.eval(b);
	}

	public static final Object exec(String script, Bindings b) throws Exception {
		return engine.eval(script, b);
	}

	public static final Object execNotExcept(CompiledScript script, Bindings b) {
		try {
			return exec(script, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final Object exec(File f, Bindings b) throws Exception {
		String str = FileEx.readText(f);
		return exec(str, b);
	}

	public static final Object execNotExcept(File f, Bindings b) {
		try {
			String str = FileEx.readText(f);
			return exec(str, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final void println(String str) {
		System.out.println(str);
	}

	public static void main(String[] args) throws Exception {
		Bindings b = createBindings();
		b.put("sys", new Shell());
		String js = "sys.println(\"hello\")";
		exec(js, b);
	}
}
