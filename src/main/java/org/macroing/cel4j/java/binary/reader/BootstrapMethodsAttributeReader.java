/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
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
import org.macroing.cel4j.java.binary.classfile.attributeinfo.BootstrapMethod;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.BootstrapMethodsAttribute;

final class BootstrapMethodsAttributeReader implements AttributeInfoReader {
	public BootstrapMethodsAttributeReader() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public AttributeInfo read(final DataInput dataInput, final int attributeNameIndex, final List<CPInfo> constantPool) {
		try {
			final BootstrapMethodsAttribute bootstrapMethodsAttribute = new BootstrapMethodsAttribute(attributeNameIndex);
			
			final int numBootstrapMethods = dataInput.readUnsignedShort();
			
			for(int i = 0; i < numBootstrapMethods; i++) {
				final
				BootstrapMethod bootstrapMethod = new BootstrapMethod();
				bootstrapMethod.setBootstrapMethodRef(dataInput.readUnsignedShort());
				
				final int numBootstrapArguments = dataInput.readUnsignedShort();
				
				for(int j = 0; j < numBootstrapArguments; j++) {
					bootstrapMethod.addBootstrapArgument(dataInput.readUnsignedShort());
				}
				
				bootstrapMethodsAttribute.addBootstrapMethod(bootstrapMethod);
			}
			
			return bootstrapMethodsAttribute;
		} catch(final IOException | IllegalArgumentException e) {
			throw new AttributeInfoReaderException("Unable to read BootstrapMethods_attribute", e);
		}
	}
	
	@Override
	public boolean isSupported(final String name) {
		return name.equals(BootstrapMethodsAttribute.NAME);
	}
}