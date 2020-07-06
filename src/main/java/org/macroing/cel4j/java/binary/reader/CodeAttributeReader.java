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

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.IOException;
import java.util.List;
import java.util.ServiceLoader;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.CodeAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.ExceptionHandler;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.Instruction;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.UnimplementedAttribute;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;

final class CodeAttributeReader implements AttributeInfoReader {
	private final ServiceLoader<AttributeInfoReader> attributeInfoReaders = ServiceLoader.load(AttributeInfoReader.class);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public CodeAttributeReader() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public AttributeInfo read(final DataInput dataInput, final int attributeNameIndex, final List<CPInfo> constantPool) {
		try {
			doReloadAttributeInfoReaders();
			
			final
			CodeAttribute codeAttribute = new CodeAttribute(attributeNameIndex);
			codeAttribute.setMaxStack(doReadU2(dataInput));
			codeAttribute.setMaxLocals(doReadU2(dataInput));
			
			final int codeLength = doReadU4(dataInput);
			
			final byte[] code = new byte[codeLength];
			
			dataInput.readFully(code);
			
			doReadInstructions(codeAttribute, code);
			
			final int exceptionTableLength = dataInput.readUnsignedShort();
			
			for(int i = 0; i < exceptionTableLength; i++) {
				codeAttribute.addExceptionHandler(new ExceptionHandler(doReadU2(dataInput), doReadU2(dataInput), doReadU2(dataInput), doReadU2(dataInput)));
			}
			
			doReadAttributeInfos(dataInput, codeAttribute, constantPool);
			
			return codeAttribute;
		} catch(final IOException | IllegalArgumentException e) {
			throw new AttributeInfoReaderException("Unable to read Code_attribute", e);
		}
	}
	
