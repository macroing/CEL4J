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
import org.macroing.cel4j.java.binary.classfile.attributeinfo.Annotation;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.AnnotationValueUnion;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.ArrayValueUnion;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.ClassInfoIndexUnion;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.ConstValueIndexUnion;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.ElementValue;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.ElementValuePair;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.EnumConstValueUnion;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.RuntimeInvisibleAnnotationsAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.Union;

final class RuntimeInvisibleAnnotationsAttributeReader implements AttributeInfoReader {
	public RuntimeInvisibleAnnotationsAttributeReader() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public AttributeInfo read(final DataInput dataInput, final int attributeNameIndex, final List<CPInfo> constantPool) {
		try {
			final RuntimeInvisibleAnnotationsAttribute runtimeInvisibleAnnotationsAttribute = new RuntimeInvisibleAnnotationsAttribute(attributeNameIndex);
			
			final int numAnnotations = dataInput.readUnsignedShort();
			
			for(int i = 0; i < numAnnotations; i++) {
				runtimeInvisibleAnnotationsAttribute.addAnnotation(doReadAnnotation(dataInput));
			}
			
			return runtimeInvisibleAnnotationsAttribute;
		} catch(final IOException | IllegalArgumentException e) {
			throw new AttributeInfoReaderException("Unable to read RuntimeInvisibleAnnotations_attribute", e);
		}
	}
	
	@Override
	public boolean isSupported(final String name) {
		return name.equals(RuntimeInvisibleAnnotationsAttribute.NAME);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Annotation doReadAnnotation(final DataInput dataInput) throws IOException {
		final int typeIndex = dataInput.readUnsignedShort();
		final int numElementValuePairs = dataInput.readUnsignedShort();
		
		final Annotation annotation = new Annotation(typeIndex);
		
		for(int i = 0; i < numElementValuePairs; i++) {
			annotation.addElementValuePair(doReadElementValuePair(dataInput));
		}
		
		return annotation;
	}
	
	private static ElementValue doReadElementValue(final DataInput dataInput) throws IOException {
		final int tag = dataInput.readUnsignedByte();
		
		final Union value = doReadUnion(dataInput, tag);
		
		return new ElementValue(tag, value);
	}
	
	private static ElementValuePair doReadElementValuePair(final DataInput dataInput) throws IOException {
		return new ElementValuePair(dataInput.readUnsignedShort(), doReadElementValue(dataInput));
	}
	
	private static Union doReadUnion(final DataInput dataInput, final int tag) throws IOException {
		switch(tag) {
			case ElementValue.ANNOTATION_TYPE_TAG:
				return new AnnotationValueUnion(doReadAnnotation(dataInput));
			case ElementValue.ARRAY_TAG:
				final int numValues = dataInput.readUnsignedShort();
				
				final ArrayValueUnion arrayValueUnion = new ArrayValueUnion();
				
				for(int i = 0; i < numValues; i++) {
					arrayValueUnion.addValue(doReadElementValue(dataInput));
				}
				
				return arrayValueUnion;
			case ElementValue.BOOLEAN_TAG:
				return new ConstValueIndexUnion(dataInput.readUnsignedShort());
			case ElementValue.BYTE_TAG:
				return new ConstValueIndexUnion(dataInput.readUnsignedShort());
			case ElementValue.CHAR_TAG:
				return new ConstValueIndexUnion(dataInput.readUnsignedShort());
			case ElementValue.CLASS_TAG:
				return new ClassInfoIndexUnion(dataInput.readUnsignedShort());
			case ElementValue.DOUBLE_TAG:
				return new ConstValueIndexUnion(dataInput.readUnsignedShort());
			case ElementValue.ENUM_CONSTANT_TAG:
				return new EnumConstValueUnion(dataInput.readUnsignedShort(), dataInput.readUnsignedShort());
			case ElementValue.FLOAT_TAG:
				return new ConstValueIndexUnion(dataInput.readUnsignedShort());
			case ElementValue.INT_TAG:
				return new ConstValueIndexUnion(dataInput.readUnsignedShort());
			case ElementValue.LONG_TAG:
				return new ConstValueIndexUnion(dataInput.readUnsignedShort());
			case ElementValue.SHORT_TAG:
				return new ConstValueIndexUnion(dataInput.readUnsignedShort());
			case ElementValue.STRING_TAG:
				return new ConstValueIndexUnion(dataInput.readUnsignedShort());
			default:
				throw new IOException(String.format("Malformed tag: %s", Integer.valueOf(tag)));
		}
	}
}