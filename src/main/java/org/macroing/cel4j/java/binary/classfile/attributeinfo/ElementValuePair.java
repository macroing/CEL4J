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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code ElementValuePair} represents an {@code element_value_pair} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is indirectly mutable and not thread-safe.
 * <p>
 * The {@code element_value_pair} structure has the following format:
 * <pre>
 * <code>
 * element_value_pair {
 *     u2 element_name_index;
 *     element_value value;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ElementValuePair implements Node {
	private final ElementValue value;
	private final int elementNameIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ElementValuePair} instance.
	 * <p>
	 * If {@code elementNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code value} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param elementNameIndex the value for the {@code element_name_index} item associated with this {@code ElementValuePair} instance
	 * @param value the value for the {@code value} item associated with this {@code ElementValuePair} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code elementNameIndex} is less than {@code 1}
	 * @throws NullPointerException thrown if, and only if, {@code value} is {@code null}
	 */
	public ElementValuePair(final int elementNameIndex, final ElementValue value) {
		this.elementNameIndex = ParameterArguments.requireRange(elementNameIndex, 1, Integer.MAX_VALUE, "elementNameIndex");
		this.value = Objects.requireNonNull(value, "value == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the value of the {@code value} item associated with this {@code ElementValuePair} instance.
	 * 
	 * @return the value of the {@code value} item associated with this {@code ElementValuePair} instance
	 */
	public ElementValue getValue() {
		return this.value;
	}
	
	/**
	 * Returns a copy of this {@code ElementValuePair} instance.
	 * 
	 * @return a copy of this {@code ElementValuePair} instance
	 */
	public ElementValuePair copy() {
		return new ElementValuePair(getElementNameIndex(), getValue().copy());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ElementValuePair} instance.
	 * 
	 * @return a {@code String} representation of this {@code ElementValuePair} instance
	 */
	@Override
	public String toString() {
		return String.format("new ElementValuePair(%s, %s)", Integer.toString(getElementNameIndex()), getValue());
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
	 * <li>traverse its child {@code Node}, an {@link ElementValue}.</li>
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
				if(!getValue().accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code ElementValuePair} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ElementValuePair}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ElementValuePair} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ElementValuePair}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ElementValuePair)) {
			return false;
		} else if(getElementNameIndex() != ElementValuePair.class.cast(object).getElementNameIndex()) {
			return false;
		} else if(!Objects.equals(getValue(), ElementValuePair.class.cast(object).getValue())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code element_name_index} item associated with this {@code ElementValuePair} instance.
	 * 
	 * @return the value of the {@code element_name_index} item associated with this {@code ElementValuePair} instance
	 */
	public int getElementNameIndex() {
		return this.elementNameIndex;
	}
	
	/**
	 * Returns the length of this {@code ElementValuePair} instance.
	 * 
	 * @return the length of this {@code ElementValuePair} instance
	 */
	public int getLength() {
		return 2 + getValue().getLength();
	}
	
	/**
	 * Returns a hash code for this {@code ElementValuePair} instance.
	 * 
	 * @return a hash code for this {@code ElementValuePair} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getElementNameIndex()), getValue());
	}
	
	/**
	 * Writes this {@code ElementValuePair} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an {@code IOException} is caught, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * This method does not close {@code dataOutput}.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an {@code IOException} is caught
	 */
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getElementNameIndex());
			
			final
			ElementValue elementValue = getValue();
			elementValue.write(dataOutput);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}