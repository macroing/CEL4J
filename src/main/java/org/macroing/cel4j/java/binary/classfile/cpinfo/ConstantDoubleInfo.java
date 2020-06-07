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
 * A {@code ConstantDoubleInfo} denotes a CONSTANT_Double_info structure in the constant_pool of a ClassFile structure.
 * <p>
 * The CONSTANT_Double_info structure was added to Java in version 1.0.2.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantDoubleInfo extends CPInfo {
	/**
	 * The name of the CONSTANT_Double_info structure.
	 */
	public static final String NAME = "CONSTANT_Double";
	
	/**
	 * The tag for CONSTANT_Double.
	 */
	public static final int TAG = 6;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private double value;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ConstantDoubleInfo(final double value) {
		super(NAME, TAG, 2);
		
		this.value = value;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantDoubleInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantDoubleInfo} instance
	 */
	@Override
	public ConstantDoubleInfo copy() {
		return new ConstantDoubleInfo(this.value);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantDoubleInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantDoubleInfo} instance
	 */
	@Override
	public String toString() {
		return Double.toString(this.value);
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantDoubleInfo}, and that {@code ConstantDoubleInfo} instance is equal to this {@code ConstantDoubleInfo} instance, {@code false}
	 * otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code ConstantDoubleInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantDoubleInfo}, and that {@code ConstantDoubleInfo} instance is equal to this {@code ConstantDoubleInfo} instance, {@code false}
	 * otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantDoubleInfo)) {
			return false;
		} else if(!Objects.equals(ConstantDoubleInfo.class.cast(object).getName(), getName())) {
			return false;
		} else if(ConstantDoubleInfo.class.cast(object).getTag() != getTag()) {
			return false;
		} else if(ConstantDoubleInfo.class.cast(object).getConstantPoolEntryCount() != getConstantPoolEntryCount()) {
			return false;
		} else if(Double.compare(ConstantDoubleInfo.class.cast(object).value, this.value) != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a {@code double} representation of this {@code ConstantDoubleInfo} instance.
	 * 
	 * @return a {@code double} representation of this {@code ConstantDoubleInfo} instance
	 */
	public double getDouble() {
		return this.value;
	}
	
	/**
	 * Returns a hash code for this {@code ConstantDoubleInfo} instance.
	 * 
	 * @return a hash code for this {@code ConstantDoubleInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Double.valueOf(this.value));
	}
	
	/**
	 * Sets a new {@code double} representation for this {@code ConstantDoubleInfo} instance.
	 * 
	 * @param value the new {@code double} representation
	 */
	public void setDouble(final double value) {
		this.value = value;
	}
	
	/**
	 * Writes this {@code ConstantDoubleInfo} to {@code dataOutput}.
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
			dataOutput.writeDouble(this.value);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Writes this {@code ConstantDoubleInfo} to {@code document}.
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
		document.linef("double value = %f;", Double.valueOf(getDouble()));
		document.outdent();
		document.linef("};");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code ConstantDoubleInfo}.
	 * 
	 * @param value the {@code double} representation of the new {@code ConstantDoubleInfo} instance
	 * @return a new {@code ConstantDoubleInfo}
	 */
	public static ConstantDoubleInfo newInstance(final double value) {
		return new ConstantDoubleInfo(value);
	}
	
	/**
	 * Returns a {@code List} with all {@code ConstantDoubleInfo}s.
	 * <p>
	 * All {@code ConstantDoubleInfo}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantDoubleInfo}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantDoubleInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantDoubleInfo.class);
	}
}