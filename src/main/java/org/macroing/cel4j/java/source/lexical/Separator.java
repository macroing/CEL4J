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
 * A {@code Separator} denotes the nonterminal symbol Separator, as defined by the Java Language Specification.
 * <p>
 * If the Java Language Specification is updated in a way that affects any separators, this enum may be updated to reflect those changes. This means that new elements could be added. This happened between Java 7 and Java 8.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum Separator implements Token {
	/**
	 * A {@code Separator} with a {@code String} representation that equals the {@code String} literal {@code "@"}.
	 */
	AT(Constants.SEPARATOR_AT),
	
	/**
	 * A {@code Separator} with a {@code String} representation that equals the {@code String} literal {@code ","}.
	 */
	COMMA(Constants.SEPARATOR_COMMA),
	
	/**
	 * A {@code Separator} with a {@code String} representation that equals the {@code String} literal <code>"{"</code>.
	 */
	CURLY_BRACKET_LEFT(Constants.SEPARATOR_CURLY_BRACKET_LEFT),
	
	/**
	 * A {@code Separator} with a {@code String} representation that equals the {@code String} literal <code>"}"</code>.
	 */
	CURLY_BRACKET_RIGHT(Constants.SEPARATOR_CURLY_BRACKET_RIGHT),
	
	/**
	 * A {@code Separator} with a {@code String} representation that equals the {@code String} literal {@code "::"}.
	 */
	DOUBLE_COLON(Constants.SEPARATOR_DOUBLE_COLON),
	
	/**
	 * A {@code Separator} with a {@code String} representation that equals the {@code String} literal {@code "("}.
	 */
	PARENTHESIS_LEFT(Constants.SEPARATOR_PARENTHESIS_LEFT),
	
	/**
	 * A {@code Separator} with a {@code String} representation that equals the {@code String} literal {@code ")"}.
	 */
	PARENTHESIS_RIGHT(Constants.SEPARATOR_PARENTHESIS_RIGHT),
	
	/**
	 * A {@code Separator} with a {@code String} representation that equals the {@code String} literal {@code "."}.
	 */
	PERIOD(Constants.SEPARATOR_PERIOD),
	
	/**
	 * A {@code Separator} with a {@code String} representation that equals the {@code String} literal {@code ";"}.
	 */
	SEMICOLON(Constants.SEPARATOR_SEMICOLON),
	
	/**
	 * A {@code Separator} with a {@code String} representation that equals the {@code String} literal {@code "["}.
	 */
	SQUARE_BRACKET_LEFT(Constants.SEPARATOR_SQUARE_BRACKET_LEFT),
	
	/**
	 * A {@code Separator} with a {@code String} representation that equals the {@code String} literal {@code "]"}.
	 */
	SQUARE_BRACKET_RIGHT(Constants.SEPARATOR_SQUARE_BRACKET_RIGHT),
	
	/**
	 * A {@code Separator} with a {@code String} representation that equals the {@code String} literal {@code "..."}.
	 */
	TRIPPLE_PERIOD(Constants.SEPARATOR_TRIPPLE_PERIOD);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Separator(final String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the source code of this {@code Separator} instance.
	 * 
	 * @return the source code of this {@code Separator} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Separator} instance.
	 * 
	 * @return a {@code String} representation of this {@code Separator} instance
	 */
	@Override
	public String toString() {
		return String.format("Separator: [SourceCode=%s]", getSourceCode());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static Separator of(final String sourceCode) {
		switch(sourceCode) {
			case Constants.SEPARATOR_AT:
				return AT;
			case Constants.SEPARATOR_COMMA:
				return COMMA;
			case Constants.SEPARATOR_CURLY_BRACKET_LEFT:
				return CURLY_BRACKET_LEFT;
			case Constants.SEPARATOR_CURLY_BRACKET_RIGHT:
				return CURLY_BRACKET_RIGHT;
			case Constants.SEPARATOR_DOUBLE_COLON:
				return DOUBLE_COLON;
			case Constants.SEPARATOR_PARENTHESIS_LEFT:
				return PARENTHESIS_LEFT;
			case Constants.SEPARATOR_PARENTHESIS_RIGHT:
				return PARENTHESIS_RIGHT;
			case Constants.SEPARATOR_PERIOD:
				return PERIOD;
			case Constants.SEPARATOR_SEMICOLON:
				return SEMICOLON;
			case Constants.SEPARATOR_SQUARE_BRACKET_LEFT:
				return SQUARE_BRACKET_LEFT;
			case Constants.SEPARATOR_SQUARE_BRACKET_RIGHT:
				return SQUARE_BRACKET_RIGHT;
			case Constants.SEPARATOR_TRIPPLE_PERIOD:
				return TRIPPLE_PERIOD;
			default:
				throw new IllegalArgumentException(String.format("Illegal Separator: %s", sourceCode));
		}
	}
}