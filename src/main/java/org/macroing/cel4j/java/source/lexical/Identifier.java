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
import java.util.regex.Pattern;

import org.macroing.cel4j.node.NodeFormatException;

/**
 * An {@code Identifier} denotes the nonterminal symbol Identifier, as defined by the Java Language Specification.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Identifier implements Token {
	private static final Map<String, Identifier> IDENTIFIERS = new HashMap<>();
	private static final Pattern PATTERN = Pattern.compile(Constants.REGEX_IDENTIFIER);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Identifier(final String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the source code of this {@code Identifier} instance.
	 * 
	 * @return the source code of this {@code Identifier} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Identifier} instance.
	 * 
	 * @return a {@code String} representation of this {@code Identifier} instance
	 */
	@Override
	public String toString() {
		return String.format("Identifier: [SourceCode=%s]", getSourceCode());
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Identifier}, and that {@code Identifier} instance is equal to this {@code Identifier} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code Identifier} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Identifier}, and that {@code Identifier} instance is equal to this {@code Identifier} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Identifier)) {
			return false;
		} else if(!Objects.equals(Identifier.class.cast(object).sourceCode, this.sourceCode)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash-code for this {@code Identifier} instance.
	 * 
	 * @return a hash-code for this {@code Identifier} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.sourceCode);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Attempts to parse {@code sourceCode} as an {@code Identifier}.
	 * <p>
	 * Returns an {@code Identifier}.
	 * <p>
	 * If {@code sourceCode} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code sourceCode} cannot be parsed into an {@code Identifier}, a {@code NodeFormatException} will be thrown.
	 * 
	 * @param sourceCode the {@code String} to parse
	 * @return an {@code Identifier}
	 * @throws NodeFormatException thrown if, and only if, {@code sourceCode} cannot be parsed into an {@code Identifier}
	 * @throws NullPointerException thrown if, and only if, {@code sourceCode} is {@code null}
	 */
	public static Identifier parseIdentifier(final String sourceCode) {
		if(PATTERN.matcher(Objects.requireNonNull(sourceCode, "sourceCode == null")).matches()) {
			return valueOf(sourceCode);
		}
		
		throw new NodeFormatException(String.format("Illegal Identifier: %s", sourceCode));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static Identifier valueOf(final String sourceCode) {
		synchronized(IDENTIFIERS) {
			return IDENTIFIERS.computeIfAbsent(sourceCode, sourceCode0 -> new Identifier(sourceCode0));
		}
	}
}