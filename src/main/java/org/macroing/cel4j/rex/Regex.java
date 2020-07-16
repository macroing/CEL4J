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
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code Regex} is a {@link Matcher} that can match Regex (Regular Expressions).
 * <p>
 * This class is immutable and thread-safe.
 * <p>
 * A {@code Regex} consists of a Regex literal. A Regex literal is similar to a {@code String} literal, with the exception that the double quotes ({@code "}) are replaced with forward slashes ({@code /}).
 * <p>
 * To use a {@code Regex} in Rex, consider the following example:
 * <pre>
 * {@code
 * /[a-zA-Z]+/
 * }
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Regex implements Matcher {
	/**
	 * A {@code Regex} that matches a {@code char} literal.
	 */
	public static final Regex CHARACTER_LITERAL = new Regex("'([^'\\\\]|\\\\([btnfr\"'\\\\0-7]|[0-7]{2}|[0-3][0-7]{2}|u+[0-9a-fA-F]{4}))'");
	
	/**
	 * A {@code Regex} that matches a comment.
	 */
	public static final Regex COMMENT = new Regex("//.*(?=\\R|$)|(?s)/\\*((?!\\*/).)*\\*/(?-s)");
	
	/**
	 * A {@code Regex} that matches a comment or a whitespace.
	 */
	public static final Regex COMMENT_OR_WHITESPACE = new Regex("//.*(?=\\R|$)|(?s)/\\*((?!\\*/).)*\\*/(?-s)|\\s");
	
	/**
	 * A {@code Regex} that matches an end-of-line comment.
	 */
	public static final Regex END_OF_LINE_COMMENT = new Regex("//.*(?=\\R|$)");
	
	/**
	 * A {@code Regex} that matches a Regex literal.
	 */
	public static final Regex REGEX_LITERAL = new Regex("/([^/\\\\]|\\\\([btnfr/'\\\\0-7]|[0-7]{2}|[0-3][0-7]{2}|u+[0-9a-fA-F]{4}))*/");
	
	/**
	 * A {@code Regex} that matches a {@code String} literal.
	 */
	public static final Regex STRING_LITERAL = new Regex("\"([^\"\\\\]|\\\\([btnfr\"'\\\\0-7]|[0-7]{2}|[0-3][0-7]{2}|u+[0-9a-fA-F]{4}))*\"");
	
	/**
	 * A {@code Regex} that matches a traditional comment.
	 */
	public static final Regex TRADITIONAL_COMMENT = new Regex("(?s)/\\*((?!\\*/).)*\\*/(?-s)");
	
	/**
	 * A {@code Regex} that matches a whitespace.
	 */
	public static final Regex WHITESPACE = new Regex("\\s");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Pattern pattern;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Regex} instance.
	 * <p>
	 * If {@code pattern} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pattern the {@code Pattern} associated with this {@code Regex} instance
	 * @throws NullPointerException thrown if, and only if, {@code pattern} is {@code null}
	 */
	public Regex(final Pattern pattern) {
		this.pattern = Objects.requireNonNull(pattern, "pattern == null");
	}
	
	/**
	 * Constructs a new {@code Regex} instance.
	 * <p>
	 * If {@code regex} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code regex} is malformed, a {@code PatternSyntaxException} will be thrown.
	 * 
	 * @param regex a Regex {@code String} to compile into a {@code Pattern}
	 * @throws NullPointerException thrown if, and only if, {@code regex} is {@code null}
	 * @throws PatternSyntaxException thrown if, and only if, {@code regex} is malformed
	 */
	public Regex(final String regex) {
		this.pattern = Pattern.compile(Objects.requireNonNull(regex, "regex == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code Regex} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * regex.write(new Document());
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
	 * Writes this {@code Regex} to {@code document}.
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
		document.linef("Regex \"%s\";", getPattern());
		
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
	 * regex.match(input, 0);
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
		
		final Pattern pattern = getPattern();
		
		java.util.regex.Matcher java_util_regex_Matcher = pattern.matcher(input);
		java_util_regex_Matcher.region(index, input.length());
		
		if(java_util_regex_Matcher.lookingAt()) {
			return new MatchResult(this, input, true, index, index + java_util_regex_Matcher.group().length());
		}
		
		return new MatchResult(this, input, false, index);
	}
	
	/**
	 * Returns the {@code Pattern} associated with this {@code Regex} instance.
	 * 
	 * @return the {@code Pattern} associated with this {@code Regex} instance
	 */
	public Pattern getPattern() {
		return this.pattern;
	}
	
	/**
	 * Returns the source code associated with this {@code Regex} instance.
	 * 
	 * @return the source code associated with this {@code Regex} instance
	 */
	@Override
	public String getSourceCode() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("/");
		stringBuilder.append(getPattern());
		stringBuilder.append("/");
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Regex} instance.
	 * 
	 * @return a {@code String} representation of this {@code Regex} instance
	 */
	@Override
	public String toString() {
		return String.format("new Regex(\"%s\")", getPattern());
	}
	
	/**
	 * Compares {@code object} to this {@code Regex} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Regex}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Regex} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Regex}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Regex)) {
			return false;
		} else if(!Objects.equals(getPattern(), Regex.class.cast(object).getPattern())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Regex} instance.
	 * 
	 * @return a hash code for this {@code Regex} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getPattern());
	}
}