/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.cel4j.
 * 
 * org.macroing.cel4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.cel4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.cel4j. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.cel4j.artifact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

/**
 * {@code ArtifactScriptEngineFactory} is a {@code ScriptEngineFactory} that manages a {@code ScriptEngine} called Artifact that evaluates a super-set of Java source code.
 * <p>
 * The {@code ScriptEngine} provided compiles the source code into {@code CompiledScript}s and loads them, using the context {@code ClassLoader}. It caches the {@code CompiledScript}s using a normalized version of the source code provided for that
 * {@code CompiledScript}. By doing so, no re-compilation will be performed when you add whitespace in other places than {@code String} literals.
 * <p>
 * To demonstrate its use, here is an example:
 * <pre>
 * {@code
 * ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
 * 
 * ScriptEngine scriptEngine = scriptEngineManager.getEngineByExtension("java");
 * scriptEngine.eval("System.out.println(\"Hello, World!\"); return true;");
 * }
 * </pre>
 * As you can see in the example above, you can return a result. Although you can, it's not necessary. If nothing is returned by you, {@code null} will be returned by default.
 * <p>
 * If a variable starts with a dollar sign ({@code $}), followed by a variable name (a Java identifier), that variable will be substituted for a variable in the {@code ScriptContext} and cast to its type. Lets say you have a variable {@code $string}
 * that refers to a variable called {@code "string"} in the {@code ScriptContext}, and that variable is of type {@code String}. A call such as {@code $string.length()} would return the length of the {@code String} variable. Note, however, that this
 * assumes the variable already exists in the {@code ScriptContext} prior to the evaluation of the current script. Adding a variable to the {@code ScriptContext} and then using this variable substitution mechanism to get that variable in the same
 * script won't work. The reason for this, is that the variable substitution is performed prior to the evaluation of the script itself.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ArtifactScriptEngineFactory implements ScriptEngineFactory {
	private static final String ENGINE_NAME = "Artifact";
	private static final String ENGINE_VERSION = "0.2.0";
	private static final String LANGUAGE_NAME = "Java";
	private static final String LANGUAGE_VERSION = "8";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final ScriptEngine scriptEngine;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ArtifactScriptEngineFactory} instance.
	 */
	public ArtifactScriptEngineFactory() {
		this.scriptEngine = new ArtifactScriptEngine(this);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with extensions.
	 * <p>
	 * The currently supported extensions are {@code ".java"} and {@code "java"}.
	 * 
	 * @return a {@code List} with extensions
	 */
	@Override
	public List<String> getExtensions() {
		return Collections.unmodifiableList(new ArrayList<>(Arrays.asList(".java", "java")));
	}
	
	/**
	 * Returns a {@code List} with mime-types.
	 * <p>
	 * There are currently no supported mime-types.
	 * 
	 * @return a {@code List} with mime-types
	 */
	@Override
	public List<String> getMimeTypes() {
		return Collections.unmodifiableList(new ArrayList<>());
	}
	
	/**
	 * Returns a {@code List} with names.
	 * <p>
	 * The currently supported names are {@code "Artifact"}, {@code "artifact"}, {@code "Java"} and {@code "java"}.
	 * 
	 * @return a {@code List} with names
	 */
	@Override
	public List<String> getNames() {
		return Collections.unmodifiableList(new ArrayList<>(Arrays.asList(ENGINE_NAME, ENGINE_NAME.toLowerCase(), LANGUAGE_NAME, LANGUAGE_NAME.toLowerCase())));
	}
	
	/**
	 * Returns an {@code Object} value based on a {@code String} key.
	 * <p>
	 * The key may be {@code null}.
	 * 
	 * @param key the key to return a value from
	 * @return an {@code Object} value based on a {@code String} key
	 */
	@Override
	public Object getParameter(final String key) {
		if(key != null) {
			switch(key) {
				case ScriptEngine.ENGINE:
					return getEngineName();
				case ScriptEngine.ENGINE_VERSION:
					return getEngineVersion();
				case ScriptEngine.LANGUAGE:
					return getLanguageName();
				case ScriptEngine.LANGUAGE_VERSION:
					return getLanguageVersion();
				case ScriptEngine.NAME:
					return getNames().get(0);
				case "THREADING":
					return null;
				default:
					return null;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the {@code ScriptEngine}.
	 * <p>
	 * It may be cached.
	 * 
	 * @return the {@code ScriptEngine}
	 */
	@Override
	public ScriptEngine getScriptEngine() {
		return this.scriptEngine;
	}
	
	/**
	 * Returns a {@code String} with the name of the {@code ScriptEngine}.
	 * <p>
	 * The name is {@code "Artifact"}.
	 * 
	 * @return a {@code String} with the name of the {@code ScriptEngine}
	 */
	@Override
	public String getEngineName() {
		return ENGINE_NAME;
	}
	
	/**
	 * Returns a {@code String} with the version of the {@code ScriptEngine}.
	 * 
	 * @return a {@code String} with the version of the {@code ScriptEngine}
	 */
	@Override
	public String getEngineVersion() {
		return ENGINE_VERSION;
	}
	
	/**
	 * Returns a {@code String} with the name of the language.
	 * <p>
	 * The name is {@code "Java"}.
	 * 
	 * @return a {@code String} with the name of the language
	 */
	@Override
	public String getLanguageName() {
		return LANGUAGE_NAME;
	}
	
	/**
	 * Returns a {@code String} with the version of the language.
	 * <p>
	 * The current version is {@code "8"}.
	 * 
	 * @return a {@code String} with the version of the language
	 */
	@Override
	public String getLanguageVersion() {
		return LANGUAGE_VERSION;
	}
	
	/**
	 * Returns a {@code String} which can be used to invoke a method of a Java object using the syntax of the supported scripting language.
	 * 
	 * @param object a {@code String} with an object
	 * @param method a {@code String} with a method
	 * @param args a {@code String} array with parameter arguments
	 * @return a {@code String} which can be used to invoke a method of a Java object using the syntax of the supported scripting language
	 */
	@Override
	public String getMethodCallSyntax(final String object, final String method, final String... args) {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(object);
		stringBuilder.append(".");
		stringBuilder.append(method);
		stringBuilder.append("(");
		
		for(int i = 0; i < args.length; i++) {
			stringBuilder.append(i > 0 ? ", " : "");
			stringBuilder.append(args[i]);
		}
		
		stringBuilder.append(")");
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns a {@code String} that can be used as a statement to display the specified {@code String} using the syntax of the supported scripting language.
	 * 
	 * @param toDisplay a {@code String} to display
	 * @return a {@code String} that can be used as a statement to display the specified {@code String} using the syntax of the supported scripting language
	 */
	@Override
	public String getOutputStatement(final String toDisplay) {
		return String.format("System.out.println(\"%s\")", toDisplay);
	}
	
	/**
	 * Returns a valid scripting language executable program with given statements.
	 * 
	 * @param statements a {@code String} array with statements
	 * @return a valid scripting language executable program with given statements
	 */
	@Override
	public String getProgram(final String... statements) {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(final String statement : statements) {
			stringBuilder.append(statement);
			stringBuilder.append(";");
			stringBuilder.append(System.getProperty("line.separator"));
		}
		
		return stringBuilder.toString();
	}
}