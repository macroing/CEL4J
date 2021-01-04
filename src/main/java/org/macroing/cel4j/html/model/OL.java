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
 * An {@code OL} represents an {@code ol} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class OL extends ContentElement<LI, Elements<LI>> {
	/**
	 * The initial {@link Display} associated with an {@code OL} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.BLOCK;
	
	/**
	 * The name associated with an {@code OL} instance.
	 */
	public static final String NAME = "ol";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeReversed;
	private final Attribute attributeStart;
	private final Attribute attributeType;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code OL} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new OL(new Elements<>(OL.DISPLAY_INITIAL));
	 * }
	 * </pre>
	 */
	public OL() {
		this(new Elements<>(DISPLAY_INITIAL));
	}
	
	/**
	 * Constructs a new {@code OL} instance.
	 * <p>
	 * If {@code elements} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param elements the {@link Elements} associated with this {@code OL} instance
	 * @throws NullPointerException thrown if, and only if, {@code elements} is {@code null}
	 */
	public OL(final Elements<LI> elements) {
		super(NAME, DISPLAY_INITIAL, elements);
		
		this.attributeReversed = new Attribute("reversed");
		this.attributeStart = new Attribute("start");
		this.attributeType = new Attribute("type");
		
		addAttribute(this.attributeReversed);
		addAttribute(this.attributeStart);
		addAttribute(this.attributeType);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "reversed"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "reversed"}
	 */
	public Attribute getAttributeReversed() {
		return this.attributeReversed;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "start"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "start"}
	 */
	public Attribute getAttributeStart() {
		return this.attributeStart;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "type"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "type"}
	 */
	public Attribute getAttributeType() {
		return this.attributeType;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code OL} instance.
	 * 
	 * @return a {@code String} representation of this {@code OL} instance
	 */
	@Override
	public String toString() {
		return "new OL()";
	}
	
	/**
	 * Compares {@code object} to this {@code OL} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code OL}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code OL} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code OL}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof OL)) {
			return false;
		} else if(!Objects.equals(getAttributes(), OL.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), OL.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getContent(), OL.class.cast(object).getContent())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code OL} instance.
	 * 
	 * @return a hash code for this {@code OL} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getContent());
	}
}