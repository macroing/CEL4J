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
package org.macroing.cel4j.lexer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

final class JavaLexer extends Lexer {
	private static final String NAME_BINARY_INTEGER_LITERAL = "BinaryIntegerLiteral";
	private static final String NAME_BINARY_INTEGER_LITERAL_BINARY_NUMERAL = "BinaryIntegerLiteralBinaryNumeral";
	private static final String NAME_BINARY_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX = "BinaryIntegerLiteralIntegerTypeSuffix";
	private static final String NAME_BOOLEAN_LITERAL = "BooleanLiteral";
	private static final String NAME_CHARACTER_LITERAL = "CharacterLiteral";
	private static final String NAME_DECIMAL_FLOATING_POINT_LITERAL = "DecimalFloatingPointLiteral";
	private static final String NAME_DECIMAL_INTEGER_LITERAL = "DecimalIntegerLiteral";
	private static final String NAME_DECIMAL_INTEGER_LITERAL_DECIMAL_NUMERAL = "DecimalIntegerLiteralDecimalNumeral";
	private static final String NAME_DECIMAL_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX = "DecimalIntegerLiteralIntegerTypeSuffix";
	private static final String NAME_END_OF_LINE_COMMENT = "EndOfLineComment";
	private static final String NAME_HEXADECIMAL_FLOATING_POINT_LITERAL = "HexadecimalFloatingPointLiteral";
	private static final String NAME_HEXADECIMAL_FLOATING_POINT_LITERAL_BINARY_EXPONENT = "HexadecimalFloatingPointLiteralBinaryExponent";
	private static final String NAME_HEXADECIMAL_FLOATING_POINT_LITERAL_FLOAT_TYPE_SUFFIX = "HexadecimalFloatingPointLiteralFloatTypeSuffix";
	private static final String NAME_HEXADECIMAL_FLOATING_POINT_LITERAL_HEX_SIGNIFICAND = "HexadecimalFloatingPointLiteralHexSignificand";
	private static final String NAME_HEX_INTEGER_LITERAL = "HexIntegerLiteral";
	private static final String NAME_HEX_INTEGER_LITERAL_HEX_NUMERAL = "HexIntegerLiteralHexNumeral";
	private static final String NAME_HEX_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX = "HexIntegerLiteralIntegerTypeSuffix";
	private static final String NAME_IDENTIFIER = "Identifier";
	private static final String NAME_KEYWORD = "Keyword";
	private static final String NAME_NULL_LITERAL = "NullLiteral";
	private static final String NAME_OCTAL_INTEGER_LITERAL = "OctalIntegerLiteral";
	private static final String NAME_OCTAL_INTEGER_LITERAL_OCTAL_NUMERAL = "OctalIntegerLiteralOctalNumeral";
	private static final String NAME_OCTAL_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX = "OctalIntegerLiteralIntegerTypeSuffix";
	private static final String NAME_OPERATOR = "Operator";
	private static final String NAME_SEPARATOR = "Separator";
	private static final String NAME_STRING_LITERAL = "StringLiteral";
	private static final String NAME_TRADITIONAL_COMMENT = "TraditionalComment";
	private static final String NAME_WHITE_SPACE = "WhiteSpace";
	private static final String REGEX_BINARY_INTEGER_LITERAL = String.format("(?<%s>(?<%s>0(?:B|b)(?:0|1)(?:(?:0|1|_)*(?:0|1))?)(?<%s>L|l|))", NAME_BINARY_INTEGER_LITERAL, NAME_BINARY_INTEGER_LITERAL_BINARY_NUMERAL, NAME_BINARY_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX);
	private static final String REGEX_BOOLEAN_LITERAL = String.format("(?<%s>false|true)", NAME_BOOLEAN_LITERAL);
	private static final String REGEX_CHARACTER_LITERAL = String.format("(?<%s>'([^'\\\\]|\\\\([btnfr\"'\\\\0-7]|[0-7]{2}|[0-3][0-7]{2}|u+[0-9a-fA-F]{4}))')", NAME_CHARACTER_LITERAL);
	private static final String REGEX_DECIMAL_FLOATING_POINT_LITERAL = String.format("(?<%s>\\.[0-9]([0-9_]*[0-9])?([eE][+-]?[0-9]([0-9_]*[0-9])?)?[fFdD]?|[0-9]([0-9_]*[0-9])?\\.([0-9]([0-9_]*[0-9])?)?([eE][+-]?[0-9]([0-9_]*[0-9])?)?[fFdD]?|[0-9]([0-9_]*[0-9])?([eE][+-]?[0-9]([0-9_]*[0-9])?)[fFdD]?|[0-9]([0-9_]*[0-9])?([eE][+-]?[0-9]([0-9_]*[0-9])?)?[fFdD])", NAME_DECIMAL_FLOATING_POINT_LITERAL);
	private static final String REGEX_DECIMAL_INTEGER_LITERAL = String.format("(?<%s>(?<%s>0|[1-9](?:[0-9_]*[0-9])?)(?<%s>L|l|))", NAME_DECIMAL_INTEGER_LITERAL, NAME_DECIMAL_INTEGER_LITERAL_DECIMAL_NUMERAL, NAME_DECIMAL_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX);
	private static final String REGEX_END_OF_LINE_COMMENT = String.format("(?<%s>//.*(?=\\R|$))", NAME_END_OF_LINE_COMMENT);
	private static final String REGEX_HEXADECIMAL_FLOATING_POINT_LITERAL = String.format("(?<%s>(?<%s>0[xX](?:(?:[0-9a-fA-F](?:[0-9a-fA-F_]*[0-9a-fA-F])?)\\.?|(?:[0-9a-fA-F](?:[0-9a-fA-F_]*[0-9a-fA-F])?)?\\.(?:[0-9a-fA-F](?:[0-9a-fA-F]*[0-9a-fA-F])?))?)(?<%s>[pP][+-]?(?:[0-9](?:[0-9_]*[0-9])?))(?<%s>[fFdD]?))", NAME_HEXADECIMAL_FLOATING_POINT_LITERAL, NAME_HEXADECIMAL_FLOATING_POINT_LITERAL_HEX_SIGNIFICAND, NAME_HEXADECIMAL_FLOATING_POINT_LITERAL_BINARY_EXPONENT, NAME_HEXADECIMAL_FLOATING_POINT_LITERAL_FLOAT_TYPE_SUFFIX);
	private static final String REGEX_HEX_INTEGER_LITERAL = String.format("(?<%s>(?<%s>0(?:X|x)[0-9A-Fa-f](?:[0-9A-Fa-f_]*[0-9A-Fa-f])?)(?<%s>L|l|))", NAME_HEX_INTEGER_LITERAL, NAME_HEX_INTEGER_LITERAL_HEX_NUMERAL, NAME_HEX_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX);
	private static final String REGEX_IDENTIFIER = String.format("(?<%s>(?!(abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|false|finally|final|float|for|if|goto|implements|import|instanceof|interface|int|long|native|new|null|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throws|throw|transient|true|try|void|volatile|while)([^\\p{javaJavaIdentifierPart}]|$))\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)", NAME_IDENTIFIER);
	private static final String REGEX_KEYWORD = String.format("(?<%s>abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|finally|final|float|for|if|goto|implements|import|instanceof|interface|int|long|native|new|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throws|throw|transient|try|void|volatile|while)", NAME_KEYWORD);
	private static final String REGEX_NULL_LITERAL = String.format("(?<%s>null)", NAME_NULL_LITERAL);
	private static final String REGEX_OCTAL_INTEGER_LITERAL = String.format("(?<%s>(?<%s>0(?:(?:_+[0-7](?:[0-7_]*[0-7])?)|[0-7](?:[0-7_]*[0-7])?))(?<%s>L|l|))", NAME_OCTAL_INTEGER_LITERAL, NAME_OCTAL_INTEGER_LITERAL_OCTAL_NUMERAL, NAME_OCTAL_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX);
	private static final String REGEX_OPERATOR = String.format("(?<%s>==|=|>>>=|>>>|>>=|>>|>=|>|<<=|<<|<=|<|!=|!|~|\\?|:|&&|&=|&|\\|\\||\\|=|\\||\\+\\+|\\+=|\\+|--|-=|->|-|\\*=|\\*|/=|/|\\^=|\\^|%%=|%%)", NAME_OPERATOR);
	private static final String REGEX_SEPARATOR = String.format("(?<%s>\\(|\\)|\\{|\\}|\\[|\\]|;|,|\\.\\.\\.|\\.|@|::)", NAME_SEPARATOR);
	private static final String REGEX_STRING_LITERAL = String.format("(?<%s>\"([^\"\\\\]|\\\\([btnfr\"'\\\\0-7]|[0-7]{2}|[0-3][0-7]{2}|u+[0-9a-fA-F]{4}))*\")", NAME_STRING_LITERAL);
	private static final String REGEX_TRADITIONAL_COMMENT = String.format("(?s)(?<%s>/\\*((?!\\*/).)*\\*/)(?-s)", NAME_TRADITIONAL_COMMENT);
	private static final String REGEX_WHITE_SPACE = String.format("(?<%s>\\s)", NAME_WHITE_SPACE);
	private static final String REGEX_COMMENT = REGEX_END_OF_LINE_COMMENT + "|" + REGEX_TRADITIONAL_COMMENT;
	private static final String REGEX_FLOATING_POINT_LITERAL = REGEX_DECIMAL_FLOATING_POINT_LITERAL + "|" + REGEX_HEXADECIMAL_FLOATING_POINT_LITERAL;
	private static final String REGEX_INTEGER_LITERAL = REGEX_BINARY_INTEGER_LITERAL + "|" + REGEX_HEX_INTEGER_LITERAL + "|" + REGEX_OCTAL_INTEGER_LITERAL + "|" + REGEX_DECIMAL_INTEGER_LITERAL;
	private static final String REGEX_LITERAL = REGEX_BOOLEAN_LITERAL + "|" + REGEX_CHARACTER_LITERAL + "|" + REGEX_FLOATING_POINT_LITERAL + "|" + REGEX_INTEGER_LITERAL + "|" + REGEX_NULL_LITERAL + "|" + REGEX_STRING_LITERAL;
	private static final String REGEX_TOKEN = REGEX_IDENTIFIER + "|" + REGEX_KEYWORD + "|" + REGEX_LITERAL + "|" + REGEX_SEPARATOR + "|" + REGEX_OPERATOR;
	private static final String REGEX_INPUT_ELEMENT = REGEX_WHITE_SPACE + "|" + REGEX_COMMENT + "|" + REGEX_TOKEN;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Map<String, Function<Match, Token>> functions = doCreateFunctions();
	private final Pattern pattern = Pattern.compile(REGEX_INPUT_ELEMENT);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public JavaLexer() {
		
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
		
		functions.put(NAME_BINARY_INTEGER_LITERAL, match -> new Token(NAME_BINARY_INTEGER_LITERAL, match.group(NAME_BINARY_INTEGER_LITERAL_BINARY_NUMERAL) + match.group(NAME_BINARY_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX)));
		functions.put(NAME_BOOLEAN_LITERAL, match -> new Token(NAME_BOOLEAN_LITERAL, match.group()));
		functions.put(NAME_CHARACTER_LITERAL, match -> new Token(NAME_CHARACTER_LITERAL, match.group()));
		functions.put(NAME_DECIMAL_FLOATING_POINT_LITERAL, match -> new Token(NAME_DECIMAL_FLOATING_POINT_LITERAL, match.group()));
		functions.put(NAME_DECIMAL_INTEGER_LITERAL, match -> new Token(NAME_DECIMAL_INTEGER_LITERAL, match.group(NAME_DECIMAL_INTEGER_LITERAL_DECIMAL_NUMERAL) + match.group(NAME_DECIMAL_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX)));
		functions.put(NAME_END_OF_LINE_COMMENT, match -> new Token(NAME_END_OF_LINE_COMMENT, match.group(), true));
		functions.put(NAME_HEXADECIMAL_FLOATING_POINT_LITERAL, match -> new Token(NAME_HEXADECIMAL_FLOATING_POINT_LITERAL, match.group(NAME_HEXADECIMAL_FLOATING_POINT_LITERAL_HEX_SIGNIFICAND) + match.group(NAME_HEXADECIMAL_FLOATING_POINT_LITERAL_BINARY_EXPONENT) + match.group(NAME_HEXADECIMAL_FLOATING_POINT_LITERAL_FLOAT_TYPE_SUFFIX)));
		functions.put(NAME_HEX_INTEGER_LITERAL, match -> new Token(NAME_HEX_INTEGER_LITERAL, match.group(NAME_HEX_INTEGER_LITERAL_HEX_NUMERAL) + match.group(NAME_HEX_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX)));
		functions.put(NAME_IDENTIFIER, match -> new Token(NAME_IDENTIFIER, match.group()));
		functions.put(NAME_KEYWORD, match -> new Token(NAME_KEYWORD, match.group()));
		functions.put(NAME_NULL_LITERAL, match -> new Token(NAME_NULL_LITERAL, match.group()));
		functions.put(NAME_OCTAL_INTEGER_LITERAL, match -> new Token(NAME_OCTAL_INTEGER_LITERAL, match.group(NAME_OCTAL_INTEGER_LITERAL_OCTAL_NUMERAL) + match.group(NAME_OCTAL_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX)));
		functions.put(NAME_OPERATOR, match -> new Token(NAME_OPERATOR, match.group()));
		functions.put(NAME_SEPARATOR, match -> new Token(NAME_SEPARATOR, match.group()));
		functions.put(NAME_STRING_LITERAL, match -> new Token(NAME_STRING_LITERAL, match.group()));
		functions.put(NAME_TRADITIONAL_COMMENT, match -> new Token(NAME_TRADITIONAL_COMMENT, match.group(), true));
		functions.put(NAME_WHITE_SPACE, match -> new Token(NAME_WHITE_SPACE, match.group(), true));
		
		return functions;
	}
}