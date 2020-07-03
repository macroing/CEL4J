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
 * A {@code ConstantInvokeDynamicInfo} represents a {@code CONSTANT_InvokeDynamic_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code CONSTANT_InvokeDynamic_info} structure was added to Java in version 7 and can be found in the {@code constant_pool} table item of a {@code ClassFile} structure.
 * <p>
 * The {@code CONSTANT_InvokeDynamic_info} structure has the following format:
 * <pre>
 * <code>
 * CONSTANT_InvokeDynamic_info {
 *     u1 tag;
 *     u2 bootstrap_method_attr_index;
 *     u2 name_and_type_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantInvokeDynamicInfo extends CPInfo {
	/**
	 * The name of the {@code CONSTANT_InvokeDynamic_info} structure.
	 */
	public static final String NAME = "CONSTANT_InvokeDynamic";
	
	/**
	 * The tag for the {@code CONSTANT_InvokeDynamic_info} structure.
	 */
	public static final int TAG = 18;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int bootstrapMethodAttrIndex;
	private int nameAndTypeIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantInvokeDynamicInfo}.
	 * <p>
	 * If {@code bootstrapMethodAttrIndex} is less than {@code 0}, or {@code nameAndTypeIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param bootstrapMethodAttrIndex the bootstrap_method_attr_index of the  new {@code ConstantInvokeDynamicInfo} instance
	 * @param nameAndTypeIndex the name_and_type_index of the new {@code ConstantInvokeDynamicInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code bootstrapMethodAttrIndex} is less than {@code 0}, or {@code nameAndTypeIndex} is less than or equal to {@code 0}
	 */
	public ConstantInvokeDynamicInfo(final int bootstrapMethodAttrIndex, final int nameAndTypeIndex) {
		super(NAME, TAG, 1);
		
		this.bootstrapMethodAttrIndex = ParameterArguments.requireRange(bootstrapMethodAttrIndex, 0, Integer.MAX_VALUE, "bootstrapMethodAttrIndex");
		this.nameAndTypeIndex = ParameterArguments.requireRange(nameAndTypeIndex, 1, Integer.MAX_VALUE, "nameAndTypeIndex");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantInvokeDynamicInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantInvokeDynamicInfo} instance
	 */
	@Override
	public ConstantInvokeDynamicInfo copy() {
		return new ConstantInvokeDynamicInfo(this.bootstrapMethodAttrIndex, this.nameAndTypeIndex);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantInvokeDynamicInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantInvokeDynamicInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("CONSTANT_InvokeDynamic_info: bootstrap_method_attr_index=%s, name_and_type_index=%s", Integer.toString(this.bootstrapMethodAttrIndex), Integer.toString(this.nameAndTypeIndex));
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantInvokeDynamicInfo}, and that {@code ConstantInvokeDynamicInfo} instance is equal to this {@code ConstantInvokeDynamicInfo}
	 * instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code ConstantInvokeDynamicInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantInvokeDynamicInfo}, and that {@code ConstantInvokeDynamicInfo} instance is equal to this {@code ConstantInvokeDynamicInfo}
	 * instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantInvokeDynamicInfo)) {
			return false;
		} else if(!Objects.equals(ConstantInvokeDynamicInfo.class.cast(object).getName(), getName())) {
			return false;
		} else if(ConstantInvokeDynamicInfo.class.cast(object).getTag() != getTag()) {
			return false;
		} else if(ConstantInvokeDynamicInfo.class.cast(object).getConstantPoolEntryCount() != getConstantPoolEntryCount()) {
			return false;
		} else if(ConstantInvokeDynamicInfo.class.cast(object).bootstrapMethodAttrIndex != this.bootstrapMethodAttrIndex) {
			return false;
		} else if(ConstantInvokeDynamicInfo.class.cast(object).nameAndTypeIndex != this.nameAndTypeIndex) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the bootstrap_method_attr_index of this {@code ConstantInvokeDynamicInfo} instance.
	 * 
	 * @return the bootstrap_method_attr_index of this {@code ConstantInvokeDynamicInfo} instance
	 */
	public int getBootstrapMethodAttrIndex() {
		return this.bootstrapMethodAttrIndex;
	}
	
	/**
	 * Returns the name_and_type_index of this {@code ConstantInvokeDynamicInfo} instance.
	 * 
	 * @return the name_and_type_index of this {@code ConstantInvokeDynamicInfo} instance
	 */
	public int getNameAndTypeIndex() {
		return this.nameAndTypeIndex;
	}
	
	/**
	 * Returns a hash code for this {@code ConstantInvokeDynamicInfo} instance.
	 * 
	 * @return a hash code for this {@code ConstantInvokeDynamicInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Integer.valueOf(this.bootstrapMethodAttrIndex), Integer.valueOf(this.nameAndTypeIndex));
	}
	
	/**
	 * Sets a new bootstrap_method_attr_index for this {@code ConstantInvokeDynamicInfo} instance.
	 * <p>
	 * If {@code bootstrapMethodAttrIndex} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param bootstrapMethodAttrIndex the new bootstrap_method_attr_index for this {@code ConstantInvokeDynamicInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code bootstrapMethodAttrIndex} is less than {@code 0}
	 */
	public void setBootstrapMethodAttrIndex(final int bootstrapMethodAttrIndex) {
		this.bootstrapMethodAttrIndex = ParameterArguments.requireRange(bootstrapMethodAttrIndex, 0, Integer.MAX_VALUE, "bootstrapMethodAttrIndex");
	}
	
	/**
	 * Sets a new name_and_type_index for this {@code ConstantInvokeDynamicInfo} instance.
	 * <p>
	 * If {@code nameAndTypeIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameAndTypeIndex the new name_and_type_index for this {@code ConstantInvokeDynamicInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameAndTypeIndex} is less than or equal to {@code 0}
	 */
	public void setNameAndTypeIndex(final int nameAndTypeIndex) {
		this.nameAndTypeIndex = ParameterArguments.requireRange(nameAndTypeIndex, 1, Integer.MAX_VALUE, "nameAndTypeIndex");
	}
	
	/**
	 * Writes this {@code ConstantInvokeDynamicInfo} to {@code dataOutput}.
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
			dataOutput.writeShort(getBootstrapMethodAttrIndex());
			dataOutput.writeShort(getNameAndTypeIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Writes this {@code ConstantInvokeDynamicInfo} to {@code document}.
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
		document.linef("u2 bootstrap_method_attr_index = %s;", Integer.toString(getBootstrapMethodAttrIndex()));
		document.linef("u2 name_and_type_index = %s;", Integer.toString(getNameAndTypeIndex()));
		document.outdent();
		document.linef("};");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantInvokeDynamicInfo} instances in {@code node}.
	 * <p>
	 * All {@code ConstantInvokeDynamicInfo} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantInvokeDynamicInfo} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantInvokeDynamicInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantInvokeDynamicInfo.class);
	}
}