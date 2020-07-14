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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code GroupReference} is a {@link Matcher} that can match a {@link Group} defined by a {@link GroupReferenceDefinition}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class GroupReference implements Matcher {
	private final AtomicReference<Group> group;
	private final Repetition repetition;
	private final String name;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code GroupReference} instance.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new GroupReference(name, Repetition.ONE);
	 * }
	 * </pre>
	 * 
	 * @param name the name associated with this {@code GroupReference} instance
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public GroupReference(final String name) {
		this(name, Repetition.ONE);
	}
	
	/**
	 * Constructs a new {@code GroupReference} instance.
	 * <p>
	 * If either {@code name} or {@code repetition} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the name associated with this {@code GroupReference} instance
	 * @param repetition the {@link Repetition} associated with this {@code GroupReference} instance
	 * @throws NullPointerException thrown if, and only if, either {@code name} or {@code repetition} are {@code null}
	 */
	public GroupReference(final String name, final Repetition repetition) {
		this.group = new AtomicReference<>();
		this.repetition = Objects.requireNonNull(repetition, "repetition == null");
		this.name = Objects.requireNonNull(name, "name == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code GroupReference} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * groupReference.write(new Document());
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
	 * Writes this {@code GroupReference} to {@code document}.
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
		document.linef("GroupReference %s;", this.name);
		
		return document;
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
	 * groupReference.match(source, 0);
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
		
		final Repetition repetition = getRepetition();
		
		final int minimumRepetition = repetition.getMinimum();
		final int maximumRepetition = repetition.getMaximum();
		
		int currentIndex = index;
		int currentRepetition = 0;
		
		int length = 0;
		
		for(int i = minimumRepetition; i <= maximumRepetition && currentIndex < source.length(); i++) {
			final MatchResult currentMatchResult = getGroup().get().match(source, currentIndex);
			
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
			return new MatchResult(this, source, true, index, index + length, matchResults);
		}
		
		return new MatchResult(this, source, false, index);
	}
	
	/**
	 * Returns an {@code Optional} with the optional {@link Group} associated with this {@code GroupReference} instance.
	 * 
	 * @return an {@code Optional} with the optional {@code Group} associated with this {@code GroupReference} instance
	 */
	public Optional<Group> getGroup() {
		return Optional.ofNullable(this.group.get());
	}
	
	/**
	 * Returns the {@link Repetition} associated with this {@code GroupReference} instance.
	 * 
	 * @return the {@code Repetition} associated with this {@code GroupReference} instance
	 */
	public Repetition getRepetition() {
		return this.repetition;
	}
	
	/**
	 * Returns the name associated with this {@code GroupReference} instance.
	 * 
	 * @return the name associated with this {@code GroupReference} instance
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the source associated with this {@code GroupReference} instance.
	 * 
	 * @return the source associated with this {@code GroupReference} instance
	 */
	@Override
	public String getSource() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<");
		stringBuilder.append(this.name);
		stringBuilder.append(">");
		stringBuilder.append(this.repetition.getSource());
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code GroupReference} instance.
	 * 
	 * @return a {@code String} representation of this {@code GroupReference} instance
	 */
	@Override
	public String toString() {
		return String.format("new GroupReference(\"%s\", %s)", getName(), getRepetition());
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
				final Optional<Group> optionalGroup = getGroup();
				
				if(optionalGroup.isPresent() && !optionalGroup.get().accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code GroupReference} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code GroupReference}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code GroupReference} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code GroupReference}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof GroupReference)) {
			return false;
		} else if(!Objects.equals(getSource(), GroupReference.class.cast(object).getSource())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code GroupReference} instance is associated with a {@code Group}, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code GroupReference} instance is associated with a {@code Group}, {@code false} otherwise
	 */
	public boolean hasGroup() {
		return this.group.get() != null;
	}
	
	/**
	 * Returns a hash code for this {@code GroupReference} instance.
	 * 
	 * @return a hash code for this {@code GroupReference} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getSource());
	}
	
	/**
	 * Sets {@code group} as the {@link Group} associated with this {@code GroupReference} instance.
	 * <p>
	 * If {@code group} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param group the {@code Group} associated with this {@code GroupReference} instance
	 * @throws NullPointerException thrown if, and only if, {@code group} is {@code null}
	 */
	public void setGroup(final Group group) {
		this.group.set(Objects.requireNonNull(group, "group == null"));
	}
}