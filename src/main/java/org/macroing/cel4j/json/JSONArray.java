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
package org.macroing.cel4j.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * A {@code JSONArray} denotes a JSON array.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class JSONArray implements JSONType {
	private final List<JSONType> values;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new empty {@code JSONArray} instance.
	 */
	public JSONArray() {
		this.values = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@link JSONType}s added to this {@code JSONArray} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code JSONArray} instance.
	 * 
	 * @return a {@code List} with all {@code JSONType}s added to this {@code JSONArray} instance
	 */
	public List<JSONType> getValues() {
		return new ArrayList<>(this.values);
	}
	
	/**
	 * Returns a {@code String} with the source code of this {@code JSONArray} instance.
	 * 
	 * @return a {@code String} with the source code of this {@code JSONArray} instance
	 */
	@Override
	public String toSourceCode() {
		return getValues().stream().map(value -> value.toSourceCode()).collect(Collectors.joining(",", "[", "]"));
	}
	
	/**
	 * Returns a {@code String} representation of this {@code JSONArray} instance.
	 * 
	 * @return a {@code String} representation of this {@code JSONArray} instance
	 */
	@Override
	public String toString() {
		return "new JSONArray()";
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
	 * <li>traverse child {@code Node}s, if it has any.</li>
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
				for(final JSONType value : this.values) {
					if(!value.accept(nodeHierarchicalVisitor)) {
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
	 * Compares {@code object} to this {@code JSONArray} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code JSONArray}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code JSONArray} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code JSONArray}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JSONArray)) {
			return false;
		} else if(!Objects.equals(getValues(), JSONArray.class.cast(object).getValues())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code JSONArray} instance.
	 * 
	 * @return a hash code for this {@code JSONArray} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getValues());
	}
	
	/**
	 * Adds {@code value} to this {@code JSONArray} instance.
	 * <p>
	 * If {@code value} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param value the {@link JSONType} to add
	 * @throws NullPointerException thrown if, and only if, {@code value} is {@code null}
	 */
	public void addValue(final JSONType value) {
		this.values.add(Objects.requireNonNull(value, "value == null"));
	}
	
	/**
	 * Removes {@code value} from this {@code JSONArray} instance.
	 * <p>
	 * If {@code value} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param value the {@link JSONType} to remove
	 * @throws NullPointerException thrown if, and only if, {@code value} is {@code null}
	 */
	public void removeValue(final JSONType value) {
		this.values.remove(Objects.requireNonNull(value, "value == null"));
	}
}