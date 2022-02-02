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
package org.macroing.cel4j.php.model;

import java.util.Objects;

/**
 * A {@code PReturnType} represents a return type.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PReturnType implements Comparable<PReturnType> {
	private final PType type;
	private final boolean isNullable;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code PReturnType} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new PReturnType(PType.VOID);
	 * }
	 * </pre>
	 */
	public PReturnType() {
		this(PType.VOID);
	}
	
	/**
	 * Constructs a new {@code PReturnType} instance.
	 * <p>
	 * If {@code type} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new PReturnType(type, false);
	 * }
	 * </pre>
	 * 
	 * @param type the {@link PType} to use
	 * @throws NullPointerException thrown if, and only if, {@code type} is {@code null}
	 */
	public PReturnType(final PType type) {
		this(type, false);
	}
	
	/**
	 * Constructs a new {@code PReturnType} instance.
	 * <p>
	 * If {@code type} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param type the {@link PType} to use
	 * @param isNullable {@code true} if, and only if, {@code type} can be {@code null} in the PHP source code, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code type} is {@code null}
	 */
	public PReturnType(final PType type, final boolean isNullable) {
		this.type = Objects.requireNonNull(type, "type == null");
		this.isNullable = isNullable;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link PType} assigned to this {@code PReturnType} instance.
	 * 
	 * @return the {@code PType} assigned to this {@code PReturnType} instance
	 */
	public PType getType() {
		return this.type;
	}
	
	/**
	 * Returns a {@code String} with PHP source code.
	 * 
	 * @return a {@code String} with PHP source code
	 */
	public String getSourceCode() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(isNullable() ? "?" : "");
		stringBuilder.append(getType().getName());
		
		return stringBuilder.toString();
	}
	
	/**
	 * Compares {@code object} to this {@code PReturnType} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code PReturnType}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code PReturnType} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code PReturnType}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof PReturnType)) {
			return false;
		} else if(!Objects.equals(this.type, PReturnType.class.cast(object).type)) {
			return false;
		} else if(this.isNullable != PReturnType.class.cast(object).isNullable) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, the {@link PType} assigned to this {@code PReturnType} can be {@code null} in the PHP source code, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, the {@code PType} assigned to this {@code PReturnType} can be {@code null} in the PHP source code, {@code false} otherwise
	 */
	public boolean isNullable() {
		return this.isNullable;
	}
	
	/**
	 * Compares this {@code PReturnType} instance to {@code returnType}.
	 * <p>
	 * Returns a comparison value.
	 * <p>
	 * If {@code returnType} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param returnType the {@code PReturnType} to compare this {@code PReturnType} instance to
	 * @return a comparison value
	 * @throws NullPointerException thrown if, and only if, {@code returnType} is {@code null}
	 */
	@Override
	public int compareTo(final PReturnType returnType) {
		final PReturnType returnTypeThis = this;
		final PReturnType returnTypeThat = returnType;
		
		final PType typeThis = returnTypeThis.type;
		final PType typeThat = returnTypeThat.type;
		
		final int compareToType = typeThis.compareTo(typeThat);
		
		if(compareToType != 0) {
			return compareToType;
		}
		
		final boolean isNullableThis = returnTypeThis.isNullable;
		final boolean isNullableThat = returnTypeThat.isNullable;
		
		return Boolean.compare(isNullableThis, isNullableThat);
	}
	
	/**
	 * Returns a hash code for this {@code PReturnType} instance.
	 * 
	 * @return a hash code for this {@code PReturnType} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.type, Boolean.valueOf(this.isNullable));
	}
}