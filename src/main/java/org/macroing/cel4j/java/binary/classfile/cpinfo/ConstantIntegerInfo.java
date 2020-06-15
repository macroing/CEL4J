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
 * A {@code ConstantIntegerInfo} denotes a CONSTANT_Integer_info structure in the constant_pool of a ClassFile structure.
 * <p>
 * The CONSTANT_Integer_info structure was added to Java in version 1.0.2.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantIntegerInfo extends CPInfo {
	/**
	 * The name of the CONSTANT_Integer_info structure.
	 */
	public static final String NAME = "CONSTANT_Integer";
	
	/**
	 * The tag for CONSTANT_Integer.
	 */
	public static final int TAG = 3;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int value;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantIntegerInfo}.
	 * 
	 * @param value the {@code int} representation of the new {@code ConstantIntegerInfo} instance
	 */
	public ConstantIntegerInfo(final int value) {
		super(NAME, TAG, 1);
		
		this.value = value;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantIntegerInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantIntegerInfo} instance
	 */
	@Override
	public ConstantIntegerInfo copy() {
		return new ConstantIntegerInfo(this.value);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantIntegerInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantIntegerInfo} instance
	 */
	@Override
	public String toString() {
		return Integer.toString(this.value);
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantIntegerInfo}, and that {@code ConstantIntegerInfo} instance is equal to this {@code ConstantIntegerInfo} instance, {@code false}
	 * otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code ConstantIntegerInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantIntegerInfo}, and that {@code ConstantIntegerInfo} instance is equal to this {@code ConstantIntegerInfo} instance, {@code false}
	 * otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantIntegerInfo)) {
			return false;
		} else if(!Objects.equals(ConstantIntegerInfo.class.cast(object).getName(), getName())) {
			return false;
		} else if(ConstantIntegerInfo.class.cast(object).getTag() != getTag()) {
			return false;
		} else if(ConstantIntegerInfo.class.cast(object).getConstantPoolEntryCount() != getConstantPoolEntryCount()) {
			return false;
		} else if(ConstantIntegerInfo.class.cast(object).value != this.value) {
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
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Integer.valueOf(this.value));
	}
	
	/**
	 * Returns an {@code int} representation of this {@code ConstantIntegerInfo} instance.
	 * 
	 * @return an {@code int} representation of this {@code ConstantIntegerInfo} instance
	 */
	public int getInt() {
		return this.value;
	}
	
	/**
	 * Sets a new {@code int} representation for this {@code ConstantIntegerInfo} instance.
	 * 
	 * @param value the new {@code int} representation
	 */
	public void setInt(final int value) {
		this.value = value;
	}
	
	/**
	 * Writes this {@code ConstantIntegerInfo} to {@code dataOutput}.
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
			dataOutput.writeInt(this.value);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Writes this {@code ConstantIntegerInfo} to {@code document}.
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
		document.linef("int value = %s;", Integer.toString(getInt()));
		document.outdent();
		document.linef("};");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantIntegerInfo}s.
	 * <p>
	 * All {@code ConstantIntegerInfo}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantIntegerInfo}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantIntegerInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantIntegerInfo.class);
	}
}