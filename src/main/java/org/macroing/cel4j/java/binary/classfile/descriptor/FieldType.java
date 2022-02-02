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
package org.macroing.cel4j.java.binary.classfile.descriptor;

import java.util.Objects;

import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code FieldType} denotes a FieldType as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface FieldType extends ComponentType, FieldDescriptor, ParameterDescriptor, ReturnDescriptor {
	/**
	 * Returns a {@code FieldType} instance that excludes all package names that are equal to {@code "java.lang"} from {@code fieldType}.
	 * <p>
	 * If {@code fieldType} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FieldType.excludePackageName(fieldType, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param fieldType a {@code FieldType} instance
	 * @return a {@code FieldType} instance that excludes all package names that are equal to {@code "java.lang"} from {@code fieldType}
	 * @throws NullPointerException thrown if, and only if, {@code fieldType} is {@code null}
	 */
	static FieldType excludePackageName(final FieldType fieldType) {
		return excludePackageName(fieldType, "java.lang");
	}
	
	/**
	 * Returns a {@code FieldType} instance that excludes all package names that are equal to {@code packageName} from {@code fieldType}.
	 * <p>
	 * If either {@code fieldType} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param fieldType a {@code FieldType} instance
	 * @param packageName the package name to exclude
	 * @return a {@code FieldType} instance that excludes all package names that are equal to {@code packageName} from {@code fieldType}
	 * @throws NullPointerException thrown if, and only if, either {@code fieldType} or {@code packageName} are {@code null}
	 */
	static FieldType excludePackageName(final FieldType fieldType, final String packageName) {
		Objects.requireNonNull(fieldType, "fieldType == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, fieldType);
	}
	
	/**
	 * Parses {@code string} into a {@code FieldType} instance.
	 * <p>
	 * Returns a {@code FieldType} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code FieldType} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	static FieldType parseFieldType(final String string) {
		return Parsers.parseFieldType(new TextScanner(string));
	}
}