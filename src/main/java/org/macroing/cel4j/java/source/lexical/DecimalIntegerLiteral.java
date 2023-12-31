/**
 * Copyright 2009 - 2024 J&#246;rgen Lundgren
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
 * A {@code DecimalIntegerLiteral} denotes the nonterminal symbol DecimalIntegerLiteral, as defined by the Java Language Specification.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class DecimalIntegerLiteral implements IntegerLiteral {
	private static final Map<String, DecimalIntegerLiteral> DECIMAL_INTEGER_LITERALS = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Optional<String> integerTypeSuffix;
	private final String decimalNumeral;
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private DecimalIntegerLiteral(final String decimalNumeral, final Optional<String> integerTypeSuffix) {
		this.decimalNumeral = decimalNumeral;
		this.integerTypeSuffix = integerTypeSuffix;
		this.sourceCode = decimalNumeral + (integerTypeSuffix.isPresent() ? integerTypeSuffix.get() : "");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code Optional} of {@code String} with the optional integer type suffix of this {@code DecimalIntegerLiteral} instance.
	 * 
	 * @return an {@code Optional} of {@code String} with the optional integer type suffix of this {@code DecimalIntegerLiteral} instance
	 */
	public Optional<String> getIntegerTypeSuffix() {
		return this.integerTypeSuffix;
	}
	
	/**
	 * Returns a {@code String} with the decimal numeral of this {@code DecimalIntegerLiteral} instance.
	 * 
	 * @return a {@code String} with the decimal numeral of this {@code DecimalIntegerLiteral} instance
	 */
	public String getDecimalNumeral() {
		return this.decimalNumeral;
	}
	
	/**
	 * Returns the source code of this {@code DecimalIntegerLiteral} instance.
	 * 
	 * @return the source code of this {@code DecimalIntegerLiteral} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code DecimalIntegerLiteral} instance.
	 * 
	 * @return a {@code String} representation of this {@code DecimalIntegerLiteral} instance
	 */
	@Override
	public String toString() {
		return String.format("DecimalIntegerLiteral: [SourceCode=%s]", getSourceCode());
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code DecimalIntegerLiteral}, and that {@code DecimalIntegerLiteral} instance is equal to this {@code DecimalIntegerLiteral} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code DecimalIntegerLiteral} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code DecimalIntegerLiteral}, and that {@code DecimalIntegerLiteral} instance is equal to this {@code DecimalIntegerLiteral} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof DecimalIntegerLiteral)) {
			return false;
		} else if(!Objects.equals(DecimalIntegerLiteral.class.cast(object).sourceCode, this.sourceCode)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code DecimalIntegerLiteral} instance.
	 * 
	 * @return a hash code for this {@code DecimalIntegerLiteral} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.sourceCode);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static DecimalIntegerLiteral valueOf(final String decimalNumeral, final String integerTypeSuffix) {
		synchronized(DECIMAL_INTEGER_LITERALS) {
			return DECIMAL_INTEGER_LITERALS.computeIfAbsent(decimalNumeral + integerTypeSuffix, string -> new DecimalIntegerLiteral(decimalNumeral, integerTypeSuffix.equals("") ? Optional.empty() : Optional.of(integerTypeSuffix)));
		}
	}
}