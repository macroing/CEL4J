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
package org.macroing.cel4j.html.model;

import java.util.Objects;

/**
 * A {@code Span} represents a {@code span} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Span extends ContentElement<Element, Content<Element>> {
	/**
	 * The initial {@link Display} associated with a {@code Span} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.INLINE;
	
	/**
	 * The name associated with a {@code Span} instance.
	 */
	public static final String NAME = "span";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Span} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Span(new Text());
	 * }
	 * </pre>
	 */
	public Span() {
		this(new Text());
	}
	
	/**
	 * Constructs a new {@code Span} instance.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content the {@link Content} associated with this {@code Span} instance
	 * @throws NullPointerException thrown if, and only if, {@code content} is {@code null}
	 */
	public Span(final Content<Element> content) {
		super(NAME, DISPLAY_INITIAL, content);
	}
	
	/**
	 * Constructs a new {@code Span} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Span(new Text(string));
	 * }
	 * </pre>
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public Span(final String string) {
		this(new Text(string));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Span} instance.
	 * 
	 * @return a {@code String} representation of this {@code Span} instance
	 */
	@Override
	public String toString() {
		return "new Span()";
	}
	
	/**
	 * Compares {@code object} to this {@code Span} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Span}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Span} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Span}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Span)) {
			return false;
		} else if(!Objects.equals(getAttributes(), Span.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), Span.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getContent(), Span.class.cast(object).getContent())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Span} instance.
	 * 
	 * @return a hash code for this {@code Span} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getContent());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Span} instance that combines {@code spanA} and {@code spanB}.
	 * <p>
	 * If either {@code spanA} or {@code spanB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Span.combine(spanA, spanB, false);
	 * }
	 * </pre>
	 * 
	 * @param spanA the {@code Span} instance that is added first
	 * @param spanB the {@code Span} instance that is added last
	 * @return a {@code Span} instance that combines {@code spanA} and {@code spanB}
	 * @throws NullPointerException thrown if, and only if, either {@code spanA} or {@code spanB} are {@code null}
	 */
	public static Span combine(final Span spanA, final Span spanB) {
		return combine(spanA, spanB, false);
	}
	
	/**
	 * Returns a {@code Span} instance that combines {@code spanA} and {@code spanB}.
	 * <p>
	 * If either {@code spanA} or {@code spanB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param spanA the {@code Span} instance that is added first
	 * @param spanB the {@code Span} instance that is added last
	 * @param isSeparatingWithSpace {@code true} if, and only if, {@code new Span(" ")} should be added between {@code spanA} and {@code spanB}, {@code false} otherwise
	 * @return a {@code Span} instance that combines {@code spanA} and {@code spanB}
	 * @throws NullPointerException thrown if, and only if, either {@code spanA} or {@code spanB} are {@code null}
	 */
	public static Span combine(final Span spanA, final Span spanB, final boolean isSeparatingWithSpace) {
		final
		Elements<Element> elements = new Elements<>(DISPLAY_INITIAL);
		elements.addElement(Objects.requireNonNull(spanA, "spanA == null"));
		
		if(isSeparatingWithSpace) {
			elements.addElement(new Span(" "));
		}
		
		elements.addElement(Objects.requireNonNull(spanB, "spanB == null"));
		
		return new Span(elements);
	}
}