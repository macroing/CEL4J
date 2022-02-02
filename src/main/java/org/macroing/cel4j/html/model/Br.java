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
package org.macroing.cel4j.html.model;

import java.util.Objects;

/**
 * A {@code Br} represents a {@code br} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Br extends EmptyElement {
	/**
	 * The initial {@link Display} associated with a {@code Br} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.INLINE;
	
	/**
	 * The name associated with a {@code Br} instance.
	 */
	public static final String NAME = "br";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Br} instance.
	 */
	public Br() {
		super(NAME, DISPLAY_INITIAL);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Br} instance.
	 * 
	 * @return a {@code String} representation of this {@code Br} instance
	 */
	@Override
	public String toString() {
		return "new Br()";
	}
	
	/**
	 * Compares {@code object} to this {@code Br} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Br}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Br} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Br}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Br)) {
			return false;
		} else if(!Objects.equals(getAttributes(), Br.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), Br.class.cast(object).getDisplay())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Br} instance.
	 * 
	 * @return a hash code for this {@code Br} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay());
	}
}