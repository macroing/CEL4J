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

import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code ReferenceTypeSignature} denotes a ReferenceTypeSignature as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface ReferenceTypeSignature extends FieldSignature, JavaTypeSignature {
	/**
	 * Returns a {@code ReferenceTypeSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code referenceTypeSignature}.
	 * <p>
	 * If {@code referenceTypeSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * ReferenceTypeSignature.excludePackageName(referenceTypeSignature, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param referenceTypeSignature a {@code ReferenceTypeSignature} instance
	 * @return a {@code ReferenceTypeSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code referenceTypeSignature}
	 * @throws NullPointerException thrown if, and only if, {@code referenceTypeSignature} is {@code null}
	 */
	static ReferenceTypeSignature excludePackageName(final ReferenceTypeSignature referenceTypeSignature) {
		return excludePackageName(referenceTypeSignature, "java.lang");
	}
	
	/**
	 * Returns a {@code ReferenceTypeSignature} instance that excludes all package names that are equal to {@code packageName} from {@code referenceTypeSignature}.
	 * <p>
	 * If either {@code referenceTypeSignature} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param referenceTypeSignature a {@code ReferenceTypeSignature} instance
	 * @param packageName the package name to exclude
	 * @return a {@code ReferenceTypeSignature} instance that excludes all package names that are equal to {@code packageName} from {@code referenceTypeSignature}
	 * @throws NullPointerException thrown if, and only if, either {@code referenceTypeSignature} or {@code packageName} are {@code null}
	 */
	static ReferenceTypeSignature excludePackageName(final ReferenceTypeSignature referenceTypeSignature, final String packageName) {
		Objects.requireNonNull(referenceTypeSignature, "referenceTypeSignature == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, referenceTypeSignature);
	}
	
	/**
	 * Parses {@code string} into a {@code ReferenceTypeSignature} instance.
	 * <p>
	 * Returns a {@code ReferenceTypeSignature} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code ReferenceTypeSignature} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	static ReferenceTypeSignature parseReferenceTypeSignature(final String string) {
		return Parsers.parseReferenceTypeSignature(new TextScanner(string));
	}
}