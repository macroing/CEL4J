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
 * A {@code TH} represents a {@code th} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class TH extends ContentElement<Element, Content<Element>> {
	/**
	 * The initial {@link Display} associated with a {@code TH} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.BLOCK;
	
	/**
	 * The name associated with a {@code TH} instance.
	 */
	public static final String NAME = "th";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeAbbr;
	private final Attribute attributeColSpan;
	private final Attribute attributeHeaders;
	private final Attribute attributeRowSpan;
	private final Attribute attributeScope;
	private final Attribute attributeSorted;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code TH} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new TH(new Text());
	 * }
	 * </pre>
	 */
	public TH() {
		this(new Text());
	}
	
	/**
	 * Constructs a new {@code TH} instance.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content the {@link Content} associated with this {@code TH} instance
	 * @throws NullPointerException thrown if, and only if, {@code content} is {@code null}
	 */
	public TH(final Content<Element> content) {
		super(NAME, DISPLAY_INITIAL, content);
		
		this.attributeAbbr = new Attribute("abbr");
		this.attributeColSpan = new Attribute("colspan");
		this.attributeHeaders = new Attribute("headers");
		this.attributeRowSpan = new Attribute("rowspan");
		this.attributeScope = new Attribute("scope");
		this.attributeSorted = new Attribute("sorted");
		
		addAttribute(this.attributeAbbr);
		addAttribute(this.attributeColSpan);
		addAttribute(this.attributeHeaders);
		addAttribute(this.attributeRowSpan);
		addAttribute(this.attributeScope);
		addAttribute(this.attributeSorted);
	}
	
	/**
	 * Constructs a new {@code TH} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new TH(new Text(string));
	 * }
	 * </pre>
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public TH(final String string) {
		this(new Text(string));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "abbr"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "abbr"}
	 */
	public Attribute getAttributeAbbr() {
		return this.attributeAbbr;
	}
	
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
	 * Returns the {@link Attribute} instance with the name {@code "scope"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "scope"}
	 */
	public Attribute getAttributeScope() {
		return this.attributeScope;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "sorted"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "sorted"}
	 */
	public Attribute getAttributeSorted() {
		return this.attributeSorted;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TH} instance.
	 * 
	 * @return a {@code String} representation of this {@code TH} instance
	 */
	@Override
	public String toString() {
		return "new TH()";
	}
	
	/**
	 * Compares {@code object} to this {@code TH} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code TH}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code TH} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code TH}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof TH)) {
			return false;
		} else if(!Objects.equals(getAttributes(), TH.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), TH.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getContent(), TH.class.cast(object).getContent())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code TH} instance.
	 * 
	 * @return a hash code for this {@code TH} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getContent());
	}
}