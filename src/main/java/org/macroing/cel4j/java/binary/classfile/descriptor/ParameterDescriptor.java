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
package org.macroing.cel4j.java.binary.classfile.descriptor;

import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code ParameterDescriptor} denotes a ParameterDescriptor as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface ParameterDescriptor extends Node {
	/**
	 * Returns a {@code Class} representation of this {@code ParameterDescriptor} instance.
	 * <p>
	 * If the {@code Class} cannot be found, a {@code ClassNotFoundException} will be thrown.
	 * 
	 * @return a {@code Class} representation of this {@code ParameterDescriptor} instance
	 * @throws ClassNotFoundException thrown if, and only if, the {@code Class} cannot be found
	 */
	Class<?> toClass() throws ClassNotFoundException;
	
	/**
	 * Returns a {@code String} representation of this {@code ParameterDescriptor} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code ParameterDescriptor} instance in external form
	 */
	String toExternalForm();
	
	/**
	 * Returns a {@code String} representation of this {@code ParameterDescriptor} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code ParameterDescriptor} instance in internal form
	 */
	String toInternalForm();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code ParameterDescriptor} instance that excludes all package names that are equal to {@code "java.lang"} from {@code parameterDescriptor}.
	 * <p>
	 * If {@code parameterDescriptor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * ParameterDescriptor.excludePackageName(parameterDescriptor, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param parameterDescriptor a {@code ParameterDescriptor} instance
	 * @return a {@code ParameterDescriptor} instance that excludes all package names that are equal to {@code "java.lang"} from {@code parameterDescriptor}
	 * @throws NullPointerException thrown if, and only if, {@code parameterDescriptor} is {@code null}
	 */
	static ParameterDescriptor excludePackageName(final ParameterDescriptor parameterDescriptor) {
		return excludePackageName(parameterDescriptor, "java.lang");
	}
	
	/**
	 * Returns a {@code ParameterDescriptor} instance that excludes all package names that are equal to {@code packageName} from {@code parameterDescriptor}.
	 * <p>
	 * If either {@code parameterDescriptor} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param parameterDescriptor a {@code ParameterDescriptor} instance
	 * @param packageName the package name to exclude
	 * @return a {@code ParameterDescriptor} instance that excludes all package names that are equal to {@code packageName} from {@code parameterDescriptor}
	 * @throws NullPointerException thrown if, and only if, either {@code parameterDescriptor} or {@code packageName} are {@code null}
	 */
	static ParameterDescriptor excludePackageName(final ParameterDescriptor parameterDescriptor, final String packageName) {
		Objects.requireNonNull(parameterDescriptor, "parameterDescriptor == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, parameterDescriptor);
	}
	
	/**
	 * Parses {@code string} into a {@code ParameterDescriptor} instance.
	 * <p>
	 * Returns a {@code ParameterDescriptor} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code ParameterDescriptor} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	static ParameterDescriptor parseParameterDescriptor(final String string) {
		return Parsers.parseParameterDescriptor(new TextScanner(string));
	}
}