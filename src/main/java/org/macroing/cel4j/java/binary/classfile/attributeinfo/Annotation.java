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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code Annotation} denotes an {@code annotation} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code annotation} structure has the following format:
 * <pre>
 * <code>
 * annotation {
 *     u2 type_index;
 *     u2 num_element_value_pairs;
 *     element_value_pair[num_element_value_pairs] element_value_pairs;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Annotation implements Node {
	private final List<ElementValuePair> elementValuePairs;
	private int typeIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Annotation} instance with {@code typeIndex} as the value for the associated {@code type_index} item.
	 * <p>
	 * If {@code typeIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param typeIndex the value for the {@code type_index} item associated with this {@code Annotation} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code typeIndex} is less than {@code 1}
	 */
	public Annotation(final int typeIndex) {
		this.elementValuePairs = new ArrayList<>();
		this.typeIndex = ParameterArguments.requireRange(typeIndex, 1, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code Annotation} instance.
	 * 
	 * @return a copy of this {@code Annotation} instance
	 */
	public Annotation copy() {
		final Annotation annotation = new Annotation(getTypeIndex());
		
		for(final ElementValuePair elementValuePair : this.elementValuePairs) {
			annotation.addElementValuePair(elementValuePair.copy());
		}
		
		return annotation;
	}
	
	/**
	 * Writes this {@code Annotation} to {@code dataOutput}.
	 * <p>
	 * Returns {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an {@code IOException} is caught, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * This method does not close {@code dataOutput}.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @return {@code dataOutput}
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an {@code IOException} is caught
	 */
	public DataOutput write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getTypeIndex());
			dataOutput.writeShort(getNumElementValuePairs());
			
			for(final ElementValuePair elementValuePair : this.elementValuePairs) {
				elementValuePair.write(dataOutput);
			}
			
			return dataOutput;
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Returns a {@code List} with all {@link ElementValuePair} instances associated with this {@code Annotation} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Annotation} instance.
	 * 
	 * @return a {@code List} with all {@code ElementValuePair} instances associated with this {@code Annotation} instance
	 */
	public List<ElementValuePair> getElementValuePairs() {
		return new ArrayList<>(this.elementValuePairs);
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
	 * <li>traverse its child {@code Node} instances.</li>
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
				for(final ElementValuePair elementValuePair : this.elementValuePairs) {
					if(!elementValuePair.accept(nodeHierarchicalVisitor)) {
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
	 * Compares {@code object} to this {@code Annotation} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Annotation}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Annotation} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Annotation}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Annotation)) {
			return false;
		} else if(!Objects.equals(this.elementValuePairs, Annotation.class.cast(object).elementValuePairs)) {
			return false;
		} else if(getTypeIndex() != Annotation.class.cast(object).getTypeIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the length of this {@code Annotation} instance.
	 * 
	 * @return the length of this {@code Annotation} instance
	 */
	public int getLength() {
		int length = 4;
		
		for(final ElementValuePair elementValuePair : this.elementValuePairs) {
			length += elementValuePair.getLength();
		}
		
		return length;
	}
	
	/**
	 * Returns the number of {@link ElementValuePair} instances contained in this {@code Annotation} instance.
	 * <p>
	 * This method refers to the value of the {@code num_element_value_pairs} item.
	 * 
	 * @return the number of {@code ElementValuePair} instances contained in this {@code Annotation} instance
	 */
	public int getNumElementValuePairs() {
		return this.elementValuePairs.size();
	}
	
	/**
	 * Returns the value of the {@code type_index} item associated with this {@code Annotation} instance.
	 * 
	 * @return the value of the {@code type_index} item associated with this {@code Annotation} instance
	 */
	public int getTypeIndex() {
		return this.typeIndex;
	}
	
	/**
	 * Returns a hash code for this {@code Annotation} instance.
	 * 
	 * @return a hash code for this {@code Annotation} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.elementValuePairs, Integer.valueOf(getTypeIndex()));
	}
	
	/**
	 * Adds {@code elementValuePair} to this {@code Annotation} instance.
	 * <p>
	 * If {@code elementValuePair} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param elementValuePair the {@link ElementValuePair} to add
	 * @throws NullPointerException thrown if, and only if, {@code elementValuePair} is {@code null}
	 */
	public void addElementValuePair(final ElementValuePair elementValuePair) {
		this.elementValuePairs.add(Objects.requireNonNull(elementValuePair, "elementValuePair == null"));
	}
	
	/**
	 * Removes {@code elementValuePair} from this {@code Annotation} instance.
	 * <p>
	 * If {@code elementValuePair} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param elementValuePair the {@link ElementValuePair} to remove
	 * @throws NullPointerException thrown if, and only if, {@code elementValuePair} is {@code null}
	 */
	public void removeElementValuePair(final ElementValuePair elementValuePair) {
		this.elementValuePairs.remove(Objects.requireNonNull(elementValuePair, "elementValuePair == null"));
	}
	
	/**
	 * Sets {@code typeIndex} as the value for the {@code type_index} item associated with this {@code Annotation} instance.
	 * <p>
	 * If {@code typeIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param typeIndex the value for the {@code type_index} item associated with this {@code Annotation} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code typeIndex} is less than {@code 1}
	 */
	public void setTypeIndex(final int typeIndex) {
		this.typeIndex = ParameterArguments.requireRange(typeIndex, 1, Integer.MAX_VALUE, "typeIndex");
	}
}