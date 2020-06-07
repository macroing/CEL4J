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
 * A {@code ConstantFieldRefInfo} denotes a CONSTANT_Fieldref_info structure in the constant_pool of a ClassFile structure.
 * <p>
 * The CONSTANT_Fieldref_info structure was added to Java in version 1.0.2.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantFieldRefInfo extends CPInfo {
	/**
	 * The name of the CONSTANT_Fieldref_info structure.
	 */
	public static final String NAME = "CONSTANT_Fieldref";
	
	/**
	 * The tag for CONSTANT_Fieldref.
	 */
	public static final int TAG = 9;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int classIndex;
	private int nameAndTypeIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ConstantFieldRefInfo(final int classIndex, final int nameAndTypeIndex) {
		super(NAME, TAG, 1);
		
		this.classIndex = classIndex;
		this.nameAndTypeIndex = nameAndTypeIndex;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantFieldRefInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantFieldRefInfo} instance
	 */
	@Override
	public ConstantFieldRefInfo copy() {
		return new ConstantFieldRefInfo(this.classIndex, this.nameAndTypeIndex);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantFieldRefInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantFieldRefInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("CONSTANT_Fieldref_info: class_index=%s, name_and_type_index=%s", Integer.toString(this.classIndex), Integer.toString(this.nameAndTypeIndex));
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantFieldRefInfo}, and that {@code ConstantFieldRefInfo} instance is equal to this {@code ConstantFieldRefInfo} instance,
	 * {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code ConstantFieldRefInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantFieldRefInfo}, and that {@code ConstantFieldRefInfo} instance is equal to this {@code ConstantFieldRefInfo} instance,
	 * {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantFieldRefInfo)) {
			return false;
		} else if(!Objects.equals(ConstantFieldRefInfo.class.cast(object).getName(), getName())) {
			return false;
		} else if(ConstantFieldRefInfo.class.cast(object).getTag() != getTag()) {
			return false;
		} else if(ConstantFieldRefInfo.class.cast(object).getConstantPoolEntryCount() != getConstantPoolEntryCount()) {
			return false;
		} else if(ConstantFieldRefInfo.class.cast(object).classIndex != this.classIndex) {
			return false;
		} else if(ConstantFieldRefInfo.class.cast(object).nameAndTypeIndex != this.nameAndTypeIndex) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the class_index of this {@code ConstantFieldRefInfo} instance.
	 * 
	 * @return the class_index of this {@code ConstantFieldRefInfo} instance
	 */
	public int getClassIndex() {
		return this.classIndex;
	}
	
	/**
	 * Returns the name_and_type_index of this {@code ConstantFieldRefInfo} instance.
	 * 
	 * @return the name_and_type_index of this {@code ConstantFieldRefInfo} instance
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
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Integer.valueOf(this.classIndex), Integer.valueOf(this.nameAndTypeIndex));
	}
	
	/**
	 * Sets a new class_index for this {@code ConstantFieldRefInfo} instance.
	 * <p>
	 * If {@code classIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param classIndex the new class_index for this {@code ConstantFieldRefInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code classIndex} is less than or equal to {@code 0}
	 */
	public void setClassIndex(final int classIndex) {
		this.classIndex = ParameterArguments.requireRange(classIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets a new name_and_type_index for this {@code ConstantFieldRefInfo} instance.
	 * <p>
	 * If {@code nameAndTypeIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameAndTypeIndex the new name_and_type_index for this {@code ConstantFieldRefInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameAndTypeIndex} is less than or equal to {@code 0}
	 */
	public void setNameAndTypeIndex(final int nameAndTypeIndex) {
		this.nameAndTypeIndex = ParameterArguments.requireRange(nameAndTypeIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code ConstantFieldRefInfo} to {@code dataOutput}.
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
	 * Writes this {@code ConstantFieldRefInfo} to {@code document}.
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
	 * Returns a new {@code ConstantFieldRefInfo}.
	 * <p>
	 * If either {@code classIndex} or {@code nameAndTypeIndex} are less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param classIndex the class_index for the new {@code ConstantFieldRefInfo} instance
	 * @param nameAndTypeIndex the name_and_type_index for the new {@code ConstantFieldRefInfo} instance
	 * @return a new {@code ConstantFieldRefInfo}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code classIndex} or {@code nameAndTypeIndex} are less than or equal to {@code 0}
	 */
	public static ConstantFieldRefInfo newInstance(final int classIndex, final int nameAndTypeIndex) {
		return new ConstantFieldRefInfo(ParameterArguments.requireRange(classIndex, 1, Integer.MAX_VALUE), ParameterArguments.requireRange(nameAndTypeIndex, 1, Integer.MAX_VALUE));
	}
	
	/**
	 * Returns a {@code List} with all {@code ConstantFieldRefInfo}s.
	 * <p>
	 * All {@code ConstantFieldRefInfo}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantFieldRefInfo}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantFieldRefInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantFieldRefInfo.class);
	}
}