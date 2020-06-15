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
public final class ObjectVariableInfo implements VerificationTypeInfo {
	private final int constantPoolIndex;
	private final int tag;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public ObjectVariableInfo(final int constantPoolIndex) {
		this.constantPoolIndex = ParameterArguments.requireRange(constantPoolIndex, 1, Integer.MAX_VALUE);
		this.tag = ITEM_OBJECT;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public ObjectVariableInfo copy() {
		return this;
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ObjectVariableInfo)) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.constantPoolIndex), Integer.valueOf(ObjectVariableInfo.class.cast(object).constantPoolIndex))) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.tag), Integer.valueOf(ObjectVariableInfo.class.cast(object).tag))) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!.
	public int getConstantPoolIndex() {
		return this.constantPoolIndex;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getLength() {
		return 3;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getTag() {
		return this.tag;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.constantPoolIndex), Integer.valueOf(this.tag));
	}
	
//	TODO: Add Javadocs!
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeByte(this.tag);
			dataOutput.writeShort(this.constantPoolIndex);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}