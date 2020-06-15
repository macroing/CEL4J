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
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;

/**
 * A {@code DeprecatedAttribute} denotes a Deprecated_attribute structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class DeprecatedAttribute extends AttributeInfo {
	/**
	 * The name of the Deprecated_attribute structure.
	 */
	public static final String NAME = "Deprecated";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code DeprecatedAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the attribute_name_index of the new {@code DeprecatedAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
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
		return new DeprecatedAttribute(getAttributeNameIndex());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code DeprecatedAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code DeprecatedAttribute} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Deprecated_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		
		final String toString = stringBuilder.toString();
		
		return toString;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code DeprecatedAttribute}, and that {@code DeprecatedAttribute} instance is equal to this {@code DeprecatedAttribute} instance, {@code false}
	 * otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code DeprecatedAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code DeprecatedAttribute}, and that {@code DeprecatedAttribute} instance is equal to this {@code DeprecatedAttribute} instance, {@code false}
	 * otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof DeprecatedAttribute)) {
			return false;
		} else if(!Objects.equals(DeprecatedAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(DeprecatedAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(DeprecatedAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the attribute_length of this {@code DeprecatedAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code DeprecatedAttribute} instance
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
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code DeprecatedAttribute}s.
	 * <p>
	 * All {@code DeprecatedAttribute}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code DeprecatedAttribute}s
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