	@Override
	public boolean isSupported(final String name) {
		return name.equals(CodeAttribute.NAME);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private AttributeInfo doReadAttributeInfo(final DataInput dataInput, final List<CPInfo> constantPool) {
		final int attributeNameIndex = doReadU2(dataInput);
		final int attributeLength = doReadU4(dataInput);
		
		final ConstantUTF8Info constantUTF8Info = ConstantUTF8Info.class.cast(constantPool.get(attributeNameIndex));
		
		final String name = constantUTF8Info.toString();
		
		for(final AttributeInfoReader attributeInfoReader : this.attributeInfoReaders) {
			if(attributeInfoReader.isSupported(name)) {
				return attributeInfoReader.read(dataInput, attributeNameIndex, constantPool);
			}
		}
		
		final AttributeInfoReader attributeInfoReader = new AttributeInfoReaderImpl();
		
		if(attributeInfoReader.isSupported(name)) {
			return attributeInfoReader.read(dataInput, attributeNameIndex, constantPool);
		}
		
		try {
			final byte[] info = new byte[attributeLength];
			
			dataInput.readFully(info);
			
			return new UnimplementedAttribute(name, attributeNameIndex, info);
		} catch(final IOException e) {
			throw new AttributeInfoReaderException("Unable to read attribute_info: name = " + name);
		}
	}
	
	private void doReadAttributeInfos(final DataInput dataInput, final CodeAttribute codeAttribute, final List<CPInfo> constantPool) {
		final int attributesCount = doReadU2(dataInput);
		
		for(int i = 0; i < attributesCount; i++) {
			final AttributeInfo attributeInfo = doReadAttributeInfo(dataInput, constantPool);
			
			if(attributeInfo == null) {
				throw new NullPointerException("attributes[" + i + "] == null");
			}
			
			codeAttribute.addAttributeInfo(attributeInfo);
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
			throw new AttributeInfoReaderException(e);
		}
	}
	
	private static int doReadU4(final DataInput dataInput) {
		try {
			return dataInput.readInt();
		} catch(final IOException e) {
			throw new AttributeInfoReaderException(e);
		}
	}
	
	private static void doReadInstructions(final CodeAttribute codeAttribute, final byte[] code) {
		for(int i = 0; i < code.length;) {
			int opcode = code[i++] & 0xFF;
			
			switch(opcode) {
				case Instruction.OPCODE_NOP:
					codeAttribute.addInstruction(Instruction.NOP);
					
					break;
				case Instruction.OPCODE_A_CONST_NULL:
					codeAttribute.addInstruction(Instruction.A_CONST_NULL);
					
					break;
				case Instruction.OPCODE_I_CONST_M1:
					codeAttribute.addInstruction(Instruction.I_CONST_M1);
					
					break;
				case Instruction.OPCODE_I_CONST_0:
					codeAttribute.addInstruction(Instruction.I_CONST_0);
					
					break;
				case Instruction.OPCODE_I_CONST_1:
					codeAttribute.addInstruction(Instruction.I_CONST_1);
					
					break;
				case Instruction.OPCODE_I_CONST_2:
					codeAttribute.addInstruction(Instruction.I_CONST_2);
					
					break;
				case Instruction.OPCODE_I_CONST_3:
					codeAttribute.addInstruction(Instruction.I_CONST_3);
					
					break;
				case Instruction.OPCODE_I_CONST_4:
					codeAttribute.addInstruction(Instruction.I_CONST_4);
					
					break;
				case Instruction.OPCODE_I_CONST_5:
					codeAttribute.addInstruction(Instruction.I_CONST_5);
					
					break;
				case Instruction.OPCODE_L_CONST_0:
					codeAttribute.addInstruction(Instruction.L_CONST_0);
					
					break;
				case Instruction.OPCODE_L_CONST_1:
					codeAttribute.addInstruction(Instruction.L_CONST_1);
					
					break;
				case Instruction.OPCODE_F_CONST_0:
					codeAttribute.addInstruction(Instruction.F_CONST_0);
					
					break;
				case Instruction.OPCODE_F_CONST_1:
					codeAttribute.addInstruction(Instruction.F_CONST_1);
					
					break;
				case Instruction.OPCODE_F_CONST_2:
					codeAttribute.addInstruction(Instruction.F_CONST_2);
					
					break;
				case Instruction.OPCODE_D_CONST_0:
					codeAttribute.addInstruction(Instruction.D_CONST_0);
					
					break;
				case Instruction.OPCODE_D_CONST_1:
					codeAttribute.addInstruction(Instruction.D_CONST_1);
					
					break;
				case Instruction.OPCODE_B_I_PUSH:
					codeAttribute.addInstruction(Instruction.getBIPush(code[i++]));
					
					break;
				case Instruction.OPCODE_S_I_PUSH:
					codeAttribute.addInstruction(Instruction.getSIPush(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_L_D_C:
					codeAttribute.addInstruction(Instruction.getLDC(code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_L_D_C_W:
					codeAttribute.addInstruction(Instruction.getLDCW(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_L_D_C_2_W:
					codeAttribute.addInstruction(Instruction.getLDC2W(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_I_LOAD:
					codeAttribute.addInstruction(Instruction.getILoad(code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_L_LOAD:
					codeAttribute.addInstruction(Instruction.getLLoad(code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_F_LOAD:
					codeAttribute.addInstruction(Instruction.getFLoad(code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_D_LOAD:
					codeAttribute.addInstruction(Instruction.getDLoad(code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_A_LOAD:
					codeAttribute.addInstruction(Instruction.getALoad(code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_I_LOAD_0:
					codeAttribute.addInstruction(Instruction.I_LOAD_0);
					
					break;
				case Instruction.OPCODE_I_LOAD_1:
					codeAttribute.addInstruction(Instruction.I_LOAD_1);
					
					break;
				case Instruction.OPCODE_I_LOAD_2:
					codeAttribute.addInstruction(Instruction.I_LOAD_2);
					
					break;
				case Instruction.OPCODE_I_LOAD_3:
					codeAttribute.addInstruction(Instruction.I_LOAD_3);
					
					break;
				case Instruction.OPCODE_L_LOAD_0:
					codeAttribute.addInstruction(Instruction.L_LOAD_0);
					
					break;
				case Instruction.OPCODE_L_LOAD_1:
					codeAttribute.addInstruction(Instruction.L_LOAD_1);
					
					break;
				case Instruction.OPCODE_L_LOAD_2:
					codeAttribute.addInstruction(Instruction.L_LOAD_2);
					
					break;
				case Instruction.OPCODE_L_LOAD_3:
					codeAttribute.addInstruction(Instruction.L_LOAD_3);
					
					break;
				case Instruction.OPCODE_F_LOAD_0:
					codeAttribute.addInstruction(Instruction.F_LOAD_0);
					
					break;
				case Instruction.OPCODE_F_LOAD_1:
					codeAttribute.addInstruction(Instruction.F_LOAD_1);
					
					break;
				case Instruction.OPCODE_F_LOAD_2:
					codeAttribute.addInstruction(Instruction.F_LOAD_2);
					
					break;
				case Instruction.OPCODE_F_LOAD_3:
					codeAttribute.addInstruction(Instruction.F_LOAD_3);
					
					break;
				case Instruction.OPCODE_D_LOAD_0:
					codeAttribute.addInstruction(Instruction.D_LOAD_0);
					
					break;
				case Instruction.OPCODE_D_LOAD_1:
					codeAttribute.addInstruction(Instruction.D_LOAD_1);
					
					break;
				case Instruction.OPCODE_D_LOAD_2:
					codeAttribute.addInstruction(Instruction.D_LOAD_2);
					
					break;
				case Instruction.OPCODE_D_LOAD_3:
					codeAttribute.addInstruction(Instruction.D_LOAD_3);
					
					break;
				case Instruction.OPCODE_A_LOAD_0:
					codeAttribute.addInstruction(Instruction.A_LOAD_0);
					
					break;
				case Instruction.OPCODE_A_LOAD_1:
					codeAttribute.addInstruction(Instruction.A_LOAD_1);
					
					break;
				case Instruction.OPCODE_A_LOAD_2:
					codeAttribute.addInstruction(Instruction.A_LOAD_2);
					
					break;
				case Instruction.OPCODE_A_LOAD_3:
					codeAttribute.addInstruction(Instruction.A_LOAD_3);
					
					break;
				case Instruction.OPCODE_I_A_LOAD:
					codeAttribute.addInstruction(Instruction.I_A_LOAD);
					
					break;
				case Instruction.OPCODE_L_A_LOAD:
					codeAttribute.addInstruction(Instruction.L_A_LOAD);
					
					break;
				case Instruction.OPCODE_F_A_LOAD:
					codeAttribute.addInstruction(Instruction.F_A_LOAD);
					
					break;
				case Instruction.OPCODE_D_A_LOAD:
					codeAttribute.addInstruction(Instruction.D_A_LOAD);
					
					break;
				case Instruction.OPCODE_A_A_LOAD:
					codeAttribute.addInstruction(Instruction.A_A_LOAD);
					
					break;
				case Instruction.OPCODE_B_A_LOAD:
					codeAttribute.addInstruction(Instruction.B_A_LOAD);
					
					break;
				case Instruction.OPCODE_C_A_LOAD:
					codeAttribute.addInstruction(Instruction.C_A_LOAD);
					
					break;
				case Instruction.OPCODE_S_A_LOAD:
					codeAttribute.addInstruction(Instruction.S_A_LOAD);
					
					break;
				case Instruction.OPCODE_I_STORE:
					codeAttribute.addInstruction(Instruction.getIStore(code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_L_STORE:
					codeAttribute.addInstruction(Instruction.getLStore(code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_F_STORE:
					codeAttribute.addInstruction(Instruction.getFStore(code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_D_STORE:
					codeAttribute.addInstruction(Instruction.getDStore(code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_A_STORE:
					codeAttribute.addInstruction(Instruction.getAStore(code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_I_STORE_0:
					codeAttribute.addInstruction(Instruction.I_STORE_0);
					
					break;
				case Instruction.OPCODE_I_STORE_1:
					codeAttribute.addInstruction(Instruction.I_STORE_1);
					
					break;
				case Instruction.OPCODE_I_STORE_2:
					codeAttribute.addInstruction(Instruction.I_STORE_2);
					
					break;
				case Instruction.OPCODE_I_STORE_3:
					codeAttribute.addInstruction(Instruction.I_STORE_3);
					
					break;
				case Instruction.OPCODE_L_STORE_0:
					codeAttribute.addInstruction(Instruction.L_STORE_0);
					
					break;
				case Instruction.OPCODE_L_STORE_1:
					codeAttribute.addInstruction(Instruction.L_STORE_1);
					
					break;
				case Instruction.OPCODE_L_STORE_2:
					codeAttribute.addInstruction(Instruction.L_STORE_2);
					
					break;
				case Instruction.OPCODE_L_STORE_3:
					codeAttribute.addInstruction(Instruction.L_STORE_3);
					
					break;
				case Instruction.OPCODE_F_STORE_0:
					codeAttribute.addInstruction(Instruction.F_STORE_0);
					
					break;
				case Instruction.OPCODE_F_STORE_1:
					codeAttribute.addInstruction(Instruction.F_STORE_1);
					
					break;
				case Instruction.OPCODE_F_STORE_2:
					codeAttribute.addInstruction(Instruction.F_STORE_2);
					
					break;
				case Instruction.OPCODE_F_STORE_3:
					codeAttribute.addInstruction(Instruction.F_STORE_3);
					
					break;
				case Instruction.OPCODE_D_STORE_0:
					codeAttribute.addInstruction(Instruction.D_STORE_0);
					
					break;
				case Instruction.OPCODE_D_STORE_1:
					codeAttribute.addInstruction(Instruction.D_STORE_1);
					
					break;
				case Instruction.OPCODE_D_STORE_2:
					codeAttribute.addInstruction(Instruction.D_STORE_2);
					
					break;
				case Instruction.OPCODE_D_STORE_3:
					codeAttribute.addInstruction(Instruction.D_STORE_3);
					
					break;
				case Instruction.OPCODE_A_STORE_0:
					codeAttribute.addInstruction(Instruction.A_STORE_0);
					
					break;
				case Instruction.OPCODE_A_STORE_1:
					codeAttribute.addInstruction(Instruction.A_STORE_1);
					
					break;
				case Instruction.OPCODE_A_STORE_2:
					codeAttribute.addInstruction(Instruction.A_STORE_2);
					
					break;
				case Instruction.OPCODE_A_STORE_3:
					codeAttribute.addInstruction(Instruction.A_STORE_3);
					
					break;
				case Instruction.OPCODE_I_A_STORE:
					codeAttribute.addInstruction(Instruction.I_A_STORE);
					
					break;
				case Instruction.OPCODE_L_A_STORE:
					codeAttribute.addInstruction(Instruction.L_A_STORE);
					
					break;
				case Instruction.OPCODE_F_A_STORE:
					codeAttribute.addInstruction(Instruction.F_A_STORE);
					
					break;
				case Instruction.OPCODE_D_A_STORE:
					codeAttribute.addInstruction(Instruction.D_A_STORE);
					
					break;
				case Instruction.OPCODE_A_A_STORE:
					codeAttribute.addInstruction(Instruction.A_A_STORE);
					
					break;
				case Instruction.OPCODE_B_A_STORE:
					codeAttribute.addInstruction(Instruction.B_A_STORE);
					
					break;
				case Instruction.OPCODE_C_A_STORE:
					codeAttribute.addInstruction(Instruction.C_A_STORE);
					
					break;
				case Instruction.OPCODE_S_A_STORE:
					codeAttribute.addInstruction(Instruction.S_A_STORE);
					
					break;
				case Instruction.OPCODE_POP:
					codeAttribute.addInstruction(Instruction.POP);
					
					break;
				case Instruction.OPCODE_POP_2:
					codeAttribute.addInstruction(Instruction.POP_2);
					
					break;
				case Instruction.OPCODE_DUP:
					codeAttribute.addInstruction(Instruction.DUP);
					
					break;
				case Instruction.OPCODE_DUP_X_1:
					codeAttribute.addInstruction(Instruction.DUP_X_1);
					
					break;
				case Instruction.OPCODE_DUP_X_2:
					codeAttribute.addInstruction(Instruction.DUP_X_2);
					
					break;
				case Instruction.OPCODE_DUP_2:
					codeAttribute.addInstruction(Instruction.DUP_2);
					
					break;
				case Instruction.OPCODE_DUP_2_X_1:
					codeAttribute.addInstruction(Instruction.DUP_2_X_1);
					
					break;
				case Instruction.OPCODE_DUP_2_X_2:
					codeAttribute.addInstruction(Instruction.DUP_2_X_2);
					
					break;
				case Instruction.OPCODE_SWAP:
					codeAttribute.addInstruction(Instruction.SWAP);
					
					break;
				case Instruction.OPCODE_I_ADD:
					codeAttribute.addInstruction(Instruction.I_ADD);
					
					break;
				case Instruction.OPCODE_L_ADD:
					codeAttribute.addInstruction(Instruction.L_ADD);
					
					break;
				case Instruction.OPCODE_F_ADD:
					codeAttribute.addInstruction(Instruction.F_ADD);
					
					break;
				case Instruction.OPCODE_D_ADD:
					codeAttribute.addInstruction(Instruction.D_ADD);
					
					break;
				case Instruction.OPCODE_I_SUB:
					codeAttribute.addInstruction(Instruction.I_SUB);
					
					break;
				case Instruction.OPCODE_L_SUB:
					codeAttribute.addInstruction(Instruction.L_SUB);
					
					break;
				case Instruction.OPCODE_F_SUB:
					codeAttribute.addInstruction(Instruction.F_SUB);
					
					break;
				case Instruction.OPCODE_D_SUB:
					codeAttribute.addInstruction(Instruction.D_SUB);
					
					break;
				case Instruction.OPCODE_I_MUL:
					codeAttribute.addInstruction(Instruction.I_MUL);
					
					break;
				case Instruction.OPCODE_L_MUL:
					codeAttribute.addInstruction(Instruction.L_MUL);
					
					break;
				case Instruction.OPCODE_F_MUL:
					codeAttribute.addInstruction(Instruction.F_MUL);
					
					break;
				case Instruction.OPCODE_D_MUL:
					codeAttribute.addInstruction(Instruction.D_MUL);
					
					break;
				case Instruction.OPCODE_I_DIV:
					codeAttribute.addInstruction(Instruction.I_DIV);
					
					break;
				case Instruction.OPCODE_L_DIV:
					codeAttribute.addInstruction(Instruction.L_DIV);
					
					break;
				case Instruction.OPCODE_F_DIV:
					codeAttribute.addInstruction(Instruction.F_DIV);
					
					break;
				case Instruction.OPCODE_D_DIV:
					codeAttribute.addInstruction(Instruction.D_DIV);
					
					break;
				case Instruction.OPCODE_I_REM:
					codeAttribute.addInstruction(Instruction.I_REM);
					
					break;
				case Instruction.OPCODE_L_REM:
					codeAttribute.addInstruction(Instruction.L_REM);
					
					break;
				case Instruction.OPCODE_F_REM:
					codeAttribute.addInstruction(Instruction.F_REM);
					
					break;
				case Instruction.OPCODE_D_REM:
					codeAttribute.addInstruction(Instruction.D_REM);
					
					break;
				case Instruction.OPCODE_I_NEG:
					codeAttribute.addInstruction(Instruction.I_NEG);
					
					break;
				case Instruction.OPCODE_L_NEG:
					codeAttribute.addInstruction(Instruction.L_NEG);
					
					break;
				case Instruction.OPCODE_F_NEG:
					codeAttribute.addInstruction(Instruction.F_NEG);
					
					break;
				case Instruction.OPCODE_D_NEG:
					codeAttribute.addInstruction(Instruction.D_NEG);
					
					break;
				case Instruction.OPCODE_I_SH_L:
					codeAttribute.addInstruction(Instruction.I_SH_L);
					
					break;
				case Instruction.OPCODE_L_SH_L:
					codeAttribute.addInstruction(Instruction.L_SH_L);
					
					break;
				case Instruction.OPCODE_I_SH_R:
					codeAttribute.addInstruction(Instruction.I_SH_R);
					
					break;
				case Instruction.OPCODE_L_SH_R:
					codeAttribute.addInstruction(Instruction.L_SH_R);
					
					break;
				case Instruction.OPCODE_I_U_SH_R:
					codeAttribute.addInstruction(Instruction.I_U_SH_R);
					
					break;
				case Instruction.OPCODE_L_U_SH_R:
					codeAttribute.addInstruction(Instruction.L_U_SH_R);
					
					break;
				case Instruction.OPCODE_I_AND:
					codeAttribute.addInstruction(Instruction.I_AND);
					
					break;
				case Instruction.OPCODE_L_AND:
					codeAttribute.addInstruction(Instruction.L_AND);
					
					break;
				case Instruction.OPCODE_I_OR:
					codeAttribute.addInstruction(Instruction.I_OR);
					
					break;
				case Instruction.OPCODE_L_OR:
					codeAttribute.addInstruction(Instruction.L_OR);
					
					break;
				case Instruction.OPCODE_I_XOR:
					codeAttribute.addInstruction(Instruction.I_XOR);
					
					break;
				case Instruction.OPCODE_L_XOR:
					codeAttribute.addInstruction(Instruction.L_XOR);
					
					break;
				case Instruction.OPCODE_I_INC:
					codeAttribute.addInstruction(Instruction.getIInc(code[i++] & 0xFF, code[i++]));
					
					break;
				case Instruction.OPCODE_I_2_L:
					codeAttribute.addInstruction(Instruction.I_2_L);
					
					break;
				case Instruction.OPCODE_I_2_F:
					codeAttribute.addInstruction(Instruction.I_2_F);
					
					break;
				case Instruction.OPCODE_I_2_D:
					codeAttribute.addInstruction(Instruction.I_2_D);
					
					break;
				case Instruction.OPCODE_L_2_I:
					codeAttribute.addInstruction(Instruction.L_2_I);
					
					break;
				case Instruction.OPCODE_L_2_F:
					codeAttribute.addInstruction(Instruction.L_2_F);
					
					break;
				case Instruction.OPCODE_L_2_D:
					codeAttribute.addInstruction(Instruction.L_2_D);
					
					break;
				case Instruction.OPCODE_F_2_I:
					codeAttribute.addInstruction(Instruction.F_2_I);
					
					break;
				case Instruction.OPCODE_F_2_L:
					codeAttribute.addInstruction(Instruction.F_2_L);
					
					break;
				case Instruction.OPCODE_F_2_D:
					codeAttribute.addInstruction(Instruction.F_2_D);
					
					break;
				case Instruction.OPCODE_D_2_I:
					codeAttribute.addInstruction(Instruction.D_2_I);
					
					break;
				case Instruction.OPCODE_D_2_L:
					codeAttribute.addInstruction(Instruction.D_2_L);
					
					break;
				case Instruction.OPCODE_D_2_F:
					codeAttribute.addInstruction(Instruction.D_2_F);
					
					break;
				case Instruction.OPCODE_I_2_B:
					codeAttribute.addInstruction(Instruction.I_2_B);
					
					break;
				case Instruction.OPCODE_I_2_C:
					codeAttribute.addInstruction(Instruction.I_2_C);
					
					break;
				case Instruction.OPCODE_I_2_S:
					codeAttribute.addInstruction(Instruction.I_2_S);
					
					break;
				case Instruction.OPCODE_L_CMP:
					codeAttribute.addInstruction(Instruction.L_CMP);
					
					break;
				case Instruction.OPCODE_F_CMP_L:
					codeAttribute.addInstruction(Instruction.F_CMP_L);
					
					break;
				case Instruction.OPCODE_F_CMP_G:
					codeAttribute.addInstruction(Instruction.F_CMP_G);
					
					break;
				case Instruction.OPCODE_D_CMP_L:
					codeAttribute.addInstruction(Instruction.D_CMP_L);
					
					break;
				case Instruction.OPCODE_D_CMP_G:
					codeAttribute.addInstruction(Instruction.D_CMP_G);
					
					break;
				case Instruction.OPCODE_IF_EQ:
					codeAttribute.addInstruction(Instruction.getIfEq(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_IF_N_E:
					codeAttribute.addInstruction(Instruction.getIfNE(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_IF_L_T:
					codeAttribute.addInstruction(Instruction.getIfLT(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_IF_G_E:
					codeAttribute.addInstruction(Instruction.getIfGE(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_IF_G_T:
					codeAttribute.addInstruction(Instruction.getIfGT(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_IF_L_E:
					codeAttribute.addInstruction(Instruction.getIfLE(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_IF_I_CMP_EQ:
					codeAttribute.addInstruction(Instruction.getIfICmpEq(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_IF_I_CMP_N_E:
					codeAttribute.addInstruction(Instruction.getIfICmpNE(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_IF_I_CMP_L_T:
					codeAttribute.addInstruction(Instruction.getIfICmpLT(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_IF_I_CMP_G_E:
					codeAttribute.addInstruction(Instruction.getIfICmpGE(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_IF_I_CMP_G_T:
					codeAttribute.addInstruction(Instruction.getIfICmpGT(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_IF_I_CMP_L_E:
					codeAttribute.addInstruction(Instruction.getIfICmpLE(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_IF_A_CMP_EQ:
					codeAttribute.addInstruction(Instruction.getIfACmpEq(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_IF_A_CMP_N_E:
					codeAttribute.addInstruction(Instruction.getIfACmpNE(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_GO_TO:
					codeAttribute.addInstruction(Instruction.getGoTo(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_J_S_R:
					codeAttribute.addInstruction(Instruction.getJSR(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_RET:
					codeAttribute.addInstruction(Instruction.getRet(code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_TABLE_SWITCH: {
					final int padding = (4 - (i % 4)) % 4;
					
					final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					
					for(int j = 0; j < padding; j++) {
						byteArrayOutputStream.write(code[i++]);
					}
					
					for(int j = 0; j < 4; j++) {
						byteArrayOutputStream.write(code[i++]);
					}
					
					final int lowByte1 = code[i + 0];
					final int lowByte2 = code[i + 1];
					final int lowByte3 = code[i + 2];
					final int lowByte4 = code[i + 3];
					
					final int highByte1 = code[i + 4];
					final int highByte2 = code[i + 5];
					final int highByte3 = code[i + 6];
					final int highByte4 = code[i + 7];
					
					for(int j = 0; j < 8; j++) {
						byteArrayOutputStream.write(code[i++]);
					}
					
					final int low = ((lowByte1 & 0xFF) << 24) | ((lowByte2 & 0xFF) << 16) | ((lowByte3 & 0xFF) << 8) | (lowByte4 & 0xFF);
					final int high = ((highByte1 & 0xFF) << 24) | ((highByte2 & 0xFF) << 16) | ((highByte3 & 0xFF) << 8) | (highByte4 & 0xFF);
					
					final int jumpOffsets = high - low + 1;
					
					for(int j = 0; j < jumpOffsets * 4; j++) {
						byteArrayOutputStream.write(code[i++]);
					}
					
					final byte[] bytes = byteArrayOutputStream.toByteArray();
					
					final int[] operands = new int[bytes.length];
					
					for(int j = 0; j < operands.length; j++) {
						operands[j] = bytes[j];
					}
					
					codeAttribute.addInstruction(Instruction.getTableSwitch(padding, operands));
					
					break;
				}
				case Instruction.OPCODE_LOOKUP_SWITCH: {
					final int padding = (4 - (i % 4)) % 4;
					
					final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					
					for(int j = 0; j < padding; j++) {
						byteArrayOutputStream.write(code[i++]);
					}
					
					for(int j = 0; j < 4; j++) {
						byteArrayOutputStream.write(code[i++]);
					}
					
					final int nPairs1 = code[i + 0];
					final int nPairs2 = code[i + 1];
					final int nPairs3 = code[i + 2];
					final int nPairs4 = code[i + 3];
					
					for(int j = 0; j < 4; j++) {
						byteArrayOutputStream.write(code[i++]);
					}
					
					final int nPairs = ((nPairs1 & 0xFF) << 24) | ((nPairs2 & 0xFF) << 16) | ((nPairs3 & 0xFF) << 8) | (nPairs4 & 0xFF);
					
					for(int j = 0; j < nPairs * 8; j++) {
						byteArrayOutputStream.write(code[i++]);
					}
					
					final byte[] bytes = byteArrayOutputStream.toByteArray();
					
					final int[] operands = new int[bytes.length];
					
					for(int j = 0; j < operands.length; j++) {
						operands[j] = bytes[j];
					}
					
					codeAttribute.addInstruction(Instruction.getLookupSwitch(padding, operands));
					
					break;
				}
				case Instruction.OPCODE_I_RETURN:
					codeAttribute.addInstruction(Instruction.I_RETURN);
					
					break;
				case Instruction.OPCODE_L_RETURN:
					codeAttribute.addInstruction(Instruction.L_RETURN);
					
					break;
				case Instruction.OPCODE_F_RETURN:
					codeAttribute.addInstruction(Instruction.F_RETURN);
					
					break;
				case Instruction.OPCODE_D_RETURN:
					codeAttribute.addInstruction(Instruction.D_RETURN);
					
					break;
				case Instruction.OPCODE_A_RETURN:
					codeAttribute.addInstruction(Instruction.A_RETURN);
					
					break;
				case Instruction.OPCODE_RETURN:
					codeAttribute.addInstruction(Instruction.RETURN);
					
					break;
				case Instruction.OPCODE_GET_STATIC:
					codeAttribute.addInstruction(Instruction.getGetStatic(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_PUT_STATIC:
					codeAttribute.addInstruction(Instruction.getPutStatic(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_GET_FIELD:
					codeAttribute.addInstruction(Instruction.getGetField(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_PUT_FIELD:
					codeAttribute.addInstruction(Instruction.getPutField(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_INVOKE_VIRTUAL:
					codeAttribute.addInstruction(Instruction.getInvokeVirtual(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_INVOKE_SPECIAL:
					codeAttribute.addInstruction(Instruction.getInvokeSpecial(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_INVOKE_STATIC:
					codeAttribute.addInstruction(Instruction.getInvokeStatic(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_INVOKE_INTERFACE:
					codeAttribute.addInstruction(Instruction.getInvokeInterface(code[i++] & 0xFF, code[i++] & 0xFF, code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_INVOKE_DYNAMIC:
					codeAttribute.addInstruction(Instruction.getInvokeDynamic(code[i++] & 0xFF, code[i++] & 0xFF, code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_NEW:
					codeAttribute.addInstruction(Instruction.getNew(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_NEW_ARRAY:
					codeAttribute.addInstruction(Instruction.getNewArray(code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_A_NEW_ARRAY:
					codeAttribute.addInstruction(Instruction.getANewArray(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_ARRAY_LENGTH:
					codeAttribute.addInstruction(Instruction.ARRAY_LENGTH);
					
					break;
				case Instruction.OPCODE_A_THROW:
					codeAttribute.addInstruction(Instruction.A_THROW);
					
					break;
				case Instruction.OPCODE_CHECK_CAST:
					codeAttribute.addInstruction(Instruction.getCheckCast(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_INSTANCE_OF:
					codeAttribute.addInstruction(Instruction.getInstanceOf(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_MONITOR_ENTER:
					codeAttribute.addInstruction(Instruction.MONITOR_ENTER);
					
					break;
				case Instruction.OPCODE_MONITOR_EXIT:
					codeAttribute.addInstruction(Instruction.MONITOR_EXIT);
					
					break;
				case Instruction.OPCODE_WIDE:
					opcode = code[i++] & 0xFF;
					
					switch(opcode) {
						case Instruction.OPCODE_A_LOAD:
						case Instruction.OPCODE_A_STORE:
						case Instruction.OPCODE_D_LOAD:
						case Instruction.OPCODE_D_STORE:
						case Instruction.OPCODE_F_LOAD:
						case Instruction.OPCODE_F_STORE:
						case Instruction.OPCODE_I_LOAD:
						case Instruction.OPCODE_I_STORE:
						case Instruction.OPCODE_L_LOAD:
						case Instruction.OPCODE_L_STORE:
						case Instruction.OPCODE_RET: {
							codeAttribute.addInstruction(Instruction.getWide(opcode, code[i++] & 0xFF, code[i++] & 0xFF));
							
							break;
						}
						case Instruction.OPCODE_I_INC: {
							codeAttribute.addInstruction(Instruction.getWide(opcode, code[i++] & 0xFF, code[i++] & 0xFF, code[i++] & 0xFF, code[i++] & 0xFF));
							
							break;
						}
						default:
							throw new IllegalArgumentException(String.format("Illegal opcode for wide: %s", Integer.toString(opcode)));
					}
					
					break;
				case Instruction.OPCODE_MULTI_A_NEW_ARRAY:
					codeAttribute.addInstruction(Instruction.getMultiANewArray(code[i++] & 0xFF, code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				case Instruction.OPCODE_IF_NULL: {
					codeAttribute.addInstruction(Instruction.getIfNull(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				}
				case Instruction.OPCODE_IF_NON_NULL: {
					codeAttribute.addInstruction(Instruction.getIfNonNull(code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				}
				case Instruction.OPCODE_GO_TO_W: {
					codeAttribute.addInstruction(Instruction.getGoToW(code[i++] & 0xFF, code[i++] & 0xFF, code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				}
				case Instruction.OPCODE_J_S_R_W: {
					codeAttribute.addInstruction(Instruction.getJSRW(code[i++] & 0xFF, code[i++] & 0xFF, code[i++] & 0xFF, code[i++] & 0xFF));
					
					break;
				}
				case Instruction.OPCODE_BREAK_POINT:
					codeAttribute.addInstruction(Instruction.BREAK_POINT);
					
					break;
				case 0xCB:
				case 0xCC:
				case 0xCD:
				case 0xCE:
				case 0xCF:
				case 0xD0:
				case 0xD1:
				case 0xD2:
				case 0xD3:
				case 0xD4:
				case 0xD5:
				case 0xD6:
				case 0xD7:
				case 0xD8:
				case 0xD9:
				case 0xDA:
				case 0xDB:
				case 0xDC:
				case 0xDD:
				case 0xDE:
				case 0xDF:
				case 0xE0:
				case 0xE1:
				case 0xE2:
				case 0xE3:
				case 0xE4:
				case 0xE5:
				case 0xE6:
				case 0xE7:
				case 0xE8:
				case 0xE9:
				case 0xEA:
				case 0xEB:
				case 0xEC:
				case 0xED:
				case 0xEE:
				case 0xEF:
				case 0xF0:
				case 0xF1:
				case 0xF2:
				case 0xF3:
				case 0xF4:
				case 0xF5:
				case 0xF6:
				case 0xF7:
				case 0xF8:
				case 0xF9:
				case 0xFA:
				case 0xFB:
				case 0xFC:
				case 0xFD:
					break;
				case Instruction.OPCODE_IMP_DEP_1:
					codeAttribute.addInstruction(Instruction.IMP_DEP_1);
					
					break;
				case Instruction.OPCODE_IMP_DEP_2:
					codeAttribute.addInstruction(Instruction.IMP_DEP_2);
					
					break;
				default:
					return;
			}
		}
	}
}