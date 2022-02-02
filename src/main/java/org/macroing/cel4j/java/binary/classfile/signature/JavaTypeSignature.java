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
package org.macroing.cel4j.java.binary.classfile.signature;

import java.util.Objects;

import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code JavaTypeSignature} denotes a JavaTypeSignature as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface JavaTypeSignature extends Result {
	/**
	 * Returns a {@code JavaTypeSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code javaTypeSignature}.
	 * <p>
	 * If {@code javaTypeSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * JavaTypeSignature.excludePackageName(javaTypeSignature, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param javaTypeSignature a {@code JavaTypeSignature} instance
	 * @return a {@code JavaTypeSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code javaTypeSignature}
	 * @throws NullPointerException thrown if, and only if, {@code javaTypeSignature} is {@code null}
	 */
	static JavaTypeSignature excludePackageName(final JavaTypeSignature javaTypeSignature) {
		return excludePackageName(javaTypeSignature, "java.lang");
	}
	
	/**
	 * Returns a {@code JavaTypeSignature} instance that excludes all package names that are equal to {@code packageName} from {@code javaTypeSignature}.
	 * <p>
	 * If either {@code javaTypeSignature} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param javaTypeSignature a {@code JavaTypeSignature} instance
	 * @param packageName the package name to exclude
	 * @return a {@code JavaTypeSignature} instance that excludes all package names that are equal to {@code packageName} from {@code javaTypeSignature}
	 * @throws NullPointerException thrown if, and only if, either {@code javaTypeSignature} or {@code packageName} are {@code null}
	 */
	static JavaTypeSignature excludePackageName(final JavaTypeSignature javaTypeSignature, final String packageName) {
		Objects.requireNonNull(javaTypeSignature, "javaTypeSignature == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, javaTypeSignature);
	}
	
	/**
	 * Parses {@code string} into a {@code JavaTypeSignature} instance.
	 * <p>
	 * Returns a {@code JavaTypeSignature} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code JavaTypeSignature} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	static JavaTypeSignature parseJavaTypeSignature(final String string) {
		return Parsers.parseJavaTypeSignature(new TextScanner(string));
	}
}