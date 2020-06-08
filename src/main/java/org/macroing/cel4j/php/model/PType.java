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
package org.macroing.cel4j.php.model;

import java.util.Objects;

/**
 * A {@code PType} represents a type.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PType implements Comparable<PType> {
	/**
	 * A {@code PType} used to represent an {@code array}.
	 */
	public static final PType ARRAY = new PType("array");
	
	/**
	 * A {@code PType} used to represent a {@code bool}.
	 */
	public static final PType BOOL = new PType("bool");
	
	/**
	 * A {@code PType} used to represent a {@code callable}.
	 */
	public static final PType CALLABLE = new PType("callable");
	
	/**
	 * A {@code PType} used to represent a {@code float}.
	 */
	public static final PType FLOAT = new PType("float");
	
	/**
	 * A {@code PType} used to represent an {@code int}.
	 */
	public static final PType INT = new PType("int");
	
	/**
	 * A {@code PType} used to represent an {@code iterable}.
	 */
	public static final PType ITERABLE = new PType("iterable");
	
	/**
	 * A {@code PType} used to represent an {@code object}.
	 */
	public static final PType OBJECT = new PType("object");
	
	/**
	 * A {@code PType} used to represent {@code self}.
	 */
	public static final PType SELF = new PType("self");
	
	/**
	 * A {@code PType} used to represent a {@code string}.
	 */
	public static final PType STRING = new PType("string");
	
	/**
	 * A {@code PType} used to represent {@code void}.
	 */
	public static final PType VOID = new PType("void");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String name;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private PType(final String name) {
		this.name = name;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the name of this {@code PType} instance.
	 * 
	 * @return the name of this {@code PType} instance
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Compares {@code object} to this {@code PType} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code PType}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code PType} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code PType}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof PType)) {
			return false;
		} else if(!Objects.equals(this.name, PType.class.cast(object).name)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Compares this {@code PType} instance to {@code type}.
	 * <p>
	 * Returns a comparison value.
	 * <p>
	 * If {@code type} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param type the {@code PType} to compare this {@code PType} instance to
	 * @return a comparison value
	 * @throws NullPointerException thrown if, and only if, {@code type} is {@code null}
	 */
	@Override
	public int compareTo(final PType type) {
		final PType typeThis = this;
		final PType typeThat = type;
		
		final String nameThis = typeThis.name;
		final String nameThat = typeThat.name;
		
		return nameThis.compareTo(nameThat);
	}
	
	/**
	 * Returns a hash code for this {@code PType} instance.
	 * 
	 * @return a hash code for this {@code PType} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code PType} instance based on a name.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the name of the {@code PType}
	 * @return a {@code PType} instance based on a name
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public static PType valueOf(final String name) {
		switch(name) {
			case "array":
				return ARRAY;
			case "bool":
				return BOOL;
			case "callable":
				return CALLABLE;
			case "float":
				return FLOAT;
			case "int":
				return INT;
			case "iterable":
				return ITERABLE;
			case "object":
				return OBJECT;
			case "self":
				return SELF;
			case "string":
				return STRING;
			case "void":
				return VOID;
			default:
				return new PType(name);
		}
	}
}