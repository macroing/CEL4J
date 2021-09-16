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

final class JPrimitive extends JType {
	public static final JPrimitive BOOLEAN = new JPrimitive(Boolean.TYPE);
	public static final JPrimitive BYTE = new JPrimitive(Byte.TYPE);
	public static final JPrimitive CHAR = new JPrimitive(Character.TYPE);
	public static final JPrimitive DOUBLE = new JPrimitive(Double.TYPE);
	public static final JPrimitive FLOAT = new JPrimitive(Float.TYPE);
	public static final JPrimitive INT = new JPrimitive(Integer.TYPE);
	public static final JPrimitive LONG = new JPrimitive(Long.TYPE);
	public static final JPrimitive SHORT = new JPrimitive(Short.TYPE);
	public static final String BOOLEAN_EXTERNAL_NAME = "boolean";
	public static final String BOOLEAN_INTERNAL_NAME = "Z";
	public static final String BYTE_EXTERNAL_NAME = "byte";
	public static final String BYTE_INTERNAL_NAME = "B";
	public static final String CHAR_EXTERNAL_NAME = "char";
	public static final String CHAR_INTERNAL_NAME = "C";
	public static final String DOUBLE_EXTERNAL_NAME = "double";
	public static final String DOUBLE_INTERNAL_NAME = "D";
	public static final String FLOAT_EXTERNAL_NAME = "float";
	public static final String FLOAT_INTERNAL_NAME = "F";
	public static final String INT_EXTERNAL_NAME = "int";
	public static final String INT_INTERNAL_NAME = "I";
	public static final String LONG_EXTERNAL_NAME = "long";
	public static final String LONG_INTERNAL_NAME = "J";
	public static final String SHORT_EXTERNAL_NAME = "short";
	public static final String SHORT_INTERNAL_NAME = "S";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Class<?> clazz;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private JPrimitive(final Class<?> clazz) {
		this.clazz = clazz;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String getName() {
		return this.clazz.getName();
	}
	
	@Override
	public String toString() {
		return String.format("JPrimitive: [Name=%s]", getName());
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JPrimitive)) {
			return false;
		} else if(!Objects.equals(this.clazz, JPrimitive.class.cast(object).clazz)) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean isInnerType() {
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.clazz);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static JPrimitive valueOf(final Class<?> clazz) {
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
			throw new JTypeException(String.format("A JPrimitive must refer to a primitive type: %s", clazz));
		}
	}
	
	public static JPrimitive valueOf(final String name) {
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
				throw new JTypeException(String.format("A JPrimitive must refer to a primitive type: %s", name));
		}
	}
}