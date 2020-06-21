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

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code EnclosingMethodAttribute} represents an {@code EnclosingMethod_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code EnclosingMethod_attribute} structure has the following format:
 * <pre>
 * <code>
 * EnclosingMethod_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 class_index;
 *     u2 method_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class EnclosingMethodAttribute extends AttributeInfo {
	/**
	 * The name of the {@code EnclosingMethod_attribute} structure.
	 */
	public static final String NAME = "EnclosingMethod";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int classIndex;
	private int methodIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code EnclosingMethodAttribute} instance.
	 * <p>
	 * If either {@code attributeNameIndex} or {@code classIndex} are less than {@code 1}, or {@code methodIndex} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code EnclosingMethodAttribute} instance
	 * @param classIndex the value for the {@code class_index} item associated with this {@code EnclosingMethodAttribute} instance
	 * @param methodIndex the value for the {@code method_index} item associated with this {@code EnclosingMethodAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code attributeNameIndex} or {@code classIndex} are less than {@code 1}, or {@code methodIndex} is less than {@code 0}
	 */
	public EnclosingMethodAttribute(final int attributeNameIndex, final int classIndex, final int methodIndex) {
		super(NAME, attributeNameIndex);
		
		this.classIndex = ParameterArguments.requireRange(classIndex, 1, Integer.MAX_VALUE);
		this.methodIndex = ParameterArguments.requireRange(methodIndex, 0, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code EnclosingMethodAttribute} instance.
	 * 
	 * @return a copy of this {@code EnclosingMethodAttribute} instance
	 */
	@Override
	public EnclosingMethodAttribute copy() {
		return new EnclosingMethodAttribute(getAttributeNameIndex(), this.classIndex, this.methodIndex);
	}
	
	/**
	 * Compares {@code object} to this {@code EnclosingMethodAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code EnclosingMethodAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code EnclosingMethodAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code EnclosingMethodAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof EnclosingMethodAttribute)) {
			return false;
		} else if(!Objects.equals(EnclosingMethodAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(EnclosingMethodAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(EnclosingMethodAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(EnclosingMethodAttribute.class.cast(object).getClassIndex() != getClassIndex()) {
			return false;
		} else if(EnclosingMethodAttribute.class.cast(object).getMethodIndex() != getMethodIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code EnclosingMethodAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code EnclosingMethodAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 4;
	}
	
	/**
	 * Returns the value of the {@code class_index} item associated with this {@code EnclosingMethodAttribute} instance.
	 * 
	 * @return the value of the {@code class_index} item associated with this {@code EnclosingMethodAttribute} instance
	 */
	public int getClassIndex() {
		return this.classIndex;
	}
	
	/**
	 * Returns the value of the {@code method_index} item associated with this {@code EnclosingMethodAttribute} instance.
	 * 
	 * @return the value of the {@code method_index} item associated with this {@code EnclosingMethodAttribute} instance
	 */
	public int getMethodIndex() {
		return this.methodIndex;
	}
	
	/**
	 * Returns a hash code for this {@code EnclosingMethodAttribute} instance.
	 * 
	 * @return a hash code for this {@code EnclosingMethodAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getClassIndex()), Integer.valueOf(getMethodIndex()));
	}
	
	/**
	 * Sets {@code classIndex} as the value for the {@code class_index} item associated with this {@code EnclosingMethodAttribute} instance.
	 * <p>
	 * If {@code classIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param classIndex the value for the {@code class_index} item associated with this {@code EnclosingMethodAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code classIndex} is less than {@code 1}
	 */
	public void setClassIndex(final int classIndex) {
		this.classIndex = ParameterArguments.requireRange(classIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets {@code methodIndex} as the value for the {@code method_index} item associated with this {@code EnclosingMethodAttribute} instance.
	 * <p>
	 * If {@code methodIndex} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param methodIndex the value for the {@code method_index} item associated with this {@code EnclosingMethodAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code methodIndex} is less than {@code 0}
	 */
	public void setMethodIndex(final int methodIndex) {
		this.methodIndex = ParameterArguments.requireRange(methodIndex, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code EnclosingMethodAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(this.classIndex);
			dataOutput.writeShort(this.methodIndex);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code EnclosingMethodAttribute} instances in {@code node}.
	 * <p>
	 * All {@code EnclosingMethodAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code EnclosingMethodAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<EnclosingMethodAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), EnclosingMethodAttribute.class);
	}
}