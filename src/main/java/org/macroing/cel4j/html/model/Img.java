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
 * An {@code Img} represents an {@code img} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Img extends EmptyElement {
	/**
	 * The initial {@link Display} associated with an {@code Img} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.INLINE;
	
	/**
	 * The name associated with an {@code Img} instance.
	 */
	public static final String NAME = "img";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeAlt;
	private final Attribute attributeCrossOrigin;
	private final Attribute attributeHeight;
	private final Attribute attributeIsMap;
	private final Attribute attributeLongDesc;
	private final Attribute attributeSizes;
	private final Attribute attributeSrc;
	private final Attribute attributeSrcSet;
	private final Attribute attributeUseMap;
	private final Attribute attributeWidth;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Img} instance.
	 */
	public Img() {
		super(NAME, DISPLAY_INITIAL);
		
		this.attributeAlt = new Attribute("alt");
		this.attributeCrossOrigin = new Attribute("crossorigin");
		this.attributeHeight = new Attribute("height");
		this.attributeIsMap = new Attribute("ismap");
		this.attributeLongDesc = new Attribute("longdesc");
		this.attributeSizes = new Attribute("sizes");
		this.attributeSrc = new Attribute("src");
		this.attributeSrcSet = new Attribute("srcset");
		this.attributeUseMap = new Attribute("usemap");
		this.attributeWidth = new Attribute("width");
		
		addAttribute(this.attributeAlt);
		addAttribute(this.attributeCrossOrigin);
		addAttribute(this.attributeHeight);
		addAttribute(this.attributeIsMap);
		addAttribute(this.attributeLongDesc);
		addAttribute(this.attributeSizes);
		addAttribute(this.attributeSrc);
		addAttribute(this.attributeSrcSet);
		addAttribute(this.attributeUseMap);
		addAttribute(this.attributeWidth);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "alt"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "alt"}
	 */
	public Attribute getAttributeAlt() {
		return this.attributeAlt;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "crossorigin"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "crossorigin"}
	 */
	public Attribute getAttributeCrossOrigin() {
		return this.attributeCrossOrigin;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "height"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "height"}
	 */
	public Attribute getAttributeHeight() {
		return this.attributeHeight;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "ismap"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "ismap"}
	 */
	public Attribute getAttributeIsMap() {
		return this.attributeIsMap;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "longdesc"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "longdesc"}
	 */
	public Attribute getAttributeLongDesc() {
		return this.attributeLongDesc;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "sizes"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "sizes"}
	 */
	public Attribute getAttributeSizes() {
		return this.attributeSizes;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "src"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "src"}
	 */
	public Attribute getAttributeSrc() {
		return this.attributeSrc;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "srcset"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "srcset"}
	 */
	public Attribute getAttributeSrcSet() {
		return this.attributeSrcSet;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "usemap"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "usemap"}
	 */
	public Attribute getAttributeUseMap() {
		return this.attributeUseMap;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "width"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "width"}
	 */
	public Attribute getAttributeWidth() {
		return this.attributeWidth;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Img} instance.
	 * 
	 * @return a {@code String} representation of this {@code Img} instance
	 */
	@Override
	public String toString() {
		return "new Img()";
	}
	
	/**
	 * Compares {@code object} to this {@code Img} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Img}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Img} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Img}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Img)) {
			return false;
		} else if(!Objects.equals(getAttributes(), Img.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), Img.class.cast(object).getDisplay())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Img} instance.
	 * 
	 * @return a hash code for this {@code Img} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay());
	}
}