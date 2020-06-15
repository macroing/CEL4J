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
import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;

import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class SameFrameExtended implements StackMapFrame {
	private final int frameType;
	private final int offsetDelta;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public SameFrameExtended(final int frameType, final int offsetDelta) {
		this.frameType = ParameterArguments.requireRange(frameType, 251, 251, "frameType");
		this.offsetDelta = ParameterArguments.requireRange(offsetDelta, 0, Integer.MAX_VALUE, "offsetDelta");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public SameFrameExtended copy() {
		return this;
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof SameFrameExtended)) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.frameType), Integer.valueOf(SameFrameExtended.class.cast(object).frameType))) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.offsetDelta), Integer.valueOf(SameFrameExtended.class.cast(object).offsetDelta))) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getFrameType() {
		return this.frameType;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getLength() {
		return 3;
	}
	
//	TODO: Add Javadocs!
	public int getOffsetDelta() {
		return this.offsetDelta;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.frameType), Integer.valueOf(this.offsetDelta));
	}
	
//	TODO: Add Javadocs!
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeByte(this.frameType);
			dataOutput.writeShort(this.offsetDelta);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}