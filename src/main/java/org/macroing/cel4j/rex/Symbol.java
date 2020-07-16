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
package org.macroing.cel4j.rex;

import java.util.Objects;

import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code Symbol} is a {@link Matcher} that can match {@code String} values.
 * <p>
 * This class is immutable and thread-safe.
 * <p>
 * A {@code Symbol} consists of a {@code Character} literal or a {@code String} literal, as well as an optional {@link Repetition} instance.
 * <p>
 * To use a {@code Symbol} in Rex, consider the following examples:
 * <pre>
 * {@code
 * "ABC"
 * 'A'?
 * }
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Symbol implements Matcher {
	private final Repetition repetition;
	private final String string;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Symbol} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Symbol(string, Repetition.ONE);
	 * }
	 * </pre>
	 * 
	 * @param string the {@code String} associated with this {@code Symbol} instance
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public Symbol(final String string) {
		this(string, Repetition.ONE);
	}
	
	/**
	 * Constructs a new {@code Symbol} instance.
	 * <p>
	 * If either {@code string} or {@code repetition} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param string the {@code String} associated with this {@code Symbol} instance
	 * @param repetition the {@link Repetition} associated with this {@code Symbol} instance
	 * @throws NullPointerException thrown if, and only if, either {@code string} or {@code repetition} are {@code null}
	 */
	public Symbol(final String string, final Repetition repetition) {
		this.repetition = Objects.requireNonNull(repetition, "repetition == null");
		this.string = Objects.requireNonNull(string, "string == null");
	}
	
	/**
	 * Constructs a new {@code Symbol} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Symbol(character, Repetition.ONE);
	 * }
	 * </pre>
	 * 
	 * @param character the {@code char} associated with this {@code Symbol} instance
	 */
	public Symbol(final char character) {
		this(character, Repetition.ONE);
	}
	
	/**
	 * Constructs a new {@code Symbol} instance.
	 * <p>
	 * If {@code repetition} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Symbol(Character.toString(character), repetition);
	 * }
	 * </pre>
	 * 
	 * @param character the {@code char} associated with this {@code Symbol} instance
	 * @param repetition the {@link Repetition} associated with this {@code Symbol} instance
	 * @throws NullPointerException thrown if, and only if, {@code repetition} is {@code null}
	 */
	public Symbol(final char character, final Repetition repetition) {
		this(Character.toString(character), repetition);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code Symbol} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * symbol.write(new Document());
	 * }
	 * </pre>
	 * 
	 * @return the {@code Document}
	 */
	@Override
	public Document write() {
		return write(new Document());
	}
	
	/**
	 * Writes this {@code Symbol} to {@code document}.
	 * <p>
	 * Returns {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param document the {@link Document} to write to
	 * @return {@code document}
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	@Override
	public Document write(final Document document) {
		document.linef("Symbol \"%s\";", getString());
		
		return document;
	}
	
	/**
	 * Matches {@code input}.
	 * <p>
	 * Returns a {@link MatchResult} with the result of the match.
	 * <p>
	 * If {@code input} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * symbol.match(input, 0);
	 * }
	 * </pre>
	 * 
	 * @param input the {@code String} to match
	 * @return a {@code MatchResult} with the result of the match
	 * @throws NullPointerException thrown if, and only if, {@code input} is {@code null}
	 */
	@Override
	public MatchResult match(final String input) {
		return match(input, 0);
	}
	
	/**
	 * Matches {@code input}.
	 * <p>
	 * Returns a {@link MatchResult} with the result of the match.
	 * <p>
	 * If {@code input} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code input.length()}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param input the {@code String} to match
	 * @param index the index in {@code input} to match from
	 * @return a {@code MatchResult} with the result of the match
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than or equal to {@code input.length()}
	 * @throws NullPointerException thrown if, and only if, {@code input} is {@code null}
	 */
	@Override
	public MatchResult match(final String input, final int index) {
		Objects.requireNonNull(input, "input == null");
		
		ParameterArguments.requireRange(index, 0, input.length(), "index");
		
		final Repetition repetition = getRepetition();
		
		final String string = getString();
		
		final int minimumRepetition = repetition.getMinimum();
		final int maximumRepetition = repetition.getMaximum();
		
		int currentIndex = index;
		int currentRepetition = 0;
		
		int length = 0;
		
		for(int i = 1; i <= maximumRepetition && currentIndex < input.length(); i++) {
			int currentLength = 0;
			
			for(int j = 0; j < string.length(); j++) {
				if(input.charAt(currentIndex + j) == string.charAt(j)) {
					currentLength++;
				} else {
					break;
				}
			}
			
			if(currentLength == string.length()) {
				currentIndex += currentLength;
				currentRepetition++;
				
				length += currentLength;
			} else {
				break;
			}
		}
		
		if(currentRepetition >= minimumRepetition) {
			return new MatchResult(this, input, true, index, index + length);
		}
		
		return new MatchResult(this, input, false, index);
	}
	
	/**
	 * Returns the {@link Repetition} associated with this {@code Symbol} instance.
	 * 
	 * @return the {@code Repetition} associated with this {@code Symbol} instance
	 */
	public Repetition getRepetition() {
		return this.repetition;
	}
	
	/**
	 * Returns the source code associated with this {@code Symbol} instance.
	 * 
	 * @return the source code associated with this {@code Symbol} instance
	 */
	@Override
	public String getSourceCode() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getString().length() == 1 ? "'" : "\"");
		stringBuilder.append(getString().replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t"));
		stringBuilder.append(getString().length() == 1 ? "'" : "\"");
		stringBuilder.append(getRepetition().getSourceCode());
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns the {@code String} associated with this {@code Symbol} instance.
	 * 
	 * @return the {@code String} associated with this {@code Symbol} instance
	 */
	public String getString() {
		return this.string;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Symbol} instance.
	 * 
	 * @return a {@code String} representation of this {@code Symbol} instance
	 */
	@Override
	public String toString() {
		return String.format("new Symbol(\"%s\", %s)", getString(), getRepetition());
	}
	
	/**
	 * Compares {@code object} to this {@code Symbol} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Symbol}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Symbol} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Symbol}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Symbol)) {
			return false;
		} else if(!Objects.equals(getRepetition(), Symbol.class.cast(object).getRepetition())) {
			return false;
		} else if(!Objects.equals(getString(), Symbol.class.cast(object).getString())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Symbol} instance.
	 * 
	 * @return a hash code for this {@code Symbol} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getRepetition(), getString());
	}
}