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
 * A {@code VoidType} is a {@link Type} implementation that represents the {@code void} type.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class VoidType extends Type {
	/**
	 * The {@code VoidType} instance for the {@code void} type.
	 */
	public static final VoidType VOID = new VoidType(Void.TYPE);
	
	/**
	 * The external name of the {@code void} type.
	 */
	public static final String VOID_EXTERNAL_NAME = "void";
	
	/**
	 * The internal name of the {@code void} type.
	 */
	public static final String VOID_INTERNAL_NAME = "V";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Class<?> clazz;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private VoidType(final Class<?> clazz) {
		this.clazz = clazz;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the name of this {@code VoidType} instance.
	 * 
	 * @return the name of this {@code VoidType} instance
	 */
	@Override
	public String getName() {
		return this.clazz.getName();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code VoidType} instance.
	 * 
	 * @return a {@code String} representation of this {@code VoidType} instance
	 */
	@Override
	public String toString() {
		return "VoidType.VOID";
	}
	
	/**
	 * Compares {@code object} to this {@code VoidType} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code VoidType}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code VoidType} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code VoidType}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof VoidType)) {
			return false;
		} else if(!Objects.equals(this.clazz, VoidType.class.cast(object).clazz)) {
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
	 * Returns a hash code for this {@code VoidType} instance.
	 * 
	 * @return a hash code for this {@code VoidType} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.clazz);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code VoidType} instance that represents {@code clazz}.
	 * <p>
	 * If {@code clazz} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code clazz} is invalid, a {@code TypeException} will be thrown.
	 * 
	 * @param clazz a {@code Class} instance
	 * @return a {@code VoidType} instance that represents {@code clazz}
	 * @throws NullPointerException thrown if, and only if, {@code clazz} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code clazz} is invalid
	 */
	public static VoidType valueOf(final Class<?> clazz) {
		Objects.requireNonNull(clazz, "clazz == null");
		
		if(clazz == Void.TYPE) {
			return VOID;
		}
		
		throw new TypeException(String.format("A VoidType must refer to the void type: %s", clazz));
	}
	
	/**
	 * Returns a {@code VoidType} instance given {@code name} in external or internal format.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code name} is invalid, a {@code TypeException} will be thrown.
	 * 
	 * @param name the name in external or internal format
	 * @return a {@code VoidType} instance given {@code name} in external or internal format
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code name} is invalid
	 */
	public static VoidType valueOf(final String name) {
		switch(name) {
			case VOID_EXTERNAL_NAME:
			case VOID_INTERNAL_NAME:
				return VOID;
			default:
				throw new TypeException(String.format("A VoidType must refer to the void type: %s", name));
		}
	}
}