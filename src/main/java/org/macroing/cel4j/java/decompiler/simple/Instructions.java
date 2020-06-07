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
package org.macroing.cel4j.java.decompiler.simple;

import java.util.stream.Collectors;

import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.Instruction;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantClassInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantDoubleInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantDynamicInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantFieldRefInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantFloatInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantIntegerInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantInterfaceMethodRefInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantInvokeDynamicInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantLongInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantMethodHandleInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantMethodRefInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantMethodTypeInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantModuleInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantNameAndTypeInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantPackageInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantStringInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.java.binary.classfile.string.ClassName;
import org.macroing.cel4j.java.binary.classfile.string.FieldDescriptor;
import org.macroing.cel4j.java.binary.classfile.string.MethodDescriptor;
import org.macroing.cel4j.util.Strings;

final class Instructions {
	private Instructions() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String toString(final ClassFile classFile, final Instruction instruction) {
		switch(instruction.getOpcode()) {
			case Instruction.OPCODE_GET_FIELD:
				return doToStringGetField(classFile, instruction);
			case Instruction.OPCODE_GET_STATIC:
				return doToStringGetStatic(classFile, instruction);
			case Instruction.OPCODE_INSTANCE_OF:
				return doToStringInstanceOf(classFile, instruction);
			case Instruction.OPCODE_INVOKE_INTERFACE:
				return doToStringInvokeInterface(classFile, instruction);
			case Instruction.OPCODE_INVOKE_SPECIAL:
				return doToStringInvokeSpecial(classFile, instruction);
			case Instruction.OPCODE_INVOKE_STATIC:
				return doToStringInvokeStatic(classFile, instruction);
			case Instruction.OPCODE_INVOKE_VIRTUAL:
				return doToStringInvokeVirtual(classFile, instruction);
			case Instruction.OPCODE_L_D_C:
				return doToStringLDC(classFile, instruction);
			case Instruction.OPCODE_L_D_C_W:
				return doToStringLDCW(classFile, instruction);
			case Instruction.OPCODE_NEW:
				return doToStringNew(classFile, instruction);
			case Instruction.OPCODE_PUT_FIELD:
				return doToStringPutField(classFile, instruction);
			case Instruction.OPCODE_PUT_STATIC:
				return doToStringPutStatic(classFile, instruction);
			default:
				return "";
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doToString(final ClassFile classFile, final CPInfo cPInfo) {
		switch(cPInfo.getTag()) {
			case ConstantClassInfo.TAG:
				return doToString(classFile, ConstantClassInfo.class.cast(cPInfo));
			case ConstantDoubleInfo.TAG:
				return doToString(classFile, ConstantDoubleInfo.class.cast(cPInfo));
			case ConstantDynamicInfo.TAG:
				return doToString(classFile, ConstantDynamicInfo.class.cast(cPInfo));
			case ConstantFieldRefInfo.TAG:
				return doToString(classFile, ConstantFieldRefInfo.class.cast(cPInfo));
			case ConstantFloatInfo.TAG:
				return doToString(classFile, ConstantFloatInfo.class.cast(cPInfo));
			case ConstantIntegerInfo.TAG:
				return doToString(classFile, ConstantIntegerInfo.class.cast(cPInfo));
			case ConstantInterfaceMethodRefInfo.TAG:
				return doToString(classFile, ConstantInterfaceMethodRefInfo.class.cast(cPInfo));
			case ConstantInvokeDynamicInfo.TAG:
				return doToString(classFile, ConstantInvokeDynamicInfo.class.cast(cPInfo));
			case ConstantLongInfo.TAG:
				return doToString(classFile, ConstantLongInfo.class.cast(cPInfo));
			case ConstantMethodHandleInfo.TAG:
				return doToString(classFile, ConstantMethodHandleInfo.class.cast(cPInfo));
			case ConstantMethodRefInfo.TAG:
				return doToString(classFile, ConstantMethodRefInfo.class.cast(cPInfo));
			case ConstantMethodTypeInfo.TAG:
				return doToString(classFile, ConstantMethodTypeInfo.class.cast(cPInfo));
			case ConstantModuleInfo.TAG:
				return doToString(classFile, ConstantModuleInfo.class.cast(cPInfo));
			case ConstantNameAndTypeInfo.TAG:
				return doToString(classFile, ConstantNameAndTypeInfo.class.cast(cPInfo));
			case ConstantPackageInfo.TAG:
				return doToString(classFile, ConstantPackageInfo.class.cast(cPInfo));
			case ConstantStringInfo.TAG:
				return doToString(classFile, ConstantStringInfo.class.cast(cPInfo));
			case ConstantUTF8Info.TAG:
				return doToString(classFile, ConstantUTF8Info.class.cast(cPInfo));
			default:
				return "";
		}
	}
	
	private static String doToString(final ClassFile classFile, final ConstantClassInfo constantClassInfo) {
		final ConstantUTF8Info constantUTF8Info = ConstantUTF8Info.findByNameIndex(classFile, constantClassInfo);
		
		final String string = constantUTF8Info.getString();
		
		try {
			return ClassName.parseClassName(string).toExternalForm();
		} catch(final IllegalArgumentException e) {
			
		}
		
		try {
			return FieldDescriptor.parseFieldDescriptor(string).toExternalForm();
		} catch(final IllegalArgumentException e) {
			
		}
		
		return "";
	}
	
	private static String doToString(final ClassFile classFile, final ConstantDoubleInfo constantDoubleInfo) {
		return Double.toString(constantDoubleInfo.getDouble());
	}
	
	private static String doToString(final ClassFile classFile, final ConstantDynamicInfo constantDynamicInfo) {
		return "";
	}
	
	private static String doToString(final ClassFile classFile, final ConstantFieldRefInfo constantFieldRefInfo) {
		final ConstantUTF8Info constantUTF8Info0 = ConstantUTF8Info.findByNameIndexByClassIndex(classFile, constantFieldRefInfo);
		final ConstantUTF8Info constantUTF8Info1 = ConstantUTF8Info.findByNameIndexByNameAndTypeIndex(classFile, constantFieldRefInfo);
		
		final String className = ClassName.parseClassName(constantUTF8Info0.getString()).toExternalForm();
		final String fieldName = constantUTF8Info1.getString();
		
		return className + "." + fieldName;
	}
	
	private static String doToString(final ClassFile classFile, final ConstantFloatInfo constantFloatInfo) {
		return Float.toString(constantFloatInfo.getFloat());
	}
	
	private static String doToString(final ClassFile classFile, final ConstantIntegerInfo constantIntegerInfo) {
		return Integer.toString(constantIntegerInfo.getInt());
	}
	
	private static String doToString(final ClassFile classFile, final ConstantInterfaceMethodRefInfo constantInterfaceMethodRefInfo) {
		final ConstantUTF8Info constantUTF8Info0 = ConstantUTF8Info.findByNameIndexByClassIndex(classFile, constantInterfaceMethodRefInfo);
		final ConstantUTF8Info constantUTF8Info1 = ConstantUTF8Info.findByNameIndexByNameAndTypeIndex(classFile, constantInterfaceMethodRefInfo);
		final ConstantUTF8Info constantUTF8Info2 = ConstantUTF8Info.findByDescriptorIndexByNameAndTypeIndex(classFile, constantInterfaceMethodRefInfo);
		
		final String className = ClassName.parseClassName(constantUTF8Info0.getString()).toExternalForm();
		final String methodName = constantUTF8Info1.getString();
		final String method = "(" + Strings.optional(MethodDescriptor.parseMethodDescriptor(constantUTF8Info2.getString()).getParameterDescriptors().stream().map(parameterDescriptor -> parameterDescriptor.toExternalForm()).collect(Collectors.toList())) + ")";
		
		return methodName.equals("<init>") ? className + method : className + "." + methodName + method;
	}
	
	private static String doToString(final ClassFile classFile, final ConstantInvokeDynamicInfo constantInvokeDynamicInfo) {
		return "";
	}
	
	private static String doToString(final ClassFile classFile, final ConstantLongInfo constantLongInfo) {
		return Long.toString(constantLongInfo.getLong());
	}
	
	private static String doToString(final ClassFile classFile, final ConstantMethodHandleInfo constantMethodHandleInfo) {
		return "";
	}
	
	private static String doToString(final ClassFile classFile, final ConstantMethodRefInfo constantMethodRefInfo) {
		final ConstantUTF8Info constantUTF8Info0 = ConstantUTF8Info.findByNameIndexByClassIndex(classFile, constantMethodRefInfo);
		final ConstantUTF8Info constantUTF8Info1 = ConstantUTF8Info.findByNameIndexByNameAndTypeIndex(classFile, constantMethodRefInfo);
		final ConstantUTF8Info constantUTF8Info2 = ConstantUTF8Info.findByDescriptorIndexByNameAndTypeIndex(classFile, constantMethodRefInfo);
		
		final String className = ClassName.parseClassName(constantUTF8Info0.getString()).toExternalForm();
		final String methodName = constantUTF8Info1.getString();
		final String method = "(" + Strings.optional(MethodDescriptor.parseMethodDescriptor(constantUTF8Info2.getString()).getParameterDescriptors().stream().map(parameterDescriptor -> parameterDescriptor.toExternalForm()).collect(Collectors.toList())) + ")";
		
		return methodName.equals("<init>") ? className + method : className + "." + methodName + method;
	}
	
	private static String doToString(final ClassFile classFile, final ConstantMethodTypeInfo constantMethodTypeInfo) {
		return "";
	}
	
	private static String doToString(final ClassFile classFile, final ConstantModuleInfo constantModuleInfo) {
		return "";
	}
	
	private static String doToString(final ClassFile classFile, final ConstantNameAndTypeInfo constantNameAndTypeInfo) {
		return "";
	}
	
	private static String doToString(final ClassFile classFile, final ConstantPackageInfo constantPackageInfo) {
		return "";
	}
	
	private static String doToString(final ClassFile classFile, final ConstantStringInfo constantStringInfo) {
		return "\"" + ConstantUTF8Info.findByStringIndex(classFile, constantStringInfo).getString() + "\"";
	}
	
	private static String doToString(final ClassFile classFile, final ConstantUTF8Info constantUTF8Info) {
		return constantUTF8Info.getString();
	}
	
	private static String doToStringGetField(final ClassFile classFile, final Instruction instruction) {
		return doToString(classFile, classFile.getCPInfo(instruction.getGetFieldIndex(), ConstantFieldRefInfo.class));
	}
	
	private static String doToStringGetStatic(final ClassFile classFile, final Instruction instruction) {
		return doToString(classFile, classFile.getCPInfo(instruction.getGetStaticIndex(), ConstantFieldRefInfo.class));
	}
	
	private static String doToStringInstanceOf(final ClassFile classFile, final Instruction instruction) {
		return doToString(classFile, classFile.getCPInfo((instruction.getOperand(0) << 8) | instruction.getOperand(1), ConstantClassInfo.class));
	}
	
	private static String doToStringInvokeInterface(final ClassFile classFile, final Instruction instruction) {
		return doToString(classFile, classFile.getCPInfo((instruction.getOperand(0) << 8) | instruction.getOperand(1), ConstantInterfaceMethodRefInfo.class));
	}
	
	private static String doToStringInvokeSpecial(final ClassFile classFile, final Instruction instruction) {
		return doToString(classFile, classFile.getCPInfo((instruction.getOperand(0) << 8) | instruction.getOperand(1), ConstantMethodRefInfo.class));
	}
	
	private static String doToStringInvokeStatic(final ClassFile classFile, final Instruction instruction) {
		return doToString(classFile, classFile.getCPInfo((instruction.getOperand(0) << 8) | instruction.getOperand(1), ConstantMethodRefInfo.class));
	}
	
	private static String doToStringInvokeVirtual(final ClassFile classFile, final Instruction instruction) {
		return doToString(classFile, classFile.getCPInfo((instruction.getOperand(0) << 8) | instruction.getOperand(1), ConstantMethodRefInfo.class));
	}
	
	private static String doToStringLDC(final ClassFile classFile, final Instruction instruction) {
		return doToString(classFile, classFile.getCPInfo(instruction.getOperand(0), CPInfo.class));
	}
	
	private static String doToStringLDCW(final ClassFile classFile, final Instruction instruction) {
		return doToString(classFile, classFile.getCPInfo((instruction.getOperand(0) << 8) | instruction.getOperand(1), ConstantStringInfo.class));
	}
	
	private static String doToStringNew(final ClassFile classFile, final Instruction instruction) {
		return doToString(classFile, classFile.getCPInfo((instruction.getOperand(0) << 8) | instruction.getOperand(1), ConstantClassInfo.class));
	}
	
	private static String doToStringPutField(final ClassFile classFile, final Instruction instruction) {
		return doToString(classFile, classFile.getCPInfo((instruction.getOperand(0) << 8) | instruction.getOperand(1), ConstantFieldRefInfo.class));
	}
	
	private static String doToStringPutStatic(final ClassFile classFile, final Instruction instruction) {
		return doToString(classFile, classFile.getCPInfo((instruction.getOperand(0) << 8) | instruction.getOperand(1), ConstantFieldRefInfo.class));
	}
}