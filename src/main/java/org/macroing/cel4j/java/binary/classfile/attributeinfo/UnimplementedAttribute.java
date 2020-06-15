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
 * An {@link AttributeInfo} implementation that is used for all attribute_info structures currently not supported by this library.
 * <p>
 * All {@code AttributeInfo}s read by any {@link org.macroing.cel4j.java.binary.reader.AttributeInfoReader AttributeInfoReader}s, are considered to be supported by this library. This includes all third party implementations of {@code AttributeInfo},
 * for which there exists a corresponding {@code AttributeInfoReader}.
 * <p>
 * This class is not thread-safe.
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
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param name the name for the new {@code UnimplementedAttribute} instance
	 * @param attributeNameIndex the attribute_name_index of the new {@code UnimplementedAttribute} instance
	 * @param info the info of the new {@code UnimplementedAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
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
		return new UnimplementedAttribute(getName(), getAttributeNameIndex(), this.info.clone());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code UnimplementedAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code UnimplementedAttribute} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Unimplemented_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		stringBuilder.append(" ");
		stringBuilder.append("info=" + Arrays.toString(this.info));
		
		final String toString = stringBuilder.toString();
		
		return toString;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code UnimplementedAttribute}, and that {@code UnimplementedAttribute} instance is equal to this {@code UnimplementedAttribute} instance,
	 * {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code UnimplementedAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code UnimplementedAttribute}, and that {@code UnimplementedAttribute} instance is equal to this {@code UnimplementedAttribute} instance,
	 * {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof UnimplementedAttribute)) {
			return false;
		} else if(!Objects.equals(UnimplementedAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(UnimplementedAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(UnimplementedAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(!Arrays.equals(UnimplementedAttribute.class.cast(object).info, this.info)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the {@code byte}s held by this {@code UnimplementedAttribute} instance.
	 * 
	 * @return the {@code byte}s held by this {@code UnimplementedAttribute} instance
	 */
	public byte[] getInfo() {
		return this.info.clone();
	}
	
	/**
	 * Returns the attribute_length of this {@code UnimplementedAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code UnimplementedAttribute} instance
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
			dataOutput.write(this.info);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code UnimplementedAttribute}s.
	 * <p>
	 * All {@code UnimplementedAttribute}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code UnimplementedAttribute}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<UnimplementedAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), UnimplementedAttribute.class);
	}
}