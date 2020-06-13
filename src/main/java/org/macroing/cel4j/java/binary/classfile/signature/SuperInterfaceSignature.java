/**
 * Copyright 2009 - 2020 J&#246;rgen Lundgren
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

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code SuperInterfaceSignature} denotes a SuperinterfaceSignature as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface SuperInterfaceSignature extends Node {
	/**
	 * Returns a {@code String} representation of this {@code SuperInterfaceSignature} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code SuperInterfaceSignature} instance in external form
	 */
	String toExternalForm();
	
	/**
	 * Returns a {@code String} representation of this {@code SuperInterfaceSignature} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code SuperInterfaceSignature} instance in internal form
	 */
	String toInternalForm();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Parses {@code string} into a {@code SuperInterfaceSignature} instance.
	 * <p>
	 * Returns a {@code SuperInterfaceSignature} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code SuperInterfaceSignature} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	static SuperInterfaceSignature parseSuperInterfaceSignature(final String string) {
		return Parsers.parseSuperInterfaceSignature(new TextScanner(string));
	}
}