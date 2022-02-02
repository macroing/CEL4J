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
 * A {@code TD} represents a {@code td} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class TD extends ContentElement<Element, Content<Element>> {
	/**
	 * The initial {@link Display} associated with a {@code TD} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.BLOCK;
	
	/**
	 * The name associated with a {@code TD} instance.
	 */
	public static final String NAME = "td";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeColSpan;
	private final Attribute attributeHeaders;
	private final Attribute attributeRowSpan;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code TD} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new TD(new Text());
	 * }
	 * </pre>
	 */
	public TD() {
		this(new Text());
	}
	
	/**
	 * Constructs a new {@code TD} instance.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content the {@link Content} associated with this {@code TD} instance
	 * @throws NullPointerException thrown if, and only if, {@code content} is {@code null}
	 */
	public TD(final Content<Element> content) {
		super(NAME, DISPLAY_INITIAL, content);
		
		this.attributeColSpan = new Attribute("colspan");
		this.attributeHeaders = new Attribute("headers");
		this.attributeRowSpan = new Attribute("rowspan");
		
		addAttribute(this.attributeColSpan);
		addAttribute(this.attributeHeaders);
		addAttribute(this.attributeRowSpan);
	}
	
	/**
	 * Constructs a new {@code TD} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new TD(new Text(string));
	 * }
	 * </pre>
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public TD(final String string) {
		this(new Text(string));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "colspan"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "colspan"}
	 */
	public Attribute getAttributeColSpan() {
		return this.attributeColSpan;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "headers"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "headers"}
	 */
	public Attribute getAttributeHeaders() {
		return this.attributeHeaders;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "rowspan"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "rowspan"}
	 */
	public Attribute getAttributeRowSpan() {
		return this.attributeRowSpan;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TD} instance.
	 * 
	 * @return a {@code String} representation of this {@code TD} instance
	 */
	@Override
	public String toString() {
		return "new TD()";
	}
	
	/**
	 * Compares {@code object} to this {@code TD} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code TD}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code TD} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code TD}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof TD)) {
			return false;
		} else if(!Objects.equals(getAttributes(), TD.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), TD.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getContent(), TD.class.cast(object).getContent())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code TD} instance.
	 * 
	 * @return a hash code for this {@code TD} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getContent());
	}
}