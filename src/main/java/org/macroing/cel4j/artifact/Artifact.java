/**
 * Copyright 2009 - 2024 J&#246;rgen Lundgren
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

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This {@code Artifact} class is used for global configuration of Artifact.
 * <p>
 * One use case for this class is to add import statements globally. Consider the following example:
 * <pre>
 * {@code
 * Artifact.addGlobalImportStatements("import javax.swing.JFrame;", "import static java.lang.Math.*;");
 * }
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Artifact {
	private static final AtomicBoolean IS_DEFAULT_IMPORT_STATEMENTS_ENABLED = new AtomicBoolean(true);
	private static final Set<String> GLOBAL_IMPORT_STATEMENTS = new LinkedHashSet<>();
	private static final String PROPERTY_IMPORT = "org.macroing.cel4j.artifact.import";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Artifact() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, the default import statements are enabled, {@code false} otherwise.
	 * <p>
	 * The default import statements are enabled by default.
	 * <p>
	 * To enable or disable the default import statements, consider using {@link #setDefaultImportStatementsEnabled(boolean)}.
	 * 
	 * @return {@code true} if, and only if, the default import statements are enabled, {@code false} otherwise
	 */
	public static boolean isDefaultImportStatementsEnabled() {
		return IS_DEFAULT_IMPORT_STATEMENTS_ENABLED.get();
	}
	
	/**
	 * Returns a {@code List} with all default import statements.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Artifact} class.
	 * <p>
	 * The default import statements will only be used by Artifact if {@link #isDefaultImportStatementsEnabled()} returns {@code true}.
	 * <p>
	 * The default import statements can be separated into two groups; the internally defined and the externally defined. The externally defined group overrides the internally defined group.
	 * <p>
	 * To create an externally defined group, set a system property called {@code org.macroing.cel4j.artifact.import} with a filename as value. The file that is pointed to by that filename should contain one import statement per line. The file should
	 * be encoded as UTF-8.
	 * <p>
	 * Consider the following example:
	 * <pre>
	 * {@code
	 * java -Dorg.macroing.cel4j.artifact.import="path/to/file.txt" -cp org.macroing.cel4j.jar org.macroing.cel4j.artifact.Main -g
	 * }
	 * </pre>
	 * If any of the externally defined import statements are invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If an I/O-error occurred while trying to load the externally defined import statements, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @return a {@code List} with all default import statements
	 * @throws IllegalArgumentException thrown if, and only if, any of the externally defined import statements are invalid
	 * @throws UncheckedIOException thrown if, and only if, an I/O-error occurred while trying to load the externally defined import statements
	 */
	public static List<String> getDefaultImportStatements() {
		final List<String> externalDefaultImportStatements = doGetExternalDefaultImportStatements();
		
		if(externalDefaultImportStatements.size() > 0) {
			return externalDefaultImportStatements;
		}
		
		final List<String> internalDefaultImportStatements = doGetInternalDefaultImportStatements();
		
		if(internalDefaultImportStatements.size() > 0) {
			return internalDefaultImportStatements;
		}
		
		return new ArrayList<>();
	}
	
	/**
	 * Returns a {@code List} with all globally added import statements.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Artifact} class.
	 * 
	 * @return a {@code List} with all globally added import statements
	 */
	public static synchronized List<String> getGlobalImportStatements() {
		return new ArrayList<>(GLOBAL_IMPORT_STATEMENTS);
	}
	
	/**
	 * Adds {@code globalImportStatement} as a global import statement, if absent.
	 * <p>
	 * If {@code globalImportStatement} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code globalImportStatement} is invalid, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param globalImportStatement the import statement to add globally
	 * @throws IllegalArgumentException thrown if, and only if, {@code globalImportStatement} is invalid
	 * @throws NullPointerException thrown if, and only if, {@code globalImportStatement} is {@code null}
	 */
	public static synchronized void addGlobalImportStatement(final String globalImportStatement) {
		Objects.requireNonNull(globalImportStatement, "globalImportStatement == null");
		
		if(!Matchers.newImportStatementMatcher(globalImportStatement).matches()) {
			throw new IllegalArgumentException(String.format("Invalid import statement: %s", globalImportStatement));
		}
		
		GLOBAL_IMPORT_STATEMENTS.add(globalImportStatement);
	}
	
	/**
	 * Adds all absent import statements in {@code globalImportStatements} globally.
	 * <p>
	 * If {@code globalImportStatements} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code globalImportStatements} contains an invalid import statement, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param globalImportStatements the import statements to add globally
	 * @throws IllegalArgumentException thrown if, and only if, {@code globalImportStatements} contains an invalid import statement
	 * @throws NullPointerException thrown if, and only if, {@code globalImportStatements} or any of its elements are {@code null}
	 */
	public static synchronized void addGlobalImportStatements(final String... globalImportStatements) {
		for(final String globalImportStatement : globalImportStatements) {
			addGlobalImportStatement(globalImportStatement);
		}
	}
	
	/**
	 * Removes {@code globalImportStatement} as a global import statement, if present.
	 * <p>
	 * If {@code globalImportStatement} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code globalImportStatement} is invalid, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param globalImportStatement the import statement to remove globally
	 * @throws IllegalArgumentException thrown if, and only if, {@code globalImportStatement} is invalid
	 * @throws NullPointerException thrown if, and only if, {@code globalImportStatement} is {@code null}
	 */
	public static synchronized void removeGlobalImportStatement(final String globalImportStatement) {
		Objects.requireNonNull(globalImportStatement, "globalImportStatement == null");
		
		if(!Matchers.newImportStatementMatcher(globalImportStatement).matches()) {
			throw new IllegalArgumentException(String.format("Invalid import statement: %s", globalImportStatement));
		}
		
		GLOBAL_IMPORT_STATEMENTS.remove(globalImportStatement);
	}
	
	/**
	 * Removes all present import statements in {@code globalImportStatements} globally.
	 * <p>
	 * If {@code globalImportStatements} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code globalImportStatements} contains an invalid import statement, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param globalImportStatements the import statements to remove globally
	 * @throws IllegalArgumentException thrown if, and only if, {@code globalImportStatements} contains an invalid import statement
	 * @throws NullPointerException thrown if, and only if, {@code globalImportStatements} or any of its elements are {@code null}
	 */
	public static synchronized void removeGlobalImportStatements(final String... globalImportStatements) {
		for(final String globalImportStatement : globalImportStatements) {
			removeGlobalImportStatement(globalImportStatement);
		}
	}
	
	/**
	 * Enables or disables the default import statements.
	 * <p>
	 * The default import statements are enabled by default.
	 * 
	 * @param isDefaultImportStatementsEnabled {@code true} if, and only if, the default import statements should be enabled, {@code false} otherwise
	 */
	public static void setDefaultImportStatementsEnabled(final boolean isDefaultImportStatementsEnabled) {
		IS_DEFAULT_IMPORT_STATEMENTS_ENABLED.set(isDefaultImportStatementsEnabled);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static List<String> doGetExternalDefaultImportStatements() {
		final List<String> defaultImportStatements = new ArrayList<>();
		
		final String importFilename = System.getProperty(PROPERTY_IMPORT);
		
		if(importFilename != null) {
			final File importFile = new File(importFilename);
			
			if(importFile.isFile()) {
				try {
					defaultImportStatements.addAll(Files.readAllLines(importFile.toPath()));
					
					for(int i = defaultImportStatements.size() - 1; i >= 0; i--) {
						final String defaultImportStatement = defaultImportStatements.get(i);
						
						if(!Matchers.newImportStatementMatcher(defaultImportStatement).matches()) {
							throw new IllegalArgumentException(String.format("Invalid import statement: %s", defaultImportStatement));
						}
					}
				} catch(final IOException e) {
					throw new UncheckedIOException(e);
				}
			}
		}
		
		return defaultImportStatements;
	}
	
	private static List<String> doGetInternalDefaultImportStatements() {
		final List<String> defaultImportStatements = new ArrayList<>();
		
		defaultImportStatements.add("import static java.lang.Math.*;");
		defaultImportStatements.add("import java.awt.*;");
		defaultImportStatements.add("import java.awt.color.*;");
		defaultImportStatements.add("import java.awt.event.*;");
		defaultImportStatements.add("import java.awt.font.*;");
		defaultImportStatements.add("import java.awt.geom.*;");
		defaultImportStatements.add("import java.awt.image.*;");
		defaultImportStatements.add("import java.lang.ref.*;");
		defaultImportStatements.add("import java.lang.reflect.*;");
		defaultImportStatements.add("import java.math.*;");
		defaultImportStatements.add("import java.net.*;");
		defaultImportStatements.add("import java.nio.*;");
		defaultImportStatements.add("import java.nio.channels.*;");
		defaultImportStatements.add("import java.nio.charset.*;");
		defaultImportStatements.add("import java.nio.file.*;");
		defaultImportStatements.add("import java.nio.file.attribute.*;");
		defaultImportStatements.add("import java.text.*;");
		defaultImportStatements.add("import java.util.*;");
		defaultImportStatements.add("import java.util.concurrent.*;");
		defaultImportStatements.add("import java.util.concurrent.atomic.*;");
		defaultImportStatements.add("import java.util.concurrent.locks.*;");
		defaultImportStatements.add("import java.util.jar.*;");
		defaultImportStatements.add("import java.util.logging.*;");
		defaultImportStatements.add("import java.util.prefs.*;");
		defaultImportStatements.add("import java.util.regex.*;");
		defaultImportStatements.add("import java.util.zip.*;");
		defaultImportStatements.add("import javax.swing.*;");
		defaultImportStatements.add("import javax.swing.border.*;");
		defaultImportStatements.add("import javax.swing.colorchooser.*;");
		defaultImportStatements.add("import javax.swing.event.*;");
		defaultImportStatements.add("import javax.swing.filechooser.*;");
		defaultImportStatements.add("import javax.swing.table.*;");
		defaultImportStatements.add("import javax.swing.text.*;");
		defaultImportStatements.add("import javax.swing.tree.*;");
		defaultImportStatements.add("import javax.swing.undo.*;");
		defaultImportStatements.add("import javax.tools.*;");
		
		return defaultImportStatements;
	}
}