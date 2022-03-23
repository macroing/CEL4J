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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;

import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.macroing.cel4j.util.Document;

final class ArtifactScriptEngine extends AbstractScriptEngine implements Compilable {
	private static final AtomicInteger IDENTIFIER;
	private static final String DEFAULT_PACKAGE_NAME;
	private static final String DEFAULT_SUPER_CLASS_NAME;
	private static final String LINE_SEPARATOR;
	private static final String PROPERTY_DUMP;
	private static final String TMP_DIRECTORY;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicReference<String> packageName;
	private final AtomicReference<String> superClassName;
	private final DynamicURLClassLoader dynamicURLClassLoader;
	private final List<String> importStatements;
	private final List<String> importStatementsRequired;
	private final Map<String, CompiledScript> compiledScripts;
	private final ScriptEngineFactory scriptEngineFactory;
	private final boolean isDumpingSourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ArtifactScriptEngine(final ScriptEngineFactory scriptEngineFactory) {
		this.packageName = new AtomicReference<>(DEFAULT_PACKAGE_NAME);
		this.superClassName = new AtomicReference<>(DEFAULT_SUPER_CLASS_NAME);
		this.dynamicURLClassLoader = new DynamicURLClassLoader();
		this.dynamicURLClassLoader.addURLsFromClassPath();
		this.importStatements = new ArrayList<>();
		this.importStatementsRequired = doCreateImportStatementsRequired();
		this.compiledScripts = new HashMap<>();
		this.scriptEngineFactory = Objects.requireNonNull(scriptEngineFactory, "scriptEngineFactory == null");
		this.isDumpingSourceCode = Objects.toString(System.getProperty(PROPERTY_DUMP)).equals("true");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static {
		IDENTIFIER = new AtomicInteger(0);
		
		DEFAULT_PACKAGE_NAME = "org.macroing.cel4j.artifact";
		
		DEFAULT_SUPER_CLASS_NAME = "ArtifactScript";
		
		PROPERTY_DUMP = "org.macroing.cel4j.artifact.dump";
		
		LINE_SEPARATOR = System.getProperty("line.separator");
		TMP_DIRECTORY = System.getProperty("java.io.tmpdir");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Bindings createBindings() {
		return new SimpleBindings();
	}
	
	@Override
	public CompiledScript compile(final Reader reader) throws ScriptException {
		return doCompile(doReadFrom(Objects.requireNonNull(reader, "reader == null")));
	}
	
	@Override
	public CompiledScript compile(final String script) throws ScriptException {
		return doCompile(Objects.requireNonNull(script, "script == null"));
	}
	
	@Override
	public Object eval(final Reader reader, final ScriptContext scriptContext) throws ScriptException {
		return doEval(Objects.requireNonNull(reader, "reader == null"), Objects.requireNonNull(scriptContext, "scriptContext == null"));
	}
	
	@Override
	public Object eval(final String script, final ScriptContext scriptContext) throws ScriptException {
		return doEval(Objects.requireNonNull(script, "script == null"), Objects.requireNonNull(scriptContext, "scriptContext == null"));
	}
	
	@Override
	public ScriptEngineFactory getFactory() {
		return this.scriptEngineFactory;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private CompiledScript doCompile(final String script) throws ScriptException {
		final String script0 = doSearchAndReplace(script);
		final String className = "ArtifactScriptImpl" + IDENTIFIER.incrementAndGet();
		final String packageName = this.packageName.get();
		final String superClassName = this.superClassName.get();
		final String directory = packageName.replace(".", "/");
		
		final File binaryDirectory = doGetBinaryDirectory();
		final File sourceDirectory = doGetSourceDirectory();
		final File sourceFile = doGetSourceFile(directory, className);
		
		doAddToClassPath(binaryDirectory);
		
		doGenerateSourceCode(packageName, className, superClassName, script0, sourceFile);
		
		try {
			final JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
			
			final List<File> files = doGetFiles(this.dynamicURLClassLoader.getURLs());
			
			try(final StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null)) {
				standardJavaFileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(binaryDirectory));
				standardJavaFileManager.setLocation(StandardLocation.CLASS_PATH, files);
				standardJavaFileManager.setLocation(StandardLocation.SOURCE_PATH, Arrays.asList(sourceDirectory));
				
				final CompilationTask compilationTask = javaCompiler.getTask(null, standardJavaFileManager, null, null, null, standardJavaFileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile)));
				
				final boolean isCompiled = compilationTask.call().booleanValue();
				
				if(!isCompiled) {
					throw new ScriptException("Unable to compile script.");
				}
			}
		} catch(final IOException | RuntimeException e) {
			throw new ScriptException(e);
		}
		
