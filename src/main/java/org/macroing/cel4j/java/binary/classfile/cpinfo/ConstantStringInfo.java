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
 * A {@code ConstantStringInfo} denotes a CONSTANT_String_info structure in the constant_pool of a ClassFile structure.
 * <p>
 * The CONSTANT_String_info structure was added to Java in version 1.0.2.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantStringInfo extends CPInfo {
	/**
	 * The name of the CONSTANT_String_info structure.
	 */
	public static final String NAME = "CONSTANT_String";
	
	/**
	 * The tag for CONSTANT_String.
	 */
	public static final int TAG = 8;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int stringIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantStringInfo}.
	 * <p>
	 * If {@code stringIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param stringIndex the string_index of the new {@code ConstantStringInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code stringIndex} is less than or equal to {@code 0}
	 */
	public ConstantStringInfo(final int stringIndex) {
		super(NAME, TAG, 1);
		
		this.stringIndex = ParameterArguments.requireRange(stringIndex, 1, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantStringInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantStringInfo} instance
	 */
	@Override
	public ConstantStringInfo copy() {
		return new ConstantStringInfo(this.stringIndex);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantStringInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantStringInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("CONSTANT_String_info: string_index=%s", Integer.toString(this.stringIndex));
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantStringInfo}, and that {@code ConstantStringInfo} instance is equal to this {@code ConstantStringInfo} instance, {@code false}
	 * otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code ConstantStringInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantStringInfo}, and that {@code ConstantStringInfo} instance is equal to this {@code ConstantStringInfo} instance, {@code false}
	 * otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantStringInfo)) {
			return false;
		} else if(!Objects.equals(ConstantStringInfo.class.cast(object).getName(), getName())) {
			return false;
		} else if(ConstantStringInfo.class.cast(object).getTag() != getTag()) {
			return false;
		} else if(ConstantStringInfo.class.cast(object).getConstantPoolEntryCount() != getConstantPoolEntryCount()) {
			return false;
		} else if(ConstantStringInfo.class.cast(object).stringIndex != this.stringIndex) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the string_index of this {@code ConstantStringInfo} instance.
	 * 
	 * @return the string_index of this {@code ConstantStringInfo} instance
	 */
	public int getStringIndex() {
		return this.stringIndex;
	}
	
	/**
	 * Returns a hash code for this {@code ConstantStringInfo} instance.
	 * 
	 * @return a hash code for this {@code ConstantStringInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Integer.valueOf(this.stringIndex));
	}
	
	/**
	 * Sets a new string_index for this {@code ConstantStringInfo} instance.
	 * <p>
	 * If {@code stringIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param stringIndex the new string_index for this {@code ConstantStringInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code stringIndex} is less than or equal to {@code 0}
	 */
	public void setStringIndex(final int stringIndex) {
		this.stringIndex = ParameterArguments.requireRange(stringIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code ConstantStringInfo} to {@code dataOutput}.
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
			dataOutput.writeShort(this.stringIndex);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Writes this {@code ConstantStringInfo} to {@code document}.
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
		document.linef("u2 string_index = %s;", Integer.toString(getStringIndex()));
		document.outdent();
		document.linef("};");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantStringInfo}s.
	 * <p>
	 * All {@code ConstantStringInfo}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantStringInfo}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantStringInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantStringInfo.class);
	}
}