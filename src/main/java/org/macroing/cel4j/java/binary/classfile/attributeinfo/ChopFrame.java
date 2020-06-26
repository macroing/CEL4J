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
 * A {@code ChopFrame} represents a {@code chop_frame} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is immutable and thread-safe.
 * <p>
 * The {@code chop_frame} structure has the following format:
 * <pre>
 * <code>
 * chop_frame {
 *     u1 frame_type;
 *     u2 offset_delta;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ChopFrame implements StackMapFrame {
	private final int frameType;
	private final int offsetDelta;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ChopFrame} instance.
	 * <p>
	 * If either {@code frameType} is less than {@code 248} or greater than {@code 250}, or {@code offsetDelta} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param frameType the value of the {@code frame_type} item associated with this {@code ChopFrame} instance
	 * @param offsetDelta the value of the {@code offset_delta} item associated with this {@code ChopFrame} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code frameType} is less than {@code 248} or greater than {@code 250}, or {@code offsetDelta} is less than {@code 0}
	 */
	public ChopFrame(final int frameType, final int offsetDelta) {
		this.frameType = ParameterArguments.requireRange(frameType, 248, 250, "frameType");
		this.offsetDelta = ParameterArguments.requireRange(offsetDelta, 0, Integer.MAX_VALUE, "offsetDelta");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ChopFrame} instance.
	 * <p>
	 * Because this class is immutable, the same instance will be returned.
	 * 
	 * @return a copy of this {@code ChopFrame} instance
	 */
	@Override
	public ChopFrame copy() {
		return this;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ChopFrame} instance.
	 * 
	 * @return a {@code String} representation of this {@code ChopFrame} instance
	 */
	@Override
	public String toString() {
		return String.format("new ChopFrame(%s, %s)", Integer.toString(getFrameType()), Integer.toString(getOffsetDelta()));
	}
	
	/**
	 * Compares {@code object} to this {@code ChopFrame} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ChopFrame}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ChopFrame} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ChopFrame}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ChopFrame)) {
			return false;
		} else if(getFrameType() != ChopFrame.class.cast(object).getFrameType()) {
			return false;
		} else if(getOffsetDelta() != ChopFrame.class.cast(object).getOffsetDelta()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code frame_type} item associated with this {@code ChopFrame} instance.
	 * 
	 * @return the value of the {@code frame_type} item associated with this {@code ChopFrame} instance
	 */
	@Override
	public int getFrameType() {
		return this.frameType;
	}
	
	/**
	 * Returns the length of this {@code ChopFrame} instance.
	 * 
	 * @return the length of this {@code ChopFrame} instance
	 */
	@Override
	public int getLength() {
		return 3;
	}
	
	/**
	 * Returns the value of the {@code offset_delta} item associated with this {@code ChopFrame} instance.
	 * 
	 * @return the value of the {@code offset_delta} item associated with this {@code ChopFrame} instance
	 */
	public int getOffsetDelta() {
		return this.offsetDelta;
	}
	
	/**
	 * Returns a hash code for this {@code ChopFrame} instance.
	 * 
	 * @return a hash code for this {@code ChopFrame} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getFrameType()), Integer.valueOf(getOffsetDelta()));
	}
	
	/**
	 * Writes this {@code ChopFrame} to {@code dataOutput}.
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
			dataOutput.writeShort(getOffsetDelta());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}