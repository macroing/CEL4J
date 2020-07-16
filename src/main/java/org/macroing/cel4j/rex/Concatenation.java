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
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code Concatenation} is a {@link Matcher} that can match concatenations of {@link Matcher} instances.
 * <p>
 * This class is indirectly mutable and not thread-safe.
 * <p>
 * A {@code Concatenation} consists of one or more {@code Matcher} instances. If two or more {@code Matcher} instances are present, they are separated with an ampersand character ({@code &}), a comma character ({@code ,}) or a semicolon character
 * ({@code ;}).
 * <p>
 * To use a {@code Concatenation} in Rex, consider the following examples:
 * <pre>
 * {@code
 * "A" & "B" & "C"
 * "A", "B", "C"
 * "A"; "B"; "C"
 * }
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Concatenation implements Matcher {
	private final List<Matcher> matchers;
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Concatenation} instance.
	 * <p>
	 * If either {@code matchers} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param matchers a {@code List} with all {@link Matcher} instances associated with this {@code Concatenation} instance
	 * @throws NullPointerException thrown if, and only if, either {@code matchers} or any of its elements are {@code null}
	 */
	public Concatenation(final List<Matcher> matchers) {
		this.matchers = new ArrayList<>(ParameterArguments.requireNonNullList(matchers, "matchers"));
		this.sourceCode = doCreateSourceCode(this.matchers);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code Concatenation} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * concatenation.write(new Document());
	 * }
	 * </pre>
	 * 
	 * @return the {@code Document}
	 */
	@Override
	public Document write() {
		return write(new Document());
	}
	
	/**
	 * Writes this {@code Concatenation} to {@code document}.
	 * <p>
	 * Returns {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param document the {@link Document} to write to
	 * @return {@code document}
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	@Override
	public Document write(final Document document) {
		document.line("Concatenation {");
		document.indent();
		
		for(final Matcher matcher : getMatchers()) {
			matcher.write(document);
		}
		
		document.outdent();
		document.line("}");
		
		return document;
	}
	
	/**
	 * Returns a {@code List} with all {@link Matcher} instances associated with this {@code Concatenation} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Concatenation} instance.
	 * 
	 * @return a {@code List} with all {@code Matcher} instances associated with this {@code Concatenation} instance
	 */
	public List<Matcher> getMatchers() {
		return new ArrayList<>(this.matchers);
	}
	
	/**
	 * Matches {@code input}.
	 * <p>
	 * Returns a {@link MatchResult} with the result of the match.
	 * <p>
	 * If {@code input} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * concatenation.match(input, 0);
	 * }
	 * </pre>
	 * 
	 * @param input the {@code String} to match
	 * @return a {@code MatchResult} with the result of the match
	 * @throws NullPointerException thrown if, and only if, {@code input} is {@code null}
	 */
	@Override
	public MatchResult match(final String input) {
		return match(input, 0);
	}
	
	/**
	 * Matches {@code input}.
	 * <p>
	 * Returns a {@link MatchResult} with the result of the match.
	 * <p>
	 * If {@code input} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code input.length()}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param input the {@code String} to match
	 * @param index the index in {@code input} to match from
	 * @return a {@code MatchResult} with the result of the match
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than or equal to {@code input.length()}
	 * @throws NullPointerException thrown if, and only if, {@code input} is {@code null}
	 */
	@Override
	public MatchResult match(final String input, final int index) {
		Objects.requireNonNull(input, "input == null");
		
		ParameterArguments.requireRange(index, 0, input.length(), "index");
		
		final List<MatchResult> matchResults = new ArrayList<>();
		
		int currentIndex = index;
		
		int length = 0;
		
		for(final Matcher matcher : getMatchers()) {
			final MatchResult currentMatchResult = matcher.match(input, currentIndex);
			
			if(currentMatchResult.isMatching()) {
				currentIndex += currentMatchResult.getLength();
				
				length += currentMatchResult.getLength();
				
				matchResults.add(currentMatchResult);
			} else {
				return new MatchResult(this, input, false, index);
			}
		}
		
		return new MatchResult(this, input, true, index, index + length, matchResults);
	}
	
	/**
	 * Returns the source code associated with this {@code Concatenation} instance.
	 * 
	 * @return the source code associated with this {@code Concatenation} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Concatenation} instance.
	 * 
	 * @return a {@code String} representation of this {@code Concatenation} instance
	 */
	@Override
	public String toString() {
		return String.format("new Concatenation(Arrays.asList(%s))", this.matchers.stream().map(matchable -> matchable.toString()).collect(Collectors.joining(", ")));
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
	
	/**
	 * Compares {@code object} to this {@code Concatenation} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Concatenation}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Concatenation} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Concatenation}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Concatenation)) {
			return false;
		} else if(!Objects.equals(getSourceCode(), Concatenation.class.cast(object).getSourceCode())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Concatenation} instance.
	 * 
	 * @return a hash code for this {@code Concatenation} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getSourceCode());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doCreateSourceCode(final List<Matcher> matchers) {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(int i = 0; i < matchers.size(); i++) {
			final Matcher matcherA = matchers.get(i > 0 ? i - 1 : i);
			final Matcher matcherB = matchers.get(i);
			
			stringBuilder.append(doGetPreferredConcatenationCharacter(matcherA, matcherB));
			stringBuilder.append(matcherB.getSourceCode());
		}
		
		return stringBuilder.toString();
	}
	
	private static String doGetPreferredConcatenationCharacter(final Matcher matcherA, final Matcher matcherB) {
		if(matcherA == matcherB) {
			return "";
		} else if(matcherA instanceof GroupReferenceDefinition) {
			return ";\n";
		} else {
			return " & ";
		}
	}
}