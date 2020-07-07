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
 * A {@code Meta} represents a {@code meta} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Meta extends EmptyElement {
	/**
	 * The initial {@link Display} associated with a {@code Meta} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.NONE;
	
	/**
	 * The name associated with a {@code Meta} instance.
	 */
	public static final String NAME = "meta";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeCharSet;
	private final Attribute attributeContent;
	private final Attribute attributeHTTPEquiv;
	private final Attribute attributeName;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Meta} instance.
	 */
	public Meta() {
		super(NAME, DISPLAY_INITIAL);
		
		this.attributeCharSet = new Attribute("charset");
		this.attributeContent = new Attribute("content");
		this.attributeHTTPEquiv = new Attribute("http-equiv");
		this.attributeName = new Attribute("name");
		
		addAttribute(this.attributeCharSet);
		addAttribute(this.attributeContent);
		addAttribute(this.attributeHTTPEquiv);
		addAttribute(this.attributeName);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "charset"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "charset"}
	 */
	public Attribute getAttributeCharSet() {
		return this.attributeCharSet;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "content"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "content"}
	 */
	public Attribute getAttributeContent() {
		return this.attributeContent;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "http-equiv"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "http-equiv"}
	 */
	public Attribute getAttributeHTTPEquiv() {
		return this.attributeHTTPEquiv;
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
	 * Returns a {@code String} representation of this {@code Meta} instance.
	 * 
	 * @return a {@code String} representation of this {@code Meta} instance
	 */
	@Override
	public String toString() {
		return "new Meta()";
	}
	
	/**
	 * Compares {@code object} to this {@code Meta} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Meta}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Meta} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Meta}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Meta)) {
			return false;
		} else if(!Objects.equals(getAttributes(), Meta.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), Meta.class.cast(object).getDisplay())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Meta} instance.
	 * 
	 * @return a hash code for this {@code Meta} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay());
	}
}