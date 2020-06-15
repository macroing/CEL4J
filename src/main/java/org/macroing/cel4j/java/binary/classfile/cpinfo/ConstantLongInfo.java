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
 * A {@code ConstantLongInfo} denotes a CONSTANT_Long_info structure in the constant_pool of a ClassFile structure.
 * <p>
 * The CONSTANT_Long_info structure was added to Java in version 1.0.2.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantLongInfo extends CPInfo {
	/**
	 * The name of the CONSTANT_Long_info structure.
	 */
	public static final String NAME = "CONSTANT_Long";
	
	/**
	 * The tag for CONSTANT_Long.
	 */
	public static final int TAG = 5;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long value;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantLongInfo}.
	 * 
	 * @param value the {@code long} representation of the new {@code ConstantLongInfo} instance
	 */
	public ConstantLongInfo(final long value) {
		super(NAME, TAG, 2);
		
		this.value = value;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantLongInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantLongInfo} instance
	 */
	@Override
	public ConstantLongInfo copy() {
		return new ConstantLongInfo(this.value);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantLongInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantLongInfo} instance
	 */
	@Override
	public String toString() {
		return Long.toString(this.value);
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantLongInfo}, and that {@code ConstantLongInfo} instance is equal to this {@code ConstantLongInfo} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code ConstantLongInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantLongInfo}, and that {@code ConstantLongInfo} instance is equal to this {@code ConstantLongInfo} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantLongInfo)) {
			return false;
		} else if(!Objects.equals(ConstantLongInfo.class.cast(object).getName(), getName())) {
			return false;
		} else if(ConstantLongInfo.class.cast(object).getTag() != getTag()) {
			return false;
		} else if(ConstantLongInfo.class.cast(object).getConstantPoolEntryCount() != getConstantPoolEntryCount()) {
			return false;
		} else if(Long.compare(ConstantLongInfo.class.cast(object).value, this.value) != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code ConstantLongInfo} instance.
	 * 
	 * @return a hash code for this {@code ConstantLongInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Long.valueOf(this.value));
	}
	
	/**
	 * Returns a {@code long} representation of this {@code ConstantLongInfo} instance.
	 * 
	 * @return a {@code long} representation of this {@code ConstantLongInfo} instance
	 */
	public long getLong() {
		return this.value;
	}
	
	/**
	 * Sets a new {@code long} representation for this {@code ConstantLongInfo} instance.
	 * 
	 * @param value the new {@code long} representation
	 */
	public void setLong(final long value) {
		this.value = value;
	}
	
	/**
	 * Writes this {@code ConstantLongInfo} to {@code dataOutput}.
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
			dataOutput.writeLong(this.value);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Writes this {@code ConstantLongInfo} to {@code document}.
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
		document.linef("long value = %s;", Long.toString(getLong()));
		document.outdent();
		document.linef("};");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantLongInfo}s.
	 * <p>
	 * All {@code ConstantLongInfo}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantLongInfo}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantLongInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantLongInfo.class);
	}
}