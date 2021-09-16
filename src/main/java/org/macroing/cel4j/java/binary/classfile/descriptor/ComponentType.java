/**
 * Copyright 2009 - 2021 J&#246;rgen Lundgren
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

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code ComponentType} denotes a ComponentType as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface ComponentType extends Node {
	/**
	 * Returns a {@code Class} representation of this {@code ComponentType} instance.
	 * <p>
	 * If the {@code Class} cannot be found, a {@code ClassNotFoundException} will be thrown.
	 * 
	 * @return a {@code Class} representation of this {@code ComponentType} instance
	 * @throws ClassNotFoundException thrown if, and only if, the {@code Class} cannot be found
	 */
	Class<?> toClass() throws ClassNotFoundException;
	
	/**
	 * Returns a {@code String} representation of this {@code ComponentType} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code ComponentType} instance in external form
	 */
	String toExternalForm();
	
	/**
	 * Returns a {@code String} representation of this {@code ComponentType} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code ComponentType} instance in internal form
	 */
	String toInternalForm();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code ComponentType} instance that excludes all package names that are equal to {@code "java.lang"} from {@code componentType}.
	 * <p>
	 * If {@code componentType} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * ComponentType.excludePackageName(componentType, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param componentType a {@code ComponentType} instance
	 * @return a {@code ComponentType} instance that excludes all package names that are equal to {@code "java.lang"} from {@code componentType}
	 * @throws NullPointerException thrown if, and only if, {@code componentType} is {@code null}
	 */
	static ComponentType excludePackageName(final ComponentType componentType) {
		return excludePackageName(componentType, "java.lang");
	}
	
	/**
	 * Returns a {@code ComponentType} instance that excludes all package names that are equal to {@code packageName} from {@code componentType}.
	 * <p>
	 * If either {@code componentType} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param componentType a {@code ComponentType} instance
	 * @param packageName the package name to exclude
	 * @return a {@code ComponentType} instance that excludes all package names that are equal to {@code packageName} from {@code componentType}
	 * @throws NullPointerException thrown if, and only if, either {@code componentType} or {@code packageName} are {@code null}
	 */
	static ComponentType excludePackageName(final ComponentType componentType, final String packageName) {
		Objects.requireNonNull(componentType, "componentType == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, componentType);
	}
	
	/**
	 * Parses {@code string} into a {@code ComponentType} instance.
	 * <p>
	 * Returns a {@code ComponentType} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code ComponentType} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	static ComponentType parseComponentType(final String string) {
		return Parsers.parseComponentType(new TextScanner(string));
	}
}