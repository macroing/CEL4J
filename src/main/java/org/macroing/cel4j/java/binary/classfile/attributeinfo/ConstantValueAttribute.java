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
import java.util.Optional;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.FieldInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code ConstantValueAttribute} represents a {@code ConstantValue_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code ConstantValue_attribute} structure has the following format:
 * <pre>
 * <code>
 * ConstantValue_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 constantvalue_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantValueAttribute extends AttributeInfo {
	/**
	 * The name of the {@code ConstantValue_attribute} structure.
	 */
	public static final String NAME = "ConstantValue";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int constantValueIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantValueAttribute} instance that is a copy of {@code constantValueAttribute}.
	 * <p>
	 * If {@code constantValueAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param constantValueAttribute the {@code ConstantValueAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code constantValueAttribute} is {@code null}
	 */
	public ConstantValueAttribute(final ConstantValueAttribute constantValueAttribute) {
		super(NAME, constantValueAttribute.getAttributeNameIndex());
		
		this.constantValueIndex = constantValueAttribute.constantValueIndex;
	}
	
	/**
	 * Constructs a new {@code ConstantValueAttribute} instance.
	 * <p>
	 * If either {@code attributeNameIndex} or {@code constantValueIndex} are less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code ConstantValueAttribute} instance
	 * @param constantValueIndex the value for the {@code constantvalue_index} item associated with this {@code ConstantValueAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code attributeNameIndex} or {@code constantValueIndex} are less than {@code 1}
	 */
	public ConstantValueAttribute(final int attributeNameIndex, final int constantValueIndex) {
		super(NAME, attributeNameIndex);
		
		this.constantValueIndex = ParameterArguments.requireRange(constantValueIndex, 1, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantValueAttribute} instance.
	 * 
	 * @return a copy of this {@code ConstantValueAttribute} instance
	 */
	@Override
	public ConstantValueAttribute copy() {
		return new ConstantValueAttribute(this);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantValueAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantValueAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new ConstantValueAttribute(%s, %s)", Integer.toString(getAttributeNameIndex()), Integer.toString(getConstantValueIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code ConstantValueAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantValueAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConstantValueAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantValueAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantValueAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), ConstantValueAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != ConstantValueAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != ConstantValueAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getConstantValueIndex() != ConstantValueAttribute.class.cast(object).getConstantValueIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code ConstantValueAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code ConstantValueAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 2;
	}
	
	/**
	 * Returns the value of the {@code constantvalue_index} item associated with this {@code ConstantValueAttribute} instance.
	 * 
	 * @return the value of the {@code constantvalue_index} item associated with this {@code ConstantValueAttribute} instance
	 */
	public int getConstantValueIndex() {
		return this.constantValueIndex;
	}
	
	/**
	 * Returns a hash code for this {@code ConstantValueAttribute} instance.
	 * 
	 * @return a hash code for this {@code ConstantValueAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getConstantValueIndex()));
	}
	
	/**
	 * Sets {@code constantValueIndex} as the value for the {@code constantvalue_index} item associated with this {@code ConstantValueAttribute} instance.
	 * <p>
	 * If {@code constantValueIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param constantValueIndex the value for the {@code constantvalue_index} item associated with this {@code ConstantValueAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code constantValueIndex} is less than {@code 1}
	 */
	public void setConstantValueIndex(final int constantValueIndex) {
		this.constantValueIndex = ParameterArguments.requireRange(constantValueIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code ConstantValueAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(getConstantValueIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantValueAttribute} instances in {@code node}.
	 * <p>
	 * All {@code ConstantValueAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantValueAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantValueAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantValueAttribute.class);
	}
	
	/**
	 * Attempts to find a {@code ConstantValueAttribute} instance in {@code fieldInfo}.
	 * <p>
	 * Returns an {@code Optional} with the optional {@code ConstantValueAttribute} instance.
	 * <p>
	 * If {@code fieldInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param fieldInfo the {@link FieldInfo} to check in
	 * @return an {@code Optional} with the optional {@code ConstantValueAttribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code fieldInfo} is {@code null}
	 */
	public static Optional<ConstantValueAttribute> find(final FieldInfo fieldInfo) {
		return fieldInfo.getAttributeInfos().stream().filter(attributeInfo -> attributeInfo instanceof ConstantValueAttribute).map(attributeInfo -> ConstantValueAttribute.class.cast(attributeInfo)).findFirst();
	}
}