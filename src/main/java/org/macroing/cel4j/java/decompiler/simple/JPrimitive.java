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
package org.macroing.cel4j.java.decompiler.simple;

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
			case "boolean":
			case "Z":
				return BOOLEAN;
			case "B":
			case "byte":
				return BYTE;
			case "C":
			case "char":
				return CHAR;
			case "D":
			case "double":
				return DOUBLE;
			case "F":
			case "float":
				return FLOAT;
			case "I":
			case "int":
				return INT;
			case "J":
			case "long":
				return LONG;
			case "S":
			case "short":
				return SHORT;
			default:
				throw new JTypeException(String.format("A JPrimitive must refer to a primitive type: %s", name));
		}
	}
}