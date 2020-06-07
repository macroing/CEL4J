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
public final class ConstValueIndexUnion implements Union {
	private final int constValueIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ConstValueIndexUnion(final int constValueIndex) {
		this.constValueIndex = constValueIndex;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public ConstValueIndexUnion copy() {
		return new ConstValueIndexUnion(this.constValueIndex);
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstValueIndexUnion)) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.constValueIndex), Integer.valueOf(ConstValueIndexUnion.class.cast(object).constValueIndex))) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	public int getConstValueIndex() {
		return this.constValueIndex;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getLength() {
		return 2;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.constValueIndex));
	}
	
//	TODO: Add Javadocs!
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(this.constValueIndex);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static ConstValueIndexUnion newInstance(final int constValueIndex) {
		return new ConstValueIndexUnion(ParameterArguments.requireRange(constValueIndex, 1, Integer.MAX_VALUE, "constValueIndex"));
	}
}