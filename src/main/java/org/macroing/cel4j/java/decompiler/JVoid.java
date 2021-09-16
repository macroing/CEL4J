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

final class JVoid extends JType {
	public static final JVoid VOID = new JVoid(Void.TYPE);
	public static final String VOID_EXTERNAL_NAME = "void";
	public static final String VOID_INTERNAL_NAME = "V";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Class<?> clazz;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private JVoid(final Class<?> clazz) {
		this.clazz = clazz;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String getName() {
		return this.clazz.getName();
	}
	
	@Override
	public String toString() {
		return String.format("JVoid: [Name=%s]", getName());
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JVoid)) {
			return false;
		} else if(!Objects.equals(this.clazz, JVoid.class.cast(object).clazz)) {
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
	
	public static JVoid valueOf(final Class<?> clazz) {
		if(clazz == Void.TYPE) {
			return VOID;
		}
		
		throw new JTypeException(String.format("A JVoid must refer to the void type: %s", clazz));
	}
	
	public static JVoid valueOf(final String name) {
		switch(name) {
			case VOID_EXTERNAL_NAME:
			case VOID_INTERNAL_NAME:
				return VOID;
			default:
				throw new JTypeException(String.format("A JVoid must refer to the void type: %s", name));
		}
	}
}