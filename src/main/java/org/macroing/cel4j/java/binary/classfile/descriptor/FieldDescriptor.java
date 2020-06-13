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
package org.macroing.cel4j.java.binary.classfile.descriptor;

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.ArrayList;
import java.util.List;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.FieldInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.LocalVariable;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.scanner.TextScanner;

//TODO: Add Javadocs!
public interface FieldDescriptor extends Node {
//	TODO: Add Javadocs!
	String toExternalForm();
	
//	TODO: Add Javadocs!
	String toInternalForm();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	static FieldDescriptor parseFieldDescriptor(final ClassFile classFile, final FieldInfo fieldInfo) {
		return parseFieldDescriptor(classFile.getCPInfo(classFile.getFieldInfo(fieldInfo).getDescriptorIndex(), ConstantUTF8Info.class).getString());
	}
	
//	TODO: Add Javadocs!
	static FieldDescriptor parseFieldDescriptor(final ClassFile classFile, final LocalVariable localVariable) {
		return parseFieldDescriptor(classFile.getCPInfo(localVariable.getDescriptorIndex(), ConstantUTF8Info.class).getString());
	}
	
//	TODO: Add Javadocs!
	static FieldDescriptor parseFieldDescriptor(final String string) {
		return Parsers.parseFieldDescriptor(new TextScanner(string));
	}
	
//	TODO: Add Javadocs!
	static List<FieldDescriptor> parseFieldDescriptors(final ClassFile classFile) {
		return classFile.getFieldInfos().stream().map(fieldInfo -> parseFieldDescriptor(classFile, fieldInfo)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
}