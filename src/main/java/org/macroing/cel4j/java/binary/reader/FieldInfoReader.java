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
import java.util.Objects;
import java.util.ServiceLoader;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.FieldInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.UnimplementedAttribute;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;

final class FieldInfoReader {
	private final ServiceLoader<AttributeInfoReader> attributeInfoReaders;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public FieldInfoReader() {
		this.attributeInfoReaders = ServiceLoader.load(AttributeInfoReader.class);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public FieldInfo read(final DataInput dataInput, final ClassFile classFile) {
		doReloadAttributeInfoReaders();
		
		return doReadFieldInfo(Objects.requireNonNull(dataInput, "dataInput == null"), Objects.requireNonNull(classFile, "classFile == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private AttributeInfo doReadAttributeInfo(final DataInput dataInput, final ClassFile classFile) {
		final int attributeNameIndex = doReadU2(dataInput);
		final int attributeLength = doReadU4(dataInput);
		
		final ConstantUTF8Info constantUTF8Info = ConstantUTF8Info.class.cast(classFile.getCPInfos().get(attributeNameIndex));
		
		final String name = constantUTF8Info.getStringValue();
		
		for(final AttributeInfoReader attributeInfoReader : this.attributeInfoReaders) {
			if(attributeInfoReader.isSupported(name)) {
				return attributeInfoReader.read(dataInput, attributeNameIndex, classFile.getCPInfos());
			}
		}
		
		final AttributeInfoReader attributeInfoReader = new AttributeInfoReaderImpl();
		
		if(attributeInfoReader.isSupported(name)) {
			return attributeInfoReader.read(dataInput, attributeNameIndex, classFile.getCPInfos());
		}
		
		try {
			final byte[] info = new byte[attributeLength];
			
			dataInput.readFully(info);
			
			return new UnimplementedAttribute(name, attributeNameIndex, info);
		} catch(final IOException e) {
			throw new ClassFileReaderException("Unable to read attribute_info: name = " + name);
		}
	}
	
	private FieldInfo doReadFieldInfo(final DataInput dataInput, final ClassFile classFile) {
		try {
			final FieldInfo fieldInfo = new FieldInfo();
			
			doReadAccessFlags(dataInput, fieldInfo);
			doReadNameIndex(dataInput, fieldInfo);
			doReadDescriptorIndex(dataInput, fieldInfo);
			doReadAttributes(dataInput, fieldInfo, classFile);
			
			return fieldInfo;
		} catch(final IllegalArgumentException e) {
			throw new ClassFileReaderException("Unable to read field_info", e);
		}
	}
	
	private void doReadAttributes(final DataInput dataInput, final FieldInfo fieldInfo, final ClassFile classFile) {
		final int attributesCount = doReadU2(dataInput);
		
		for(int i = 0; i < attributesCount; i++) {
			final AttributeInfo attributeInfo = doReadAttributeInfo(dataInput, classFile);
			
			if(attributeInfo == null) {
				throw new NullPointerException("attributes[" + i + "] == null");
			}
			
			fieldInfo.addAttributeInfo(attributeInfo);
		}
	}
	
	private void doReloadAttributeInfoReaders() {
		this.attributeInfoReaders.reload();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static int doReadU2(final DataInput dataInput) {
		try {
			return dataInput.readUnsignedShort();
		} catch(final IOException e) {
			throw new ClassFileReaderException(e);
		}
	}
	
	private static int doReadU4(final DataInput dataInput) {
		try {
			return dataInput.readInt();
		} catch(final IOException e) {
			throw new ClassFileReaderException(e);
		}
	}
	
	private static void doReadAccessFlags(final DataInput dataInput, final FieldInfo fieldInfo) {
		final int accessFlags = doReadU2(dataInput);
		
		fieldInfo.setEnum((accessFlags & FieldInfo.ACC_ENUM) != 0);
		fieldInfo.setFinal((accessFlags & FieldInfo.ACC_FINAL) != 0);
		fieldInfo.setPrivate((accessFlags & FieldInfo.ACC_PRIVATE) != 0);
		fieldInfo.setProtected((accessFlags & FieldInfo.ACC_PROTECTED) != 0);
		fieldInfo.setPublic((accessFlags & FieldInfo.ACC_PUBLIC) != 0);
		fieldInfo.setStatic((accessFlags & FieldInfo.ACC_STATIC) != 0);
		fieldInfo.setSynthetic((accessFlags & FieldInfo.ACC_SYNTHETIC) != 0);
		fieldInfo.setTransient((accessFlags & FieldInfo.ACC_TRANSIENT) != 0);
		fieldInfo.setVolatile((accessFlags & FieldInfo.ACC_VOLATILE) != 0);
	}
	
	private static void doReadDescriptorIndex(final DataInput dataInput, final FieldInfo fieldInfo) {
		final int descriptorIndex = doReadU2(dataInput);
		
		fieldInfo.setDescriptorIndex(descriptorIndex);
	}
	
	private static void doReadNameIndex(final DataInput dataInput, final FieldInfo fieldInfo) {
		final int nameIndex = doReadU2(dataInput);
		
		fieldInfo.setNameIndex(nameIndex);
	}
}