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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code LocalVariable} denotes a local_variable structure somewhere in a LocalVariableTable_attribute structure.
 * <p>
 * This class is not thread-safe.
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
	
	private LocalVariable(final int startPC, final int length, final int nameIndex, final int descriptorIndex, final int index) {
		this.startPC = startPC;
		this.length = length;
		this.nameIndex = nameIndex;
		this.descriptorIndex = descriptorIndex;
		this.index = index;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code LocalVariable} instance.
	 * 
	 * @return a copy of this {@code LocalVariable} instance
	 */
	public LocalVariable copy() {
		return new LocalVariable(this.startPC, this.length, this.nameIndex, this.descriptorIndex, this.index);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code LocalVariable} instance.
	 * 
	 * @return a {@code String} representation of this {@code LocalVariable} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("local_variable");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("start_pc=" + getStartPC());
		stringBuilder.append(" ");
		stringBuilder.append("length=" + getLength());
		stringBuilder.append(" ");
		stringBuilder.append("name_index=" + getNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("descriptor_index=" + getDescriptorIndex());
		stringBuilder.append(" ");
		stringBuilder.append("index=" + getIndex());
		
		final String toString = stringBuilder.toString();
		
		return toString;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code LocalVariable}, and that {@code LocalVariable} instance is equal to this {@code LocalVariable} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code LocalVariable} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code LocalVariable}, and that {@code LocalVariable} instance is equal to this {@code LocalVariable} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof LocalVariable)) {
			return false;
		} else if(LocalVariable.class.cast(object).getStartPC() != getStartPC()) {
			return false;
		} else if(LocalVariable.class.cast(object).getLength() != getLength()) {
			return false;
		} else if(LocalVariable.class.cast(object).getNameIndex() != getNameIndex()) {
			return false;
		} else if(LocalVariable.class.cast(object).getDescriptorIndex() != getDescriptorIndex()) {
			return false;
		} else if(LocalVariable.class.cast(object).getIndex() != getIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the descriptor_index of this {@code LocalVariable} instance.
	 * 
	 * @return the descriptor_index of this {@code LocalVariable} instance.
	 */
	public int getDescriptorIndex() {
		return this.descriptorIndex;
	}
	
	/**
	 * Returns the index of this {@code LocalVariable} instance.
	 * 
	 * @return the index of this {@code LocalVariable} instance.
	 */
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Returns the length of this {@code LocalVariable} instance.
	 * 
	 * @return the length of this {@code LocalVariable} instance.
	 */
	public int getLength() {
		return this.length;
	}
	
	/**
	 * Returns the name_index of this {@code LocalVariable} instance.
	 * 
	 * @return the name_index of this {@code LocalVariable} instance.
	 */
	public int getNameIndex() {
		return this.nameIndex;
	}
	
	/**
	 * Returns the start_pc of this {@code LocalVariable} instance.
	 * 
	 * @return the start_pc of this {@code LocalVariable} instance.
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
	 * Sets the descriptor_index for this {@code LocalVariable} instance.
	 * <p>
	 * If {@code descriptorIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param descriptorIndex the new descriptor_index
	 * @throws IllegalArgumentException thrown if, and only if, {@code descriptorIndex} is less than or equal to {@code 0}
	 */
	public void setDescriptorIndex(final int descriptorIndex) {
		this.descriptorIndex = ParameterArguments.requireRange(descriptorIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets the index for this {@code LocalVariable} instance.
	 * 
	 * @param index the new index
	 */
	public void setIndex(final int index) {
		this.index = index;
	}
	
	/**
	 * Sets the length for this {@code LocalVariable} instance.
	 * 
	 * @param length the new length
	 */
	public void setLength(final int length) {
		this.length = length;
	}
	
	/**
	 * Sets the name_index for this {@code LocalVariable} instance.
	 * <p>
	 * If {@code nameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameIndex the new name_index
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameIndex} is less than or equal to {@code 0}
	 */
	public void setNameIndex(final int nameIndex) {
		this.nameIndex = ParameterArguments.requireRange(nameIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets the start_pc for this {@code LocalVariable} instance.
	 * 
	 * @param startPC the new start_pc
	 */
	public void setStartPC(final int startPC) {
		this.startPC = startPC;
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
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code LocalVariable} instance.
	 * <p>
	 * If either {@code nameIndex} or {@code descriptorIndex} are less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param startPC the start_pc
	 * @param length the length
	 * @param nameIndex the name_index
	 * @param descriptorIndex the descriptor_index
	 * @param index the index
	 * @return a new {@code LocalVariable} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code nameIndex} or {@code descriptorIndex} are less than or equal to {@code 0}
	 */
	public static LocalVariable newInstance(final int startPC, final int length, final int nameIndex, final int descriptorIndex, final int index) {
		return new LocalVariable(startPC, length, ParameterArguments.requireRange(nameIndex, 1, Integer.MAX_VALUE), ParameterArguments.requireRange(descriptorIndex, 1, Integer.MAX_VALUE), index);
	}
}