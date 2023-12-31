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
package org.macroing.cel4j.java.binary.reader;

import java.io.DataInput;
import java.io.IOException;

import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantFieldRefInfo;
import org.macroing.cel4j.util.ParameterArguments;

final class ConstantFieldRefInfoReader implements CPInfoReader {
	public ConstantFieldRefInfoReader() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public CPInfo read(final DataInput dataInput, final int tag) {
		try {
			return new ConstantFieldRefInfo(dataInput.readUnsignedShort(), dataInput.readUnsignedShort());
		} catch(final IOException | IllegalArgumentException e) {
			throw new CPInfoReaderException("Unable to read CONSTANT_Fieldref_info", e);
		}
	}
	
	@Override
	public boolean isSupported(final int tag) {
		return ParameterArguments.requireRange(tag, 0, Integer.MAX_VALUE) == ConstantFieldRefInfo.TAG;
	}
}