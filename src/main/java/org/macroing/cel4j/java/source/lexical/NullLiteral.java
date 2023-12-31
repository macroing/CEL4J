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

/**
 * A {@code NullLiteral} denotes the nonterminal symbol NullLiteral, as defined by the Java Language Specification.
 * <p>
 * If the Java Language Specification is updated in a way that affects {@code null}, this enum may be updated to reflect those changes. This means that new elements could be added. But it's highly unlikely.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum NullLiteral implements Literal {
	/**
	 * A {@code NullLiteral} with a {@code String} representation that equals the {@code String} literal {@code "null"}.
	 */
	NULL(Constants.NULL_LITERAL);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private NullLiteral(final String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the source code of this {@code NullLiteral} instance.
	 * 
	 * @return the source code of this {@code NullLiteral} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code NullLiteral} instance.
	 * 
	 * @return a {@code String} representation of this {@code NullLiteral} instance
	 */
	@Override
	public String toString() {
		return String.format("NullLiteral: [SourceCode=%s]", getSourceCode());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static NullLiteral of(final String sourceCode) {
		switch(sourceCode) {
			case Constants.NULL_LITERAL:
				return NULL;
			default:
				throw new IllegalArgumentException(String.format("Illegal NullLiteral: %s", sourceCode));
		}
	}
}