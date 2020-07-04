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

/**
 * A {@code ConstantIntegerInfo} represents a {@code CONSTANT_Integer_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code CONSTANT_Integer_info} structure was added to Java in version 1.0.2 and can be found in the {@code constant_pool} table item of a {@code ClassFile} structure.
 * <p>
 * The {@code CONSTANT_Integer_info} structure has the following format:
 * <pre>
 * <code>
 * CONSTANT_Integer_info {
 *     u1 tag;
 *     u4 bytes;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantIntegerInfo extends CPInfo {
	/**
	 * The name of the {@code CONSTANT_Integer_info} structure.
	 */
	public static final String NAME = "CONSTANT_Integer";
	
	/**
	 * The tag for the {@code CONSTANT_Integer_info} structure.
	 */
	public static final int TAG = 3;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int intValue;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantIntegerInfo} instance that is a copy of {@code constantIntegerInfo}.
	 * <p>
	 * If {@code constantIntegerInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param constantIntegerInfo the {@code ConstantIntegerInfo} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code constantIntegerInfo} is {@code null}
	 */
	public ConstantIntegerInfo(final ConstantIntegerInfo constantIntegerInfo) {
		super(NAME, TAG, 1);
		
		this.intValue = constantIntegerInfo.intValue;
	}
	
	/**
	 * Constructs a new {@code ConstantIntegerInfo} instance.
	 * 
	 * @param intValue the {@code int} value for the {@code bytes} item associated with this {@code ConstantIntegerInfo} instance
	 */
	public ConstantIntegerInfo(final int intValue) {
		super(NAME, TAG, 1);
		
		this.intValue = intValue;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantIntegerInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantIntegerInfo} instance
	 */
	@Override
	public ConstantIntegerInfo copy() {
		return new ConstantIntegerInfo(this);
	}
	
	/**
	 * Writes this {@code ConstantIntegerInfo} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * constantIntegerInfo.write(new Document());
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
	 * Writes this {@code ConstantIntegerInfo} to {@code document}.
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
		document.linef("int value = %s;", Integer.toString(getIntValue()));
		document.outdent();
		document.linef("};");
		
		return document;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantIntegerInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantIntegerInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("new ConstantIntegerInfo(%s)", Integer.toString(getIntValue()));
	}
	
	/**
	 * Compares {@code object} to this {@code ConstantIntegerInfo} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantIntegerInfo}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConstantIntegerInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantIntegerInfo}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantIntegerInfo)) {
			return false;
		} else if(!Objects.equals(getName(), ConstantIntegerInfo.class.cast(object).getName())) {
			return false;
		} else if(getTag() != ConstantIntegerInfo.class.cast(object).getTag()) {
			return false;
		} else if(getConstantPoolEntryCount() != ConstantIntegerInfo.class.cast(object).getConstantPoolEntryCount()) {
			return false;
		} else if(getIntValue() != ConstantIntegerInfo.class.cast(object).getIntValue()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code ConstantIntegerInfo} instance.
	 * 
	 * @return a hash code for this {@code ConstantIntegerInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Integer.valueOf(getIntValue()));
	}
	
	/**
	 * Returns the {@code int} value for the {@code bytes} item associated with this {@code ConstantIntegerInfo} instance.
	 * 
	 * @return the {@code int} value for the {@code bytes} item associated with this {@code ConstantIntegerInfo} instance
	 */
	public int getIntValue() {
		return this.intValue;
	}
	
	/**
	 * Sets {@code intValue} as the {@code int} value for the {@code bytes} item associated with this {@code ConstantIntegerInfo} instance.
	 * 
	 * @param intValue the {@code int} value for the {@code bytes} item associated with this {@code ConstantIntegerInfo} instance
	 */
	public void setIntValue(final int intValue) {
		this.intValue = intValue;
	}
	
	/**
	 * Writes this {@code ConstantIntegerInfo} to {@code dataOutput}.
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
			dataOutput.writeInt(getIntValue());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantIntegerInfo} instances in {@code node}.
	 * <p>
	 * All {@code ConstantIntegerInfo} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantIntegerInfo} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantIntegerInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantIntegerInfo.class);
	}
}