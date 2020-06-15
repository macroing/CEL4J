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
 * A {@code SourceFileAttribute} denotes a SourceFile_attribute structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class SourceFileAttribute extends AttributeInfo {
	/**
	 * The name of the SourceFile_attribute structure.
	 */
	public static final String NAME = "SourceFile";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int sourceFileIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code SourceFileAttribute} instance.
	 * <p>
	 * If either {@code attributeNameIndex} or {@code sourceFileIndex} are less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the attribute_name_index of the new {@code SourceFileAttribute} instance
	 * @param sourceFileIndex the sourcefile_index of the new {@code SourceFileAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code attributeNameIndex} or {@code sourceFileIndex} are less than or equal to {@code 0}
	 */
	public SourceFileAttribute(final int attributeNameIndex, final int sourceFileIndex) {
		super(NAME, attributeNameIndex);
		
		this.sourceFileIndex = ParameterArguments.requireRange(sourceFileIndex, 1, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code SourceFileAttribute} instance.
	 * 
	 * @return a copy of this {@code SourceFileAttribute} instance
	 */
	@Override
	public SourceFileAttribute copy() {
		return new SourceFileAttribute(getAttributeNameIndex(), this.sourceFileIndex);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code SourceFileAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code SourceFileAttribute} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SourceFile_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		stringBuilder.append(" ");
		stringBuilder.append("sourcefile_index=" + this.sourceFileIndex);
		
		final String toString = stringBuilder.toString();
		
		return toString;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code SourceFileAttribute}, and that {@code SourceFileAttribute} instance is equal to this {@code SourceFileAttribute} instance, {@code false}
	 * otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code SourceFileAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code SourceFileAttribute}, and that {@code SourceFileAttribute} instance is equal to this {@code SourceFileAttribute} instance, {@code false}
	 * otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof SourceFileAttribute)) {
			return false;
		} else if(!Objects.equals(SourceFileAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(SourceFileAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(SourceFileAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(SourceFileAttribute.class.cast(object).getSourceFileIndex() != getSourceFileIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the attribute_length of this {@code SourceFileAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code SourceFileAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 2;
	}
	
	/**
	 * Returns the sourcefile_index of this {@code SourceFileAttribute} instance.
	 * 
	 * @return the sourcefile_index of this {@code SourceFileAttribute} instance
	 */
	public int getSourceFileIndex() {
		return this.sourceFileIndex;
	}
	
	/**
	 * Returns a hash code for this {@code SourceFileAttribute} instance.
	 * 
	 * @return a hash code for this {@code SourceFileAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getSourceFileIndex()));
	}
	
	/**
	 * Sets a new sourcefile_index for this {@code SourceFileAttribute} instance.
	 * <p>
	 * If {@code sourceFileIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param sourceFileIndex the new sourcefile_index for this {@code SourceFileAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code sourceFileIndex} is less than or equal to {@code 0}
	 */
	public void setSourceFileIndex(final int sourceFileIndex) {
		this.sourceFileIndex = ParameterArguments.requireRange(sourceFileIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code SourceFileAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(this.sourceFileIndex);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code SourceFileAttribute}s.
	 * <p>
	 * All {@code SourceFileAttribute}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code SourceFileAttribute}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<SourceFileAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), SourceFileAttribute.class);
	}
}