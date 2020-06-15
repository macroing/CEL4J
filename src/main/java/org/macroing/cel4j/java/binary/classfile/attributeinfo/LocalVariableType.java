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
 * A {@code LocalVariableType} denotes a local_variable_type structure somewhere in a LocalVariableTypeTable_attribute structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class LocalVariableType implements Node {
	private int index;
	private int length;
	private int nameIndex;
	private int signatureIndex;
	private int startPC;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code LocalVariableType} instance.
	 * <p>
	 * If either {@code nameIndex} or {@code signatureIndex} are less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param startPC the start_pc
	 * @param length the length
	 * @param nameIndex the name_index
	 * @param signatureIndex the signature_index
	 * @param index the index
	 * @throws IllegalArgumentException thrown if, and only if, either {@code nameIndex} or {@code signatureIndex} are less than or equal to {@code 0}
	 */
	public LocalVariableType(final int startPC, final int length, final int nameIndex, final int signatureIndex, final int index) {
		this.startPC = startPC;
		this.length = length;
		this.nameIndex = ParameterArguments.requireRange(nameIndex, 1, Integer.MAX_VALUE);
		this.signatureIndex = ParameterArguments.requireRange(signatureIndex, 1, Integer.MAX_VALUE);
		this.index = index;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code LocalVariableType} instance.
	 * 
	 * @return a copy of this {@code LocalVariableType} instance
	 */
	public LocalVariableType copy() {
		return new LocalVariableType(this.startPC, this.length, this.nameIndex, this.signatureIndex, this.index);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code LocalVariableType} instance.
	 * 
	 * @return a {@code String} representation of this {@code LocalVariableType} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("local_variable_type");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("start_pc=" + getStartPC());
		stringBuilder.append(" ");
		stringBuilder.append("length=" + getLength());
		stringBuilder.append(" ");
		stringBuilder.append("name_index=" + getNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("signature_index=" + getSignatureIndex());
		stringBuilder.append(" ");
		stringBuilder.append("index=" + getIndex());
		
		final String toString = stringBuilder.toString();
		
		return toString;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code LocalVariableType}, and that {@code LocalVariableType} instance is equal to this {@code LocalVariableType} instance, {@code false}
	 * otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code LocalVariableType} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code LocalVariableType}, and that {@code LocalVariableType} instance is equal to this {@code LocalVariableType} instance, {@code false}
	 * otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof LocalVariableType)) {
			return false;
		} else if(LocalVariableType.class.cast(object).getStartPC() != getStartPC()) {
			return false;
		} else if(LocalVariableType.class.cast(object).getLength() != getLength()) {
			return false;
		} else if(LocalVariableType.class.cast(object).getNameIndex() != getNameIndex()) {
			return false;
		} else if(LocalVariableType.class.cast(object).getSignatureIndex() != getSignatureIndex()) {
			return false;
		} else if(LocalVariableType.class.cast(object).getIndex() != getIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the index of this {@code LocalVariableType} instance.
	 * 
	 * @return the index of this {@code LocalVariableType} instance.
	 */
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Returns the length of this {@code LocalVariableType} instance.
	 * 
	 * @return the length of this {@code LocalVariableType} instance.
	 */
	public int getLength() {
		return this.length;
	}
	
	/**
	 * Returns the name_index of this {@code LocalVariableType} instance.
	 * 
	 * @return the name_index of this {@code LocalVariableType} instance.
	 */
	public int getNameIndex() {
		return this.nameIndex;
	}
	
	/**
	 * Returns the signature_index of this {@code LocalVariableType} instance.
	 * 
	 * @return the signature_index of this {@code LocalVariableType} instance.
	 */
	public int getSignatureIndex() {
		return this.signatureIndex;
	}
	
	/**
	 * Returns the start_pc of this {@code LocalVariableType} instance.
	 * 
	 * @return the start_pc of this {@code LocalVariableType} instance.
	 */
	public int getStartPC() {
		return this.startPC;
	}
	
	/**
	 * Returns a hash code for this {@code LocalVariableType} instance.
	 * 
	 * @return a hash code for this {@code LocalVariableType} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getStartPC()), Integer.valueOf(getLength()), Integer.valueOf(getNameIndex()), Integer.valueOf(getSignatureIndex()), Integer.valueOf(getIndex()));
	}
	
	/**
	 * Sets the index for this {@code LocalVariableType} instance.
	 * 
	 * @param index the new index
	 */
	public void setIndex(final int index) {
		this.index = index;
	}
	
	/**
	 * Sets the length for this {@code LocalVariableType} instance.
	 * 
	 * @param length the new length
	 */
	public void setLength(final int length) {
		this.length = length;
	}
	
	/**
	 * Sets the name_index for this {@code LocalVariableType} instance.
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
	 * Sets the signature_index for this {@code LocalVariableType} instance.
	 * <p>
	 * If {@code signatureIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param signatureIndex the new signature_index
	 * @throws IllegalArgumentException thrown if, and only if, {@code signatureIndex} is less than or equal to {@code 0}
	 */
	public void setSignatureIndex(final int signatureIndex) {
		this.signatureIndex = ParameterArguments.requireRange(signatureIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets the start_pc for this {@code LocalVariableType} instance.
	 * 
	 * @param startPC the new start_pc
	 */
	public void setStartPC(final int startPC) {
		this.startPC = startPC;
	}
	
	/**
	 * Writes this {@code LocalVariableType} to {@code dataOutput}.
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
			dataOutput.writeShort(getSignatureIndex());
			dataOutput.writeShort(getIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}