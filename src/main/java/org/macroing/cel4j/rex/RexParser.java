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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.lexer.Token;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.util.Strings;

final class RexParser {
	private static final RexLexer REX_LEXER = new RexLexer();
	private static final Token ALTERNATION_SEPARATOR = new Token(RexLexer.NAME_SEPARATOR, "|");
	private static final Token CONCATENATION_SEPARATOR_0 = new Token(RexLexer.NAME_SEPARATOR, "&");
	private static final Token CONCATENATION_SEPARATOR_1 = new Token(RexLexer.NAME_SEPARATOR, ",");
	private static final Token CONCATENATION_SEPARATOR_2 = new Token(RexLexer.NAME_SEPARATOR, ";");
	private static final Token GROUP_REFERENCE_DEFINITION_ASSIGNMENT = new Token(RexLexer.NAME_OPERATOR, "=");
	private static final Token GROUP_REFERENCE_OR_GROUP_REFERENCE_DEFINITION_SEPARATOR_L = new Token(RexLexer.NAME_SEPARATOR, "<");
	private static final Token GROUP_REFERENCE_OR_GROUP_REFERENCE_DEFINITION_SEPARATOR_R = new Token(RexLexer.NAME_SEPARATOR, ">");
	private static final Token GROUP_SEPARATOR_L = new Token(RexLexer.NAME_SEPARATOR, "(");
	private static final Token GROUP_SEPARATOR_R = new Token(RexLexer.NAME_SEPARATOR, ")");
	private static final Token REPETITION_ANY = new Token(RexLexer.NAME_OPERATOR, "*");
	private static final Token REPETITION_ONE_OR_MORE = new Token(RexLexer.NAME_OPERATOR, "+");
	private static final Token REPETITION_ZERO_OR_ONE = new Token(RexLexer.NAME_OPERATOR, "?");
	private static final Token SYMBOL_CLASS_SEPARATOR_L = new Token(RexLexer.NAME_OPERATOR, "%");
	private static final Token SYMBOL_CLASS_SEPARATOR_R = new Token(RexLexer.NAME_OPERATOR, "%");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private RexParser() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Expression parse(final File file) {
		return parse(Strings.toString(Objects.requireNonNull(file, "file == null")));
	}
	
