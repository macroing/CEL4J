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

/**
 * A {@code SourceDebugExtensionAttribute} represents a {@code SourceDebugExtension_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code SourceDebugExtension_attribute} structure has the following format:
 * <pre>
 * <code>
 * SourceDebugExtension_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u1[attribute_length] debug_extension;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class SourceDebugExtensionAttribute extends AttributeInfo {
	/**
	 * The name of the {@code SourceDebugExtension_attribute} structure.
	 */
	public static final String NAME = "SourceDebugExtension";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String debugExtension;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code SourceDebugExtensionAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code debugExtension} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code SourceDebugExtensionAttribute} instance
	 * @param debugExtension the value for the {@code debug_extension} item associated with this {@code SourceDebugExtensionAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 * @throws NullPointerException thrown if, and only if, {@code debugExtension} is {@code null}
	 */
	public SourceDebugExtensionAttribute(final int attributeNameIndex, final String debugExtension) {
		super(NAME, attributeNameIndex);
		
		this.debugExtension = Objects.requireNonNull(debugExtension, "debugExtension == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code SourceDebugExtensionAttribute} instance.
	 * 
	 * @return a copy of this {@code SourceDebugExtensionAttribute} instance
	 */
	@Override
	public SourceDebugExtensionAttribute copy() {
		return new SourceDebugExtensionAttribute(getAttributeNameIndex(), getDebugExtension());
	}
	
	/**
	 * Returns the value of the {@code debug_extension} item associated with this {@code SourceDebugExtensionAttribute} instance.
	 * 
	 * @return the value of the {@code debug_extension} item associated with this {@code SourceDebugExtensionAttribute} instance
	 */
	public String getDebugExtension() {
		return this.debugExtension;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code SourceDebugExtensionAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code SourceDebugExtensionAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new SourceDebugExtensionAttribute(%s, \"%s\")", Integer.toString(getAttributeNameIndex()), getDebugExtension());
	}
	
	/**
	 * Compares {@code object} to this {@code SourceDebugExtensionAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code SourceDebugExtensionAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code SourceDebugExtensionAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code SourceDebugExtensionAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof SourceDebugExtensionAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), SourceDebugExtensionAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != SourceDebugExtensionAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != SourceDebugExtensionAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(!Objects.equals(getDebugExtension(), SourceDebugExtensionAttribute.class.cast(object).getDebugExtension())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code SourceDebugExtensionAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code SourceDebugExtensionAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return getDebugExtension().length();
	}
	
	/**
	 * Returns a hash code for this {@code SourceDebugExtensionAttribute} instance.
	 * 
	 * @return a hash code for this {@code SourceDebugExtensionAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), getDebugExtension());
	}
	
	/**
	 * Sets a new debug_extension for this {@code SourceDebugExtensionAttribute} instance.
	 * <p>
	 * If {@code debugExtension} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param debugExtension the new debug_extension for this {@code SourceDebugExtensionAttribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code debugExtension} is {@code null}
	 */
	/**
	 * Sets {@code debugExtension} as the value for the {@code debug_extension} item associated with this {@code SourceDebugExtensionAttribute} instance.
	 * <p>
	 * If {@code debugExtension} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param debugExtension the value for the {@code debug_extension} item associated with this {@code SourceDebugExtensionAttribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code debugExtension} is {@code null}
	 */
	public void setDebugExtension(final String debugExtension) {
		this.debugExtension = Objects.requireNonNull(debugExtension, "debugExtension == null");
	}
	
	/**
	 * Writes this {@code SourceDebugExtensionAttribute} to {@code dataOutput}.
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
			dataOutput.writeUTF(getDebugExtension());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code SourceDebugExtensionAttribute} instances in {@code node}.
	 * <p>
	 * All {@code SourceDebugExtensionAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code SourceDebugExtensionAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<SourceDebugExtensionAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), SourceDebugExtensionAttribute.class);
	}
}