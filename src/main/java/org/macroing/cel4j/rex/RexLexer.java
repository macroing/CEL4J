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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.macroing.cel4j.lexer.Lexer;
import org.macroing.cel4j.lexer.Token;

final class RexLexer extends Lexer {
	public static final String NAME_CHARACTER_LITERAL = "CharacterLiteral";
	public static final String NAME_DECIMAL_INTEGER_LITERAL = "DecimalIntegerLiteral";
	public static final String NAME_END_OF_LINE_COMMENT = "EndOfLineComment";
	public static final String NAME_IDENTIFIER = "Identifier";
	public static final String NAME_OPERATOR = "Operator";
	public static final String NAME_REGEX_LITERAL = "RegexLiteral";
	public static final String NAME_SEPARATOR = "Separator";
	public static final String NAME_STRING_LITERAL = "StringLiteral";
	public static final String NAME_TRADITIONAL_COMMENT = "TraditionalComment";
	public static final String NAME_WHITESPACE = "Whitespace";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String REGEX_CHARACTER_LITERAL = String.format("'(?<%s>([^'\\\\]|\\\\([btnfr\"'\\\\0-7]|[0-7]{2}|[0-3][0-7]{2}|u+[0-9a-fA-F]{4})))'", NAME_CHARACTER_LITERAL);
	private static final String REGEX_DECIMAL_INTEGER_LITERAL = String.format("(?<%s>0|[1-9][0-9]*)", NAME_DECIMAL_INTEGER_LITERAL);
	private static final String REGEX_END_OF_LINE_COMMENT = String.format("(?<%s>//.*(?=\\R|$))", NAME_END_OF_LINE_COMMENT);
	private static final String REGEX_IDENTIFIER = String.format("(?<%s>\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)", NAME_IDENTIFIER);
	private static final String REGEX_OPERATOR = String.format("(?<%s>%%|\\*|\\+|=|\\?)", NAME_OPERATOR);
	private static final String REGEX_REGEX_LITERAL = String.format("/(?<%s>([^/\\\\]|\\\\([btnfr/'\\\\0-7]|[0-7]{2}|[0-3][0-7]{2}|u+[0-9a-fA-F]{4}))*)/", NAME_REGEX_LITERAL);
	private static final String REGEX_SEPARATOR = String.format("(?<%s>&|\\(|\\)|,|<|>|\\[|\\]|\\{|\\||\\})", NAME_SEPARATOR);
	private static final String REGEX_STRING_LITERAL = String.format("\"(?<%s>([^\"\\\\]|\\\\([btnfr\"'\\\\0-7]|[0-7]{2}|[0-3][0-7]{2}|u+[0-9a-fA-F]{4}))*)\"", NAME_STRING_LITERAL);
	private static final String REGEX_TRADITIONAL_COMMENT = String.format("(?s)(?<%s>/\\*((?!\\*/).)*\\*/)(?-s)", NAME_TRADITIONAL_COMMENT);
	private static final String REGEX_WHITESPACE = String.format("(?<%s>\\s)", NAME_WHITESPACE);
	private static final String REGEX_COMMENT = String.format("%s|%s", REGEX_END_OF_LINE_COMMENT, REGEX_TRADITIONAL_COMMENT);
	private static final String REGEX_INTEGER_LITERAL = REGEX_DECIMAL_INTEGER_LITERAL;
	private static final String REGEX_LITERAL = String.format("%s|%s|%s|%s", REGEX_CHARACTER_LITERAL, REGEX_INTEGER_LITERAL, REGEX_REGEX_LITERAL, REGEX_STRING_LITERAL);
	private static final String REGEX_TOKEN = String.format("%s|%s|%s|%s", REGEX_IDENTIFIER, REGEX_LITERAL, REGEX_SEPARATOR, REGEX_OPERATOR);
	private static final String REGEX_INPUT_ELEMENT = String.format("%s|%s|%s", REGEX_WHITESPACE, REGEX_COMMENT, REGEX_TOKEN);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Map<String, Function<Match, Token>> functions;
	private final Pattern pattern;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public RexLexer() {
		this.functions = doCreateFunctions();
		this.pattern = Pattern.compile(REGEX_INPUT_ELEMENT);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	protected Map<String, Function<Match, Token>> functions() {
		return new LinkedHashMap<>(this.functions);
	}
	
	@Override
	protected Pattern pattern() {
		return this.pattern;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Map<String, Function<Match, Token>> doCreateFunctions() {
		final Map<String, Function<Match, Token>> functions = new LinkedHashMap<>();
		
		functions.put(NAME_CHARACTER_LITERAL, match -> new Token(NAME_CHARACTER_LITERAL, match.group(NAME_CHARACTER_LITERAL)));
		functions.put(NAME_DECIMAL_INTEGER_LITERAL, match -> new Token(NAME_DECIMAL_INTEGER_LITERAL, match.group()));
		functions.put(NAME_END_OF_LINE_COMMENT, match -> new Token(NAME_END_OF_LINE_COMMENT, match.group(), true));
		functions.put(NAME_IDENTIFIER, match -> new Token(NAME_IDENTIFIER, match.group()));
		functions.put(NAME_OPERATOR, match -> new Token(NAME_OPERATOR, match.group()));
		functions.put(NAME_REGEX_LITERAL, match -> new Token(NAME_REGEX_LITERAL, match.group(NAME_REGEX_LITERAL)));
		functions.put(NAME_SEPARATOR, match -> new Token(NAME_SEPARATOR, match.group()));
		functions.put(NAME_STRING_LITERAL, match -> new Token(NAME_STRING_LITERAL, match.group(NAME_STRING_LITERAL)));
		functions.put(NAME_TRADITIONAL_COMMENT, match -> new Token(NAME_TRADITIONAL_COMMENT, match.group(), true));
		functions.put(NAME_WHITESPACE, match -> new Token(NAME_WHITESPACE, match.group(), true));
		
		return functions;
	}
}