package com.bowlong.third.groovy;

import java.io.File;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.bowlong.io.FileEx;
import com.bowlong.third.groovy.examples.config.GroovyContext;

public class Shell {
	static final ScriptEngineManager factory = new ScriptEngineManager();
	static final ScriptEngine engine = factory.getEngineByName("groovy");
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

	public static void main(String[] args) throws Exception {
		// groovy 脚本做配置文件示例
		GroovyContext ac = new GroovyContext();
		Bindings b = createBindings();
		b.put("app", ac);

		CompiledScript script = compile(new File("app.groovy"));
		execNotExcept(script, b);
		System.out.println(ac);
		System.out.println(ac.map);
		// app.groovy file in package com.bowlong.third.groovy.config
		// evalNotExcept(new File("app.groovy"), b);
		// System.out.println(ac);
	}
}
