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
package org.macroing.cel4j.java.model;

import java.util.ArrayList;
import java.util.List;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.CodeAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.Instruction;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantFieldRefInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantMethodRefInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.java.binary.classfile.descriptor.ClassName;

final class Instructions {
	private Instructions() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static List<String> findTypeNames(final ClassFile classFile, final CodeAttribute codeAttribute) {
		final List<String> typeNames = new ArrayList<>();
		
		for(final Instruction instruction : codeAttribute.getInstructions()) {
			switch(instruction.getOpcode()) {
				case Instruction.OPCODE_GET_FIELD:
					doAddTypeNameGetField(classFile, instruction, typeNames);
					
					break;
				case Instruction.OPCODE_GET_STATIC:
					doAddTypeNameGetStatic(classFile, instruction, typeNames);
					
					break;
				case Instruction.OPCODE_INVOKE_SPECIAL:
					doAddTypeNameInvokeSpecial(classFile, instruction, typeNames);
					
					break;
				case Instruction.OPCODE_INVOKE_STATIC:
					doAddTypeNameInvokeStatic(classFile, instruction, typeNames);
					
					break;
				case Instruction.OPCODE_INVOKE_VIRTUAL:
					doAddTypeNameInvokeVirtual(classFile, instruction, typeNames);
					
					break;
				default:
					break;
			}
		}
		
		return typeNames;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static void doAddTypeName(final ClassFile classFile, final ConstantFieldRefInfo constantFieldRefInfo, final List<String> typeNames) {
		final ConstantUTF8Info constantUTF8Info = ConstantUTF8Info.findByNameIndexByClassIndex(classFile, constantFieldRefInfo);
		
		final String className = ClassName.parseClassName(constantUTF8Info.getStringValue()).toExternalForm();
		
		typeNames.add(className);
	}
	
	private static void doAddTypeName(final ClassFile classFile, final ConstantMethodRefInfo constantMethodRefInfo, final List<String> typeNames) {
		final ConstantUTF8Info constantUTF8Info = ConstantUTF8Info.findByNameIndexByClassIndex(classFile, constantMethodRefInfo);
		
		final String className = ClassName.parseClassName(constantUTF8Info.getStringValue()).toExternalForm();
		
		typeNames.add(className);
	}
	
	private static void doAddTypeNameGetField(final ClassFile classFile, final Instruction instruction, final List<String> typeNames) {
		doAddTypeName(classFile, classFile.getCPInfo(instruction.getGetFieldIndex(), ConstantFieldRefInfo.class), typeNames);
	}
	
	private static void doAddTypeNameGetStatic(final ClassFile classFile, final Instruction instruction, final List<String> typeNames) {
		doAddTypeName(classFile, classFile.getCPInfo(instruction.getGetStaticIndex(), ConstantFieldRefInfo.class), typeNames);
	}
	
	private static void doAddTypeNameInvokeSpecial(final ClassFile classFile, final Instruction instruction, final List<String> typeNames) {
		doAddTypeName(classFile, classFile.getCPInfo((instruction.getOperand(0) << 8) | instruction.getOperand(1), ConstantMethodRefInfo.class), typeNames);
	}
	
	private static void doAddTypeNameInvokeStatic(final ClassFile classFile, final Instruction instruction, final List<String> typeNames) {
		doAddTypeName(classFile, classFile.getCPInfo((instruction.getOperand(0) << 8) | instruction.getOperand(1), ConstantMethodRefInfo.class), typeNames);
	}
	
	private static void doAddTypeNameInvokeVirtual(final ClassFile classFile, final Instruction instruction, final List<String> typeNames) {
		doAddTypeName(classFile, classFile.getCPInfo((instruction.getOperand(0) << 8) | instruction.getOperand(1), ConstantMethodRefInfo.class), typeNames);
	}
}