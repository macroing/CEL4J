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
 * A {@code Link} represents a {@code link} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Link extends EmptyElement {
	/**
	 * The initial {@link Display} associated with a {@code Link} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.NONE;
	
	/**
	 * The name associated with a {@code Link} instance.
	 */
	public static final String NAME = "link";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeCrossOrigin;
	private final Attribute attributeHRef;
	private final Attribute attributeHRefLang;
	private final Attribute attributeMedia;
	private final Attribute attributeRel;
	private final Attribute attributeSizes;
	private final Attribute attributeType;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Link} instance.
	 */
	public Link() {
		super(NAME, DISPLAY_INITIAL);
		
		this.attributeCrossOrigin = new Attribute("crossorigin");
		this.attributeHRef = new Attribute("href");
		this.attributeHRefLang = new Attribute("hreflang");
		this.attributeMedia = new Attribute("media");
		this.attributeRel = new Attribute("rel");
		this.attributeSizes = new Attribute("sizes");
		this.attributeType = new Attribute("type");
		
		addAttribute(this.attributeCrossOrigin);
		addAttribute(this.attributeHRef);
		addAttribute(this.attributeHRefLang);
		addAttribute(this.attributeMedia);
		addAttribute(this.attributeRel);
		addAttribute(this.attributeSizes);
		addAttribute(this.attributeType);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "crossorigin"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "crossorigin"}
	 */
	public Attribute getAttributeCrossOrigin() {
		return this.attributeCrossOrigin;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "href"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "href"}
	 */
	public Attribute getAttributeHRef() {
		return this.attributeHRef;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "hreflang"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "hreflang"}
	 */
	public Attribute getAttributeHRefLang() {
		return this.attributeHRefLang;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "media"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "media"}
	 */
	public Attribute getAttributeMedia() {
		return this.attributeMedia;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "rel"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "rel"}
	 */
	public Attribute getAttributeRel() {
		return this.attributeRel;
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
	 * Returns the {@link Attribute} instance with the name {@code "type"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "type"}
	 */
	public Attribute getAttributeType() {
		return this.attributeType;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Link} instance.
	 * 
	 * @return a {@code String} representation of this {@code Link} instance
	 */
	@Override
	public String toString() {
		return "new Link()";
	}
	
	/**
	 * Compares {@code object} to this {@code Link} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Link}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Link} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Link}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Link)) {
			return false;
		} else if(!Objects.equals(getAttributes(), Link.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), Link.class.cast(object).getDisplay())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Link} instance.
	 * 
	 * @return a hash code for this {@code Link} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay());
	}
}