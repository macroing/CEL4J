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
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;

/**
 * A {@code SyntheticAttribute} represents a {@code Synthetic_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code Synthetic_attribute} structure has the following format:
 * <pre>
 * <code>
 * Synthetic_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class SyntheticAttribute extends AttributeInfo {
	/**
	 * The name of the {@code Synthetic_attribute} structure.
	 */
	public static final String NAME = "Synthetic";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code SyntheticAttribute} instance that is a copy of {@code syntheticAttribute}.
	 * <p>
	 * If {@code syntheticAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param syntheticAttribute the {@code SyntheticAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code syntheticAttribute} is {@code null}
	 */
	public SyntheticAttribute(final SyntheticAttribute syntheticAttribute) {
		super(NAME, syntheticAttribute.getAttributeNameIndex());
	}
	
	/**
	 * Constructs a new {@code SyntheticAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code SyntheticAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public SyntheticAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code SyntheticAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code SyntheticAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new SyntheticAttribute(%s)", Integer.toString(getAttributeNameIndex()));
	}
	
	/**
	 * Returns a copy of this {@code SyntheticAttribute} instance.
	 * 
	 * @return a copy of this {@code SyntheticAttribute} instance
	 */
	@Override
	public SyntheticAttribute copy() {
		return new SyntheticAttribute(this);
	}
	
	/**
	 * Compares {@code object} to this {@code SyntheticAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code SyntheticAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code SyntheticAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code SyntheticAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof SyntheticAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), SyntheticAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != SyntheticAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != SyntheticAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code SyntheticAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code SyntheticAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 0;
	}
	
	/**
	 * Returns a hash code for this {@code SyntheticAttribute} instance.
	 * 
	 * @return a hash code for this {@code SyntheticAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()));
	}
	
	/**
	 * Writes this {@code SyntheticAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(getAttributeNameIndex());
			dataOutput.writeInt(getAttributeLength());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code SyntheticAttribute} instances in {@code node}.
	 * <p>
	 * All {@code SyntheticAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code SyntheticAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<SyntheticAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), SyntheticAttribute.class);
	}
}