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
package org.macroing.cel4j.java.binary.classfile.signature;

import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code BaseType} denotes a BaseType as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum BaseType implements JavaTypeSignature {
	/**
	 * The {@code BaseType} instance that represents the {@code boolean} type.
	 */
	BOOLEAN(Constants.BOOLEAN_TERM, Constants.BOOLEAN_TYPE),
	
	/**
	 * The {@code BaseType} instance that represents the {@code byte} type.
	 */
	BYTE(Constants.BYTE_TERM, Constants.BYTE_TYPE),
	
	/**
	 * The {@code BaseType} instance that represents the {@code char} type.
	 */
	CHAR(Constants.CHAR_TERM, Constants.CHAR_TYPE),
	
	/**
	 * The {@code BaseType} instance that represents the {@code double} type.
	 */
	DOUBLE(Constants.DOUBLE_TERM, Constants.DOUBLE_TYPE),
	
	/**
	 * The {@code BaseType} instance that represents the {@code float} type.
	 */
	FLOAT(Constants.FLOAT_TERM, Constants.FLOAT_TYPE),
	
	/**
	 * The {@code BaseType} instance that represents the {@code int} type.
	 */
	INT(Constants.INT_TERM, Constants.INT_TYPE),
	
	/**
	 * The {@code BaseType} instance that represents the {@code long} type.
	 */
	LONG(Constants.LONG_TERM, Constants.LONG_TYPE),
	
	/**
	 * The {@code BaseType} instance that represents the {@code short} type.
	 */
	SHORT(Constants.SHORT_TERM, Constants.SHORT_TYPE);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String term;
	private final String type;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BaseType(final String term, final String type) {
		this.term = term;
		this.type = type;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} with the term associated with this {@code BaseType} instance.
	 * 
	 * @return a {@code String} with the term associated with this {@code BaseType} instance
	 */
	public String getTerm() {
		return this.term;
	}
	
	/**
	 * Returns a {@code String} with the type associated with this {@code BaseType} instance.
	 * 
	 * @return a {@code String} with the type associated with this {@code BaseType} instance
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code BaseType} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code BaseType} instance in external form
	 */
	@Override
	public String toExternalForm() {
		return this.type;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code BaseType} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code BaseType} instance in internal form
	 */
	@Override
	public String toInternalForm() {
		return this.term;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code BaseType} instance.
	 * 
	 * @return a {@code String} representation of this {@code BaseType} instance
	 */
	@Override
	public String toString() {
		return String.format("BaseType: [Term=%s], [Type=%s], [ExternalForm=%s], [InternalForm=%s]", getTerm(), getType(), toExternalForm(), toInternalForm());
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code BaseType} instance represents the {@code boolean} type, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code BaseType} instance represents the {@code boolean} type, {@code false} otherwise
	 */
	public boolean isBoolean() {
		return getTerm().equals(Constants.BOOLEAN_TERM);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code BaseType} instance represents the {@code byte} type, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code BaseType} instance represents the {@code byte} type, {@code false} otherwise
	 */
	public boolean isByte() {
		return getTerm().equals(Constants.BYTE_TERM);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code BaseType} instance represents the {@code char} type, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code BaseType} instance represents the {@code char} type, {@code false} otherwise
	 */
	public boolean isChar() {
		return getTerm().equals(Constants.CHAR_TERM);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code BaseType} instance represents the {@code double} type, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code BaseType} instance represents the {@code double} type, {@code false} otherwise
	 */
	public boolean isDouble() {
		return getTerm().equals(Constants.DOUBLE_TERM);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code BaseType} instance represents the {@code float} type, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code BaseType} instance represents the {@code float} type, {@code false} otherwise
	 */
	public boolean isFloat() {
		return getTerm().equals(Constants.FLOAT_TERM);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code BaseType} instance represents the {@code int} type, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code BaseType} instance represents the {@code int} type, {@code false} otherwise
	 */
	public boolean isInt() {
		return getTerm().equals(Constants.INT_TERM);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code BaseType} instance represents the {@code long} type, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code BaseType} instance represents the {@code long} type, {@code false} otherwise
	 */
	public boolean isLong() {
		return getTerm().equals(Constants.LONG_TERM);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code BaseType} instance represents the {@code short} type, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code BaseType} instance represents the {@code short} type, {@code false} otherwise
	 */
	public boolean isShort() {
		return getTerm().equals(Constants.SHORT_TERM);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Parses {@code string} into a {@code BaseType} instance.
	 * <p>
	 * Returns a {@code BaseType} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code BaseType} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static BaseType parseBaseType(final String string) {
		return Parsers.parseBaseType(new TextScanner(string));
	}
}