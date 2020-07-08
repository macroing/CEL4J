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
 * An {@code A} represents an {@code a} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class A extends ContentElement<Element, Content<Element>> {
	/**
	 * The initial {@link Display} associated with an {@code A} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.INLINE;
	
	/**
	 * The name associated with an {@code A} instance.
	 */
	public static final String NAME = "a";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeDownload;
	private final Attribute attributeHRef;
	private final Attribute attributeHRefLang;
	private final Attribute attributeMedia;
	private final Attribute attributePing;
	private final Attribute attributeReferrerPolicy;
	private final Attribute attributeRel;
	private final Attribute attributeTarget;
	private final Attribute attributeType;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code A} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new A(new Text());
	 * }
	 * </pre>
	 */
	public A() {
		this(new Text());
	}
	
	/**
	 * Constructs a new {@code A} instance.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content the {@link Content} associated with this {@code A} instance
	 * @throws NullPointerException thrown if, and only if, {@code content} is {@code null}
	 */
	public A(final Content<Element> content) {
		super(NAME, DISPLAY_INITIAL, content);
		
		this.attributeDownload = new Attribute("download");
		this.attributeHRef = new Attribute("href");
		this.attributeHRefLang = new Attribute("hreflang");
		this.attributeMedia = new Attribute("media");
		this.attributePing = new Attribute("ping");
		this.attributeReferrerPolicy = new Attribute("referrerpolicy");
		this.attributeRel = new Attribute("rel");
		this.attributeTarget = new Attribute("target");
		this.attributeType = new Attribute("type");
		
		addAttribute(this.attributeDownload);
		addAttribute(this.attributeHRef);
		addAttribute(this.attributeHRefLang);
		addAttribute(this.attributeMedia);
		addAttribute(this.attributePing);
		addAttribute(this.attributeReferrerPolicy);
		addAttribute(this.attributeRel);
		addAttribute(this.attributeTarget);
		addAttribute(this.attributeType);
	}
	
	/**
	 * Constructs a new {@code A} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new A(new Text(string));
	 * }
	 * </pre>
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public A(final String string) {
		this(new Text(string));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "download"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "download"}
	 */
	public Attribute getAttributeDownload() {
		return this.attributeDownload;
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
	 * Returns the {@link Attribute} instance with the name {@code "ping"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "ping"}
	 */
	public Attribute getAttributePing() {
		return this.attributePing;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "referrerpolicy"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "referrerpolicy"}
	 */
	public Attribute getAttributeReferrerPolicy() {
		return this.attributeReferrerPolicy;
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
	 * Returns the {@link Attribute} instance with the name {@code "target"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "target"}
	 */
	public Attribute getAttributeTarget() {
		return this.attributeTarget;
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
	 * Returns a {@code String} representation of this {@code A} instance.
	 * 
	 * @return a {@code String} representation of this {@code A} instance
	 */
	@Override
	public String toString() {
		return "new A()";
	}
	
	/**
	 * Compares {@code object} to this {@code A} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code A}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code A} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code A}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof A)) {
			return false;
		} else if(!Objects.equals(getAttributes(), A.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), A.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getContent(), A.class.cast(object).getContent())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code A} instance.
	 * 
	 * @return a hash code for this {@code A} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getContent());
	}
}