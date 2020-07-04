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
 * A {@code ConstantFieldRefInfo} represents a {@code CONSTANT_Fieldref_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code CONSTANT_Fieldref_info} structure was added to Java in version 1.0.2 and can be found in the {@code constant_pool} table item of a {@code ClassFile} structure.
 * <p>
 * The {@code CONSTANT_Fieldref_info} structure has the following format:
 * <pre>
 * <code>
 * CONSTANT_Fieldref_info {
 *     u1 tag;
 *     u2 class_index;
 *     u2 name_and_type_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantFieldRefInfo extends CPInfo {
	/**
	 * The name of the {@code CONSTANT_Fieldref_info} structure.
	 */
	public static final String NAME = "CONSTANT_Fieldref";
	
	/**
	 * The tag for the {@code CONSTANT_Fieldref_info} structure.
	 */
	public static final int TAG = 9;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int classIndex;
	private int nameAndTypeIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantFieldRefInfo} instance that is a copy of {@code constantFieldRefInfo}.
	 * <p>
	 * If {@code constantFieldRefInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param constantFieldRefInfo the {@code ConstantFieldRefInfo} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code constantFieldRefInfo} is {@code null}
	 */
	public ConstantFieldRefInfo(final ConstantFieldRefInfo constantFieldRefInfo) {
		super(NAME, TAG, 1);
		
		this.classIndex = constantFieldRefInfo.classIndex;
		this.nameAndTypeIndex = constantFieldRefInfo.nameAndTypeIndex;
	}
	
	/**
	 * Constructs a new {@code ConstantFieldRefInfo} instance.
	 * <p>
	 * If either {@code classIndex} or {@code nameAndTypeIndex} are less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param classIndex the value for the {@code class_index} item associated with this {@code ConstantFieldRefInfo} instance
	 * @param nameAndTypeIndex the value for the {@code name_and_type_index} item associated with this {@code ConstantFieldRefInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code classIndex} or {@code nameAndTypeIndex} are less than {@code 1}
	 */
	public ConstantFieldRefInfo(final int classIndex, final int nameAndTypeIndex) {
		super(NAME, TAG, 1);
		
		this.classIndex = ParameterArguments.requireRange(classIndex, 1, Integer.MAX_VALUE, "classIndex");
		this.nameAndTypeIndex = ParameterArguments.requireRange(nameAndTypeIndex, 1, Integer.MAX_VALUE, "nameAndTypeIndex");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantFieldRefInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantFieldRefInfo} instance
	 */
	@Override
	public ConstantFieldRefInfo copy() {
		return new ConstantFieldRefInfo(this);
	}
	
	/**
	 * Writes this {@code ConstantFieldRefInfo} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * constantFieldRefInfo.write(new Document());
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
	 * Writes this {@code ConstantFieldRefInfo} to {@code document}.
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
		document.linef("u2 class_index = %s;", Integer.toString(getClassIndex()));
		document.linef("u2 name_and_type_index = %s;", Integer.toString(getNameAndTypeIndex()));
		document.outdent();
		document.linef("};");
		
		return document;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantFieldRefInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantFieldRefInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("new ConstantFieldRefInfo(%s, %s)", Integer.toString(getClassIndex()), Integer.toString(getNameAndTypeIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code ConstantFieldRefInfo} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantFieldRefInfo}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConstantFieldRefInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantFieldRefInfo}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantFieldRefInfo)) {
			return false;
		} else if(!Objects.equals(getName(), ConstantFieldRefInfo.class.cast(object).getName())) {
			return false;
		} else if(getTag() != ConstantFieldRefInfo.class.cast(object).getTag()) {
			return false;
		} else if(getConstantPoolEntryCount() != ConstantFieldRefInfo.class.cast(object).getConstantPoolEntryCount()) {
			return false;
		} else if(getClassIndex() != ConstantFieldRefInfo.class.cast(object).getClassIndex()) {
			return false;
		} else if(getNameAndTypeIndex() != ConstantFieldRefInfo.class.cast(object).getNameAndTypeIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code class_index} item associated with this {@code ConstantFieldRefInfo} instance.
	 * 
	 * @return the value of the {@code class_index} item associated with this {@code ConstantFieldRefInfo} instance
	 */
	public int getClassIndex() {
		return this.classIndex;
	}
	
	/**
	 * Returns the value of the {@code name_and_type_index} item associated with this {@code ConstantFieldRefInfo} instance.
	 * 
	 * @return the value of the {@code name_and_type_index} item associated with this {@code ConstantFieldRefInfo} instance
	 */
	public int getNameAndTypeIndex() {
		return this.nameAndTypeIndex;
	}
	
	/**
	 * Returns a hash code for this {@code ConstantFieldRefInfo} instance.
	 * 
	 * @return a hash code for this {@code ConstantFieldRefInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Integer.valueOf(getClassIndex()), Integer.valueOf(getNameAndTypeIndex()));
	}
	
	/**
	 * Sets {@code classIndex} as the value for the {@code class_index} item associated with this {@code ConstantFieldRefInfo} instance.
	 * <p>
	 * If {@code classIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param classIndex the value for the {@code class_index} item associated with this {@code ConstantFieldRefInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code classIndex} is less than {@code 1}
	 */
	public void setClassIndex(final int classIndex) {
		this.classIndex = ParameterArguments.requireRange(classIndex, 1, Integer.MAX_VALUE, "classIndex");
	}
	
	/**
	 * Sets {@code nameAndTypeIndex} as the value for the {@code name_and_type_index} item associated with this {@code ConstantFieldRefInfo} instance.
	 * <p>
	 * If {@code nameAndTypeIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameAndTypeIndex the value for the {@code name_and_type_index} item associated with this {@code ConstantFieldRefInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameAndTypeIndex} is less than {@code 1}
	 */
	public void setNameAndTypeIndex(final int nameAndTypeIndex) {
		this.nameAndTypeIndex = ParameterArguments.requireRange(nameAndTypeIndex, 1, Integer.MAX_VALUE, "nameAndTypeIndex");
	}
	
	/**
	 * Writes this {@code ConstantFieldRefInfo} to {@code dataOutput}.
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
			dataOutput.writeShort(getClassIndex());
			dataOutput.writeShort(getNameAndTypeIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantFieldRefInfo} instances in {@code node}.
	 * <p>
	 * All {@code ConstantFieldRefInfo} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantFieldRefInfo} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantFieldRefInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantFieldRefInfo.class);
	}
}