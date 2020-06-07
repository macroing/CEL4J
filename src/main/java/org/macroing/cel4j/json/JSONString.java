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
package org.macroing.cel4j.json;

import java.util.Objects;

/**
 * A {@code JSONString} denotes a JSON string.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class JSONString implements JSONType {
	private final String value;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code JSONString} instance.
	 * <p>
	 * If {@code value} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param value the value to use
	 * @throws NullPointerException thrown if, and only if, {@code value} is {@code null}
	 */
	public JSONString(final String value) {
		this.value = Objects.requireNonNull(value, "value == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@code String} value of this {@code JSONString} instance.
	 * 
	 * @return the {@code String} value of this {@code JSONString} instance
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Returns a {@code String} with the source code of this {@code JSONString} instance.
	 * 
	 * @return a {@code String} with the source code of this {@code JSONString} instance
	 */
	@Override
	public String toSourceCode() {
		return String.format("\"%s\"", getValue());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code JSONString} instance.
	 * 
	 * @return a {@code String} representation of this {@code JSONString} instance
	 */
	@Override
	public String toString() {
		return String.format("new JSONString(\"%s\")", getValue());
	}
	
	/**
	 * Compares {@code object} to this {@code JSONString} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code JSONString}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code JSONString} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code JSONString}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JSONString)) {
			return false;
		} else if(!Objects.equals(getValue(), JSONString.class.cast(object).getValue())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code JSONString} instance.
	 * 
	 * @return a hash code for this {@code JSONString} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getValue());
	}
}