	public static Expression parse(final String input) {
		return doUpdateGroupReferences(new Expression(doParseAlternation(REX_LEXER.lex(Objects.requireNonNull(input, "input == null"), true), new int[] {-1})));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Alternation doParseAlternation(final List<Token> tokens, final int[] index) {
		final List<Concatenation> concatenations = new ArrayList<>();
		
		do {
			concatenations.add(doParseConcatenation(tokens, index));
		} while(doGetNextToken(tokens, index, ALTERNATION_SEPARATOR) && doHasNextTokenOrThrow(tokens, index, "Illegal Alternation!"));
		
		return new Alternation(concatenations);
	}
	
	private static Concatenation doParseConcatenation(final List<Token> tokens, final int[] index) {
		final List<Matcher> matchers = new ArrayList<>();
		
		do {
			matchers.add(doParseMatcher(tokens, index));
		} while(doGetNextToken(tokens, index, CONCATENATION_SEPARATOR_0, CONCATENATION_SEPARATOR_1, CONCATENATION_SEPARATOR_2) && doHasNextTokenOrThrow(tokens, index, "Illegal Concatenation!"));
		
		return new Concatenation(matchers);
	}
	
	private static Expression doUpdateGroupReferences(final Expression expression) {
		final List<Group> groups = NodeFilter.filter(expression, NodeFilter.any(), Group.class);
		final List<GroupReference> groupReferences = NodeFilter.filter(expression, NodeFilter.any(), GroupReference.class);
		final List<GroupReferenceDefinition> groupReferenceDefinitions = NodeFilter.filter(expression, NodeFilter.any(), GroupReferenceDefinition.class);
		
		if(groups.size() > 0 && groupReferences.size() > 0) {
			for(final GroupReference groupReference : groupReferences) {
				if(!groupReference.hasGroup()) {
					final String name = groupReference.getName();
					
					if(name.matches("[0-9]+")) {
						final int index = Integer.parseInt(name);
						
						if(index >= 0 && index < groups.size()) {
							groupReference.setGroup(groups.get(index));
							
							break;
						}
						
						throw new IllegalArgumentException("No matching Group could be found for the GroupReference '%" + groupReference.getSourceCode() + "'.");
					}
					
					for(final GroupReferenceDefinition groupReferenceDefinition : groupReferenceDefinitions) {
						if(groupReferenceDefinition.getName().equals(name)) {
							groupReference.setGroup(groupReferenceDefinition.getGroup());
						}
					}
					
					if(!groupReference.hasGroup()) {
						throw new IllegalArgumentException("No matching Group could be found for the GroupReference '%" + groupReference.getSourceCode() + "'.");
					}
				}
			}
		}
		
		return expression;
	}
	
	private static Group doParseGroup(final List<Token> tokens, final int[] index) {
		if(doGetNextToken(tokens, index, GROUP_SEPARATOR_L)) {
			final Alternation alternation = doParseAlternation(tokens, index);
			
			if(doGetNextTokenOrThrow(tokens, index, GROUP_SEPARATOR_R, "Illegal Group!")) {
				final Repetition repetition = doParseRepetition(tokens, index);
				
				return new Group(alternation, repetition);
			}
		}
		
		throw new IllegalArgumentException("Illegal Group!");
	}
	
	private static Matcher doParseMatcher(final List<Token> tokens, final int[] index) {
		if(doGetNextToken(tokens, index, GROUP_REFERENCE_OR_GROUP_REFERENCE_DEFINITION_SEPARATOR_L)) {
			if(doGetNextToken(tokens, index, RexLexer.NAME_DECIMAL_INTEGER_LITERAL)) {
				final String name = tokens.get(index[0]).getText();
				
				if(doGetNextTokenOrThrow(tokens, index, GROUP_REFERENCE_OR_GROUP_REFERENCE_DEFINITION_SEPARATOR_R, "Illegal GroupReference!")) {
					final Repetition repetition = doParseRepetition(tokens, index);
					
					return new GroupReference(name, repetition);
				}
			} else if(doGetNextTokenOrThrow(tokens, index, RexLexer.NAME_IDENTIFIER, "Illegal GroupReference or GroupReferenceDefinition!")) {
				final String name = tokens.get(index[0]).getText();
				
				if(doGetNextTokenOrThrow(tokens, index, GROUP_REFERENCE_OR_GROUP_REFERENCE_DEFINITION_SEPARATOR_R, "Illegal GroupReference or GroupReferenceDefinition!")) {
					if(doGetNextToken(tokens, index, GROUP_REFERENCE_DEFINITION_ASSIGNMENT)) {
						final Group group = doParseGroup(tokens, index);
						
						return new GroupReferenceDefinition(name, group);
					}
					
					final Repetition repetition = doParseRepetition(tokens, index);
					
					return new GroupReference(name, repetition);
				}
			}
			
			throw new IllegalArgumentException("Illegal GroupReference or GroupReferenceDefinition!");
		} else if(doHasNextToken(tokens, index, GROUP_SEPARATOR_L)) {
			return doParseGroup(tokens, index);
		} else if(doGetNextToken(tokens, index, RexLexer.NAME_CHARACTER_LITERAL)) {
			return new Symbol(tokens.get(index[0]).getText(), doParseRepetition(tokens, index));
		} else if(doGetNextToken(tokens, index, RexLexer.NAME_REGEX_LITERAL)) {
			return new Regex(tokens.get(index[0]).getText());
		} else if(doGetNextToken(tokens, index, RexLexer.NAME_STRING_LITERAL)) {
			return new Symbol(tokens.get(index[0]).getText(), doParseRepetition(tokens, index));
		} else if(doGetNextToken(tokens, index, SYMBOL_CLASS_SEPARATOR_L)) {
			if(doGetNextTokenOrThrow(tokens, index, RexLexer.NAME_IDENTIFIER, "Illegal SymbolClass!")) {
				final String name = tokens.get(index[0]).getText();
				
				if(doGetNextTokenOrThrow(tokens, index, SYMBOL_CLASS_SEPARATOR_R, "Illegal SymbolClass!")) {
					final Repetition repetition = doParseRepetition(tokens, index);
					
					return new SymbolClass(name, repetition);
				}
			}
			
			throw new IllegalArgumentException("Illegal SymbolClass!");
		} else {
			throw new IllegalArgumentException("Illegal Matcher!");
		}
	}
	
	private static Repetition doParseRepetition(final List<Token> tokens, final int[] index) {
		if(doGetNextToken(tokens, index, REPETITION_ANY)) {
			return Repetition.ANY;
		} else if(doGetNextToken(tokens, index, REPETITION_ONE_OR_MORE)) {
			return Repetition.ONE_OR_MORE;
		} else if(doGetNextToken(tokens, index, REPETITION_ZERO_OR_ONE)) {
			return Repetition.ZERO_OR_ONE;
		} else {
			return Repetition.ONE;
		}
	}
	
	private static boolean doGetNextToken(final List<Token> tokens, final int[] index, final String name) {
		if(doHasNextToken(tokens, index, name)) {
			index[0]++;
			
			return true;
		}
		
		return false;
	}
	
	private static boolean doGetNextToken(final List<Token> tokens, final int[] index, final Token... tokenAlternatives) {
		if(doHasNextToken(tokens, index, tokenAlternatives)) {
			index[0]++;
			
			return true;
		}
		
		return false;
	}
	
	private static boolean doGetNextTokenOrThrow(final List<Token> tokens, final int[] index, final String name, final String message) {
		if(doGetNextToken(tokens, index, name)) {
			return true;
		}
		
		throw new IllegalArgumentException(message);
	}
	
	private static boolean doGetNextTokenOrThrow(final List<Token> tokens, final int[] index, final Token token, final String message) {
		if(doGetNextToken(tokens, index, token)) {
			return true;
		}
		
		throw new IllegalArgumentException(message);
	}
	
	private static boolean doHasNextToken(final List<Token> tokens, final int[] index) {
		return index[0] + 1 < tokens.size();
	}
	
	private static boolean doHasNextToken(final List<Token> tokens, final int[] index, final String name) {
		return index[0] + 1 < tokens.size() && tokens.get(index[0] + 1).getName().equals(name);
	}
	
	private static boolean doHasNextToken(final List<Token> tokens, final int[] index, final Token... tokenAlternatives) {
		if(index[0] + 1 < tokens.size()) {
			final Token token = tokens.get(index[0] + 1);
			
			for(final Token tokenAlternative : tokenAlternatives) {
				if(token.equals(tokenAlternative)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private static boolean doHasNextTokenOrThrow(final List<Token> tokens, final int[] index, final String message) {
		if(doHasNextToken(tokens, index)) {
			return true;
		}
		
		throw new IllegalArgumentException(message);
	}
}