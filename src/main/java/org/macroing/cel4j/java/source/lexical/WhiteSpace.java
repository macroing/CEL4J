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

/**
 * A {@code WhiteSpace} denotes the nonterminal symbol WhiteSpace, as defined by the Java Language Specification.
 * <p>
 * If the Java Language Specification is updated in a way that affects any white-space characters, this enum may be updated to reflect those changes. This means that new elements could be added. But it's highly unlikely.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum WhiteSpace implements InputElement {
	/**
	 * A {@code WhiteSpace} with a {@code String} representation that equals the {@code String} literal {@code "\r"}.
	 */
	CARRIAGE_RETURN(Constants.WHITE_SPACE_CARRIAGE_RETURN),
	
	/**
	 * A {@code WhiteSpace} with a {@code String} representation that equals the {@code String} literal {@code "\r\n"}.
	 */
	CARRIAGE_RETURN_LINE_FEED(Constants.WHITE_SPACE_CARRIAGE_RETURN_LINE_FEED),
	
	/**
	 * A {@code WhiteSpace} with a {@code String} representation that equals the {@code String} literal {@code "\f"}.
	 */
	FORM_FEED(Constants.WHITE_SPACE_FORM_FEED),
	
	/**
	 * A {@code WhiteSpace} with a {@code String} representation that equals the {@code String} literal {@code "\t"}.
	 */
	HORIZONTAL_TAB(Constants.WHITE_SPACE_HORIZONTAL_TAB),
	
	/**
	 * A {@code WhiteSpace} with a {@code String} representation that equals the {@code String} literal {@code "\n"}.
	 */
	LINE_FEED(Constants.WHITE_SPACE_LINE_FEED),
	
	/**
	 * A {@code WhiteSpace} with a {@code String} representation that equals the {@code String} literal {@code " "}.
	 */
	SPACE(Constants.WHITE_SPACE_SPACE);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private WhiteSpace(final String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the source code of this {@code WhiteSpace} instance.
	 * 
	 * @return the source code of this {@code WhiteSpace} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code WhiteSpace} instance.
	 * 
	 * @return a {@code String} representation of this {@code WhiteSpace} instance
	 */
	@Override
	public String toString() {
		return String.format("WhiteSpace: [SourceCode=%s]", getSourceCode());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static WhiteSpace of(final String sourceCode) {
		switch(sourceCode) {
			case Constants.WHITE_SPACE_CARRIAGE_RETURN:
				return CARRIAGE_RETURN;
			case Constants.WHITE_SPACE_CARRIAGE_RETURN_LINE_FEED:
				return CARRIAGE_RETURN_LINE_FEED;
			case Constants.WHITE_SPACE_FORM_FEED:
				return FORM_FEED;
			case Constants.WHITE_SPACE_HORIZONTAL_TAB:
				return HORIZONTAL_TAB;
			case Constants.WHITE_SPACE_LINE_FEED:
				return LINE_FEED;
			case Constants.WHITE_SPACE_SPACE:
				return SPACE;
			default:
				throw new IllegalArgumentException(String.format("Illegal WhiteSpace: %s", sourceCode));
		}
	}
}