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
package org.macroing.cel4j.lexer;

import java.util.Objects;
import java.util.Optional;

/**
 * A {@code Token} denotes a token.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Token {
	private final Object object;
	private final String name;
	private final String text;
	private final boolean isSkippable;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Token} instance.
	 * <p>
	 * If either {@code name} or {@code text} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Token(name, text, false);
	 * }
	 * </pre>
	 * 
	 * @param name the name to use
	 * @param text the text to use
	 * @throws NullPointerException thrown if, and only if, either {@code name} or {@code text} are {@code null}
	 */
	public Token(final String name, final String text) {
		this(name, text, false);
	}
	
	/**
	 * Constructs a new {@code Token} instance.
	 * <p>
	 * If either {@code name} or {@code text} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Token(name, text, isSkippable, null);
	 * }
	 * </pre>
	 * 
	 * @param name the name to use
	 * @param text the text to use
	 * @param isSkippable {@code true} if, and only if, this {@code Token} should be skippable, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code name} or {@code text} are {@code null}
	 */
	public Token(final String name, final String text, final boolean isSkippable) {
		this(name, text, isSkippable, null);
	}
	
	/**
	 * Constructs a new {@code Token} instance.
	 * <p>
	 * If either {@code name} or {@code text} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the name to use
	 * @param text the text to use
	 * @param isSkippable {@code true} if, and only if, this {@code Token} should be skippable, {@code false} otherwise
	 * @param object the {@code Object} that is associated with this {@code Token}, or {@code null}
	 * @throws NullPointerException thrown if, and only if, either {@code name} or {@code text} are {@code null}
	 */
	public Token(final String name, final String text, final boolean isSkippable, final Object object) {
		this.name = Objects.requireNonNull(name, "name == null");
		this.text = Objects.requireNonNull(text, "text == null");
		this.isSkippable = isSkippable;
		this.object = object;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code Optional} with the optional {@code Object} that is associated with this {@code Token} instance.
	 * 
	 * @return an {@code Optional} with the optional {@code Object} that is associated with this {@code Token} instance
	 */
	public Optional<Object> getObject() {
		return Optional.ofNullable(this.object);
	}
	
	/**
	 * Returns the name of this {@code Token} instance.
	 * 
	 * @return the name of this {@code Token} instance
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the text of this {@code Token} instance.
	 * 
	 * @return the text of this {@code Token} instance
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Token} instance.
	 * 
	 * @return a {@code String} representation of this {@code Token} instance
	 */
	@Override
	public String toString() {
		return String.format("new Token(\"%s\", \"%s\", %s)", getName(), getText(), Boolean.toString(isSkippable()));
	}
	
	/**
	 * Compares {@code object} to this {@code Token} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Token}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Token} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Token}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Token)) {
			return false;
		} else if(!Objects.equals(getObject(), Token.class.cast(object).getObject())) {
			return false;
		} else if(!Objects.equals(getName(), Token.class.cast(object).getName())) {
			return false;
		} else if(!Objects.equals(getText(), Token.class.cast(object).getText())) {
			return false;
		} else if(isSkippable() != Token.class.cast(object).isSkippable()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Token} instance is skippable, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Token} instance is skippable, {@code false} otherwise
	 */
	public boolean isSkippable() {
		return this.isSkippable;
	}
	
	/**
	 * Returns a hash code for this {@code Token} instance.
	 * 
	 * @return a hash code for this {@code Token} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getObject(), getName(), getText(), Boolean.valueOf(isSkippable()));
	}
}