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
package org.macroing.cel4j.java.source.lexical;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A {@code CharacterLiteral} denotes the nonterminal symbol CharacterLiteral, as defined by the Java Language Specification.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class CharacterLiteral implements Literal {
	private static final Map<String, CharacterLiteral> CHARACTER_LITERALS = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private CharacterLiteral(final String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the source code of this {@code CharacterLiteral} instance.
	 * 
	 * @return the source code of this {@code CharacterLiteral} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code CharacterLiteral} instance.
	 * 
	 * @return a {@code String} representation of this {@code CharacterLiteral} instance
	 */
	@Override
	public String toString() {
		return String.format("CharacterLiteral: [SourceCode=%s]", getSourceCode());
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code CharacterLiteral}, and that {@code CharacterLiteral} instance is equal to this {@code CharacterLiteral} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code CharacterLiteral} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code CharacterLiteral}, and that {@code CharacterLiteral} instance is equal to this {@code CharacterLiteral} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof CharacterLiteral)) {
			return false;
		} else if(!Objects.equals(CharacterLiteral.class.cast(object).sourceCode, this.sourceCode)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code CharacterLiteral} instance.
	 * 
	 * @return a hash code for this {@code CharacterLiteral} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.sourceCode);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static CharacterLiteral valueOf(final String sourceCode) {
		synchronized(CHARACTER_LITERALS) {
			return CHARACTER_LITERALS.computeIfAbsent(sourceCode, sourceCode0 -> new CharacterLiteral(sourceCode0));
		}
	}
}