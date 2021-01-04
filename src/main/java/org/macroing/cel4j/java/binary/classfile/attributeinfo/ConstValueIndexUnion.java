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

import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code ConstValueIndexUnion} represents an unnamed {@code const_value_index} {@code union} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is immutable and thread-safe.
 * <p>
 * The unnamed {@code const_value_index} {@code union} structure has the following format:
 * <pre>
 * <code>
 * {
 *     u2 const_value_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstValueIndexUnion implements Union {
	private final int constValueIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstValueIndexUnion} instance.
	 * <p>
	 * If {@code constValueIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param constValueIndex the value for the {@code const_value_index} item associated with this {@code ConstValueIndexUnion} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code constValueIndex} is less than {@code 1}
	 */
	public ConstValueIndexUnion(final int constValueIndex) {
		this.constValueIndex = ParameterArguments.requireRange(constValueIndex, 1, Integer.MAX_VALUE, "constValueIndex");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstValueIndexUnion} instance.
	 * 
	 * @return a copy of this {@code ConstValueIndexUnion} instance
	 */
	@Override
	public ConstValueIndexUnion copy() {
		return new ConstValueIndexUnion(getConstValueIndex());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstValueIndexUnion} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstValueIndexUnion} instance
	 */
	@Override
	public String toString() {
		return String.format("new ConstValueIndexUnion(%s)", Integer.toString(getConstValueIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code ConstValueIndexUnion} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstValueIndexUnion}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConstValueIndexUnion} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstValueIndexUnion}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstValueIndexUnion)) {
			return false;
		} else if(getConstValueIndex() != ConstValueIndexUnion.class.cast(object).getConstValueIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code const_value_index} item associated with this {@code ConstValueIndexUnion} instance.
	 * 
	 * @return the value of the {@code const_value_index} item associated with this {@code ConstValueIndexUnion} instance
	 */
	public int getConstValueIndex() {
		return this.constValueIndex;
	}
	
	/**
	 * Returns the length of this {@code ConstValueIndexUnion} instance.
	 * 
	 * @return the length of this {@code ConstValueIndexUnion} instance
	 */
	@Override
	public int getLength() {
		return 2;
	}
	
	/**
	 * Returns a hash code for this {@code ConstValueIndexUnion} instance.
	 * 
	 * @return a hash code for this {@code ConstValueIndexUnion} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getConstValueIndex()));
	}
	
	/**
	 * Writes this {@code ConstValueIndexUnion} to {@code dataOutput}.
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
			dataOutput.writeShort(getConstValueIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}