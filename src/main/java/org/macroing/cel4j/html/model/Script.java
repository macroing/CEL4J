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
 * A {@code Script} represents a {@code script} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Script extends ContentElement<Element, Text> {
	/**
	 * The initial {@link Display} associated with a {@code Script} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.BLOCK;
	
	/**
	 * The name associated with a {@code Script} instance.
	 */
	public static final String NAME = "script";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeAsync;
	private final Attribute attributeCharSet;
	private final Attribute attributeCrossOrigin;
	private final Attribute attributeDefer;
	private final Attribute attributeIntegrity;
	private final Attribute attributeSrc;
	private final Attribute attributeType;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Script} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Script(new Text());
	 * }
	 * </pre>
	 */
	public Script() {
		this(new Text());
	}
	
	/**
	 * Constructs a new {@code Script} instance.
	 * <p>
	 * If {@code text} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param text the {@link Text} associated with this {@code Script} instance
	 * @throws NullPointerException thrown if, and only if, {@code text} is {@code null}
	 */
	public Script(final Text text) {
		super(NAME, DISPLAY_INITIAL, text);
		
		this.attributeAsync = new Attribute("async");
		this.attributeCharSet = new Attribute("charset");
		this.attributeCrossOrigin = new Attribute("crossorigin");
		this.attributeDefer = new Attribute("defer");
		this.attributeIntegrity = new Attribute("integrity");
		this.attributeSrc = new Attribute("src");
		this.attributeType = new Attribute("type");
		
		addAttribute(this.attributeAsync);
		addAttribute(this.attributeCharSet);
		addAttribute(this.attributeCrossOrigin);
		addAttribute(this.attributeDefer);
		addAttribute(this.attributeIntegrity);
		addAttribute(this.attributeSrc);
		addAttribute(this.attributeType);
	}
	
	/**
	 * Constructs a new {@code Script} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Script(new Text(string));
	 * }
	 * </pre>
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public Script(final String string) {
		this(new Text(string));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "async"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "async"}
	 */
	public Attribute getAttributeAsync() {
		return this.attributeAsync;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "charset"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "charset"}
	 */
	public Attribute getAttributeCharSet() {
		return this.attributeCharSet;
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
	 * Returns the {@link Attribute} instance with the name {@code "defer"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "defer"}
	 */
	public Attribute getAttributeDefer() {
		return this.attributeDefer;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "integrity"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "integrity"}
	 */
	public Attribute getAttributeIntegrity() {
		return this.attributeIntegrity;
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
	 * Returns the {@link Attribute} instance with the name {@code "type"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "type"}
	 */
	public Attribute getAttributeType() {
		return this.attributeType;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Script} instance.
	 * 
	 * @return a {@code String} representation of this {@code Script} instance
	 */
	@Override
	public String toString() {
		return "new Script()";
	}
	
	/**
	 * Compares {@code object} to this {@code Script} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Script}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Script} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Script}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Script)) {
			return false;
		} else if(!Objects.equals(getAttributes(), Script.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), Script.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getContent(), Script.class.cast(object).getContent())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Script} instance.
	 * 
	 * @return a hash code for this {@code Script} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getContent());
	}
}