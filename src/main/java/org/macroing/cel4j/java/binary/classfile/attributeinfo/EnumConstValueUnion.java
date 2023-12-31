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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code EnumConstValueUnion} represents an unnamed {@code enum_const_value} {@code union} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is immutable and thread-safe.
 * <p>
 * The unnamed {@code enum_const_value} {@code union} structure has the following format:
 * <pre>
 * <code>
 * {
 *     u2 type_name_index;
 *     u2 const_name_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class EnumConstValueUnion implements Union {
	private final int constNameIndex;
	private final int typeNameIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code EnumConstValueUnion} instance.
	 * <p>
	 * If either {@code typeNameIndex} or {@code constNameIndex} are less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param typeNameIndex the value for the {@code type_name_index} item associated with this {@code EnumConstValueUnion} instance
	 * @param constNameIndex the value for the {@code const_name_index} item associated with this {@code EnumConstValueUnion} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code typeNameIndex} or {@code constNameIndex} are less than {@code 1}
	 */
	public EnumConstValueUnion(final int typeNameIndex, final int constNameIndex) {
		this.typeNameIndex = ParameterArguments.requireRange(typeNameIndex, 1, Integer.MAX_VALUE, "typeNameIndex");
		this.constNameIndex = ParameterArguments.requireRange(constNameIndex, 1, Integer.MAX_VALUE, "constNameIndex");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code EnumConstValueUnion} instance.
	 * 
	 * @return a copy of this {@code EnumConstValueUnion} instance
	 */
	@Override
	public EnumConstValueUnion copy() {
		return new EnumConstValueUnion(getTypeNameIndex(), getConstNameIndex());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code EnumConstValueUnion} instance.
	 * 
	 * @return a {@code String} representation of this {@code EnumConstValueUnion} instance
	 */
	@Override
	public String toString() {
		return String.format("new EnumConstValueUnion(%s, %s)", Integer.toString(getTypeNameIndex()), Integer.toString(getConstNameIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code EnumConstValueUnion} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code EnumConstValueUnion}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code EnumConstValueUnion} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code EnumConstValueUnion}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof EnumConstValueUnion)) {
			return false;
		} else if(getTypeNameIndex() != EnumConstValueUnion.class.cast(object).getTypeNameIndex()) {
			return false;
		} else if(getConstNameIndex() != EnumConstValueUnion.class.cast(object).getConstNameIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code const_name_index} item associated with this {@code EnumConstValueUnion} instance.
	 * 
	 * @return the value of the {@code const_name_index} item associated with this {@code EnumConstValueUnion} instance
	 */
	public int getConstNameIndex() {
		return this.constNameIndex;
	}
	
	/**
	 * Returns the length of this {@code EnumConstValueUnion} instance.
	 * 
	 * @return the length of this {@code EnumConstValueUnion} instance
	 */
	@Override
	public int getLength() {
		return 4;
	}
	
	/**
	 * Returns the value of the {@code type_name_index} item associated with this {@code EnumConstValueUnion} instance.
	 * 
	 * @return the value of the {@code type_name_index} item associated with this {@code EnumConstValueUnion} instance
	 */
	public int getTypeNameIndex() {
		return this.typeNameIndex;
	}
	
	/**
	 * Returns a hash code for this {@code EnumConstValueUnion} instance.
	 * 
	 * @return a hash code for this {@code EnumConstValueUnion} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getTypeNameIndex()), Integer.valueOf(getConstNameIndex()));
	}
	
	/**
	 * Writes this {@code EnumConstValueUnion} to {@code dataOutput}.
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
			dataOutput.writeShort(getTypeNameIndex());
			dataOutput.writeShort(getConstNameIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}