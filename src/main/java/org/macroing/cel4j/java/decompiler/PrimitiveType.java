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
package org.macroing.cel4j.java.decompiler;

import java.util.Objects;

/**
 * A {@code PrimitiveType} is a {@link Type} implementation that represents a primitive type.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class PrimitiveType extends Type {
	/**
	 * The {@code PrimitiveType} instance for the {@code boolean} type.
	 */
	public static final PrimitiveType BOOLEAN = new PrimitiveType(Boolean.TYPE);
	
	/**
	 * The {@code PrimitiveType} instance for the {@code byte} type.
	 */
	public static final PrimitiveType BYTE = new PrimitiveType(Byte.TYPE);
	
	/**
	 * The {@code PrimitiveType} instance for the {@code char} type.
	 */
	public static final PrimitiveType CHAR = new PrimitiveType(Character.TYPE);
	
	/**
	 * The {@code PrimitiveType} instance for the {@code double} type.
	 */
	public static final PrimitiveType DOUBLE = new PrimitiveType(Double.TYPE);
	
	/**
	 * The {@code PrimitiveType} instance for the {@code float} type.
	 */
	public static final PrimitiveType FLOAT = new PrimitiveType(Float.TYPE);
	
	/**
	 * The {@code PrimitiveType} instance for the {@code int} type.
	 */
	public static final PrimitiveType INT = new PrimitiveType(Integer.TYPE);
	
	/**
	 * The {@code PrimitiveType} instance for the {@code long} type.
	 */
	public static final PrimitiveType LONG = new PrimitiveType(Long.TYPE);
	
	/**
	 * The {@code PrimitiveType} instance for the {@code short} type.
	 */
	public static final PrimitiveType SHORT = new PrimitiveType(Short.TYPE);
	
	/**
	 * The external name of the {@code boolean} type.
	 */
	public static final String BOOLEAN_EXTERNAL_NAME = "boolean";
	
	/**
	 * The internal name of the {@code boolean} type.
	 */
	public static final String BOOLEAN_INTERNAL_NAME = "Z";
	
	/**
	 * The external name of the {@code byte} type.
	 */
	public static final String BYTE_EXTERNAL_NAME = "byte";
	
	/**
	 * The internal name of the {@code byte} type.
	 */
	public static final String BYTE_INTERNAL_NAME = "B";
	
	/**
	 * The external name of the {@code char} type.
	 */
	public static final String CHAR_EXTERNAL_NAME = "char";
	
	/**
	 * The internal name of the {@code char} type.
	 */
	public static final String CHAR_INTERNAL_NAME = "C";
	
	/**
	 * The external name of the {@code double} type.
	 */
	public static final String DOUBLE_EXTERNAL_NAME = "double";
	
	/**
	 * The internal name of the {@code double} type.
	 */
	public static final String DOUBLE_INTERNAL_NAME = "D";
	
	/**
	 * The external name of the {@code float} type.
	 */
	public static final String FLOAT_EXTERNAL_NAME = "float";
	
	/**
	 * The internal name of the {@code float} type.
	 */
	public static final String FLOAT_INTERNAL_NAME = "F";
	
	/**
	 * The external name of the {@code int} type.
	 */
	public static final String INT_EXTERNAL_NAME = "int";
	
	/**
	 * The internal name of the {@code int} type.
	 */
	public static final String INT_INTERNAL_NAME = "I";
	
	/**
	 * The external name of the {@code long} type.
	 */
	public static final String LONG_EXTERNAL_NAME = "long";
	
	/**
	 * The internal name of the {@code long} type.
	 */
	public static final String LONG_INTERNAL_NAME = "J";
	
	/**
	 * The external name of the {@code short} type.
	 */
	public static final String SHORT_EXTERNAL_NAME = "short";
	
	/**
	 * The internal name of the {@code short} type.
	 */
	public static final String SHORT_INTERNAL_NAME = "S";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Class<?> clazz;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private PrimitiveType(final Class<?> clazz) {
		this.clazz = clazz;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the external name of this {@code PrimitiveType} instance.
	 * 
	 * @return the external name of this {@code PrimitiveType} instance
	 */
	@Override
	public String getExternalName() {
		return this.clazz.getName();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code PrimitiveType} instance.
	 * 
	 * @return a {@code String} representation of this {@code PrimitiveType} instance
	 */
	@Override
	public String toString() {
		if(this == BOOLEAN) {
			return "PrimitiveType.BOOLEAN";
		} else if(this == BYTE) {
			return "PrimitiveType.BYTE";
		} else if(this == CHAR) {
			return "PrimitiveType.CHAR";
		} else if(this == DOUBLE) {
			return "PrimitiveType.DOUBLE";
		} else if(this == FLOAT) {
			return "PrimitiveType.FLOAT";
		} else if(this == INT) {
			return "PrimitiveType.INT";
		} else if(this == LONG) {
			return "PrimitiveType.LONG";
		} else if(this == SHORT) {
			return "PrimitiveType.SHORT";
		} else {
			return "";
		}
	}
	
	/**
	 * Compares {@code object} to this {@code PrimitiveType} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code PrimitiveType}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code PrimitiveType} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code PrimitiveType}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof PrimitiveType)) {
			return false;
		} else if(!Objects.equals(this.clazz, PrimitiveType.class.cast(object).clazz)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code false}.
	 * 
	 * @return {@code false}
	 */
	@Override
	public boolean isInnerType() {
		return false;
	}
	
	/**
	 * Returns a hash code for this {@code PrimitiveType} instance.
	 * 
	 * @return a hash code for this {@code PrimitiveType} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.clazz);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code PrimitiveType} instance that represents {@code clazz}.
	 * <p>
	 * If {@code clazz} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code clazz} is invalid, a {@code TypeException} will be thrown.
	 * 
	 * @param clazz a {@code Class} instance
	 * @return a {@code PrimitiveType} instance that represents {@code clazz}
	 * @throws NullPointerException thrown if, and only if, {@code clazz} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code clazz} is invalid
	 */
	public static PrimitiveType valueOf(final Class<?> clazz) {
		Objects.requireNonNull(clazz, "clazz == null");
		
		if(clazz == Boolean.TYPE) {
			return BOOLEAN;
		} else if(clazz == Byte.TYPE) {
			return BYTE;
		} else if(clazz == Character.TYPE) {
			return CHAR;
		} else if(clazz == Double.TYPE) {
			return DOUBLE;
		} else if(clazz == Float.TYPE) {
			return FLOAT;
		} else if(clazz == Integer.TYPE) {
			return INT;
		} else if(clazz == Long.TYPE) {
			return LONG;
		} else if(clazz == Short.TYPE) {
			return SHORT;
		} else {
			throw new TypeException(String.format("A PrimitiveType must refer to a primitive type: %s", clazz));
		}
	}
	
	/**
	 * Returns a {@code PrimitiveType} instance given {@code name} in external or internal format.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code name} is invalid, a {@code TypeException} will be thrown.
	 * 
	 * @param name the name in external or internal format
	 * @return a {@code PrimitiveType} instance given {@code name} in external or internal format
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code name} is invalid
	 */
	public static PrimitiveType valueOf(final String name) {
		switch(name) {
			case BOOLEAN_EXTERNAL_NAME:
			case BOOLEAN_INTERNAL_NAME:
				return BOOLEAN;
			case BYTE_EXTERNAL_NAME:
			case BYTE_INTERNAL_NAME:
				return BYTE;
			case CHAR_EXTERNAL_NAME:
			case CHAR_INTERNAL_NAME:
				return CHAR;
			case DOUBLE_EXTERNAL_NAME:
			case DOUBLE_INTERNAL_NAME:
				return DOUBLE;
			case FLOAT_EXTERNAL_NAME:
			case FLOAT_INTERNAL_NAME:
				return FLOAT;
			case INT_EXTERNAL_NAME:
			case INT_INTERNAL_NAME:
				return INT;
			case LONG_EXTERNAL_NAME:
			case LONG_INTERNAL_NAME:
				return LONG;
			case SHORT_EXTERNAL_NAME:
			case SHORT_INTERNAL_NAME:
				return SHORT;
			default:
				throw new TypeException(String.format("A PrimitiveType must refer to a primitive type: %s", name));
		}
	}
}