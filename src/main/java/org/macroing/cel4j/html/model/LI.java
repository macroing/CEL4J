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
 * An {@code LI} represents an {@code li} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class LI extends ContentElement<Element, Content<Element>> {
	/**
	 * The initial {@link Display} associated with an {@code LI} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.BLOCK;
	
	/**
	 * The name associated with an {@code LI} instance.
	 */
	public static final String NAME = "li";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeValue;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code LI} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new LI(new Text());
	 * }
	 * </pre>
	 */
	public LI() {
		this(new Text());
	}
	
	/**
	 * Constructs a new {@code LI} instance.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content the {@link Content} associated with this {@code LI} instance
	 * @throws NullPointerException thrown if, and only if, {@code content} is {@code null}
	 */
	public LI(final Content<Element> content) {
		super(NAME, DISPLAY_INITIAL, content);
		
		this.attributeValue = new Attribute("value");
		
		addAttribute(this.attributeValue);
	}
	
	/**
	 * Constructs a new {@code LI} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new LI(new Text(string));
	 * }
	 * </pre>
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public LI(final String string) {
		this(new Text(string));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "value"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "value"}
	 */
	public Attribute getAttributeValue() {
		return this.attributeValue;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code LI} instance.
	 * 
	 * @return a {@code String} representation of this {@code LI} instance
	 */
	@Override
	public String toString() {
		return "new LI()";
	}
	
	/**
	 * Compares {@code object} to this {@code LI} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code LI}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code LI} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code LI}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof LI)) {
			return false;
		} else if(!Objects.equals(getAttributes(), LI.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), LI.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getContent(), LI.class.cast(object).getContent())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code LI} instance.
	 * 
	 * @return a hash code for this {@code LI} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getContent());
	}
}