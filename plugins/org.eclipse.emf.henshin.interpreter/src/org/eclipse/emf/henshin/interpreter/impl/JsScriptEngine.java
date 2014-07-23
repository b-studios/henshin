package org.eclipse.emf.henshin.interpreter.impl;

import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * We are using the decorator pattern to customize the behavior of the
 * default JavaScript scripting engine.
 */
public class JsScriptEngine implements ScriptEngine {

	/**
	 * The original scripting engine to delegate to
	 */
	private ScriptEngine that;
	
	public JsScriptEngine(ScriptEngine original) {
		that = original;
	}
	
	/**
	 * Evaluates a given expression in a context which is extended with the provided imports
	 * 
	 * The imports are on purpose not added to the global scope to prevent pollution of
	 * the namespace.
	 * 
	 * @param script
	 * @param imports
	 * @return
	 * @throws ScriptException
	 */
	public Object eval(String script, List<String> imports) throws ScriptException {
		// We are using crazy unicode characters to avoid accidental capture
		String tmpl = "(function() { with (new JavaImporter(◖IMPORTS◗)) { return ◖EXPR◗; }}).call(this);";
		
		String wrapped = tmpl
				.replaceFirst("◖IMPORTS◗", toImportString(imports))
				.replaceFirst("◖EXPR◗", script);
		
		return that.eval(wrapped);
	}
	
	@Override
	public Object eval(String script) throws ScriptException {
		return eval(script, new LinkedList<String>());
	}
	
	/**
	 * Converts a list of imports like List("foo.Foo", "foo.bar.*") into
	 * one string "foo.Foo, foo.bar"
	 */
	private String toImportString(List<String> imports) {
		StringBuffer out = new StringBuffer();		
		String delim = "";
		
		for (String i : imports) {
		    out.append(delim).append(stripWildcard(i));
		    delim = ", ";
		}
		
		return out.toString();
	}
	
	private String stripWildcard(String imp) {
		return isWildcard(imp) ? imp.substring(0, imp.length() - 2): imp;
	}
	
	private boolean isWildcard(String imp) {
		return Pattern.matches("(.*)\\.\\*$", imp);
	}
	
	/**
	 * Delegates
	 */
	
	@Override
	public Object eval(String script, ScriptContext context) throws ScriptException {
		return that.eval(script, context);
	}

	@Override
	public Object eval(Reader reader, ScriptContext context) throws ScriptException {
		return that.eval(reader, context);
	}

	@Override
	public Object eval(Reader reader) throws ScriptException {
		return that.eval(reader);
	}

	@Override
	public Object eval(String script, Bindings n) throws ScriptException {
		return that.eval(script);
	}

	@Override
	public Object eval(Reader reader, Bindings n) throws ScriptException {
		return that.eval(reader, n);
	}

	@Override
	public void put(String key, Object value) {
		that.put(key, value);
	}

	@Override
	public Object get(String key) {
		return that.get(key);
	}

	@Override
	public Bindings getBindings(int scope) {
		return that.getBindings(scope);
	}

	@Override
	public void setBindings(Bindings bindings, int scope) {
		that.setBindings(bindings, scope);
	}

	@Override
	public Bindings createBindings() {
		return that.createBindings();
	}

	@Override
	public ScriptContext getContext() {
		return that.getContext();
	}

	@Override
	public void setContext(ScriptContext context) {
		that.setContext(context);
	}

	@Override
	public ScriptEngineFactory getFactory() {
		return that.getFactory();
	}
	
	
	/**
	 * Statics
	 */
	public static JsScriptEngine getJsEngine() {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
		
		if (engine == null) {
			System.err.println("Warning: cannot find JavaScript engine");
		} else try {
			// Add java.lang to the global namespace
			engine.eval("importPackage(java.lang)");
		} catch (Throwable t1) {
			// Try again with compatibility library
			try {
				engine.eval("load(\"nashorn:mozilla_compat.js\");\n importPackage(java.lang)");
			} catch (Throwable t2) {
				// Also didn't work
				System.err.println("Warning: error importing java.lang package in JavaScript engine");
			}
		}
		
		return new JsScriptEngine(engine);
	}
}
