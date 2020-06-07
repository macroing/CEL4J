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
import org.macroing.cel4j.java.binary.classfile.attributeinfo.InnerClass;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.InnerClassesAttribute;
import org.macroing.cel4j.node.NodeFormatException;

final class InnerClassesAttributeReader implements AttributeInfoReader {
	public InnerClassesAttributeReader() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public AttributeInfo readAttributeInfo(final DataInput dataInput, final int attributeNameIndex, final List<CPInfo> constantPool) {
		try {
			final InnerClassesAttribute innerClassesAttribute = InnerClassesAttribute.newInstance(attributeNameIndex);
			
			final int numberOfClasses = dataInput.readUnsignedShort();
			
			for(int i = 0; i < numberOfClasses; i++) {
				final
				InnerClass innerClass = InnerClass.newInstance();
				innerClass.setInnerClassInfoIndex(dataInput.readUnsignedShort());
				innerClass.setOuterClassInfoIndex(dataInput.readUnsignedShort());
				innerClass.setInnerNameIndex(dataInput.readUnsignedShort());
				innerClass.setInnerClassAccessFlags(dataInput.readUnsignedShort());
				
				innerClassesAttribute.addInnerClass(innerClass);
			}
			
			return innerClassesAttribute;
		} catch(final IOException e) {
			throw new NodeFormatException("Unable to read ConstantValue_attribute", e);
		}
	}
	
	@Override
	public boolean isAttributeInfoReadingSupportedFor(final String name) {
		return name.equals(InnerClassesAttribute.NAME);
	}
}