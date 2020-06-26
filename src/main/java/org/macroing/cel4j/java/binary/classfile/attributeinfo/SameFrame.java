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

import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code SameFrame} represents a {@code same_frame} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is immutable and thread-safe.
 * <p>
 * The {@code same_frame} structure has the following format:
 * <pre>
 * <code>
 * same_frame {
 *     u1 frame_type;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class SameFrame implements StackMapFrame {
	private final int frameType;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code SameFrame} instance.
	 * <p>
	 * If {@code frameType} is less than {@code 0} or greater than {@code 63}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param frameType the value of the {@code frame_type} item associated with this {@code SameFrame} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code frameType} is less than {@code 0} or greater than {@code 63}
	 */
	public SameFrame(final int frameType) {
		this.frameType = ParameterArguments.requireRange(frameType, 0, 63, "frameType");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code SameFrame} instance.
	 * <p>
	 * Because this class is immutable, the same instance will be returned.
	 * 
	 * @return a copy of this {@code SameFrame} instance
	 */
	@Override
	public SameFrame copy() {
		return this;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code SameFrame} instance.
	 * 
	 * @return a {@code String} representation of this {@code SameFrame} instance
	 */
	@Override
	public String toString() {
		return String.format("new SameFrame(%s)", Integer.toString(getFrameType()));
	}
	
	/**
	 * Compares {@code object} to this {@code SameFrame} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code SameFrame}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code SameFrame} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code SameFrame}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof SameFrame)) {
			return false;
		} else if(getFrameType() != SameFrame.class.cast(object).getFrameType()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code frame_type} item associated with this {@code SameFrame} instance.
	 * 
	 * @return the value of the {@code frame_type} item associated with this {@code SameFrame} instance
	 */
	@Override
	public int getFrameType() {
		return this.frameType;
	}
	
	/**
	 * Returns the length of this {@code SameFrame} instance.
	 * 
	 * @return the length of this {@code SameFrame} instance
	 */
	@Override
	public int getLength() {
		return 1;
	}
	
	/**
	 * Returns a hash code for this {@code SameFrame} instance.
	 * 
	 * @return a hash code for this {@code SameFrame} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getFrameType()));
	}
	
	/**
	 * Writes this {@code SameFrame} to {@code dataOutput}.
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
			dataOutput.writeByte(getFrameType());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}