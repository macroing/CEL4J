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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.util.CharPredicate;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code SymbolClass} is a {@link Matcher} that can match {@code String} values in different predefined formats.
 * <p>
 * This class is immutable and thread-safe.
 * <p>
 * A {@code SymbolClass} consists of a percent character ({@code %}) followed by a name and an optional {@link Repetition} instance.
 * <p>
 * To use a {@code SymbolClass} in Rex, consider the following example:
 * <pre>
 * {@code
 * %Digit+
 * }
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class SymbolClass implements Matcher {
	/**
	 * The name {@code "CharacterLiteral"} is used by a {@code SymbolClass} that matches using {@link Regex#CHARACTER_LITERAL}.
	 */
	public static final String NAME_CHARACTER_LITERAL = "CharacterLiteral";
	
	/**
	 * The name {@code "Comment"} is used by a {@code SymbolClass} that matches using {@link Regex#COMMENT}.
	 */
	public static final String NAME_COMMENT = "Comment";
	
	/**
	 * The name {@code "CommentOrWhitespace"} is used by a {@code SymbolClass} that matches using {@link Regex#COMMENT_OR_WHITESPACE}.
	 */
	public static final String NAME_COMMENT_OR_WHITESPACE = "CommentOrWhitespace";
	
	/**
	 * The name {@code "Digit"} is used by a {@code SymbolClass} that matches using {@code Character.isDigit(char)}.
	 */
	public static final String NAME_DIGIT = "Digit";
	
	/**
	 * The name {@code "EndOfLineComment"} is used by a {@code SymbolClass} that matches using {@link Regex#END_OF_LINE_COMMENT}.
	 */
	public static final String NAME_END_OF_LINE_COMMENT = "EndOfLineComment";
	
	/**
	 * The name {@code "JavaIdentifierPart"} is used by a {@code SymbolClass} that matches using {@code Character.isJavaIdentifierPart(char)}.
	 */
	public static final String NAME_JAVA_IDENTIFIER_PART = "JavaIdentifierPart";
	
	/**
	 * The name {@code "JavaIdentifierStart"} is used by a {@code SymbolClass} that matches using {@code Character.isJavaIdentifierStart(char)}.
	 */
	public static final String NAME_JAVA_IDENTIFIER_START = "JavaIdentifierStart";
	
	/**
	 * The name {@code "Letter"} is used by a {@code SymbolClass} that matches using {@code Character.isLetter(char)}.
	 */
	public static final String NAME_LETTER = "Letter";
	
	/**
	 * The name {@code "LetterOrDigit"} is used by a {@code SymbolClass} that matches using {@code Character.isLetterOrDigit(char)}.
	 */
	public static final String NAME_LETTER_OR_DIGIT = "LetterOrDigit";
	
	/**
	 * The name {@code "LowerCase"} is used by a {@code SymbolClass} that matches using {@code Character.isLowerCase(char)}.
	 */
	public static final String NAME_LOWER_CASE = "LowerCase";
	
	/**
	 * The name {@code "RegexLiteral"} is used by a {@code SymbolClass} that matches using {@link Regex#REGEX_LITERAL}.
	 */
	public static final String NAME_REGEX_LITERAL = "RegexLiteral";
	
	/**
	 * The name {@code "StringLiteral"} is used by a {@code SymbolClass} that matches using {@link Regex#STRING_LITERAL}.
	 */
	public static final String NAME_STRING_LITERAL = "StringLiteral";
	
	/**
	 * The name {@code "TraditionalComment"} is used by a {@code SymbolClass} that matches using {@link Regex#TRADITIONAL_COMMENT}.
	 */
	public static final String NAME_TRADITIONAL_COMMENT = "TraditionalComment";
	
	/**
	 * The name {@code "UnicodeIdentifierPart"} is used by a {@code SymbolClass} that matches using {@code Character.isUnicodeIdentifierPart(char)}.
	 */
	public static final String NAME_UNICODE_IDENTIFIER_PART = "UnicodeIdentifierPart";
	
	/**
	 * The name {@code "UnicodeIdentifierStart"} is used by a {@code SymbolClass} that matches using {@code Character.isUnicodeIdentifierStart(char)}.
	 */
	public static final String NAME_UNICODE_IDENTIFIER_START = "UnicodeIdentifierStart";
	
	/**
	 * The name {@code "UpperCase"} is used by a {@code SymbolClass} that matches using {@code Character.isUpperCase(char)}.
	 */
	public static final String NAME_UPPER_CASE = "UpperCase";
	
	/**
	 * The name {@code "Whitespace"} is used by a {@code SymbolClass} that matches using {@code Character.isWhitespace(char)}.
	 */
	public static final String NAME_WHITESPACE = "Whitespace";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String name;
	private final Repetition repetition;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code SymbolClass} instance.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new SymbolClass(name, Repetition.ONE);
	 * }
	 * </pre>
	 * 
	 * @param name the name associated with this {@code SymbolClass} instance
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public SymbolClass(final String name) {
		this(name, Repetition.ONE);
	}
	
	/**
	 * Constructs a new {@code SymbolClass} instance.
	 * <p>
	 * If either {@code name} or {@code repetition} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the name associated with this {@code SymbolClass} instance
	 * @param repetition the {@link Repetition} associated with this {@code SymbolClass} instance
	 * @throws NullPointerException thrown if, and only if, either {@code name} or {@code repetition} are {@code null}
	 */
	public SymbolClass(final String name, final Repetition repetition) {
		this.name = Objects.requireNonNull(name, "name == null");
		this.repetition = Objects.requireNonNull(repetition, "repetition == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code SymbolClass} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * symbolClass.write(new Document());
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
	 * Writes this {@code SymbolClass} to {@code document}.
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
		document.linef("SymbolClass \"%s\";", getName());
		
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
	 * symbolClass.match(input, 0);
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
		
		switch(getName()) {
			case NAME_CHARACTER_LITERAL:
				return doMatchRegex(input, index, Regex.CHARACTER_LITERAL);
			case NAME_COMMENT:
				return doMatchRegex(input, index, Regex.COMMENT);
			case NAME_COMMENT_OR_WHITESPACE:
				return doMatchRegex(input, index, Regex.COMMENT_OR_WHITESPACE);
			case NAME_DIGIT:
				return doMatchCharPredicate(input, index, character -> Character.isDigit(character));
			case NAME_END_OF_LINE_COMMENT:
				return doMatchRegex(input, index, Regex.END_OF_LINE_COMMENT);
			case NAME_JAVA_IDENTIFIER_PART:
				return doMatchCharPredicate(input, index, character -> Character.isJavaIdentifierPart(character));
			case NAME_JAVA_IDENTIFIER_START:
				return doMatchCharPredicate(input, index, character -> Character.isJavaIdentifierStart(character));
			case NAME_LETTER:
				return doMatchCharPredicate(input, index, character -> Character.isLetter(character));
			case NAME_LETTER_OR_DIGIT:
				return doMatchCharPredicate(input, index, character -> Character.isLetterOrDigit(character));
			case NAME_LOWER_CASE:
				return doMatchCharPredicate(input, index, character -> Character.isLowerCase(character));
			case NAME_REGEX_LITERAL:
				return doMatchRegex(input, index, Regex.REGEX_LITERAL);
			case NAME_STRING_LITERAL:
				return doMatchRegex(input, index, Regex.STRING_LITERAL);
			case NAME_TRADITIONAL_COMMENT:
				return doMatchRegex(input, index, Regex.TRADITIONAL_COMMENT);
			case NAME_UNICODE_IDENTIFIER_PART:
				return doMatchCharPredicate(input, index, character -> Character.isUnicodeIdentifierPart(character));
			case NAME_UNICODE_IDENTIFIER_START:
				return doMatchCharPredicate(input, index, character -> Character.isUnicodeIdentifierStart(character));
			case NAME_UPPER_CASE:
				return doMatchCharPredicate(input, index, character -> Character.isUpperCase(character));
			case NAME_WHITESPACE:
				return doMatchCharPredicate(input, index, character -> Character.isWhitespace(character));
			default:
				return doMatchDefault(input, index);
		}
	}
	
	/**
	 * Returns the {@link Repetition} associated with this {@code SymbolClass} instance.
	 * 
	 * @return the {@code Repetition} associated with this {@code SymbolClass} instance
	 */
	public Repetition getRepetition() {
		return this.repetition;
	}
	
	/**
	 * Returns the name associated with this {@code SymbolClass} instance.
	 * 
	 * @return the name associated with this {@code SymbolClass} instance
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the source code associated with this {@code SymbolClass} instance.
	 * 
	 * @return the source code associated with this {@code SymbolClass} instance
	 */
	@Override
	public String getSourceCode() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("%");
		stringBuilder.append(getName());
		stringBuilder.append(getRepetition().getSourceCode());
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code SymbolClass} instance.
	 * 
	 * @return a {@code String} representation of this {@code SymbolClass} instance
	 */
	@Override
	public String toString() {
		return String.format("new SymbolClass(\"%s\", %s)", getName(), getRepetition());
	}
	
	/**
	 * Compares {@code object} to this {@code SymbolClass} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code SymbolClass}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code SymbolClass} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code SymbolClass}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof SymbolClass)) {
			return false;
		} else if(!Objects.equals(getRepetition(), SymbolClass.class.cast(object).getRepetition())) {
			return false;
		} else if(!Objects.equals(getName(), SymbolClass.class.cast(object).getName())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code SymbolClass} instance.
	 * 
	 * @return a hash code for this {@code SymbolClass} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getRepetition(), getName());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private MatchResult doMatchDefault(final String input, final int index) {
		return new MatchResult(this, input, false, index);
	}
	
	private MatchResult doMatchCharPredicate(final String input, final int index, final CharPredicate charPredicate) {
		final Repetition repetition = getRepetition();
		
		final int minimumRepetition = repetition.getMinimum();
		final int maximumRepetition = repetition.getMaximum();
		
		int currentIndex = index;
		int currentRepetition = 0;
		
		int length = 0;
		
		for(int i = 1; i <= maximumRepetition && currentIndex < input.length(); i++) {
			if(charPredicate.test(input.charAt(currentIndex))) {
				currentIndex += 1;
				currentRepetition++;
				
				length += 1;
			} else {
				break;
			}
		}
		
		if(currentRepetition >= minimumRepetition) {
			return new MatchResult(this, input, true, index, index + length);
		}
		
		return new MatchResult(this, input, false, index);
	}
	
	private MatchResult doMatchRegex(final String input, final int index, final Regex regex) {
		final List<MatchResult> matchResults = new ArrayList<>();
		
		final Repetition repetition = getRepetition();
		
		final int minimumRepetition = repetition.getMinimum();
		final int maximumRepetition = repetition.getMaximum();
		
		int currentIndex = index;
		int currentRepetition = 0;
		
		int length = 0;
		
		for(int i = 1; i <= maximumRepetition && currentIndex < input.length(); i++) {
			final MatchResult currentMatchResult = regex.match(input, currentIndex);
			
			if(currentMatchResult.isMatching()) {
				currentIndex += currentMatchResult.getLength();
				currentRepetition++;
				
				length += currentMatchResult.getLength();
				
				matchResults.add(currentMatchResult);
			} else {
				break;
			}
		}
		
		if(currentRepetition >= minimumRepetition) {
			return new MatchResult(this, input, true, index, index + length, matchResults);
		}
		
		return new MatchResult(this, input, false, index);
	}
}