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
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.rex.Matcher.MatchInfo;
import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class GroupReference implements Matchable {
	private final AtomicReference<Group> group;
	private final String name;
	private final boolean isDefinition;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public GroupReference(final String name) {
		this.group = new AtomicReference<>();
		this.name = Objects.requireNonNull(name, "name == null");
		this.isDefinition = false;
	}
	
//	TODO: Add Javadocs!
	public GroupReference(final String name, final Group group) {
		this.group = new AtomicReference<>(Objects.requireNonNull(group, "group == null"));
		this.name = Objects.requireNonNull(name, "name == null");
		this.isDefinition = true;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public Matcher matcher(final String source) {
		return matcher(source, 0, 0);
	}
	
//	TODO: Add Javadocs!
	@Override
	public Matcher matcher(final String source, final int beginIndex, final int endIndex) {
		Objects.requireNonNull(source, "source == null");
		
		ParameterArguments.requireRange(beginIndex, 0, source.length(), "beginIndex");
		ParameterArguments.requireRange(endIndex, beginIndex, source.length(), "endIndex");
		
		final Optional<Group> optionalGroup = getGroup();
		
		if(optionalGroup.isPresent()) {
			return optionalGroup.get().matcher(source, beginIndex, endIndex);
		}
		
		final
		Matcher.Builder matcher_Builder = new Matcher.Builder();
		matcher_Builder.setBeginIndex(beginIndex);
		matcher_Builder.setCurrentCharacterMatch(0);
		matcher_Builder.setEndIndex(endIndex);
		matcher_Builder.setMatchInfo(MatchInfo.FAILURE);
		matcher_Builder.setMatchable(this);
		matcher_Builder.setMatching(false);
		matcher_Builder.setSource(source);
		
		return matcher_Builder.build();
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
		stringBuilder.append("$");
		stringBuilder.append(this.name);
		
		if(this.group != null) {
			stringBuilder.append("=");
			stringBuilder.append(this.group);
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code GroupReference} instance.
	 * 
	 * @return a {@code String} representation of this {@code GroupReference} instance
	 */
	@Override
	public String toString() {
		return "new GroupReference(...)";
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
	
//	TODO: Add Javadocs!
	public boolean hasGroup() {
		return this.group.get() != null;
	}
	
//	TODO: Add Javadocs!
	public boolean isDefinition() {
		return this.isDefinition;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getMaximumCharacterMatch() {
		final Optional<Group> optionalGroup = getGroup();
		
		if(optionalGroup.isPresent()) {
			return optionalGroup.get().getMaximumCharacterMatch();
		}
		
		return 0;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getMinimumCharacterMatch() {
		final Optional<Group> optionalGroup = getGroup();
		
		if(optionalGroup.isPresent()) {
			return optionalGroup.get().getMinimumCharacterMatch();
		}
		
		return 0;
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
	
//	TODO: Add Javadocs!
	public void setGroup(final Group group) {
		this.group.set(Objects.requireNonNull(group, "group == null"));
	}
}