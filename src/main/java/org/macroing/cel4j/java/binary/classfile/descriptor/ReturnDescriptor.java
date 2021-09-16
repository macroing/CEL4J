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
 * A {@code ReturnDescriptor} denotes a ReturnDescriptor as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface ReturnDescriptor extends Node {
	/**
	 * Returns a {@code Class} representation of this {@code ReturnDescriptor} instance.
	 * <p>
	 * If the {@code Class} cannot be found, a {@code ClassNotFoundException} will be thrown.
	 * 
	 * @return a {@code Class} representation of this {@code ReturnDescriptor} instance
	 * @throws ClassNotFoundException thrown if, and only if, the {@code Class} cannot be found
	 */
	Class<?> toClass() throws ClassNotFoundException;
	
	/**
	 * Returns a {@code String} with the term associated with this {@code ReturnDescriptor} instance.
	 * 
	 * @return a {@code String} with the term associated with this {@code ReturnDescriptor} instance
	 */
	String getTerm();
	
	/**
	 * Returns a {@code String} with the type associated with this {@code ReturnDescriptor} instance.
	 * 
	 * @return a {@code String} with the type associated with this {@code ReturnDescriptor} instance
	 */
	String getType();
	
	/**
	 * Returns a {@code String} representation of this {@code ReturnDescriptor} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code ReturnDescriptor} instance in external form
	 */
	String toExternalForm();
	
	/**
	 * Returns a {@code String} representation of this {@code ReturnDescriptor} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code ReturnDescriptor} instance in internal form
	 */
	String toInternalForm();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code ReturnDescriptor} instance that excludes all package names that are equal to {@code "java.lang"} from {@code returnDescriptor}.
	 * <p>
	 * If {@code returnDescriptor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * ReturnDescriptor.excludePackageName(returnDescriptor, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param returnDescriptor a {@code ReturnDescriptor} instance
	 * @return a {@code ReturnDescriptor} instance that excludes all package names that are equal to {@code "java.lang"} from {@code returnDescriptor}
	 * @throws NullPointerException thrown if, and only if, {@code returnDescriptor} is {@code null}
	 */
	static ReturnDescriptor excludePackageName(final ReturnDescriptor returnDescriptor) {
		return excludePackageName(returnDescriptor, "java.lang");
	}
	
	/**
	 * Returns a {@code ReturnDescriptor} instance that excludes all package names that are equal to {@code packageName} from {@code returnDescriptor}.
	 * <p>
	 * If either {@code returnDescriptor} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param returnDescriptor a {@code ReturnDescriptor} instance
	 * @param packageName the package name to exclude
	 * @return a {@code ReturnDescriptor} instance that excludes all package names that are equal to {@code packageName} from {@code returnDescriptor}
	 * @throws NullPointerException thrown if, and only if, either {@code returnDescriptor} or {@code packageName} are {@code null}
	 */
	static ReturnDescriptor excludePackageName(final ReturnDescriptor returnDescriptor, final String packageName) {
		Objects.requireNonNull(returnDescriptor, "returnDescriptor == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, returnDescriptor);
	}
	
	/**
	 * Parses {@code string} into a {@code ReturnDescriptor} instance.
	 * <p>
	 * Returns a {@code ReturnDescriptor} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code ReturnDescriptor} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	static ReturnDescriptor parseReturnDescriptor(final String string) {
		return Parsers.parseReturnDescriptor(new TextScanner(string));
	}
}