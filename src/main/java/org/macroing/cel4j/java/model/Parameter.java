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
package org.macroing.cel4j.java.model;

import java.util.Objects;
import java.util.Optional;

import org.macroing.cel4j.java.binary.classfile.signature.JavaTypeSignature;

/**
 * A {@code Parameter} represents a constructor or method parameter.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Parameter implements Comparable<Parameter> {
	private final Type type;
	private final JavaTypeSignature javaTypeSignature;
	private final String name;
	private final boolean isFinal;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Parameter} instance.
	 * <p>
	 * If {@code type} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Parameter(type, "");
	 * }
	 * </pre>
	 * 
	 * @param type a {@link Type} instance that represents the type of this {@code Parameter} instance
	 * @throws NullPointerException thrown if, and only if, {@code type} is {@code null}
	 */
	public Parameter(final Type type) {
		this(type, "");
	}
	
	/**
	 * Constructs a new {@code Parameter} instance.
	 * <p>
	 * If either {@code type} or {@code name} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Parameter(type, name, false);
	 * }
	 * </pre>
	 * 
	 * @param type a {@link Type} instance that represents the type of this {@code Parameter} instance
	 * @param name a {@code String} instance that represents the name of this {@code Parameter} instance
	 * @throws NullPointerException thrown if, and only if, either {@code type} or {@code name} are {@code null}
	 */
	public Parameter(final Type type, final String name) {
		this(type, name, false);
	}
	
	/**
	 * Constructs a new {@code Parameter} instance.
	 * <p>
	 * If either {@code type} or {@code name} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param type a {@link Type} instance that represents the type of this {@code Parameter} instance
	 * @param name a {@code String} instance that represents the name of this {@code Parameter} instance
	 * @param isFinal {@code true} if, and only if, this {@code Parameter} instance should be final, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code type} or {@code name} are {@code null}
	 */
	public Parameter(final Type type, final String name, final boolean isFinal) {
		this.type = Objects.requireNonNull(type, "type == null");
		this.name = Objects.requireNonNull(name, "name == null");
		this.isFinal = isFinal;
		this.javaTypeSignature = null;
	}
	
	/**
	 * Constructs a new {@code Parameter} instance.
	 * <p>
	 * If either {@code type}, {@code name} or {@code javaTypeSignature} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param type a {@link Type} instance that represents the type of this {@code Parameter} instance
	 * @param name a {@code String} instance that represents the name of this {@code Parameter} instance
	 * @param isFinal {@code true} if, and only if, this {@code Parameter} instance should be final, {@code false} otherwise
	 * @param javaTypeSignature a {@link JavaTypeSignature} instance that represents the Java type signature of this {@code Parameter} instance
	 * @throws NullPointerException thrown if, and only if, either {@code type}, {@code name} or {@code javaTypeSignature} are {@code null}
	 */
	public Parameter(final Type type, final String name, final boolean isFinal, final JavaTypeSignature javaTypeSignature) {
		this.type = Objects.requireNonNull(type, "type == null");
		this.name = Objects.requireNonNull(name, "name == null");
		this.isFinal = isFinal;
		this.javaTypeSignature = Objects.requireNonNull(javaTypeSignature, "javaTypeSignature == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the optional {@link JavaTypeSignature} instance associated with this {@code Parameter} instance.
	 * 
	 * @return the optional {@code JavaTypeSignature} instance associated with this {@code Parameter} instance
	 */
	public Optional<JavaTypeSignature> getOptionalJavaTypeSignature() {
		return Optional.ofNullable(this.javaTypeSignature);
	}
	
	/**
	 * Returns a {@code String} instance that represents the name of this {@code Parameter} instance.
	 * 
	 * @return a {@code String} instance that represents the name of this {@code Parameter} instance
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Parameter} instance.
	 * 
	 * @return a {@code String} representation of this {@code Parameter} instance
	 */
	@Override
	public String toString() {
		return String.format("new Parameter(%s, \"%s\", %s)", getType(), getName(), Boolean.valueOf(isFinal()));
	}
	
	/**
	 * Returns a {@link Type} instance that represents the type of this {@code Parameter} instance.
	 * 
	 * @return a {@code Type} instance that represents the type of this {@code Parameter} instance
	 */
	public Type getType() {
		return this.type;
	}
	
	/**
	 * Compares {@code object} to this {@code Parameter} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Parameter}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Parameter} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Parameter}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Parameter)) {
			return false;
		} else if(!Objects.equals(this.type, Parameter.class.cast(object).type)) {
			return false;
		} else if(!Objects.equals(this.javaTypeSignature, Parameter.class.cast(object).javaTypeSignature)) {
			return false;
		} else if(!Objects.equals(this.name, Parameter.class.cast(object).name)) {
			return false;
		} else if(this.isFinal != Parameter.class.cast(object).isFinal) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Parameter} instance is final, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Parameter} instance is final, {@code false} otherwise
	 */
	public boolean isFinal() {
		return this.isFinal;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Parameter} instance is named, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Parameter} instance is named, {@code false} otherwise
	 */
	public boolean isNamed() {
		return !this.name.isEmpty();
	}
	
	/**
	 * Compares this {@code Parameter} instance with {@code parameter} for order.
	 * <p>
	 * Returns a negative integer, zero or a positive integer as this {@code Parameter} instance is less than, equal to or greater than {@code parameter}.
	 * <p>
	 * If {@code parameter} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param parameter a {@code Parameter} instance
	 * @return a negative integer, zero or a positive integer as this {@code Parameter} instance is less than, equal to or greater than {@code parameter}
	 * @throws NullPointerException thrown if, and only if, {@code parameter} is {@code null}
	 */
	@Override
	public int compareTo(final Parameter parameter) {
		return getType().getExternalSimpleName().compareTo(parameter.getType().getExternalSimpleName());
	}
	
	/**
	 * Returns a hash code for this {@code Parameter} instance.
	 * 
	 * @return a hash code for this {@code Parameter} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.type, this.javaTypeSignature, this.name, Boolean.valueOf(this.isFinal));
	}
}