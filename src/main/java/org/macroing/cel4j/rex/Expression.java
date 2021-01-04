/**
 * Copyright 2009 - 2021 J&#246;rgen Lundgren
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
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code Expression} is a {@link Matcher} that can match an {@link Alternation} instance.
 * <p>
 * This class is indirectly mutable and not thread-safe.
 * <p>
 * An {@code Expression} consists of a single {@code Alternation} instance.
 * <p>
 * To use an {@code Expression} in Rex, consider the following example:
 * <pre>
 * {@code
 * "A" & "B" | "C" & "D"
 * }
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Expression implements Matcher {
	private final Alternation alternation;
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Expression} instance.
	 * <p>
	 * If {@code alternation} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param alternation the {@link Alternation} associated with this {@code Expression} instance
	 * @throws NullPointerException thrown if, and only if, {@code alternation} is {@code null}
	 */
	public Expression(final Alternation alternation) {
		this.alternation = Objects.requireNonNull(alternation, "alternation == null");
		this.sourceCode = alternation.getSourceCode();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Alternation} associated with this {@code Expression} instance.
	 * 
	 * @return the {@code Alternation} associated with this {@code Expression} instance
	 */
	public Alternation getAlternation() {
		return this.alternation;
	}
	
	/**
	 * Writes this {@code Expression} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * expression.write(new Document());
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
	 * Writes this {@code Expression} to {@code document}.
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
		document.line("Expression {");
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
	 * expression.match(input, 0);
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
		
		final MatchResult matchResult = getAlternation().match(input, index);
		
		if(matchResult.isMatching()) {
			return new MatchResult(this, input, true, index, index + matchResult.getLength(), Arrays.asList(matchResult));
		}
		
		return new MatchResult(this, input, false, index);
	}
	
	/**
	 * Returns the source code associated with this {@code Expression} instance.
	 * 
	 * @return the source code associated with this {@code Expression} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Expression} instance.
	 * 
	 * @return a {@code String} representation of this {@code Expression} instance
	 */
	@Override
	public String toString() {
		return String.format("new Expression(%s)", getAlternation());
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
				if(!getAlternation().accept(nodeHierarchicalVisitor)) {
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
		} else if(!Objects.equals(getSourceCode(), Expression.class.cast(object).getSourceCode())) {
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
		return Objects.hash(getSourceCode());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code Expression} that can match Rex itself.
	 * 
	 * @return an {@code Expression} that can match Rex itself
	 */
	public static Expression newRexExpression() {
		return parse(doCreateInputForRex());
	}
	
	/**
	 * Parses {@code file} into an {@code Expression} instance.
	 * <p>
	 * Returns an {@code Expression} instance.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code file} cannot be read, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * If {@code file} contains malformed text, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param file the {@code File} to parse
	 * @return an {@code Expression} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code file} contains malformed text
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, {@code file} cannot be read
	 */
	public static Expression parse(final File file) {
		return RexParser.parse(Objects.requireNonNull(file, "file == null"));
	}
	
	/**
	 * Parses {@code input} into an {@code Expression} instance.
	 * <p>
	 * Returns an {@code Expression} instance.
	 * <p>
	 * If {@code input} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code input} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param input the {@code String} to parse
	 * @return an {@code Expression} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code input} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code input} is {@code null}
	 */
	public static Expression parse(final String input) {
		return RexParser.parse(Objects.requireNonNull(input, "input == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doCreateInputForRex() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<Alternation> = (<Concatenation> & (%CommentOrWhitespace%* & '|' & %CommentOrWhitespace%* & <Concatenation>)*);");
		stringBuilder.append("<Concatenation> = (<Matcher> & (%CommentOrWhitespace%* & ('&' | ',' | ';') & %CommentOrWhitespace%* & <Matcher>)*);");
		stringBuilder.append("<Expression> = (<Alternation>);");
		stringBuilder.append("<Group> = (%CommentOrWhitespace%* & '(' & %CommentOrWhitespace%* & <Alternation> & %CommentOrWhitespace%* & ')' & %CommentOrWhitespace%* & <Repetition>?);");
		stringBuilder.append("<GroupReference> = (%CommentOrWhitespace%* & '<' & %CommentOrWhitespace%* & (%JavaIdentifier% | %Digit%+) & %CommentOrWhitespace%* & '>' & %CommentOrWhitespace%* & <Repetition>?);");
		stringBuilder.append("<GroupReferenceDefinition> = (%CommentOrWhitespace%* & '<' & %CommentOrWhitespace%* & %JavaIdentifier% & %CommentOrWhitespace%* & '>' & %CommentOrWhitespace%* & '=' & %CommentOrWhitespace%* & <Group>);");
		stringBuilder.append("<Matcher> = (<Group> | <GroupReferenceDefinition> | <GroupReference> | <Regex> | <Symbol> | <SymbolClass>);");
		stringBuilder.append("<Regex> = (%CommentOrWhitespace%* & %RegexLiteral%);");
		stringBuilder.append("<Repetition> = (%CommentOrWhitespace%* & ('*' | '+' | '?'));");
		stringBuilder.append("<Symbol> = (%CommentOrWhitespace%* & (%CharacterLiteral% | %StringLiteral%) & %CommentOrWhitespace%* & <Repetition>?);");
		stringBuilder.append("<SymbolClass> = (%CommentOrWhitespace%* & '%' & %JavaIdentifier% & '%' & %CommentOrWhitespace%* & <Repetition>?);");
		stringBuilder.append("<Expression>");
		
		return stringBuilder.toString();
	}
}