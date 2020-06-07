/**
 * Copyright 2009 - 2020 J&#246;rgen Lundgren
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

/**
 * A {@code DecimalFloatingPointLiteral} denotes the nonterminal symbol DecimalFloatingPointLiteral, as defined by the Java Language Specification.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class DecimalFloatingPointLiteral implements FloatingPointLiteral {
	private static final Map<String, DecimalFloatingPointLiteral> DECIMAL_FLOATING_POINT_LITERALS = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private DecimalFloatingPointLiteral(final String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the source code of this {@code DecimalFloatingPointLiteral} instance.
	 * 
	 * @return the source code of this {@code DecimalFloatingPointLiteral} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code DecimalFloatingPointLiteral} instance.
	 * 
	 * @return a {@code String} representation of this {@code DecimalFloatingPointLiteral} instance
	 */
	@Override
	public String toString() {
		return String.format("DecimalFloatingPointLiteral: [SourceCode=%s]", getSourceCode());
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code DecimalFloatingPointLiteral}, and that {@code DecimalFloatingPointLiteral} instance is equal to this {@code DecimalFloatingPointLiteral} instance, {@code false}
	 * otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code DecimalFloatingPointLiteral} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code DecimalFloatingPointLiteral}, and that {@code DecimalFloatingPointLiteral} instance is equal to this {@code DecimalFloatingPointLiteral} instance, {@code false}
	 * otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof DecimalFloatingPointLiteral)) {
			return false;
		} else if(!Objects.equals(DecimalFloatingPointLiteral.class.cast(object).sourceCode, this.sourceCode)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code DecimalFloatingPointLiteral} instance.
	 * 
	 * @return a hash code for this {@code DecimalFloatingPointLiteral} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.sourceCode);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static DecimalFloatingPointLiteral valueOf(final String toString) {
		synchronized(DECIMAL_FLOATING_POINT_LITERALS) {
			return DECIMAL_FLOATING_POINT_LITERALS.computeIfAbsent(toString, string -> new DecimalFloatingPointLiteral(string));
		}
	}
}