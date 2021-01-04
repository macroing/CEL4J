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
package org.macroing.cel4j.util;

import java.util.Objects;

/**
 * A {@code Pair} represents a pair of values, X and Y.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Pair<X, Y> {
	private final X x;
	private final Y y;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Pair} instance.
	 * <p>
	 * If either {@code x} or {@code y} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param x the value of X
	 * @param y the value of Y
	 * @throws NullPointerException thrown if, and only if, either {@code x} or {@code y} are {@code null}
	 */
	public Pair(final X x, final Y y) {
		this.x = Objects.requireNonNull(x, "x == null");
		this.y = Objects.requireNonNull(y, "y == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Pair} instance.
	 * 
	 * @return a {@code String} representation of this {@code Pair} instance
	 */
	@Override
	public String toString() {
		return String.format("new Pair(%s, %s)", this.x, this.y);
	}
	
	/**
	 * Returns the value of X.
	 * 
	 * @return the value of X
	 */
	public X getX() {
		return this.x;
	}
	
	/**
	 * Returns the value of Y.
	 * 
	 * @return the value of Y
	 */
	public Y getY() {
		return this.y;
	}
	
	/**
	 * Compares {@code object} to this {@code Pair} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Pair}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Pair} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Pair}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Pair)) {
			return false;
		} else if(!Objects.equals(this.x, Pair.class.cast(object).x)) {
			return false;
		} else if(!Objects.equals(this.y, Pair.class.cast(object).y)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Pair} instance.
	 * 
	 * @return a hash code for this {@code Pair} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.x, this.y);
	}
}