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
package org.macroing.cel4j.rex;

import java.util.Objects;

import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code Repetition} contains the minimum and maximum repetition bounds that must be satisfied for a match to occur.
 * <p>
 * The current implementation uses {@code Integer.MAX_VALUE} to denote a bound of positive infinity. This may change.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Repetition {
	/**
	 * A {@code Repetition} with a minimum bound of 0 and a maximum bound of positive infinity.
	 */
	public static final Repetition ANY = new Repetition(0, Integer.MAX_VALUE);
	
	/**
	 * A {@code Repetition} with a minimum bound of 1 and a maximum bound of 1.
	 */
	public static final Repetition ONE = new Repetition(1, 1);
	
	/**
	 * A {@code Repetition} with a minimum bound of 1 and a maximum bound of positive infinity.
	 */
	public static final Repetition ONE_OR_MORE = new Repetition(1, Integer.MAX_VALUE);
	
	/**
	 * A {@code Repetition} with a minimum bound of 0 and a maximum bound of 1.
	 */
	public static final Repetition ZERO_OR_ONE = new Repetition(0, 1);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final int maximum;
	private final int minimum;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Repetition} instance.
	 * <p>
	 * If either {@code minimum} is less than {@code 0} or {@code maximum} is less than {@code minimum}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param minimum the minimum repetition count
	 * @param maximum the maximum repetition count
	 * @throws IllegalArgumentException thrown if, and only if, either {@code minimum} is less than {@code 0} or {@code maximum} is less than {@code minimum}
	 */
	public Repetition(final int minimum, final int maximum) {
		this.maximum = ParameterArguments.requireRange(maximum, minimum, Integer.MAX_VALUE, "maximum");
		this.minimum = ParameterArguments.requireRange(minimum, 0, Integer.MAX_VALUE, "minimum");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the source code associated with this {@code Repetition} instance.
	 * 
	 * @return the source code associated with this {@code Repetition} instance
	 */
	public String getSourceCode() {
		if(getMinimum() == 0 && getMaximum() == Integer.MAX_VALUE) {
			return "*";
		} else if(getMinimum() == 1 && getMaximum() == 1) {
			return "";
		} else if(getMinimum() == 1 && getMaximum() == Integer.MAX_VALUE) {
			return "+";
		} else if(getMinimum() == 0 && getMaximum() == 1) {
			return "?";
		} else {
			return String.format("{%s,%s}", Integer.toString(getMinimum()), Integer.toString(getMaximum()));
		}
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Repetition} instance.
	 * 
	 * @return a {@code String} representation of this {@code Repetition} instance
	 */
	@Override
	public String toString() {
		if(getMinimum() == 0 && getMaximum() == Integer.MAX_VALUE) {
			return "Repetition.ANY";
		} else if(getMinimum() == 1 && getMaximum() == 1) {
			return "Repetition.ONE";
		} else if(getMinimum() == 1 && getMaximum() == Integer.MAX_VALUE) {
			return "Repetition.ONE_OR_MORE";
		} else if(getMinimum() == 0 && getMaximum() == 1) {
			return "Repetition.ZERO_OR_ONE";
		} else {
			return String.format("new Repetition(%s, %s)", Integer.toString(getMinimum()), Integer.toString(getMaximum()));
		}
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
		} else if(getMaximum() != Repetition.class.cast(object).getMaximum()) {
			return false;
		} else if(getMinimum() != Repetition.class.cast(object).getMinimum()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the maximum repetition count.
	 * 
	 * @return the maximum repetition count
	 */
	public int getMaximum() {
		return this.maximum;
	}
	
	/**
	 * Returns the minimum repetition count.
	 * 
	 * @return the minimum repetition count
	 */
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
		return Objects.hash(Integer.valueOf(getMaximum()), Integer.valueOf(getMinimum()));
	}
}