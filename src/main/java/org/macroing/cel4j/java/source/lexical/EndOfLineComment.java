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
 * An {@code EndOfLineComment} denotes the nonterminal symbol EndOfLineComment, as defined by the Java Language Specification.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class EndOfLineComment implements Comment {
	private static final Map<String, EndOfLineComment> END_OF_LINE_COMMENTS = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private EndOfLineComment(final String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the source code of this {@code EndOfLineComment} instance.
	 * 
	 * @return the source code of this {@code EndOfLineComment} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code EndOfLineComment} instance.
	 * 
	 * @return a {@code String} representation of this {@code EndOfLineComment} instance
	 */
	@Override
	public String toString() {
		return String.format("EndOfLineComment: [SourceCode=%s]", getSourceCode());
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code EndOfLineComment}, and that {@code EndOfLineComment} instance is equal to this {@code EndOfLineComment} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code EndOfLineComment} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code EndOfLineComment}, and that {@code EndOfLineComment} instance is equal to this {@code EndOfLineComment} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof EndOfLineComment)) {
			return false;
		} else if(!Objects.equals(EndOfLineComment.class.cast(object).sourceCode, this.sourceCode)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code EndOfLineComment} instance.
	 * 
	 * @return a hash code for this {@code EndOfLineComment} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.sourceCode);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static EndOfLineComment valueOf(final String sourceCode) {
		synchronized(END_OF_LINE_COMMENTS) {
			return END_OF_LINE_COMMENTS.computeIfAbsent(sourceCode, sourceCode0 -> new EndOfLineComment(sourceCode0));
		}
	}
}