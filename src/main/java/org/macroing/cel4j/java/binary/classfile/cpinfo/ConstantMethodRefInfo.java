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
 * A {@code ConstantMethodRefInfo} denotes a CONSTANT_Methodref_info structure in the constant_pool of a ClassFile structure.
 * <p>
 * The CONSTANT_Methodref_info structure was added to Java in version 1.0.2.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantMethodRefInfo extends CPInfo {
	/**
	 * The name of the CONSTANT_Methodref_info structure.
	 */
	public static final String NAME = "CONSTANT_Methodref";
	
	/**
	 * The tag for CONSTANT_Methodref.
	 */
	public static final int TAG = 10;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int classIndex;
	private int nameAndTypeIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantMethodRefInfo}.
	 * <p>
	 * If either {@code classIndex} or {@code nameAndTypeIndex} are less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param classIndex the class_index of the new {@code ConstantMethodRefInfo} instance
	 * @param nameAndTypeIndex the name_and_type_index of the new {@code ConstantMethodRefInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code classIndex} or {@code nameAndTypeIndex} are less than or equal to {@code 0}
	 */
	public ConstantMethodRefInfo(final int classIndex, final int nameAndTypeIndex) {
		super(NAME, TAG, 1);
		
		this.classIndex = ParameterArguments.requireRange(classIndex, 1, Integer.MAX_VALUE);
		this.nameAndTypeIndex = ParameterArguments.requireRange(nameAndTypeIndex, 1, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantMethodRefInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantMethodRefInfo} instance
	 */
	@Override
	public ConstantMethodRefInfo copy() {
		return new ConstantMethodRefInfo(this.classIndex, this.nameAndTypeIndex);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantMethodRefInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantMethodRefInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("CONSTANT_Methodref_info: class_index=%s, name_and_type_index=%s", Integer.toString(this.classIndex), Integer.toString(this.nameAndTypeIndex));
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantMethodRefInfo}, and that {@code ConstantMethodRefInfo} instance is equal to this {@code ConstantMethodRefInfo} instance,
	 * {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code ConstantMethodRefInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantMethodRefInfo}, and that {@code ConstantMethodRefInfo} instance is equal to this {@code ConstantMethodRefInfo} instance,
	 * {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantMethodRefInfo)) {
			return false;
		} else if(!Objects.equals(ConstantMethodRefInfo.class.cast(object).getName(), getName())) {
			return false;
		} else if(ConstantMethodRefInfo.class.cast(object).getTag() != getTag()) {
			return false;
		} else if(ConstantMethodRefInfo.class.cast(object).getConstantPoolEntryCount() != getConstantPoolEntryCount()) {
			return false;
		} else if(ConstantMethodRefInfo.class.cast(object).classIndex != this.classIndex) {
			return false;
		} else if(ConstantMethodRefInfo.class.cast(object).nameAndTypeIndex != this.nameAndTypeIndex) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the class_index of this {@code ConstantMethodRefInfo} instance.
	 * 
	 * @return the class_index of this {@code ConstantMethodRefInfo} instance
	 */
	public int getClassIndex() {
		return this.classIndex;
	}
	
	/**
	 * Returns the name_and_type_index of this {@code ConstantMethodRefInfo} instance.
	 * 
	 * @return the name_and_type_index of this {@code ConstantMethodRefInfo} instance
	 */
	public int getNameAndTypeIndex() {
		return this.nameAndTypeIndex;
	}
	
	/**
	 * Returns a hash code for this {@code ConstantMethodRefInfo} instance.
	 * 
	 * @return a hash code for this {@code ConstantMethodRefInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Integer.valueOf(this.classIndex), Integer.valueOf(this.nameAndTypeIndex));
	}
	
	/**
	 * Sets a new class_index for this {@code ConstantMethodRefInfo} instance.
	 * <p>
	 * If {@code classIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param classIndex the new class_index for this {@code ConstantMethodRefInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code classIndex} is less than or equal to {@code 0}
	 */
	public void setClassIndex(final int classIndex) {
		this.classIndex = ParameterArguments.requireRange(classIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets a new name_and_type_index for this {@code ConstantMethodRefInfo} instance.
	 * <p>
	 * If {@code nameAndTypeIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameAndTypeIndex the new name_and_type_index for this {@code ConstantMethodRefInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameAndTypeIndex} is less than or equal to {@code 0}
	 */
	public void setNameAndTypeIndex(final int nameAndTypeIndex) {
		this.nameAndTypeIndex = ParameterArguments.requireRange(nameAndTypeIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code ConstantMethodRefInfo} to {@code dataOutput}.
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
			dataOutput.writeByte(getTag());
			dataOutput.writeShort(this.classIndex);
			dataOutput.writeShort(this.nameAndTypeIndex);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Writes this {@code ConstantMethodRefInfo} to {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param document the {@link Document} to write to
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	@Override
	public void write(final Document document) {
		document.linef("%s_info = {", getName());
		document.indent();
		document.linef("u1 tag = %s;", Integer.toString(getTag()));
		document.linef("u2 class_index = %s;", Integer.toString(getClassIndex()));
		document.linef("u2 name_and_type_index = %s;", Integer.toString(getNameAndTypeIndex()));
		document.outdent();
		document.linef("};");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantMethodRefInfo}s.
	 * <p>
	 * All {@code ConstantMethodRefInfo}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantMethodRefInfo}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantMethodRefInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantMethodRefInfo.class);
	}
}