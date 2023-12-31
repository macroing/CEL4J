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
 * A {@code JSONToken} denotes a JSON token.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class JSONToken {
	private final String name;
	private final String text;
	private final boolean isSkippable;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code JSONToken} instance.
	 * <p>
	 * Calling this constructor is equivalent to {@code new JSONToken(name, text, false)}.
	 * <p>
	 * If either {@code name} or {@code text} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the name to use
	 * @param text the text to use
	 * @throws NullPointerException thrown if, and only if, either {@code name} or {@code text} are {@code null}
	 */
	public JSONToken(final String name, final String text) {
		this(name, text, false);
	}
	
	/**
	 * Constructs a new {@code JSONToken} instance.
	 * <p>
	 * If either {@code name} or {@code text} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the name to use
	 * @param text the text to use
	 * @param isSkippable {@code true} if, and only if, this {@code JSONToken} should be skippable, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code name} or {@code text} are {@code null}
	 */
	public JSONToken(final String name, final String text, final boolean isSkippable) {
		this.name = Objects.requireNonNull(name, "name == null");
		this.text = Objects.requireNonNull(text, "text == null");
		this.isSkippable = isSkippable;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the name of this {@code JSONToken} instance.
	 * 
	 * @return the name of this {@code JSONToken} instance
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the text of this {@code JSONToken} instance.
	 * 
	 * @return the text of this {@code JSONToken} instance
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code JSONToken} instance.
	 * 
	 * @return a {@code String} representation of this {@code JSONToken} instance
	 */
	@Override
	public String toString() {
		return String.format("new JSONToken(\"%s\", \"%s\", %s)", getName(), getText(), Boolean.toString(isSkippable()));
	}
	
	/**
	 * Compares {@code object} to this {@code JSONToken} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code JSONToken}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code JSONToken} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code JSONToken}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JSONToken)) {
			return false;
		} else if(!Objects.equals(getName(), JSONToken.class.cast(object).getName())) {
			return false;
		} else if(!Objects.equals(getText(), JSONToken.class.cast(object).getText())) {
			return false;
		} else if(isSkippable() != JSONToken.class.cast(object).isSkippable()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code JSONToken} instance is skippable, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code JSONToken} instance is skippable, {@code false} otherwise
	 */
	public boolean isSkippable() {
		return this.isSkippable;
	}
	
	/**
	 * Returns a hash code for this {@code JSONToken} instance.
	 * 
	 * @return a hash code for this {@code JSONToken} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), getText(), Boolean.valueOf(isSkippable()));
	}
}