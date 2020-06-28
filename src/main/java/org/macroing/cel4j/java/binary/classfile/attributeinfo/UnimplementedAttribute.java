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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;

/**
 * An {@code UnimplementedAttribute} represents a custom {@code Unimplemented_attribute} structure.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code Unimplemented_attribute} structure is not defined by the Java Virtual Machine Specifications. It is defined by this library to represent an {@code attribute_info} structure that does not have a corresponding implementation in this
 * library.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class UnimplementedAttribute extends AttributeInfo {
	private final byte[] info;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code UnimplementedAttribute} instance.
	 * <p>
	 * If either {@code name} or {@code info} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param name the name associated with this {@code UnimplementedAttribute} instance
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code UnimplementedAttribute} instance
	 * @param info the values for the {@code info} item associated with this {@code UnimplementedAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 * @throws NullPointerException thrown if, and only if, either {@code name} or {@code info} are {@code null}
	 */
	public UnimplementedAttribute(final String name, final int attributeNameIndex, final byte[] info) {
		super(name, attributeNameIndex);
		
		this.info = Objects.requireNonNull(info, "info == null").clone();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code UnimplementedAttribute} instance.
	 * 
	 * @return a copy of this {@code UnimplementedAttribute} instance
	 */
	@Override
	public UnimplementedAttribute copy() {
		return new UnimplementedAttribute(getName(), getAttributeNameIndex(), getInfo());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code UnimplementedAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code UnimplementedAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new UnimplementedAttribute(\"%s\", %s, new byte[] {})", getName(), Integer.toString(getAttributeNameIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code UnimplementedAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code UnimplementedAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code UnimplementedAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code UnimplementedAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof UnimplementedAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), UnimplementedAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != UnimplementedAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != UnimplementedAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(!Arrays.equals(this.info, UnimplementedAttribute.class.cast(object).info)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the values of the {@code info} item associated with this {@code UnimplementedAttribute} instance.
	 * 
	 * @return the values of the {@code info} item associated with this {@code UnimplementedAttribute} instance
	 */
	public byte[] getInfo() {
		return this.info.clone();
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code UnimplementedAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code UnimplementedAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return this.info.length;
	}
	
	/**
	 * Returns a hash code for this {@code UnimplementedAttribute} instance.
	 * 
	 * @return a hash code for this {@code UnimplementedAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(Arrays.hashCode(this.info)));
	}
	
	/**
	 * Writes this {@code UnimplementedAttribute} to {@code dataOutput}.
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
			dataOutput.write(this.info);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code UnimplementedAttribute} instances in {@code node}.
	 * <p>
	 * All {@code UnimplementedAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code UnimplementedAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<UnimplementedAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), UnimplementedAttribute.class);
	}
}