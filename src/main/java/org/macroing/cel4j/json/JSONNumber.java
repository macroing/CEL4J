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
package org.macroing.cel4j.json;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * A {@code JSONNumber} denotes a JSON number.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class JSONNumber implements JSONType {
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final double value;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code JSONNumber} instance.
	 * 
	 * @param value the value to use
	 */
	public JSONNumber(final double value) {
		this.value = value;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} with the source code of this {@code JSONNumber} instance.
	 * 
	 * @return a {@code String} with the source code of this {@code JSONNumber} instance
	 */
	@Override
	public String toSourceCode() {
		return DECIMAL_FORMAT.format(getValue());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code JSONNumber} instance.
	 * 
	 * @return a {@code String} representation of this {@code JSONNumber} instance
	 */
	@Override
	public String toString() {
		return String.format("new JSONNumber(%s)", DECIMAL_FORMAT.format(getValue()));
	}
	
	/**
	 * Compares {@code object} to this {@code JSONNumber} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code JSONNumber}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code JSONNumber} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code JSONNumber}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JSONNumber)) {
			return false;
		} else if(Double.compare(getValue(), JSONNumber.class.cast(object).getValue()) != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the {@code double} value of this {@code JSONNumber} instance.
	 * 
	 * @return the {@code double} value of this {@code JSONNumber} instance
	 */
	public double getValue() {
		return this.value;
	}
	
	/**
	 * Returns the {@code double} value of this {@code JSONNumber} instance as {@code int}.
	 * 
	 * @return the {@code double} value of this {@code JSONNumber} instance as {@code int}
	 */
	public int getValueAsInt() {
		return new Double(getValue()).intValue();
	}
	
	/**
	 * Returns a hash code for this {@code JSONNumber} instance.
	 * 
	 * @return a hash code for this {@code JSONNumber} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(getValue()));
	}
	
	/**
	 * Returns the {@code double} value of this {@code JSONNumber} instance as {@code long}.
	 * 
	 * @return the {@code double} value of this {@code JSONNumber} instance as {@code long}
	 */
	public long getValueAsLong() {
		return new Double(getValue()).longValue();
	}
	
	/**
	 * Returns the {@code double} value of this {@code JSONNumber} instance as {@code short}.
	 * 
	 * @return the {@code double} value of this {@code JSONNumber} instance as {@code short}
	 */
	public short getValueAsShort() {
		return new Double(getValue()).shortValue();
	}
}