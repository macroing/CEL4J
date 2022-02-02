/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
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
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code NestHostAttribute} represents a {@code NestHost_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code NestHost_attribute} structure has the following format:
 * <pre>
 * <code>
 * NestHost_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 host_class_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class NestHostAttribute extends AttributeInfo {
	/**
	 * The name of the {@code NestHost_attribute} structure.
	 */
	public static final String NAME = "NestHost";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int hostClassIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code NestHostAttribute} instance that is a copy of {@code nestHostAttribute}.
	 * <p>
	 * If {@code nestHostAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param nestHostAttribute the {@code NestHostAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code nestHostAttribute} is {@code null}
	 */
	public NestHostAttribute(final NestHostAttribute nestHostAttribute) {
		super(NAME, nestHostAttribute.getAttributeNameIndex());
		
		this.hostClassIndex = nestHostAttribute.hostClassIndex;
	}
	
	/**
	 * Constructs a new {@code NestHostAttribute} instance.
	 * <p>
	 * If either {@code attributeNameIndex} or {@code hostClassIndex} are less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code NestHostAttribute} instance
	 * @param hostClassIndex the value for the {@code host_class_index} item associated with this {@code NestHostAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code attributeNameIndex} or {@code hostClassIndex} are less than {@code 1}
	 */
	public NestHostAttribute(final int attributeNameIndex, final int hostClassIndex) {
		super(NAME, attributeNameIndex);
		
		this.hostClassIndex = ParameterArguments.requireRange(hostClassIndex, 1, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code NestHostAttribute} instance.
	 * 
	 * @return a copy of this {@code NestHostAttribute} instance
	 */
	@Override
	public NestHostAttribute copy() {
		return new NestHostAttribute(this);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code NestHostAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code NestHostAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new NestHostAttribute(%s, %s)", Integer.toString(getAttributeNameIndex()), Integer.toString(getHostClassIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code NestHostAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code NestHostAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code NestHostAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code NestHostAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof NestHostAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), NestHostAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != NestHostAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != NestHostAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getHostClassIndex() != NestHostAttribute.class.cast(object).getHostClassIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code NestHostAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code NestHostAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 2;
	}
	
	/**
	 * Returns the value of the {@code host_class_index} item associated with this {@code NestHostAttribute} instance.
	 * 
	 * @return the value of the {@code host_class_index} item associated with this {@code NestHostAttribute} instance
	 */
	public int getHostClassIndex() {
		return this.hostClassIndex;
	}
	
	/**
	 * Returns a hash code for this {@code NestHostAttribute} instance.
	 * 
	 * @return a hash code for this {@code NestHostAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getHostClassIndex()));
	}
	
	/**
	 * Sets {@code hostClassIndex} as the value for the {@code host_class_index} item associated with this {@code NestHostAttribute} instance.
	 * <p>
	 * If {@code hostClassIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param hostClassIndex the value for the {@code host_class_index} item associated with this {@code NestHostAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code hostClassIndex} is less than {@code 1}
	 */
	public void setHostClassIndex(final int hostClassIndex) {
		this.hostClassIndex = ParameterArguments.requireRange(hostClassIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code NestHostAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(getHostClassIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code NestHostAttribute} instances in {@code node}.
	 * <p>
	 * All {@code NestHostAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code NestHostAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<NestHostAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), NestHostAttribute.class);
	}
}