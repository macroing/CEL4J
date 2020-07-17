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

public final class TextArea extends ContentElement<Element, Text> {
	/**
	 * The initial {@link Display} associated with a {@code TextArea} instance.
	 */
	public static final Display DISPLAY_INITIAL = Display.INLINE;
	
	/**
	 * The name associated with a {@code TextArea} instance.
	 */
	public static final String NAME = "textarea";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Attribute attributeAutoFocus;
	private final Attribute attributeCols;
	private final Attribute attributeDirName;
	private final Attribute attributeDisabled;
	private final Attribute attributeForm;
	private final Attribute attributeMaxLength;
	private final Attribute attributeName;
	private final Attribute attributePlaceholder;
	private final Attribute attributeReadOnly;
	private final Attribute attributeRequired;
	private final Attribute attributeRows;
	private final Attribute attributeWrap;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code TextArea} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new TextArea(new Text());
	 * }
	 * </pre>
	 */
	public TextArea() {
		this(new Text());
	}
	
	/**
	 * Constructs a new {@code TextArea} instance.
	 * <p>
	 * If {@code text} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param text the {@link Text} associated with this {@code TextArea} instance
	 * @throws NullPointerException thrown if, and only if, {@code text} is {@code null}
	 */
	public TextArea(final Text text) {
		super(NAME, DISPLAY_INITIAL, text);
		
		this.attributeAutoFocus = new Attribute("autofocus");
		this.attributeCols = new Attribute("cols");
		this.attributeDirName = new Attribute("dirname");
		this.attributeDisabled = new Attribute("disabled");
		this.attributeForm = new Attribute("form");
		this.attributeMaxLength = new Attribute("maxlength");
		this.attributeName = new Attribute("name");
		this.attributePlaceholder = new Attribute("placeholder");
		this.attributeReadOnly = new Attribute("readonly");
		this.attributeRequired = new Attribute("required");
		this.attributeRows = new Attribute("rows");
		this.attributeWrap = new Attribute("wrap");
		
		addAttribute(this.attributeAutoFocus);
		addAttribute(this.attributeCols);
		addAttribute(this.attributeDirName);
		addAttribute(this.attributeDisabled);
		addAttribute(this.attributeForm);
		addAttribute(this.attributeMaxLength);
		addAttribute(this.attributeName);
		addAttribute(this.attributePlaceholder);
		addAttribute(this.attributeReadOnly);
		addAttribute(this.attributeRequired);
		addAttribute(this.attributeRows);
		addAttribute(this.attributeWrap);
	}
	
	/**
	 * Constructs a new {@code TextArea} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new TextArea(new Text(string));
	 * }
	 * </pre>
	 * 
	 * @param string the {@code String} associated with a {@link Text} instance
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public TextArea(final String string) {
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
	 * Returns the {@link Attribute} instance with the name {@code "cols"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "cols"}
	 */
	public Attribute getAttributeCols() {
		return this.attributeCols;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "dirname"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "dirname"}
	 */
	public Attribute getAttributeDirName() {
		return this.attributeDirName;
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
	 * Returns the {@link Attribute} instance with the name {@code "maxlength"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "maxlength"}
	 */
	public Attribute getAttributeMaxLength() {
		return this.attributeMaxLength;
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
	 * Returns the {@link Attribute} instance with the name {@code "placeholder"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "placeholder"}
	 */
	public Attribute getAttributePlaceholder() {
		return this.attributePlaceholder;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "readonly"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "readonly"}
	 */
	public Attribute getAttributeReadOnly() {
		return this.attributeReadOnly;
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
	 * Returns the {@link Attribute} instance with the name {@code "rows"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "rows"}
	 */
	public Attribute getAttributeRows() {
		return this.attributeRows;
	}
	
	/**
	 * Returns the {@link Attribute} instance with the name {@code "wrap"}.
	 * 
	 * @return the {@code Attribute} instance with the name {@code "wrap"}
	 */
	public Attribute getAttributeWrap() {
		return this.attributeWrap;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TextArea} instance.
	 * 
	 * @return a {@code String} representation of this {@code TextArea} instance
	 */
	@Override
	public String toString() {
		return "new TextArea()";
	}
	
	/**
	 * Compares {@code object} to this {@code TextArea} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code TextArea}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code TextArea} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code TextArea}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof TextArea)) {
			return false;
		} else if(!Objects.equals(getAttributes(), TextArea.class.cast(object).getAttributes())) {
			return false;
		} else if(!Objects.equals(getDisplay(), TextArea.class.cast(object).getDisplay())) {
			return false;
		} else if(!Objects.equals(getContent(), TextArea.class.cast(object).getContent())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code TextArea} instance.
	 * 
	 * @return a hash code for this {@code TextArea} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAttributes(), getDisplay(), getContent());
	}
}