/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
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
package org.macroing.cel4j.artifact;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Matchers {
	public static final String NAME_EXTENDS_STATEMENT;
	public static final String NAME_IMPORT_STATEMENT;
	public static final String NAME_PACKAGE_STATEMENT;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final Pattern PATTERN_EXTENDS_STATEMENT;
	private static final Pattern PATTERN_IMPORT_STATEMENT;
	private static final Pattern PATTERN_PACKAGE_STATEMENT;
	private static final Pattern PATTERN_SUBSTITUTION_VARIABLE;
	private static final Pattern PATTERN_WHITE_SPACE;
	private static final String REGEX_EXTENDS_STATEMENT;
	private static final String REGEX_IDENTIFIER;
	private static final String REGEX_IMPORT_STATEMENT;
	private static final String REGEX_PACKAGE_STATEMENT;
	private static final String REGEX_SUBSTITUTION_VARIABLE;
	private static final String REGEX_WHITE_SPACE;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Matchers() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static {
		NAME_EXTENDS_STATEMENT = "ExtendsStatement";
		NAME_IMPORT_STATEMENT = "ImportStatement";
		NAME_PACKAGE_STATEMENT = "PackageStatement";
		
		REGEX_IDENTIFIER = "(?!(abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|false|finally|final|float|for|if|goto|implements|import|instanceof|interface|int|long|native|new|null|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throws|throw|transient|true|try|void|volatile|while)([^\\p{javaJavaIdentifierPart}]|$))\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*";
		REGEX_EXTENDS_STATEMENT = String.format("extends\\s+(?<%s>%s(\\s*\\.\\s*%s)*?)\\s*;", NAME_EXTENDS_STATEMENT, REGEX_IDENTIFIER, REGEX_IDENTIFIER);
		REGEX_IMPORT_STATEMENT = String.format("(?<%s>import(\\s+static)?\\s+%s(\\s*\\.\\s*%s)*?(\\s*\\.\\s*\\*)?\\s*;)", NAME_IMPORT_STATEMENT, REGEX_IDENTIFIER, REGEX_IDENTIFIER);
		REGEX_PACKAGE_STATEMENT = String.format("package\\s+(?<%s>%s(\\s*\\.\\s*%s)*?)\\s*;", NAME_PACKAGE_STATEMENT, REGEX_IDENTIFIER, REGEX_IDENTIFIER);
		REGEX_SUBSTITUTION_VARIABLE = String.format("\\$(%s)", REGEX_IDENTIFIER);
		REGEX_WHITE_SPACE = "\\s+(?=((\\\\[\\\\\"]|[^\\\\\"])*\"(\\\\[\\\\\"]|[^\\\\\"])*\")*(\\\\[\\\\\"]|[^\\\\\"])*$)";
		
		PATTERN_EXTENDS_STATEMENT = Pattern.compile(REGEX_EXTENDS_STATEMENT);
		PATTERN_IMPORT_STATEMENT = Pattern.compile(REGEX_IMPORT_STATEMENT);
		PATTERN_PACKAGE_STATEMENT = Pattern.compile(REGEX_PACKAGE_STATEMENT);
		PATTERN_SUBSTITUTION_VARIABLE = Pattern.compile(REGEX_SUBSTITUTION_VARIABLE);
		PATTERN_WHITE_SPACE = Pattern.compile(REGEX_WHITE_SPACE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Matcher newExtendsStatementMatcher(final CharSequence input) {
		return PATTERN_EXTENDS_STATEMENT.matcher(input);
	}
	
	public static Matcher newImportStatementMatcher(final CharSequence input) {
		return PATTERN_IMPORT_STATEMENT.matcher(input);
	}
	
	public static Matcher newPackageStatementMatcher(final CharSequence input) {
		return PATTERN_PACKAGE_STATEMENT.matcher(input);
	}
	
	public static Matcher newSubstitutionVariableMatcher(final CharSequence input) {
		return PATTERN_SUBSTITUTION_VARIABLE.matcher(input);
	}
	
	public static Matcher newWhiteSpaceMatcher(final CharSequence input) {
		return PATTERN_WHITE_SPACE.matcher(input);
	}
}