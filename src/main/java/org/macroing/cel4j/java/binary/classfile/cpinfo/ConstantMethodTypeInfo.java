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
package org.macroing.cel4j.java.binary.classfile.cpinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;//TODO: Update Javadocs!
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code ConstantMethodTypeInfo} represents a {@code CONSTANT_MethodType_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code CONSTANT_MethodType_info} structure was added to Java in version 7 and can be found in the {@code constant_pool} table item of a {@code ClassFile} structure.
 * <p>
 * The {@code CONSTANT_MethodType_info} structure has the following format:
 * <pre>
 * <code>
 * CONSTANT_MethodType_info {
 *     u1 tag;
 *     u2 descriptor_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantMethodTypeInfo extends CPInfo {
	/**
	 * The name of the {@code CONSTANT_MethodType_info} structure.
	 */
	public static final String NAME = "CONSTANT_MethodType";
	
	/**
	 * The tag for the {@code CONSTANT_MethodType_info} structure.
	 */
	public static final int TAG = 16;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int descriptorIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantMethodTypeInfo}.
	 * <p>
	 * If {@code descriptorIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param descriptorIndex the descriptor_index of the new {@code ConstantMethodTypeInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code descriptorIndex} is less than or equal to {@code 0}
	 */
	public ConstantMethodTypeInfo(final int descriptorIndex) {
		super(NAME, TAG, 1);
		
		this.descriptorIndex = ParameterArguments.requireRange(descriptorIndex, 1, Integer.MAX_VALUE, "descriptorIndex");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantMethodTypeInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantMethodTypeInfo} instance
	 */
	@Override
	public ConstantMethodTypeInfo copy() {
		return new ConstantMethodTypeInfo(this.descriptorIndex);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantMethodTypeInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantMethodTypeInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("CONSTANT_MethodType_info: descriptor_index=%s", Integer.toString(this.descriptorIndex));
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantMethodTypeInfo}, and that {@code ConstantMethodTypeInfo} instance is equal to this {@code ConstantMethodTypeInfo} instance,
	 * {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code ConstantMethodTypeInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantMethodTypeInfo}, and that {@code ConstantMethodTypeInfo} instance is equal to this {@code ConstantMethodTypeInfo} instance,
	 * {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantMethodTypeInfo)) {
			return false;
		} else if(!Objects.equals(ConstantMethodTypeInfo.class.cast(object).getName(), getName())) {
			return false;
		} else if(ConstantMethodTypeInfo.class.cast(object).getTag() != getTag()) {
			return false;
		} else if(ConstantMethodTypeInfo.class.cast(object).getConstantPoolEntryCount() != getConstantPoolEntryCount()) {
			return false;
		} else if(ConstantMethodTypeInfo.class.cast(object).descriptorIndex != this.descriptorIndex) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the descriptor_index of this {@code ConstantMethodTypeInfo} instance.
	 * 
	 * @return the descriptor_index of this {@code ConstantMethodTypeInfo} instance
	 */
	public int getDescriptorIndex() {
		return this.descriptorIndex;
	}
	
	/**
	 * Returns a hash code for this {@code ConstantMethodTypeInfo} instance.
	 * 
	 * @return a hash code for this {@code ConstantMethodTypeInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Integer.valueOf(this.descriptorIndex));
	}
	
	/**
	 * Sets a new descriptor_index for this {@code ConstantMethodTypeInfo} instance.
	 * <p>
	 * If {@code descriptorIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param descriptorIndex the new descriptor_index for this {@code ConstantMethodTypeInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code descriptorIndex} is less than or equal to {@code 0}
	 */
	public void setDescriptorIndex(final int descriptorIndex) {
		this.descriptorIndex = ParameterArguments.requireRange(descriptorIndex, 1, Integer.MAX_VALUE, "descriptorIndex");
	}
	
	/**
	 * Writes this {@code ConstantMethodTypeInfo} to {@code dataOutput}.
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
			dataOutput.writeByte(getTag());
			dataOutput.writeShort(getDescriptorIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Writes this {@code ConstantMethodTypeInfo} to {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param document the {@link Document} to write to
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	@Override
	public void write(final Document document) {
		document.linef("%s_info = {", getName());
		document.indent();
		document.linef("u1 tag = %s;", Integer.toString(getTag()));
		document.linef("u2 descriptor_index = %s;", Integer.toString(getDescriptorIndex()));
		document.outdent();
		document.linef("};");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantMethodTypeInfo} instances in {@code node}.
	 * <p>
	 * All {@code ConstantMethodTypeInfo} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantMethodTypeInfo} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantMethodTypeInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantMethodTypeInfo.class);
	}
}