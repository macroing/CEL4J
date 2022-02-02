/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
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
 * A {@code Button} represents a {@code button} element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Button extends ContentElement<Element, Content<Element>> {
	/**
	 * The initial {@link Display} associated with a {@code Button} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.INLINE;
	
	/**
	 * The name associated with a {@code Button} instance.
	 */
	public static final String NAME = "button";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeAutoFocus;
	private final Attribute attributeDisabled;
	private final Attribute attributeForm;
	private final Attribute attributeFormAction;
	private final Attribute attributeFormEncType;
	private final Attribute attributeFormMethod;
	private final Attribute attributeFormNoValidate;
	private final Attribute attributeFormTarget;
	private final Attribute attributeName;
	private final Attribute attributeOnClick;
	private final Attribute attributeType;
	private final Attribute attributeValue;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Button} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Button(new Text());
	 * }
	 * </pre>
	 */
	public Button() {
		this(new Text());
	}
	
	/**
	 * Constructs a new {@code Button} instance.
	 * <p>
	 * If {@code content} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param content the {@link Content} associated with this {@code Button} instance
	 * @throws NullPointerException thrown if, and only if, {@code content} is {@code null}
	 */
	public Button(final Content<Element> content) {
		super(NAME, DISPLAY_INITIAL, content);
		
		this.attributeAutoFocus = new Attribute("autofocus");
		this.attributeDisabled = new Attribute("disabled");
		this.attributeForm = new Attribute("form");
		this.attributeFormAction = new Attribute("formaction");
		this.attributeFormEncType = new Attribute("formenctype");
		this.attributeFormMethod = new Attribute("formmethod");
		this.attributeFormNoValidate = new Attribute("formnovalidate");
		this.attributeFormTarget = new Attribute("formtarget");
		this.attributeName = new Attribute("name");
		this.attributeOnClick = new Attribute("onclick");
		this.attributeType = new Attribute("type");
		this.attributeValue = new Attribute("value");
		
		addAttribute(this.attributeAutoFocus);
		addAttribute(this.attributeDisabled);
		addAttribute(this.attributeForm);
		addAttribute(this.attributeFormAction);
		addAttribute(this.attributeFormEncType);
		addAttribute(this.attributeFormMethod);
		addAttribute(this.attributeFormNoValidate);
		addAttribute(this.attributeFormTarget);
		addAttribute(this.attributeName);
		addAttribute(this.attributeOnClick);
		addAttribute(this.attributeType);
		addAttribute(this.attributeValue);
	}
	
	/**
	 * Constructs a new {@code Button} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Button(new Text(string));
	 * }
	 * </pre>
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public Button(final String string) {
		this(new Text(string));
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
	 * Returns the {@link Attribute} instance with the name {@code "formaction"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "formaction"}
	 */
	public Attribute getAttributeFormAction() {
		return this.attributeFormAction;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "formenctype"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "formenctype"}
	 */
	public Attribute getAttributeFormEncType() {
		return this.attributeFormEncType;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "formmethod"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "formmethod"}
	 */
	public Attribute getAttributeMethod() {
		return this.attributeFormMethod;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "formnovalidate"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "formnovalidate"}
	 */
	public Attribute getAttributeFormNoValidate() {
		return this.attributeFormNoValidate;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "formtarget"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "formtarget"}
	 */
	public Attribute getAttributeFormTarget() {
		return this.attributeFormTarget;
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
	 * Returns the {@link Attribute} instance with the name {@code "onclick"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "onclick"}
	 */
	public Attribute getAttributeOnClick() {
		return this.attributeOnClick;
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
	 * Returns the {@link Attribute} instance with the name {@code "value"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "value"}
	 */
	public Attribute getAttributeValue() {
		return this.attributeValue;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Button} instance.
	 * 
	 * @return a {@code String} representation of this {@code Button} instance
	 */
	@Override
	public String toString() {
		return "new Button()";
	}
	
	/**
	 * Compares {@code object} to this {@code Button} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Button}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Button} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Button}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Button)) {
			return false;
		} else if(!Objects.equals(getAttributes(), Button.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), Button.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getContent(), Button.class.cast(object).getContent())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Button} instance.
	 * 
	 * @return a hash code for this {@code Button} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getContent());
	}
}