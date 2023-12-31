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
 * A {@code OctalIntegerLiteral} denotes the nonterminal symbol OctalIntegerLiteral, as defined by the Java Language Specification.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class OctalIntegerLiteral implements IntegerLiteral {
	private static final Map<String, OctalIntegerLiteral> OCTAL_INTEGER_LITERALS = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Optional<String> integerTypeSuffix;
	private final String octalNumeral;
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private OctalIntegerLiteral(final String octalNumeral, final Optional<String> integerTypeSuffix) {
		this.octalNumeral = octalNumeral;
		this.integerTypeSuffix = integerTypeSuffix;
		this.sourceCode = octalNumeral + (integerTypeSuffix.isPresent() ? integerTypeSuffix.get() : "");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code Optional} of {@code String} with the optional integer type suffix of this {@code OctalIntegerLiteral} instance.
	 * 
	 * @return an {@code Optional} of {@code String} with the optional integer type suffix of this {@code OctalIntegerLiteral} instance
	 */
	public Optional<String> getIntegerTypeSuffix() {
		return this.integerTypeSuffix;
	}
	
	/**
	 * Returns a {@code String} with the hex numeral of this {@code OctalIntegerLiteral} instance.
	 * 
	 * @return a {@code String} with the hex numeral of this {@code OctalIntegerLiteral} instance
	 */
	public String getOctalNumeral() {
		return this.octalNumeral;
	}
	
	/**
	 * Returns the source code of this {@code OctalIntegerLiteral} instance.
	 * 
	 * @return the source code of this {@code OctalIntegerLiteral} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code OctalIntegerLiteral} instance.
	 * 
	 * @return a {@code String} representation of this {@code OctalIntegerLiteral} instance
	 */
	@Override
	public String toString() {
		return String.format("OctalIntegerLiteral: [SourceCode=%s]", getSourceCode());
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code OctalIntegerLiteral}, and that {@code OctalIntegerLiteral} instance is equal to this {@code OctalIntegerLiteral} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code OctalIntegerLiteral} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code OctalIntegerLiteral}, and that {@code OctalIntegerLiteral} instance is equal to this {@code OctalIntegerLiteral} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof OctalIntegerLiteral)) {
			return false;
		} else if(!Objects.equals(OctalIntegerLiteral.class.cast(object).sourceCode, this.sourceCode)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code OctalIntegerLiteral} instance.
	 * 
	 * @return a hash code for this {@code OctalIntegerLiteral} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.sourceCode);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static OctalIntegerLiteral valueOf(final String octalNumeral, final String integerTypeSuffix) {
		synchronized(OCTAL_INTEGER_LITERALS) {
			return OCTAL_INTEGER_LITERALS.computeIfAbsent(octalNumeral + integerTypeSuffix, sourceCode -> new OctalIntegerLiteral(octalNumeral, integerTypeSuffix.equals("") ? Optional.empty() : Optional.of(integerTypeSuffix)));
		}
	}
}