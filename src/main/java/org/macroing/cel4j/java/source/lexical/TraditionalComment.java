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

/**
 * An {@code TraditionalComment} denotes the nonterminal symbol TraditionalComment, as defined by the Java Language Specification.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class TraditionalComment implements Comment {
	private static final Map<String, TraditionalComment> TRADITIONAL_COMMENTS = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private TraditionalComment(final String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the source code of this {@code TraditionalComment} instance.
	 * 
	 * @return the source code of this {@code TraditionalComment} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TraditionalComment} instance.
	 * 
	 * @return a {@code String} representation of this {@code TraditionalComment} instance
	 */
	@Override
	public String toString() {
		return String.format("TraditionalComment: [SourceCode=%s]", getSourceCode());
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code TraditionalComment}, and that {@code TraditionalComment} instance is equal to this {@code TraditionalComment} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code TraditionalComment} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code EndOfLineComment}, and that {@code TraditionalComment} instance is equal to this {@code TraditionalComment} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof TraditionalComment)) {
			return false;
		} else if(!Objects.equals(TraditionalComment.class.cast(object).sourceCode, this.sourceCode)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code TraditionalComment} instance.
	 * 
	 * @return a hash code for this {@code TraditionalComment} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.sourceCode);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static TraditionalComment valueOf(final String sourceCode) {
		synchronized(TRADITIONAL_COMMENTS) {
			return TRADITIONAL_COMMENTS.computeIfAbsent(sourceCode, sourceCode0 -> new TraditionalComment(sourceCode0));
		}
	}
}