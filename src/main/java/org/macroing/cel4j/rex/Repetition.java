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
package org.macroing.cel4j.rex;

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;

import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class Repetition {
	public static final Repetition ANY = new Repetition(0, Integer.MAX_VALUE);
	public static final Repetition ONE = new Repetition(1, 1);
	public static final Repetition ONE_OR_MORE = new Repetition(1, Integer.MAX_VALUE);
	public static final Repetition ZERO_OR_ONE = new Repetition(0, 1);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final int maximum;
	private final int minimum;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Repetition(final int minimum, final int maximum) {
		this.maximum = ParameterArguments.requireRange(maximum, minimum, Integer.MAX_VALUE, "maximum");
		this.minimum = ParameterArguments.requireRange(minimum, 0, Integer.MAX_VALUE, "minimum");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the source associated with this {@code Repetition} instance.
	 * 
	 * @return the source associated with this {@code Repetition} instance
	 */
	public String getSource() {
		if(this.minimum == 0 && this.maximum == Integer.MAX_VALUE) {
			return "*";
		} else if(this.minimum == 1 && this.maximum == 1) {
			return "";
		} else if(this.minimum == 1 && this.maximum == Integer.MAX_VALUE) {
			return "+";
		} else if(this.minimum == 0 && this.maximum == 1) {
			return "?";
		} else {
			return String.format("{%s,%s}", Integer.toString(this.minimum), Integer.toString(this.maximum));
		}
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Repetition} instance.
	 * 
	 * @return a {@code String} representation of this {@code Repetition} instance
	 */
	@Override
	public String toString() {
		return String.format("new Repetition(%s, %s)", Integer.toString(getMinimum()), Integer.toString(getMaximum()));
	}
	
	/**
	 * Compares {@code object} to this {@code Repetition} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Repetition}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Repetition} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Repetition}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Repetition)) {
			return false;
		} else if(this.maximum != Repetition.class.cast(object).maximum) {
			return false;
		} else if(this.minimum != Repetition.class.cast(object).minimum) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	public int getMaximum() {
		return this.maximum;
	}
	
//	TODO: Add Javadocs!
	public int getMinimum() {
		return this.minimum;
	}
	
	/**
	 * Returns a hash code for this {@code Repetition} instance.
	 * 
	 * @return a hash code for this {@code Repetition} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.maximum), Integer.valueOf(this.minimum));
	}
}