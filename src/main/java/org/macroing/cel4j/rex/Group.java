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
package org.macroing.cel4j.rex;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code Group} is a {@link Matcher} that can match an {@link Alternation} instance.
 * <p>
 * This class is indirectly mutable and not thread-safe.
 * <p>
 * A {@code Group} consists of a single {@code Alternation} instance inside a set of parenthesis and an optional {@link Repetition} instance.
 * <p>
 * To use a {@code Group} in Rex, consider the following example:
 * <pre>
 * {@code
 * ("A" & "B" | "C" & "D")+
 * }
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Group implements Matcher {
	private final Alternation alternation;
	private final Repetition repetition;
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Group} instance.
	 * <p>
	 * If {@code alternation} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Group(alternation, Repetition.ONE);
	 * }
	 * </pre>
	 * 
	 * @param alternation the {@link Alternation} associated with this {@code Group} instance
	 * @throws NullPointerException thrown if, and only if, {@code alternation} is {@code null}
	 */
	public Group(final Alternation alternation) {
		this(alternation, Repetition.ONE);
	}
	
	/**
	 * Constructs a new {@code Group} instance.
	 * <p>
	 * If either {@code alternation} or {@code repetition} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param alternation the {@link Alternation} associated with this {@code Group} instance
	 * @param repetition the {@link Repetition} associated with this {@code Group} instance
	 * @throws NullPointerException thrown if, and only if, either {@code alternation} or {@code repetition} are {@code null}
	 */
	public Group(final Alternation alternation, final Repetition repetition) {
		this.alternation = Objects.requireNonNull(alternation, "alternation == null");
		this.repetition = Objects.requireNonNull(repetition, "repetition == null");
		this.sourceCode = doCreateSourceCode(this.alternation, this.repetition);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Alternation} associated with this {@code Group} instance.
	 * 
	 * @return the {@code Alternation} associated with this {@code Group} instance
	 */
	public Alternation getAlternation() {
		return this.alternation;
	}
	
	/**
	 * Writes this {@code Group} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * group.write(new Document());
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
	 * Writes this {@code Group} to {@code document}.
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
		document.line("Group {");
		document.indent();
		
		getAlternation().write(document);
		
		document.outdent();
		document.line("}");
		
		return document;
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
	 * group.match(input, 0);
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
		
		final Repetition repetition = getRepetition();
		
		final int minimumRepetition = repetition.getMinimum();
		final int maximumRepetition = repetition.getMaximum();
		
		int currentIndex = index;
		int currentRepetition = 0;
		
		int length = 0;
		
		for(int i = 1; i <= maximumRepetition && currentIndex < input.length(); i++) {
			final MatchResult currentMatchResult = getAlternation().match(input, currentIndex);
			
			if(currentMatchResult.isMatching()) {
				currentIndex += currentMatchResult.getLength();
				currentRepetition++;
				
				length += currentMatchResult.getLength();
				
				matchResults.add(currentMatchResult);
			} else {
				break;
			}
		}
		
		if(currentRepetition >= minimumRepetition) {
			return new MatchResult(this, input, true, index, index + length, matchResults);
		}
		
		return new MatchResult(this, input, false, index);
	}
	
	/**
	 * Returns the {@link Repetition} associated with this {@code Group} instance.
	 * 
	 * @return the {@code Repetition} associated with this {@code Group} instance
	 */
	public Repetition getRepetition() {
		return this.repetition;
	}
	
	/**
	 * Returns the source code associated with this {@code Group} instance.
	 * 
	 * @return the source code associated with this {@code Group} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Group} instance.
	 * 
	 * @return a {@code String} representation of this {@code Group} instance
	 */
	@Override
	public String toString() {
		return String.format("new Group(%s, %s)", getAlternation(), getRepetition());
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
				if(!this.alternation.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code Group} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Group}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Group} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Group}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Group)) {
			return false;
		} else if(!Objects.equals(getSourceCode(), Group.class.cast(object).getSourceCode())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Group} instance.
	 * 
	 * @return a hash code for this {@code Group} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getSourceCode());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doCreateSourceCode(final Alternation alternation, final Repetition repetition) {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("(");
		stringBuilder.append(alternation.getSourceCode());
		stringBuilder.append(")");
		stringBuilder.append(repetition.getSourceCode());
		
		return stringBuilder.toString();
	}
}