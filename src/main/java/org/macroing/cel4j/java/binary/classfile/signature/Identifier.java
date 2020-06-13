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

import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * An {@code Identifier} denotes an Identifier as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Identifier implements Node {
	private final String internalForm;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Identifier(final String internalForm) {
		this.internalForm = internalForm;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Identifier} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code Identifier} instance in external form
	 */
	public String toExternalForm() {
		return this.internalForm.replace('/', '.');
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Identifier} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code Identifier} instance in internal form
	 */
	public String toInternalForm() {
		return this.internalForm;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Identifier} instance.
	 * 
	 * @return a {@code String} representation of this {@code Identifier} instance
	 */
	@Override
	public String toString() {
		return String.format("Identifier: [InternalForm=%s]", toInternalForm());
	}
	
	/**
	 * Compares {@code object} to this {@code Identifier} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Identifier}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Identifier} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Identifier}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Identifier)) {
			return false;
		} else if(!Objects.equals(Identifier.class.cast(object).internalForm, this.internalForm)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Identifier} instance.
	 * 
	 * @return a hash code for this {@code Identifier} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.internalForm);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Parses {@code string} into an {@code Identifier} instance.
	 * <p>
	 * Returns an {@code Identifier} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return an {@code Identifier} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static Identifier parseIdentifier(final String string) {
		return Parsers.parseIdentifier(new TextScanner(string));
	}
}