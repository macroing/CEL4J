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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class Matcher implements Node {
	private final List<Matcher> matchers;
	private final MatchInfo matchInfo;
	private final Matchable matchable;
	private final String source;
	private final boolean isMatching;
	private final int beginIndex;
	private final int currentCharacterMatch;
	private final int endIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Matcher(final Builder builder) {
		this.matchers = Collections.unmodifiableList(new ArrayList<>(builder.matchers));
		this.matchInfo = builder.matchInfo;
		this.matchable = builder.matchable;
		this.source = builder.source;
		this.isMatching = builder.isMatching;
		this.beginIndex = builder.beginIndex;
		this.currentCharacterMatch = builder.currentCharacterMatch;
		this.endIndex = builder.endIndex;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public List<Matcher> getMatchers() {
		return new ArrayList<>(this.matchers);
	}
	
//	TODO: Add Javadocs!
	public MatchInfo getMatchInfo() {
		return this.matchInfo;
	}
	
//	TODO: Add Javadocs!
	public Matchable getMatchable() {
		return this.matchable;
	}
	
//	TODO: Add Javadocs!
	public String getResult() {
		return this.source.substring(this.beginIndex, this.endIndex);
	}
	
//	TODO: Add Javadocs!
	public String getSource() {
		return this.source;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Matcher} instance.
	 * 
	 * @return a {@code String} representation of this {@code Matcher} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Matcher:");
		stringBuilder.append("\n");
		stringBuilder.append("- Source=\"" + getSource() + "\"");
		stringBuilder.append("\n");
		stringBuilder.append("- Result=\"" + getResult() + "\"");
		stringBuilder.append("\n");
		stringBuilder.append("- BeginIndex=" + getBeginIndex());
		stringBuilder.append("\n");
		stringBuilder.append("- EndIndex=" + getEndIndex());
		stringBuilder.append("\n");
		stringBuilder.append("- CurrentCharacterMatch=" + getCurrentCharacterMatch());
		stringBuilder.append("\n");
		stringBuilder.append("- IsMatching=" + isMatching());
		stringBuilder.append("\n");
		stringBuilder.append("- IsMatchingAll=" + isMatchingAll());
		stringBuilder.append("\n");
		stringBuilder.append("- Matchable=" + getMatchable());
		stringBuilder.append("\n");
		stringBuilder.append("- MatchableSource=" + getMatchable().getSource());
		stringBuilder.append("\n");
		stringBuilder.append("- MatchInfo=" + getMatchInfo());
		
		return stringBuilder.toString();
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				for(final Matcher matcher : this.matchers) {
					if(!matcher.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
//	TODO: Add Javadocs!
	public boolean isMatching() {
		return this.isMatching;
	}
	
//	TODO: Add Javadocs!
	public boolean isMatchingAll() {
		return isMatching() && getResult().equals(getSource());
	}
	
//	TODO: Add Javadocs!
	public int getBeginIndex() {
		return this.beginIndex;
	}
	
//	TODO: Add Javadocs!
	public int getCurrentCharacterMatch() {
		return this.currentCharacterMatch;
	}
	
//	TODO: Add Javadocs!
	public int getEndIndex() {
		return this.endIndex;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static enum MatchInfo {
//		TODO: Add Javadocs!
		FAILURE,
		
//		TODO: Add Javadocs!
		SUCCESS,
		
//		TODO: Add Javadocs!
		UNEXPECTED_CHARACTER_MATCH,
		
//		TODO: Add Javadocs!
		UNEXPECTED_STRING_LENGTH;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private MatchInfo() {
			
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static final class Builder {
		final List<Matcher> matchers;
		MatchInfo matchInfo;
		Matchable matchable;
		String source;
		boolean isMatching;
		int beginIndex;
		int currentCharacterMatch;
		int endIndex;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Builder() {
			this.matchers = new ArrayList<>();
			this.matchInfo = null;
			this.matchable = null;
			this.source = null;
			this.isMatching = false;
			this.beginIndex = 0;
			this.currentCharacterMatch = 0;
			this.endIndex = 0;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Builder addMatcher(final Matcher matcher) {
			this.matchers.add(Objects.requireNonNull(matcher, "matcher == null"));
			
			return this;
		}
		
		public Builder setBeginIndex(final int beginIndex) {
			this.beginIndex = ParameterArguments.requireRange(beginIndex, 0, Integer.MAX_VALUE, "beginIndex");
			
			return this;
		}
		
		public Builder setCurrentCharacterMatch(final int currentCharacterMatch) {
			this.currentCharacterMatch = ParameterArguments.requireRange(currentCharacterMatch, 0, Integer.MAX_VALUE, "currentCharacterMatch");
			
			return this;
		}
		
		public Builder setEndIndex(final int endIndex) {
			this.endIndex = ParameterArguments.requireRange(endIndex, 0, Integer.MAX_VALUE, "endIndex");
			
			return this;
		}
		
		public Builder setMatchInfo(final MatchInfo matchInfo) {
			this.matchInfo = Objects.requireNonNull(matchInfo, "matchInfo == null");
			
			return this;
		}
		
		public Builder setMatchable(final Matchable matchable) {
			this.matchable = Objects.requireNonNull(matchable, "matchable == null");
			
			return this;
		}
		
		public Builder setMatching(final boolean isMatching) {
			this.isMatching = isMatching;
			
			return this;
		}
		
		public Builder setSource(final String source) {
			this.source = Objects.requireNonNull(source, "source == null");
			
			return this;
		}
		
		public Matcher build() {
			try {
				Objects.requireNonNull(this.matchInfo, "matchInfo == null");
				Objects.requireNonNull(this.matchable, "matchable == null");
				Objects.requireNonNull(this.source, "source == null");
				
				ParameterArguments.requireRange(this.beginIndex, 0, this.source.length(), "beginIndex");
				ParameterArguments.requireRange(this.currentCharacterMatch, 0, Integer.MAX_VALUE, "currentCharacterMatch");
				ParameterArguments.requireRange(this.endIndex, this.beginIndex, this.source.length(), "endIndex");
				
				return new Matcher(this);
			} catch(final IllegalArgumentException | NullPointerException e) {
				throw new IllegalStateException(e);
			}
		}
	}
}