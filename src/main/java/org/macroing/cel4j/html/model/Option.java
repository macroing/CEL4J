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
package org.macroing.cel4j.html.model;

import java.util.Objects;

/**
 * An {@code Option} represents an {@code option} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Option extends ContentElement<Element, Content<Element>> {
	/**
	 * The initial {@link Display} associated with an {@code Option} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.INLINE;
	
	/**
	 * The name associated with an {@code Option} instance.
	 */
	public static final String NAME = "option";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeDisabled;
	private final Attribute attributeLabel;
	private final Attribute attributeSelected;
	private final Attribute attributeValue;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Option} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Option(new Text());
	 * }
	 * </pre>
	 */
	public Option() {
		this(new Text());
	}
	
	/**
	 * Constructs a new {@code Option} instance.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content the {@link Content} associated with this {@code Option} instance
	 * @throws NullPointerException thrown if, and only if, {@code content} is {@code null}
	 */
	public Option(final Content<Element> content) {
		super(NAME, DISPLAY_INITIAL, content);
		
		this.attributeDisabled = new Attribute("disabled");
		this.attributeLabel = new Attribute("label");
		this.attributeSelected = new Attribute("selected");
		this.attributeValue = new Attribute("value");
		
		addAttribute(this.attributeDisabled);
		addAttribute(this.attributeLabel);
		addAttribute(this.attributeSelected);
		addAttribute(this.attributeValue);
	}
	
	/**
	 * Constructs a new {@code Option} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Option(new Text(string));
	 * }
	 * </pre>
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public Option(final String string) {
		this(new Text(string));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "disabled"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "disabled"}
	 */
	public Attribute getAttributeDisabled() {
		return this.attributeDisabled;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "label"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "label"}
	 */
	public Attribute getAttributeLabel() {
		return this.attributeLabel;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "selected"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "selected"}
	 */
	public Attribute getAttributeSelected() {
		return this.attributeSelected;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "value"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "value"}
	 */
	public Attribute getAttributeValue() {
		return this.attributeValue;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Option} instance.
	 * 
	 * @return a {@code String} representation of this {@code Option} instance
	 */
	@Override
	public String toString() {
		return "new Option()";
	}
	
	/**
	 * Compares {@code object} to this {@code Option} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Option}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Option} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Option}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Option)) {
			return false;
		} else if(!Objects.equals(getAttributes(), Option.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), Option.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getContent(), Option.class.cast(object).getContent())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Option} instance.
	 * 
	 * @return a hash code for this {@code Option} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getContent());
	}
}