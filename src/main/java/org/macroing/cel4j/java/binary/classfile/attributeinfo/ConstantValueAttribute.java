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
 * A {@code ConstantValueAttribute} denotes a ConstantValue_attribute structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantValueAttribute extends AttributeInfo {
	/**
	 * The name of the ConstantValue_attribute structure.
	 */
	public static final String NAME = "ConstantValue";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int constantValueIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ConstantValueAttribute(final int attributeNameIndex, final int constantValueIndex) {
		super(NAME, attributeNameIndex);
		
		this.constantValueIndex = constantValueIndex;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantValueAttribute} instance.
	 * 
	 * @return a copy of this {@code ConstantValueAttribute} instance
	 */
	@Override
	public ConstantValueAttribute copy() {
		return new ConstantValueAttribute(getAttributeNameIndex(), this.constantValueIndex);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantValueAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantValueAttribute} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ConstantValue_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		stringBuilder.append(" ");
		stringBuilder.append("constantvalue_index=" + this.constantValueIndex);
		
		final String toString = stringBuilder.toString();
		
		return toString;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantValueAttribute}, and that {@code ConstantValueAttribute} instance is equal to this {@code ConstantValueAttribute} instance,
	 * {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code ConstantValueAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantValueAttribute}, and that {@code ConstantValueAttribute} instance is equal to this {@code ConstantValueAttribute} instance,
	 * {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantValueAttribute)) {
			return false;
		} else if(!Objects.equals(ConstantValueAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(ConstantValueAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(ConstantValueAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(ConstantValueAttribute.class.cast(object).getConstantValueIndex() != getConstantValueIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the attribute_length of this {@code ConstantValueAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code ConstantValueAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 2;
	}
	
	/**
	 * Returns the constantvalue_index of this {@code ConstantValueAttribute} instance.
	 * 
	 * @return the constantvalue_index of this {@code ConstantValueAttribute} instance
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
	 * Sets a new constantvalue_index for this {@code ConstantValueAttribute} instance.
	 * <p>
	 * If {@code constantValueIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param constantValueIndex the new constantvalue_index for this {@code ConstantValueAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code constantValueIndex} is less than or equal to {@code 0}
	 */
	public void setConstantValueIndex(final int constantValueIndex) {
		this.constantValueIndex = ParameterArguments.requireRange(constantValueIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code ConstantValueAttribute} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is an {@code OutputStream} (or any other type of stream), this method will not close it.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O-error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O-error occurs
	 */
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getAttributeNameIndex());
			dataOutput.writeInt(getAttributeLength());
			dataOutput.writeShort(this.constantValueIndex);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code ConstantValueAttribute} instance.
	 * <p>
	 * If either {@code attributeNameIndex} or {@code constantValueIndex} are less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the attribute_name_index of the new {@code ConstantValueAttribute} instance
	 * @param constantValueIndex the constantvalue_index of the new {@code ConstantValueAttribute} instance
	 * @return a new {@code ConstantValueAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code attributeNameIndex} or {@code constantValueIndex} are less than or equal to {@code 0}
	 */
	public static ConstantValueAttribute newInstance(final int attributeNameIndex, final int constantValueIndex) {
		return new ConstantValueAttribute(ParameterArguments.requireRange(attributeNameIndex, 1, Integer.MAX_VALUE), ParameterArguments.requireRange(constantValueIndex, 1, Integer.MAX_VALUE));
	}
	
	/**
	 * Returns a {@code List} with all {@code ConstantValueAttribute}s.
	 * <p>
	 * All {@code ConstantValueAttribute}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantValueAttribute}s
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