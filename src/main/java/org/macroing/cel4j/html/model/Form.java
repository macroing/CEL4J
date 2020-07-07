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
 * A {@code Form} represents a {@code form} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Form extends ContentElement<Content> {
	/**
	 * The initial {@link Display} associated with a {@code Form} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.BLOCK;
	
	/**
	 * The name associated with a {@code Form} instance.
	 */
	public static final String NAME = "form";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeAcceptCharSet;
	private final Attribute attributeAction;
	private final Attribute attributeAutoComplete;
	private final Attribute attributeEncType;
	private final Attribute attributeMethod;
	private final Attribute attributeName;
	private final Attribute attributeNoValidate;
	private final Attribute attributeTarget;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Form} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Form(new Elements<>(Form.DISPLAY_INITIAL));
	 * }
	 * </pre>
	 */
	public Form() {
		this(new Elements<>(DISPLAY_INITIAL));
	}
	
	/**
	 * Constructs a new {@code Form} instance.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content the {@link Content} associated with this {@code Form} instance
	 * @throws NullPointerException thrown if, and only if, {@code content} is {@code null}
	 */
	public Form(final Content content) {
		super(NAME, DISPLAY_INITIAL, content);
		
		this.attributeAcceptCharSet = new Attribute("accept-charset");
		this.attributeAction = new Attribute("action");
		this.attributeAutoComplete = new Attribute("autocomplete");
		this.attributeEncType = new Attribute("enctype");
		this.attributeMethod = new Attribute("method");
		this.attributeName = new Attribute("name");
		this.attributeNoValidate = new Attribute("novalidate");
		this.attributeTarget = new Attribute("target");
		
		addAttribute(this.attributeAcceptCharSet);
		addAttribute(this.attributeAction);
		addAttribute(this.attributeAutoComplete);
		addAttribute(this.attributeEncType);
		addAttribute(this.attributeMethod);
		addAttribute(this.attributeName);
		addAttribute(this.attributeNoValidate);
		addAttribute(this.attributeTarget);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "accept-charset"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "accept-charset"}
	 */
	public Attribute getAttributeAcceptCharSet() {
		return this.attributeAcceptCharSet;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "action"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "action"}
	 */
	public Attribute getAttributeAction() {
		return this.attributeAction;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "autocomplete"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "autocomplete"}
	 */
	public Attribute getAttributeAutoComplete() {
		return this.attributeAutoComplete;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "enctype"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "enctype"}
	 */
	public Attribute getAttributeEncType() {
		return this.attributeEncType;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "method"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "method"}
	 */
	public Attribute getAttributeMethod() {
		return this.attributeMethod;
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
	 * Returns the {@link Attribute} instance with the name {@code "novalidate"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "novalidate"}
	 */
	public Attribute getAttributeNoValidate() {
		return this.attributeNoValidate;
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
	 * Returns a {@code String} representation of this {@code Form} instance.
	 * 
	 * @return a {@code String} representation of this {@code Form} instance
	 */
	@Override
	public String toString() {
		return "new Form()";
	}
	
	/**
	 * Compares {@code object} to this {@code Form} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Form}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Form} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Form}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Form)) {
			return false;
		} else if(!Objects.equals(getAttributes(), Form.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), Form.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getContent(), Form.class.cast(object).getContent())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Form} instance.
	 * 
	 * @return a hash code for this {@code Form} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getContent());
	}
}