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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code LocalVariable} represents an entry in the {@code local_variable_table} item of the {@code LocalVariableTable_attribute} structure.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * Each entry has the following format:
 * <pre>
 * <code>
 * {
 *     u2 start_pc;
 *     u2 length;
 *     u2 name_index;
 *     u2 descriptor_index;
 *     u2 index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class LocalVariable implements Node {
	private int descriptorIndex;
	private int index;
	private int length;
	private int nameIndex;
	private int startPC;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code LocalVariable} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new LocalVariable(0, 0, 1, 1, 0);
	 * }
	 * </pre>
	 */
	public LocalVariable() {
		this(0, 0, 1, 1, 0);
	}
	
	/**
	 * Constructs a new {@code LocalVariable} instance that is a copy of {@code localVariable}.
	 * <p>
	 * If {@code localVariable} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param localVariable the {@code LocalVariable} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code localVariable} is {@code null}
	 */
	public LocalVariable(final LocalVariable localVariable) {
		this.startPC = localVariable.startPC;
		this.length = localVariable.length;
		this.nameIndex = localVariable.nameIndex;
		this.descriptorIndex = localVariable.descriptorIndex;
		this.index = localVariable.index;
	}
	
	/**
	 * Constructs a new {@code LocalVariable} instance.
	 * <p>
	 * If either {@code startPC}, {@code length} or {@code index} are less than {@code 0}, or {@code nameIndex} or {@code descriptorIndex} are less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param startPC the value for the {@code start_pc} item associated with this {@code LocalVariable} instance
	 * @param length the value for the {@code length} item associated with this {@code LocalVariable} instance
	 * @param nameIndex the value for the {@code name_index} item associated with this {@code LocalVariable} instance
	 * @param descriptorIndex the value for the {@code descriptor_index} item associated with this {@code LocalVariable} instance
	 * @param index the value for the {@code index} item associated with this {@code LocalVariable} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code startPC}, {@code length} or {@code index} are less than {@code 0}, or {@code nameIndex} or {@code descriptorIndex} are less than {@code 1}
	 */
	public LocalVariable(final int startPC, final int length, final int nameIndex, final int descriptorIndex, final int index) {
		this.startPC = ParameterArguments.requireRange(startPC, 0, Integer.MAX_VALUE, "startPC");
		this.length = ParameterArguments.requireRange(length, 0, Integer.MAX_VALUE, "length");
		this.nameIndex = ParameterArguments.requireRange(nameIndex, 1, Integer.MAX_VALUE, "nameIndex");
		this.descriptorIndex = ParameterArguments.requireRange(descriptorIndex, 1, Integer.MAX_VALUE, "descriptorIndex");
		this.index = ParameterArguments.requireRange(index, 0, Integer.MAX_VALUE, "index");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code LocalVariable} instance.
	 * 
	 * @return a copy of this {@code LocalVariable} instance
	 */
	public LocalVariable copy() {
		return new LocalVariable(this);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code LocalVariable} instance.
	 * 
	 * @return a {@code String} representation of this {@code LocalVariable} instance
	 */
	@Override
	public String toString() {
		return String.format("new LocalVariable(%s, %s, %s, %s, %s)", Integer.toString(getStartPC()), Integer.toString(getLength()), Integer.toString(getNameIndex()), Integer.toString(getDescriptorIndex()), Integer.toString(getIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code LocalVariable} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code LocalVariable}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code LocalVariable} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code LocalVariable}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof LocalVariable)) {
			return false;
		} else if(getStartPC() != LocalVariable.class.cast(object).getStartPC()) {
			return false;
		} else if(getLength() != LocalVariable.class.cast(object).getLength()) {
			return false;
		} else if(getNameIndex() != LocalVariable.class.cast(object).getNameIndex()) {
			return false;
		} else if(getDescriptorIndex() != LocalVariable.class.cast(object).getDescriptorIndex()) {
			return false;
		} else if(getIndex() != LocalVariable.class.cast(object).getIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value for the {@code descriptor_index} item associated with this {@code LocalVariable} instance.
	 * 
	 * @return the value for the {@code descriptor_index} item associated with this {@code LocalVariable} instance
	 */
	public int getDescriptorIndex() {
		return this.descriptorIndex;
	}
	
	/**
	 * Returns the value for the {@code index} item associated with this {@code LocalVariable} instance.
	 * 
	 * @return the value for the {@code index} item associated with this {@code LocalVariable} instance
	 */
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Returns the value for the {@code length} item associated with this {@code LocalVariable} instance.
	 * 
	 * @return the value for the {@code length} item associated with this {@code LocalVariable} instance
	 */
	public int getLength() {
		return this.length;
	}
	
	/**
	 * Returns the value for the {@code name_index} item associated with this {@code LocalVariable} instance.
	 * 
	 * @return the value for the {@code name_index} item associated with this {@code LocalVariable} instance
	 */
	public int getNameIndex() {
		return this.nameIndex;
	}
	
	/**
	 * Returns the value for the {@code start_pc} item associated with this {@code LocalVariable} instance.
	 * 
	 * @return the value for the {@code start_pc} item associated with this {@code LocalVariable} instance
	 */
	public int getStartPC() {
		return this.startPC;
	}
	
	/**
	 * Returns a hash code for this {@code LocalVariable} instance.
	 * 
	 * @return a hash code for this {@code LocalVariable} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getStartPC()), Integer.valueOf(getLength()), Integer.valueOf(getNameIndex()), Integer.valueOf(getDescriptorIndex()), Integer.valueOf(getIndex()));
	}
	
	/**
	 * Sets {@code descriptorIndex} as the value for the {@code descriptor_index} item associated with this {@code LocalVariable} instance.
	 * <p>
	 * If {@code descriptorIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param descriptorIndex the value for the {@code descriptor_index} item associated with this {@code LocalVariable} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code descriptorIndex} is less than {@code 1}
	 */
	public void setDescriptorIndex(final int descriptorIndex) {
		this.descriptorIndex = ParameterArguments.requireRange(descriptorIndex, 1, Integer.MAX_VALUE, "descriptorIndex");
	}
	
	/**
	 * Sets {@code index} as the value for the {@code index} item associated with this {@code LocalVariable} instance.
	 * <p>
	 * If {@code index} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index the value for the {@code index} item associated with this {@code LocalVariable} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0}
	 */
	public void setIndex(final int index) {
		this.index = ParameterArguments.requireRange(index, 0, Integer.MAX_VALUE, "index");
	}
	
	/**
	 * Sets {@code length} as the value for the {@code length} item associated with this {@code LocalVariable} instance.
	 * <p>
	 * If {@code length} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param length the value for the {@code length} item associated with this {@code LocalVariable} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code length} is less than {@code 0}
	 */
	public void setLength(final int length) {
		this.length = ParameterArguments.requireRange(length, 0, Integer.MAX_VALUE, "length");
	}
	
	/**
	 * Sets {@code nameIndex} as the value for the {@code name_index} item associated with this {@code LocalVariable} instance.
	 * <p>
	 * If {@code nameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameIndex the value for the {@code name_index} item associated with this {@code LocalVariable} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameIndex} is less than {@code 1}
	 */
	public void setNameIndex(final int nameIndex) {
		this.nameIndex = ParameterArguments.requireRange(nameIndex, 1, Integer.MAX_VALUE, "nameIndex");
	}
	
	/**
	 * Sets {@code startPC} as the value for the {@code start_pc} item associated with this {@code LocalVariable} instance.
	 * <p>
	 * If {@code startPC} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param startPC the value for the {@code start_pc} item associated with this {@code LocalVariable} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code startPC} is less than {@code 0}
	 */
	public void setStartPC(final int startPC) {
		this.startPC = ParameterArguments.requireRange(startPC, 0, Integer.MAX_VALUE, "startPC");
	}
	
	/**
	 * Writes this {@code LocalVariable} to {@code dataOutput}.
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
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getStartPC());
			dataOutput.writeShort(getLength());
			dataOutput.writeShort(getNameIndex());
			dataOutput.writeShort(getDescriptorIndex());
			dataOutput.writeShort(getIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}