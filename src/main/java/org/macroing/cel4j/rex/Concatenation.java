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
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Concatenation implements Matcher {
	private final List<Matcher> matchers;
	private final String source;
	
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
		this.source = doCreateSource(this.matchers);
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
		
		for(final Matcher matcher : this.matchers) {
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
	 * Matches {@code source}.
	 * <p>
	 * Returns a {@link MatchResult} with the result of the match.
	 * <p>
	 * If {@code source} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * concatenation.match(source, 0);
	 * }
	 * </pre>
	 * 
	 * @param source the source to match
	 * @return a {@code MatchResult} with the result of the match
	 * @throws NullPointerException thrown if, and only if, {@code source} is {@code null}
	 */
	@Override
	public MatchResult match(final String source) {
		return match(source, 0);
	}
	
	/**
	 * Matches {@code source}.
	 * <p>
	 * Returns a {@link MatchResult} with the result of the match.
	 * <p>
	 * If {@code source} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code source.length()}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param source the source to match
	 * @param index the index in {@code source} to match from
	 * @return a {@code MatchResult} with the result of the match
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than or equal to {@code source.length()}
	 * @throws NullPointerException thrown if, and only if, {@code source} is {@code null}
	 */
	@Override
	public MatchResult match(final String source, final int index) {
		Objects.requireNonNull(source, "source == null");
		
		ParameterArguments.requireRange(index, 0, source.length(), "index");
		
		final List<MatchResult> matchResults = new ArrayList<>();
		
		int currentIndex = index;
		
		int length = 0;
		
		for(final Matcher matcher : getMatchers()) {
			final MatchResult currentMatchResult = matcher.match(source, currentIndex);
			
			if(currentMatchResult.isMatching()) {
				currentIndex += currentMatchResult.getLength();
				
				length += currentMatchResult.getLength();
				
				matchResults.add(currentMatchResult);
			} else {
				return new MatchResult(this, source, false, index);
			}
		}
		
		return new MatchResult(this, source, true, index, index + length, matchResults);
	}
	
	/**
	 * Returns the source associated with this {@code Concatenation} instance.
	 * 
	 * @return the source associated with this {@code Concatenation} instance
	 */
	@Override
	public String getSource() {
		return this.source;
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
		} else if(!Objects.equals(getSource(), Concatenation.class.cast(object).getSource())) {
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
		return Objects.hash(getSource());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doCreateSource(final List<Matcher> matchables) {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(int i = 0; i < matchables.size(); i++) {
			stringBuilder.append(matchables.get(i).getSource());
		}
		
		return stringBuilder.toString();
	}
}