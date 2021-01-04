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
package org.macroing.cel4j.java.binary.classfile.cpinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code ConstantDynamicInfo} represents a {@code CONSTANT_Dynamic_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code CONSTANT_Dynamic_info} structure was added to Java in version 11 and can be found in the {@code constant_pool} table item of a {@code ClassFile} structure.
 * <p>
 * The {@code CONSTANT_Dynamic_info} structure has the following format:
 * <pre>
 * <code>
 * CONSTANT_Dynamic_info {
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
public final class ConstantDynamicInfo extends CPInfo {
	/**
	 * The name of the {@code CONSTANT_Dynamic_info} structure.
	 */
	public static final String NAME = "CONSTANT_Dynamic";
	
	/**
	 * The tag for the {@code CONSTANT_Dynamic_info} structure.
	 */
	public static final int TAG = 17;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int bootstrapMethodAttrIndex;
	private int nameAndTypeIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantDynamicInfo} instance that is a copy of {@code constantDynamicInfo}.
	 * <p>
	 * If {@code constantDynamicInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param constantDynamicInfo the {@code ConstantDynamicInfo} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code constantDynamicInfo} is {@code null}
	 */
	public ConstantDynamicInfo(final ConstantDynamicInfo constantDynamicInfo) {
		super(NAME, TAG, 1);
		
		this.bootstrapMethodAttrIndex = constantDynamicInfo.bootstrapMethodAttrIndex;
		this.nameAndTypeIndex = constantDynamicInfo.nameAndTypeIndex;
	}
	
	/**
	 * Constructs a new {@code ConstantDynamicInfo} instance.
	 * <p>
	 * If {@code bootstrapMethodAttrIndex} is less than {@code 0}, or {@code nameAndTypeIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param bootstrapMethodAttrIndex the value for the {@code bootstrap_method_attr_index} item associated with this {@code ConstantDynamicInfo} instance
	 * @param nameAndTypeIndex the value for the {@code name_and_type_index} item associated with this {@code ConstantDynamicInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code bootstrapMethodAttrIndex} is less than {@code 0}, or {@code nameAndTypeIndex} is less than {@code 1}
	 */
	public ConstantDynamicInfo(final int bootstrapMethodAttrIndex, final int nameAndTypeIndex) {
		super(NAME, TAG, 1);
		
		this.bootstrapMethodAttrIndex = ParameterArguments.requireRange(bootstrapMethodAttrIndex, 0, Integer.MAX_VALUE, "bootstrapMethodAttrIndex");
		this.nameAndTypeIndex = ParameterArguments.requireRange(nameAndTypeIndex, 1, Integer.MAX_VALUE, "nameAndTypeIndex");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantDynamicInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantDynamicInfo} instance
	 */
	@Override
	public ConstantDynamicInfo copy() {
		return new ConstantDynamicInfo(this);
	}
	
	/**
	 * Writes this {@code ConstantDynamicInfo} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * constantDynamicInfo.write(new Document());
	 * }
	 * </pre>
	 * 
	 * @return the {@code Document}
	 */
	@Override
	public Document write() {
		return write(new Document());
	}
	
	/**
	 * Writes this {@code ConstantDynamicInfo} to {@code document}.
	 * <p>
	 * Returns {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param document the {@link Document} to write to
	 * @return {@code document}
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	@Override
	public Document write(final Document document) {
		document.linef("%s_info = {", getName());
		document.indent();
		document.linef("u1 tag = %s;", Integer.toString(getTag()));
		document.linef("u2 bootstrap_method_attr_index = %s;", Integer.toString(getBootstrapMethodAttrIndex()));
		document.linef("u2 name_and_type_index = %s;", Integer.toString(getNameAndTypeIndex()));
		document.outdent();
		document.linef("};");
		
		return document;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantDynamicInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantDynamicInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("new ConstantDynamicInfo(%s, %s)", Integer.toString(getBootstrapMethodAttrIndex()), Integer.toString(getNameAndTypeIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code ConstantDynamicInfo} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantDynamicInfo}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConstantDynamicInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantDynamicInfo}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantDynamicInfo)) {
			return false;
		} else if(!Objects.equals(getName(), ConstantDynamicInfo.class.cast(object).getName())) {
			return false;
		} else if(getTag() != ConstantDynamicInfo.class.cast(object).getTag()) {
			return false;
		} else if(getConstantPoolEntryCount() != ConstantDynamicInfo.class.cast(object).getConstantPoolEntryCount()) {
			return false;
		} else if(getBootstrapMethodAttrIndex() != ConstantDynamicInfo.class.cast(object).getBootstrapMethodAttrIndex()) {
			return false;
		} else if(getNameAndTypeIndex() != ConstantDynamicInfo.class.cast(object).getNameAndTypeIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code bootstrap_method_attr_index} item associated with this {@code ConstantDynamicInfo} instance.
	 * 
	 * @return the value of the {@code bootstrap_method_attr_index} item associated with this {@code ConstantDynamicInfo} instance
	 */
	public int getBootstrapMethodAttrIndex() {
		return this.bootstrapMethodAttrIndex;
	}
	
	/**
	 * Returns the value of the {@code name_and_type_index} item associated with this {@code ConstantDynamicInfo} instance.
	 * 
	 * @return the value of the {@code name_and_type_index} item associated with this {@code ConstantDynamicInfo} instance
	 */
	public int getNameAndTypeIndex() {
		return this.nameAndTypeIndex;
	}
	
	/**
	 * Returns a hash code for this {@code ConstantDynamicInfo} instance.
	 * 
	 * @return a hash code for this {@code ConstantDynamicInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Integer.valueOf(getBootstrapMethodAttrIndex()), Integer.valueOf(getNameAndTypeIndex()));
	}
	
	/**
	 * Sets {@code bootstrapMethodAttrIndex} as the value for the {@code bootstrap_method_attr_index} item associated with this {@code ConstantDynamicInfo} instance.
	 * <p>
	 * If {@code bootstrapMethodAttrIndex} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param bootstrapMethodAttrIndex the value for the {@code bootstrap_method_attr_index} item associated with this {@code ConstantDynamicInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code bootstrapMethodAttrIndex} is less than {@code 0}
	 */
	public void setBootstrapMethodAttrIndex(final int bootstrapMethodAttrIndex) {
		this.bootstrapMethodAttrIndex = ParameterArguments.requireRange(bootstrapMethodAttrIndex, 0, Integer.MAX_VALUE, "bootstrapMethodAttrIndex");
	}
	
	/**
	 * Sets {@code nameAndTypeIndex} as the value for the {@code name_and_type_index} item associated with this {@code ConstantDynamicInfo} instance.
	 * <p>
	 * If {@code nameAndTypeIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameAndTypeIndex the value for the {@code name_and_type_index} item associated with this {@code ConstantDynamicInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameAndTypeIndex} is less than {@code 1}
	 */
	public void setNameAndTypeIndex(final int nameAndTypeIndex) {
		this.nameAndTypeIndex = ParameterArguments.requireRange(nameAndTypeIndex, 1, Integer.MAX_VALUE, "nameAndTypeIndex");
	}
	
	/**
	 * Writes this {@code ConstantDynamicInfo} to {@code dataOutput}.
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
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantDynamicInfo} instances in {@code node}.
	 * <p>
	 * All {@code ConstantDynamicInfo} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantDynamicInfo} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantDynamicInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantDynamicInfo.class);
	}
}