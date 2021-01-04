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
 * A {@code HexIntegerLiteral} denotes the nonterminal symbol HexIntegerLiteral, as defined by the Java Language Specification.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class HexIntegerLiteral implements IntegerLiteral {
	private static final Map<String, HexIntegerLiteral> HEX_INTEGER_LITERALS = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Optional<String> integerTypeSuffix;
	private final String hexNumeral;
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private HexIntegerLiteral(final String hexNumeral, final Optional<String> integerTypeSuffix) {
		this.hexNumeral = hexNumeral;
		this.integerTypeSuffix = integerTypeSuffix;
		this.sourceCode = hexNumeral + (integerTypeSuffix.isPresent() ? integerTypeSuffix.get() : "");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code Optional} of {@code String} with the optional integer type suffix of this {@code HexIntegerLiteral} instance.
	 * 
	 * @return an {@code Optional} of {@code String} with the optional integer type suffix of this {@code HexIntegerLiteral} instance
	 */
	public Optional<String> getIntegerTypeSuffix() {
		return this.integerTypeSuffix;
	}
	
	/**
	 * Returns a {@code String} with the hex numeral of this {@code HexIntegerLiteral} instance.
	 * 
	 * @return a {@code String} with the hex numeral of this {@code HexIntegerLiteral} instance
	 */
	public String getHexNumeral() {
		return this.hexNumeral;
	}
	
	/**
	 * Returns the source code of this {@code HexIntegerLiteral} instance.
	 * 
	 * @return the source code of this {@code HexIntegerLiteral} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code HexIntegerLiteral} instance.
	 * 
	 * @return a {@code String} representation of this {@code HexIntegerLiteral} instance
	 */
	@Override
	public String toString() {
		return String.format("HexIntegerLiteral: [SourceCode=%s]", getSourceCode());
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code HexIntegerLiteral}, and that {@code HexIntegerLiteral} instance is equal to this {@code HexIntegerLiteral} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code HexIntegerLiteral} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code HexIntegerLiteral}, and that {@code HexIntegerLiteral} instance is equal to this {@code HexIntegerLiteral} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof HexIntegerLiteral)) {
			return false;
		} else if(!Objects.equals(HexIntegerLiteral.class.cast(object).sourceCode, this.sourceCode)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code HexIntegerLiteral} instance.
	 * 
	 * @return a hash code for this {@code HexIntegerLiteral} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.sourceCode);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static HexIntegerLiteral valueOf(final String hexNumeral, final String integerTypeSuffix) {
		synchronized(HEX_INTEGER_LITERALS) {
			return HEX_INTEGER_LITERALS.computeIfAbsent(hexNumeral + integerTypeSuffix, sourceCode -> new HexIntegerLiteral(hexNumeral, integerTypeSuffix.equals("") ? Optional.empty() : Optional.of(integerTypeSuffix)));
		}
	}
}