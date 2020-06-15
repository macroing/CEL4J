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
public final class EnumConstValueUnion implements Union {
	private final int constNameIndex;
	private final int typeNameIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public EnumConstValueUnion(final int typeNameIndex, final int constNameIndex) {
		this.typeNameIndex = ParameterArguments.requireRange(typeNameIndex, 1, Integer.MAX_VALUE, "typeNameIndex");
		this.constNameIndex = ParameterArguments.requireRange(constNameIndex, 1, Integer.MAX_VALUE, "constNameIndex");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public EnumConstValueUnion copy() {
		return new EnumConstValueUnion(this.typeNameIndex, this.constNameIndex);
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof EnumConstValueUnion)) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.typeNameIndex), Integer.valueOf(EnumConstValueUnion.class.cast(object).typeNameIndex))) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.constNameIndex), Integer.valueOf(EnumConstValueUnion.class.cast(object).constNameIndex))) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	public int getConstNameIndex() {
		return this.constNameIndex;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getLength() {
		return 4;
	}
	
//	TODO: Add Javadocs!
	public int getTypeNameIndex() {
		return this.typeNameIndex;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.typeNameIndex), Integer.valueOf(this.constNameIndex));
	}
	
//	TODO: Add Javadocs!
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(this.typeNameIndex);
			dataOutput.writeShort(this.constNameIndex);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}