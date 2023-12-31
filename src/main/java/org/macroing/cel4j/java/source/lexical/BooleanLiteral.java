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
 * A {@code BooleanLiteral} denotes the nonterminal symbol BooleanLiteral, as defined by the Java Language Specification.
 * <p>
 * If the Java Language Specification is updated in a way that affects the {@code boolean} type, this enum may be updated to reflect those changes. This means that new elements could be added. But it's highly unlikely.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum BooleanLiteral implements Literal {
	/**
	 * A {@code BooleanLiteral} representing {@code false}.
	 */
	FALSE(false),
	
	/**
	 * A {@code BooleanLiteral} representing {@code true}.
	 */
	TRUE(true);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String sourceCode;
	private final boolean toBoolean;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BooleanLiteral(final boolean toBoolean) {
		this.sourceCode = Boolean.toString(toBoolean);
		this.toBoolean = toBoolean;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the source code of this {@code BooleanLiteral} instance.
	 * 
	 * @return the source code of this {@code BooleanLiteral} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code BooleanLiteral} instance.
	 * 
	 * @return a {@code String} representation of this {@code BooleanLiteral} instance
	 */
	@Override
	public String toString() {
		return String.format("BooleanLiteral: [SourceCode=%s]", getSourceCode());
	}
	
	/**
	 * Returns a {@code boolean} representation of this {@code BooleanLiteral} instance.
	 * 
	 * @return a {@code boolean} representation of this {@code BooleanLiteral} instance
	 */
	public boolean toBoolean() {
		return this.toBoolean;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a negated version of the given {@code BooleanLiteral} instance.
	 * <p>
	 * If {@code booleanLiteral} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If you import members from this class statically, the expression {@code not(TRUE)} will return {@code FALSE}.
	 * 
	 * @param booleanLiteral the {@code BooleanLiteral} to negate
	 * @return a negated version of the given {@code BooleanLiteral} instance
	 * @throws NullPointerException thrown if, and only if, {@code booleanLiteral} is {@code null}
	 */
	public static BooleanLiteral not(final BooleanLiteral booleanLiteral) {
		return booleanLiteral.toBoolean() ? FALSE : TRUE;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static BooleanLiteral of(final String sourceCode) {
		switch(sourceCode) {
			case Constants.BOOLEAN_LITERAL_FALSE:
				return FALSE;
			case Constants.BOOLEAN_LITERAL_TRUE:
				return TRUE;
			default:
				throw new IllegalArgumentException(String.format("Illegal BooleanLiteral: %s", sourceCode));
		}
	}
}