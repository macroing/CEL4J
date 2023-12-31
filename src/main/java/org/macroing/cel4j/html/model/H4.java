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
package org.macroing.cel4j.html.model;

import java.util.Objects;

/**
 * An {@code H4} represents an {@code h4} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class H4 extends ContentElement<Element, Content<Element>> {
	/**
	 * The initial {@link Display} associated with an {@code H4} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.BLOCK;
	
	/**
	 * The name associated with an {@code H4} instance.
	 */
	public static final String NAME = "h4";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code H4} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new H4(new Text());
	 * }
	 * </pre>
	 */
	public H4() {
		this(new Text());
	}
	
	/**
	 * Constructs a new {@code H4} instance.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content the {@link Content} associated with this {@code H4} instance
	 * @throws NullPointerException thrown if, and only if, {@code content} is {@code null}
	 */
	public H4(final Content<Element> content) {
		super(NAME, DISPLAY_INITIAL, content);
	}
	
	/**
	 * Constructs a new {@code H4} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new H4(new Text(string));
	 * }
	 * </pre>
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public H4(final String string) {
		this(new Text(string));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code H4} instance.
	 * 
	 * @return a {@code String} representation of this {@code H4} instance
	 */
	@Override
	public String toString() {
		return "new H4()";
	}
	
	/**
	 * Compares {@code object} to this {@code H4} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code H4}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code H4} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code H4}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof H4)) {
			return false;
		} else if(!Objects.equals(getAttributes(), H4.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), H4.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getContent(), H4.class.cast(object).getContent())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code H4} instance.
	 * 
	 * @return a hash code for this {@code H4} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getContent());
	}
}