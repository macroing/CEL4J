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

/**
 * A {@code ConstantDoubleInfo} represents a {@code CONSTANT_Double_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code CONSTANT_Double_info} structure was added to Java in version 1.0.2 and can be found in the {@code constant_pool} table item of a {@code ClassFile} structure.
 * <p>
 * The {@code CONSTANT_Double_info} structure has the following format:
 * <pre>
 * <code>
 * CONSTANT_Double_info {
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
public final class ConstantDoubleInfo extends CPInfo {
	/**
	 * The name of the {@code CONSTANT_Double_info} structure.
	 */
	public static final String NAME = "CONSTANT_Double";
	
	/**
	 * The tag for the {@code CONSTANT_Double_info} structure.
	 */
	public static final int TAG = 6;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private double doubleValue;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantDoubleInfo} instance that is a copy of {@code constantDoubleInfo}.
	 * <p>
	 * If {@code constantDoubleInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param constantDoubleInfo the {@code ConstantDoubleInfo} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code constantDoubleInfo} is {@code null}
	 */
	public ConstantDoubleInfo(final ConstantDoubleInfo constantDoubleInfo) {
		super(NAME, TAG, 2);
		
		this.doubleValue = constantDoubleInfo.doubleValue;
	}
	
	/**
	 * Constructs a new {@code ConstantDoubleInfo} instance.
	 * 
	 * @param doubleValue the {@code double} value for the {@code high_bytes} and {@code low_bytes} items associated with this {@code ConstantDoubleInfo} instance
	 */
	public ConstantDoubleInfo(final double doubleValue) {
		super(NAME, TAG, 2);
		
		this.doubleValue = doubleValue;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantDoubleInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantDoubleInfo} instance
	 */
	@Override
	public ConstantDoubleInfo copy() {
		return new ConstantDoubleInfo(this);
	}
	
	/**
	 * Writes this {@code ConstantDoubleInfo} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * constantDoubleInfo.write(new Document());
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
	 * Writes this {@code ConstantDoubleInfo} to {@code document}.
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
		document.linef("double value = %f;", Double.valueOf(getDoubleValue()));
		document.outdent();
		document.linef("};");
		
		return document;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantDoubleInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantDoubleInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("new ConstantDoubleInfo(%s)", Double.toString(getDoubleValue()));
	}
	
	/**
	 * Compares {@code object} to this {@code ConstantDoubleInfo} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantDoubleInfo}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConstantDoubleInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantDoubleInfo}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantDoubleInfo)) {
			return false;
		} else if(!Objects.equals(getName(), ConstantDoubleInfo.class.cast(object).getName())) {
			return false;
		} else if(getTag() != ConstantDoubleInfo.class.cast(object).getTag()) {
			return false;
		} else if(getConstantPoolEntryCount() != ConstantDoubleInfo.class.cast(object).getConstantPoolEntryCount()) {
			return false;
		} else if(Double.compare(getDoubleValue(), ConstantDoubleInfo.class.cast(object).getDoubleValue()) != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the {@code double} value for the {@code high_bytes} and {@code low_bytes} items associated with this {@code ConstantDoubleInfo} instance.
	 * 
	 * @return the {@code double} value for the {@code high_bytes} and {@code low_bytes} items associated with this {@code ConstantDoubleInfo} instance
	 */
	public double getDoubleValue() {
		return this.doubleValue;
	}
	
	/**
	 * Returns a hash code for this {@code ConstantDoubleInfo} instance.
	 * 
	 * @return a hash code for this {@code ConstantDoubleInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Double.valueOf(getDoubleValue()));
	}
	
	/**
	 * Sets {@code doubleValue} as the {@code double} value for the {@code high_bytes} and {@code low_bytes} items associated with this {@code ConstantDoubleInfo} instance.
	 * 
	 * @param doubleValue the {@code double} value for the {@code high_bytes} and {@code low_bytes} items associated with this {@code ConstantDoubleInfo} instance
	 */
	public void setDoubleValue(final double doubleValue) {
		this.doubleValue = doubleValue;
	}
	
	/**
	 * Writes this {@code ConstantDoubleInfo} to {@code dataOutput}.
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
			dataOutput.writeDouble(getDoubleValue());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantDoubleInfo} instances in {@code node}.
	 * <p>
	 * All {@code ConstantDoubleInfo} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantDoubleInfo} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantDoubleInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantDoubleInfo.class);
	}
}