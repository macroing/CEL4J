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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.AnnotationDefaultAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.CodeAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.ConstantValueAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.DeprecatedAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.EnclosingMethodAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.ExceptionsAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.InnerClassesAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.LineNumberTableAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.LocalVariableTableAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.LocalVariableTypeTableAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.MethodParametersAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.RuntimeInvisibleAnnotationsAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.RuntimeInvisibleParameterAnnotationsAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.RuntimeVisibleAnnotationsAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.RuntimeVisibleParameterAnnotationsAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SignatureAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SourceDebugExtensionAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SourceFileAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.StackMapTableAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SyntheticAttribute;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.util.ParameterArguments;

final class AttributeInfoReaderImpl implements AttributeInfoReader {
	private final Map<String, AttributeInfoReader> attributeInfoReaders;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public AttributeInfoReaderImpl() {
		this.attributeInfoReaders = new HashMap<>();
		this.attributeInfoReaders.put(AnnotationDefaultAttribute.NAME, new AnnotationDefaultAttributeReader());
		this.attributeInfoReaders.put(CodeAttribute.NAME, new CodeAttributeReader());
		this.attributeInfoReaders.put(ConstantValueAttribute.NAME, new ConstantValueAttributeReader());
		this.attributeInfoReaders.put(DeprecatedAttribute.NAME, new DeprecatedAttributeReader());
		this.attributeInfoReaders.put(EnclosingMethodAttribute.NAME, new EnclosingMethodAttributeReader());
		this.attributeInfoReaders.put(ExceptionsAttribute.NAME, new ExceptionsAttributeReader());
		this.attributeInfoReaders.put(InnerClassesAttribute.NAME, new InnerClassesAttributeReader());
		this.attributeInfoReaders.put(LineNumberTableAttribute.NAME, new LineNumberTableAttributeReader());
		this.attributeInfoReaders.put(LocalVariableTableAttribute.NAME, new LocalVariableTableAttributeReader());
		this.attributeInfoReaders.put(LocalVariableTypeTableAttribute.NAME, new LocalVariableTypeTableAttributeReader());
		this.attributeInfoReaders.put(MethodParametersAttribute.NAME, new MethodParametersAttributeReader());
		this.attributeInfoReaders.put(RuntimeInvisibleAnnotationsAttribute.NAME, new RuntimeInvisibleAnnotationsAttributeReader());
		this.attributeInfoReaders.put(RuntimeInvisibleParameterAnnotationsAttribute.NAME, new RuntimeInvisibleParameterAnnotationsAttributeReader());
		this.attributeInfoReaders.put(RuntimeVisibleAnnotationsAttribute.NAME, new RuntimeVisibleAnnotationsAttributeReader());
		this.attributeInfoReaders.put(RuntimeVisibleParameterAnnotationsAttribute.NAME, new RuntimeVisibleParameterAnnotationsAttributeReader());
		this.attributeInfoReaders.put(SignatureAttribute.NAME, new SignatureAttributeReader());
		this.attributeInfoReaders.put(SourceDebugExtensionAttribute.NAME, new SourceDebugExtensionAttributeReader());
		this.attributeInfoReaders.put(SourceFileAttribute.NAME, new SourceFileAttributeReader());
		this.attributeInfoReaders.put(StackMapTableAttribute.NAME, new StackMapTableAttributeReader());
		this.attributeInfoReaders.put(SyntheticAttribute.NAME, new SyntheticAttributeReader());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public AttributeInfo read(final DataInput dataInput, final int attributeNameIndex, final List<CPInfo> constantPool) {
		final String name = doGetName(ParameterArguments.requireRange(attributeNameIndex, 1, Integer.MAX_VALUE), ParameterArguments.requireNonNullList(constantPool, "constantPool"));
		
		final AttributeInfoReader attributeInfoReader = this.attributeInfoReaders.get(name);
		
		if(attributeInfoReader == null) {
			throw new AttributeInfoReaderException(String.format("Unable to read attribute_info: name=%s", name));
		}
		
		return attributeInfoReader.read(Objects.requireNonNull(dataInput, "dataInput == null"), attributeNameIndex, constantPool);
	}
	
	@Override
	public boolean isSupported(final String name) {
		return this.attributeInfoReaders.containsKey(Objects.requireNonNull(name, "name == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doGetName(final int attributeNameIndex, final List<CPInfo> constantPool) {
		String name = "";
		
		if(attributeNameIndex >= 0 && attributeNameIndex < constantPool.size()) {
			final CPInfo cPInfo = constantPool.get(attributeNameIndex);
			
			if(cPInfo instanceof ConstantUTF8Info) {
				name = ConstantUTF8Info.class.cast(cPInfo).getString();
			}
		}
		
		return name;
	}
}