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
package org.macroing.cel4j.java.source.lexical;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@code BinaryIntegerLiteral} denotes the nonterminal symbol BinaryIntegerLiteral, as defined by the Java Language Specification.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class BinaryIntegerLiteral implements IntegerLiteral {
	private static final Map<String, BinaryIntegerLiteral> BINARY_INTEGER_LITERALS = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Optional<String> integerTypeSuffix;
	private final String binaryNumeral;
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BinaryIntegerLiteral(final String binaryNumeral, final Optional<String> integerTypeSuffix) {
		this.binaryNumeral = binaryNumeral;
		this.integerTypeSuffix = integerTypeSuffix;
		this.sourceCode = binaryNumeral + (integerTypeSuffix.isPresent() ? integerTypeSuffix.get() : "");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code Optional} of {@code String} with the optional integer type suffix of this {@code BinaryIntegerLiteral} instance.
	 * 
	 * @return an {@code Optional} of {@code String} with the optional integer type suffix of this {@code BinaryIntegerLiteral} instance
	 */
	public Optional<String> getIntegerTypeSuffix() {
		return this.integerTypeSuffix;
	}
	
	/**
	 * Returns a {@code String} with the binary numeral of this {@code BinaryIntegerLiteral} instance.
	 * 
	 * @return a {@code String} with the binary numeral of this {@code BinaryIntegerLiteral} instance
	 */
	public String getBinaryNumeral() {
		return this.binaryNumeral;
	}
	
	/**
	 * Returns the source code of this {@code BinaryIntegerLiteral} instance.
	 * 
	 * @return the source code of this {@code BinaryIntegerLiteral} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code BinaryIntegerLiteral} instance.
	 * 
	 * @return a {@code String} representation of this {@code BinaryIntegerLiteral} instance
	 */
	@Override
	public String toString() {
		return String.format("BinaryIntegerLiteral: [SourceCode=%s]", getSourceCode());
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code BinaryIntegerLiteral}, and that {@code BinaryIntegerLiteral} instance is equal to this {@code BinaryIntegerLiteral} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code BinaryIntegerLiteral} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code BinaryIntegerLiteral}, and that {@code BinaryIntegerLiteral} instance is equal to this {@code BinaryIntegerLiteral} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof BinaryIntegerLiteral)) {
			return false;
		} else if(!Objects.equals(BinaryIntegerLiteral.class.cast(object).sourceCode, this.sourceCode)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code BinaryIntegerLiteral} instance.
	 * 
	 * @return a hash code for this {@code BinaryIntegerLiteral} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.sourceCode);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static BinaryIntegerLiteral valueOf(final String binaryNumeral, final String integerTypeSuffix) {
		synchronized(BINARY_INTEGER_LITERALS) {
			return BINARY_INTEGER_LITERALS.computeIfAbsent(binaryNumeral + integerTypeSuffix, sourceCode -> new BinaryIntegerLiteral(binaryNumeral, integerTypeSuffix.equals("") ? Optional.empty() : Optional.of(integerTypeSuffix)));
		}
	}
}