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
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code ConstantPackageInfo} represents a {@code CONSTANT_Package_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code CONSTANT_Package_info} structure was added to Java in version 9 and can be found in the {@code constant_pool} table item of a {@code ClassFile} structure.
 * <p>
 * The {@code CONSTANT_Package_info} structure has the following format:
 * <pre>
 * <code>
 * CONSTANT_Package_info {
 *     u1 tag;
 *     u2 name_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantPackageInfo extends CPInfo {
	/**
	 * The name of the {@code CONSTANT_Package_info} structure.
	 */
	public static final String NAME = "CONSTANT_Package";
	
	/**
	 * The tag for the {@code CONSTANT_Package_info} structure.
	 */
	public static final int TAG = 20;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int nameIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantPackageInfo} instance that is a copy of {@code constantPackageInfo}.
	 * <p>
	 * If {@code constantPackageInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param constantPackageInfo the {@code ConstantPackageInfo} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code constantPackageInfo} is {@code null}
	 */
	public ConstantPackageInfo(final ConstantPackageInfo constantPackageInfo) {
		super(NAME, TAG, 1);
		
		this.nameIndex = constantPackageInfo.nameIndex;
	}
	
	/**
	 * Constructs a new {@code ConstantPackageInfo} instance.
	 * <p>
	 * If {@code nameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameIndex the value for the {@code name_index} item associated with this {@code ConstantPackageInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameIndex} is less than {@code 1}
	 */
	public ConstantPackageInfo(final int nameIndex) {
		super(NAME, TAG, 1);
		
		this.nameIndex = ParameterArguments.requireRange(nameIndex, 1, Integer.MAX_VALUE, "nameIndex");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantPackageInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantPackageInfo} instance
	 */
	@Override
	public ConstantPackageInfo copy() {
		return new ConstantPackageInfo(this);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantPackageInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantPackageInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("new ConstantPackageInfo(%s)", Integer.toString(getNameIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code ConstantPackageInfo} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantPackageInfo}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConstantPackageInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantPackageInfo}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantPackageInfo)) {
			return false;
		} else if(!Objects.equals(getName(), ConstantPackageInfo.class.cast(object).getName())) {
			return false;
		} else if(getTag() != ConstantPackageInfo.class.cast(object).getTag()) {
			return false;
		} else if(getConstantPoolEntryCount() != ConstantPackageInfo.class.cast(object).getConstantPoolEntryCount()) {
			return false;
		} else if(getNameIndex() != ConstantPackageInfo.class.cast(object).getNameIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code name_index} item associated with this {@code ConstantPackageInfo} instance.
	 * 
	 * @return the value of the {@code name_index} item associated with this {@code ConstantPackageInfo} instance
	 */
	public int getNameIndex() {
		return this.nameIndex;
	}
	
	/**
	 * Returns a hash code for this {@code ConstantPackageInfo} instance.
	 * 
	 * @return a hash code for this {@code ConstantPackageInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Integer.valueOf(getNameIndex()));
	}
	
	/**
	 * Sets {@code nameIndex} as the value for the {@code name_index} item associated with this {@code ConstantPackageInfo} instance.
	 * <p>
	 * If {@code nameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameIndex the value for the {@code name_index} item associated with this {@code ConstantPackageInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameIndex} is less than {@code 1}
	 */
	public void setNameIndex(final int nameIndex) {
		this.nameIndex = ParameterArguments.requireRange(nameIndex, 1, Integer.MAX_VALUE, "nameIndex");
	}
	
	/**
	 * Writes this {@code ConstantPackageInfo} to {@code dataOutput}.
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
			dataOutput.writeShort(getNameIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Writes this {@code ConstantPackageInfo} to {@code document}.
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
		document.linef("u2 name_index = %s;", Integer.toString(getNameIndex()));
		document.outdent();
		document.linef("};");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantPackageInfo} instances in {@code node}.
	 * <p>
	 * All {@code ConstantPackageInfo} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantPackageInfo} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantPackageInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantPackageInfo.class);
	}
}