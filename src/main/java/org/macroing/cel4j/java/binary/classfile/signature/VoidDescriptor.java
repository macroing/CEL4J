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

import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code VoidDescriptor} denotes a VoidDescriptor as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum VoidDescriptor implements Result {
	/**
	 * The {@code VoidDescriptor} instance.
	 */
	VOID(Constants.VOID_TERM, Constants.VOID_TYPE);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String term;
	private final String type;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private VoidDescriptor(final String term, final String type) {
		this.term = term;
		this.type = type;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} with the term associated with this {@code VoidDescriptor} instance.
	 * 
	 * @return a {@code String} with the term associated with this {@code VoidDescriptor} instance
	 */
	public String getTerm() {
		return this.term;
	}
	
	/**
	 * Returns a {@code String} with the type associated with this {@code VoidDescriptor} instance.
	 * 
	 * @return a {@code String} with the type associated with this {@code VoidDescriptor} instance
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code VoidDescriptor} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code VoidDescriptor} instance in external form
	 */
	@Override
	public String toExternalForm() {
		return this.type;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code VoidDescriptor} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code VoidDescriptor} instance in internal form
	 */
	@Override
	public String toInternalForm() {
		return this.term;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code VoidDescriptor} instance.
	 * 
	 * @return a {@code String} representation of this {@code VoidDescriptor} instance
	 */
	@Override
	public String toString() {
		return String.format("VoidDescriptor: [Term=%s], [Type=%s], [ExternalForm=%s], [InternalForm=%s]", getTerm(), getType(), toExternalForm(), toInternalForm());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Parses {@code string} into a {@code VoidDescriptor} instance.
	 * <p>
	 * Returns a {@code VoidDescriptor} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code VoidDescriptor} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static VoidDescriptor parseVoidDescriptor(final String string) {
		return Parsers.parseVoidDescriptor(new TextScanner(string));
	}
}