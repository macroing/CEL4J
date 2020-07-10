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

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;

import org.macroing.cel4j.rex.Matcher.MatchInfo;
import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class Symbol implements Matchable {
	private final Repetition repetition;
	private final char character;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
	 * 
	 * @param character the {@code char} associated with this {@code Symbol} instance
	 * @param repetition the {@link Repetition} associated with this {@code Symbol} instance
	 * @throws NullPointerException thrown if, and only if, {@code repetition} is {@code null}
	 */
	public Symbol(final char character, final Repetition repetition) {
		this.repetition = Objects.requireNonNull(repetition, "repetition == null");
		this.character = character;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public Matcher matcher(final String source) {
		return matcher(source, 0, 0);
	}
	
//	TODO: Add Javadocs!
	@Override
	public Matcher matcher(final String source, final int beginIndex, final int endIndex) {
		Objects.requireNonNull(source, "source == null");
		
		ParameterArguments.requireRange(beginIndex, 0, source.length(), "beginIndex");
		ParameterArguments.requireRange(endIndex, beginIndex, source.length(), "endIndex");
		
		boolean isInEscapeMode = false;
		boolean isMatching = true;
		
		int currentCharacterMatch = 0;
		int length = source.length();
		int matches = 0;
		int maximum = this.repetition.getMaximum();
		int minimum = this.repetition.getMinimum();
		int newBeginIndex = beginIndex;
		int newEndIndex = endIndex;
		
		MatchInfo matchInfo = MatchInfo.SUCCESS;
		
		for(; newEndIndex < length; newEndIndex++) {
			char character = source.charAt(newEndIndex);
			
			if(character == '\\' && !isInEscapeMode) {
				isInEscapeMode = true;
				
				currentCharacterMatch++;
			} else if(this.character == character) {
				isInEscapeMode = false;
				
				currentCharacterMatch++;
				
				matches++;
				
				matchInfo = MatchInfo.SUCCESS;
				
				if(matches == maximum) {
					newEndIndex++;
					
					break;
				}
			} else {
				if(matches < minimum) {
					matchInfo = MatchInfo.UNEXPECTED_CHARACTER_MATCH;
				}
				
				break;
			}
		}
		
		if(matches < minimum) {
			isMatching = false;
			
			if(matchInfo != MatchInfo.UNEXPECTED_CHARACTER_MATCH) {
				matchInfo = MatchInfo.UNEXPECTED_STRING_LENGTH;
			}
		}
		
		final
		Matcher.Builder matcher_Builder = new Matcher.Builder();
		matcher_Builder.setBeginIndex(newBeginIndex);
		matcher_Builder.setCurrentCharacterMatch(currentCharacterMatch);
		matcher_Builder.setEndIndex(newEndIndex);
		matcher_Builder.setMatchInfo(matchInfo);
		matcher_Builder.setMatchable(this);
		matcher_Builder.setMatching(isMatching);
		matcher_Builder.setSource(source);
		
		return matcher_Builder.build();
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
	 * Returns the source associated with this {@code Symbol} instance.
	 * 
	 * @return the source associated with this {@code Symbol} instance
	 */
	@Override
	public String getSource() {
		final StringBuilder stringBuilder = new StringBuilder();
		
		switch(this.character) {
			case '+':
			case '*':
			case '?':
			case '[':
			case ']':
			case '(':
			case ')':
			case '{':
			case '}':
			case '|':
			case '$':
			case '=':
				stringBuilder.append('\\');
				
				break;
			default:
				break;
		}
		
		stringBuilder.append(this.character);
		stringBuilder.append(this.repetition);
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Symbol} instance.
	 * 
	 * @return a {@code String} representation of this {@code Symbol} instance
	 */
	@Override
	public String toString() {
		return String.format("new Symbol(%s, %s)", Character.toString(getCharacter()), getRepetition());
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
		} else if(!Objects.equals(this.repetition, Symbol.class.cast(object).repetition)) {
			return false;
		} else if(this.character != Symbol.class.cast(object).character) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the {@code char} associated with this {@code Symbol} instance.
	 * 
	 * @return the {@code char} associated with this {@code Symbol} instance
	 */
	public char getCharacter() {
		return this.character;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getMaximumCharacterMatch() {
		return this.repetition.getMaximum();
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getMinimumCharacterMatch() {
		return this.repetition.getMinimum();
	}
	
	/**
	 * Returns a hash code for this {@code Symbol} instance.
	 * 
	 * @return a hash code for this {@code Symbol} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.repetition, Character.valueOf(this.character));
	}
}