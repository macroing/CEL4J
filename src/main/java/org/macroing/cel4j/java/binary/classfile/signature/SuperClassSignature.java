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
package org.macroing.cel4j.java.binary.classfile.signature;

import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code SuperClassSignature} denotes a SuperclassSignature as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface SuperClassSignature extends Node {
	/**
	 * Returns a {@code String} representation of this {@code SuperClassSignature} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code SuperClassSignature} instance in external form
	 */
	String toExternalForm();
	
	/**
	 * Returns a {@code String} representation of this {@code SuperClassSignature} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code SuperClassSignature} instance in internal form
	 */
	String toInternalForm();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code SuperClassSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code superClassSignature}.
	 * <p>
	 * If {@code superClassSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * SuperClassSignature.excludePackageName(superClassSignature, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param superClassSignature a {@code SuperClassSignature} instance
	 * @return a {@code SuperClassSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code superClassSignature}
	 * @throws NullPointerException thrown if, and only if, {@code superClassSignature} is {@code null}
	 */
	static SuperClassSignature excludePackageName(final SuperClassSignature superClassSignature) {
		return excludePackageName(superClassSignature, "java.lang");
	}
	
	/**
	 * Returns a {@code SuperClassSignature} instance that excludes all package names that are equal to {@code packageName} from {@code superClassSignature}.
	 * <p>
	 * If either {@code superClassSignature} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param superClassSignature a {@code SuperClassSignature} instance
	 * @param packageName the package name to exclude
	 * @return a {@code SuperClassSignature} instance that excludes all package names that are equal to {@code packageName} from {@code superClassSignature}
	 * @throws NullPointerException thrown if, and only if, either {@code superClassSignature} or {@code packageName} are {@code null}
	 */
	static SuperClassSignature excludePackageName(final SuperClassSignature superClassSignature, final String packageName) {
		Objects.requireNonNull(superClassSignature, "superClassSignature == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, superClassSignature);
	}
	
	/**
	 * Parses {@code string} into a {@code SuperClassSignature} instance.
	 * <p>
	 * Returns a {@code SuperClassSignature} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code SuperClassSignature} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	static SuperClassSignature parseSuperClassSignature(final String string) {
		return Parsers.parseSuperClassSignature(new TextScanner(string));
	}
}