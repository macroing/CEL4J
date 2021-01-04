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

/**
 * A {@code ConstantLongInfo} represents a {@code CONSTANT_Long_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code CONSTANT_Long_info} structure was added to Java in version 1.0.2 and can be found in the {@code constant_pool} table item of a {@code ClassFile} structure.
 * <p>
 * The {@code CONSTANT_Long_info} structure has the following format:
 * <pre>
 * <code>
 * CONSTANT_Long_info {
 *     u1 tag;
 *     u4 high_bytes;
 *     u4 low_bytes;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantLongInfo extends CPInfo {
	/**
	 * The name of the {@code CONSTANT_Long_info} structure.
	 */
	public static final String NAME = "CONSTANT_Long";
	
	/**
	 * The tag for the {@code CONSTANT_Long_info} structure.
	 */
	public static final int TAG = 5;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long longValue;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantLongInfo} instance that is a copy of {@code constantLongInfo}.
	 * <p>
	 * If {@code constantLongInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param constantLongInfo the {@code ConstantLongInfo} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code constantLongInfo} is {@code null}
	 */
	public ConstantLongInfo(final ConstantLongInfo constantLongInfo) {
		super(NAME, TAG, 2);
		
		this.longValue = constantLongInfo.longValue;
	}
	
	/**
	 * Constructs a new {@code ConstantLongInfo} instance.
	 * 
	 * @param longValue the {@code long} value for the {@code high_bytes} and {@code low_bytes} items associated with this {@code ConstantLongInfo} instance
	 */
	public ConstantLongInfo(final long longValue) {
		super(NAME, TAG, 2);
		
		this.longValue = longValue;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantLongInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantLongInfo} instance
	 */
	@Override
	public ConstantLongInfo copy() {
		return new ConstantLongInfo(this);
	}
	
	/**
	 * Writes this {@code ConstantLongInfo} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * constantLongInfo.write(new Document());
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
	 * Writes this {@code ConstantLongInfo} to {@code document}.
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
		document.linef("long value = %s;", Long.toString(getLongValue()));
		document.outdent();
		document.linef("};");
		
		return document;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantLongInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantLongInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("new ConstantLongInfo(%s)", Long.toString(getLongValue()));
	}
	
	/**
	 * Compares {@code object} to this {@code ConstantLongInfo} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantLongInfo}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConstantLongInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantLongInfo}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantLongInfo)) {
			return false;
		} else if(!Objects.equals(getName(), ConstantLongInfo.class.cast(object).getName())) {
			return false;
		} else if(getTag() != ConstantLongInfo.class.cast(object).getTag()) {
			return false;
		} else if(getConstantPoolEntryCount() != ConstantLongInfo.class.cast(object).getConstantPoolEntryCount()) {
			return false;
		} else if(Long.compare(getLongValue(), ConstantLongInfo.class.cast(object).getLongValue()) != 0) {
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
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Long.valueOf(getLongValue()));
	}
	
	/**
	 * Returns the {@code long} value for the {@code high_bytes} and {@code low_bytes} items associated with this {@code ConstantLongInfo} instance.
	 * 
	 * @return the {@code long} value for the {@code high_bytes} and {@code low_bytes} items associated with this {@code ConstantLongInfo} instance
	 */
	public long getLongValue() {
		return this.longValue;
	}
	
	/**
	 * Sets {@code longValue} as the {@code long} value for the {@code high_bytes} and {@code low_bytes} items associated with this {@code ConstantLongInfo} instance.
	 * 
	 * @param longValue the {@code long} value for the {@code high_bytes} and {@code low_bytes} items associated with this {@code ConstantLongInfo} instance
	 */
	public void setLongValue(final long longValue) {
		this.longValue = longValue;
	}
	
	/**
	 * Writes this {@code ConstantLongInfo} to {@code dataOutput}.
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
			dataOutput.writeLong(getLongValue());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantLongInfo} instances in {@code node}.
	 * <p>
	 * All {@code ConstantLongInfo} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantLongInfo} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantLongInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantLongInfo.class);
	}
}