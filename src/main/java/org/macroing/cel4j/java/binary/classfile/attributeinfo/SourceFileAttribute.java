/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
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
 * A {@code SourceFileAttribute} represents a {@code SourceFile_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code SourceFile_attribute} structure has the following format:
 * <pre>
 * <code>
 * SourceFile_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 sourcefile_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class SourceFileAttribute extends AttributeInfo {
	/**
	 * The name of the {@code SourceFile_attribute} structure.
	 */
	public static final String NAME = "SourceFile";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int sourceFileIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code SourceFileAttribute} instance that is a copy of {@code sourceFileAttribute}.
	 * <p>
	 * If {@code sourceFileAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param sourceFileAttribute the {@code SourceFileAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code sourceFileAttribute} is {@code null}
	 */
	public SourceFileAttribute(final SourceFileAttribute sourceFileAttribute) {
		super(NAME, sourceFileAttribute.getAttributeNameIndex());
		
		this.sourceFileIndex = sourceFileAttribute.sourceFileIndex;
	}
	
	/**
	 * Constructs a new {@code SourceFileAttribute} instance.
	 * <p>
	 * If either {@code attributeNameIndex} or {@code sourceFileIndex} are less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code SourceFileAttribute} instance
	 * @param sourceFileIndex the value for the {@code sourcefile_index} item associated with this {@code SourceFileAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code attributeNameIndex} or {@code sourceFileIndex} are less than {@code 1}
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
		return new SourceFileAttribute(this);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code SourceFileAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code SourceFileAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new SourceFileAttribute(%s, %s)", Integer.toString(getAttributeNameIndex()), Integer.toString(getSourceFileIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code SourceFileAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code SourceFileAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code SourceFileAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code SourceFileAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof SourceFileAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), SourceFileAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != SourceFileAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != SourceFileAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getSourceFileIndex() != SourceFileAttribute.class.cast(object).getSourceFileIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code SourceFileAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code SourceFileAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 2;
	}
	
	/**
	 * Returns the value of the {@code sourcefile_index} item associated with this {@code SourceFileAttribute} instance.
	 * 
	 * @return the value of the {@code sourcefile_index} item associated with this {@code SourceFileAttribute} instance
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
	 * Sets {@code sourceFileIndex} as the value for the {@code sourcefile_index} item associated with this {@code SourceFileAttribute} instance.
	 * <p>
	 * If {@code sourceFileIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param sourceFileIndex the value for the {@code sourcefile_index} item associated with this {@code SourceFileAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code sourceFileIndex} is less than {@code 1}
	 */
	public void setSourceFileIndex(final int sourceFileIndex) {
		this.sourceFileIndex = ParameterArguments.requireRange(sourceFileIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code SourceFileAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(getSourceFileIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code SourceFileAttribute} instances in {@code node}.
	 * <p>
	 * All {@code SourceFileAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code SourceFileAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<SourceFileAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), SourceFileAttribute.class);
	}
}