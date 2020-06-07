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
package org.macroing.cel4j.json;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A {@code JSONLexer} is a JSON lexer.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class JSONLexer {
	private static final String NAME_BOOLEAN_LITERAL = "BooleanLiteral";
	private static final String NAME_DECIMAL_FLOATING_POINT_LITERAL = "DecimalFloatingPointLiteral";
	private static final String NAME_DECIMAL_INTEGER_LITERAL = "DecimalIntegerLiteral";
	private static final String NAME_NULL_LITERAL = "NullLiteral";
	private static final String NAME_SEPARATOR = "Separator";
	private static final String NAME_STRING_LITERAL = "StringLiteral";
	private static final String NAME_WHITE_SPACE = "WhiteSpace";
	private static final String REGEX_BOOLEAN_LITERAL = String.format("(?<%s>false|true)", NAME_BOOLEAN_LITERAL);
	private static final String REGEX_DECIMAL_FLOATING_POINT_LITERAL = String.format("(?<%s>-?\\.[0-9]([0-9_]*[0-9])?([eE][+-]?[0-9]([0-9_]*[0-9])?)?[fFdD]?|-?[0-9]([0-9_]*[0-9])?\\.([0-9]([0-9_]*[0-9])?)?([eE][+-]?[0-9]([0-9_]*[0-9])?)?[fFdD]?|-?[0-9]([0-9_]*[0-9])?([eE][+-]?[0-9]([0-9_]*[0-9])?)[fFdD]?|-?[0-9]([0-9_]*[0-9])?([eE][+-]?[0-9]([0-9_]*[0-9])?)?[fFdD])", NAME_DECIMAL_FLOATING_POINT_LITERAL);
	private static final String REGEX_DECIMAL_INTEGER_LITERAL = String.format("(?<%s>-?(0|[1-9](?:[0-9_]*[0-9])?)(L|l|))", NAME_DECIMAL_INTEGER_LITERAL);
	private static final String REGEX_NULL_LITERAL = String.format("(?<%s>null)", NAME_NULL_LITERAL);
	private static final String REGEX_SEPARATOR = String.format("(?<%s>\\{|\\}|\\[|\\]|:|,)", NAME_SEPARATOR);
	private static final String REGEX_STRING_LITERAL = String.format("(?<%s>\"([^\"]|\\\\\")*?\")", NAME_STRING_LITERAL);
	private static final String REGEX_WHITE_SPACE = String.format("(?<%s>\\s)", NAME_WHITE_SPACE);
	private static final String REGEX_FLOATING_POINT_LITERAL = REGEX_DECIMAL_FLOATING_POINT_LITERAL;
	private static final String REGEX_INTEGER_LITERAL = REGEX_DECIMAL_INTEGER_LITERAL;
	private static final String REGEX_LITERAL = REGEX_BOOLEAN_LITERAL + "|" + REGEX_FLOATING_POINT_LITERAL + "|" + REGEX_INTEGER_LITERAL + "|" + REGEX_NULL_LITERAL + "|" + REGEX_STRING_LITERAL;
	private static final String REGEX_TOKEN = REGEX_LITERAL + "|" + REGEX_SEPARATOR;
	private static final String REGEX_INPUT_ELEMENT = REGEX_WHITE_SPACE + "|" + REGEX_TOKEN;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Map<String, Function<Matcher, JSONToken>> functions = doCreateFunctions();
	private final Pattern pattern = Pattern.compile(REGEX_INPUT_ELEMENT);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code JSONLexer} instance.
	 */
	public JSONLexer() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Performs the lexing process.
	 * <p>
	 * Returns a {@code List} with {@link JSONToken}s.
	 * <p>
	 * Calling this method is equivalent to calling {@code jSONLexer.lex(input, false)}.
	 * <p>
	 * If {@code input} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an error occurs while performing the lexing process, a {@link JSONLexerException} will be thrown.
	 * 
	 * @param input the input
	 * @return a {@code List} with {@code JSONToken}s
	 * @throws JSONLexerException thrown if, and only if, an error occurs while performing the lexing process
	 * @throws NullPointerException thrown if, and only if, {@code input} is {@code null}
	 */
	public List<JSONToken> lex(final String input) {
		return lex(input, false);
	}
	
	/**
	 * Performs the lexing process.
	 * <p>
	 * Returns a {@code List} with {@link JSONToken}s.
	 * <p>
	 * If {@code isSkippingSkippables} is {@code true}, all {@code JSONToken}s that have an {@code isSkippable()} method that returns {@code true} will be skipped. That is, they will not be added to the returned
	 * {@code List}.
	 * <p>
	 * If {@code input} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an error occurs while performing the lexing process, a {@link JSONLexerException} will be thrown.
	 * 
	 * @param input the input
	 * @param isSkippingSkippables {@code true} if, and only if, all {@code JSONToken}s that have an {@code isSkippable()} method that returns {@code true} should be skipped, {@code false} otherwise
	 * @return a {@code List} with {@code JSONToken}s
	 * @throws JSONLexerException thrown if, and only if, an error occurs while performing the lexing process
	 * @throws NullPointerException thrown if, and only if, {@code input} is {@code null}
	 */
	public List<JSONToken> lex(final String input, final boolean isSkippingSkippables) {
		final List<JSONToken> tokens = new ArrayList<>();
		
		final Pattern pattern = this.pattern;
		
		final Matcher matcher = pattern.matcher(Objects.requireNonNull(input, "input == null"));
		
		final Map<String, Function<Matcher, JSONToken>> functions = this.functions;
		
		int index = 0;
		
		while(matcher.lookingAt()) {
			for(final Entry<String, Function<Matcher, JSONToken>> entry : functions.entrySet()) {
				final String name = entry.getKey();
				final String group = matcher.group(name);
				
				if(group != null) {
					final Function<Matcher, JSONToken> function = entry.getValue();
					
					try {
						final JSONToken token = function.apply(matcher);
						
						if(token == null) {
							throw new JSONLexerException(String.format("JSONToken at index %s may no be null: '%s'", Integer.toString(index), input.substring(index, index + 1)));
						}
						
						if(!isSkippingSkippables || !token.isSkippable()) {
							tokens.add(token);
						}
						
						break;
					} catch(final IllegalArgumentException | NullPointerException e) {
						throw new JSONLexerException(e);
					}
				}
			}
			
			index = matcher.end();
			
			matcher.region(matcher.end(), matcher.regionEnd());
		}
		
		if(!matcher.hitEnd()) {
			throw new JSONLexerException(String.format("Illegal input found at index %s: '%s'", Integer.toString(index), input.substring(index, index + 10)));
		}
		
		return tokens;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Map<String, Function<Matcher, JSONToken>> doCreateFunctions() {
		final Map<String, Function<Matcher, JSONToken>> functions = new LinkedHashMap<>();
		
		functions.put(NAME_BOOLEAN_LITERAL, match -> new JSONToken(NAME_BOOLEAN_LITERAL, match.group()));
		functions.put(NAME_DECIMAL_FLOATING_POINT_LITERAL, match -> new JSONToken(NAME_DECIMAL_FLOATING_POINT_LITERAL, match.group()));
		functions.put(NAME_DECIMAL_INTEGER_LITERAL, match -> new JSONToken(NAME_DECIMAL_INTEGER_LITERAL, match.group()));
		functions.put(NAME_NULL_LITERAL, match -> new JSONToken(NAME_NULL_LITERAL, match.group()));
		functions.put(NAME_SEPARATOR, match -> new JSONToken(NAME_SEPARATOR, match.group()));
		functions.put(NAME_STRING_LITERAL, match -> new JSONToken(NAME_STRING_LITERAL, match.group()));
		functions.put(NAME_WHITE_SPACE, match -> new JSONToken(NAME_WHITE_SPACE, match.group(), true));
		
		return functions;
	}
}