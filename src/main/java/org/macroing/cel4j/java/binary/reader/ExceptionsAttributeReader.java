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
package org.macroing.cel4j.java.binary.reader;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.ExceptionsAttribute;

final class ExceptionsAttributeReader implements AttributeInfoReader {
	public ExceptionsAttributeReader() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public AttributeInfo read(final DataInput dataInput, final int attributeNameIndex, final List<CPInfo> constantPool) {
		try {
			final ExceptionsAttribute exceptionsAttribute = ExceptionsAttribute.newInstance(attributeNameIndex);
			
			final int numberOfExceptions = doReadU2(dataInput);
			
			for(int i = 0; i < numberOfExceptions; i++) {
				final int exceptionIndex = doReadU2(dataInput);
				
				exceptionsAttribute.addExceptionIndex(exceptionIndex);
			}
			
			return exceptionsAttribute;
		} catch(final IllegalArgumentException e) {
			throw new AttributeInfoReaderException(e);
		}
	}
	
	@Override
	public boolean isSupported(final String name) {
		return name.equals(ExceptionsAttribute.NAME);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static int doReadU2(final DataInput dataInput) {
		try {
			return dataInput.readUnsignedShort();
		} catch(final IOException e) {
			throw new AttributeInfoReaderException(e);
		}
	}
}