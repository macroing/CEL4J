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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;

/**
 * A {@code DeprecatedAttribute} represents a {@code Deprecated_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code ConstantValue_attribute} structure has the following format:
 * <pre>
 * <code>
 * Deprecated_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class DeprecatedAttribute extends AttributeInfo {
	/**
	 * The name of the {@code Deprecated_attribute} structure.
	 */
	public static final String NAME = "Deprecated";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code DeprecatedAttribute} instance that is a copy of {@code deprecatedAttribute}.
	 * <p>
	 * If {@code deprecatedAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param deprecatedAttribute the {@code DeprecatedAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code deprecatedAttribute} is {@code null}
	 */
	public DeprecatedAttribute(final DeprecatedAttribute deprecatedAttribute) {
		super(NAME, deprecatedAttribute.getAttributeNameIndex());
	}
	
	/**
	 * Constructs a new {@code DeprecatedAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code DeprecatedAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public DeprecatedAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code DeprecatedAttribute} instance.
	 * 
	 * @return a copy of this {@code DeprecatedAttribute} instance
	 */
	@Override
	public DeprecatedAttribute copy() {
		return new DeprecatedAttribute(this);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code DeprecatedAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code DeprecatedAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new DeprecatedAttribute(%s)", Integer.toString(getAttributeNameIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code DeprecatedAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code DeprecatedAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code DeprecatedAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code DeprecatedAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof DeprecatedAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), DeprecatedAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != DeprecatedAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != DeprecatedAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code DeprecatedAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code DeprecatedAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 0;
	}
	
	/**
	 * Returns a hash code for this {@code DeprecatedAttribute} instance.
	 * 
	 * @return a hash code for this {@code DeprecatedAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()));
	}
	
	/**
	 * Writes this {@code DeprecatedAttribute} to {@code dataOutput}.
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
	 * Returns a {@code List} with all {@code DeprecatedAttribute} instances in {@code node}.
	 * <p>
	 * All {@code DeprecatedAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code DeprecatedAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<DeprecatedAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), DeprecatedAttribute.class);
	}
	
	/**
	 * Attempts to find a {@code DeprecatedAttribute} instance in {@code methodInfo}.
	 * <p>
	 * Returns an {@code Optional} with the optional {@code DeprecatedAttribute} instance.
	 * <p>
	 * If {@code methodInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param methodInfo the {@link MethodInfo} to check in
	 * @return an {@code Optional} with the optional {@code DeprecatedAttribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code methodInfo} is {@code null}
	 */
	public static Optional<DeprecatedAttribute> find(final MethodInfo methodInfo) {
		return methodInfo.getAttributeInfos().stream().filter(attributeInfo -> attributeInfo instanceof DeprecatedAttribute).map(attributeInfo -> DeprecatedAttribute.class.cast(attributeInfo)).findFirst();
	}
}