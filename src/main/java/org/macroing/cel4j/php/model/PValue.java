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
package org.macroing.cel4j.php.model;

import java.util.Objects;

/**
 * A {@code PValue} represents a value assigned to a constant or a default value assigned to a parameter argument.
 * <p>
 * The classes {@link PConst} and {@link PParameterArgument} can contain an instance of this {@code PValue} class. For the {@code PConst} class it is required, but for the {@code PParameterArgument} class it is optional.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PValue {
	/**
	 * A {@code PValue} instance used to represent an array.
	 */
	public static final PValue ARRAY = new PValue(new Object[] {});
	
	/**
	 * A {@code PValue} instance used to represent {@code null}.
	 */
	public static final PValue NULL = new PValue(null);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Object object;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private PValue(final Object object) {
		this.object = object;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@code Object} that defines the type for this {@code PValue} instance.
	 * <p>
	 * This method may return {@code null}.
	 * 
	 * @return the {@code Object} that defines the type for this {@code PValue} instance
	 */
	public Object getObject() {
		return this.object;
	}
	
	/**
	 * Returns a {@code String} with PHP source code.
	 * 
	 * @return a {@code String} with PHP source code
	 */
	public String getSourceCode() {
		if(this.object instanceof Object[]) {
			return "array()";
		} else if(this.object instanceof String) {
			return "'" + this.object + "'";
		} else if(this.object == null) {
			return "null";
		} else {
			return this.object.toString();
		}
	}
	
	/**
	 * Compares {@code object} to this {@code PValue} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code PValue}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code PValue} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code PValue}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof PValue)) {
			return false;
		} else if(!Objects.equals(this.object, PValue.class.cast(object).object)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code PValue} instance.
	 * 
	 * @return a hash code for this {@code PValue} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.object);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code PValue} instance given a {@code boolean} value.
	 * 
	 * @param value the {@code boolean} value
	 * @return a {@code PValue} instance given a {@code boolean} value
	 */
	public static PValue valueOf(final boolean value) {
		return new PValue(Boolean.valueOf(value));
	}
	
	/**
	 * Returns a {@code PValue} instance given a {@code double} value.
	 * 
	 * @param value the {@code double} value
	 * @return a {@code PValue} instance given a {@code double} value
	 */
	public static PValue valueOf(final double value) {
		return new PValue(Double.valueOf(value));
	}
	
	/**
	 * Returns a {@code PValue} instance given a {@code float} value.
	 * 
	 * @param value the {@code float} value
	 * @return a {@code PValue} instance given a {@code float} value
	 */
	public static PValue valueOf(final float value) {
		return new PValue(Float.valueOf(value));
	}
	
	/**
	 * Returns a {@code PValue} instance given an {@code int} value.
	 * 
	 * @param value the {@code int} value
	 * @return a {@code PValue} instance given an {@code int} value
	 */
	public static PValue valueOf(final int value) {
		return new PValue(Integer.valueOf(value));
	}
	
	/**
	 * Returns a {@code PValue} instance given a {@code long} value.
	 * 
	 * @param value the {@code long} value
	 * @return a {@code PValue} instance given a {@code long} value
	 */
	public static PValue valueOf(final long value) {
		return new PValue(Long.valueOf(value));
	}
	
	/**
	 * Returns a {@code PValue} instance given a {@link PType} value.
	 * <p>
	 * If {@code pType} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code PValue} returned is determined by {@code pType.getName()}. If the name is one of {@code "array"}, {@code "bool"}, {@code "float"}, {@code "int"} or {@code "string"}, the {@code PValue} returned will be
	 * <code>PValue.valueOf(new Object[] {})</code>, {@code PValue.valueOf(false)}, {@code PValue.valueOf(0.0F)}, {@code PValue.valueOf(0)} or {@code PValue.valueOf("")}, respectively. In any other case {@code PValue.NULL} will be returned.
	 * 
	 * @param pType the {@code PType} value
	 * @return a {@code PValue} instance given a {@code PType} value
	 * @throws NullPointerException thrown if, and only if, {@code pType} is {@code null}
	 */
	public static PValue valueOf(final PType pType) {
		switch(pType.getName()) {
			case "array":
				return ARRAY;
			case "bool":
				return valueOf(false);
			case "float":
				return valueOf(0.0F);
			case "int":
				return valueOf(0);
			case "string":
				return valueOf("");
			default:
				return NULL;
		}
	}
	
	/**
	 * Returns a {@code PValue} instance given a {@code String} value.
	 * <p>
	 * If {@code value} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param value the {@code String} value
	 * @return a {@code PValue} instance given a {@code String} value
	 * @throws NullPointerException thrown if, and only if, {@code value} is {@code null}
	 */
	public static PValue valueOf(final String value) {
		return new PValue(Objects.requireNonNull(value, "value == null"));
	}
}