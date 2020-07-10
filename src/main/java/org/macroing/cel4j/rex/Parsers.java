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

import java.util.List;
import java.util.Optional;

import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeFormatException;
import org.macroing.cel4j.scanner.TextScanner;

final class Parsers {
	private Parsers() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Expression parseExpression(final TextScanner textScanner) {
		final Alternation alternation = doParseAlternation(textScanner);
		
		if(alternation != null) {
			final
			Expression.Builder expression_Builder = new Expression.Builder();
			expression_Builder.addMatchable(alternation);
			
			return doUpdateGroupReferences(expression_Builder.build());
		}
		
		throw new IllegalArgumentException(String.format("Illegal Expression: %s", textScanner));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Alternation doParseAlternation(final TextScanner textScanner) {
		final Alternation.Builder alternation_Builder = new Alternation.Builder();
		
		final int indexAtBeginningInclusive = textScanner.getIndexAtBeginningInclusive();
		final int indexAtEndExclusive = textScanner.getIndexAtEndExclusive();
		
		do {
			final Concatenation concatenation = doParseConcatenation(textScanner);
			
			if(concatenation != null) {
				alternation_Builder.addMatchable(concatenation);
			} else {
				textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
				
				return null;
			}
		} while(textScanner.nextCharacter('|') && textScanner.consume());
		
		return alternation_Builder.build();
	}
	
	private static Concatenation doParseConcatenation(final TextScanner textScanner) {
		final Concatenation.Builder concatenation_Builder = new Concatenation.Builder();
		
		final int indexAtBeginningInclusive = textScanner.getIndexAtBeginningInclusive();
		final int indexAtEndExclusive = textScanner.getIndexAtEndExclusive();
		
		int count = 0;
		
		loop:
		while(true) {
			switch(textScanner.currentCharacter()) {
				case TextScanner.EOF:
					break loop;
				case '(': {
					final Group group = doParseGroup(textScanner);
					
					if(group != null) {
						count++;
						
						concatenation_Builder.addMatchable(group);
						
						continue;
					}
					
					textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
					
					return null;
				}
				case '$': {
					final GroupReference groupReference = doParseGroupReference(textScanner);
					
					if(groupReference != null) {
						count++;
						
						concatenation_Builder.addMatchable(groupReference);
						
						continue;
					}
					
					textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
					
					return null;
				}
				default: {
					final Symbol symbol = doParseSymbol(textScanner);
					
					if(symbol != null) {
						count++;
						
						concatenation_Builder.addMatchable(symbol);
						
						continue;
					}
					
					break loop;
				}
			}
		}
		
		if(count > 0) {
			return concatenation_Builder.build();
		}
		
		textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
		
		return null;
	}
	
	private static Expression doUpdateGroupReferences(final Expression expression) {
		final List<Group> groups = NodeFilter.filter(expression, NodeFilter.any(), Group.class);
		final List<GroupReference> groupReferences = NodeFilter.filter(expression, NodeFilter.any(), GroupReference.class);
		
		if(groups.size() > 0 && groupReferences.size() > 0) {
			for(final GroupReference groupReference : groupReferences) {
				if(!groupReference.isDefinition() && !groupReference.hasGroup()) {
					final String name = groupReference.getName();
					
					if(name.matches("[0-9]+")) {
						final int index = Integer.parseInt(name);
						
						if(index >= 0 && index < groups.size()) {
							groupReference.setGroup(groups.get(index));
							
							break;
						}
						
						throw new NodeFormatException("No matching Group could be found for the GroupReference '" + groupReference + "'.");
					}
					
					for(int i = 0; i < groupReferences.size(); i++) {
						final GroupReference currentGroupReference = groupReferences.get(i);
						
						final Optional<Group> currentGroup = currentGroupReference.getGroup();
						
						if(currentGroupReference.getName().equals(name) && currentGroupReference.isDefinition() && currentGroup.isPresent()) {
							groupReference.setGroup(currentGroup.get());
						}
					}
					
					if(!groupReference.hasGroup()) {
						throw new NodeFormatException("No matching Group could be found for the GroupReference '" + groupReference + "'.");
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
					
					character = textScanner.currentCharacter();
					
					switch(character) {
						case '+':
							textScanner.nextCharacter();
							textScanner.consume();
							
							return new Group.Builder().addMatchable(alternation).setRepetition(Repetition.ONE_OR_MORE).build();
						case '*':
							textScanner.nextCharacter();
							textScanner.consume();
							
							return new Group.Builder().addMatchable(alternation).setRepetition(Repetition.ANY).build();
						case '?':
							textScanner.nextCharacter();
							textScanner.consume();
							
							return new Group.Builder().addMatchable(alternation).setRepetition(Repetition.ZERO_OR_ONE).build();
						default:
							return new Group.Builder().addMatchable(alternation).setRepetition(Repetition.ONE).build();
					}
				}
			} else {
				textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
			}
		}
		
		return null;
	}
	
	private static GroupReference doParseGroupReference(final TextScanner textScanner) {
		char character = textScanner.currentCharacter();
		
		if(character == '$') {
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
				
				if(character == '=') {
					textScanner.nextCharacter();
					textScanner.consume();
					
					final Group group = doParseGroup(textScanner);
					
					if(group != null) {
						return new GroupReference(stringBuilder.toString(), group);
					}
					
					textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
					
					return null;
				}
				
				return new GroupReference(stringBuilder.toString());
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
				
				if(character == '=') {
					textScanner.nextCharacter();
					textScanner.consume();
					
					final Group group = doParseGroup(textScanner);
					
					if(group != null) {
						return new GroupReference(stringBuilder.toString(), group);
					}
					
					textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
					
					return null;
				}
				
				return new GroupReference(stringBuilder.toString());
			} else {
				textScanner.stateSet(indexAtBeginningInclusive, indexAtEndExclusive);
			}
		}
		
		return null;
	}
	
	private static Symbol doParseSymbol(final TextScanner textScanner) {
		final char character0 = textScanner.currentCharacter();
		
		switch(character0) {
			case TextScanner.EOF:
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
			case '=': {
				return null;
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
						
						return null;
					default:
						textScanner.nextCharacter();
						textScanner.consume();
						
						final char character2 = textScanner.currentCharacter();
						
						switch(character2) {
							case TextScanner.EOF:
								return new Symbol(character1);
							case '+':
								textScanner.nextCharacter();
								textScanner.consume();
								
								return new Symbol(character1, Repetition.ONE_OR_MORE);
							case '*':
								textScanner.nextCharacter();
								textScanner.consume();
								
								return new Symbol(character1, Repetition.ANY);
							case '?':
								textScanner.nextCharacter();
								textScanner.consume();
								
								return new Symbol(character1, Repetition.ZERO_OR_ONE);
							default:
								return new Symbol(character1);
						}
				}
			}
			default: {
				textScanner.nextCharacter();
				textScanner.consume();
				
				final char character1 = textScanner.currentCharacter();
				
				switch(character1) {
					case TextScanner.EOF:
						return new Symbol(character0);
					case '+':
						textScanner.nextCharacter();
						textScanner.consume();
						
						return new Symbol(character0, Repetition.ONE_OR_MORE);
					case '*':
						textScanner.nextCharacter();
						textScanner.consume();
						
						return new Symbol(character0, Repetition.ANY);
					case '?':
						textScanner.nextCharacter();
						textScanner.consume();
						
						return new Symbol(character0, Repetition.ZERO_OR_ONE);
					default:
						return new Symbol(character0);
				}
			}
		}
	}
}