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
package org.macroing.cel4j.scanner;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A {@code Key} is a key associated with state in a {@link Scanner} instance.
 * <p>
 * To obtain a {@code Key}, {@link Scanner#stateSave()} should be called. This saves the state, which consists of two indices. Once a {@code Key} has been obtained, a call to {@link Scanner#stateLoad(Key)} will load the state associated with the
 * {@code Key} into the {@code Scanner}. If {@link Scanner#stateDelete(Key)} is called, the state associated with the {@code Key} is deleted from the {@code Scanner}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Key {
	private final long value;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Key} instance.
	 */
	public Key() {
		this.value = ThreadLocalRandom.current().nextLong();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Compares {@code object} to this {@code Key} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Key}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Key} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Key}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Key)) {
			return false;
		} else if(Long.compare(this.value, Key.class.cast(object).value) != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Key} instance.
	 * 
	 * @return a hash code for this {@code Key} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Long.valueOf(this.value));
	}
}