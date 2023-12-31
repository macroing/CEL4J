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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * An {@code ArrayValueUnion} represents an unnamed {@code array_value} {@code union} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The unnamed {@code array_value} {@code union} structure has the following format:
 * <pre>
 * <code>
 * {
 *     u2 num_values;
 *     element_value[num_values] values;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ArrayValueUnion implements Union {
	private final List<ElementValue> values;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new empty {@code ArrayValueUnion} instance.
	 */
	public ArrayValueUnion() {
		this.values = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ArrayValueUnion} instance.
	 * 
	 * @return a copy of this {@code ArrayValueUnion} instance
	 */
	@Override
	public ArrayValueUnion copy() {
		final ArrayValueUnion arrayValueUnion = new ArrayValueUnion();
		
		for(final ElementValue value : this.values) {
			arrayValueUnion.addValue(value);
		}
		
		return arrayValueUnion;
	}
	
	/**
	 * Returns a {@code List} with all currently added {@link ElementValue} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code ArrayValueUnion} instance.
	 * 
	 * @return a {@code List} with all currently added {@code ElementValue} instances
	 */
	public List<ElementValue> getValues() {
		return new ArrayList<>(this.values);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ArrayValueUnion} instance.
	 * 
	 * @return a {@code String} representation of this {@code ArrayValueUnion} instance
	 */
	@Override
	public String toString() {
		return "new ArrayValueUnion()";
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
				for(final ElementValue value : this.values) {
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
	 * Compares {@code object} to this {@code ArrayValueUnion} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ArrayValueUnion}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ArrayValueUnion} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ArrayValueUnion}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ArrayValueUnion)) {
			return false;
		} else if(!Objects.equals(this.values, ArrayValueUnion.class.cast(object).values)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the length of this {@code ArrayValueUnion} instance.
	 * 
	 * @return the length of this {@code ArrayValueUnion} instance
	 */
	@Override
	public int getLength() {
		int length = 2;
		
		for(final ElementValue value : this.values) {
			length += value.getLength();
		}
		
		return length;
	}
	
	/**
	 * Returns the value of the {@code num_values} item associated with this {@code ArrayValueUnion} instance.
	 * 
	 * @return the value of the {@code num_values} item associated with this {@code ArrayValueUnion} instance
	 */
	public int getNumValues() {
		return this.values.size();
	}
	
	/**
	 * Returns a hash code for this {@code ArrayValueUnion} instance.
	 * 
	 * @return a hash code for this {@code ArrayValueUnion} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.values);
	}
	
	/**
	 * Adds {@code value} to this {@code ArrayValueUnion} instance.
	 * <p>
	 * If {@code value} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param value the {@link ElementValue} to add
	 * @throws NullPointerException thrown if, and only if, {@code value} is {@code null}
	 */
	public void addValue(final ElementValue value) {
		this.values.add(Objects.requireNonNull(value, "value == null"));
	}
	
	/**
	 * Removes {@code value} from this {@code ArrayValueUnion} instance.
	 * <p>
	 * If {@code value} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param value the {@link ElementValue} to remove
	 * @throws NullPointerException thrown if, and only if, {@code value} is {@code null}
	 */
	public void removeValue(final ElementValue value) {
		this.values.remove(Objects.requireNonNull(value, "value == null"));
	}
	
	/**
	 * Writes this {@code ArrayValueUnion} to {@code dataOutput}.
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
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getNumValues());
			
			for(final ElementValue value : this.values) {
				value.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}