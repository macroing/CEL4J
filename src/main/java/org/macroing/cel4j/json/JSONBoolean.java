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
package org.macroing.cel4j.json;

import java.util.Objects;

/**
 * A {@code JSONBoolean} denotes a JSON boolean.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class JSONBoolean implements JSONType {
	private final boolean value;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code JSONBoolean} instance.
	 * 
	 * @param value the value to use
	 */
	public JSONBoolean(final boolean value) {
		this.value = value;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} with the source code of this {@code JSONBoolean} instance.
	 * 
	 * @return a {@code String} with the source code of this {@code JSONBoolean} instance
	 */
	@Override
	public String toSourceCode() {
		return Boolean.toString(getValue());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code JSONBoolean} instance.
	 * 
	 * @return a {@code String} representation of this {@code JSONBoolean} instance
	 */
	@Override
	public String toString() {
		return String.format("new JSONBoolean(%s)", Boolean.toString(getValue()));
	}
	
	/**
	 * Compares {@code object} to this {@code JSONBoolean} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code JSONBoolean}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code JSONBoolean} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code JSONBoolean}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JSONBoolean)) {
			return false;
		} else if(getValue() != JSONBoolean.class.cast(object).getValue()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the {@code boolean} value of this {@code JSONBoolean} instance.
	 * 
	 * @return the {@code boolean} value of this {@code JSONBoolean} instance
	 */
	public boolean getValue() {
		return this.value;
	}
	
	/**
	 * Returns a hash code for this {@code JSONBoolean} instance.
	 * 
	 * @return a hash code for this {@code JSONBoolean} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Boolean.valueOf(getValue()));
	}
}