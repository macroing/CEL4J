/**
 * Copyright 2009 - 2024 J&#246;rgen Lundgren
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
 * A {@code ConstantStringInfo} represents a {@code CONSTANT_String_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code CONSTANT_String_info} structure was added to Java in version 1.0.2 and can be found in the {@code constant_pool} table item of a {@code ClassFile} structure.
 * <p>
 * The {@code CONSTANT_String_info} structure has the following format:
 * <pre>
 * <code>
 * CONSTANT_String_info {
 *     u1 tag;
 *     u2 string_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantStringInfo extends CPInfo {
	/**
	 * The name of the {@code CONSTANT_String_info} structure.
	 */
	public static final String NAME = "CONSTANT_String";
	
	/**
	 * The tag for the {@code CONSTANT_String_info} structure.
	 */
	public static final int TAG = 8;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int stringIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantStringInfo} instance that is a copy of {@code constantStringInfo}.
	 * <p>
	 * If {@code constantStringInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param constantStringInfo the {@code ConstantStringInfo} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code constantStringInfo} is {@code null}
	 */
	public ConstantStringInfo(final ConstantStringInfo constantStringInfo) {
		super(NAME, TAG, 1);
		
		this.stringIndex = constantStringInfo.stringIndex;
	}
	
	/**
	 * Constructs a new {@code ConstantStringInfo} instance.
	 * <p>
	 * If {@code stringIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param stringIndex the value for the {@code string_index} item associated with this {@code ConstantStringInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code stringIndex} is less than {@code 1}
	 */
	public ConstantStringInfo(final int stringIndex) {
		super(NAME, TAG, 1);
		
		this.stringIndex = ParameterArguments.requireRange(stringIndex, 1, Integer.MAX_VALUE, "stringIndex");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantStringInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantStringInfo} instance
	 */
	@Override
	public ConstantStringInfo copy() {
		return new ConstantStringInfo(this);
	}
	
	/**
	 * Writes this {@code ConstantStringInfo} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * constantStringInfo.write(new Document());
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
	 * Writes this {@code ConstantStringInfo} to {@code document}.
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
		document.linef("u2 string_index = %s;", Integer.toString(getStringIndex()));
		document.outdent();
		document.linef("};");
		
		return document;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantStringInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantStringInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("new ConstantStringInfo(%s)", Integer.toString(getStringIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code ConstantStringInfo} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantStringInfo}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConstantStringInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantStringInfo}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantStringInfo)) {
			return false;
		} else if(!Objects.equals(getName(), ConstantStringInfo.class.cast(object).getName())) {
			return false;
		} else if(getTag() != ConstantStringInfo.class.cast(object).getTag()) {
			return false;
		} else if(getConstantPoolEntryCount() != ConstantStringInfo.class.cast(object).getConstantPoolEntryCount()) {
			return false;
		} else if(getStringIndex() != ConstantStringInfo.class.cast(object).getStringIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code string_index} item associated with this {@code ConstantStringInfo} instance.
	 * 
	 * @return the value of the {@code string_index} item associated with this {@code ConstantStringInfo} instance
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
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Integer.valueOf(getStringIndex()));
	}
	
	/**
	 * Sets {@code stringIndex} as the value for the {@code string_index} item associated with this {@code ConstantStringInfo} instance.
	 * <p>
	 * If {@code stringIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param stringIndex the value for the {@code string_index} item associated with this {@code ConstantStringInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code stringIndex} is less than {@code 1}
	 */
	public void setStringIndex(final int stringIndex) {
		this.stringIndex = ParameterArguments.requireRange(stringIndex, 1, Integer.MAX_VALUE, "stringIndex");
	}
	
	/**
	 * Writes this {@code ConstantStringInfo} to {@code dataOutput}.
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
			dataOutput.writeShort(getStringIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantStringInfo} instances in {@code node}.
	 * <p>
	 * All {@code ConstantStringInfo} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantStringInfo} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantStringInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantStringInfo.class);
	}
}