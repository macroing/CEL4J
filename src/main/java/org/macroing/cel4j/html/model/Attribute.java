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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code Attribute} represents an attribute of an element in HTML source code.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Attribute implements Node {
	private final String name;
	private String value;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Attribute} instance.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Attribute(name, "");
	 * }
	 * </pre>
	 * 
	 * @param name the case-insensitive name associated with this {@code Attribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public Attribute(final String name) {
		this(name, "");
	}
	
	/**
	 * Constructs a new {@code Attribute} instance.
	 * <p>
	 * If either {@code name} or {@code value} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the case-insensitive name associated with this {@code Attribute} instance
	 * @param value the value associated with this {@code Attribute} instance
	 * @throws NullPointerException thrown if, and only if, either {@code name} or {@code value} are {@code null}
	 */
	public Attribute(final String name, final String value) {
		this.name = name.toLowerCase();
		this.value = Objects.requireNonNull(value, "value == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the name associated with this {@code Attribute} instance.
	 * <p>
	 * This method will return the name in lower case.
	 * 
	 * @return the name associated with this {@code Attribute} instance
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the name and value associated with this {@code Attribute} instance.
	 * 
	 * @return the name and value associated with this {@code Attribute} instance
	 */
	public String getNameAndValue() {
		return String.format("%s=\"%s\"", getName(), getValue());
	}
	
	/**
	 * Returns the value associated with this {@code Attribute} instance.
	 * 
	 * @return the value associated with this {@code Attribute} instance
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Attribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code Attribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new Attribute(\"%s\", \"%s\")", getName(), getValue());
	}
	
	/**
	 * Compares {@code object} to this {@code Attribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Attribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Attribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Attribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Attribute)) {
			return false;
		} else if(!Objects.equals(getName(), Attribute.class.cast(object).getName())) {
			return false;
		} else if(!Objects.equals(getValue(), Attribute.class.cast(object).getValue())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, the value associated with this {@code Attribute} instance is set, {@code false} ortherwise.
	 * <p>
	 * The value is set if, and only if, it is not an empty ({@code ""}) {@code String}.
	 * 
	 * @return {@code true} if, and only if, the value associated with this {@code Attribute} instance is set, {@code false} ortherwise
	 */
	public boolean isSet() {
		return !this.value.isEmpty();
	}
	
	/**
	 * Returns a hash code for this {@code Attribute} instance.
	 * 
	 * @return a hash code for this {@code Attribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), getValue());
	}
	
	/**
	 * Adds {@code value} to the value associated with this {@code Attribute} instance.
	 * <p>
	 * If {@code value} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param value the value to add to the value associated with this {@code Attribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code value} is {@code null}
	 */
	public void addValue(final String value) {
		Objects.requireNonNull(value, "value == null");
		
		if(isSet()) {
			this.value += " ";
		}
		
		this.value += value;
	}
	
	/**
	 * Sets the value associated with this {@code Attribute} instance to an empty ({@code ""}) {@code String}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * attribute.setValue("");
	 * }
	 * </pre>
	 */
	public void setValue() {
		setValue("");
	}
	
	/**
	 * Sets the value associated with this {@code Attribute} instance to {@code value}.
	 * <p>
	 * If {@code value} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param value the value associated with this {@code Attribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code value} is {@code null}
	 */
	public void setValue(final String value) {
		this.value = Objects.requireNonNull(value, "value == null");
	}
	
	/**
	 * Sets the value associated with this {@code Attribute} instance to a joined version of {@code values}.
	 * <p>
	 * If either {@code values} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The delimiter used in the joining process is a space ({@code " "}).
	 * 
	 * @param values the values to join
	 * @throws NullPointerException thrown if, and only if, either {@code values} or any of its elements are {@code null}
	 */
	public void setValue(final String[] values) {
		this.value = Arrays.stream(ParameterArguments.requireNonNullArray(values, "values")).collect(Collectors.joining(" "));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of {@code attributes}.
	 * <p>
	 * If either {@code attributes} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Attribute.getAttributesAsString(attributes, false);
	 * }
	 * </pre>
	 * 
	 * @param attributes a {@code List} of {@code Attribute} instances
	 * @return a {@code String} representation of {@code attributes}
	 * @throws NullPointerException thrown if, and only if, either {@code attributes} or any of its elements are {@code null}
	 */
	public static String getAttributesAsString(final List<Attribute> attributes) {
		return getAttributesAsString(attributes, false);
	}
	
	/**
	 * Returns a {@code String} representation of {@code attributes}.
	 * <p>
	 * If either {@code attributes} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attributes a {@code List} of {@code Attribute} instances
	 * @param isFilteringOnIsSet {@code true} if, and only if, only {@code Attribute} instances for which {@code #isSet()} returns {@code true} should be accepted, {@code false} otherwise
	 * @return a {@code String} representation of {@code attributes}
	 * @throws NullPointerException thrown if, and only if, either {@code attributes} or any of its elements are {@code null}
	 */
	public static String getAttributesAsString(final List<Attribute> attributes, final boolean isFilteringOnIsSet) {
		return ParameterArguments.requireNonNullList(attributes, "attributes").stream().filter(attribute -> isFilteringOnIsSet && attribute.isSet() || !isFilteringOnIsSet).map(attribute -> attribute.getNameAndValue()).collect(Collectors.joining(" "));
	}
}