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
 * A {@code Select} represents a {@code select} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Select extends ContentElement<Option, Elements<Option>> {
	/**
	 * The initial {@link Display} associated with a {@code Select} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.INLINE;
	
	/**
	 * The name associated with a {@code Select} instance.
	 */
	public static final String NAME = "select";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeAutoFocus;
	private final Attribute attributeDisabled;
	private final Attribute attributeForm;
	private final Attribute attributeMultiple;
	private final Attribute attributeName;
	private final Attribute attributeRequired;
	private final Attribute attributeSize;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Select} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Select(new Elements<>(Select.DISPLAY_INITIAL));
	 * }
	 * </pre>
	 */
	public Select() {
		this(new Elements<>(DISPLAY_INITIAL));
	}
	
	/**
	 * Constructs a new {@code Select} instance.
	 * <p>
	 * If {@code elements} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param elements the {@link Elements} associated with this {@code Select} instance
	 * @throws NullPointerException thrown if, and only if, {@code elements} is {@code null}
	 */
	public Select(final Elements<Option> elements) {
		super(NAME, DISPLAY_INITIAL, elements);
		
		this.attributeAutoFocus = new Attribute("autofocus");
		this.attributeDisabled = new Attribute("disabled");
		this.attributeForm = new Attribute("form");
		this.attributeMultiple = new Attribute("multiple");
		this.attributeName = new Attribute("name");
		this.attributeRequired = new Attribute("required");
		this.attributeSize = new Attribute("size");
		
		addAttribute(this.attributeAutoFocus);
		addAttribute(this.attributeDisabled);
		addAttribute(this.attributeForm);
		addAttribute(this.attributeMultiple);
		addAttribute(this.attributeName);
		addAttribute(this.attributeRequired);
		addAttribute(this.attributeSize);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "autofocus"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "autofocus"}
	 */
	public Attribute getAttributeAutoFocus() {
		return this.attributeAutoFocus;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "disabled"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "disabled"}
	 */
	public Attribute getAttributeDisabled() {
		return this.attributeDisabled;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "form"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "form"}
	 */
	public Attribute getAttributeForm() {
		return this.attributeForm;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "multiple"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "multiple"}
	 */
	public Attribute getAttributeMultiple() {
		return this.attributeMultiple;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "name"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "name"}
	 */
	public Attribute getAttributeName() {
		return this.attributeName;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "required"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "required"}
	 */
	public Attribute getAttributeRequired() {
		return this.attributeRequired;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "size"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "size"}
	 */
	public Attribute getAttributeSize() {
		return this.attributeSize;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Select} instance.
	 * 
	 * @return a {@code String} representation of this {@code Select} instance
	 */
	@Override
	public String toString() {
		return "new Select()";
	}
	
	/**
	 * Compares {@code object} to this {@code Select} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Select}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Select} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Select}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Select)) {
			return false;
		} else if(!Objects.equals(getAttributes(), Select.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), Select.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getContent(), Select.class.cast(object).getContent())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Select} instance.
	 * 
	 * @return a hash code for this {@code Select} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getContent());
	}
}