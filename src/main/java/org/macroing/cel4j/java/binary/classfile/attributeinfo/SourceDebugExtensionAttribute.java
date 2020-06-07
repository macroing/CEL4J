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
 * A {@code SourceDebugExtensionAttribute} denotes a SourceDebugExtension_attribute structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class SourceDebugExtensionAttribute extends AttributeInfo {
	/**
	 * The name of the SourceDebugExtension_attribute structure.
	 */
	public static final String NAME = "SourceDebugExtension";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String debugExtension;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private SourceDebugExtensionAttribute(final int attributeNameIndex, final String debugExtension) {
		super(NAME, attributeNameIndex);
		
		this.debugExtension = debugExtension;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code SourceDebugExtensionAttribute} instance.
	 * 
	 * @return a copy of this {@code SourceDebugExtensionAttribute} instance
	 */
	@Override
	public SourceDebugExtensionAttribute copy() {
		return new SourceDebugExtensionAttribute(getAttributeNameIndex(), this.debugExtension);
	}
	
	/**
	 * Returns the debug_extension of this {@code SourceDebugExtensionAttribute} instance.
	 * 
	 * @return the debug_extension of this {@code SourceDebugExtensionAttribute} instance
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
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SourceDebugExtension_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		stringBuilder.append(" ");
		stringBuilder.append("debug_extension=" + this.debugExtension);
		
		final String toString = stringBuilder.toString();
		
		return toString;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code SourceDebugExtensionAttribute}, and that {@code SourceDebugExtensionAttribute} instance is equal to this
	 * {@code SourceDebugExtensionAttribute} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code SourceDebugExtensionAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code SourceDebugExtensionAttribute}, and that {@code SourceDebugExtensionAttribute} instance is equal to this
	 * {@code SourceDebugExtensionAttribute} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof SourceDebugExtensionAttribute)) {
			return false;
		} else if(!Objects.equals(SourceDebugExtensionAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(SourceDebugExtensionAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(SourceDebugExtensionAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(!Objects.equals(SourceDebugExtensionAttribute.class.cast(object).getDebugExtension(), getDebugExtension())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the attribute_length of this {@code SourceDebugExtensionAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code SourceDebugExtensionAttribute} instance.
	 */
	@Override
	public int getAttributeLength() {
		return this.debugExtension.length();
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
	public void setDebugExtension(final String debugExtension) {
		this.debugExtension = Objects.requireNonNull(debugExtension, "debugExtension == null");
	}
	
	/**
	 * Writes this {@code SourceDebugExtensionAttribute} to {@code dataOutput}.
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
			dataOutput.writeUTF(this.debugExtension);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code SourceDebugExtensionAttribute}s.
	 * <p>
	 * All {@code SourceDebugExtensionAttribute}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code SourceDebugExtensionAttribute}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<SourceDebugExtensionAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), SourceDebugExtensionAttribute.class);
	}
	
	/**
	 * Returns a new {@code SourceDebugExtensionAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code debugExtension} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attributeNameIndex the attribute_name_index of the new {@code SourceDebugExtensionAttribute} instance
	 * @param debugExtension the debug_extension of the new {@code SourceDebugExtensionAttribute} instance
	 * @return a new {@code SourceDebugExtensionAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
	 * @throws NullPointerException thrown if, and only if, {@code debugExtension} is {@code null}
	 */
	public static SourceDebugExtensionAttribute newInstance(final int attributeNameIndex, final String debugExtension) {
		return new SourceDebugExtensionAttribute(ParameterArguments.requireRange(attributeNameIndex, 1, Integer.MAX_VALUE), Objects.requireNonNull(debugExtension, "debugExtension == null"));
	}
}