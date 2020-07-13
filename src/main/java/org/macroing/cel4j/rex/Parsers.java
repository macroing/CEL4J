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
import java.util.regex.Pattern;

import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.scanner.TextScanner;

final class Parsers {
	private static final Pattern PATTERN_REPETITION = Pattern.compile("\\{(\\d*),?(\\d*)\\}");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Parsers() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Expression parseExpression(final TextScanner textScanner) {
		final Alternation alternation = doParseAlternation(textScanner);
		
		if(alternation != null) {
			return doUpdateGroupReferences(new Expression(alternation));
		}
		
		throw new IllegalArgumentException(String.format("Illegal Expression: %s", textScanner));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Alternation doParseAlternation(final TextScanner textScanner) {
		final List<Concatenation> concatenations = new ArrayList<>();
		
		final int indexAtBeginningInclusive = textScanner.getIndexAtBeginningInclusive();
		final int indexAtEndExclusive = textScanner.getIndexAtEndExclusive();
		
		do {
			final Concatenation concatenation = doParseConcatenation(textScanner);
			
			if(concatenation != null) {
				concatenations.add(concatenation);
			} else {
				textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
				
				throw new IllegalArgumentException(String.format("Illegal Alternation: %s", textScanner));
			}
		} while(textScanner.nextCharacter('|') && textScanner.consume());
		
		return new Alternation(concatenations);
	}
	
	private static Concatenation doParseConcatenation(final TextScanner textScanner) {
		final List<Matcher> matchables = new ArrayList<>();
		
		final int indexAtBeginningInclusive = textScanner.getIndexAtBeginningInclusive();
		final int indexAtEndExclusive = textScanner.getIndexAtEndExclusive();
		
		int count = 0;
		
		loop:
		while(true) {
			switch(textScanner.currentCharacter()) {
				case TextScanner.EOF: {
					break loop;
				}
				case ' ': {
					textScanner.nextCharacter();
					textScanner.consume();
					
					continue;
				}
				case '&': {
					textScanner.nextCharacter();
					textScanner.consume();
					
					continue;
				}
				case '(': {
					final Group group = doParseGroup(textScanner);
					
					if(group != null) {
						count++;
						
						matchables.add(group);
						
						continue;
					}
					
					textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
					
					return null;
				}
				case ')': {
					break loop;
				}
				case '|': {
					break loop;
				}
				case '%': {
					final GroupReferenceDefinition groupReferenceDefinition = doParseGroupReferenceDefinition(textScanner);
					
					if(groupReferenceDefinition != null) {
						count++;
						
						matchables.add(groupReferenceDefinition);
						
						continue;
					}
					
					final GroupReference groupReference = doParseGroupReference(textScanner);
					
					if(groupReference != null) {
						count++;
						
						matchables.add(groupReference);
						
						continue;
					}
					
					textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
					
					throw new IllegalArgumentException(String.format("Illegal Concatenation: %s", textScanner));
				}
				default: {
					final Symbol symbol = doParseSymbol(textScanner);
					
					if(symbol != null) {
						count++;
						
						matchables.add(symbol);
						
						continue;
					}
					
					break loop;
				}
			}
		}
		
		if(count > 0) {
			return new Concatenation(matchables);
		}
		
		textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
		
		throw new IllegalArgumentException(String.format("Illegal Concatenation: %s", textScanner));
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
						
						throw new IllegalArgumentException("No matching Group could be found for the GroupReference '%" + groupReference.getSource() + "'.");
					}
					
					for(final GroupReferenceDefinition groupReferenceDefinition : groupReferenceDefinitions) {
						if(groupReferenceDefinition.getName().equals(name)) {
							groupReference.setGroup(groupReferenceDefinition.getGroup());
						}
					}
					
					if(!groupReference.hasGroup()) {
						throw new IllegalArgumentException("No matching Group could be found for the GroupReference '%" + groupReference.getSource() + "'.");
					}
				}
			}
		}
		
		return expression;
	}
	
	private static Group doParseGroup(final TextScanner textScanner) {
		char character = textScanner.currentCharacter();
		
		if(character == '(') {
			final int indexAtBeginningInclusive = textScanner.getIndexAtBeginningInclusive();
			final int indexAtEndExclusive = textScanner.getIndexAtEndExclusive();
			
			textScanner.nextCharacter();
			textScanner.consume();
			
			final Alternation alternation = doParseAlternation(textScanner);
			
			if(alternation != null) {
				character = textScanner.currentCharacter();
				
				if(character == ')') {
					textScanner.nextCharacter();
					textScanner.consume();
					
					return new Group(alternation, doParseRepetition(textScanner));
				}
			} else {
				textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
			}
		}
		
		return null;
	}
	
	private static GroupReference doParseGroupReference(final TextScanner textScanner) {
		char character = textScanner.currentCharacter();
		
		if(character == '%') {
			final int indexAtBeginningInclusive = textScanner.getIndexAtBeginningInclusive();
			final int indexAtEndExclusive = textScanner.getIndexAtEndExclusive();
			
			textScanner.nextCharacter();
			textScanner.consume();
			
			final StringBuilder stringBuilder = new StringBuilder();
			
			character = textScanner.currentCharacter();
			
			if(character == TextScanner.EOF) {
				textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
				
				return null;
			} else if(Character.isDigit(character)) {
				while(Character.isDigit(character) && character != TextScanner.EOF) {
					stringBuilder.append(character);
					
					textScanner.nextCharacter();
					textScanner.consume();
					
					character = textScanner.currentCharacter();
				}
				
				return new GroupReference(stringBuilder.toString(), doParseRepetition(textScanner));
			} else if(Character.isJavaIdentifierStart(character)) {
				stringBuilder.append(character);
				
				textScanner.nextCharacter();
				textScanner.consume();
				
				character = textScanner.currentCharacter();
				
				while(Character.isJavaIdentifierPart(character) && character != TextScanner.EOF) {
					stringBuilder.append(character);
					
					textScanner.nextCharacter();
					textScanner.consume();
					
					character = textScanner.currentCharacter();
				}
				
				return new GroupReference(stringBuilder.toString(), doParseRepetition(textScanner));
			} else {
				textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
			}
		}
		
		return null;
	}
	
	private static GroupReferenceDefinition doParseGroupReferenceDefinition(final TextScanner textScanner) {
		char character = textScanner.currentCharacter();
		
		if(character == '%') {
			final int indexAtBeginningInclusive = textScanner.getIndexAtBeginningInclusive();
			final int indexAtEndExclusive = textScanner.getIndexAtEndExclusive();
			
			textScanner.nextCharacter();
			textScanner.consume();
			
			final StringBuilder stringBuilder = new StringBuilder();
			
			character = textScanner.currentCharacter();
			
			if(Character.isJavaIdentifierStart(character) && character != TextScanner.EOF) {
				stringBuilder.append(character);
				
				textScanner.nextCharacter();
				textScanner.consume();
				
				character = textScanner.currentCharacter();
				
				while(Character.isJavaIdentifierPart(character) && character != TextScanner.EOF) {
					stringBuilder.append(character);
					
					textScanner.nextCharacter();
					textScanner.consume();
					
					character = textScanner.currentCharacter();
				}
				
				if(character == '=') {
					textScanner.nextCharacter();
					textScanner.consume();
					
					final Group group = doParseGroup(textScanner);
					
					if(group != null) {
						return new GroupReferenceDefinition(stringBuilder.toString(), group);
					}
				}
			}
			
			textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
		}
		
		return null;
	}
	
	private static Repetition doParseRepetition(final TextScanner textScanner) {
		char character = textScanner.currentCharacter();
		
		switch(character) {
			case TextScanner.EOF:
				return Repetition.ONE;
			case '+':
				textScanner.nextCharacter();
				textScanner.consume();
				
				return Repetition.ONE_OR_MORE;
			case '*':
				textScanner.nextCharacter();
				textScanner.consume();
				
				return Repetition.ANY;
			case '?':
				textScanner.nextCharacter();
				textScanner.consume();
				
				return Repetition.ZERO_OR_ONE;
			case '{':
				final StringBuilder stringBuilderMinimum = new StringBuilder();
				final StringBuilder stringBuilderMaximum = new StringBuilder();
				
				if(textScanner.nextRegex(PATTERN_REPETITION, matchResult -> {stringBuilderMinimum.append(matchResult.group(1)); stringBuilderMaximum.append(matchResult.group(2));})) {
					textScanner.consume();
					
					final String stringMinimum = stringBuilderMinimum.toString();
					final String stringMaximum = stringBuilderMaximum.toString();
					
					final int minimum = stringMinimum.isEmpty() ? 0 : Integer.parseInt(stringMinimum);
					final int maximum = stringMaximum.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(stringMaximum);
					
					return new Repetition(minimum, maximum);
				}
				
				throw new IllegalArgumentException(String.format("Illegal Repetition: %s", textScanner));
			default:
				return Repetition.ONE;
		}
	}
	
	private static Symbol doParseSymbol(final TextScanner textScanner) {
		final char character0 = textScanner.currentCharacter();
		
		switch(character0) {
			case TextScanner.EOF:
			case ' ':
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
			case '%':
			case '&':
			case '=': {
				throw new IllegalArgumentException(String.format("Illegal Symbol: %s", textScanner));
			}
			case '\\': {
				final int indexAtBeginningInclusive = textScanner.getIndexAtBeginningInclusive();
				final int indexAtEndExclusive = textScanner.getIndexAtEndExclusive();
				
				textScanner.nextCharacter();
				textScanner.consume();
				
				final char character1 = textScanner.currentCharacter();
				
				switch(character1) {
					case TextScanner.EOF:
						textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
						
						throw new IllegalArgumentException(String.format("Illegal Symbol: %s", textScanner));
					default:
						textScanner.nextCharacter();
						textScanner.consume();
						
						return new Symbol(character1, doParseRepetition(textScanner));
				}
			}
			default: {
				textScanner.nextCharacter();
				textScanner.consume();
				
				return new Symbol(character0, doParseRepetition(textScanner));
			}
		}
	}
}