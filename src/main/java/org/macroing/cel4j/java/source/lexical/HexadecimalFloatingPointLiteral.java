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
 * A {@code HexadecimalFloatingPointLiteral} denotes the nonterminal symbol HexadecimalFloatingPointLiteral, as defined by the Java Language Specification.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class HexadecimalFloatingPointLiteral implements FloatingPointLiteral {
	private static final Map<String, HexadecimalFloatingPointLiteral> HEXADECIMAL_FLOATING_POINT_LITERALS = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Optional<String> floatTypeSuffix;
	private final String binaryExponent;
	private final String hexSignificand;
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private HexadecimalFloatingPointLiteral(final String hexSignificand, final String binaryExponent, final Optional<String> floatTypeSuffix) {
		this.hexSignificand = hexSignificand;
		this.binaryExponent = binaryExponent;
		this.floatTypeSuffix = floatTypeSuffix;
		this.sourceCode = hexSignificand + binaryExponent + (floatTypeSuffix.isPresent() ? floatTypeSuffix.get() : "");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code Optional} of {@code String} with the optional float type suffix of this {@code HexadecimalFloatingPointLiteral} instance.
	 * 
	 * @return an {@code Optional} of {@code String} with the optional float type suffix of this {@code HexadecimalFloatingPointLiteral} instance
	 */
	public Optional<String> getFloatTypeSuffix() {
		return this.floatTypeSuffix;
	}
	
	/**
	 * Returns a {@code String} with the binary exponent of this {@code HexadecimalFloatingPointLiteral} instance.
	 * 
	 * @return a {@code String} with the binary exponent of this {@code HexadecimalFloatingPointLiteral} instance
	 */
	public String getBinaryExponent() {
		return this.binaryExponent;
	}
	
	/**
	 * Returns a {@code String} with the hex significand of this {@code HexadecimalFloatingPointLiteral} instance.
	 * 
	 * @return a {@code String} with the hex significand of this {@code HexadecimalFloatingPointLiteral} instance
	 */
	public String getHexSignificand() {
		return this.hexSignificand;
	}
	
	/**
	 * Returns the source code of this {@code HexadecimalFloatingPointLiteral} instance.
	 * 
	 * @return the source code of this {@code HexadecimalFloatingPointLiteral} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code HexadecimalFloatingPointLiteral} instance.
	 * 
	 * @return a {@code String} representation of this {@code HexadecimalFloatingPointLiteral} instance
	 */
	@Override
	public String toString() {
		return String.format("HexadecimalFloatingPointLiteral: [SourceCode=%s]", getSourceCode());
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code HexadecimalFloatingPointLiteral}, and that {@code HexadecimalFloatingPointLiteral} instance is equal to this {@code HexadecimalFloatingPointLiteral} instance,
	 * {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code HexadecimalFloatingPointLiteral} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code HexadecimalFloatingPointLiteral}, and that {@code HexadecimalFloatingPointLiteral} instance is equal to this {@code HexadecimalFloatingPointLiteral} instance,
	 * {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof HexadecimalFloatingPointLiteral)) {
			return false;
		} else if(!Objects.equals(HexadecimalFloatingPointLiteral.class.cast(object).sourceCode, this.sourceCode)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code HexadecimalFloatingPointLiteral} instance.
	 * 
	 * @return a hash code for this {@code HexadecimalFloatingPointLiteral} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.sourceCode);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static HexadecimalFloatingPointLiteral valueOf(final String hexSignificand, final String binaryExponent, final String floatTypeSuffix) {
		synchronized(HEXADECIMAL_FLOATING_POINT_LITERALS) {
			return HEXADECIMAL_FLOATING_POINT_LITERALS.computeIfAbsent(hexSignificand + binaryExponent + floatTypeSuffix, sourceCode -> new HexadecimalFloatingPointLiteral(hexSignificand, binaryExponent, floatTypeSuffix.equals("") ? Optional.empty() : Optional.of(floatTypeSuffix)));
		}
	}
}