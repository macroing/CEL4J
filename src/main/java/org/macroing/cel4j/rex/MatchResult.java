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
package org.macroing.cel4j.rex;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code MatchResult} contains the result of a match by a {@link Matcher}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class MatchResult implements Node {
	private final List<MatchResult> matchResults;
	private final Matcher matcher;
	private final String input;
	private final boolean isMatching;
	private final int indexAtBeginning;
	private final int indexAtEnd;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code MatchResult} instance.
	 * <p>
	 * If either {@code matcher} or {@code input} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code indexAtBeginning} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new MatchResult(matcher, input, isMatching, indexAtBeginning, indexAtBeginning);
	 * }
	 * </pre>
	 * 
	 * @param matcher the {@link Matcher} associated with this {@code MatchResult} instance
	 * @param input the {@code String} used to match the associated {@code Matcher} instance against
	 * @param isMatching {@code true} if, and only if, this {@code MatchResult} instance contains a partial match, {@code false} otherwise
	 * @param indexAtBeginning the index of {@code input} that indicates the beginning of a possible match
	 * @throws IllegalArgumentException thrown if, and only if, {@code indexAtBeginning} is less than {@code 0}
	 * @throws NullPointerException thrown if, and only if, either {@code matcher} or {@code input} are {@code null}
	 */
	public MatchResult(final Matcher matcher, final String input, final boolean isMatching, final int indexAtBeginning) {
		this(matcher, input, isMatching, indexAtBeginning, indexAtBeginning);
	}
	
	/**
	 * Constructs a new {@code MatchResult} instance.
	 * <p>
	 * If either {@code matcher} or {@code input} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code indexAtBeginning} is less than {@code 0} or {@code indexAtEnd} is less than {@code indexAtBeginning}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new MatchResult(matcher, input, isMatching, indexAtBeginning, indexAtEnd, new ArrayList<>());
	 * }
	 * </pre>
	 * 
	 * @param matcher the {@link Matcher} associated with this {@code MatchResult} instance
	 * @param input the {@code String} used to match the associated {@code Matcher} instance against
	 * @param isMatching {@code true} if, and only if, this {@code MatchResult} instance contains a partial match, {@code false} otherwise
	 * @param indexAtBeginning the index of {@code input} that indicates the beginning of a possible match
	 * @param indexAtEnd the index of {@code input} that indicates the end of a possible match
	 * @throws IllegalArgumentException thrown if, and only if, {@code indexAtBeginning} is less than {@code 0} or {@code indexAtEnd} is less than {@code indexAtBeginning}
	 * @throws NullPointerException thrown if, and only if, either {@code matcher} or {@code input} are {@code null}
	 */
	public MatchResult(final Matcher matcher, final String input, final boolean isMatching, final int indexAtBeginning, final int indexAtEnd) {
		this(matcher, input, isMatching, indexAtBeginning, indexAtEnd, new ArrayList<>());
	}
	
	/**
	 * Constructs a new {@code MatchResult} instance.
	 * <p>
	 * If either {@code matcher}, {@code input}, {@code matchResults} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code indexAtBeginning} is less than {@code 0} or {@code indexAtEnd} is less than {@code indexAtBeginning}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param matcher the {@link Matcher} associated with this {@code MatchResult} instance
	 * @param input the {@code String} used to match the associated {@code Matcher} instance against
	 * @param isMatching {@code true} if, and only if, this {@code MatchResult} instance contains a partial match, {@code false} otherwise
	 * @param indexAtBeginning the index of {@code input} that indicates the beginning of a possible match
	 * @param indexAtEnd the index of {@code input} that indicates the end of a possible match
	 * @param matchResults a {@code List} with all {@code MatchResult} instances associated with this {@code MatchResult} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code indexAtBeginning} is less than {@code 0} or {@code indexAtEnd} is less than {@code indexAtBeginning}
	 * @throws NullPointerException thrown if, and only if, either {@code matcher}, {@code input}, {@code matchResults} or any of its elements are {@code null}
	 */
	public MatchResult(final Matcher matcher, final String input, final boolean isMatching, final int indexAtBeginning, final int indexAtEnd, final List<MatchResult> matchResults) {
		this.matchResults = ParameterArguments.requireNonNullList(matchResults, "matchResults");
		this.matcher = Objects.requireNonNull(matcher, "matcher == null");
		this.input = Objects.requireNonNull(input, "input == null");
		this.isMatching = isMatching;
		this.indexAtBeginning = ParameterArguments.requireRange(indexAtBeginning, 0, input.length(), "indexAtBeginning");
		this.indexAtEnd = ParameterArguments.requireRange(indexAtEnd, indexAtBeginning, input.length(), "indexAtEnd");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code MatchResult} instances associated with this {@code MatchResult} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code MatchResult} instance.
	 * 
	 * @return a {@code List} with all {@code MatchResult} instances associated with this {@code MatchResult} instance
	 */
	public List<MatchResult> getMatchResults() {
		return new ArrayList<>(this.matchResults);
	}
	
	/**
	 * Returns the {@link Matcher} associated with this {@code MatchResult} instance.
	 * 
	 * @return the {@code Matcher} associated with this {@code MatchResult} instance
	 */
	public Matcher getMatcher() {
		return this.matcher;
	}
	
	/**
	 * Returns the {@code String} used to match the associated {@link Matcher} instance against.
	 * 
	 * @return the {@code String} used to match the associated {@code Matcher} instance against
	 */
	public String getInput() {
		return this.input;
	}
	
	/**
	 * Returns a {@code String} with the match produced by the associated {@link Matcher} instance.
	 * 
	 * @return a {@code String} with the match produced by the associated {@code Matcher} instance
	 */
	public String getMatch() {
		return getInput().substring(getIndexAtBeginning(), getIndexAtEnd());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code MatchResult} instance.
	 * 
	 * @return a {@code String} representation of this {@code MatchResult} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("MatchResult:");
		stringBuilder.append("\n");
		stringBuilder.append("- Input = \"" + doFormat(getInput()) + "\"");
		stringBuilder.append("\n");
		stringBuilder.append("- Match = \"" + doFormat(getMatch()) + "\"");
		stringBuilder.append("\n");
		stringBuilder.append("- IndexAtBeginning = " + getIndexAtBeginning());
		stringBuilder.append("\n");
		stringBuilder.append("- IndexAtEnd = " + getIndexAtEnd());
		stringBuilder.append("\n");
		stringBuilder.append("- IsMatching = " + isMatching());
		stringBuilder.append("\n");
		stringBuilder.append("- IsMatchingAll = " + isMatchingAll());
		stringBuilder.append("\n");
		stringBuilder.append("- Matcher = " + getMatcher());
		stringBuilder.append("\n");
		stringBuilder.append("- MatcherSourceCode = \"" + doFormat(getMatcher().getSourceCode()) + "\"");
		
		return stringBuilder.toString();
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
	 * <li>traverse its child {@code Node} instances, if it has any.</li>
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
				for(final MatchResult matchResult : this.matchResults) {
					if(!matchResult.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code MatchResult} instance contains a partial match, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code MatchResult} instance contains a partial match, {@code false} otherwise
	 */
	public boolean isMatching() {
		return this.isMatching;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code MatchResult} instance contains a full match, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code MatchResult} instance contains a full match, {@code false} otherwise
	 */
	public boolean isMatchingAll() {
		return isMatching() && getMatch().equals(getInput());
	}
	
	/**
	 * Returns the index of {@code matchResult.getInput()} that indicates the beginning of a possible match.
	 * 
	 * @return the index of {@code matchResult.getInput()} that indicates the beginning of a possible match
	 */
	public int getIndexAtBeginning() {
		return this.indexAtBeginning;
	}
	
	/**
	 * Returns the index of {@code matchResult.getInput()} that indicates the end of a possible match.
	 * 
	 * @return the index of {@code matchResult.getInput()} that indicates the end of a possible match
	 */
	public int getIndexAtEnd() {
		return this.indexAtEnd;
	}
	
	/**
	 * Returns the length of the possible match.
	 * 
	 * @return the length of the possible match
	 */
	public int getLength() {
		return getIndexAtEnd() - getIndexAtBeginning();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doFormat(final String string) {
		return string.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
	}
}