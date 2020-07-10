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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.rex.Matcher.MatchInfo;
import org.macroing.cel4j.scanner.TextScanner;
import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class Expression implements Node {
	private final Group group;
	private final String source;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Expression(final Builder builder) {
		this.group = builder.group_Builder.build();
		this.source = doCreateSource(this.group);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Group} associated with this {@code Expression} instance.
	 * 
	 * @return the {@code Group} associated with this {@code Expression} instance
	 */
	public Group getGroup() {
		return this.group;
	}
	
//	TODO: Add Javadocs!
	public Matcher matcher(final String source) {
		return matcher(source, 0, 0);
	}
	
//	TODO: Add Javadocs!
	public Matcher matcher(final String source, final int beginIndex, final int endIndex) {
		Objects.requireNonNull(source, "source == null");
		
		ParameterArguments.requireRange(beginIndex, 0, source.length(), "beginIndex");
		ParameterArguments.requireRange(endIndex, beginIndex, source.length(), "endIndex");
		
		boolean isMatching = true;
		
		int currentCharacterMatch = 0;
		int newBeginIndex = beginIndex;
		int newEndIndex = endIndex;
		int matches = 0;
		int maximum = 1;
		int minimum = 1;
		
		final List<Matcher> matchers = new ArrayList<>();
		
		MatchInfo matchInfo = MatchInfo.SUCCESS;
		
		loop:
		while(matches < maximum) {
			for(final Matchable matchable : this.group.getMatchables()) {
				if(matchable instanceof GroupReference && GroupReference.class.cast(matchable).isDefinition()) {
					continue;
				}
				
				final Matcher matcher = matchable.matcher(source, newBeginIndex, newEndIndex);
				
				matchers.add(matcher);
				
				currentCharacterMatch += matcher.getCurrentCharacterMatch();
				
				switch(matcher.getMatchInfo()) {
					case FAILURE:
						matchInfo = MatchInfo.FAILURE;
						
						break;
					case SUCCESS:
						break;
					case UNEXPECTED_CHARACTER_MATCH:
						if(matchInfo != MatchInfo.FAILURE) {
							matchInfo = MatchInfo.UNEXPECTED_CHARACTER_MATCH;
						}
						
						break;
					case UNEXPECTED_STRING_LENGTH:
						if(matchInfo != MatchInfo.FAILURE && matchInfo != MatchInfo.UNEXPECTED_CHARACTER_MATCH) {
							matchInfo = MatchInfo.UNEXPECTED_STRING_LENGTH;
						}
						
						break;
					default:
						break;
				}
				
				if(!matcher.isMatching()) {
					break loop;
				}
				
				newBeginIndex = matcher.getEndIndex();
				newEndIndex = matcher.getEndIndex();
			}
			
			matches++;
		}
		
		if(matches < minimum) {
			isMatching = false;
		}
		
		newBeginIndex = beginIndex;
		
		final
		Matcher.Builder matcher_Builder = new Matcher.Builder();
		matcher_Builder.setBeginIndex(newBeginIndex);
		matcher_Builder.setCurrentCharacterMatch(currentCharacterMatch);
		matcher_Builder.setEndIndex(newEndIndex);
		matcher_Builder.setMatchInfo(matchInfo);
		matcher_Builder.setMatchable(this.group);
		matcher_Builder.setMatching(isMatching);
		matcher_Builder.setSource(source);
		
		for(final Matcher matcher : matchers) {
			matcher_Builder.addMatcher(matcher);
		}
		
		return matcher_Builder.build();
	}
	
	/**
	 * Returns the source associated with this {@code Expression} instance.
	 * 
	 * @return the source associated with this {@code Expression} instance
	 */
	public String getSource() {
		return this.source;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Expression} instance.
	 * 
	 * @return a {@code String} representation of this {@code Expression} instance
	 */
	@Override
	public String toString() {
		return "new Expression(...)";
	}
	
	/**
	 * Accepts a {@link NodeHierarchicalVisitor}.
	 * <p>
	 * Returns the result of {@code nodeHierarchicalVisitor.visitLeave(this)}.
	 * <p>
	 * If {@code nodeHierarchicalVisitor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}, a {@code NodeTraversalException} will be thrown with the {@code RuntimeException} wrapped.
	 * <p>
	 * This implementation will:
	 * <ul>
	 * <li>throw a {@code NullPointerException} if {@code nodeHierarchicalVisitor} is {@code null}.</li>
	 * <li>throw a {@code NodeTraversalException} if {@code nodeHierarchicalVisitor} throws a {@code RuntimeException}.</li>
	 * <li>traverse its child {@code Node} instance.</li>
	 * </ul>
	 * 
	 * @param nodeHierarchicalVisitor the {@code NodeHierarchicalVisitor} to accept
	 * @return the result of {@code nodeHierarchicalVisitor.visitLeave(this)}
	 * @throws NodeTraversalException thrown if, and only if, a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code nodeHierarchicalVisitor} is {@code null}
	 */
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				if(!this.group.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code Expression} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Expression}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Expression} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Expression}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Expression)) {
			return false;
		} else if(!Objects.equals(getSource(), Expression.class.cast(object).getSource())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Expression} instance.
	 * 
	 * @return a hash code for this {@code Expression} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getSource());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static Expression parse(final String string) {
		return parse(new TextScanner(Objects.requireNonNull(string, "string == null")));
	}
	
//	TODO: Add Javadocs!
	public static Expression parse(final TextScanner textScanner) {
		return Parsers.parseExpression(Objects.requireNonNull(textScanner, "textScanner == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static final class Builder {
		final Group.Builder group_Builder;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
//		TODO: Add Javadocs!
		public Builder() {
			this.group_Builder = new Group.Builder();
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
//		TODO: Add Javadocs!
		public Builder addMatchable(final Matchable matchable) {
			this.group_Builder.addMatchable(Objects.requireNonNull(matchable, "matchable == null"));
			
			return this;
		}
		
//		TODO: Add Javadocs!
		public Builder removeMatchable(final Matchable matchable) {
			this.group_Builder.removeMatchable(Objects.requireNonNull(matchable, "matchable == null"));
			
			return this;
		}
		
//		TODO: Add Javadocs!
		public Expression build() {
			return new Expression(this);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doCreateSource(final Group group) {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("/");
		
		for(final Matchable matchable : group.getMatchables()) {
			stringBuilder.append(matchable);
		}
		
		stringBuilder.append("/");
		
		return stringBuilder.toString();
	}
}