		try {
			final Class<?> clazz = Class.forName(packageName + "." + className, true, this.dynamicURLClassLoader);
			
			final Object object = clazz.getConstructor(new Class<?>[] {ScriptEngine.class}).newInstance(new Object[] {this});
			
			if(object instanceof CompiledScript) {
				return CompiledScript.class.cast(object);
			}
			
			return new CompiledScript() {
				@Override
				public Object eval(final ScriptContext scriptContext) throws ScriptException {
					try {
						return clazz.getMethod("eval", ScriptContext.class).invoke(object, scriptContext);
					} catch(final IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
						throw new ScriptException(e);
					}
				}
				
				@Override
				public ScriptEngine getEngine() {
					return ArtifactScriptEngine.this;
				}
			};
		} catch(final Exception e) {
			throw new ScriptException(e);
		}
	}
	
	private Object doEval(final Reader reader, final ScriptContext scriptContext) throws ScriptException {
		return doEval(doReadFrom(reader), scriptContext);
	}
	
	private Object doEval(final String script, final ScriptContext scriptContext) throws ScriptException {
		try {
			final String key = Matchers.newWhiteSpaceMatcher(script).replaceAll("");
			
			CompiledScript compiledScript = this.compiledScripts.get(key);
			
			if(compiledScript == null) {
				compiledScript = doCompile(script);
				
				this.compiledScripts.put(key, compiledScript);
			}
			
			if(compiledScript == null) {
				throw new ScriptException("Unable to evaluate script \"" + script + "\".");
			}
			
			return compiledScript.eval(scriptContext);
		} catch(final NullPointerException e) {
			throw new ScriptException(e);
		}
	}
	
	private String doGenerateSourceCode(final String packageName, final String className, final String superClassName, final String script) throws ScriptException {
		final
		Document document = new Document();
		document.linef("package %s;", packageName);
		document.linef("");
		
		for(final String importStatement : this.importStatementsRequired) {
			document.linef(importStatement);
		}
		
		if(Artifact.isDefaultImportStatementsEnabled()) {
			try {
				for(final String importStatement : Artifact.getDefaultImportStatements()) {
					document.linef(importStatement);
				}
			} catch(final IllegalArgumentException | UncheckedIOException e) {
				throw new ScriptException(e);
			}
		}
		
		for(final String importStatement : Artifact.getGlobalImportStatements()) {
			document.linef(importStatement);
		}
		
		for(final String importStatement : this.importStatements) {
			document.linef(importStatement);
		}
		
		document.linef("");
		document.linef("public final class %s extends %s {", className, superClassName);
		document.linef("	private ScriptEngine scriptEngine;");
		document.linef("	");
		document.linef("	public %s(final ScriptEngine scriptEngine) {", className);
		document.linef("		this.scriptEngine = scriptEngine;");
		document.linef("	}");
		document.linef("	");
		document.linef("	public Object eval(final ScriptContext scriptContext) throws ScriptException {");
		document.linef("		Exception exception = null;");
		document.linef("		");
		document.linef("		try {");
		document.linef("			%s", doFormatScript(script));
		document.linef("		} catch(final Exception e) {");
		document.linef("			exception = e;");
		document.linef("		}");
		document.linef("		");
		document.linef("		if(exception != null) {");
		document.linef("			throw new ScriptException(exception);");
		document.linef("		} else {");
		document.linef("			return null;");
		document.linef("		}");
		document.linef("	}");
		document.linef("	");
		document.linef("	public ScriptEngine getEngine() {");
		document.linef("		return this.scriptEngine;");
		document.linef("	}");
		document.linef("}");
		
		return document.toString();
	}
	
	private String doSearchAndReplace(String script) throws ScriptException {
		script = doSearchAndReplaceExtends(script);
		script = doSearchAndReplaceImports(script);
		script = doSearchAndReplacePackages(script);
		script = doSearchAndReplaceSubstitutionVariables(script);
		
		return script;
	}
	
	private String doSearchAndReplaceExtends(final String script) {
		final StringBuffer stringBuffer = new StringBuffer(script.length());
		
		final Matcher matcher = Matchers.newExtendsStatementMatcher(script);
		
		while(matcher.find()) {
			final String superClassName = matcher.group(Matchers.NAME_EXTENDS_STATEMENT);
			final String replacement = "";
			
			this.superClassName.set(superClassName);
			
			matcher.appendReplacement(stringBuffer, replacement);
		}
		
		matcher.appendTail(stringBuffer);
		
		return stringBuffer.toString();
	}
	
	private String doSearchAndReplaceImports(final String script) {
		final StringBuffer stringBuffer = new StringBuffer(script.length());
		
		final Matcher matcher = Matchers.newImportStatementMatcher(script);
		
		while(matcher.find()) {
			final String importStatement = matcher.group(Matchers.NAME_IMPORT_STATEMENT);
			final String replacement = "";
			
			this.importStatements.add(importStatement);
			
			matcher.appendReplacement(stringBuffer, replacement);
		}
		
		matcher.appendTail(stringBuffer);
		
		return stringBuffer.toString();
	}
	
	private String doSearchAndReplacePackages(final String script) {
		final StringBuffer stringBuffer = new StringBuffer(script.length());
		
		final Matcher matcher = Matchers.newPackageStatementMatcher(script);
		
		while(matcher.find()) {
			final String packageName = matcher.group(Matchers.NAME_PACKAGE_STATEMENT);
			final String replacement = "";
			
			this.packageName.set(packageName);
			
			matcher.appendReplacement(stringBuffer, replacement);
		}
		
		matcher.appendTail(stringBuffer);
		
		return stringBuffer.toString();
	}
	
	private String doSearchAndReplaceSubstitutionVariables(final String script) throws ScriptException {
		final StringBuffer stringBuffer = new StringBuffer(script.length());
		
		final Matcher matcher = Matchers.newSubstitutionVariableMatcher(script);
		
		while(matcher.find()) {
			final String variableName = matcher.group(1);
			final String dynamicCastEvaluation = String.format("return scriptContext.getBindings(ScriptContext.ENGINE_SCOPE).get(\"%s\");", variableName);
			
			String replacement = "";
			
			final Object object = eval(dynamicCastEvaluation);
			
			if(object != null) {
				final Class<?> clazz = object.getClass();
				
				replacement = doCast(clazz, variableName);
			}
			
			matcher.appendReplacement(stringBuffer, replacement);
		}
		
		matcher.appendTail(stringBuffer);
		
		return stringBuffer.toString();
	}
	
	private void doAddToClassPath(final File file) {
		try {
			this.dynamicURLClassLoader.addURL(file.toURI().toURL());
		} catch(final MalformedURLException e) {
			throw new UnsupportedOperationException(e);
		}
	}
	
	private void doGenerateSourceCode(final String packageName, final String className, final String superClassName, final String script, final File sourceFile) throws ScriptException {
		final String sourceCode = doGenerateSourceCode(packageName, className, superClassName, script);
		
		if(this.isDumpingSourceCode) {
			System.out.println(sourceCode);
		}
		
		try(final FileWriter fileWriter = new FileWriter(sourceFile)) {
			fileWriter.write(sourceCode);
		} catch(final IOException e) {
			throw new ScriptException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static File doGetBinaryDirectory() {
		final
		File file = new File(TMP_DIRECTORY, "artifact/bin");
		file.mkdirs();
		
		return file;
	}
	
	private static File doGetSourceDirectory() {
		final
		File file = new File(TMP_DIRECTORY, "artifact/src");
		file.mkdirs();
		
		return file;
	}
	
	private static File doGetSourceFile(final String directory, final String className) {
		final
		File file = new File(doGetSourceDirectory(), directory + "/" + className + ".java");
		file.getParentFile().mkdirs();
		
		return file;
	}
	
	private static int doGetDimensionsOf(final Class<?> clazz, final int dimensions) {
		if(clazz.isArray()) {
			return doGetDimensionsOf(clazz.getComponentType(), dimensions + 1);
		}
		
		return dimensions;
	}
	
	private static List<File> doGetFiles(final URL[] uRLs) {
		final List<File> files = new ArrayList<>();
		
		for(final URL uRL : uRLs) {
			try {
				files.add(new File(uRL.toURI()));
			} catch(final URISyntaxException e) {
				files.add(new File(uRL.getPath()));
			}
		}
		
		return files;
	}
	
	private static List<String> doCreateImportStatementsRequired() {
		final List<String> importStatementsRequired = new ArrayList<>();
		
		importStatementsRequired.add("import javax.script.*;");
		importStatementsRequired.add("import org.macroing.cel4j.artifact.*;");
		
		return importStatementsRequired;
	}
	
	private static String doCast(final Class<?> clazz, final String variableName) {
		String className = clazz.getName();
		
		if(clazz.isArray()) {
			final int dimensions = doGetDimensionsOf(clazz, 0);
			
			className = className.replace(";", "");
			className = className.replaceAll("\\[{" + dimensions + "," + dimensions + "}L", "");
			className = className + String.join("", Collections.nCopies(dimensions, "[]"));
		}
		
		return String.format("%s.class.cast(scriptContext.getBindings(ScriptContext.ENGINE_SCOPE).get(\"%s\"))", className, variableName);
	}
	
	private static String doFormatScript(final String script) {
		final String[] lines = script.trim().split("\n");
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(int i = 0; i < lines.length; i++) {
			final String line = lines[i];
			
			stringBuilder.append(i > 0 ? "			" : "");
			stringBuilder.append(line);
			stringBuilder.append(i + 1 < lines.length ? LINE_SEPARATOR : "");
		}
		
		return stringBuilder.toString();
	}
	
	private static String doReadFrom(final Reader reader) throws ScriptException {
		try(final BufferedReader bufferedReader = new BufferedReader(reader)) {
			final StringBuilder stringBuilder = new StringBuilder();
			
			for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
				stringBuilder.append(line);
				stringBuilder.append(LINE_SEPARATOR);
			}
			
			return stringBuilder.toString();
		} catch(final IOException e) {
			throw new ScriptException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class DynamicURLClassLoader extends URLClassLoader {
		private static final String JAVA_CLASS_PATH = System.getProperty("java.class.path");
		private static final String PATH_SEPARATOR = System.getProperty("path.separator");
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public DynamicURLClassLoader() {
			super(new URL[0], ClassLoader.getSystemClassLoader());
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public void addURL(final URL uRL) {
			super.addURL(uRL);
		}
		
		public void addURLsFromClassPath() {
			final String[] javaClassPathElements = JAVA_CLASS_PATH.split(PATH_SEPARATOR);
			
			for(final String javaClassPathElement : javaClassPathElements) {
				try {
					addURL(new File(javaClassPathElement).toURI().toURL());
				} catch(final MalformedURLException e) {
//					Do nothing for now!
				}
			}
		}
	}
}